<div id="grid">
	<div id="gridheader">
		<img src="images/leftcorner.gif" alt="Left Corner" class="leftcorner" />
		<img src="images/rightcorner.gif" alt="Right Corner" class="rightcorner" />		
		<p><fmt:message key="audithistory.heading"/></p>				
	</div>
	<div id="gridcontainer">
		<c:if test="${not empty modal.projectAuditorHelpers}">
			<table width="100%" bgcolor="#f8f8ff" border="1" cellspacing="0" cellpadding="5" align="center" style="padding:5px">
				<tr bgcolor="#888888">
					<th width="20%" align="center">THI Name</th>
					<th width="20%" align="center">Auditor</th>
					<th width="20%" align="center"> AuditComplete </th>
					<th width="20%" align="center"> Audit&nbsp;Date</th>
				</tr>
				<c:forEach items="${modal.projectAuditorHelpers}" var="projauditor">
					<tr bgcolor="#CCCCFF">
						<td align="left">
							&nbsp;<c:out value="${projauditor.projectName}"/>
						</td>
						<td align="left">
							&nbsp;<c:out value="${ projauditor.auditorName }"/>
						</td>
						<td align="center"> 
							<input type="checkbox" checked="checked" id="auditcomplete${projauditor.id }" name="auditcomplete" disabled="disabled"/> 
							<script>if(!${projauditor.auditComplete }){
								$("#auditcomplete${projauditor.id }").removeAttr("checked");
							}
							</script>
						</td>
						<td align="right"> 
							&nbsp;<fmt:formatDate pattern="dd/MM/yyyy" value="${projauditor.auditDate}" />&nbsp;
						</td>
					</tr>
				</c:forEach>
			</table>
		</c:if>
		<c:if test="${empty modal.projectAuditorHelpers}">
			<h4 style="color:red" align="center">No Record found</h4>
		</c:if>
	</div>
</div>