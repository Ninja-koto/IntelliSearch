<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.constants.ContextualConstantsIF"%>
<%@page import="java.util.List"%>
<%@page import="com.model.AJAXResponse"%>
<%@page import="com.model.Message"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/reset.css">
<link rel='stylesheet prefetch' href='http://fonts.googleapis.com/css?family=Roboto:400,100,300,500,700,900|RobotoDraft:400,100,300,500,700,900'>
<link rel='stylesheet prefetch' href='http://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css'>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/loginstyle.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/errormsg.css">

<script type="text/javascript" >
var contextPath='<%=request.getContextPath()%>';
</script>
</head>
<body>
<jsp:include page="/jsp/teachermenu.jsp"></jsp:include>

<div id="content">

<%
	AJAXResponse ajax=(AJAXResponse)request.getAttribute(ContextualConstantsIF.MODEL_NAME);

ajax.getModel();

if(null==ajax){
}else{	
	List<Message> ebErrors=ajax.getEbErrors();
	if(null==ebErrors){
	}else{
	Message msg=ebErrors.get(0);
%>
<%=msg.getErrMessage()%>

<%
	}
}
%>

</div>

</body>
</html>