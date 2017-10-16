<%@ include file="/WEB-INF/jsp/include.jsp" %>
<script>
	$(document).ready(function(){
		$(".acc-section").hide();
		$(".acc-head").click(function(){
			$(this).next().slideToggle();
		});
		$(".expand").click(function(){
			$(".acc-section").show();
			
		});
		$(".collapse").click(function(){
			$(".acc-section").hide();
		});
		$(".projectDet tr:even").css({			
			background:"#FFFEEF"
		});
		$(".projectDet tr:odd").css({
			background:"#eceff1"
		});
		$(".export").click(function(){
			var url = 'report.htm?exportAsPdf=true&' + $("#auditForm").serialize();
			window.open(url);
			
		});
		
	});
			
</script>
	<c:if test="${not empty thiScore}">
	<div id="options">
		<a href="#" class="export">Export As PDF</a> | <a href="#" class="expand">Expand All</a> | <a href="#" class="collapse">Collapse All</a>
	</div>
	<ul id="acc" class="acc">
		<li>
			<div class="comments acc-header-section">			
				<span style="float:left;"> Category </span>
				<span style="float:right;">Score </span>
				<div style="clear:both;"></div>
			</div>
		</li>
			<c:forEach var="groupScore" items="${thiScore.assesmentGroupScores}">
				<li>
					<h3 class="acc-head">			
						<span class="catTitle"> ${groupScore.name} </span>						
						<c:choose>
						    <c:when test="${groupScore.score==-1}"><span style="float:right;" class="score">N/A</span></c:when>
						      <c:otherwise><span style="float:right;" class="score score${groupScore.score}"> ${groupScore.score}</span>
						      </c:otherwise>
						    </c:choose>
						<div style="clear:both;"></div>
					</h3>
					<div class="acc-section" style="opacity: 1; height: auto;">
						<div class="acc-content">				
							<table border="0" class="catagory" cellpadding=0 cellspacing=0 border=0 width="100%">
								<tr bgcolor="#bdcdd9" align="center" class="headingblack">
			                        <th width="20%">Criteria</th>
			                        <th width="35%">Description</th>
			                        <th width="35%">Remarks</th> 
			                        <th width="10%">Score</th>                          
			                    </tr>														
								<c:forEach var="lineItemLog" items="${groupScore.lineItemLogs}">
								<tr>
									<td width="20%" class="lineItemHeading">${lineItemLog.text}</td>
									<td width="35%">${lineItemLog.description}</td>
									<td width="35%">${lineItemLog.comments}</td>
									<td width="10%">${(lineItemLog.score>=0) ?  lineItemLog.score: "N/A"}</td>
								</tr>
								</c:forEach>							
							</table>
						</div>
					</div>
				</li>
		</c:forEach>
		<li>
			<div class="comments acc-header-section">			
				<span style="float:left;"> OverAll TSC </span>
				
				<c:choose>
					    <c:when test="${thiScore.overallScore >=0 && thiScore.overallScore< 1}">
					    	<span class="score score1">${thiScore.overallScore}</span>
					    </c:when>
					    <c:when test="${thiScore.overallScore >=1 && thiScore.overallScore< 2}">
					    	<span class="score score2">${thiScore.overallScore}</span>
					    </c:when>
					    <c:when test="${thiScore.overallScore >=2 && thiScore.overallScore< 2.95}">
					    	<span class="score score3">${thiScore.overallScore}</span>
					    </c:when>
					    <c:when test="${thiScore.overallScore >=2.95}">
					    	<span class="score score4">${thiScore.overallScore}</span>
					    </c:when>
					    <c:otherwise>
					    	<span class="score">${thiScore.overallScore}</span>
					    </c:otherwise>
			   </c:choose> 					
				<div style="clear:both;"></div>
			</div>
		</li>
		<li>
			<div class="comments">
				<table width="100%" cellspacing="1" cellpadding="0" border="0">                       
		            <tbody><tr>
		              <td valign="middle" bgcolor="#eceff1" nowrap="nowrap" style="padding: 5px 10px;" class="heading1">Overall Comments</td>
		              <td valign="middle" bgcolor="#f1f1f1" class="content">${thiScore.comments}</td>
		            </tr>
		            <tr>
		              <td valign="middle" height="28" bgcolor="#eceff1" nowrap="nowrap" style="padding: 5px 10px;" class="heading1">Recommendations for the Project</td>
		              <td valign="middle" bgcolor="#f1f1f1" class="content">${thiScore.recommendations}</td>
		            </tr>
		            
		           
		            <tr>
		              <td valign="middle" height="28" bgcolor="#eceff1" nowrap="nowrap" style="padding: 5px 10px;" class="heading1">Auditor's Name</td>
		              <td valign="middle" bgcolor="#f1f1f1" class="content">${thiScore.auditorName}</td>
		            </tr>		           
					<tr>
		              <td valign="middle" height="28" bgcolor="#eceff1" nowrap="nowrap" style="padding: 5px 10px;" class="heading1">Auditor Date</td>
		              <td valign="middle" bgcolor="#f1f1f1" class="content">${thiScore.auditDate}</td>
		            </tr><tr>
		              <td valign="middle" height="28" bgcolor="#eceff1" nowrap="nowrap" style="padding: 5px 10px;" class="heading1">Auditee</td>
		              <td valign="middle" bgcolor="#f1f1f1" class="content">
		              <c:forEach items="${thiScore.auditeeNames}" var="emp">
						<c:out value="${emp}"></c:out>&nbsp;&nbsp;
						</c:forEach>
		              </td>
		            </tr>
		           
		       		</tbody>
				</table>
			</div>
		</li>
	</ul>
</c:if>
<c:if test="${empty thiScore}">
<br></br>
 <center><h1><fmt:message key="report.notfound"/></h1></center>
</c:if>