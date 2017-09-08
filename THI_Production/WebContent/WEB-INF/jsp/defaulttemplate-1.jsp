<%@ taglib prefix="propelj" uri="/propelj"%>

<head>

<script type="text/javascript">
	var requestPathURI = 'http://' + window.location.host + '<%=request.getContextPath()%>';
</script>

<link href="styles/style_horizontal.css" type="text/css" rel="stylesheet" />	
	
<script	type="text/javascript" src="../menu/horizontal/menu.js"></script>
<script type="text/javascript" src="../menu/horizontal/script/c_config.js"></script>
<script type="text/javascript" src="../menu/horizontal/script/c_smartmenus.js"></script>


</head>

<div id="main">
	<!-- Header Part Start -->
	<div id="header">
		<img src="images/logo1.gif" alt="Aspire Systems" class="logo1" />
		<img src="images/aspire_logo100.gif" alt="Aspire Systems" class="logo2" />
	</div>
	<!-- Header Part End -->

	<!-- Menu Part Start -->
	<div id="menu">
		<ul id="Menu1" class="MM">
			<propelj:menu filePath="/WEB-INF/menu.xml" menutype="horizontal" aceNo="ACE0106" auditor="false"/>
		</ul>
	</div>
	<!-- Menu Part End -->
	
	<!-- Middle Part Start -->
	<div id="middle"> 
	  <table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
          <td style="padding:10px;" valign="top" width="100%">
				<!-- Content Part Start -->
				<decorator:body />
		  </td>
		  </tr>
	  </table>
	</div>

	<div id="footer">
		<p class="copyright">Propel Version 1.1</p>
	</div>

</div>
