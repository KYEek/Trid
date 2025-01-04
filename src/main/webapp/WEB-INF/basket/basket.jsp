<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>장바구니</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.1/font/bootstrap-icons.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/myPage/mypage.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/bootstrap-4.6.2-dist/css/bootstrap.min.css">
<script type="text/javascript">
const basketListStr = `${requestScope.basketList}`;
const basketList = JSON.parse(basketListStr);
</script>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/basket/basket.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/basket/basket.js"></script>
</head>
<body>

<%-- <%@ include file="../header.jsp"%><style> --%>
<!-- .header { -->
<!-- 	display: sticky; -->
<!-- } -->
</style>
 <div id="roading_container">
      <div id="roading_box">변경사항 저장중입니다.</div>
    </div>
    <main>
      <div id="basket_container">
        <!-- 장바구니 헤더 -->
        <div id="basket_header">
          <div>바스켓백<span id="basket_count">(10)</span></div>
        </div>
        <!-- 장바구니 설명문 -->
        <div id="basket_explain">
          <span
            >장바구니에 담긴 상품은 구매가 완료될 때까지 예약되지 않습니다.
          </span>
        </div>
        <!-- 상품 목록 -->
        <div id="basket_list"></div>
        <div id="basket_footer">
          <div id="basket_footer_info">
            <div>
              * 계속 진행함으로써 본인은 구매 조건을 읽고 이에 동의하며 Zara의
              개인정보 및 쿠키 정책을 이해했음을 선언합니다.
            </div>
          </div>
          <div id="basket_footer_total_price">
            <div>총 <span id="total_price">₩739,400</span></div>
            <div>부가세 포함함</div>
          </div>
          <div id="basket_footer_next_button">
            <span>계속</span>
          </div>
        </div>
      </div>
    </main>
<%@ include file="../footer.jsp"%>
</body>