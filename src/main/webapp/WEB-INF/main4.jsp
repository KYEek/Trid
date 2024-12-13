<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>TRID</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/header/hamburger.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/header/header.css">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.1/font/bootstrap-icons.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap-4.6.2-dist/css/bootstrap.min.css">

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script src="${pageContext.request.contextPath}/bootstrap-4.6.2-dist/js/bootstrap.bundle.min.js"></script>

</head>
<div class="container-fluid m-0 p-0">
	<div class="header">
		<!-- 헤더 카테고리 메뉴 버튼 -->
		<div class="header-menu-container">
			<div class="menu-trigger">
				<span></span> <span></span>
			</div>
		</div>

		<!-- 로고 -->
		<div class="header-logo-container">
			<div class="header-logo">
				<img src="./images/logo/logo_white.svg" />
			</div>
		</div>

		<!-- 검색창 -->
		<div class="header-search-container">
			<div class="search-bar">
				<a class="search-bar-link">검색</a>
			</div>
		</div>

		<!-- 네비게이션 -->
		<div class="header-links">
			<a class="header-link">로그인</a> <a class="header-link">바스켓백</a>
		</div>
	</div>

	<div id="img-container">
		<img src="${pageContext.request.contextPath}/images/main_image_1.jpg" />
	</div>

	<footer> </footer>
</div>

<script>
	let burger = $('.menu-trigger');

	burger.each(function(index) {
		$(this).on('click', function(e) {
			e.preventDefault();
			$(this).toggleClass('active-' + (index + 1));
		});
	});
</script>
</body>
</html>