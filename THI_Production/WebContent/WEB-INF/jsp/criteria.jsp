 <%@ include file="/WEB-INF/jsp/include.jsp" %>
 <c:if test="${not empty criterias}">
	 <ul class="criterias"> 
	 	<c:forEach var="criteria" items="${criterias}">
	 	<li>${criteria}</li>
	 	</c:forEach>
	 </ul>
 </c:if>
  <c:if test="${empty criterias}">
	<br />
	<br />
	<center>
		<h1>No Checklist Items.</h1>
	</center>
</c:if>