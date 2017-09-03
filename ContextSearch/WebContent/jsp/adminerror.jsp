<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="com.model.AJAXResponse"%>
<%@page import="com.constants.ContextualConstantsIF"%>
<%@page import="java.util.List"%>


<%@page import="com.model.Message"%><html>
<head>
<link href="<%=request.getContextPath()%>/css/errormsg.css"
	rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Admin Error</title>
</head>
<body>
<jsp:include page="/jsp/adminmenu.jsp"></jsp:include>

<%

AJAXResponse ajax=(AJAXResponse)request.getAttribute(ContextualConstantsIF.Keys.OBJ);
if(null==ajax)
{
	
}
else
{
	
	List<Message> errMsgList=ajax.getEbErrors();
	if(null==errMsgList)
	{
		
	}
	else
	{
		Message m=errMsgList.get(0);
%>
<div class="error">

<%=m.getErrMessage()%>
</div>

<% 		
		
	}
	
}
%>
</body>
</html>