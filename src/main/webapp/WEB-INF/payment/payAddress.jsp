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
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/payment/payAddress.css" />

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
	<!-- 유저의 주소정보를 저장 -->
    <script type="text/javascript">
    	//데이터를 가져오기
    	const instantPay = `${requestScope.instantPay}`;
    	let productInfoStr = "";
    	let productInfo;
    	//바로결제로 들어온 경우라면 실행
    	if(instantPay == "true") {
			productInfoStr = "sessionStorage.getItem('basket_item_arry')";
			productInfoStr = productInfoStr.replaceAll("\\", "\\\\");
    		productInfo = JSON.parse(productInfoStr);
    	}
		const addrListStr = `${requestScope.addrList}`;
		const addrList = JSON.parse(addrListStr);
	</script>
    <!-- 유저 js -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/payment/payAddress.js"></script>
    <title>배송지 선택 페이지</title>
    <!-- <link rel="stylesheet" href="styles.css"> -->
  </head>
  <body>
    <!-- 로딩창 -->
    <div id="loading">
      <div id="roading_container">
        <div id="roading_box">변경사항 저장중입니다.</div>
      </div>
    </div>
    <!-- 주소 리스트 -->
    <div id="address_list_container">
      <div id="address_list">
        <div id="address_list_close"><span>x</span></div>
        <div id="address_list_header">배송 위치를 선택하세요</div>
        <div id="address_list_main">
          <!-- <ul>
            <li>
              <div class="address_list_item">
                <div>연규영</div>
                <div>주소</div>
                <div>부가주소</div>
                <div>우편번호</div>
                <div>전화번호</div>
              </div>
            </li>
          </ul> -->
        </div>
       <a href="/Trid/member/addressAdd.trd"><div id="address_list_add">새로운 주소 추가</div></a>
      </div>
    </div>
    <%@include file="../header.jsp"%>
    <main>
      <div id="select_shipment_container">
        <!-- 배송지 본문 -->
        <div id="shipment_main">
          <h1>물품을 배송 받을 주소</h1>
          <div id="address_select_box">
            <div><i class="fa-solid fa-truck"></i><span> 주소정보</span></div>
            <div id="address_info_and_select">
              <div>경기 성남시 분당구 판교역로 4</div>
              <a href="#">변경</a>
            </div>
          </div>
          <div id="delivery_select_box">
            <div id ="delevery_date">1/2 배송</div>
            <div id="delivery_product_list"></div>
            <div id="delivery_radio_box">배송은 약 1-3일 소요됩니다</div>
          </div>
        </div>
        <!-- 푸터 -->
        <div id="basket_footer">
          <div id="basket_footer_info">
            <div></div>
          </div>
          <div id="basket_footer_total_price">
            <div>배송 <span id="total_price">3000</span> ₩</div>
          </div>
          <div id="basket_footer_next_button">
            <span>계속</span>
          </div>
        </div>
      </div>
    </main>
    <%@include file="../footer.jsp"%>
  </body>
</html>

