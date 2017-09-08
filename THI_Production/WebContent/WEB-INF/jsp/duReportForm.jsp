<?xml version="1.0" encoding="ISO-8859-1" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
<title><fmt:message key="audit.title"/></title>
<%@ include file="/WEB-INF/jsp/cssAndScirptInclude.jsp" %>
<%@ include file="/WEB-INF/jsp/head.html" %>

<script>
	$(function() {		
	    $('.date-picker').datepicker( {
	        changeMonth: true,
	        changeYear: true,
	        showButtonPanel: false,
	        dateFormat: 'mm/yy',
	        gotoCurrent: false,
	        constrainInput: true,	        
	        onChangeMonthYear: function(dateText, inst) { 
	            var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val();
	            var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
	            $(this).datepicker('setDate', new Date(year, month, 1));
	        },	        
	        onClose: function(dateText, inst) { 
	            var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val();
	            var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
	            $(this).datepicker('setDate', new Date(year, month, 1));
	        }
	    });
	    if(!$("#startDate").val().trim()){
	    	$('.date-picker').datepicker('setDate', new Date());
	    }
	    $("#resetdate").click(function(){
		    var datestr = new Date();
		    var month = datestr.getMonth() + 1 ;
		    var monstr = "";
		    if(month<10){
			    monstr = "0"+month;
		    }else{
			    monstr = month;
		    }
		    var year = datestr.getFullYear();
		    $("#startDate").val(monstr+"/"+year);
	    	$("#toDate").val(monstr+"/"+year);
	    });
	    $("#GetDuReport").click(function(){
		    if(!validateFromToDate()){
			    return;
		    }
	    	$.ajax({
		    	  type:"post",
		    	  url: 'duReport.htm',
		    	  data:$("#duReportForm").serialize(),		    	  
		    	  success: function(data) {
		    	    $('#reportContainer').html(data);
		    	  }
		    	});
	    	return false;
		 });
	});		
	function validateFromToDate(){
		var auditDate = $("#startDate").val();
		var auditDateArray = auditDate.split("/");
		var auditDateObj = new Date();
		auditDateObj.setYear(parseInt(auditDateArray[1]));
		auditDateObj.setMonth(parseInt(auditDateArray[0])-1);
		auditDateObj.setHours(0);
		auditDateObj.setMinutes(0);
		auditDateObj.setSeconds(0);
		auditDateObj.setMilliseconds(0);
		var auditDateTime = auditDateObj.getTime();
		var toDate = $("#toDate").val();
		var toDateArray = toDate.split("/");
		var toDateObj = new Date();
		toDateObj.setYear(parseInt(toDateArray[1]));
		toDateObj.setMonth(parseInt(toDateArray[0])-1);
		toDateObj.setHours(0);
		toDateObj.setMinutes(0);
		toDateObj.setSeconds(0);
		toDateObj.setMilliseconds(0);
		var toDateTime = toDateObj.getTime();
		var todayDateObj = new Date();
		todayDateObj.setHours(0);
		todayDateObj.setMinutes(0);
		todayDateObj.setSeconds(0);
		todayDateObj.setMilliseconds(0);
		var todayDateTime = todayDateObj.getTime();
		if(todayDateTime<auditDateTime){
			alert("Please select a valid From Date");
			return false;
		}
		if(todayDateTime<toDateTime){
			alert("Please select a valid To Date");
			return false;
		}
		if(auditDateTime>toDateTime){
			alert("From date is greater than to Date");
			return false;
		}
		return true;
	}
</script>
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
							<p>Welcome ${userDetail.userName}, DU Report Form</p>				
						</div>
						<div id="gridcontainer">
							<table class="border" cellspacing="0" cellpadding="0" width="100%">
								<tr>
									<td align="center"> 
										<form:form method="post" commandName="duReportForm" action="#" onsubmit="return false" id="duReportForm"> 
											<table class="projectDet" width="100%" cellspacing="0">
												<tr style="background: rgb(255, 254, 239);">
													<td width="20%" align="right">DU&nbsp;Name&nbsp;</td>
													<td align="left">
														<form:select path="departmentId" id="departmentId">
															<form:option value="-1" label="Select DU"></form:option>
						         							<form:options items="${delivaryUnits}" itemValue="id" itemLabel="name" />
														</form:select>																
													</td>
												</tr>
												<tr style="background: rgb(236, 239, 241);">	
													<td width="20%" align="right">From&nbsp;Month&nbsp;</td>
													<td align="left">
													    <form:input path="auditCycle" id="startDate" class="date-picker thiReportForm" />					    
													</td>
													<td width="20%" align="right">To&nbsp;Month&nbsp;</td>
													<td align="left">
													    <form:input path="toDate" id="toDate" class="date-picker thiReportForm" />					    
													</td>
												</tr>
												<tr style="background: rgb(255, 254, 239);">	
													<td width="20%" align="right" ><input type="submit" value="&nbsp;Get Report&nbsp;" id="GetDuReport"/></td>
													<td width="20%" align="left" ><input type="button" value="&nbsp;Reset&nbsp;" id="resetdate" style="text-align:center;min-width:120px;height:25px;"/></td>							
												</tr>
											</table>
											<br />
										</form:form>
									</td>
								</tr>
							</table>
						</div>
					</div>
					<div id="reportContainer" class="border" style="float:left; position:relative;width:100%;"></div>
				</td>
			</tr>
		  </table>		  
		</div>		
		<%@ include file="/WEB-INF/jsp/footer.html" %>
	</div>
</body>
</html>