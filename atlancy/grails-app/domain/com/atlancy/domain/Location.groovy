package com.atlancy.domain;

import java.util.Date;

public class Location {

	String id;
	Double latitude;
	Double longitude;
	String address;
	Date time;
	Double accuracyMeters;
	String provider;
	String recordState;
	String recordOrigin;

	static belongsTo = [targetPhone:TargetPhone]
	static mapping = { 
		id generator: 'assigned',type:'string',name:'id';
		version false;
		cache true;
	 }

	static constraints = {
		latitude nullable :true;
		longitude nullable :true;
		time nullable :true;
		address nullable: true;
		accuracyMeters nullable :true;
		provider nullable: true;
		recordState nullable :true;
		recordOrigin nullable :true;
	}
}
