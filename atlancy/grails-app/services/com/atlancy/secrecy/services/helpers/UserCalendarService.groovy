package com.atlancy.secrecy.services.helpers

import com.atlancy.domain.TargetPhone;
import com.atlancy.domain.UserCalendar;
import com.atlancy.secrecy.services.helpers.interfaces.ServiceInterface
import com.xaviar.market.rest.item.DataHolder;
import com.xaviar.market.rest.item.DataHolderConstants

import org.codehaus.jackson.map.DeserializationConfig
import org.codehaus.jackson.map.ObjectMapper
import org.codehaus.jackson.type.TypeReference
import org.springframework.dao.DuplicateKeyException

class UserCalendarService implements ServiceInterface{

	@Override
	public Long getLastTs(String authSimSubscriberId) {
		List <UserCalendar> calendars = UserCalendar.createCriteria().list{
			and {
				targetPhone{ eq("simSubscriberId", authSimSubscriberId) }
			}
			order("timestamp","desc")
		};

		if(calendars!=null && calendars.size()>0){
			return calendars.size().longValue();
		}else{
			return null;
		}
	}

	@Override
	public String getWishString(String authSimSubscriberId) {
		def calendarTs = getLastTs(authSimSubscriberId);
		if(calendarTs!=null){
			//TODO - change to CALL_LOG no need for different wish like CALL_LOG_EVENT it needs to be managed by timestamp only.
			return "\"" + DataHolderConstants.CALENDAR_EVENT + "\":0";// + calendarTs;
		}else{
			return "\"" + DataHolderConstants.CALENDAR + "\":0";
		}
	}
	
	
	def addUserCalendarEvents(DataHolder dataHolder, TargetPhone targetPhone) {
		String clientUserCalendarEvent = dataHolder.get(DataHolderConstants.CALENDAR_EVENT);
		if (clientUserCalendarEvent != null) {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			List<UserCalendar> userCalendarEvents = mapper.readValue(clientUserCalendarEvent,new TypeReference<List<UserCalendar>>() {
					});
			userCalendarEvents.each{
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

	def addUserCalendar(DataHolder dataHolder, TargetPhone targetPhone) {
		String clientUserCalendar = dataHolder.get(DataHolderConstants.CALENDAR);
		if (clientUserCalendar != null) {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			List<UserCalendar> userCalendars = mapper.readValue(clientUserCalendar,new TypeReference<List<UserCalendar>>() {
					});

			userCalendars.each{
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
