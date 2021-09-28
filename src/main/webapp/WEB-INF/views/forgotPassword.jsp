<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.heatmap.constants.AppConstant"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>TSP Kelvin</title>
<link rel = "icon" href ="${pageContext.request.contextPath}/resources/img/favicon.ico" type = "image/x-icon"> 
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!--===============================================================================================-->
<link rel="icon" type="image/png"
	href="${pageContext.request.contextPath}/resources/img/favicon.ico" />
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/vendor/bootstrap/css/bootstrap.min.css">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/fonts/Linearicons-Free-v1.0.0/icon-font.min.css">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/vendor/animate/animate.css">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/vendor/css-hamburgers/hamburgers.min.css">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/vendor/animsition/css/animsition.min.css">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/vendor/select2/select2.min.css">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/vendor/daterangepicker/daterangepicker.css">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/util.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/main.css">
<!--===============================================================================================-->
<!-- Global site tag (gtag.js) - Google Analytics -->
<script async src="https://www.googletagmanager.com/gtag/js?id=UA-168236605-1"></script>
<script>
  window.dataLayer = window.dataLayer || [];
  function gtag(){dataLayer.push(arguments);}
  gtag('js', new Date());
  gtag('config', 'UA-168236605-1');
</script>
</head>
<body style="background-color: #666666;">

	<div class="limiter">
		<div class="container-login100">
			<div class="wrap-login100">
				<form class="login100-form validate-form" action="sendOtp"
					method="POST">
					<div style="text-align: center">
						<img class="kelvin-login-image"
							src="${pageContext.request.contextPath}/resources/img/Picture1.png">
					</div>
					<span class="login100-form-title p-b-43"> Forgot Password
					</span>

					<div style="color: red" align="center">
						<b>${error}</b>
					</div>
					<div class="wrap-input100 validate-input"
						data-validate="Valid email is required: ex@abc.xyz">
						<input class="input100" type="text" name="email" required>
						<span class="focus-input100"></span> <span class="label-input100">Email</span>
					</div>


					<div class="flex-sb-m w-full p-t-3 p-b-32">
						<div>
							<a href="${AppConstant.LOGIN}" class="txt1">
										Go to login page </a>
						</div>
					</div>


					<div class="container-login100-form-btn">
						<button class="login100-form-btn">Submit</button>
					</div>


				</form>

				<div class="login100-more"
					style="background-image: url('${pageContext.request.contextPath}/resources/img/Analytics.png');">
					<h2 class="title-image-text">Hassle Free Journey to S/4HANA
						with TSP Kelvin</h2>
				</div>
			</div>
		</div>
	</div>
	<!--===============================================================================================-->
	<script
		src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
	<!--===============================================================================================-->
	<script src="${pageContext.request.contextPath}/resources/js/main.js"></script>
	<!--===============================================================================================-->
</body>
</html>