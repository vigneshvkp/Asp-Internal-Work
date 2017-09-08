<%@ include file="/WEB-INF/jsp/include.jsp" %>

<html>
	<head>
		<%@ include file="/WEB-INF/jsp/head.html" %>
		<script>
			function openAudit(id,auditCycle,auditFreq,auditDate){
				var auditDateArray = auditDate.split("-");
				var auditDateObj = new Date();
				auditDateObj.setYear(parseInt(auditDateArray[0]));
				auditDateObj.setMonth(parseInt(auditDateArray[1]));
				auditDateObj.setDate(1);
				auditDateObj.setHours(0);
				auditDateObj.setMinutes(0);
				auditDateObj.setSeconds(0);
				auditDateObj.setMilliseconds(0);
				var auditDateTime = auditDateObj.getTime();

				var todayDateObj = new Date();
				todayDateObj.setHours(0);
				todayDateObj.setMinutes(0);
				todayDateObj.setSeconds(0);
				todayDateObj.setMilliseconds(0);
				var todayDateTime = todayDateObj.getTime();
				if(todayDateTime<auditDateTime){
					window.location.href="audit.htm?projectId="+id+"&auditCycle="+auditCycle;
				}else{
					alert("The audit cycle is over");
				}
			}
		</script>
	</head>
	<body>
		<div id="main">

			<%@ include file="/WEB-INF/jsp/header.html" %>
			<%@ include file="/WEB-INF/jsp/menu.jsp" %>

			<div id="middle"> 
			  <table width="100%"  border="0" cellspacing="0" cellpadding="0">
		         <tr>
			          <td style="padding:10px;" valign="top" width="100%">
						<div id="grid">
							<div id="gridheader">
								<img src="images/leftcorner.gif" alt="Left Corner" class="leftcorner" />
								<img src="images/rightcorner.gif" alt="Right Corner" class="rightcorner" />		
								<p>Welcome ${userDetail.userName}, Here are the list of THI's to be audited for this cycle</p>				
							</div>
							<div id="gridcontainer">
								<table class="border" cellspacing="0" cellpadding="0" width="100%">
								<tr>
								<td align="center"> 
								<c:if test="${not empty auditProjects}">
									<table align="left">
									<tr><th align="left">Project</th><th align="left">Audit Date</th></tr>
									<c:forEach var="project" items="${auditProjects}">
										<tr>
											<td>
												<a href="javascript:openAudit(${project.id},'${auditCycle}',${project.auditFrequency},'${project.projectAuditor.auditDate}')">${project.name}</a>
											</td>
											<td><label>&nbsp;<fmt:formatDate pattern="dd-MMM-yyyy" value="${project.projectAuditor.auditDate}"/></label></td>
										</tr>
									</c:forEach>
									</table>
								</c:if>
								<c:if test="${empty auditProjects}">
									<span>No Audit Found</span>
								</c:if>
								</td>
								</tr>
								</table>
								
							</div>
						</div>
					</td>
				</tr>
			  </table>
			</div>
			<%@ include file="/WEB-INF/jsp/footer.html" %>
		</div>
	</body>
</html>