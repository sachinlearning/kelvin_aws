<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.heatmap.constants.AppConstant"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>TSP Kelvin</title>
<link rel = "icon" href ="${pageContext.request.contextPath}/resources/img/favicon.ico" type = "image/x-icon"> 
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- Global site tag (gtag.js) - Google Analytics -->
<script async src="https://www.googletagmanager.com/gtag/js?id=UA-168236605-1"></script>
<script>
  window.dataLayer = window.dataLayer || [];
  function gtag(){dataLayer.push(arguments);}
  gtag('js', new Date());
  gtag('config', 'UA-168236605-1');
</script>
</head>
<body style="background-image:url('${pageContext.request.contextPath}/resources/img/Analytics.png');">

	<div style="color: white" align="center">
		<h1 class="title-image-text">
			Password updated successfully. <a href="${AppConstant.LOGIN}">Click
				here</a> to Login.
		</h1>
	</div>

</body>
</html>