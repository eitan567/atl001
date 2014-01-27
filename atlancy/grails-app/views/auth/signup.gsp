<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8" />
  <title>Web Application | todo</title>
  <meta name="description" content="app, web app, responsive, admin dashboard, admin, flat, flat ui, ui kit, off screen nav" />
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
  <link rel="stylesheet" href="${resource(dir: 'css', file: 'bootstrap.css')}" type="text/css"/>
  <link rel="stylesheet" href="${resource(dir: 'css', file: 'animate.css')}" type="text/css"/>
  <link rel="stylesheet" href="${resource(dir: 'css', file: 'font-awesome.min.css')}" type="text/css"/>
  <link rel="stylesheet" href="${resource(dir: 'css', file: 'font.css')}" type="text/css" cache="false"/>
  <link rel="stylesheet" href="${resource(dir: 'css', file: 'plugin.css')}" type="text/css"/>	
  <link rel="stylesheet" href="${resource(dir: 'css', file: 'app.css')}" type="text/css"/>	 
  <!--[if lt IE 9]>
    <script src="${resource(dir:'js/ie', file: 'respond.min.js')}" type="text/javascript" cache="false"></script>
    <script src="${resource(dir:'js/ie', file: 'html5.js')}" type="text/javascript" cache="false"></script>
    <script src="${resource(dir:'js/ie', file: 'fix.js')}" type="text/javascript" cache="false"></script>
  <![endif]-->
</head>
<body>
  <section id="content" class="m-t-lg wrapper-md animated fadeInDown">
    <a class="nav-brand" href="index.html">Secrecy</a>
    <div class="row m-n">
      <div class="col-md-4 col-md-offset-4 m-t-lg">
        <section class="panel">
          <header class="panel-heading bg bg-primary text-center">
            הירשם
          </header>
          <g:form class="panel-body" controller="auth" action="register" method="post">
          	<div id="resultsDiv"></div>            
            <div class="form-group">
              <label class="control-label">שם פרטי</label>
              <input type="text" name="firstName" placeholder="פלוני" class="form-control">
            </div>
            <div class="form-group">             
              <label class="control-label">שם משפחה</label>
              <input type="text" name="lastName" placeholder="אלמוני" class="form-control">
            </div>
            <div class="form-group">
              <label class="control-label">שם משתמש</label>
              <input type="text" placeholder="שם משתמש" name="username" value="" class="form-control">
            </div>
            <div class="form-group">
              <label class="control-label">סיסמה</label>
              <input type="password" name="password" value="" id="inputPassword" placeholder="סיסמה" class="form-control">
            </div>
             <div class="form-group">
              <label class="control-label">הזן סיסמה שנית</label>
              <input type="password" name="password2" value="" id="inputPassword2" placeholder="הזן סיסמה שנית" class="form-control">
            </div>
            <div class="checkbox">
              <label>
                <input type="checkbox"> אשר <a href="#"> תנאים ומדיניות </a>
              </label>
            </div>           
            <g:submitToRemote url="[controller: 'auth', action:'register']" class="btn btn-info" method="POST" update="resultsDiv" value="הירשם"/>	
            <div class="line line-dashed"></div>
            <p class="text-muted text-center"><small>כבר יש לך חשבון?</small></p>
            <g:link action="login" class="btn btn-white btn-block">התחבר</g:link>
          </g:form>
        </section>
      </div>
    </div>
  </section>
  <!-- footer -->
  <footer id="footer">
    <div class="text-center padder clearfix">
      <p>
        <small>כל הזכויות שמורות ל-Atlancy <br>&copy; 2013</small>
      </p>
    </div>
  </footer>
  <!-- / footer -->
  <script src="${resource(dir: 'js', file: 'jquery.min.js')}" type="text/javascript"></script>
  <!-- Bootstrap -->
  <script src="${resource(dir: 'js', file: 'bootstrap.js')}" type="text/javascript"></script>
  <!-- app -->
  <script src="${resource(dir: 'js', file: 'app.js')}" type="text/javascript"></script>
  <script src="${resource(dir: 'js', file: 'app.plugin.js')}" type="text/javascript"></script>
  <script src="${resource(dir: 'js', file: 'app.data.js')}" type="text/javascript"></script>
</body>
</html>