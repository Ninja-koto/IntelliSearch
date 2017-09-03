<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="org.springframework.web.servlet.ModelAndView"%>
<%@page import="org.springframework.web.servlet.ModelAndView"%>
<%@page import="java.util.List"%>
<%@page import="com.model.AJAXResponse,java.util.List,com.model.Message"%>
<%@page import="com.constants.ContextualConstantsIF"%>
<%@page import="com.model.SearchObj"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>IntelliSearch</title>

<script type="text/javascript">
	validatesearch = function() {
		var words = document.getElementById('query').value;

		if (words <= 0) {
			alert("Please Enter a Value for Search");
			return false;
		}
	}
</script>

<style>
div { width: 100%; }
.success { background-color: #cc0000; }
</style>

</head>
<body>

	<jsp:include page="/jsp/usermenu.jsp"></jsp:include>


	<form action="<%=request.getContextPath()%>/review/doSearchArticles.do"
		method="post">


		<table>
			<tr>
				<td><label>Search Contents:</label></td>
				<td><input name="query" id="query" type="text" size="100"
					maxlength="100" /></td>
			</tr>
			
			<tr>
				<td><label>Enter Authors (Optional Comma Separated):</label></td>
				<td><input name="authors" id="authors" type="text" size="100"
					maxlength="100" /></td>
			</tr>
			
			<tr>
				<td><label>Enter Publisher:</label></td>
				<td><input name="publisher" id="publisher" type="text" size="100"
					maxlength="100" /></td>
			</tr>
			
			<tr>
				<td><label>Enter Keywords:(Optional Comma Separated)</label></td>
				<td><input name="keywords" id="keywords" type="text" size="100"
					maxlength="100" /></td>
			</tr>
			
			<tr>
				<td><label>Enter Area of interest:(Optional Comma Separated)</label></td>
				<td><input name="interestArea" id="interestArea" type="text" size="100"
					maxlength="100" /></td>
			</tr>

			

			<tr>
				<td><input type="submit" value="Search"
					 /></td><!-- onclick="validatesearch()" -->
			</tr>

		</table>

	</form>


	<%
		AJAXResponse ajaxResponse = (AJAXResponse) request
			.getAttribute(ContextualConstantsIF.Keys.OBJ);
			if (null == ajaxResponse) {

			} else {
		boolean status = ajaxResponse.isStatus();
	%>
	<%
		if (!status) {
				List<Message> msg = ajaxResponse.getEbErrors();
	%>
	<%
		for (int i = 0; i < msg.size(); i++) {
					Message tempMsg = msg.get(i);
	%>

	<div class="errMsg">
		<%=tempMsg.getErrMessage()%>
	</div>

	<%
		}

			}

			if (status) {
	%>





	<%
		List<SearchObj> results = (List<SearchObj>) ajaxResponse
						.getModel();

				if (null == results || results.isEmpty()) {

				} else {

					for (int i=0; i<1;i++) {
	%>

	<div class="results">
		<table>
			<tr>
				<td><a href="<%=results.get(i).getUrl()%>" target="_blank"><%=results.get(i).getTitle()%></a></td>
			</tr>

			<!--  <tr>
				<%=results.get(i).getDesc()%>
			</tr>-->
			<tr>
			<td><font color="blue" size="4" >Feature Vector</font></td>
				<td><%=results.get(i).getFeatureVector()%></td>
			</tr>
			<tr>
			<td><font color="blue" size="4" >Keywords</font></td>
				<td><%=results.get(i).getKeywords()%></td>
			</tr>
			<tr>
			<td><font color="blue" size="4" >Author List</font></td>
				<td><%=results.get(i).getAuthorList()%></td>
			</tr>
			
			<tr>
			<td><font color="blue" size="4" >Publisher</font></td>
				<td><%=results.get(i).getPublisher()%></td>
			</tr>
			
			<tr>
					<td><span class="heading"><a class="blueBack"
							href="<%=request.getContextPath()%>/review/download.do?articleName=<%=results.get(i).getArticleId()%>"
							target="_blank">Download</a></span></td>
				</tr>
		</table>

	</div>

	<%
		}%>
		<p><p>
		<div class="success"><h1 align="center">RECOMENDATION: </h1></div>
		
		
		
		
		<% 
					
					//for (SearchObj googleResult : results) {
						for (int i=1; i<results.size() ; i++) {
						%>
<div class="results">
		<table>
			<tr>
				<td><a href="<%=results.get(i).getUrl()%>" target="_blank"><%=results.get(i).getTitle()%></a></td>
			</tr>

			<!--  <tr>
				<%=results.get(i).getDesc()%>
			</tr>-->
			<tr>
			<td><font color="blue" size="4" >Feature Vector</font></td>
				<td><%=results.get(i).getFeatureVector()%></td>
			</tr>
			<tr>
			<td><font color="blue" size="4" >Keywords</font></td>
				<td><%=results.get(i).getKeywords()%></td>
			</tr>
			<tr>
			<td><font color="blue" size="4" >Author List</font></td>
				<td><%=results.get(i).getAuthorList()%></td>
			</tr>
			<tr>
			<td><font color="blue" size="4" >Publisher</font></td>
				<td><%=results.get(i).getPublisher()%></td>
			</tr>
			
			<tr>
					<td><span class="heading"><a class="blueBack"
							href="<%=request.getContextPath()%>/review/download.do?articleName=<%=results.get(i).getArticleId()%>"
							target="_blank">Download</a></span></td>
				</tr>
		</table>

	</div>
				<%}}} }%>

</body>
</html>