<%@page import="com.aspire.thi.web.AuditorController"%>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
	<head>
		<title><fmt:message key="title"/></title>
		<style>
			.error { color: red; }
		</style>
		<%@ include file="/WEB-INF/jsp/head.html" %>
		<link rel="stylesheet" href="styles/jquery-ui-1.8.9.custom.css" type="text/css"/>
		<script src="scripts/jquery-1.4.4.min.js" type="text/javascript"></script>
		<script src="scripts/jquery.tools.min.js" type="text/javascript"></script>
		<script src="scripts/jquery-ui-1.8.9.custom.min.js" type="text/javascript"></script>
		<script>
			$(document).ready(function() {
				$("#startdatepicker").datepicker({dateFormat: 'dd/mm/yy', minDate: new Date()});
			});
			$(document).ready(function() {
				$("#enddatepicker").datepicker({dateFormat: 'dd/mm/yy', minDate: new Date()});
			});
			function validateForm(){
				initialize();
				var flag = false;
				var projname = document.forms["addprojectForm"]["projectName"].value;
				var startdate = document.forms["addprojectForm"]["startDate"].value;
				if (projname==null || projname==""){
					document.getElementById("projectNameErrorMsg").style.display = "block";
					flag = true;
				}
				if (startdate==null || startdate=="") {
					document.getElementById("startDateErrorMsg").style.display = "block";
					flag = true;
				} else {
					var date = new Date(startdate);
					if ( Object.prototype.toString.call(date) === "[object Date]" ) {
					  // it is a date
					  if ( isNaN( date.getTime() ) ) {  // d.valueOf() could also work
					    // date is not valid
						document.getElementById("startDateErrorMsg").style.display = "block";
						flag = true;
					  }
					}
					else {
					  // not a date
						document.getElementById("startDateErrorMsg").style.display = "block";
						flag = true;
					}
				}
				if(flag) {
					return false;
				} else {
					return true;
				}
			}
            function moveItems(fromId, toId) {
                var from = document.getElementById(fromId);
                var to = document.getElementById(toId);
                for (i=0;i < from.options.length;i++) {
                    if (from.options[i].selected) {
                    	/* from.options[i].id = 'Moved'; */
                       var opt = from.options[i];
                       to.options[to.options.length] = new Option(opt.innerHTML, opt.value);
                       from.options[i] = null;
                       i = i - 1;
                    }
                }
			}
            function saveSelectedAuditors() {
            	var comboBox = document.getElementById('selectedItems');
            	var pipeSeparatedElements = '';
            	for (i=0; i < comboBox.options.length; i++) {
                    if (comboBox.options[i]) {
                   		if(pipeSeparatedElements != '') {
                       		pipeSeparatedElements += '|';
                       	}
                       	pipeSeparatedElements += comboBox.options[i].value;                    		
                   }
                }
            	document.getElementById('<%= AuditorController.PIPE_SEPARATED_ACE_NO %>').value = pipeSeparatedElements; 
            }

            function removeItems(fromId, toId) {
                removeAuditors(fromId);
                var from = document.getElementById(fromId);
                var to = document.getElementById(toId);
                for (i=0;i < from.options.length;i++) {
                    if (from.options[i].selected) {
                    	/* from.options[i].id = 'Moved'; */
                       var opt = from.options[i];
                       to.options[to.options.length] = new Option(opt.innerHTML, opt.value);
                       from.options[i] = null;
                       i = i - 1;
                    }
                }
			}
            function removeAuditors(fromId){
            	var from = document.getElementById(fromId);
				var removeAceNos = "";
                for (i=0;i < from.options.length;i++) {
                    if (from.options[i].selected) {
                    	/* from.options[i].id = 'Moved'; */
                       var opt = from.options[i];
                       if(removeAceNos != ""){
                       		removeAceNos = removeAceNos + "---" + opt.value +"--" + opt.innerHTML;
                       }else{
                    	   removeAceNos = opt.value +"--" + opt.innerHTML;
                       }
                    }
                }
                var hyphenSeparatedAcenos = document.getElementById('<%= AuditorController.HYPHEN_SEPARATED_ACE_NO_REMOVE_AUDITOR %>').value;
                if(hyphenSeparatedAcenos != ""){
                	hyphenSeparatedAcenos = hyphenSeparatedAcenos + "---" + removeAceNos;
                }else{
                	hyphenSeparatedAcenos = removeAceNos;
                }
                document.getElementById('<%= AuditorController.HYPHEN_SEPARATED_ACE_NO_REMOVE_AUDITOR %>').value = hyphenSeparatedAcenos;
                
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
								<p>Welcome <%= userDetail.getUserName()%>, <fmt:message key="auditor.heading"/></p>				
							</div>
							<div id="gridcontainer">
								<table class="border" cellspacing="0" cellpadding="0" width="100%">
									<tr>
										<td align="center"> 
											<form:form method="post" id="addproject" name="addprojectForm">
											<input type="hidden" name="<%= AuditorController.PIPE_SEPARATED_ACE_NO %>" id="<%= AuditorController.PIPE_SEPARATED_ACE_NO %>" value=""/>
											<input type="hidden" name="<%= AuditorController.HYPHEN_SEPARATED_ACE_NO_REMOVE_AUDITOR %>" id="<%= AuditorController.HYPHEN_SEPARATED_ACE_NO_REMOVE_AUDITOR %>" value=""/>
											 <table width="100%">
											 	<tr>
											 		<td width="45%" style="padding-left:20%">
											 			<label>Non Auditors</label>
											 		</td>
											 		<td width="10%"></td>
											 		<td width="45%" style="padding-right:20%">
											 			<label>Auditors</label>
											 		</td>
											 	</tr>
								                <tr>
					                                <td width="45%" style="padding-left:20%">
		                                                <select id='availableItems' multiple="" size='10' style="width:75%">
		                                                	<c:forEach items="${modal.emps}" var="emps">
																<option value="${emps.aceNo}" ><c:out value="${emps.name}"></c:out></option>
															</c:forEach>
		                                                </select>
					                                </td>
					                                <td width="10%">
		                                                <table>
															<tr>
																<td>
																	<a href="javascript:removeItems('selectedItems','availableItems')">
																		<img src="images/leftarrow.jpg" width="32" height="32" />
																	</a>
																</td>
																<td>
																	<a href="javascript:moveItems('availableItems', 'selectedItems')">
																		<img src="images/rightarrow.jpg" width="32" height="32" />
																	</a>
																</td>
															</tr>
														</table>
					                                </td>
					                                <td width="45%" style="padding-right:20%">
		                                                <select id='selectedItems' multiple="" size='10' style="width:75%">
		                                                	<c:forEach items="${modal.auditors}" var="auditor">
																<option value="${auditor.aceNo}" ><c:out value="${auditor.name}"></c:out></option>
															</c:forEach>
		                                                </select>
					                                </td>
								                </tr>
								                <tr>
								                	<td width="15%" align="center" colspan="3">
								                		<button value="save" onclick="javascript:saveSelectedAuditors()">Save</button>
								                		${modal.msg}
								                	</td>
								                </tr>
											  </table>
											</form:form>
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
