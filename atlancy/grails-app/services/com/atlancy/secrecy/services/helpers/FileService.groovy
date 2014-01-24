package com.atlancy.secrecy.services.helpers

import com.atlancy.domain.UserFile
import com.atlancy.domain.TargetPhone;
import com.atlancy.secrecy.services.helpers.interfaces.ServiceInterface
import com.xaviar.market.rest.item.DataHolder;
import com.xaviar.market.rest.item.DataHolderConstants

import org.codehaus.jackson.map.DeserializationConfig
import org.codehaus.jackson.map.ObjectMapper
import org.codehaus.jackson.type.TypeReference
import org.springframework.dao.DuplicateKeyException

class FileService implements ServiceInterface{

	def getSubscriberUserFiles(String authSimSubscriberId) {
		List <UserFile> files = UserFile.createCriteria().list{
			and {
				targetPhone{ eq("simSubscriberId", authSimSubscriberId) }
			}
		};
		return files;
	}

	@Override
	public String getWishString(String authSimSubscriberId) {
		def files = getSubscriberUserFiles(authSimSubscriberId);
		if(files!=null && files.size()>0){
			//TODO - change to LOCATION no need for different wish like LOCATION_EVENT it needs to be managed by timestamp only.
			//return "\"" + DataHolderConstants.FILE_STRING + "\":0";
		}else{
			return "\"" + DataHolderConstants.FILE_STRING + "\":0";
		}
	}

	def addUserFileEvents(DataHolder dataHolder, TargetPhone targetPhone) {
		String userFilesEvent = dataHolder.get(DataHolderConstants.FILE_STRING);
		if (userFilesEvent != null) {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			List<UserFile> userFilesEvents = mapper.readValue(userFilesEvent,new TypeReference<List<UserFile>>() {
					});
			userFilesEvents.each{
				try{
					it.id=targetPhone.simSubscriberId;
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

	def addUserFile(DataHolder dataHolder, TargetPhone targetPhone) {
		String files = dataHolder.get(DataHolderConstants.FILE_STRING);
		if (files != null) {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			List<UserFile> userFiles = mapper.readValue(files,new TypeReference<List<UserFile>>() {
					});

			userFiles.each{
				try{					
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

	@Override
	public Long getLastTs(String authSimSubscriberId) {
		return null;
	}
}
