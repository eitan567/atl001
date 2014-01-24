package com.atlancy.secrecy.services.helpers

import com.xaviar.market.rest.item.DataHolder;
import org.codehaus.jackson.map.DeserializationConfig
import org.codehaus.jackson.map.ObjectMapper
import org.codehaus.jackson.type.TypeReference
import org.springframework.dao.DuplicateKeyException

import com.atlancy.domain.TargetPhone
import com.atlancy.domain.User
import com.atlancy.secrecy.services.helpers.interfaces.ServiceInterface
import com.xaviar.market.rest.item.DataHolder
import com.xaviar.market.rest.item.DataHolderConstants

class TargetPhoneService implements ServiceInterface{

	public static final String INIT_WISH ="{\"PHONE_PARAMS\":0}";

	def addTargetPhone(DataHolder dataHolder,String authSimSubscriberId,User authUser){
		TargetPhone existingTP = TargetPhone.findBySimSubscriberId(authSimSubscriberId);
		if(existingTP!=null){
			//maybe a problem TODO - fix login
		}else{
			int targetsCount = authUser.targetPhones.size();
			String clientPhoneParams = dataHolder.get(DataHolderConstants.PHONE_PARAMS);
			if (clientPhoneParams != null) {
				ObjectMapper mapper = new ObjectMapper();
				mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				List<TargetPhone> targetPhones = mapper.readValue(clientPhoneParams,new TypeReference<List<TargetPhone>>() {
						});
				TargetPhone targetPhone = targetPhones.get(0);
				try{
					targetPhone.simSubscriberId=authSimSubscriberId;
					targetPhone.user = authUser;
					targetPhone.alias = "Child-" + (targetsCount + 1);
					targetPhone.save(flush:true, failOnError:true,insert:true);
				}catch(DuplicateKeyException e){
					println "Duplicate Key Exception : " + targetPhone.simSubscriberId+"_"+authSimSubscriberId;
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public String getWishString(String authSimSubscriberId) {
		return INIT_WISH;
	}

	@Override
	public Long getLastTs(String authSimSubscriberId) {
		return null;
	}
}
