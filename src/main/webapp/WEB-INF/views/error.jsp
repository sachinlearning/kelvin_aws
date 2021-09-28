<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css"
	rel="stylesheet" id="bootstrap-css">
<script
	src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
<style>
body {
    background: #dedede;
}
.page-wrap {
    min-height: 100vh;
}
</style>
</head>
<body>

	<!------ Include the above in your HEAD tag ---------->

	<div class="page-wrap d-flex flex-row align-items-center">
		<div class="container">
			<div class="row justify-content-center">
				<div class="col-md-12 text-center">
					<div class="mb-4 lead">Something went wrong</div>
					<a href="login" class="btn btn-link">Back to Home</a>
				</div>
			</div>
		</div>
	</div>
</body>
</html>