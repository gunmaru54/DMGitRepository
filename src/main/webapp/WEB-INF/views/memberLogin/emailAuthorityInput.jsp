<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description"
	content="Creative - Bootstrap 3 Responsive Admin Template">
<meta name="author" content="GeeksLabs">
<meta name="keyword"
	content="Creative, Dashboard, Admin, Template, Theme, Bootstrap, Responsive, Retina, Minimal">
<link rel="shortcut icon" href="img/favicon.png">

<!-- Bootstrap CSS -->
<link href="/resources/css/bootstrap.min.css" rel="stylesheet">
<!-- bootstrap theme -->
<link href="/resources/css/bootstrap-theme.css" rel="stylesheet">
<!--external css-->
<!-- font icon -->
<link href="/resources/css/elegant-icons-style.css" rel="stylesheet" />
<link href="/resources/css/font-awesome.min.css" rel="stylesheet" />
<!-- Custom styles -->
<link href="/resources/css/style.css" rel="stylesheet">
<link href="/resources/css/style-responsive.css" rel="stylesheet" />

<title>cplay 이메일 인증</title>
</head>
<body class="login-img3-body">
	<div class="container">
		<form class="login-form" action="join_injeung.do${dice}" method="post">
			<div class="login-wrap">
				<div class="form-group col-lg-12">
					<label for="mem_email" class="control-label">인증 번호<span
						class="required">*</span></label> <input class="form-control "
						id="email_injeung" name="email_injeung" type="number" placeholder="인증번호"/>
						
					<!-- <div class="eheck_font" id="email_check"></div> -->
				</div>
				<div class="form-group">
					<button class="btn btn-primary btn-lg btn-block" type="submit">이메일 인증</button>
					<button class="btn btn-info btn-lg btn-block" id="memberJoincancelBtn1" type="button">취소</button>
				</div>
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				<input type="hidden" id="mem_email" name="mem_email" value='<c:out value="${mem_email}"/>'>
		</form>
	</div>

	<!-- javascripts -->
	<script src="/resources/js/jquery.js"></script>
	<script src="/resources/js/bootstrap.min.js"></script>
	<!--custome script for all page-->
	<script src="/resources/js/scripts.js"></script>
 	<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>	
	
	<script type="text/javascript">
	$(document).ready(function () {
  		$("#memberJoincancelBtn1").on("click", function() {
			self.location = "/cplay/mlist";
		});
	});
  </script>
</body>
</html>