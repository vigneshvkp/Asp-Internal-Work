<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
	<head>
		<title><fmt:message key="title"/></title>
		<%@ include file="/WEB-INF/jsp/head.html" %>
		<link rel="stylesheet" href="styles/jquery-ui-1.8.9.custom.css" type="text/css"/>
		<script src="scripts/jquery-1.4.4.min.js" type="text/javascript"></script>
		<script src="scripts/jquery.tools.min.js" type="text/javascript"></script>
		<script src="scripts/jquery-ui-1.8.9.custom.min.js" type="text/javascript"></script>
		<script>
			$(document).ready(function() {
				$("#auditdateId").datepicker({dateFormat: 'dd/mm/yy', minDate: new Date()});
			});
			function validateForm(){
				initialize();
				var flag = false;
				var auditdate = document.forms["projectauditorform"]["auditdate"].value;
				if (auditdate==null || auditdate=="") {
					document.getElementById("auditDateErrorMsg").style.display = "block";
					flag = true;
				} else {
					var date = new Date(auditdate);
					if ( Object.prototype.toString.call(date) === "[object Date]" ) {
					  // it is a date
					  if ( isNaN( date.getTime() ) ) {  // d.valueOf() could also work
					    // date is not valid
						document.getElementById("auditDateErrorMsg").style.display = "block";
						flag = true;
					  }
					}
					else {
					  // not a date
						document.getElementById("auditDateErrorMsg").style.display = "block";
						flag = true;
					}
				}
				if(flag) {
					return false;
				} else {
					return true;
				}
			}
			function initialize(){
				document.getElementById("auditDateErrorMsg").style.display = "none";
			}
		</script>
	</head>
	<body onload="initialize()">
		<div id="main">
			<%@ include file="/WEB-INF/jsp/header.html" %>
			<div id="middle"> 
			  <table width="100%"  border="0" cellspacing="0" cellpadding="0">
		         <tr>
			          <td style="padding:10px;" valign="top" width="100%">
						<div id="grid">
							<div id="gridheader">
								<img src="images/leftcorner.gif" alt="Left Corner" class="leftcorner" />
								<img src="images/rightcorner.gif" alt="Right Corner" class="rightcorner" />		
								<p>Welcome <%= userDetail.getUserName()%>, <fmt:message key="projectauditormap.heading"/></p>				
							</div>
							<div id="gridcontainer">
								<table class="border" cellspacing="0" cellpadding="0" width="100%">
									<tr>
										<td align="center"> 
											<form method="post" name="projectauditorform">
												<table width="100%" bgcolor="#f8f8ff" border="0" cellspacing="5" cellpadding="0" align="left">
													<tr>
														<td align="right" width="20%">Auditors&nbsp;&nbsp;</td>
														<td align="left">
															<select name="auditorId" id="auditor">
																<c:forEach items="${modal.auditors}" var="auditor">
																	<option id="${auditor.id }" value="${auditor.id}"><c:out value="${auditor.name}"></c:out> </option>
																</c:forEach>
													    	</select>
													    	<script>
													    		$("#auditor").val('${modal.projectauditor.auditorId}');
													    	</script>
													    </td>
													</tr>
													<tr>
														<td align="right" width="20%">Audit&nbsp;Date&nbsp;</td>
														<td align="left">
															<input type="text" id="auditdateId" name="auditdate" value="<fmt:formatDate pattern="dd/MM/yyyy" value="${modal.projectauditor.auditDate }" />"/>
															<div style="color:red;" id="auditDateErrorMsg">Please enter the valid Date</div>
														</td>
													</tr>
													<tr align="center">
														<td width="20%"></td>
														<td width="20%" align="right">
															<input id="map_auditor" type="submit" value="Map&nbsp;Auditor" onclick="return validateForm()" id="submitButton"/>
														</td>
														<td id="success">
															&nbsp;
														</td>
													</tr>
												</table>
												<script>
													if(${modal.projectauditor.auditComplete}){
														$("#submitButton").attr('disabled','disabled');
													}
													var isSaved=${modal.isSaved};
													if(isSaved){
														$("#success").html("Auditor Mapped Successfully");
													}
										    	</script>
												<input id="projectId" type="hidden" name="projectId" value="${modal.projectauditor.projectId}"/>
												<script>
													if(($("#projectId").val()=="")||($("#projectId").val()==0)){
														var url=document.location.href;
														$("#projectId").val(decodeURI((RegExp('id=' + '(.+?)(&|$)').exec(location.search)||[,null])[1]));
													}
												</script>
											</form>
										</td>
									</tr>
								</table>
							</div>
							<c:if test="${false == modal.isRowUpdated}">
								<h4 style="color:red" align="center">This Project already mapped for the given month</h4>
							</c:if>
						</div>
					</td>
				</tr>
			  </table>
			</div>
			<%@ include file="/WEB-INF/jsp/footer.html" %>
		</div>
	</body>
</html>