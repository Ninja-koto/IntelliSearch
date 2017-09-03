<!DOCTYPE html>
<html>
<head>
<%@page import="com.model.RegisterVerifyMsgs"%>
<%@page import="com.constants.ContextualConstantsIF"%>
<title>Register</title>

<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=1.0">

<link rel="stylesheet" href="<%=request.getContextPath()%>/css/demo.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/sky-forms.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/errormsg.css">
<!--[if lt IE 9]>
			<link rel="stylesheet" href="css/sky-forms-ie8.css">
		<![endif]-->

<!--[if lt IE 10]>
			<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
			<script src="js/jquery.placeholder.min.js"></script>
		<![endif]-->
<!--[if lt IE 9]>
			<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
			<script src="js/sky-forms-ie8.js"></script>
		<![endif]-->
<script>
	function preventBack() {
		window.history.forward();
	}
	setTimeout("preventBack()", 0);
	window.onunload = function() {
		null
	};
</script>
</head>
<body class="bg-cyan">

	<div class="isa_success">
		<i class="fa fa-check"> Teacher Login. </i>

	</div>

	<%
		RegisterVerifyMsgs loginVerify = (RegisterVerifyMsgs) request
				.getAttribute(ContextualConstantsIF.Keys.OBJ);
		if (null == loginVerify) {

		} else {
			String errMsg = loginVerify.getSeverMessage();
			if (null == errMsg) {

			} else {
	%>

	<div class="isa_error" name="userNameErr">
		<i class="fa fa-times-circle"></i>
		<%=errMsg%>
	</div>
	<%
		}
		}
	%>

	<%
		if (null == loginVerify) {

		} else {
			String sucessMsg = loginVerify.getSucessMsg();
			if (null == sucessMsg) {

			} else {
	%>

	<div class="isa_success">
		<i class="fa fa-check"></i>
		<%=sucessMsg%>
	</div>
	<%
		}
		}
	%>







	<div class="body body-s">

		<form action="<%=request.getContextPath()%>/review/registerTeacher.do"
			method="post" class="sky-form">
			<header>
				Registration form <a
					href="<%=request.getContextPath()%>/jsp/welcome.jsp" class="button">Home</a>
			</header>
			<fieldset>

				<section>
					<label class="input"> <i class="icon-append icon-user"></i>
						<input type="text" name="USN" placeholder="USN"> <b
						class="tooltip tooltip-bottom-right">Enter USN</b>
					</label>
				</section>

				<section>
					<label class="input"> <i class="icon-append icon-user"></i>
						<input type="text" name="name" placeholder="Name"> <b
						class="tooltip tooltip-bottom-right">Enter Name</b>
					</label>
				</section>

				<section>
					<label class="input"> <i
						class="icon-append icon-envelope-alt"></i> <input type="text"
						name="email" placeholder="Email address"> <b
						class="tooltip tooltip-bottom-right">Email</b>
					</label>
				</section>

				<section>
					<label class="input"> <i class="icon-append icon-lock"></i>
						<input type="password" name="password" placeholder="Password">
						<b class="tooltip tooltip-bottom-right">Only latin characters
							and numbers</b>
					</label>
				</section>


			</fieldset>

			<fieldset>

				<section>
					<label>Enter Address</label>
					<textarea name="address" class="form-control" rows="5" cols="40"
						placeholder="Address">
					</textarea>
				</section>

				<section>
					<label class="input"> <i class="icon-append icon-user"></i>
						<input type="text" name="city" placeholder="City"> <b
						class="tooltip tooltip-bottom-right">City</b>
					</label>
				</section>

				<section>
					<label class="input"> <i class="icon-append icon-user"></i>
						<input type="text" name="state" placeholder="State"> <b
						class="tooltip tooltip-bottom-right">State</b>
					</label>
				</section>

				<section>
					<label class="input"> <i class="icon-append icon-user"></i>
						<input type="password" name="pinCode" placeholder="PINCODE">
						<b class="tooltip tooltip-bottom-right">Only latin characters
							and numbers</b>
					</label>
				</section>

				<section>
					<label class="select"> <select name="gender">
							<option value="0" selected disabled>Gender</option>
							<option value="Male">Male</option>
							<option value="Female">Female</option>
					</select> <i></i>
					</label>
				</section>


			</fieldset>

			
				<fieldset>

				<section>
					<label class="input"> <i class="icon-append icon-user"></i>
						<input type="text" name="degree" placeholder="Degree"> <b
						class="tooltip tooltip-bottom-right">Enter Degree</b>
					</label>
				</section>

				<section>
					<label class="input"> <i class="icon-append icon-user"></i>
						<input type="text" name="specification" placeholder="Specification"> <b
						class="tooltip tooltip-bottom-right">Enter Specification</b>
					</label>
				</section>

				<section>
					<label class="input"> <i
						class="icon-append icon-envelope-alt"></i> <input type="text"
						name="college" placeholder="College"> <b
						class="tooltip tooltip-bottom-right">College</b>
					</label>
				</section>

				<section>
					<label class="input"> <i class="icon-append icon-lock"></i>
						<input type="text" name="department" placeholder="Department">
						<b class="tooltip tooltip-bottom-right">Only latin characters</b>
					</label>
				</section>


			</fieldset>
			

			
			<fieldset>

				<section>
					<label>Enter Subjects Comma Separated</label>
					<textarea name="subjects" class="form-control" rows="5" cols="40"
						placeholder="Subjects">
					</textarea>
				</section>


			</fieldset>


			<footer>
				<button type="submit" class="button">Submit</button>
			</footer>
		</form>

	</div>
</body>
</html>