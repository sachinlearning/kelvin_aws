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
<link rel = "icon" href ="https://tspkelvin.com/resources/img/favicon.ico" type = "image/x-icon"> 
<meta name="viewport" content="width=device-width, initial-scale=1">
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.slim.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<!-- Global site tag (gtag.js) - Google Analytics -->
<script async
	src="https://www.googletagmanager.com/gtag/js?id=UA-168236605-1"></script>
<script>
	window.dataLayer = window.dataLayer || [];
	function gtag() {
		dataLayer.push(arguments);
	}
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
		href="${pageContext.request.contextPath}/resources/css/templateDownloadStyle.css">


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
				<div class="float-right cursr-point">
					<a style="color: white; padding: 1em;"
						href="javascript:history.go(-1)">Back</a>
				</div>
			</div>
		</div>
		<div class="row shadow use-none">
			<div class="header">
				<a href="#" class="navbar-brand"> <img
					src="${pageContext.request.contextPath}/resources/img/logo.png"
					height="30vh" alt="TSP">
				</a>
				<div class="float-right header-width">
					<div class="row text-center text-lg-left p-t-6">
						<div class="lineprnts">
							<div class="line"></div>
						</div>
						<div class="col-lg-4 col-md-4 col-6 text-center">
							<span class="circle bgcolr"
								style="background-color: #28a745 !important;">&nbsp;</span> <span
								class="ctitle dwnldTop">Download Template</span>
						</div>
						<div class="col-lg-4 col-md-4 col-6 text-center">
							<span class="circle">&nbsp;</span> <span class="ctitle upldTop">Upload
								File</span>
						</div>
						<div class="col-lg-4 col-md-4 col-6 text-center">
							<span class="circle">&nbsp;</span> <span class="ctitle dwnldTop">Display
								Heatmap</span>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- <div class="content" id="content">
      <div class="conatainer">dd</div>
    </div> -->
		<div class="row h-header use-none">
			<div class="col-sm-12 align-self-center">
				<div class="text-center">
					<!-- content -->

					<div class="row text-center text-lg-left p-lr-80">
						<div class="col-lg-5 col-md-5 col-5 text-center"
							style="background-color: #f2f2f2; height:233px; margin-top: 4%;">
							<p class="video-desc">Download the excel template from the
								link below. Please use this template as a reference for your
								upload file. You may also use the standard SAP extract file from
								your SAP ECC system to upload in the next step. Please refer to
								the video link for help on extraction process.</p>
							<a href="${pageContext.request.contextPath}/resources/sample/TSPKelvin_Template.xlsx">
								<button class="button button-primary btnTempStyle " style="position: static;">Download Template</button>
							</a>
							<a href="${pageContext.request.contextPath}/resources/sample/sample.xlsx">
								<button class="button button-primary btnTempStyle bg-grey" style="position: static;">Test with Sample Data</button>
							</a>
						</div>
						<div class="col-lg-7 col-md-7 col-6 text-center">
							<video controls
								poster="${pageContext.request.contextPath}/resources/img/kelvinthumbnail.png"
								width="560" height="320">
								<source
									src="${pageContext.request.contextPath}/resources/video/TSPKelvin.webm"
									type="video/webm">

								Sorry, your browser doesn't support embedded videos.
							</video>
						</div>

					</div>
					<!-- content end -->
				</div>
			</div>
		</div>
		<div class="foter">
			<a href="${AppConstant.UPLOADFILE}"><button id="proceedBtn"
					class="button button-primary float-right btnStyle cursr-point">
					Proceed</button></a>
		</div>

	</div>
</body>
</html>
