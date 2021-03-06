<!DOCTYPE html>
<html lang="he">
	<head>
	  <meta charset="utf-8" />
	  <title>Web Application | סיקרסי</title>
	  <meta name="description" content="app, web app, responsive, admin dashboard, admin, flat, flat ui, ui kit, off screen nav" />
	  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" /> 
	  <link rel="stylesheet" href="${resource(dir: 'css', file: 'bootstrap.css')}" type="text/css"/>
	  <link rel="stylesheet" href="${resource(dir: 'css', file: 'animate.css')}" type="text/css"/>
	  <link rel="stylesheet" href="${resource(dir: 'css', file: 'font-awesome.min.css')}" type="text/css"/>
	  <link rel="stylesheet" href="${resource(dir: 'css', file: 'font.css')}" type="text/css" cache="false"/>
	  <link rel="stylesheet" href="${resource(dir: 'css', file: 'plugin.css')}" type="text/css"/>
	  <link rel="stylesheet" href="${resource(dir: 'js/fuelux', file: 'fuelux.css')}" type="text/css"/>
	  <link rel="stylesheet" href="${resource(dir: 'css', file: 'app.css')}" type="text/css"/>
	  <link rel="stylesheet" href="${resource(dir: 'js/datatables', file: 'datatables.css')}" type="text/css"/>
	  <!--[if lt IE 9]>
	    <script src="${resource(dir:'js/ie', file: 'respond.min.js')}" type="text/javascript" cache="false"></script>
	    <script src="${resource(dir:'js/ie', file: 'html5.js')}" type="text/javascript" cache="false"></script>
	    <script src="${resource(dir:'js/ie', file: 'fix.js')}" type="text/javascript" cache="false"></script>
	  <![endif]-->
	  
	  <g:layoutHead/>
	  <r:layoutResources />
		
	</head>
	<body>
	  <section class="hbox stretch">
	    <!-- .aside -->
	    <aside class="bg-dark aside-sm nav-vertical" id="nav">
	      <section class="vbox">
	        <header class="dker nav-bar">
	          <a class="btn btn-link visible-xs" data-toggle="class:nav-off-screen" data-target="#nav">
	            <i class="icon-reorder"></i>
	          </a>
	          <a href="#" class="nav-brand" data-toggle="fullscreen">Secrecy</a>
	          <a class="btn btn-link visible-xs" data-toggle="class:show" data-target=".nav-user">
	            <i class="icon-comment-alt"></i>
	          </a>
	        </header>
	        <section>
	          <div class="lter nav-user hidden-xs pos-rlt">            
	            <div class="nav-avatar pos-rlt">
	              <a href="#" class="thumb-sm avatar animated rollIn" data-toggle="dropdown">
	                 <img src="${resource(dir: 'images', file: 'avatar.jpg')}" alt="" class="">
	                <span class="caret caret-white"></span>
	              </a>
	              <ul class="dropdown-menu m-t-sm animated fadeInRight">
	              	<span class="arrow top"></span>
	                <li>
	                  <a href="#">הגדרות</a>
	                </li>
	                <li>
	                  <g:link action="profile" controller="spyBoy">הפרופיל שלי</g:link>
	                </li>
	                <li>
	                  <a href="#">
	                    <span class="badge bg-danger pull-left">3</span>
	                    התראות
	                  </a>
	                </li>
	                <li class="divider"></li>
	                <li>
	                  <a href="docs.html">עזרה</a>
	                </li>
	                <li>
	                  <g:link action="signOut" controller="auth">התנתק</g:link>
	                </li>
	              </ul>
	              <div class="visible-xs m-t m-b">
	                <a href="#" class="h3">${user.firstName + " " + user.lastName}</a>
	                <p><i class="icon-map-marker"></i>${user.address}</p>
	              </div>
	            </div>
	            <div class="nav-msg">
	              <a href="#" class="dropdown-toggle" data-toggle="dropdown">
	                <b class="badge badge-white count-n">2</b>
	              </a>
	              <section class="dropdown-menu m-r-sm pull-right animated fadeInLeft">
	                <div class="arrow right"></div>
	                <section class="panel bg-white">
	                  <header class="panel-heading">
	                    <strong>יש לך <span class="count-n">2</span> התראות</strong>
	                  </header>
	                  <div class="list-group">
	                    <a href="#" class="media list-group-item">
	                      <span class="pull-right thumb-sm">
	                         <img src="${resource(dir: 'images', file: 'avatar.jpg')}" alt="John said" class="img-circle">
	                      </span>
	                      <span class="media-body block m-b-none">
	                        Use awesome animate.css<br>
	                        <small class="text-muted">28 Aug 13</small>
	                      </span>
	                    </a>
	                    <a href="#" class="media list-group-item">
	                      <span class="media-body block m-b-none">
	                        1.0 initial released<br>
	                        <small class="text-muted">27 Aug 13</small>
	                      </span>
	                    </a>
	                  </div>
	                  <footer class="panel-footer text-sm">
	                    <a href="#" class="pull-left"><i class="icon-cog"></i></a>
	                    <a href="#">הצג את כל ההתראות</a>
	                  </footer>
	                </section>
	              </section>
	            </div>
	          </div>
	          <nav class="nav-primary hidden-xs">
	            <ul class="nav">
	              <li>
	                <a href="index.html">
	                  <i class="icon-eye-open"></i>
	                  <span>שולחן עבודה</span>
	                </a>
	              </li>              
	              <li class="dropdown-submenu active">
	                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
	                  <i class="icon-beaker"></i>
	                  <span>ניתוח נותנים</span>
	                </a>
	                <ul class="dropdown-menu">                
	                  <li>
	                    <a href="buttons.html">Buttons</a>
	                  </li>
	                  <li>
	                    <a href="icons.html">
	                      <b class="badge pull-left">302</b>Icons
	                    </a>
	                  </li>
	                  <li>
	                    <a href="grid.html">Grid</a>
	                  </li>
	                  <li>
	                    <a href="widgets.html">
	                      <b class="badge bg-primary pull-left">8</b>Widgets
	                    </a>
	                  </li>
	                  <li>
	                    <a href="components.html">
	                      <b class="badge pull-left">18</b>Components
	                    </a>
	                  </li>
	                  <li>
	                    <a href="list.html">List groups</a>
	                  </li>
	                  <li>
	                    <a href="table.html">Table</a>
	                  </li>
	                  <li>
	                    <a href="form.html">Form</a>
	                  </li>
	                  <li>
	                    <a href="chart.html">Chart</a>
	                  </li>
	                  <li>
	                    <a href="calendar.html">Fullcalendar</a>
	                  </li>
	                  <li>
	                    <a href="portlet.html">Portlet</a>
	                  </li>
	                </ul>
	              </li>
	               <li class="dropdown-submenu">
	                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
	                  <i class="icon-file-text"></i>
	                  <span>דוחות</span>
	                </a>
	                <ul class="dropdown-menu">                
	                  <li>
	                    <a href="dashboard.html">שולחן עבודה</a>                    
	                  </li>
	                  <li>
	                    <a href="dashboard-1.html">Dashboard one</a>              
	                  </li>
	                  <li>
	                    <a href="dashboard-2.html">Dashboard layout</a>
	                  </li>
	                  <li>
	                    <a href="analysis.html">Analysis</a>
	                  </li>
	                  <li>
	                    <a href="master.html">Master</a>
	                  </li>
	                  <li>
	                    <a href="maps.html">Maps</a>
	                  </li>
	                  <li>
	                    <a href="gallery.html">Gallery</a>              
	                  </li>
	                  <li>
	                    <a href="profile.html">Profile</a>
	                  </li>
	                  <li>
	                    <a href="blog.html">Blog</a>              
	                  </li>
	                  <li>
	                    <a href="invoice.html">Invoice</a>              
	                  </li>                  
	                  <li>
	                    <a href="signin.html">Signin page</a>
	                  </li>
	                  <li>
	                    <a href="signup.html">Signup page</a>
	                  </li>
	                  <li>
	                    <a href="404.html">404 page</a>
	                  </li>
	                </ul>
	              </li>
	              <li>
	                <a href="mail.html">
	                  <b class="badge bg-danger pull-left">3</b>
	                  <i class="icon-envelope-alt"></i>
	                  <span>דואר</span>
	                </a>
	              </li>
	              <li>
	                <a href="tasks.html">
	                  <i class="icon-tasks"></i>
	                  <span>משימות</span>
	                </a>
	              </li>
	              <li>
	                <a href="notes.html">
	                  <i class="icon-pencil"></i>
	                  <span>הערות אישיות</span>
	                </a>
	              </li>
	              <li>
	                <a href="timeline.html">
	                  <i class="icon-time"></i>
	                  <span>קו זמן</span>
	                </a>
	              </li>
	            </ul>
	          </nav>
	        </section>
	        <footer class="footer bg-gradient hidden-xs">
	          <g:link action="lockme" controller="secrecy" data-toggle="ajaxModal" class="btn btn-sm btn-link m-l-n-xs pull-left"><i class="icon-off"></i></g:link>	          
	          <a href="#nav" data-toggle="class:nav-vertical" class="btn btn-sm btn-link m-r-n-sm">
	            <i class="icon-reorder"></i>
	          </a>
	        </footer>
	      </section>
	    </aside>
        <section id="content">          
           	<g:layoutBody/>
        </section>       
 	  </section>
    
	  <g:javascript library="application"/>			
	  <r:layoutResources/>
	  
		<script src="${resource(dir: 'js', file: 'jquery.min.js')}"></script>
	  <!-- Bootstrap -->
	  <script src="${resource(dir: 'js', file: 'bootstrap.js')}"></script>
	  <!-- Sparkline Chart -->
	  <script src="${resource(dir: 'js/charts/sparkline', file: 'jquery.sparkline.min.js')}"></script>
	  <!-- App -->
	  <script src="${resource(dir: 'js', file: 'app.js')}"></script>
	  <script src="${resource(dir: 'js', file: 'app.plugin.js')}"></script>
	  <script src="${resource(dir: 'js', file: 'app.data.js')}"></script>
	  <script src="${resource(dir: 'js/libs', file: 'jquery.pjax.js')}" type="text/javascript" cache="false"></script>
	  <!-- Sparkline Chart -->
	  <script src="${resource(dir: 'js/charts/sparkline', file: 'jquery.sparkline.min.js')}"></script>
	  <!-- Easy Pie Chart -->
	  <script src="${resource(dir: 'js/charts/easypiechart', file: 'jquery.easy-pie-chart.js')}"></script>
	  <!-- Morris -->
	  <!--<script src="${resource(dir: 'js/charts/morris', file: 'raphael-min.js')}" type="text/javascript" cache="false"></script>
	  <script src="${resource(dir: 'js/charts/morris', file: 'morris.min.js')}" type="text/javascript" cache="false"></script> -->
	  <!-- datatables -->
	  <script src="${resource(dir: 'js/datatables', file: 'jquery.dataTables.min.js')}"></script>
	  <!-- fuelux -->
	  <script src="${resource(dir: 'js/fuelux', file: 'fuelux.js')}"></script>
	  <script src="${resource(dir: 'js/libs', file: 'underscore-min.js')}"></script>
	</body>
</html>