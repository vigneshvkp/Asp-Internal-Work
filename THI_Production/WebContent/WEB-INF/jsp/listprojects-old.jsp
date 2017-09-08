<?xml version="1.0" encoding="ISO-8859-1" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
	<head>
		<title><fmt:message key="title"/></title>
		<link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css"/>
		<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.4/jquery.min.js"></script>
		<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
	</head>
	<body>
		<h1><fmt:message key="projectlist.heading"/></h1>
		<p><fmt:message key="greeting"/> <c:out value="${modal.now}"/></p>
		<form:form method="post">
			<table align="center" bgcolor="f8f8ff" border="1" cellpadding="5" cellspacing="0" width="95%">
				<tr bgcolor="888888">
					<th>Pros-Project</th>
					<th>Project Name</th>
					<th>Active</th>
					<th>StartDate</th>
					<th>EndDate</th>
					<th></th>
				</tr>
				<c:forEach items="${modal.projects}" var="proj">
					<tr bgcolor="CCCCFF">
						<td align="center">
							<c:out value="${proj.prosProjectName}"></c:out>
						</td>
						<td align="center">
							<c:out value="${proj.projectName}"></c:out>
						</td>
						<td align="center">
							<input type="checkbox" id="active${proj.id}" checked="checked" disabled="disabled"/>
								<script>if(!${proj.active}){
									$("#active${proj.id}").removeAttr("checked");
								}
								</script>
						</td>
						<td align="center">
							<c:out value="${proj.startDate}"></c:out>
						</td>
						<td align="center">
							<c:out value="${proj.endDate}"></c:out>
						</td>
						<td align="center">
							<a href="addproject.htm?id=${proj.id}" id="editProject${proj.id}"> Edit Project </a>
						</td>
					</tr>
				</c:forEach>
			</table>
		</form:form>
		<br/>
		<a href="<c:url value="addproject.htm"/>"> Add Project </a>
	</body>
</html>