package com.atlancy.domain;

public class Account {

	//String id;
	String accountName;
	String accountType;

	static belongsTo = [targetPhone:TargetPhone]
	static mapping = {
		//id generator: 'assigned',type:'string',name:'id';
		version false;
		cache true;
	}

	static constraints = {
		accountName(nullable: true, blank: true);
		accountType(nullable: true, blank: true);
	}
}
