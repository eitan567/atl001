package com.atlancy.secrecy.services.helpers

import org.codehaus.jackson.map.DeserializationConfig
import org.codehaus.jackson.map.ObjectMapper
import org.codehaus.jackson.type.TypeReference

import com.atlancy.domain.FileData;
import com.atlancy.domain.TargetPhone;
import com.xaviar.market.rest.item.DataHolder
import com.xaviar.market.rest.item.DataHolderConstants

class PhotoAlbumService {

	def addPictures(DataHolder dataHolder, TargetPhone targetPhone) {
		String clientPics = dataHolder.get(DataHolderConstants.PHOTO_ALBUM);
		if (clientPics != null) {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			List<FileData> clientPictures = mapper.readValue(clientPics,new TypeReference<List<FileData>>() {
					});
			clientPictures.each{
				it.save(flush:true, failOnError:true,insert:true);
			}
		}
	}
}
