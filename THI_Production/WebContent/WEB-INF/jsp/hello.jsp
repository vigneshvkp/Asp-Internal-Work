<?xml version="1.0" encoding="ISO-8859-1" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.aspire.thi.domain.UserDetail" %>
<%//@ include file="/WEB-INF/jsp/include.jsp" %>
<!-- html>
  <head><title>Hello :: Spring Application</title></head>
  <body>
    <h1>Hello - Spring Application</h1>
	<p>Greetings, it is now  <c:out value="${now}"/></p>
  </body>
</html-->

<html>
  <head><title>Hello :: Spring Application</title></head>
  <body>
  Session Reconnect
  <%--
    <h1><fmt:message key="heading"/></h1>
    <p><fmt:message key="greeting"/> <c:out value="${model.now}"/></p>
    <h3>Products</h3>
    <c:forEach items="${model.products}" var="prod">
      <c:out value="${prod.description}"/> <i>$<c:out value="${prod.price}"/></i><br/><br/>
    </c:forEach>
	<br/>
    	<a href="<c:url value="priceincrease.htm"/>">Increase Prices</a>
    <br/>
     --%>
    <%
    	//UserDetail userDetail = (UserDetail)request.getSession().getAttribute("UserDetail");
    	//out.println("UserDetail----->" + userDetail);
    %>
  </body>
</html>