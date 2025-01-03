<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.Map, java.util.HashMap"%>
    <%
    Map temp_address_info = (HashMap)session.getAttribute("temp_address_info");
    String name = (String)temp_address_info.get("member_name");
    String address = (String)temp_address_info.get("addr_address");
    String addr_detail = (String)temp_address_info.get("addr_detail");
    String post_no = (String)temp_address_info.get("addr_post_no");
    String mobile = (String)temp_address_info.get("member_mobile");%>
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
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/payment/paymentInfo.css" />
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
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/payment/paymentInfo.js"></script>
    <title>배송지 정보 사이트</title>
    <!-- <link rel="stylesheet" href="styles.css"> -->
  </head>
  <body>
    <div id="loading">
      <div id="roading_container">
        <div id="roading_box">변경사항 저장중입니다.</div>
      </div>
    </div>
    <main>
      <div id="payment_info_container">
        <!-- 배송지 본문 -->
        <div id="paymanet_main">
          <div id="payment_detail">
            <div id="payment_shipment_box">
              <h1>배송</h1>
              <div
                id="payment_shiptment_content"
                class="payment_content_container"
              >
                <div id="payment_shipment_method">
                  <div>자택 배송</div>
                  <div>배송 화요일 31, 12월 - 목요일 02, 1월</div>
                  <div>편집</div>
                </div>
                <div id="payment_address_info" class="address_list_item">
                  <div id="payment_address_name"><%=name %></div>
                  <div id="payment_address"><%=address %></div>
                  <div id="payment_address_extra"><%=addr_detail %></div>
                  <div id="payment_address_post_num"> <%=post_no %></div>
                  <div id="payment_address_mobile"><%=mobile %></div>
                </div>
              </div>
            </div>
            <div id="payment_pay_box">
              <h1>결제</h1>
              <div id="payment_pay_method" class="payment_content_container">
                <i class="fa-regular fa-credit-card"></i> 카드
              </div>
            </div>
          </div>
          <div id="payment_product_list_container">
            <h1>배송 요약</h1>
            <div
              id="payment_product_list_box"
              class="payment_content_container"
            >
              <div>
                <div>
                  <span id="delivery_day"
                    >화요일 31, 12월 - 목요일 02, 1월</span
                  >
                </div>
                <div><sapn id="item_count">3</sapn> 상품</div>
                <div id="delivery_product_list">
                  <!-- <img src="images/basket/00155055500-p.jpg" /> -->
                </div>
              </div>
            </div>
          </div>
        </div>
        <!-- 푸터 -->
        <div id="basket_footer">
          <div id="basket_footer_info">
            <div>
              * 결제 승인 버튼을 클릭하시면 나이스페이먼츠 전자금융거래 서비스
              이용약관에 동의하시는 것으로 간주됩니다.
            </div>
          </div>
          <div id="basket_footer_total_price">
            <div>총 <span id="total_price">₩3000</span></div>
          </div>
          <div id="basket_footer_next_button">
            <span>결제승인</span>
          </div>
        </div>
      </div>
    </main>
    <form id="payComplete" action="paying.trd">
      <input type="hidden" name="total_price" />
    </form>
  </body>
</html>

