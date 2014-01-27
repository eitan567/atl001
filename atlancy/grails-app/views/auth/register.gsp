<g:if test="${error}">
	<div class="alert alert-danger">
		<button class="close" data-dismiss="alert"></button>
		<strong>שגיאה !</strong> ${flash.message}
	</div>
</g:if>
<g:else>
	<div class="alert alert-info">
		<button class="close" data-dismiss="alert"></button>
		<strong>הצלחה !</strong> ${flash.message}
	</div>
</g:else>