<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.heatmap.constants.AppConstant"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="robots" content="noindex, nofollow">
<title>TSP Kelvin</title>
<link rel = "icon" href ="${pageContext.request.contextPath}/resources/img/favicon.ico" type = "image/x-icon"> 
<meta name="viewport" content="width=device-width, initial-scale=1">
<script
	src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
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
		href="${pageContext.request.contextPath}/resources/css/uploadFileStyle.css">
	<script src="${pageContext.request.contextPath}/resources/js/script.js"></script>
	<!-- Bootstrap CSS -->
	<link rel="stylesheet"
		href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">


	<div class="container-fluid h-100">

		<div class="row use-none">
			<div class="userheader">
				Hi ${username}
				<div class="headerright cursr-point">
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
				<div class="header-right">
					<div class="row text-center text-lg-left p-t-6">
						<div class="lineprnts">
							<div class="line"></div>
						</div>
						<div class="col-lg-4 col-md-4 col-6 text-center">
							<span class="circle prcsedbgcolr"
								style="background-color: #6c757d !important;">&nbsp;</span> <span
								class="ctitle dwnldTop">Download Template</span>
						</div>
						<div class="col-lg-4 col-md-4 col-6 text-center">
							<span class="circle bgcolr"
								style="background-color: #28a745 !important;">&nbsp;</span> <span
								class="ctitle upldTop">Upload File</span>
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
					<p class="upload-txt text-center">Upload</p>
					<div class="uploadWrapper text-center m-t-2">
						<div align="center" class="loader"></div>
						<div id="formwrapper" class="formwrapper">
							<form id="form" style="border: 2px dashed #6c757d;">
								<!-- <i class="fa fa-upload faa-burst animated fa-4x"></i>-->
								<img text-center
									src="${pageContext.request.contextPath}/resources/img/file-upload.png">
								<p class="file-type-desc">
									( <font color="red">*</font>file extension allowed: <strong>.xlsx
										and .xls </strong>)
								</p>

								<div class="drop-txt">
									Click to upload </br> or </br> Drop Your File Here
								</div>
								<input id="fileUpload" class="cursr-point" type="file"
									name="file" required
									accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel" />
								<div class="drag-text"></div>
							</form>
						</div>
					</div>
					<h5 id="uploadStatus"
						class="uploadStatus text-center m-t-30 user-select-none">Please
						choose file</h5>
				</div>
			</div>
		</div>

		<div class="foter">
			<button id="proceedBtn"
				class="button button-primary headerright btnStyle cursr-point">
				Generate Dashboard</button>
		</div>

	</div>
	<script type="text/javascript">
		
	</script>
</body>
</html>
