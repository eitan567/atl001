<header class="header bg-primary navbar">
  <div class="navbar-collapse collapse">
    	 <ul class="nav navbar-nav navbar-right">
          <li class="dropdown">
            <a data-toggle="dropdown" class="dropdown-toggle" href="#">
              <i class="icon-beaker  text-white"></i>
              <span class="text-white">UI kit</span> <b class="caret"></b>
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
          <li class="dropdown">
            <a data-toggle="dropdown" class="dropdown-toggle" href="#">
              <i class="icon-file-text text-white"></i>
              <span class="text-white">Pages</span> <b class="caret"></b>
            </a>
            <ul class="dropdown-menu">                
              <li>
                <a href="dashboard.html">Dashboard</a>                    
              </li>
              <li>
                <a href="dashboard-1.html">Dashboard one</a>              
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
       </ul>
       <form role="search" class="navbar-form navbar-left m-t-sm">
         <div class="form-group">
           <div class="input-group input-s">
             <input type="text" placeholder="Search" class="form-control input-sm no-border dk text-white">
             <span class="input-group-btn">
               <button class="btn btn-sm btn-success btn-icon" type="submit"><i class="icon-search"></i></button>
             </span>
           </div>
         </div>
       </form>
       <ul class="nav navbar-nav">
         <li class="hidden-xs">
           <a data-toggle="dropdown" class="dropdown-toggle" href="#">
             <i class="icon-bell-alt text-white"></i>
             <span class="badge up bg-danger m-l-n-sm">2</span>
           </a>
           <section class="dropdown-menu animated fadeInUp input-s-lg">
             <section class="panel bg-white">
               <header class="panel-heading">
                 <strong>You have <span class="count-n">2</span> notifications</strong>
               </header>
               <div class="list-group">
                 <a class="media list-group-item" href="#">
                   <span class="pull-left thumb-sm">
                     <img src="${resource(dir: 'images', file: 'avatar.jpg')}" alt="John said" class="img-circle">
                   </span>
                   <span class="media-body block m-b-none">
                     Use awesome animate.css<br>
                     <small class="text-muted">28 Aug 13</small>
                   </span>
                 </a>
                 <a class="media list-group-item" href="#">
                   <span class="media-body block m-b-none">
                     1.0 initial released<br>
                     <small class="text-muted">27 Aug 13</small>
                   </span>
                 </a>
               </div>
               <footer class="panel-footer text-sm">
                 <a class="pull-right" href="#"><i class="icon-cog"></i></a>
                 <a href="#">See all the notifications</a>
               </footer>
             </section>
           </section>
         </li>
         <li class="dropdown">
           <a data-toggle="dropdown" class="dropdown-toggle aside-sm" href="#">
             <span class="thumb-sm avatar pull-left m-t-n-xs m-r-xs">
               <img src="${resource(dir: 'images', file: 'avatar.jpg')}">
             </span>
             John.Smith <b class="caret"></b>
           </a>
           <ul class="dropdown-menu m-t-sm animated fadeInRight">
              	<span class="arrow top"></span>
                <li>
                  <a href="#">הגדרות</a>
                </li>
                <li>
                  <g:link action="profile" controller="secrecy">הפרופיל שלי</g:link>
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
         </li>
       </ul>
     </div>
</header>