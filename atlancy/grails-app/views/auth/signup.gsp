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
          <form action="index.html" class="panel-body">
            <div class="form-group">
              <label class="control-label">שם מלא</label>
              <input type="text" placeholder="eg. Your name or company" class="form-control">
            </div>
            <div class="form-group">
              <label class="control-label">שם משתמש (באנגלית)</label>
              <input type="text" placeholder="שם משתמש" name="username" class="form-control">
            </div>
            <div class="form-group">
              <label class="control-label">הזן סיסמה</label>
              <input type="password" name="password" value="" placeholder="סיסמה" class="form-control">
            </div>
            <div class="checkbox">
              <label>
                <input type="checkbox"> אשר <a href="#"> תנאים ומדיניות </a>
              </label>
            </div>
            <button type="submit" class="btn btn-info">הירשם</button>
            <div class="line line-dashed"></div>
            <p class="text-muted text-center"><small>כבר יש לך חשבון?</small></p>
            <g:link action="login" class="btn btn-white btn-block">התחבר</g:link>
          </form>
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