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
	    $("#GetorgReport").click(function(){
	    	$.ajax({
		    	  type:"post",
		    	  url: 'orgReport.htm',
		    	  data:$("#orgReportForm").serialize(),		    	  
		    	  success: function(data) {
		    	    $('#reportContainer').html(data);
		    	  }
		    	});
	    	return false;
		 });
	    
	});		
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
							<p>Welcome ${userDetail.userName}, Organisation Report Form</p>				
						</div>
						<div id="gridcontainer">
							<table class="border" cellspacing="0" cellpadding="0" width="100%">
								<tr>
									<td align="center"> 
										<form:form method="post" commandName="orgReportForm" action="#" onsubmit="return false" id="orgReportForm"> 
											<table class="projectDet" width="100%" cellspacing="0">												
												<tr style="background: rgb(236, 239, 241);">	
													<td width="20%" align="right">For&nbsp;Month&nbsp;</td>
													<td align="left">
													    <form:input path="auditCycle" id="startDate" class="date-picker thiReportForm" />					    
													</td>
												</tr>
												<tr style="background: rgb(255, 254, 239);">	
													<td width="20%" align="right" ><input type="submit" value="&nbsp;Get Report&nbsp;" id="GetorgReport"/></td>
													<td>&nbsp;</td>							
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