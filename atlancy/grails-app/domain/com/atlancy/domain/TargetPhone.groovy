package com.atlancy.domain

import org.apache.commons.codec.binary.Base64

class TargetPhone  {

	String simSubscriberId;
	String networkOperatorName;
	String simSerialNumber;
	String phoneType;
	String deviceID;
	String deviceSoftwareVersion;
	String networkOperator;
	String callState;
	String networkCountryIso;
	String simOperator;
	String simstate;
	String simOperatorName;
	String simCountryIso;
	String line1Number;
	String cellLocation;
	String alias;
	String image;
	
	static hasMany = [contacts:Contact,callLogs:CallLog,smss:Sms,locations:Location,userCalendars:UserCalendar,packages:Package,accounts:Account,userFiles:UserFile]
	static belongsTo = [user:User]
	
	
    static constraints = {
		contacts nullable :true;
		callLogs nullable :true;
		smss nullable :true;
		locations nullable :true;
		userCalendars nullable :true;
		packages nullable :true;
		accounts nullable :true;
		userFiles nullable :true;
		//user nullable: false;
		
		networkOperatorName nullable :true;
		simSerialNumber nullable :true;
		phoneType nullable :true;
		deviceID nullable :true;
		deviceSoftwareVersion nullable :true;
		networkOperator nullable :true;
		callState nullable :true;
		networkCountryIso nullable :true;
		simOperator nullable :true;
		simstate nullable :true;
		simOperatorName nullable :true;
		simCountryIso nullable :true;
		line1Number nullable :true;
		cellLocation nullable :true;
		alias nullable :true;
		image nullable :true;
    }
	
	static mapping = {
		id name:'simSubscriberId', type:'string', generator: 'assigned';		
		image sqlType:'text(4294967295)';
		version false;
		cache true;
	}
	
	public byte[] getDecodedImage(){
		byte[] decodedImage = Base64.decodeBase64(this.image.getBytes());
		return decodedImage;
	}
}
