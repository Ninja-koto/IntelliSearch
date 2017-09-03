<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@page import="org.springframework.web.servlet.ModelAndView"%>
<%@page import="java.util.List"%>
<%@page import="com.model.AJAXResponse,java.util.List,com.model.Message"%>
<%@page import="com.constants.ContextualConstantsIF"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Energy Panel</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/extjs41/resources/css/ext-all.css" />
<script type="text/javascript" >
var contextPath='<%=request.getContextPath()%>';
var updatable=false;
</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/extjs41/ext-all-debug.js"></script>

</head>
<body>
<jsp:include page="/jsp/teachermenu.jsp"></jsp:include>

<%
	AJAXResponse ajax=(AJAXResponse)request.getAttribute(ContextualConstantsIF.Keys.OBJ);

if(null==ajax)
{
	
}
else
{	
	List<Message> ebErrors=ajax.getEbErrors();
	
	if(null==ebErrors)
	{
		
	}
	else
	{
	Message msg=ebErrors.get(0);
%>
<div class="isa_error" name="userNameErr">
<i class="fa fa-times-circle"></i>
<%=msg.getErrMessage()%>
</div>
<%
	}
	}


%>


<form method="post" action="<%=request.getContextPath()%>/review/submitArticle.do" enctype="multipart/form-data">
 
 <table id="fileTable">
 
 		<tr>
 			<td>
 			Enter Article Name:
 			</td>
 			<td>
 			<input type="text" name="articleName"></input>
 			</td>
 		</tr>
 		
 		<tr>
 			<td>
 			Enter Author List Use Comma as Separator:
 			</td>
 			<td>
 			<input type="text" name="authors"></input>
 			</td>
 		</tr>
 		
 		
 		<tr>
 			<td>
 			Enter Publisher:
 			</td>
 			<td>
 			<input type="text" name="publisher"></input>
 			</td>
 		</tr>
 		
 		<tr>
 			<td>
 			Enter Article Desc:
 			</td>
 			<td>
 			<input type="text" name="articleDesc"></input>
 			</td>
 		</tr>
 		
 		
 		<tr>
 			<td>
 			Enter File Name:
 			</td>
 			<td>
 			<input type="text" name="fileName"></input>
 			</td>
 		</tr>
        <tr>
            <td><input name="file" type="file" /></td>
        </tr>
    
    </table>
    <br/><input type="submit" value="Upload" />
</form>
 

<div id="content">
</div>
<div id="confirmationMessage" ></div>

<div id="criteriaContainer" ></div>



<div id="viewtracelist"></div>
</body>
</html>