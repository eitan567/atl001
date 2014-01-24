<!DOCTYPE html>
<html lang="he">
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
  <section id="content" class="m-t-lg wrapper-md animated fadeInUp">
    <a class="nav-brand" href="index.html">Secrecy</a>
    <div class="row m-n">
      <div class="col-md-4 col-md-offset-4 m-t-lg">
        <section class="panel">
          <header class="panel-heading text-center">
            התחברות
          </header>
          <g:form class="panel-body" controller="auth" action="signIn" method="post">
          	<input type="hidden" name="targetUri" value="${targetUri}" />
            <div class="form-group">
              <label class="control-label">שם משתמש</label>
              <input type="text" placeholder="שם משתמש" name="username" value="${username}" class="form-control">
            </div>
            <div class="form-group">
              <label class="control-label">סיסמה</label>
              <input type="password" name="password" value="" id="inputPassword" placeholder="סיסמה" class="form-control">
            </div>
            <div class="checkbox">
              <label>
                <input type="checkbox"> זכור אותי
              </label>
            </div>
            <a href="#" class="pull-left m-t-xs"><small>שחכת את סיסמתך?</small></a>
            <button type="submit" class="btn btn-info">התחבר</button>
            <div class="line line-dashed"></div>
            <a href="#" class="btn btn-facebook btn-block m-b-sm"><i class="icon-facebook pull-right"></i>התחבר באמצעות פייסבוק</a>
            <a href="#" class="btn btn-twitter btn-block"><i class="icon-twitter pull-right"></i>התחבר באמצעות טוויטר</a>
            <div class="line line-dashed"></div>
            <p class="text-muted text-center"><small>אין לך חשבון?</small></p>
            <g:link action="signup" class="btn btn-white btn-block">צור חשבון</g:link>
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