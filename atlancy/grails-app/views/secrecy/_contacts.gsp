<g:if test="${contactInstanceTotal!=null && contactInstanceTotal>0}">
	<g:each in="${contactInstanceList}" status="i" var="contactInstance">
		<article class="media" 
				 id="contact-${contactInstance.id}"
				 data-phone="${contactInstance.number}" 			 
				 style="cursor: pointer; padding: 5px 15px 5px 5px;margin: 0px;"
				 onmouseover="cOver(this);" 
				 onclick="loadAllAjaxSections('${contactInstance.formatedPhoneNumber}','${contactInstance.targetPhone.simSubscriberId}',${contactInstance.id});cSelected(this);">
		    <span class="pull-right thumb-smed m-t-xs avatar bg-primary">
		    	<g:if test='${contactInstance.image!=null && contactInstance.image!=""}'>
					<img src="data:image/png;base64,${contactInstance.image}" alt="" class="img-circle"/>
				</g:if>
				<g:else>
					<img src="${resource(dir: 'images', file: 'avatar_default.jpg')}" alt="" class="img-circle"/>
				</g:else>
		    </span>
		    <div class="media-body">                                             
		      <div class="h5 truncate" data-toggle="tooltip" data-placement="${i==0 ? 'bottom':'top'}" title="${contactInstance.name}">${contactInstance.name}</div>                     
		      <small class="block">
		      	<i class="icon-chevron-left pull-left"></i> 
		      	<span class="dirLTR" style="padding-left: 10px;">${contactInstance.number}</span> 
		      	<br>
		      	<g:if test="${contactInstance.countryCode!=null}">                      	
	     			<span class="label bg-primary">(${contactInstance.countryCode})</span>
	     		</g:if>  
	     		<span class="label label-success">${contactInstance.type}</span>                      	
		      </small>  
		      <small class="block m-t-xs"></small>                      
		   </div>
		</article>
		<g:if test="${i<=contactInstanceTotal}">
			<div class="line pull-in" style="padding: 0px;margin:0px"></div>
		</g:if>
	</g:each>
</g:if>
<g:else>
	 <div id="noContacts" class="text-center m-b">
       <i> אין אנשי קשר להצגה </i>
     </div> 
</g:else>
<script>
	jQuery(document).ready(function() {		
		runEmoji();
		jQuery('.popovers').popover();			
		// tooltip
	    jQuery("[data-toggle=tooltip]").tooltip();	
	});  

</script>