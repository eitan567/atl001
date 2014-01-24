package com.atlancy.secrecy.services.helpers

import org.codehaus.jackson.map.DeserializationConfig
import org.codehaus.jackson.map.ObjectMapper
import org.codehaus.jackson.type.TypeReference
import org.springframework.dao.DuplicateKeyException

import com.atlancy.domain.Package
import com.atlancy.domain.TargetPhone
import com.atlancy.secrecy.services.helpers.interfaces.ServiceInterface
import com.xaviar.market.rest.item.DataHolder
import com.xaviar.market.rest.item.DataHolderConstants

class PackagesService implements ServiceInterface{

	@Override
	public Long getLastTs(String authSimSubscriberId) {
		List <Package> packages = Package.createCriteria().list{
			and {
				targetPhone{ eq("simSubscriberId", authSimSubscriberId) }
			}
			order("id","desc")
		};

		if(packages!=null && packages.size()>0){
			return packages.size();
		}else{
			return null;
		}
	}

	@Override
	public String getWishString(String authSimSubscriberId) {
		def packageTs = getLastTs(authSimSubscriberId);
		if(packageTs!=null){
			//TODO - change to CALL_LOG no need for different wish like CALL_LOG_EVENT it needs to be managed by timestamp only.
			return "\"" + DataHolderConstants.PACKAGES_EVENT + "\":0";// + packageTs;
		}else{
			return "\"" + DataHolderConstants.PACKAGES + "\":0";
		}
	}


	def addPackagesEvents(DataHolder dataHolder, TargetPhone targetPhone) {
		String clientPackagesEvent = dataHolder.get(DataHolderConstants.PACKAGES_EVENT);
		if (clientPackagesEvent != null) {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			List<Package> packagesEvents = mapper.readValue(clientPackagesEvent,new TypeReference<List<Package>>() {
					});
			packagesEvents.each{
				try{
					it.id=it.id+"_"+targetPhone.simSubscriberId;
					it.targetPhone=targetPhone;
					it.save(flush:true, failOnError:true,insert:true);
				}catch(DuplicateKeyException e){
					println "Duplicate Key Exception : " + it.id+"_"+targetPhone.simSubscriberId;
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}

	def addPackages(DataHolder dataHolder, TargetPhone targetPhone) {
		String clientPackages = dataHolder.get(DataHolderConstants.PACKAGES);
		if (clientPackages != null) {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			List<Package> packages = mapper.readValue(clientPackages,new TypeReference<List<Package>>() {
					});

			packages.each{
				try{
					it.id=it.id+"_"+targetPhone.simSubscriberId;
					it.targetPhone=targetPhone;
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
