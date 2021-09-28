<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.heatmap.constants.AppConstant"%>
<%-- <%
	if (session.getAttribute(AppConstant.USEREMAIL) == null){
		response.sendRedirect("/login");
		response.setHeader("pragma", "no-cache");
		response.setHeader("Cache-control", "no-cache, no-store, must-revalidate");
		response.setHeader("Expires", "0");
	}
%> --%>
<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="robots" content="noindex, nofollow">
<title>TSP Kelvin</title>
<link rel = "icon" href ="${pageContext.request.contextPath}/resources/img/favicon.ico" type = "image/x-icon"> 
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- Global site tag (gtag.js) - Google Analytics -->
<script async
	src="https://www.googletagmanager.com/gtag/js?id=UA-168236605-1"></script>
<script>
  window.dataLayer = window.dataLayer || [];
  function gtag(){dataLayer.push(arguments);}
  gtag('js', new Date());
  gtag('config', 'UA-168236605-1');
</script>
</head>
<body>
	<!-- Required meta tags -->
	<meta charset="utf-8">
	<meta name="viewport"
		content="width=device-width, initial-scale=1, shrink-to-fit=no">

	<!-- Fonts -->
	<link rel="dns-prefetch" href="https://fonts.gstatic.com">
	<link
		href="https://fonts.googleapis.com/css?family=Raleway:300,400,600"
		rel="stylesheet" type="text/css">
	<link rel="stylesheet"
		href="${pageContext.request.contextPath}/resources/css/frontPageStyle.css">
	<!-- Bootstrap CSS -->
	<link rel="stylesheet"
		href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">


	<div class="container-fluid h-100">

		<div class="row use-none">
			<div class="userheader">
				Hi ${username}
				<div class="float-right cursr-point">
					<a style="color: white;" href="${AppConstant.LOGOUT}">Logout</a>
				</div>
			</div>
		</div>
		<div class="row shadow use-none">
			<div class="header">
				<a href="#" class="navbar-brand"> <img
					src="${pageContext.request.contextPath}/resources/img/logo.png"
					height="30vh" alt="TSP">
				</a>
				<div class="float-right stepguide">
					<span>The 3-Step Guide</span>
				</div>
			</div>
		</div>

		<div class="row h-header use-none">
			<div class="col-sm-12 align-self-center">
				<div class="text-center font-17">
					<!-- content -->

					<label>Follow a simple 3 steps guide to download your SAP
						ECC data and upload it to TSP Kelvin to review your Business
						Process Heatmap </label>

					<div class="row text-center text-lg-left p-lr-80 m-t-30">
						<div class="lineprnts">
							<div class="line"></div>
						</div>
						<div class="col-lg-4 col-md-4 col-6 text-center">
							<span class="circle">1</span>
						</div>
						<div class="col-lg-4 col-md-4 col-6 text-center">
							<span class="circle">2</span>
						</div>
						<div class="col-lg-4 col-md-4 col-6 text-center">
							<span class="circle">3</span>
						</div>
					</div>
					<div class="row text-center text-lg-left p-lr-80 m-t-30">
						<div class="col-lg-4 col-md-4 col-6">
							<img class="img-fluid img-thumbnail img-bg"
								src="${pageContext.request.contextPath}/resources/img/laptps1.png"
								alt="">
						</div>
						<div class="col-lg-4 col-md-4 col-6">
							<img class="img-fluid img-thumbnail img-bg"
								src="${pageContext.request.contextPath}/resources/img/laptps2.png"
								alt="">
						</div>
						<div class="col-lg-4 col-md-4 col-6">
							<img class="img-fluid img-thumbnail img-bg"
								src="${pageContext.request.contextPath}/resources/img/laptps3.png"
								alt="">
						</div>
					</div>
					<div class="row text-center text-lg-left m-b-30 p-lr-80">
						<div class="col-lg-4 col-md-4 col-6 text-center">
							<span class="fontbold">Download Template</span>
						</div>
						<div class="col-lg-4 col-md-4 col-6 text-center">
							<span class="fontbold">Upload File</span>
						</div>
						<div class="col-lg-4 col-md-4 col-6 text-center">
							<span class="fontbold">Display Heatmap</span>
						</div>
					</div>
					<div>&nbsp</div>
					<!-- content end -->
				</div>
			</div>
		</div>
		<div class="foter">
			<a href="${AppConstant.TEMPLATEDOWNLOAD}"><button id="proceedBtn"
					class="button button-primary float-right btnStyle cursr-point">
					Proceed</button></a>
		</div>
	</div>
</body>
</html>
