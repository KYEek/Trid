<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/payment/payAddress.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/payment/paynicepay.css" />

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
    <!-- 유저 js -->
    <title>결제페이지</title>
    <!-- <link rel="stylesheet" href="styles.css"> -->
	<!--결제 api -->
	<script src="https://cdn.portone.io/v2/browser-sdk.js"></script>
	<script>
	
	
	async function requestPayment() {
		  const response = await PortOne.requestPayment({
			// Store ID 설정
			  storeId: "store-96b0d25f-1d8d-4239-a08e-6efbda0c3e29",
			  // 채널 키 설정
			  channelKey: "channel-key-abcd395e-b82e-4b41-be89-02016c0d0995",
			  paymentId: "payment-"+crypto.randomUUID(),
			  orderName: "${requestScope.member_name}님의 결제",
			  totalAmount: 100,
			  currency: "CURRENCY_KRW",
			  payMethod: "CARD",
		  });

		  if (response.code !== undefined) {
		    // 오류 발생
		    alert(response.message);
		    return location.href= sessionStorage.getItem('backURL');
	 		
		  }

		  // /payment/complete 엔드포인트를 구현해야 합니다. 다음 목차에서 설명합니다.
		  const notified = await fetch("complete.trd", {
		    method: "POST",
		    headers: { "Content-Type": "application/json" },
		    // paymentId와 주문 정보를 서버에 전달합니다
		    body: JSON.stringify({
		      paymentId: response.paymentId,
		      // 주문 정보...
		    }),
		  }).then(data =>{
			return true;
		  }).catch (message =>{ 
				console.error(message);
				return false
			});

	document.addEventListener("DOMContentLoaded", function() {
		if(requestPayment()) {
			document.querySelector("div#payment_loading_container").style.display ="none";
		}
	} );
	</script>
  </head>
  <body>
    <!-- 결제 완료 글자자 -->
    <div id="payment_complete">계속버튼을 눌러 결제를 완료하세요!</div>
    <!-- 로딩창 -->
    <div id="payment_loading_container">
      <img src="../images/loading.gif" id="payment_loading" />
    </div>
    <!-- 주소 리스트 -->
    <main>
      <div id="select_shipment_container">
        <!-- 배송지 본문 -->
        <div style="padding-top: 101.053%; position: relative"></div>
        <!-- 푸터 -->
        <div id="basket_footer">
          <div id="basket_footer_info">
            <div></div>
          </div>
          <div id="basket_footer_total_price">
            <div>배송 <span id="total_price">₩3000</span></div>
          </div>
          <div id="basket_footer_next_button">
            <span>계속</span>
          </div>
        </div>
      </div>
    </main>
  </body>
</html>

