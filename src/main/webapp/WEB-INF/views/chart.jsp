<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.heatmap.constants.AppConstant"%>
<%
		response.setHeader("pragma", "no-cache");
		response.setHeader("Cache-control", "no-cache, no-store, must-revalidate");
		response.setHeader("Expires", "0");
%>
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
<%-- <script src="${pageContext.request.contextPath}/resources/js/jquery.slim.js"></script> --%>
<script
	src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
<!-- Global site tag (gtag.js) - Google Analytics -->
<script async src="https://www.googletagmanager.com/gtag/js?id=UA-168236605-1"></script>
<script>
  window.dataLayer = window.dataLayer || [];
  function gtag(){dataLayer.push(arguments);}
  gtag('js', new Date());
  gtag('config', 'UA-168236605-1');
</script>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/taucharts.min.css">
	
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/loader.css">
<script src="${pageContext.request.contextPath}/resources/js/d3.min.js"
	type="text/javascript" charset="utf-8"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/taucharts.min.js"
	type="text/javascript"></script>
 <script src="${pageContext.request.contextPath}/resources/js/script.js" type="text/javascript"></script>
 <style>
	.flex-container {
	  display: flex;
	  width:76%;
	}
	
	.flex-container > div {
	  background-color: #f1f1f1;
	  margin: 5px;
	  padding: 20px;
	  font-size: 20px;
	  border-radius:5px;
	  width:25%;
	}
	.butns{
		width: 24%;
		text-align: center;
	}
	.btnSaveStyle{
		margin-bottom: 10px;;
	}
	
	
	</style>
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

	<link rel="stylesheet" type="text/css"
		href="${pageContext.request.contextPath}/resources/css/scatter.css">
	<link rel="stylesheet"
		href="${pageContext.request.contextPath}/resources/css/chartStyle.css">
	<script
		src="${pageContext.request.contextPath}/resources/js/scatter.js"></script>

	<!-- Bootstrap CSS -->
	<link rel="stylesheet"
		href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">

	<div class="container-fluid ">

		<div class="row">
			<div class="userheader">
				Hi ${username}
				<div class="headerright">
					<a style="color: white;" href="${AppConstant.LOGOUT}">Logout</a>
				</div>
				<div class="headerright">
					<a style="color: white; padding: 1em;"
						href="javascript:history.go(-1)">Back</a>
				</div>
			</div>
		</div>
		<div class="row shadow mb-2">
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
							<span class="circle prcsedbgcolr"
								style="background-color: #6c757d !important;">&nbsp;</span> <span
								class="ctitle upldTop">Upload File</span>
						</div>
						<div class="col-lg-4 col-md-4 col-6 text-center">
							<span class="circle bgcolr"
								style="background-color: #28a745 !important;">&nbsp;</span> <span
								class="ctitle dwnldTop">Display Heatmap</span>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="fullWidth">
				<div class="headerright mr-4">
					<button id="save"
						class="button button-primary btnSaveStyle flt-rght"
						style="cursor: pointer;">Save Chart</button>
				</div>
			</div>
		</div>

		<div align="center" class="loader"></div>
		<div id="loaderWrapper" class="loaderWrapper">
			<div class="spinner-border text-info centered" role="status">
				<span class="sr-only">Loading...</span>
			</div>
		</div>
		<div style="height: 490px;">
			<div id="scatter"></div>
		</div>

		<!-- Modal -->
		<div class="modal fade" id="myModal" role="dialog">
			<div class="modal-dialog">

				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-body">
						<p id="msg"></p>
					</div>
					<div class="modal-footer">
						<button type="button" id="nextPage" class="btn btn-default"
							data-dismiss="modal">Close</button>
					</div>
				</div>

			</div>
		</div>

		<!--Confirmation Modal -->
		<div class="modal fade" id="confirmationModal" role="dialog">
			<div class="modal-dialog">

				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-body">
						<p>Do you want to save the chart data?</p>
					</div>
					<div class="modal-footer" align="center">
						<button type="button" id="saveChart" class="btn btn-default"
							data-dismiss="modal">Yes</button>
						<button type="button" id="no" class="btn btn-default"
							data-dismiss="modal">No</button>
					</div>
				</div>

			</div>
		</div>
		
		<!-- <div class="foter">
			<button id="proceed"
				class="button button-primary headerright btnStyle cursr-point">
				Additional Reports and Dashboards</button>
		</div> -->
		<!-- <div class="row">
			<div class="fullWidth">
				<div class="headerright mr-4">
					<button id="proceed"
						class="button button-primary btnSaveStyle flt-rght"
						style="cursor: pointer;">Additional Reports and Dashboards</button>
				</div>
			</div>
		</div> -->
		<div class="row">
			<div class="flex-container">
				<div>1</div>
				<div>2</div>
				<div>3</div>  
				<div>4</div>  
			</div>
			<div class="butns">
				<button id="proceed" class="button button-primary btnSaveStyle " >Additional Reports and Dashboards</button>
				<button id="proceed" class="button button-primary btnSaveStyle " >Additional Reports and Dashboards</button>
				<button id="proceed" class="button button-primary btnSaveStyle " >Additional Reports and Dashboards</button>
			</div>
		</div>
	</div>
</body>
</html>