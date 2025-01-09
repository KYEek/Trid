<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <!-- Required meta tags -->
    <meta charset="utf-8" />
    <meta
      name="viewport"
      content="width=device-width, initial-scale=1, shrink-to-fit=no"
    />

    <!-- Bootstrap CSS -->
    <link
      rel="stylesheet"
      type="text/css"
      href="${pageContext.request.contextPath}/bootstrap-4.6.2-dist/css/bootstrap.min.css"
    />

    <!-- Font Awesome 6 Icons -->
    <link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.1/css/all.min.css"
    />

    <!-- Optional JavaScript -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
    <script
      type="text/javascript"
      src="${pageContext.request.contextPath}/bootstrap-4.6.2-dist/js/bootstrap.bundle.min.js"
    ></script>
    <!-- 유저 CSS -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/basket/basket.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/order/orderDetail.css" />

    <!--jQueryUI CSS 및 JS   -->
    <link
      rel="stylesheet"
      type="text/css"
      href="${pageContext.request.contextPath}/jquery-ui-1.13.1.custom/jquery-ui.min.css"
    />
    <script
      type="text/javascript"
      src="${pageContext.request.contextPath}/jquery-ui-1.13.1.custom/jquery-ui.min.js"
    ></script>
	
	<script>
// 	json데이터를 불러오기	
		let orderDetailStr = `${requestScope.orderDetail}`;
		orderDetailStr = orderDetailStr.replaceAll("\\","\\\\");
		const addrInfoStr = `${requestScope.addrInfo}`;
	</script>
    <!-- 유저 js -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/order/orderDetail.js"></script>
    <title>주문상세</title>
    <!-- <link rel="stylesheet" href="styles.css"> -->
  </head>
   <body>
    <div id="loading">
      <div id="roading_container">
        <div id="roading_box">변경사항 저장중입니다.</div>
      </div>
    </div>
    <%@include file="../header.jsp"%>
    <main>
      <div id="order_detail_header">
        <div>주문번호<span id="orderNo">1000</span></div>
        <div id="order_date_box">
          <div>구매날짜</div>
          <div id="order_date">2024/12/19</div>
        </div>
        <div><span id="order_status">주문상태</span></div>
      </div>
      <div id="order_detail">
        <!-- 상품 목록 -->
        <div id="basket_list"></div>
        <div id="order_detail_footer">
          <div id="order_address_summary">
            <div>발송주소</div>
            <div id="delivery_addr_div">
              <div class="addr_li addr_name">연규영</div>
              <div class="addr_li">경기 성남시 분당구 판교역로 4</div>
              <div class="addr_li">202호</div>
              <div class="addr_li">13536</div>
              <div class="addr_li">01040209698</div>
            </div>
          </div>
          <div id="order_price_summary">
            <div id="order_price_summary_header">요약</div>
            <div class="order_price_summary_item">
              <div>제품 <span id="item_count_number">2</span>개</div>
              <div id="order_price_sum">₩10000</div>
            </div>
            <div class="order_price_summary_item">
              <div>배송비</div>
              <div id="delevery_cost">₩3000</div>
            </div>
            <div class="order_price_summary_item">
              <div>합계</div>
              <div id="order_price_total">₩13000</div>
          </div>
        </div>
      </div>
    </main>
    <%@include file="../footer.jsp"%>
  </body>
</html>


<%-- <%@ include file="../header.jsp" %> --%>
<%-- <%@ include file="../footer.jsp" %> --%>