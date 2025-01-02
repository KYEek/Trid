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
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/order/orderList.css" />

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
		const addrListStr = `${requestScope.addrList}`;
		const addrList = JSON.parse(addrListStr);
	</script>
    <!-- 유저 js -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/order/orderList.js"></script>
    <title>주문 목록</title>
    <!-- <link rel="stylesheet" href="styles.css"> -->
  </head>
  <body>
     <main>
      <div id="order_header"><h1>구매내역</h1></div>
      <div id="order_list_box">
      </div>
    </main>
  </body>
</html>


<%-- <%@ include file="../header.jsp" %> --%>
<%-- <%@ include file="../footer.jsp" %> --%>