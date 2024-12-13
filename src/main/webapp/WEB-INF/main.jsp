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
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/mainPage/mainpage.css">
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

	<div id="carouselExampleControls" class="carousel slide"
		data-ride="carousel">
		<div class="carousel-inner">
			<div class="carousel-item active fullscreen group_list">
				<ul class="h-100 w-100 group_ul">
					<li class="h-100 w-100"><a><img
							src="images/mainPage/women/image-landscape-64243cff-b87b-4267-81c5-cfea3f1f7455-default_0.jpg"></a></li>
					<li class="h-100 w-100"><a><img
							src="images/mainPage/women/image-landscape-default-fill-ee6c3f0b-60c2-4f63-b677-ff142edc747a-default_0.jpg"></a></li>
					<li class="h-100 w-100"><a><img
							src="images/mainPage/women/image-landscape-fill-015914f5-1fc2-49d1-82b1-923e1e0e5764-default_0.jpg"></a></li>
					<li class="h-100 w-100"><a><img
							src="images/mainPage/women/image-landscape-fill-0bffae4e-6483-4a81-9068-5050469498d0-default_0.jpg"></a></li>
					<li class="h-100 w-100"><a><img
							src="images/mainPage/women/image-landscape-fill-11a2dd9b-3409-46a3-a246-4a231c97e324-default_0.jpg"></a></li>
					<li class="h-100 w-100"><a><img
							src="images/mainPage/women/image-landscape-fill-684d0f95-30fb-49aa-aa66-f37f86260128-default_0.jpg"></a></li>
					<li class="h-100 w-100"><a><img
							src="images/mainPage/women/image-landscape-fill-6b7cb60d-0126-4a6c-8f20-127d0ccc4ccc-default_0.jpg"></a></li>
				</ul>
			</div>
			<div class="carousel-item fullscreen group_list">
				<ul class="h-100 w-100 group_ul">
					<li class="h-100 w-100"><a><img
							src="images/mainPage/men/image-landscape-03ceec99-c2ba-42f0-b7aa-f97e27744f85-default_0.jpg"></a></li>
					<li class="h-100 w-100"><a><img
							src="images/mainPage/men/image-landscape-default-fill-2044ea1f-ea6a-4e93-9079-62a160d3eace-default_0.jpg"></a></li>
					<li class="h-100 w-100"><a><img
							src="images/mainPage/women/image-landscape-fill-015914f5-1fc2-49d1-82b1-923e1e0e5764-default_0.jpg"></a></li>
					<li class="h-100 w-100"><a><img
							src="images/mainPage/men/image-landscape-03ceec99-c2ba-42f0-b7aa-f97e27744f85-default_0.jpg"></a></li>
					<li class="h-100 w-100"><a><img
							src="images/mainPage/image-landscape-4af6b9e7-1ca1-42d4-9bbb-1a321783250e-default_0.jpg"></a></li>
				</ul>

			</div>
		</div>
		<button class="carousel-control-prev" type="button"
			data-target="#carouselExampleControls" data-slide="prev">
			<span class="carousel-control-prev-icon" aria-hidden="true"></span> <span
				class="visually-hidden"></span>
		</button>
		<button class="carousel-control-next" type="button"
			data-target="#carouselExampleControls" data-slide="next">
			<span class="carousel-control-next-icon" aria-hidden="true"></span> <span
				class="visually-hidden"></span>
		</button>
	</div>
	<!-- <div id="main_container">
      <img
        src="images/image-landscape-default-fill-ee6c3f0b-60c2-4f63-b677-ff142edc747a-default_0.jpg"
        class="d-block w-100 h-100 img-fluid"
        alt="..."
      /> -->


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