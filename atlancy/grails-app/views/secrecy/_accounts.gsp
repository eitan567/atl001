<g:each in="${accountInstanceList}" status="i" var="accountInstance">	
	<article class="media">
	  <span class="pull-left thumb-sm">
	  	<%--<img src="data:image/png;base64,${accountInstance.image}" alt="" class="img-circle"/>--%>
	  </span>
	  <div class="media-body">
	    <div class="pull-right media-xs text-center text-muted">
	      <strong class="h4"></strong><br>
	      <small class="label bg-light"></small>
	    </div>
	    <a href="#" class="h4">${accountInstance.accountName}</a>
	    <small class="block"><a href="#" class="">${accountInstance.accountType}</a><span class="label label-success"></span></small>
	    <small class="block m-t-sm"></small>
	  </div>
	</article>
</g:each>
<script>
	jQuery("#numberOfAccounts").html(${accountInstanceTotal})
</script>