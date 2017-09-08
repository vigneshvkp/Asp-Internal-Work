<?xml version="1.0" encoding="ISO-8859-1" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
  <title><fmt:message key="title"/></title>
  <style>
    .error { color: red; }
  </style>  
  <%@ include file="/WEB-INF/jsp/head.html" %>
</head>
<body>
<div id="main">
<%@ include file="/WEB-INF/jsp/header.html" %>
<br><br>
<form:form method="post" commandName="loginCredentials">
   <table style="valign:middle" align="center" width="100%" bgcolor="#ffffff" border="0" cellspacing="5" cellpadding="5">
   <tr>
   	<td width="40%" colspan="3" align="center"><fmt:message key="login.heading"/></td>
   </tr>
    <tr >
      <td align="right" width="40%"><fmt:message key="login.userName"/>:</td>
        <td width="15%">
          <form:input path="userName" maxlength="50"/></td><td width="50%">
          <form:errors path="userName" cssClass="error"/>
        </td>
    </tr>
    <tr>
      <td align="right" width="40%" ><fmt:message key="login.password"/>:</td>
        <td width="15%">
          <form:password path="password" maxlength="50"/></td><td width="45%">
          <form:errors path="password" cssClass="error"/>
        </td>
    </tr>
    <tr>
      <td align="center" colspan="3" width="40%">
      	<input type="submit" value="Login">
      </td>
    </tr>
  </table>
  <br>
</form:form>
</div>
<%@ include file="/WEB-INF/jsp/footer.html" %>
</body>
</html>