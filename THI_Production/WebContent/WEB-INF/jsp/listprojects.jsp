<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
	<head>
		<script src="scripts/jquery-1.4.4.min.js" type="text/javascript"></script>
		<script src="scripts/jquery.tools.min.js" type="text/javascript"></script>
		<script src="scripts/jquery-ui-1.8.9.custom.min.js" type="text/javascript"></script>
		<script>
			function showPrev(){
				var index = Number(document.projectForm.pagination.value);
				document.projectForm.pagination.value = index - Number(1);
				document.projectForm.submit();
			}
			function showNext(){
				var index = Number(document.projectForm.pagination.value);
				document.projectForm.pagination.value = index + Number(1);
				document.projectForm.submit();
			}
			function sortBy(sortByName, sortDirection){
				document.projectForm.sortByName.value = sortByName;
				document.projectForm.sortDirection.value = sortDirection;
				document.projectForm.submit();
			}			
		</script>
		<title><fmt:message key="title"/></title>
		<%@ include file="/WEB-INF/jsp/head.html" %>
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
								<p>Welcome <%= userDetail.getUserName()%>, <fmt:message key="projectlist.heading"/></p>				
							</div>
							<form method="post" name="projectForm">
								<input type="hidden" id="paginationId" name="pagination" value="${modal.pagination }" />
								<input type="hidden" id="sortById" name="sortByName" value="${modal.sortByName }" />
								<input type="hidden" name="sortDirection" value="" />
								<input type="hidden" name="pagesize" value="20"/>
								<div id="gridcontainer">
									<table class="border" cellspacing="0" cellpadding="0" width="100%">
										<tr>
											<td align="center">
												<table width="100%" bgcolor="#f8f8ff" border="0" cellpadding="5" cellspacing="0" align="center">
													<tr style="background: rgb(255, 254, 239);">
														<td align="left" width="10%">
															&nbsp;&nbsp;Project&nbsp;name&nbsp;
															<input type="text" name="thiName" value="${modal.thiNameStr }"/>
															&nbsp;&nbsp;
															&nbsp;&nbsp;DU&nbsp;
															<select id="deptId" name="dept">
																<option value="0">Select...</option>
																<c:forEach items="${modal.depts}" var="dept">
																	<option value="${dept.id }" ><c:out value="${dept.name }"></c:out></option>
																</c:forEach>
															</select>
															<script>
																$("#deptId").val('${modal.deptStr}');
															</script>															
															&nbsp;&nbsp;
															<input type="submit" value="Search" align="left"/>
														</td>
													</tr>
												</table>
											</td>
										</tr>
									</table>
								</div>
								<c:if test="${not empty modal.projects}">
								<div id="gridcontainer">
									<table align="center" bgcolor="#f8f8ff" border="1" cellpadding="5" cellspacing="0" width="100%">
										<tr bgcolor="#888888">
											<th align="center" width="25%"><a href="javascript:sortBy('THIName','${modal.thiNameSortDir }')">Project</a></th>
											<th align="center" width="25%">Active</th>
											<th align="center" width="25%">StartDate</th>
											<th align="center" width="25%">EndDate</th>
										</tr>
										<c:forEach items="${modal.projects}" var="proj">
											<tr bgcolor="#CCCCFF">
												<td align="left" width="25%">
													<a href="addproject.htm?id=${proj.id}" id="editProject${proj.id}">
														&nbsp;<c:out value="${proj.projectName}"/>
													</a>
												</td>
												<td align="center" width="25%">
													<input type="checkbox" id="active${proj.id}" checked="checked" disabled="disabled"/>
														<script>
														if(!${proj.active}){
															$("#active${proj.id}").removeAttr("checked");
														}
														</script>
												</td>
												<td align="right" width="25%">&nbsp;<fmt:formatDate pattern="dd/MM/yyyy" value="${proj.startDate}" />
													&nbsp;
												</td>
												<td align="right" width="25%">&nbsp;<fmt:formatDate pattern="dd/MM/yyyy" value="${proj.endDate}" />
													&nbsp;
												</td>
											</tr>
										</c:forEach>
									</table>
									<table align="right">
										<tr align="right">
											<td align="right" id="previous">&nbsp;
												<input type="button" id="prev" onclick="javascript:showPrev()" value="prev" style="height:20px;width:30px" disabled="disabled"/>
												<script>
													if(!${modal.showPrev}){
														$("#prev").removeAttr("disabled");
													}
												</script>
											</td>
											<td align="right"  id="next">&nbsp;
												<input type="button" id="nxt" onclick="javascript:showNext()" value="next" style="height:20px;width:30px" disabled="disabled"/>
												<script>
													if(!${modal.showNext}){
														$("#nxt").removeAttr("disabled");
													}
												</script>
											</td>
										</tr>
									</table>
								</div>
							</form>
						</div>
						</c:if>
						<c:if test="${empty modal.projects}">
							<h4 style="color:red" align="center">No Record found</h4>
						</c:if>
					</td>
				</tr>
			  </table>
			</div>
			<%@ include file="/WEB-INF/jsp/footer.html" %>
		</div>
	</body>
</html>