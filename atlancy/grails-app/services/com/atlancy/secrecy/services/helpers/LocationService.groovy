package com.atlancy.secrecy.services.helpers

import org.codehaus.jackson.map.DeserializationConfig
import org.codehaus.jackson.map.ObjectMapper
import org.codehaus.jackson.type.TypeReference
import org.springframework.dao.DuplicateKeyException

import com.atlancy.domain.Location
import com.atlancy.domain.TargetPhone
import com.atlancy.secrecy.services.helpers.interfaces.ServiceInterface
import com.spy.tools.SpyUtils
import com.xaviar.market.rest.item.DataHolder
import com.xaviar.market.rest.item.DataHolderConstants

class LocationService implements ServiceInterface{

	@Override
	public Long getLastTs(String authSimSubscriberId) {
		List <Location> locations = Location.createCriteria().list{
			and {
				targetPhone{ eq("simSubscriberId", authSimSubscriberId) }
			}
			order("time","desc")
		};

		if(locations!=null && locations.size()>0){
			return locations.get(0).time.getTime();
		}else{
			return null;
		}
	}

	@Override
	public String getWishString(String authSimSubscriberId) {
		def locationTs = getLastTs(authSimSubscriberId);
		if(locationTs!=null){
			//TODO - change to LOCATION no need for different wish like LOCATION_EVENT it needs to be managed by timestamp only.
			return "\"" + DataHolderConstants.LOCATION_EVENT + "\":" + locationTs;
		}else{
			return "\"" + DataHolderConstants.LOCATION_EVENT + "\":0";
		}
	}


	def addLocationEvents(DataHolder dataHolder, TargetPhone targetPhone) {
		String clientLocation = dataHolder.get(DataHolderConstants.LOCATION_EVENT);
		if (clientLocation != null) {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			List<Location> locations = mapper.readValue(clientLocation,new TypeReference<List<Location>>() {
					});
			def address = "";
			locations.each{
				try{
					it.targetPhone=targetPhone;
					try{
						address = SpyUtils.getGeocode(it.latitude,it.longitude).get(0).formattedAddress;
					}catch(Exception e){
						address = "כתובת לא קיימת";
					}
					it.id=it.id+"_"+targetPhone.simSubscriberId;
					it.address = address;
					it.recordOrigin="EVENT";
					it.recordState="CREATED";
					it.save(flush:true, failOnError:true,insert:true);
				}catch(DuplicateKeyException e){
					println "Duplicate Key Exception : " + it.id+"_"+targetPhone.simSubscriberId;
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}
}
