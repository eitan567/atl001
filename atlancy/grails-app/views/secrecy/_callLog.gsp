<g:if test="${callLogsInstanceTotal>0}">
	<table class="table table-striped m-b-none" data-ride="datatables">
        <thead>
          <tr>
<%--            <th id="callLogNumberThHeader" class="hidden-480" style="min-width: 41px;max-width: 100px !important;"> <!-- min-width: 120px; -->--%>
<%--		  		${message(code: 'callLog.phoneNumber.label', default: 'phoneNumber')}--%>
<%--		  	</th>--%>
			<th class="hidden-480 centerContent">
				${message(code: 'callLog.type.label', default: 'type')}
			</th>
			<th class="hidden-480">
				${message(code: 'callLog.duration.label', default: 'duration')}
			</th>
			<th class="hidden-480">
				${message(code: 'callLog.time.label', default: 'time')}
			</th>
          </tr>
        </thead>
		<tbody>		
			<g:each in="${callLogInstanceList}" status="i" var="callLogInstance">
				<tr class="odd gradeX">
<%--					<td class="hidden-480"><g:if--%>
<%--							test="${fieldValue(bean: callLogInstance, field: 'type')!=''}">--%>
<%--							<img--%>
<%--								src="${resource(dir: 'images/callog', file: fieldValue(bean: callLogInstance, field: 'type')+'.png')}"--%>
<%--								alt="" style="width: 25px; height: 25px; float: right" />--%>
<%--						</g:if>--%>
<%--						 <g:link action="showCallLog" id="${callLogInstance.id}" style="padding:1px 5px 0 0;vertical-align:middle;">--%>
<%--							${fieldValue(bean: callLogInstance, field: "formatedPhoneNumber")}--%>
<%--						</g:link></td>--%>
					<td class="hidden-480 centerContent">
						${message(code: 'callLog.type.' + fieldValue(bean: callLogInstance, field: 'type') , default: '')}
					</td>
					<td class="hidden-480">
						<div data-toggle="tooltip" data-placement="top" title="${fieldValue(bean: callLogInstance, field: "formatedPeriod")}"> ${fieldValue(bean: callLogInstance, field: "formatedPeriod")}</div>
					</td>
					<td class="hidden-480" data-toggle="tooltip" data-placement="top" title="${fieldValue(bean: callLogInstance, field: "formatedDate")}">
						${fieldValue(bean: callLogInstance, field: "formatedDate")}
					</td>
				</tr>
			</g:each>
		</tbody>
	</table>
</g:if>
<script>
	jQuery(document).ready(function() {		
		// tooltip
	    jQuery("[data-toggle=tooltip]").tooltip();	
	}); 
</script>