package com.atlancy.domain

import org.apache.commons.codec.binary.Base64

class Package {

	String id;
	String appname;
	String icon;
	String pname;
	String versionCode;
	String versionName;

	static belongsTo = [targetPhone:TargetPhone]
	static mapping = {
		version false;
		id generator: 'assigned',type:'string',name:'id';
		icon sqlType:'text(4294967295)';
		cache true;
	}

	static constraints = {
		icon(nullable: true, blank: true);
		pname(nullable: true, blank: true);
		versionCode(nullable: true, blank: true);
		versionName(nullable: true, blank: true);
	}

	public byte[] getDecodedImage(){
		byte[] decodedImage = Base64.decodeBase64(this.icon.getBytes());
		return decodedImage;
	}
}
