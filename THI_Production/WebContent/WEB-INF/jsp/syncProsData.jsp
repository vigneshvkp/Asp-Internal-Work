<%@ include file="/WEB-INF/jsp/include.jsp" %>
<html>
	<head>
		<%@ include file="/WEB-INF/jsp/head.html" %>
  	</head>
	<body onload="initialize()">
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
								<p>Welcome <%= userDetail.getUserName()%>, Synchronization Page</p>				
							</div>
							<div id="gridcontainer">
								<table class="border" cellspacing="0" cellpadding="0" width="100%">
									<tr>
										<td align="center"> 
										    Successfully Synchronized the Customer & Project entries from Pros are into THI System at <c:out value="${model.now}"/>
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