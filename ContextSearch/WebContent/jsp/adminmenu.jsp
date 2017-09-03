<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="<%=request.getContextPath()%>/css/styles.css"
	rel="stylesheet" type="text/css">
</head>
<body>
	<div id='cssmenu'>
		<ul>
			<li class='active '><a
				href="<%=request.getContextPath()%>/jsp/admin.jsp"><span>Home</span></a></li>

			<li class='active'><a
				href='<%=request.getContextPath()%>/jsp/viewarticles.jsp'>Articles<span></span></a>

			</li>
			<li class='has-sub '><a href='#'>Stopword Analysis<span></span></a>
				<ul>
					<li class='active '><a
						href="<%=request.getContextPath()%>/jsp/addStopword.jsp"><span>Add
								Stopword</span></a></li>
					<li class='active '><a
						href="<%=request.getContextPath()%>/jsp/viewstopwords.jsp"><span>View
								Stopword</span></a></li>
					<li class='active '><a
						href="<%=request.getContextPath()%>/jsp/removeStopword.jsp"><span>Remove
								Stopword</span></a></li>
				</ul></li>
			<li class='has-sub '><a href='#'>Data Cleaning<span></span></a>
				<ul>
					<li class='has-sub '><a
						href="<%=request.getContextPath()%>/jsp/viewcleanarticles.jsp"><span>View
								Clean Data</span></a></li>
					<li class='has-sub '><a
						href="<%=request.getContextPath()%>/jsp/viewtokens.jsp"><span>View
								Tokens</span></a></li>
				</ul></li>
			<li class='has-sub '><a href='#'>Frequency Computation<span></span></a>
				<ul>
					<li class='has-sub '><a
						href="<%=request.getContextPath()%>/jsp/viewFreqComputation.jsp"><span>View
								Frequency</span></a></li>

					<li class='has-sub '><a
						href="<%=request.getContextPath()%>/review/doFeatureVector.do"><span>Feature
								Computation</span></a></li>
					<li class='has-sub '><a
						href="<%=request.getContextPath()%>/jsp/viewFVComputation.jsp"><span>View
								FV</span></a></li>

				</ul></li>

			<li class='has-sub '><a href='#'>Log Likelihood<span></span></a>
				<ul>
					<li class='has-sub '><a
						href="<%=request.getContextPath()%>/review/doLogLikelihood.do"><span>Likelihood
						</span></a></li>
					<li class='has-sub '><a
						href="<%=request.getContextPath()%>/jsp/viewloglikelihood.jsp"><span>View
								Likelihood</span></a></li>
				</ul></li>

			<li class='has-sub '><a href='#'>Duplicate Articles<span></span></a>
				<ul>
					<li class='has-sub '><a
						href="<%=request.getContextPath()%>/jsp/articleSimilarRV.jsp"><span>RV
								Coeffcient</span></a></li>
					<li class='has-sub '><a
						href="<%=request.getContextPath()%>/jsp/articleGrouping.jsp"><span>CLustering
						</span></a></li>
				</ul></li>


			<li class='active'><a
				href="<%=request.getContextPath()%>/review/logout.do"><span>Logout</span></a></li>
		</ul>
	</div>
</body>
</html>