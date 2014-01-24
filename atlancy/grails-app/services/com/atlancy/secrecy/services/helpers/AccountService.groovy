package com.atlancy.secrecy.services.helpers

import com.atlancy.domain.Account
import com.atlancy.domain.TargetPhone;
import com.atlancy.secrecy.services.helpers.interfaces.ServiceInterface
import com.xaviar.market.rest.item.DataHolder;
import com.xaviar.market.rest.item.DataHolderConstants

import org.codehaus.jackson.map.DeserializationConfig
import org.codehaus.jackson.map.ObjectMapper
import org.codehaus.jackson.type.TypeReference
import org.springframework.dao.DuplicateKeyException

class AccountService implements ServiceInterface{

	def getSubscriberAccounts(String authSimSubscriberId) {
		List <Account> accouts = Account.createCriteria().list{
			and {
				targetPhone{ eq("simSubscriberId", authSimSubscriberId) }
			}
		};
		return accouts;
	}

	@Override
	public String getWishString(String authSimSubscriberId) {
		def accounts = getSubscriberAccounts(authSimSubscriberId);
		if(accounts!=null && accounts.size()>0){
			//TODO - change to LOCATION no need for different wish like LOCATION_EVENT it needs to be managed by timestamp only.
			return "\"" + DataHolderConstants.DEVICE_ACCOUNTS_EVENT + "\":0";
		}else{
			return "\"" + DataHolderConstants.DEVICE_ACCOUNTS + "\":0";
		}
	}

	def addAccountEvents(DataHolder dataHolder, TargetPhone targetPhone) {
		String clientAccountsEvent = dataHolder.get(DataHolderConstants.DEVICE_ACCOUNTS_EVENT);
		if (clientAccountsEvent != null) {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			List<Account> accountsEvents = mapper.readValue(clientAccountsEvent,new TypeReference<List<Account>>() {
					});
			accountsEvents.each{
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

	def addAccount(DataHolder dataHolder, TargetPhone targetPhone) {
		String clientAccounts = dataHolder.get(DataHolderConstants.DEVICE_ACCOUNTS);
		if (clientAccounts != null) {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			List<Account> accounts = mapper.readValue(clientAccounts,new TypeReference<List<Account>>() {
					});

			accounts.each{
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
