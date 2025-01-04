<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="java.util.HashMap, java.util.Map" %>

 <%
    Map temp_address_info = (HashMap)session.getAttribute("temp_address_info");
    String name = (String)temp_address_info.get("member_name");
    String address = (String)temp_address_info.get("addr_address");
    String addr_detail = (String)temp_address_info.get("addr_detail");
    String post_no = (String)temp_address_info.get("addr_post_no");
    String mobile = (String)temp_address_info.get("member_mobile");
    session.removeAttribute("temp_address_info");%>
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
    <!-- 유저 js -->
    <title>결제완료</title>
    <!-- <link rel="stylesheet" href="styles.css"> -->
    <style>
      div#order_info {
        text-align: center;
        border: var(--border-var);
        width: 30vw;
        margin: 5vh 0;
      }

      div#order_info > div {
        padding: 2vh 0 2vh 2vw;
        text-align: left;
      }

      div#order_info_button {
        border-top: var(--border-var);
        padding: 1vh 0 1vh 0 !important;
        text-align: center !important;
      }

      a {
        text-decoration: none;
      }
      a:hover {
        text-decoration: none;
      }
    </style>
  </head>
  <body>
    <!-- 로딩창 -->
    <!-- <div id="loading">
      <div id="roading_container">
        <div id="roading_box">변경사항 저장중입니다.</div>
      </div>
    </div> -->
    <!-- 주소 리스트 -->

    <main>
      <div id="select_shipment_container">
        <!-- 배송지 본문 -->
        <div id="shipment_main">
          <h1><span>연규영</span>님, Trid에서 쇼핑해 주셔서 감사합니다.</h1>
          <div id="order_info">
            <div>
              <div>예상 배송일</div>
              <div><strong>배송 화요일 - 목요일</strong></div>
            </div>
            <div>
              <div>배송지</div>
              <div><%=name %></div>
              <div><%=address %></div>
              <div><%=addr_detail %></div>
              <div > <%=post_no %></div>
              <div><%=mobile %></div>
            </div>
            <div>
              <div>주문번호 : <sapn>${requestScope.orderNo}</sapn></div>
            </div>
            <div id="order_info_button">
              <div><a href="http://localhost:9090/Trid/orders/detail.trd?orderNo=${requestScope.orderNo}">주문 정보 보기 </a></div>
            </div>
          </div>
        </div>
      </div>
    </main>
  </body>
</html>
