<%@ include file="/WEB-INF/jsp/include.jsp" %>
<script>
	$(document).ready(function(){
		$(".acc-section").hide();
		$(".duReport tbody tr:even").css({			
			background:"#FFFEEF"
		});
		$(".duReport tbody tr:odd").css({
			background:"#eceff1"
		});
		$(".export").click(function(){
			//$("#duReportPdf").submit();
			alert("Yet To Implement");			
		});
		
	});
			
</script>

<c:if test="${not empty duReport}">
	<center>
	<form action="duReport.htm" target="_blank" id="duReportPdf" method="post">
		<input type="hidden" name="exportAsPdf" value="true">
		<input type="hidden" name="departmentId" value="${reportForm.departmentId}">
		<input type="hidden" name="auditCycle" value="<fmt:formatDate pattern="MM/yyyy" value="${reportForm.auditCycle}"/>">
	</form>		
	<div id="options">
		<a href="#" class="export">Export As PDF </a>
	</div>
	<c:forEach var="entry" items="${duReport}">
	<br/>
    <h2 style="font-size: 15px; font-weight:bold; padding:10px 0px 10px 25px; background-color:#0077BB; color: #FFFFFF;"  align="left" >${entry.key} Projects</h2>
    <table cellpadding="5" cellspacing="0" border="0" class="duReport" align="center">
			<c:forEach var="duReportData" items="${entry.value}" varStatus="vs">				
				<c:if test="${vs.first}">
					<thead>
						<tr align="center">
							<th>Project Name</th>
							<th>Audit Date</th>
							<c:forEach var="entry" items="${duReportData.assesmentGroupScore}">
								<th>&nbsp;${entry.key}&nbsp;</th>
							</c:forEach>
							<th>Overall Score</th>
						</tr>
					</thead>
				</c:if>
				<tr>
					<td>${duReportData.projectName}</td>
					<td>${duReportData.auditDate}</td>
					<c:forEach var="entry" items="${duReportData.assesmentGroupScore}">						
							<c:choose>
								<c:when test="${entry.value==-1}"><td align="center">&nbsp;N/A&nbsp;</td></c:when>
								<c:otherwise>
							    	<td align="center" class="score<fmt:formatNumber type="number" value="${entry.value}"/>">&nbsp;<fmt:formatNumber type="number" value="${entry.value}" />&nbsp;</td>
							    </c:otherwise>
							</c:choose>						
					</c:forEach>
					<c:choose>
						<c:when test="${duReportData.overallScore < 0 }">
							<td align="center">&nbsp;N/A&nbsp;</td>
						</c:when>							   
					    <c:when test="${duReportData.overallScore >= 0 && duReportData.overallScore< 1}">
					    	<td align="center" class="score1">&nbsp;${duReportData.overallScore}&nbsp;</td>
					    </c:when>
					    <c:when test="${duReportData.overallScore >=1 && duReportData.overallScore< 2}">
					    	<td align="center" class="score2">&nbsp;${duReportData.overallScore}&nbsp;</td>
					    </c:when>
					    <c:when test="${duReportData.overallScore >=2  && duReportData.overallScore < 2.95 }">
					    	<td align="center" class="score3">&nbsp;${duReportData.overallScore}&nbsp;</td>
					    </c:when>
					     <c:when test="${duReportData.overallScore >= 2.95}">
					    	<td align="center" class="score4">&nbsp;${duReportData.overallScore}&nbsp;</td>
					    </c:when>
					    <c:otherwise>
					    	<td align="center">&nbsp;${duReportData.overallScore}&nbsp;</td>
					    </c:otherwise>
					 </c:choose>
				</tr>
			</c:forEach>
		</table>
	</c:forEach>
</center>
</c:if>
<c:if test="${empty duReport}">
	<br></br>
	<center>
	<h1><fmt:message key="report.notfound" /></h1>
	</center>
</c:if>