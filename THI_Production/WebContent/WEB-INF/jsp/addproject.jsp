<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
	<head>
		<title><fmt:message key="title"/></title>
		<style>
			.error { color: red; }
			.field_width {
				width:20%;
				float:left;
			}
			.field_label{
				float:left;
			}
			.row{
			height:20px;
			padding:10px;
			}
			.heading{
			font-weight:bold;
			}
		</style>
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
				var auditdate = document.forms["addprojectForm"]["auditdate"].value;
				if (auditdate==null || auditdate=="") {
					document.getElementById("auditDateErrorMsg").style.display = "block";
					//flag = true;
				} else {
					var date = new Date(auditdate);
					if ( Object.prototype.toString.call(date) === "[object Date]" ) {
					  // it is a date
					  if ( isNaN( date.getTime() ) ) {  // d.valueOf() could also work
					    // date is not valid
						document.getElementById("auditDateErrorMsg").style.display = "block";
						//flag = true;
					  }
					}
					else {
					  // not a date
						document.getElementById("auditDateErrorMsg").style.display = "block";
						//flag = true;
					}
				}
				if(!flag){
					var auditdateArray = auditdate.split("/");
					auditMonth = parseInt(auditdateArray[1],10)-1;
					var auditDateObj = new Date(auditdateArray[2],auditMonth,auditdateArray[0],0,0,0,0);
					var todayObj = new Date();
					todayObj = todayObj.setHours(0,0,0,0);
					if(auditDateObj.valueOf()<todayObj.valueOf()){
						alert("Enter a valid date - auditdate should be greater than or equal to today");
						flag = true;
					}else{
						flag = false;
					}
				}
				if(!flag){
					if(validateOtherEmails()){
						flag=false;
					}else{
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
                sortList(toId);
                var comboBox = document.getElementById('otheruserids2');
            	var pipeSeparatedElements = '';
            	for (i=0; i < comboBox.options.length; i++) {
                    if (comboBox.options[i]) {
                   		if(pipeSeparatedElements != '') {
                       		pipeSeparatedElements += '-';
                       	}
                       	pipeSeparatedElements += comboBox.options[i].value;      
                   }
                }
            	document.getElementById("otheracenos").value = pipeSeparatedElements; 
            }
			function sortList(id) {
				var obj = document.getElementById(id);
				var values = new Array();
				for(var i = 0; i < obj.options.length; i++) {
					values.push(obj.options[i].innerHTML + "--xx--" + obj.options[i].value);
				}
			 
				values = values.sort();
			 
				for(var i = 0; i < values.length; i++) {
					valueArray = values[i].split('--xx--');
					obj.options[i].innerHTML = valueArray[0];
					obj.options[i].value = valueArray[1];
				}
			}
			function validateOtherEmails(){
				var emails = document.getElementById("otheremails").value;
				if(emails.trim() == ""){
					return true;
				}
				emails = emails.trim();
				var emailArray = emails.split(',');
				for(i=0;i<emailArray.length;++i){
					var emailStr = emailArray[i];
					emailStr = emailStr.trim();
					if(emailStr != ""){
						if(!isValidEmailFormat(emailStr)){
							return false;
						}
					}
				}
				return true;
			}

			//is valid email format
			function isValidEmailFormat(email){
				var len = email.length;
				var k = email.lastIndexOf("@");
				if(k==-1){
					alert("Invalid email format"+"  ("+email+")");
					return false;
				}
				if(email.charAt(k+1) == '.'){
					alert("Invalid email format"+"  ("+email+")");
					return false;
				}
				var localpart = email.substring(0,k);
				len = localpart.length;
				if(len<=0){
					alert("Local part is missing"+"  ("+email+")");
					return false;
				}else if(len>64){
					alert("Local part should not exceed 64 characters"+"  ("+email+")");
					return false;
				}
				
				var domain = email.substring(k+1,email.length);
				len = domain.length;
				if(len<=0){
					alert("Domain is missing"+"  ("+email+")");
					return false;
				}else if(len>255){
					alert("Domain should not exceed 255 characters"+"  ("+email+")");
					return false;
				}
				
				//check the format with regular expression
				var reg = /^[a-zA-Z0-9+&@$!#'%=?/|._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
					if (reg.test(email)){
			 			return true;
					} else{
			 			alert("Invalid email format"+"  ("+email+")");
						return false;
					}
					
					return true;
					
			}
						
		</script>
	</head>
	<body onload="initialize()">
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
								<p>Welcome <%= userDetail.getUserName()%>, <fmt:message key="project.heading"/></p>				
							</div>
							<div id="gridcontainer">
								<table class="border" cellspacing="0" cellpadding="0" width="100%" style="font-size: 12px;">
									<tr>
										<td align="center"> 
											<form:form method="post" id="addproject" name="addprojectForm">
											<input type="hidden" name="otheracenos" id="otheracenos" value=""/>
												<div class="row">
													<div class="field_width" style="width:50%">
														<div class="field_label">
															<label class="heading">Project Name:&nbsp;</label>
														</div>
														<div class="field_label">
															<label>&nbsp;<c:out value="${modal.project.projectName }"></c:out></label>
														</div>
													</div>
													<div class="field_width" style="width:10%">
														<div class="field_label">
															<label class="heading">Active:&nbsp;&nbsp;</label>
														</div>
														<div class="field_label">
															<input type="checkbox" name="active" checked="checked" id="active" disabled="disabled"/>
															<script type="text/javascript">if(!${modal.project.active}){
																$("#active").removeAttr("checked");
															}
															</script>
														</div>
													</div>
													<div class="field_width">
														<div class="field_label">
															<label class="heading">Start Date:&nbsp;</label>
														</div>
														<div class="field_label">
															<label>&nbsp;<fmt:formatDate pattern="dd-MMM-yyyy" value="${modal.project.startDate }"/></label>
														</div>
													</div>
													<div id="end_date" class="field_width">
														<div class="field_label">
															<label class="heading">End date:&nbsp;</label>
														</div>
														<div class="field_label">
															<label id="end_date_value">&nbsp;<fmt:formatDate pattern="dd-MMM-yyyy" value="${modal.project.endDate }"/></label>
														</div>
													</div>
													<!-- script type="text/javascript">
														var label=$("#end_date_value").html();
														if(label){
															$("#end_date").hide();
														}
													</script -->
													<div style="clear:both;"></div>
												</div>
												<hr />
												<div class="row">
													<div class="field_label" style="width:15%;float:left">
														<label class="heading">Audit Frequency <br/>(in Months):&nbsp;&nbsp;</label>
													</div>
													<div class="field_label" style="width:25%;float:left">
														<select id="auditFrequency" name="auditFrequency">
															<c:forEach begin="1" end="2" varStatus="month">
																<option style="font-family: tahoma;" value="${month.index}" ><c:out value="${month.index}"></c:out></option>
															</c:forEach>
														</select>
														<script>
															$("#auditFrequency").val('${modal.project.auditFrequency}');
														</script>
													</div>
													<div class="field_label" style="float:left">
														<label class="heading">Audit&nbsp;Date:&nbsp;&nbsp;</label>
													</div>
													<div class="field_label" style="float:left">
														<input type="text" id="auditdateId" name="auditdate" value="<fmt:formatDate pattern="dd/MM/yyyy" value="${modal.projectauditor.auditDate }" />"/>
														<div style="color:red;" id="auditDateErrorMsg">Please enter the valid Date</div>
													</div>
												</div>
												<div class="row">
													<div class="field_label" style="width:15%;float:left">
														<label class="heading">Auditors:&nbsp;&nbsp;</label>
													</div>
													<div class="field_label" style="width:25%;float:left">
														<select name="auditorId" id="auditor">
															<c:forEach items="${modal.auditors}" var="auditor">
																<option id="${auditor.id }" value="${auditor.id}"><c:out value="${auditor.name}"></c:out> </option>
															</c:forEach>
												    	</select>
												    	<script>
												    		$("#auditor").val('${modal.projectauditor.auditorId}');
												    	</script>
													</div>
													<div class="field_label" style="width:8%;float:left">
														<label class="heading">Notify To:&nbsp;&nbsp;</label>
													</div>
													<div class="field_label" style="width:45%;float:left">
													<table><tr><td>
														<select name="otheruserids1" id="otheruserids1" multiple size="3">
															<c:forEach items="${modal.emps}" var="emp">
																<option id="${emp.aceNo}" value="${emp.aceNo}"><c:out value="${emp.name}"></c:out> </option>
															</c:forEach>
												    	</select>
												    	<!--  
												    		<script>
												    			var data = '${modal.otheruserids}';
												    		 	var dataarray = data.split(',');
												    			$("#otheruserids1").val(dataarray);
												    			$("#otheruserids1").multiselect('refresh');
												    		</script>
												    		-->
												    </td>
												    <td>
												    	<a href="javascript:moveItems('otheruserids2', 'otheruserids1')">
															<img src="images/leftarrow.jpg" width="32" height="32" />
														</a>
														</td><td>
														<a href="javascript:moveItems('otheruserids1', 'otheruserids2')">
															<img src="images/rightarrow.jpg" width="32" height="32" />
														</a>
														</td>
													<td>
														<select name="otheruserids2" id="otheruserids2" multiple size="3">
														</select>
													</td>
													</tr></table>
													</div>
												</div>
												<div class="row">
												 <div class="field_label" style="width:100%;float:left">
												 &nbsp;
												 </div>
												 </div>
												<div class="row">
												 <div class="field_label" style="width:15%;float:left">
												 	<label class="heading">Other Email:&nbsp;&nbsp;</label>
												 </div>
												 <div class="field_label" style="width:25%;float:left">
													<textarea rows="5" cols="25" name="otheremails" id="otheremails" style="vertical-align:top;horizontal-align:left;">&nbsp;
													</textarea>
													<script>
													document.getElementById("otheremails").value='';
													</script>
												</div>
												<div class="field_label" style="width:25%;float:left">
													<input type="submit" style="text-align:center; width:100px;" value="Save" onclick="return validateForm()"/>
													<div id="success" class="field_width" style="float:right;color:red;font-weight:bold;">
													</div>
													<script>
														var isSaved=${modal.isRowUpdated};
														if(isSaved){
															$("#success").html("&nbsp;&nbsp;Saved Successfully");
														}
													</script>
													</div>
												</div>
												<div class="row">
												 <div class="field_label" style="width:100%;float:left">
												 &nbsp;
												 </div>
												 </div>
												<input type="hidden" id="audit" name="auditFrequency" value="${modal.project.auditFrequency }"/>
												<input id="projectId" type="hidden" name="projectId" value="${modal.project.id}"/>
											</form:form>
										</td>
									</tr>
								</table>
							</div>
							<c:if test="${false == modal.isRowUpdated}">
								<h4 style="color:red" align="center">This Project already audited for the audit cycle</h4>
							</c:if>
						</div>
					</td>
				</tr>
		         <tr>
		         	<td style="padding:10px;" valign="top" width="100%">
		         		<%@ include file="/WEB-INF/jsp/projectauditor.jsp" %>
					</td>
				</tr>
			</table>
			</div>
			</div>
		<%@ include file="/WEB-INF/jsp/footer.html" %>
	</body>
</html>