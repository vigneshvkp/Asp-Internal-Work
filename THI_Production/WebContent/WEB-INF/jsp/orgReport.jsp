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

<c:if test="${not empty orgReport}">
	<center>
	<form action="orgReport.htm" target="_blank" id="duReportPdf" method="post">
		<input type="hidden" name="exportAsPdf" value="true">
		<input type="hidden" name="auditCycle" value="<fmt:formatDate pattern="MM/yyyy" value="${reportForm.auditCycle}"/>">
	</form>		
	<div id="options">
		<a href="#" class="export">Export As PDF </a>
	</div>
	<table cellpadding="5" cellspacing="0" border="0" class="duReport" align="center">
	<c:set var="tableHeaderPrinted" value="false"/>
	<c:forEach var="delivaryUnits" items="${orgReport}" varStatus="vs">
	<br/>	
		<c:forEach var="entry" items="${delivaryUnits.value}">						
				<c:if test="${tableHeaderPrinted == 'false'}">
					<thead>
						<tr align="center">
							<th>Delivary Unit</th>
							<th>Assesment Type</th>
							<c:forEach var="astGrpName" items="${entry.value.assesmentGroupScore}">
								<th>&nbsp;${astGrpName.key}&nbsp;</th>
							</c:forEach>
							<th>Overall Score</th>
						</tr>
					</thead>
					<c:set var="tableHeaderPrinted" value="true"/>
				</c:if>
				<tr align="center">
					<td>${delivaryUnits.key}</td>
					<td>${entry.key}</td>
					<c:forEach var="grpScores" items="${entry.value.assesmentGroupScore}">						
							<c:choose>
								<c:when test="${grpScores.value < 0 }">
									<td align="center">&nbsp;N/A&nbsp;</td>
								</c:when>							   
							    <c:when test="${grpScores.value >= 0 && grpScores.value< 1}">
							    	<td align="center" class="score1">&nbsp;<fmt:formatNumber type="number" maxFractionDigits="2" value="${grpScores.value}" />&nbsp;</td>
							    </c:when>
							    <c:when test="${grpScores.value >=1 && grpScores.value< 2}">
							    	<td align="center" class="score2">&nbsp;<fmt:formatNumber type="number" maxFractionDigits="2" value="${grpScores.value}" />&nbsp;</td>
							    </c:when>
							    <c:when test="${grpScores.value >=2  && grpScores.value < 2.95 }">
							    	<td align="center" class="score3">&nbsp;<fmt:formatNumber type="number" maxFractionDigits="2" value="${grpScores.value}" />&nbsp;</td>
							    </c:when>
							     <c:when test="${grpScores.value >= 2.95}">
							    	<td align="center" class="score4">&nbsp;<fmt:formatNumber type="number" maxFractionDigits="2" value="${grpScores.value}" />&nbsp;</td>
							    </c:when>
							    <c:otherwise>
							    	<td align="center">&nbsp;<fmt:formatNumber type="number" maxFractionDigits="2" value="${grpScores.value}" />&nbsp;</td>
							    </c:otherwise>
					   		</c:choose>
					</c:forEach>
						<c:choose>
							<c:when test="${entry.value.overallScore < 0 }">
								<td align="center">&nbsp;N/A&nbsp;</td>
							</c:when>							   
						    <c:when test="${entry.value.overallScore >= 0 && entry.value.overallScore< 1}">
						    	<td align="center" class="score1">&nbsp;<fmt:formatNumber type="number" maxFractionDigits="2" value="${entry.value.overallScore}" />&nbsp;</td>
						    </c:when>
						    <c:when test="${entry.value.overallScore >=1 && entry.value.overallScore< 2}">
						    	<td align="center" class="score2">&nbsp;<fmt:formatNumber type="number" maxFractionDigits="2" value="${entry.value.overallScore}" />&nbsp;</td>
						    </c:when>
						    <c:when test="${entry.value.overallScore >=2  && entry.value.overallScore < 2.95 }">
						    	<td align="center" class="score3">&nbsp;<fmt:formatNumber type="number" maxFractionDigits="2" value="${entry.value.overallScore}" />&nbsp;</td>
						    </c:when>
						     <c:when test="${entry.value.overallScore >= 2.95}">
						    	<td align="center" class="score4">&nbsp;<fmt:formatNumber type="number" maxFractionDigits="2" value="${entry.value.overallScore}" />&nbsp;</td>
						    </c:when>
						    <c:otherwise>
						    	<td align="center">&nbsp;<fmt:formatNumber type="number" maxFractionDigits="2" value="${entry.value.overallScore}" />&nbsp;</td>
						    </c:otherwise>
					   	</c:choose>					
				</tr>		
		</c:forEach>	
	</c:forEach>
</table>
</center>
</c:if>
<c:if test="${empty orgReport}">
	<br></br>
	<center>
	<h1><fmt:message key="report.notfound" /></h1>
	</center>
</c:if>