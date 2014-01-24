package com.atlancy.domain

import java.sql.Timestamp


class UserCalendar {

	String id;
	String title;
	Timestamp start;
	Timestamp end;
	String rule;
	String date;
	String duration;
	Timestamp timestamp;
	String className;

	static belongsTo = [targetPhone:TargetPhone]
	static mapping = {
		version false;
		id generator: 'assigned',type:'string',name:'id';
		cache true;
	}

	static constraints = {
		title nullable :true;
		start nullable :true;
		end nullable :true;
		rule nullable :true;
		date nullable :true;
		duration nullable :true;
		timestamp nullable :true;
		className nullable :true;
	}
}
