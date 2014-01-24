<%--<g:if test="${smsInstanceTotal>0  && !isAllSmses}">	
	<g:each in="${smsInstanceList}" status="i" var="smsInstance">
		<article id="chat-id-${i}" class="chat-item ${smsInstance.folderName =='inbox' ? 'right' : 'left'}">
	      <a href="#" class="${smsInstance.folderName =='inbox' ? 'pull-right' : 'pull-left'} thumb-smed avatar">
	      	<g:if test='${contactDetails.image!=null && contactDetails.image!="" && smsInstance.folderName =="inbox"}'>
				<img class="avatar"	src="data:image/png;base64,${contactDetails.image}" alt="" />
			</g:if>
			<g:elseif test='${phoneOwnerData.image!=null && phoneOwnerData.image!="" && smsInstance.folderName !="inbox"}'>
				<img class="avatar"	src="data:image/png;base64,${phoneOwnerData.image}" alt="" />
			</g:elseif>
			<g:else>
				 <img src="${resource(dir: 'images', file: 'avatar_default.jpg')}" class="img-circle">				
			</g:else>
	      </a>
	      <section class="chat-body">
	        <div class="panel text-sm m-b-none ${smsInstance.folderName =='inbox' ? 'bg-light-dark' : ''}">
	          <div class="panel-body">
	            <span class="arrow ${smsInstance.folderName =='inbox' ? 'right' : 'left'}"></span>
					<span class="emoji ${smsInstance.folderName =='inbox' ? 'pull-right' : 'pull-left'}" style="line-height: 1.7em;word-wrap: break-word;">${smsInstance.msg}</span>
	          </div>
	        </div>
	        <small class="text-muted ${smsInstance.folderName =='inbox' ? 'pull-left' : 'pull-right'}">
	        <i class="icon-ok text-success"></i>
	        ${smsInstance.formatedDate} ${smsInstance.folderName =='inbox' ? contactDetails.name : phoneOwnerData.alias}</small>
	      </section>
	    </article>
	</g:each>		
</g:if> --%>


<g:if test="${smsInstanceTotal>0  && !isAllSmses}">	
	<g:each in="${smsInstanceList}" status="i" var="smsInstance">
		<article id="chat-id-${i}" class="chat-item right">
	      <a href="#" class="pull-right thumb-smed avatar">
	      	<g:if test='${contactDetails.image!=null && contactDetails.image!="" && smsInstance.folderName =="inbox"}'>
				<img class="avatar"	src="data:image/png;base64,${contactDetails.image}" alt="" />
			</g:if>
			<g:elseif test='${phoneOwnerData.image!=null && phoneOwnerData.image!="" && smsInstance.folderName !="inbox"}'>
				<img class="avatar"	src="data:image/png;base64,${phoneOwnerData.image}" alt="" />
			</g:elseif>
			<g:else>
				 <img src="${resource(dir: 'images', file: 'avatar_default.jpg')}" class="img-circle">				
			</g:else>
	      </a>
	      <section class="chat-body">
	        <div class="panel text-sm m-b-none ${smsInstance.folderName =='inbox' ? 'bg-light-dark' : ''}">
	          <div class="panel-body">
	            <span class="arrow right ${smsInstance.folderName =='inbox' ? 'dark' : 'light'}"></span>
					<span class="emoji pull-right" style="line-height: 1.7em;word-wrap: break-word;">${smsInstance.msg}</span>
	          </div>
	        </div>
	        <small class="text-muted pull-left">
	        <i class="icon-ok text-success"></i>
	        ${smsInstance.formatedDate} ${smsInstance.folderName =='inbox' ? contactDetails.name : phoneOwnerData.alias}</small>
	      </section>
	    </article>
	</g:each>		
</g:if>
<g:else>
	<div style="text-align: center;">אין נתונים להצגה</div>
</g:else>
<script type="text/javascript">
	$(document).ready(function() {
		runEmoji();		
		$('.no-touch .slim-scroll').each(function(){
			var $self = $(this), $data = $self.data(), $slimResize;
			var scrollHeight =0;
			if($data.entity=="contacts"){
				scrollHeight = window.screen.height-560;
			}else{
				scrollHeight= $data.height;
			}		
			$self.slimScroll({
			    height: scrollHeight,
			    position: 'right',
			    distance:0,
			    alwaysVisible: true,		    
			    railVisible: true,
			    railColor: '#222',
			    railOpacity: 0.1,
			    wheelStep: 1,
			    allowPageScroll: true,
			    disableFadeOut: false		    
			});
			$(window).resize(function(e) {
				clearTimeout($slimResize);
				$slimResize = setTimeout(function(){$self.slimScroll($data);}, 500);
			});
		});
	});
</script>