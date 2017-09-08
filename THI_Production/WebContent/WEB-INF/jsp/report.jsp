<?xml version="1.0" encoding="ISO-8859-1" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/WEB-INF/jsp/include.jsp" %>

<html>
  <head><title><fmt:message key="report.title"/></title></head>
  <body>
    <p><fmt:message key="report.overallScore"/> <c:out value="${thiScore.overallScore}"/></p>  
	<br/>
    	<a href="<c:url value="report.htm"/>">Report</a>
    <br/>
  </body>
</html>