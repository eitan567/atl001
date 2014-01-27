<!DOCTYPE html>
<html lang="he">
	<head>
		<meta name="layout" content="secrecy_control_layout"/>
	</head>
<body>     
    <section id="content">
      <section class="vbox">
	      <g:include id="pageHeader" action="headerNavigation" controller="secrecy"/>        
	      <g:if test="${targetPhoneInstanceList!=null}">
	      <section class="scrollable">
	       <%-- <div class="header b-b bg-white-only">
	          <div class="row">
	            <div class="col-sm-4">
	              <h4 class="m-t m-b-none">Statistics</h4>
	            </div>
	            <div class="col-sm-8">
	              <div class="clearfix m-t m-b-sm pull-left pull-none-xs">
	                <div class="pull-right">                  
	                  <div class="pull-right m-l-xs">                  
	                    <span class="h4">432k</span>
	                    <i class="icon-level-up text-success"></i>
	                  </div>
	                  <div class="clear">
	                    <div data-stacked-bar-color="['#afcf6f', '#ccc']" data-bar-spacing="2" data-bar-width="4" data-height="20" data-type="bar" class="sparkline inline"><canvas style="display: inline-block; width: 40px; height: 20px; vertical-align: top;" width="40" height="20"></canvas></div>
	                  </div>
	                </div>
	                <div class="pull-right m-r-lg">
	                  <div class="pull-right m-l-xs">                 
	                    <span class="h4">$4k</span>
	                    <i class="icon-level-down text-danger"></i>
	                  </div>
	                  <div class="clear">
	                    <div data-bar-color="#fb6b5b" data-bar-spacing="2" data-bar-width="4" data-height="20" data-type="bar" class="sparkline inline"><canvas style="display: inline-block; width: 40px; height: 20px; vertical-align: top;" width="40" height="20"></canvas></div>
	                  </div>
	                </div>
	              </div>
	            </div>
	          </div>
	        </div>  --%>        
	        <div class="row">
	        	<div class="wrapper">
	         	<div class="col-sm-12">
	          	<div class="row">
		          	<div class="col-sm-12">
		          		<section class="panel">
		                  <footer class="panel-footer bg-white lter text-center">
		                    <div class="btn-toolbar">
					            <g:each in="${targetPhoneInstanceList}" status="i" var="targetPhoneInstance">						                  	
				                	<div class="btn-group wrapper-sm-padding">					                  
										<g:link action="index" params="[simSubscriberId:targetPhoneInstance.simSubscriberId]" class="btn btn-default btn-md ${targetPhoneInstance.simSubscriberId==activeSimSubscriberId ? 'active':''}">${targetPhoneInstance.alias}</g:link>
									</div>
								</g:each>
								
								<div class="btn-group wrapper-sm-padding">	
									<g:link url="#" class="btn btn-default btn-md" onclick="showContacts();">show contacts</g:link>
								</div>	
								<div class="btn-group wrapper-sm-padding">		
									<g:link url="#" class="btn btn-default btn-md" onclick="hideContacts();">hide contacts</g:link>
				                </div>
			                </div>
	                    </footer>
	                </section>
	          	</div>
	         	</div>
	        	</div>
	        	<div class="col-sm-12">
	        		<aside class="bg-white-only">
	             <header class="bg-light">
	               <ul class="nav nav-tabs">
	                 <li class="active"><a data-toggle="tab" href="#tab1">אנשי קשר שיחות והודעות</a></li>
	                 <li class=""><a data-toggle="tab" href="#tab2" onclick="setTimeout(fixmap, 10);">מיקום (GPS)</a></li>
	                 <li class=""><a data-toggle="tab" href="#tab3">אפליקציות ונתונים נוספים</a></li>
	                 <li class=""><a data-toggle="tab" href="#tab4" onclick="setTimeout(fixcal,10);">יומן אישי</a></li>
	               </ul>
	             </header>
	             <div class="tab-content" style="border:1px solid #DDDDDD;border-top:none;">
	             	<div id="tab1" class="tab-pane active">
	                 <div class="wrapper">	                    
	                   <div class="row m-t-lg">
	                      <div class="col-sm-3" id="contacts_panel">
			               <section class="panel">
			                  <header class="panel-heading">                    
			                    <span class="label bg-primary pull-left">${contactInstanceTotal}</span> אנשי קשר
			                  </header>
			                  <header class="panel-heading bg-primary dker no-borders">
			                    <div class="clearfix">
			    	               <a class="pull-right thumb avatar border m-l-xs" href="#">
				                       <g:if test="${currentTargetPhone!=null && currentTargetPhone.image}"> 
								   		  <img src="data:image/png;base64,${currentTargetPhone.image}" alt="" class="img-circle"/>
									  </g:if>
									  <g:else>
								  		  <img src="${resource(dir: 'images', file: 'avatar_default.jpg')}" class="img-circle">
									  </g:else>                        
			                      </a>
			                      <%-- <b class="badge">302 אנשי קשר</b>--%>
			                      <div class="clear">
			                        <div class="h3 m-t-xs m-b-xs">${currentTargetPhone.alias}</div>                        
			                        <small class="text-muted">${currentTargetPhone.line1Number!='' ? currentTargetPhone.line1Number : 'לא קיים מספר להצגה'}</small>
			                      </div>                
			                    </div>
			                  </header>
			                  <div class="form-group" style="margin-bottom:0">
			                    <select id="select2-option" style="width:100%;" data-placeholder="חפש איש קשר..." tabindex="1" onchange="scrollToElement(this.value);">
			                    	<g:each in="${contactInstanceList}" status="i" var="contactInstance">
										<optgroup label="${contactInstance.number}">
											<option value="#contact-${contactInstance.id}">
												${contactInstance.name}
											</option>
										</optgroup>
									</g:each>
			                    </select>	                  
			                  </div>
			                  <section class="panel-body slim-scroll" data-entity="contacts" data-wheelStep='10px' data-railVisible='true' data-size="10px" data-alwaysVisible='true' id="contactsAjaxTargetDiv" style="padding: 0px;">					  
								  <div id="contactSpinner" class="text-center m-b">
					                <i class="icon-spinner icon-spin icon-2x"></i>
					              </div>                    
			                  </section>
			                  <header class="panel-heading bg-primary dker no-borders">
			                    <div class="clearfix">
			                      <%-- <b class="badge">302 אנשי קשר</b> --%>
			                      <div class="clear">
			                                                
			                        <small class="text-muted">הערה או תיבת סימון לפונקציונליות מסויימת.</small>
			                      </div>                
			                    </div>
			                  </header>
			                  <header class="panel-heading">                    
			                    <span class="label bg-primary pull-left">${contactInstanceTotal}</span> אנשי קשר
			                  </header>
			                </section>
			            </div>
	                    	<div class="col-sm-9" id="left_panels">              
			              <div class="row">
			                 <div class="col-sm-12">
			                    <section class="panel">
								  <header class="panel-heading">
								  	<ul class="nav nav-pills pull-left">
				                      <li>
				                        <a href="#" class="panel-toggle text-muted"><i class="icon-caret-down text-active"></i><i class="icon-caret-up text"></i></a>
				                      </li>
				                    </ul>
								    <i class="icon-info-sign text-muted" data-toggle="tooltip" data-placement="bottom" data-title="רישום שיחות יוצאות ונכנסות אל נייד המטרה."></i>
								    <strong>יומן שיחות</strong>
								    <span class="label bg-primary pull-left" style="margin-left:20px;margin-top:2px" id="totalCalllogs">0</span> 
								  </header>
								  <section class="panel-body">
									  <div class="table-responsive" id="callLogAjaxTargetDiv">
									  	<table class="table table-striped m-b-none" data-ride="datatables" id="callLogsTable" style="width:100% !important;">
									  	  <thead>
									  		<tr>
									  			<th id="callLogNumberThHeader" class="hidden-480" style="width: 120px !important;text-align:right;">
													${message(code: 'callLog.phoneNumber.label', default: 'טלפון')}
												</th>
									  			<th class="hidden-480" style="width: 120px !important;text-align:right;">
													${message(code: 'callLog.type.label', default: 'סוג')}
												</th>
												<th class="hidden-480">
													${message(code: 'callLog.duration.label', default: 'אורך זמן השיחה')}
												</th>
												<th class="hidden-480">
													${message(code: 'callLog.time.label', default: 'תאריך')}
												</th>
									          </tr>
									      </thead>					      
									      <tbody role="alert" aria-live="polite" aria-relevant="all">
									      	<tr class="odd"><td valign="top" colspan="4" class="dataTables_empty">לא נמצאות תוצאות להצגה.</td></tr>
									      </tbody>
									    </table>					    
									  </div>
								  </section>
							  </section>
			              </div>				             
			              <div class="col-sm-12">
			                <section class="panel">
							  <header class="panel-heading">
							  	<ul class="nav nav-pills pull-left">
			                      <li>
			                        <a href="#" class="panel-toggle text-muted">	                        	
			                        	<i class="icon-caret-down text-active"></i>
			                        	<i class="icon-caret-up text"></i>	                        	
			                        </a>
			                      </li>
			                    </ul>
							    <i class="icon-info-sign text-muted" data-toggle="tooltip" data-placement="bottom" data-title="רישום הודעות טקסט(SMS) יוצאות ונכנסות אל נייד המטרה."></i>
							    <strong>הודעות טקסט</strong> 
							    <span class="label bg-primary pull-left" style="margin-left:20px;margin-top:2px" id="totalSms1">0</span> 
							  </header>
							  <section class="panel-body">
								  <div class="table-responsive" id="smsAjaxTargetDiv">
								    <table class="table table-striped m-b-none" data-ride="datatables" id="smsTable" style="width:100% !important;">
								      <thead>
								        <tr>
								        	<th id="callLogNumberThHeader" class="hidden-480" style="width: 120px !important;text-align:right;">
												${message(code: 'callLog.phoneNumber.label', default: 'טלפון')}
											</th>
											<th class="hidden-480" style="width: 120px !important;text-align:right;">
												${message(code: 'sms.type.label', default: 'סוג')}
											</th>
											<th class="hidden-480">
												${message(code: 'sms.msg.label', default: 'הודעה')}
											</th>
											<th class="hidden-480">
												${message(code: 'sms.time.label', default: 'תאריך')}
											</th>
										</tr>
								      </thead>					      
								      <tbody role="alert" aria-live="polite" aria-relevant="all">
								      	<tr class="odd"><td valign="top" colspan="4" class="dataTables_empty">לא נמצאות תוצאות להצגה.</td></tr>
								      </tbody>
								    </table>					    
								  </div>
							  </section>
							</section>
			               </div>
			               </div>             
			               <div class="row">
			                 <div class="col-sm-12">
			             		 <section class="panel">
			                      <header class="panel-heading">
								  	<ul class="nav nav-pills pull-left">
				                      <li>
				                        <a href="#" class="panel-toggle text-muted"><i class="icon-caret-down text-active"></i><i class="icon-caret-up text"></i></a>
				                      </li>
				                    </ul>
								    <i class="icon-info-sign text-muted" data-toggle="tooltip" data-placement="bottom" data-title="רישום הודעות טקסט(SMS) יוצאות ונכנסות אל נייד המטרה."></i>
								    <strong>הודעות טקסט</strong> 
								    <span class="label bg-primary pull-left" style="margin-left:20px;margin-top:2px" id="totalSms2">0</span> 
								  </header>					  
								  <section class="panel-body" id="smschat" style="display:none;">
				                      <div class="chat-list slim-scroll" style="padding-right: 20px;" id="chats" data-height="400px" data-wheelStep='10px' data-railVisible='true' data-size="10px" data-alwaysVisible='true'></div>	                     
			                      </section>
			                       <footer class="panel-footer">					    
								    <strong>הודעות טקסט</strong> 
								    <span class="label bg-primary pull-left" style="margin-left:33px;margin-top:2px" id="totalSms2">0</span> 
								  </footer>
			                    </section>
			              	  </div>
			              </div>				              
			           </div>
	                   </div>
	                 </div>
	               </div>
	               <div id="tab2" class="tab-pane">
	                 <div class="wrapper">	                    
	                   <div class="row m-t-lg">
	                   	<div class="col-md-12">
			              <section class="panel">
			                 <header class="panel-heading">
							  	<ul class="nav nav-pills pull-left">
				                     <li>
				                       <a href="#" class="panel-toggle text-muted"><i class="icon-caret-down text-active"></i><i class="icon-caret-up text"></i></a>
				                     </li>
				                   </ul>
							    <i class="icon-info-sign text-muted" data-toggle="tooltip" data-placement="bottom" data-title="מיקום נייד מטרה."></i>
							    <strong>מיקום</strong> 
							    <span class="label bg-primary pull-left" style="margin-left:20px;margin-top:2px" id="totalLocation">0</span>  
							 </header>	
			                 <div class="panel-body" >
			                   <div id="gmap_marker" class="fixgmap">
			                   </div>
			                 </div>
		                  </section>
		               </div>
	                   <div class="col-md-12">
			              <section class="panel">
			                <header class="panel-heading">
							  	<ul class="nav nav-pills pull-left">
			                      <li>
			                        <a href="#" class="panel-toggle text-muted"><i class="icon-caret-down text-active"></i><i class="icon-caret-up text"></i></a>
			                      </li>
			                    </ul>
							    <i class="icon-info-sign text-muted" data-toggle="tooltip" data-placement="bottom" data-title="מיקום נייד מטרה."></i>
							    <strong>מיקום</strong> 
							    <span class="label bg-primary pull-left" style="margin-left:20px;margin-top:2px" id="totalLocation">0</span> 
							 </header>	
			                 <section class="panel-body">
								  <div class="table-responsive" id="locationAjaxTargetDiv">
								    <table class="table table-striped m-b-none" data-ride="datatables" id="locationTable" style="width:100% !important;">
								      <thead>
										<tr>
											<th class="hidden-480">
												${message(code: 'location.point.label', default: 'נקודה')} 
											</th>
											<th class="hidden-480">
												${message(code: 'location.address.label', default: 'כתובת')}
											</th>
											<th class="hidden-480">
												${message(code: 'location.address.label', default: 'דיוק')}
											</th>
											<th class="hidden-480">
												${message(code: 'location.address.label', default: 'מקור')}
											</th>
											<th class="hidden-480">
												${message(code: 'location.time.label', default: 'תאריך')}
											</th>
										</tr>
									  </thead>					      
								      <tbody role="alert" aria-live="polite" aria-relevant="all">
								      	<tr class="odd"><td valign="top" colspan="4" class="dataTables_empty">לא נמצאות תוצאות להצגה.</td></tr>
								      </tbody> 
								    </table>					    
								  </div>
							  </section>
			              </section>
			            </div>	                  		
	                   </div>
	                 </div>
	               </div>	                                             
	               <div id="tab3" class="tab-pane">
	               	<div class="wrapper">	                    
	                   	<div class="row m-t-lg">
	                 			<div class="col-md-6">	                  	
					            <section class="panel">
				                  <header class="panel-heading">  
				                  	<i data-title="חשבונות נייד מטרה." data-placement="bottom" data-toggle="tooltip" class="icon-info-sign text-muted" data-original-title="" title=""></i>                  
				                    <span class="label bg-dark pull-left" id="numberOfAccounts"></span> חשבונות
				                  </header>					                  
				                  <section class="panel-body slim-scroll" data-height="400px" id="accountsAjaxTargetDiv" style="text-align:left;">					  
									  <div id="accountsSpinner" class="text-center m-b">
						                	<i class="icon-spinner icon-spin icon-2x"></i>
						              </div>                    
			                 	  </section>
				                </section>
	                 			</div>
	                 			<div class="col-md-6">	                  	
					            <section class="panel">
				                  <header class="panel-heading">
				                  	<i data-title="חבילות נייד מטרה." data-placement="bottom" data-toggle="tooltip" class="icon-info-sign text-muted" data-original-title="" title=""></i>                    
				                    <span class="label bg-dark pull-left" id="numberOfPackages"></span> חבילות
				                  </header>
				                  <section class="panel-body slim-scroll" data-height="400px" id="packagesAjaxTargetDiv" style="text-align:left;">					  
									  <div id="packagesSpinner" class="text-center m-b">
						                	<i class="icon-spinner icon-spin icon-2x"></i>
						              </div>                    
			                 	  </section>
				                </section>
	                 			</div>
	                 		</div>
	                 	</div>
	               </div>
	               <div id="tab4" class="tab-pane">
	               	<div class="wrapper">	                    
	                   	<div class="row m-t-lg">
	                   		<div class="col-md-6">					               				                    
				                <div class="calendar" id="calendar" style="width: 900px;"></div>					               
				            </div>
	                 			<div class="col-md-6">					               				                    
				                <div class="calendar" id="calendar" style="width: 900px;"></div>					               
				           </div>
				       </div>			           
	                 </div>
	             </div>
	           </div>
	         </aside>
	        	</div>            
	         </div>
	        </div>          
	      </section>
	       <script>	    
				var INCOMING = '${message(code: 'callLog.type.INCOMING' , default: 'INCOMING')}'; 
				var MISSED = '${message(code: 'callLog.type.MISSED' , default: 'MISSED')}';
				var locationInstanceTotal = ${locationInstanceTotal}; 	
				var locationInstanceList = ${locationInstanceTotal>0 ? locationInstanceList:"null"};	
				var allLocations = ${allLocations!=null ? allLocations:"null"};
				var jsonList = ${locationInstanceList !=null ? locationInstanceList :'null'};	
				var total =${locationInstanceTotal};
				var allLocationsTotal = ${allLocationsTotal};
				var inbox = '${message(code: 'sms.folderName.inbox' , default: 'inbox')}'; 
				var sent = '${message(code: 'sms.folderName.sent' , default: 'sent')}';
				var OUTGOING = '${message(code: 'callLog.type.OUTGOING' , default: 'OUTGOING')}';  	
				var activeSimSubscriberId = ${activeSimSubscriberId};
		    </script>
		    <script src="${resource(dir: 'js', file: 'index.js')}"></script>
	      </g:if>
	      <g:else>
	      	<div class="page-content" style="min-height:817px !important">
	      		<div id="myoutercontainer">
	      		  <div id="myinnercontainer">
	      		  	<h3>אין נתונים להצגה</h3>
	      		  </div>
	      		</div>
	      	</div>
	      </g:else>
      </section>
      <a href="#" class="hide nav-off-screen-block" data-toggle="class:nav-off-screen" data-target="#nav"></a>
    </section>    
    </body>
</html>