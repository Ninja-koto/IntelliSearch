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
		<i class="fa fa-check"> If you are interested to track your
			history please register (These are only for academic purpose. The
			information will be kept confidential). </i>

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

		<form action="<%=request.getContextPath()%>/review/registerUser.do"
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
				<div class="row">
					<section class="col col-6">
						<label class="input"> <input type="text" name="fatherName"
							placeholder="Father Name">
						</label>
					</section>
					<section class="col col-6">
						<label class="input"> <input type="text"
							name="fatherNumber" placeholder="Father Number">
						</label>
					</section>

					<section class="col col-6">
						<label class="input"> <input type="text"
							name="fatherEmail" placeholder="Father Email">
						</label>
					</section>
				</div>
			</fieldset>

			<fieldset>
				<div class="row">
					<section class="col col-6">
						<label class="input"> <input type="text" name="motherName"
							placeholder="Mother Name">
						</label>
					</section>
					<section class="col col-6">
						<label class="input"> <input type="text"
							name="motherNumber" placeholder="Mother Number">
						</label>
					</section>

					<section class="col col-6">
						<label class="input"> <input type="text"
							name="motherEmail" placeholder="Mother Email">
						</label>
					</section>
				</div>
			</fieldset>

			<fieldset>
				<div class="row">
					<section class="col col-6">
						<label class="input"> <input type="text"
							name="localGuardName" placeholder="Guardian Name">
						</label>
					</section>
					<section class="col col-6">
						<label class="input"> <input type="text"
							name="localGuardNumber" placeholder="Guardian Number">
						</label>
					</section>

					<section class="col col-6">
						<label class="input"> <input type="text"
							name="localGuardEmail" placeholder="Guardian Email">
						</label>
					</section>
				</div>
			</fieldset>


			<fieldset>
				<div class="row">

					<section class="col col-6">
						<label class="input"> <input type="text"
							name="challanNumber" placeholder="Challan Number">
						</label>
					</section>

					<section class="col col-6">
						<label class="input"> <input type="text" name="feePaid"
							placeholder="Fee Paid">
						</label>
					</section>
				</div>
			</fieldset>


			<fieldset>
				<div class="row">
					<section class="col col-6">
						<label class="input"> <input type="text"
							name="residentialAddress" placeholder="Residential Address">
						</label>
					</section>
					<section class="col col-6">
						<label class="input"> <input type="text"
							name="residenceStatus" placeholder="Residential Status">
						</label>
					</section>

					<section class="col col-6">
						<label class="input"> <input type="text"
							name="semesterMarks" placeholder="Semester Marks">
						</label>
					</section>
				</div>
			</fieldset>

			<fieldset>

				<section>
					<label>Enter Area of Interest Comma Separated</label>
					<textarea name="activity" class="form-control" rows="5" cols="40"
						placeholder="Activity">
					</textarea>
				</section>


				<section>
					<label class="select"> <select name="admissionType">
							<option value="" selected disabled>Admission Type</option>
							<option value="1">CET</option>
							<option value="2">COMEDK</option>
					</select> <i></i>
					</label>
				</section>


			</fieldset>


			<footer>
				<button type="submit" class="button">Submit</button>
			</footer>
		</form>

	</div>
</body>
</html>