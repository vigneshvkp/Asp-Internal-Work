<%@ taglib prefix="propelj" uri="/propelj"%>

<%-- Make sure menu is included only for pages that comes after login cycle, if we need menu before login we need another page --%>
<!-- Menu Part Start -->
<div id="menu">
	<ul id="Menu1" class="MM">
		<propelj:menu filePath="/WEB-INF/menu.xml" menutype="horizontal" aceNo="<%=userDetail.getAceNo()%>" auditor="<%=userDetail.isAuditor()%>"/>
	</ul>
</div>
<!-- Menu Part End -->
