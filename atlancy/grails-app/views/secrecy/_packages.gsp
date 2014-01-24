<g:each in="${packageInstanceList}" status="i" var="packageInstance">	
	<article class="media">
	  <span class="pull-left thumb-sm">
	  	<img src="data:image/png;base64,${packageInstance.icon}" alt="" class="img-circle" style="border-radius:80%"/>
	  </span>
	  <div class="media-body">
	    <div class="pull-right media-xs text-center text-muted">
	      <%--<strong class="h4">${packageInstance.versionCode}</strong><br>--%>
	      <small class="label bg-light">${packageInstance.versionName}</small>
	    </div>
	    <a href="#" class="h4">${packageInstance.appname}</a>
	    <small class="block"><a href="#" class="">${packageInstance.pname}</a> <span class="label label-success"></span></small>
	    <small class="block m-t-sm"></small>
	  </div>
	</article>
</g:each>
<script>
	jQuery("#numberOfPackages").html(${packageInstanceTotal})
</script>