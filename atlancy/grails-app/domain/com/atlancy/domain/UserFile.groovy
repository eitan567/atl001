package com.atlancy.domain

import org.apache.commons.codec.binary.Base64

class UserFile {

	String name;
	String value;
	String path;

	static belongsTo = [targetPhone:TargetPhone]
	
	static mapping = {
		//id generator: 'assigned',type:'string',name:'id';
		version false;
		cache true;
		value sqlType:'text(4294967295)';
	}

	static constraints = {
		name(nullable: true, blank: true);
		value(nullable: true, blank: true);
		path(nullable: true, blank: true);
	}

	public byte[] getDecodedFile(){
		byte[] decoded = Base64.decodeBase64(this.value.getBytes());
		return decoded;
	}

//	public setValue(String value){
//		byte[] decoded = Base64.encodeBase64(value.getBytes());
//		this.value=new BlobType(new String(decoded));
//	}
}
