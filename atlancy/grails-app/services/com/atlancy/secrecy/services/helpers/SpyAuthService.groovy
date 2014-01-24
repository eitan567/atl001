package com.atlancy.secrecy.services.helpers

import org.apache.commons.codec.binary.Base64
import org.apache.shiro.SecurityUtils
import org.apache.shiro.authc.AuthenticationException
import org.apache.shiro.authc.UsernamePasswordToken
import org.codehaus.jackson.map.ObjectMapper
import org.codehaus.jackson.type.TypeReference

import com.atlancy.domain.User;
import com.org.krams.domain.Flower

class SpyAuthService {

	//get user from client flower
	def getUser(String clientFlower) {
		if(clientFlower!=null && !clientFlower.isEmpty()){
			clientFlower = new String(Base64.decodeBase64(clientFlower.getBytes()));
			ObjectMapper mapper = new ObjectMapper();
			List<Flower> flowers = mapper.readValue(clientFlower,new TypeReference<List<Flower>>(){});

			Flower flower = flowers.get(0);

			String username = flower.user;
			String password = flower.password;

			if(username!=null && !username.isEmpty() && password!=null && !password.isEmpty()){
				User user = User.findByUsername(username);
				return user;
			}
		}

		return null;
	}

	//get SimSubscriberId - targetPhone.id
	def getSimSubscriberId(String clientFlower){
		if(clientFlower!=null && !clientFlower.isEmpty()){
			clientFlower = new String(Base64.decodeBase64(clientFlower.getBytes()));
			ObjectMapper mapper = new ObjectMapper();
			List<Flower> flowers = mapper.readValue(clientFlower,new TypeReference<List<Flower>>(){});

			Flower flower = flowers.get(0);


			if(flower.simSubscriberId!=null && !flower.simSubscriberId.isEmpty()){
				return flower.simSubscriberId;
			}
		}
	}

	//auth user sending data
	def authTargetRequest(String clientFlower) {
		Flower flower = null;
		User user = null;
		if(clientFlower!=null && !clientFlower.isEmpty()){
			ObjectMapper mapper = new ObjectMapper();
			List<Flower> flowers = mapper.readValue(clientFlower,new TypeReference<List<Flower>>(){});

			flower = flowers.get(0);

			String username = flower.user;
			String password = flower.password;

			if(username!=null && !username.isEmpty() && password!=null && !password.isEmpty()){
				user = User.findByUsername(username);
			}
		}

		try{
			if(user!=null){
				def authToken = new UsernamePasswordToken(flower.getUser(), flower.getPassword() as String);
				SecurityUtils.subject.login(authToken);
			}
		}catch (AuthenticationException ex){
			return false;
		}

		return true;
	}


	def isUserAuth(String clientFlower){
		// validate clientFlower data exists
		if(clientFlower!=null && !clientFlower.isEmpty()){
			// decode flower data before use
			clientFlower = new String(Base64.decodeBase64(clientFlower.getBytes()));
			//auth user
			if(authTargetRequest(clientFlower)){ //user is authenticated
				return true;
			}
		}
		return false;
	}
}
