<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@page import="org.springframework.web.servlet.ModelAndView"%>
<%@page import="java.util.List"%>
<%@page import="com.model.AJAXResponse,java.util.List,com.model.Message"%>
<%@page import="com.constants.ContextualConstantsIF"%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Energy Panel</title>
<script type="text/javascript">
var contextPath='<%=request.getContextPath()%>';
	var updatable = false;
</script>

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/bootstrap.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap.js"></script>
</head>
<body>
	<jsp:include page="/jsp/customer.jsp"></jsp:include>
	<jsp:include page="/jsp/teachermenu.jsp"></jsp:include>

	<%
		AJAXResponse ajax = (AJAXResponse) request
				.getAttribute(ContextualConstantsIF.Keys.OBJ);

		if (null == ajax) {

		} else {
			List<Message> ebErrors = ajax.getEbErrors();

			if (null == ebErrors) {

			} else {
				Message msg = ebErrors.get(0);
	%>
	<div class="isa_error" name="userNameErr">
		<i class="fa fa-times-circle"></i>
		<%=msg.getErrMessage()%>
	</div>
	<%
		}
		}
	%>


	<div class="container">

		<form method="post"
			action="<%=request.getContextPath()%>/review/addSettings.do">


			<div class="form-group">
				<label for="email">Settings:</label> <input type="text"
					class="form-control" name="settings"></input>
			</div>

		</form>

	</div>
	<div id="content"></div>
	<div id="confirmationMessage"></div>

	<div id="criteriaContainer"></div>



	<div id="viewtracelist"></div>
</body>
</html>