<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>TSP Kelvin</title>
<link rel = "icon" href ="${pageContext.request.contextPath}/resources/img/favicon.ico" type = "image/x-icon"> 
<meta name="viewport" content="width=device-width, initial-scale=1">
<script
	src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/taucharts.min.css">
<script src="${pageContext.request.contextPath}/resources/js/d3.min.js"
	type="text/javascript" charset="utf-8"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/taucharts.min.js"
	type="text/javascript"></script>
<!-- Global site tag (gtag.js) - Google Analytics -->
<script async src="https://www.googletagmanager.com/gtag/js?id=UA-168236605-1"></script>
<script>
  window.dataLayer = window.dataLayer || [];
  function gtag(){dataLayer.push(arguments);}
  gtag('js', new Date());
  gtag('config', 'UA-168236605-1');
</script>
</head>
<body>
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

	<!-- Bootstrap CSS -->
	<link rel="stylesheet"
		href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
	<div class="container-fluid ">
		<div class="row shadow mb-2">
			<div class="header">
				<a href="#" class="navbar-brand"> <img
					src="${pageContext.request.contextPath}/resources/img/logo.png"
					height="30vh" alt="TSP">
				</a>
			</div>
		</div>
		<div id="loaderWrapper" class="loaderWrapper">
			<div class="spinner-border text-info centered" role="status">
				<span class="sr-only">Loading...</span>
			</div>
		</div>
		<div style="height: 490px;">
			<div id="scatter"></div>
		</div>
	</div>

	<script>
		var url_string = window.location.href; //window.location.href
		var url = new URL(url_string);
		var uniqueId = url.searchParams.get("uniqueId");
		(function() {
			$('.text').text('loading . . .');
			$.ajax({
				type : "GET",
				url : 'api/getMap',
				contentType : false, // Not to set any content header  
				processData : false, // Not to process data
				data : 'uniqueId=' + uniqueId,
				success : function(data) {
					$(".loader").hide();
					created3Chart(data);
					$(".tau-chart__filter__wrap:gt(1)").hide();
					/* $(".tau-chart__filter__wrap:eq(0) text:eq(0)").text("Max");
					$(".tau-chart__filter__wrap:eq(0) text:eq(1)").text("Min");
					$(".tau-chart__filter__wrap:eq(1) text:eq(0)").text("Max");
					$(".tau-chart__filter__wrap:eq(1) text:eq(1)").text("Min"); */
					$(".tau-chart__filter__wrap:eq(0) text:eq(0)").hide();
					$(".tau-chart__filter__wrap:eq(0) text:eq(1)").hide();
					$(".tau-chart__filter__wrap:eq(1) text:eq(0)").hide();
					$(".tau-chart__filter__wrap:eq(1) text:eq(1)").hide();
					$(".tau-chart__legend__title:eq(1)").text("Scale Count");
					$(".tau-chart__legend__title:eq(2)").text("Scale Time");
				},
				error : function(err) {
					console.log(err);
				}
			});

		})();
		function created3Chart(jdata) {
			$("#loaderWrapper").hide();
			var chart = new Taucharts.Chart(
					{
						guide : {
							x : {
								label : {
									text : "Time"
								}
							},
							y : {
								label : {
									text : "Count"
								}
							},
							color : {
								label : "Business Process Criticality Rating",
								brewer : [ 'color-a', 'color-b', 'color-c' ]
							}
						},
						data : jdata,
						type : 'scatterplot',
						x : 'guiTime',
						y : 'trxCount',
						color : 'colorCode',
						plugins : [
								Taucharts.api.plugins
										.get('tooltip')
										(
												{
													fields : [
															"trxCode",
															"description",
															"individualPercentageForTrxCount",
															"cumlativeValueForTrxCount",
															"individualPercentageForGuiTime",
															"cumlativeValueForGuiTime",
															"colorCode", ],
													formatters : {
														trxCode : {
															label : "Business Transaction Code"
														},
														description : {
															label : "Business Process Description"
														},
														individualPercentageForTrxCount : {
															label : "Individual Percentage Rating for the Count",
															format : function(n) {
																return (n + "%");
															}
														},
														cumlativeValueForTrxCount : {
															label : "Cumulative Percentage Rating for the Count",
															format : function(n) {
																return (n + "%");
															}
														},
														individualPercentageForGuiTime : {
															label : "Individual Percentage Rating for the Time",
															format : function(n) {
																return (n + "%");
															}
														},
														cumlativeValueForGuiTime : {
															label : "Cumulative Percentage Rating for the Time",
															format : function(n) {
																return (n + "%");
															}
														},
														colorCode : {
															label : "Business Criticality Rating"
														}
													}
												}),
								Taucharts.api.plugins.get('legend')(),
								Taucharts.api.plugins.get('quick-filter')() ],
					//size : 'count'
					});
			$('#scatter')[0].innerHTML = ''
			chart.renderTo('#scatter');
		};
	</script>
</body>

</html>