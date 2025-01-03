<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>TRID</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/mainPage/mainpage.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/mainPage/mainFooter.css">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.1/font/bootstrap-icons.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap-4.6.2-dist/css/bootstrap.min.css">

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script src="${pageContext.request.contextPath}/bootstrap-4.6.2-dist/js/bootstrap.bundle.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/mainPage/mainpage.js"></script>
<style>
div.header-links > a.header-link {
	color:white;
}
</style>
</head>
<div class="container-fluid m-0 p-0">

	<%@ include file="header.jsp" %>
	<!-- 케러셀 부분  -->
	<div
      id="carouselExampleControls"
      class="carousel slide"
      data-ride="carousel"
      data-wrap="false"
    >
      <div class="carousel-inner">
      
        	<!--첫번째 카테고리 리스트  -->
        <div class="carousel-item active fullscreen group_list">
          <ul class="h-100 w-100 group_ul">
            <li class="list_size">
              <a
                ><img
                  class="h-100 w-100"
                  src="images/mainPage/women/image-landscape-64243cff-b87b-4267-81c5-cfea3f1f7455-default_0.jpg"
              /></a>
            </li>
            <li class="list_size">
              <a
                ><img
                  class="h-100 w-100"
                  src="images/mainPage/women/image-landscape-default-fill-ee6c3f0b-60c2-4f63-b677-ff142edc747a-default_0.jpg"
              /></a>
            </li>
            <li class="list_size">
              <a
                ><img
                  class="h-100 w-100"
                  src="images/mainPage/women/image-landscape-fill-015914f5-1fc2-49d1-82b1-923e1e0e5764-default_0.jpg"
              /></a>
            </li>
            <li class="list_size">
              <a
                ><img
                  class="h-100 w-100"
                  src="images/mainPage/women/image-landscape-fill-0bffae4e-6483-4a81-9068-5050469498d0-default_0.jpg"
              /></a>
            </li>
            <li class="list_size">
              <a
                ><img
                  class="h-100 w-100"
                  src="images/mainPage/women/image-landscape-fill-11a2dd9b-3409-46a3-a246-4a231c97e324-default_0.jpg"
              /></a>
            </li>
            <li class="list_size">
              <a
                ><img
                  class="h-100 w-100"
                  src="images/mainPage/women/image-landscape-fill-684d0f95-30fb-49aa-aa66-f37f86260128-default_0.jpg"
              /></a>
            </li>
            
            <!-- 메인푸터 -->
            <li class="list_size">
              <footer>
                <div id="first_footer">
                  <div>뉴스레터에 가입하세요</div>
                  <div id="news_letter_row" class="row">
                    <span class="news_letter col">TIKTOK</span
                    ><span class="news_letter col">INSTAGRAM</span
                    ><span class="news_letter col">FACEBOOK</span
                    ><span class="news_letter col">X</span
                    ><span class="news_letter col">PINTEREST</span
                    ><span class="news_letter col">KAKAO</span
                    ><span class="news_letter col">YOUTUBE</span
                    ><span class="news_letter col">SPOTFY</span>
                  </div>
                </div>
                <div id="second_footer">
                  아이티엑스코리아 유한회사 ｜ 사업자등록번호: 120-88-14733 ｜
                  대표자 : ROMAY DE LA COLINA JOSE MANUEL ｜ 서울시 강남구
                  영동대로 511 (삼성동, 트레이드타워 33층) ｜ 대표번호:
                  080-479-0880 ｜ 호스팅 서비스 사업자: ITX Merken, B.V. ｜
                  통신판매업신고 : 제2014-서울강남-02297 (사업자정보확인) ｜
                  개인정보처리방침 | 이용약관
                </div>
              </footer>
            </li>
          </ul>
        </div>
        
        <!--두번째 카테고리 리스트  -->
        <div class="carousel-item fullscreen group_list">
          <ul class="h-100 w-100 group_ul">
            <li class="h-100 w-100">
              <a
                ><img
                  class="h-100 w-100"
                  src="images/mainPage/men/image-landscape-03ceec99-c2ba-42f0-b7aa-f97e27744f85-default_0.jpg"
              /></a>
            </li>
            <li class="h-100 w-100">
              <a
                ><img
                  class="h-100 w-100"
                  src="images/mainPage/men/image-landscape-default-fill-2044ea1f-ea6a-4e93-9079-62a160d3eace-default_0.jpg"
              /></a>
            </li>
            <li class="h-100 w-100">
              <a
                ><img
                  class="h-100 w-100"
                  src="images/mainPage/women/image-landscape-fill-015914f5-1fc2-49d1-82b1-923e1e0e5764-default_0.jpg"
              /></a>
            </li>
            <li class="h-100 w-100">
              <a
                ><img
                  class="h-100 w-100"
                  src="images/mainPage/men/image-landscape-03ceec99-c2ba-42f0-b7aa-f97e27744f85-default_0.jpg"
              /></a>
            </li>
            <li class="h-100 w-100">
              <a
                ><img
                  class="h-100 w-100"
                  src="images/mainPage/image-landscape-4af6b9e7-1ca1-42d4-9bbb-1a321783250e-default_0.jpg"
              /></a>
            </li>
            
            <!-- 메인푸터 -->
            <li class="h-100 w-100">
              <footer>
                <div id="first_footer">
                  <div>뉴스레터에 가입하세요</div>
                  <div id="news_letter_row" class="row">
                    <span class="news_letter col">TIKTOK</span
                    ><span class="news_letter col">INSTAGRAM</span
                    ><span class="news_letter col">FACEBOOK</span
                    ><span class="news_letter col">X</span
                    ><span class="news_letter col">PINTEREST</span
                    ><span class="news_letter col">KAKAO</span
                    ><span class="news_letter col">YOUTUBE</span
                    ><span class="news_letter col">SPOTFY</span>
                  </div>
                </div>
                <div id="second_footer">
                  아이티엑스코리아 유한회사 ｜ 사업자등록번호: 120-88-14733 ｜
                  대표자 : ROMAY DE LA COLINA JOSE MANUEL ｜ 서울시 강남구
                  영동대로 511 (삼성동, 트레이드타워 33층) ｜ 대표번호:
                  080-479-0880 ｜ 호스팅 서비스 사업자: ITX Merken, B.V. ｜
                  통신판매업신고 : 제2014-서울강남-02297 (사업자정보확인) ｜
                  개인정보처리방침 | 이용약관
                </div>
              </footer>
            </li>
          </ul>
        </div>
      </div>
      
      <!-- 버튼부분 -->
      <button
        class="carousel-control-prev"
        type="button"
        data-target="#carouselExampleControls"
        data-slide="prev"
      >
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="visually-hidden"></span>
      </button>
      <button
        class="carousel-control-next"
        type="button"
        data-target="#carouselExampleControls"
        data-slide="next"
      >
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="visually-hidden"></span>
      </button>
    </div>

</div>
</body>
</html>