<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Server Error</title>
<style>
@font-face {
	font-family: "Pretendard";
	font-style: normal;
	font-weight: 400;
	src: url("../../fonts/Pretendard/Pretendard-Medium.ttf")
		format("truetype");
}

* {
	font-family: "Pretendard";
}

p {
	font-size: 15pt;
}

h1 {
	margin-top: 30px;
}

a:visited {
	color: black;
}
</style>
</head>
<body>
	<div style="margin-top: 20px; padding: 20px;">
		<img src="${pageContext.request.contextPath}/images/logo/logo.svg"
			width="400" />

		<h1>죄송합니다.</h1>
		<p>
			서비스 이용에 불편을 드려서 죄송합니다. <br>시스템 에러가 발생하여 페이지를 표시할 수 없습니다. <br>관리자에게
			문의하시거나 잠시 후 다시 시도하세요.
		</p>


		<a href="${pageContext.request.contextPath}/main.trd">메인페이지로 이동</a>
	</div>
</body>
</html>