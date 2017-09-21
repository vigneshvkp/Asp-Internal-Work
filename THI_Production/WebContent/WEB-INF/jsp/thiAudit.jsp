<?xml version="1.0" encoding="ISO-8859-1" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<%@ include file="/WEB-INF/jsp/cssAndScirptInclude.jsp"%>
<%@ include file="/WEB-INF/jsp/head.html"%>
<script>
	$(function() {
		$(".projectDet tr:even").css({
			background : "#FFFEEF"
		});
		$(".projectDet tr:odd").css({
			background : "#eceff1"
		});
		$('.date-picker')
				.datepicker(
						{
							changeMonth : true,
							changeYear : true,
							showButtonPanel : true,
							dateFormat : 'mm/yy',
							currentText : 'Now',
							gotoCurrent : true,
							onClose : function(dateText, inst) {
								var month = $(
										"#ui-datepicker-div .ui-datepicker-month :selected")
										.val();
								var year = $(
										"#ui-datepicker-div .ui-datepicker-year :selected")
										.val();
								$(this).datepicker('setDate',
										new Date(year, month, 1));
							}
						});
		$("#getAudit").click(function() {
			$.ajax({
				method : "post",
				url : 'audit.htm',
				data : $("#auditForm").serialize(),
				success : function(data) {
					$('#auditEntyContainer').html(data);
				}
			});
		});
		$("#dialog-modal").dialog({
			height : 200,
			modal : true,
			autoOpen : false,
			resizable : false,
			width : 420,
			buttons : {
				Ok : function() {
					$(this).dialog("close");
				}
			}
		});
		$("#apple_overlay").dialog("destroy");
		$("#apple_overlay").dialog({
			height : 470,
			modal : true,
			autoOpen : false,
			width : 720
		});
		initTabs();
	});
	function validateInput() {
		var isComplete = true;
		var tabIndex = -1;
		$('.assignment_groups').each(function(index) {
			var i = index;
			if ($("select.group_score", this).val() != -1 || i < 5) {
				var hasValue = false;
				$("textarea.lineItemComments", this).each(function() {
					if ($.trim($(this).val())) {
						hasValue = true;
						return false;
					}
				});
				if (!hasValue) {
					isComplete = false;
					tabIndex = i;
					return false;
				}
			}
			if (tabIndex >= 0) {
				return false;
			}
		});
		if (!isComplete) {
			var tabName = $(".tabs li:nth-child(" + (tabIndex + 1) + ")")
					.text();
			$("#dialog-modal")
					.html(
							"<p align='center'>Please provide atleast one comment on Tab  <br /> <br />   <span style='font-size:14px; font-weight:bolder'>"
									+ tabName + "</span> <p>");
			$("#dialog-modal").dialog("option", "title", 'Error');
			$("#dialog-modal").dialog("open");
		} else if (!$.trim($("#comments").val())
				|| !$.trim($("#recommendations").val())) {
			$("#dialog-modal")
					.html(
							"<p align='center'>Please provide Overall Comments and Recommentations <p>");
			$("#dialog-modal").dialog("option", "title", 'Error');
			$("#dialog-modal").dialog("open");
			isComplete = false;
		}
		var auditeecount = $("#otheruserids1 option").length;
		var selectedauditeecount = $("#otheruserids2 option").length;
		if ((auditeecount > 0) && (selectedauditeecount == 0)) {
			$("#dialog-modal")
					.html(
							"<p align='center'>Please select an auditee from the available list <p>");
			$("#dialog-modal").dialog("option", "title", 'Error');
			$("#dialog-modal").dialog("open");
			isComplete = false;
		}
		return isComplete;
	}
	function saveAuditData() {
		$.ajax({
			type : "post",
			url : 'saveAudit.htm',
			data : $("#thiAudit").serialize(),
			success : function(data) {
				var idVal = $.trim($("#thiAudit").find("#id").val());
				if (idVal == '' || idVal <= 0
						|| $("#auditComplete").val() == 'true') {
					$.ajax({
						method : "post",
						url : 'audit.htm',
						data : $("#auditForm").serialize(),
						async : false,
						success : function(data) {
							var html = $(data).find("#auditEntyContainer")
									.html();
							$('#auditEntyContainer').html(html);
							initTabs();
						}
					});
				}
				$("#dialog-modal").html(
						"<p align='center'><br/>Successfully Saved<br/></p>");
				$("#dialog-modal").dialog("option", "title", 'THI Audit');
				$("#dialog-modal").dialog("option", "height", 140);
				$("#dialog-modal").dialog("open");
			}
		});
	}
	function initTabs() {
		$("ul.tabs").tabs("div.panes > div");
		$("a", ".tabs").click(function() {
			window.location = $(this).attr("href");
		});
		$("#saveSubmit").click(function() {
			if (validateInput()) {
				$("#auditComplete").val("true");
				saveAuditData();
			} else {
			}
		});
		$("#saveAudit").click(function() {
			saveAuditData();
		});
		$(".assignmentGroups tr:odd").addClass("oddRow");
		// if the function argument is given to overlay,
		// it is assumed to be the onBeforeLoad event listener
		$(".viewCriteria").click(function() {
			$("#apple_overlay").html("");
			$("#apple_overlay").load($(this).attr("href"));
			$("#apple_overlay").dialog("option", "title", 'Checklist');
			$("#apple_overlay").dialog("open");
			return false;
		});
		var timeoutId = 0;
		$('textarea').keypress(function() {
			if (timeoutId)
				clearTimeout(timeoutId);
			timeoutId = setTimeout(function() {
				saveAuditData();
			}, 35000);
		});
	}
	function moveItems(fromId, toId) {
		var from = document.getElementById(fromId);
		var to = document.getElementById(toId);
		for (i = 0; i < from.options.length; i++) {
			if (from.options[i].selected) {
				/* from.options[i].id = 'Moved'; */
				var opt = from.options[i];
				to.options[to.options.length] = new Option(opt.innerHTML,
						opt.value);
				from.options[i] = null;
				i = i - 1;
			}
		}
		sortList(toId);
		var comboBox = document.getElementById('otheruserids2');
		var pipeSeparatedElements = '';
		for (i = 0; i < comboBox.options.length; i++) {
			if (comboBox.options[i]) {
				if (pipeSeparatedElements != '') {
					pipeSeparatedElements += '-';
				}
				pipeSeparatedElements += comboBox.options[i].value;
			}
		}
		document.getElementById("otheracenos").value = pipeSeparatedElements;
	}
	function sortList(id) {
		var obj = document.getElementById(id);
		var values = new Array();
		for (var i = 0; i < obj.options.length; i++) {
			values.push(obj.options[i].innerHTML + "--xx--"
					+ obj.options[i].value);
		}
		values = values.sort();
		for (var i = 0; i < values.length; i++) {
			valueArray = values[i].split('--xx--');
			obj.options[i].innerHTML = valueArray[0];
			obj.options[i].value = valueArray[1];
		}
	}

	function roundToTwo(num) {    
	    return +(Math.round(num + "e+2")  + "e-2");
	}
	
	//vkp
	var groupscore=[0,0,0,0,0,0,0];
	var de=4,req=3,des=2,cod=3,ut=2,rel=3,usa=1;
	var grouprow=[4,3,2,3,2,3,5];
	var calcweight=new Array(7);
	for (var i = 0; i < 8; i++) {
		calcweight[i] = new Array(8);
	}

	for(var i=0;i< 8;i++){
		for(var j=0;j<8;j++){
			calcweight[i][j]=0;
		}
	}
	
	function calcScore(groupCount,logCount) {

		var combo_score = document.getElementById("assesmentGroupScores"+groupCount+".lineItemScores"+logCount+".score");
		var percentage=document.getElementById("assesmentGroupScores"+groupCount+".weight"+logCount+".percentage");
		console.log("percentage : "+percentage.value);
		var wei=(percentage.value/100)/3;
		console.log("weigh	t : "+wei);
		console.log("Given score "+combo_score.value);
		var L=0;
		var val= wei*(L/(1-L)+1);	
	
		if(combo_score.value<0){
				val=val*0;
			}
		else{
			val=val*combo_score.value;
			}
		calcweight[groupCount][logCount]=val;
		console.log("calc weight "+calcweight[groupCount][logCount]);

		groupscore[groupCount]=0;
		for(var i=0;i<grouprow[groupCount];i++){
			groupscore[groupCount]=groupscore[groupCount]+calcweight[groupCount][i];
		}
		groupscore[groupCount]=groupscore[groupCount]*3;
		groupscore[groupCount]=roundToTwo(groupscore[groupCount]);
		console.log("group score tol "+groupscore[groupCount]);
		document.getElementById("assesmentGroupScores"+groupCount+".score").value=groupscore[groupCount];

	}
</script>
</head>
<body>
	<div id="main">
		<%@ include file="/WEB-INF/jsp/header.html"%>
		<%@ include file="/WEB-INF/jsp/menu.jsp"%>
		<div id="middle">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td style="padding: 10px;" valign="top" width="100%">
						<div id="grid">
							<div id="gridheader">
								<img src="images/leftcorner.gif" alt="Left Corner"
									class="leftcorner" /> <img src="images/rightcorner.gif"
									alt="Right Corner" class="rightcorner" />
								<p>
									Welcome ${userDetail.userName},
									<fmt:message key="audit.title" />
								</p>
							</div>
							<div id="gridcontainer">
								<table class="border" cellspacing="0" cellpadding="0"
									width="100%">
									<tr>
										<td align="center">
											<div id="auditEntyContainer">
												<c:choose>
													<c:when test="${selectAssesmentType}">
														<form action="audit.htm">
															<input type="hidden" name="projectId"
																value="${thiAudit.projectId}" /> <input type="hidden"
																value="<fmt:formatDate pattern="MM/yyyy" value="${thiAudit.auditCycleDate}" />"
																name="auditCycle" id="auditCycleDate" /> <b>Please
																select AssesmentType : </b> <select name="assesmentType"
																style="width: 120px; height: 20px; font-size: 14px;">
																<c:forEach var="assesmentType" items="${assesmentTypes}">
																	<option value="${assesmentType.id}">${assesmentType.name}</option>
																</c:forEach>
															</select> <input type="submit" value=" Audit " />
														</form>
														<br />
													</c:when>
													<c:otherwise>
														<c:if test="${not empty thiAudit}">
															<form:form method="post" commandName="thiAudit"
																onsubmit="return false" id="auditForm">
																<form:hidden path="projectId" />
																<input type="hidden"
																	value="<fmt:formatDate pattern="MM/yyyy" value="${thiAudit.auditCycleDate}"/>"
																	name="auditCycle" id="auditCycleDate" />
															</form:form>

															<form:form method="post" commandName="thiAudit"
																action="saveAudit.htm" onsubmit="return false">
																<form:hidden path="id" />
																<form:hidden path="auditorId" />
																<form:hidden path="projectId" />
																<form:hidden path="projectOwnerId" />
																<input type="hidden"
																	value="<fmt:formatDate pattern="yyyy-MM-dd" value="${thiAudit.auditCycleDate}"/>"
																	name="auditCycleDate" id="auditCycleDate" />
																<form:hidden path="assesmentType" />
																<form:hidden path="auditComplete" id="auditComplete" />
																<!-- the tabs -->
																<ul class="tabs skin2">
																	<c:forEach var="groupScore"
																		items="${thiAudit.assesmentGroupScores}">
																		<li><a class="current" href="#${groupScore.name}">${groupScore.name}</a></li>
																	</c:forEach>
																</ul>
																<c:set var="groupCount" value="0" />
																<c:forEach var="groupScore"
																	items="${thiAudit.assesmentGroupScores}">
																	<form:hidden
																		path="assesmentGroupScores[${groupCount}].assesmentGroupId" />
																	<form:hidden
																		path="assesmentGroupScores[${groupCount}].id" />
																	<!-- tab "panes" -->
																	<div class="panes">
																		<div style="display: block;" class="assignment_groups">
																			<table border="0" class="assignmentGroups"
																				width="100%" cellpadding="0" cellspacing="0">
																				<tr align="center" class="headerRow">
																					<th width="20%">Criteria</th>
																					<th width="30%">Description</th>
																					<th width="30%">Remarks</th>
																					<th width="10%">Scores</th>
																				</tr>
																				<c:set var="logCount" value="0" />
																				<c:set var="lineItemCount" value="0" />
																				
																				<c:forEach var="lineItemLog"
																					items="${groupScore.lineItemLogs}">
																					<form:hidden
																						path="assesmentGroupScores[${groupCount}].lineItemLogs[${logCount}].id" />
																					<form:hidden
																						path="assesmentGroupScores[${groupCount}].lineItemLogs[${logCount}].lineItemId" />
																					<form:hidden 
																						path="assesmentGroupScores[${groupCount}].lineItemScores[${logCount}].ass_line_item_id" />
																				 	<form:hidden 
																						path="assesmentGroupScores[${groupCount}].weight[${logCount}].assesment_line_item_id" />
																					<form:hidden 
																						path="assesmentGroupScores[${groupCount}].weight[${logCount}].assesment_type_id" />
																					<form:hidden 
																						path="assesmentGroupScores[${groupCount}].weight[${logCount}].percentage" />
																					
																					<tr>
																						<td>${lineItemLog.text}</td>
																						<td>${lineItemLog.description}</td>
																						<td><c:choose>
																								<c:when test="${thiAudit.auditComplete}">${lineItemLog.comments}</c:when>
																								<c:otherwise>
																									<form:textarea cols="40" rows="6"
																										cssClass="lineItemComments"
																										path="assesmentGroupScores[${groupCount}].lineItemLogs[${logCount}].comments" />
																								</c:otherwise>
																							</c:choose></td>

																						<!--  My change vkp assesmentGroupScores[${groupCount}].lineItemScores[${lineItemCount}].percentage
																						onchange="calcScore(${groupCount}, ${logCount})"
																						-->
																						<td ><form:select
																								path="assesmentGroupScores[${groupCount}].lineItemScores[${logCount}].score"
																								class="group_score" onchange="calcScore(${groupCount}, ${logCount})" >
																								<option value="-1">N/A</option>
																								<option value="0">0</option>
																								<option value="1">1</option>
																								<option value="2">2</option>
																								<option value="3">3</option>
																							</form:select></td>

																						<!--  My change vkp end -->

																					</tr>
																					<c:set var="lineItemCount"
																						value="${lineItemCount+1}" />
																					<c:set var="logCount" value="${logCount+1}" />
																				</c:forEach>
																				<tr class="scoreRow">
																					<td><a
																						href="viewCriteria.htm?assesmentGroupId=${groupScore.assesmentGroupId}"
																						rel="#overlay" class="viewCriteria">View
																							Checklist</a></td>
																							
																					<td align="right">Group Score</td>
																					<td align="left"><c:choose>
																							<c:when test="${thiAudit.auditComplete}">
																								<c:choose>
																									<c:when test="${groupScore.score==-1}">
																					    	N/A
																					    </c:when>
																									<c:otherwise>
																					    	${groupScore.score}
																					     </c:otherwise>
																								</c:choose>
																							</c:when>
																							<c:otherwise>
																								<!-- vkp  -->
																								<form:input style="width: 50px; align:left"
																									path="assesmentGroupScores[${groupCount}].score"
																									value="0" cssClass="group_score" />
																								<!--  vkp end -->
																								<!--	vkp
																								<form:select
																				 					path="assesmentGroupScores[${groupCount}].score"
																									cssClass="group_score">
																									<form:option value="-1">N/A</form:option>
																									<form:option value="0">0</form:option>
																									<form:option value="1">1</form:option>
																									<form:option value="2">2</form:option>
																									<form:option value="3">3</form:option>
																								</form:select>
																						vkp end		-->

																							</c:otherwise>
																						</c:choose></td>
																						<td></td>
																				</tr>
																			</table>
																			<!-- another link. uses the same overlay -->
																		</div>
																	</div>
																	<c:set var="groupCount" value="${groupCount+1}" />
																</c:forEach>
																<div class="ovrComments">
																	<div>
																		<table border="0" width="100%" cellspacing="0">
																			<tr class="oddRow">
																				<td width="60%">Overall Comments</td>

																				<td><c:choose>
																						<c:when test="${thiAudit.auditComplete}">${thiAudit.comments}</c:when>
																						<c:otherwise>
																							<form:textarea path="comments" rows="6" cols="80" />
																						</c:otherwise>
																					</c:choose></td>
																			</tr>
																			<tr>
																				<td>Recommentations</td>
																				<td><c:choose>
																						<c:when test="${thiAudit.auditComplete}">${thiAudit.recommendations}</c:when>
																						<c:otherwise>
																							<form:textarea path="recommendations" rows="6"
																								cols="80" />
																						</c:otherwise>
																					</c:choose></td>
																			</tr>
																			<tr>
																				<td>Auditees</td>
																				<td>
																					<div class="field_label"
																						style="width: 45%; float: left">
																						<table>
																							<tr>
																								<td><input type="hidden" name="otheracenos"
																									id="otheracenos" value="" /> <select
																									name="otheruserids1" id="otheruserids1"
																									multiple size="3">
																										<c:forEach items="${projectUsers}" var="emp">
																											<option id="${emp.aceNo}" value="${emp.name}"><c:out
																													value="${emp.name}"></c:out>
																											</option>
																										</c:forEach>
																								</select> <!--  
												    		<script>
												    			var data = '${modal.otheruserids}';
												    		 	var dataarray = data.split(',');
												    			$("#otheruserids1").val(dataarray);
												    			$("#otheruserids1").multiselect('refresh');
												    		</script>
												    		--></td>
																								<td><a
																									href="javascript:moveItems('otheruserids2', 'otheruserids1')">
																										<img src="images/leftarrow.jpg" width="32"
																										height="32" />
																								</a></td>
																								<td><a
																									href="javascript:moveItems('otheruserids1', 'otheruserids2')">
																										<img src="images/rightarrow.jpg" width="32"
																										height="32" />
																								</a></td>
																								<td><select name="otheruserids2"
																									id="otheruserids2" multiple size="3">
																										<c:forEach items="${projectAuditees}"
																											var="emp">
																											<option id="${emp.aceNo}" value="${emp.name}"><c:out
																													value="${emp.name}"></c:out>
																											</option>
																										</c:forEach>
																								</select></td>
																							</tr>
																						</table>
																					</div>
																				</td>
																			</tr>
																			<c:if test="${thiAudit.auditComplete}">
																				<tr class="oddRow">
																					<td>Overall Score:</td>
																					<td>${thiAudit.overallScore}</td>
																				</tr>
																			</c:if>
																		</table>
																	</div>
																</div>
																<center>
																	<br> <c:if test="${not thiAudit.auditComplete}">
																			<input type="submit" value="Save" id="saveAudit" /> &nbsp; <input
																				type="submit" value="Save & Submit" id="saveSubmit" />
																		</c:if> <c:if test="${thiAudit.auditComplete}">
																			<a
																				href="thiReport.htm?projectId=${thiAudit.projectId}&auditCycle=<fmt:formatDate pattern="MM/yyyy" value="${thiAudit.auditCycleDate}" />"><button>View
																					Report</button></a>
																		</c:if>
																</center>
															</form:form>
															<!-- overlayed element -->

															<!-- This JavaScript snippet activates those tabs -->
															<script>
																
															</script>
														</c:if>
														<c:if test="${empty thiAudit}">
															<h1>No Audit Found</h1>
														</c:if>
													</c:otherwise>
												</c:choose>
											</div>
										</td>
									</tr>
								</table>
							</div>
						</div>
					</td>
				</tr>
			</table>
		</div>
		<%@ include file="/WEB-INF/jsp/footer.html"%>
	</div>
	<div class="apple_overlay" id="apple_overlay">
		<!-- the external content is loaded inside this tag -->
	</div>
	<div id="dialog-modal" title="Error">
		<p>Adding the modal overlay screen makes the dialog look more
			prominent because it dims out the page content.</p>
	</div>
</body>
</html>
