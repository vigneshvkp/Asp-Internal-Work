<%@ include file="/WEB-INF/jsp/include.jsp" %>

<html>
	<head>
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
								<p>Welcome ${userDetail.userName}, Here are the list of THI's you are associated in this cycle</p>				
							</div>
							<div id="gridcontainer">
								<table class="border" cellspacing="0" cellpadding="0" width="100%">
									<tr>
										<td align="center"> 
											<c:if test="${not empty projects}">
												<table width="100%" >
													<c:forEach var="project" items="${projects}">
														<tr><td><a href="thiReport.htm?projectId=${project.id}&auditCycle=${auditCycle}">${project.name}</a></td></tr>
													</c:forEach>
												</table>
											</c:if>
											<c:if test="${empty projects}">
												<span>No Projects were associated to you</span>
											</c:if>
										</td>
									</tr>
								</table>
							</div>
						</div>
					</td>
				</tr>
			  </table>
			</div>
			<%@ include file="/WEB-INF/jsp/footer.html" %>
		</div>
	</body>
</html>