<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품 상세 페이지</title>


<%-- 공용 CSS --%>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.1/font/bootstrap-icons.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap-4.6.2-dist/css/bootstrap.min.css">
<%-- 공용 JS --%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script src="${pageContext.request.contextPath}/bootstrap-4.6.2-dist/js/bootstrap.bundle.min.js"></script>

<%-- 개인 CSS 및 JS --%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/product/detail.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/product/detail.js"></script>

<script>
$(document).ready(function() {
   
   <%-- "장바구니에 추가하기" 버튼 클릭 시 --%>
   $("input#go_basket").on("click", function(e){
      
      <%-- 로그인 하지 않고 "장바구니에 추가하기" 버튼 클릭 시 --%>
      if(${sessionScope.loginuser == null}) {
         alert("로그인 후에 이용하세요!");
         <%-- 로그인 페이지로 보내기 --%>
         location.href = "/Trid/login.trd";
      }
      <%-- 사이즈를 고르지 않고 "장바구니에 추가하기" 버튼 클릭 시 --%>
      else if(!$("input.size").hasClass("clicked")) {
         alert("사이즈를 선택하세요!");
      }
      <%-- 모든 조건을 만족했을 때 --%>
      else {
         $("form#go_basketFrm").submit();
      }
      
   });
   
   
   <%-- "바로 결제하기" 버튼 클릭 시 --%>
   $("input#go_payment").on("click", function(e){
	      
      <%-- 로그인 하지 않고 "장바구니에 추가하기" 버튼 클릭 시 --%>
      if(${sessionScope.loginuser == null}) {
         alert("로그인 후에 이용하세요!");
         <%-- 로그인 페이지로 보내기 --%>
         location.href = "/Trid/login.trd";
      }
      <%-- 사이즈를 고르지 않고 "장바구니에 추가하기" 버튼 클릭 시 --%>
      else if(!$("input.size").hasClass("clicked")) {
         alert("사이즈를 선택하세요!");
      }
      <%-- 모든 조건을 만족했을 때 --%>
      else {
         $("form#go_paymentFrm").submit();
      }
      
   });
   
});
</script>

</head>
<body>

   <jsp:include page="/WEB-INF/header.jsp" />

   <%-- ProductDTO --%>
   <c:set var="productDTO" value="${requestScope.productDTO}" />
   <%-- ColorDTO List --%>
   <c:set var="colorList" value="${requestScope.productDTO.colorList}" />
   <%-- CategoryDTO --%>
   <c:set var="categoryDTO" value="${requestScope.productDTO.categoryDTO}" />
   <%-- ProductDetailDTO List --%>
   <c:set var="productDetailList" value="${requestScope.productDTO.productDetailList}" />
   <%-- ImageDTO List --%>
   <c:set var="imageList" value="${requestScope.productDTO.imageList}" />
   <%-- RecommendProductMapList --%>
   <c:set var="recommendProductMapList" value="${requestScope.recommendProductMapList}"/>
   
   
   <div id="container">
      
      <%-- 추천 상품이 있을때만 보여주기 --%>
      <c:if test="${fn:length(recommendProductMapList) % 2 == 0 && fn:length(recommendProductMapList) != 0}">   
	      <div id="recommend_bar">
	         
	         <div id="recommend_product_title">추천 상품리스트</div>
	         
	         <div id="recommend_product_list">
	            
	            <c:forEach items="${recommendProductMapList}" var="recommendProductMap">
	            	
		            <div id="recommend_product" onclick="location.href='/Trid/product/detail.trd?productNo=${recommendProductMap.product_no}'">
		            
		               <div id="recommend_productimgbox">
		               		<img id="recommend_productimg" src="${pageContext.request.contextPath}/${recommendProductMap.image_path}"/>
		               </div>
		               <div id="recommend_productname">${recommendProductMap.product_name}</div>
		               
		            </div>
		            
	            </c:forEach>
	         
	         </div>
	         
	      </div>
      </c:if>
      
      <div id="image_box" style="height: 700px;">
         <%@ include file="../image_carousel.jsp"%>
      </div>

      <div id="product_detailbox">
         <div id="top_deatilbox">
            <div id="name">${productDTO.productName}</div>
            <div id="price">${productDTO.price}원</div>
            <div id="explanation">${productDTO.explanation}</div>
         </div>
   
         <div id="bottom_detailbox">   
            
            <div id="color">
               
               <div id="color_name">
                  <c:forEach items="${colorList}" var="colorDTO" varStatus="status">
                     
                     <%-- 첫번째면 || 하지 않기 --%>
                     <c:if test="${status.first}">
                        <div>${colorDTO.colorName}</div>
                     </c:if>
                     <%-- 첫번째가 아니면 공백 + || 추가하기 --%>
                     <c:if test="${!status.first}">
                        <div>&nbsp;|| ${colorDTO.colorName}</div>
                     </c:if>
                     
                  </c:forEach>
               </div>  
               
               <div id="color_code">
               
                  <c:forEach items="${colorList}" var="colorDTO"  varStatus="status">
                     
                     <%-- 첫번째면 || 하지 않기 --%>
                     <c:if test="${status.first}">
                        <div>${colorDTO.colorCode}</div>
                     </c:if>
                     <%-- 첫번째가 아니면 공백 + || 추가하기 --%>
                     <c:if test="${!status.first}">
                        <div>&nbsp;|| ${colorDTO.colorCode}</div>
                     </c:if>
                      
                  </c:forEach>
               </div> 
               
            </div>
            
            <div id="colorbox">
            
               <c:forEach items="${colorList}" var="colorDTO">
                  <div class="gratify" style="background-color: ${colorDTO.colorCode};"></div>
               </c:forEach>
               
            </div>

            <div id="size_bar">
               
               <c:forEach items="${productDetailList}" var="productDetailDTO">
               
                  <%-- 사이즈 별로 choose --%>
                  <c:choose>
                     <%-- S 사이즈 --%>
                     <c:when test="${productDetailDTO.size == 0}">
                     
                        <%-- S 사이즈 재고가 있을 때 --%>
                        <c:if test="${productDetailDTO.inventory != 0}">
                           <input id="${productDetailDTO.pkProductDetailNo}" class="size" type="button" value="S" size="${productDetailDTO.size}">
                        </c:if>
                        <%-- S 사이즈 재고가 없을 때 --%>
                        <c:if test="${productDetailDTO.inventory == 0}">
                           <input id="${productDetailDTO.pkProductDetailNo}" class="noinventory" type="button" value="재고없음" size="${productDetailDTO.size}">
                        </c:if>
                        
                     </c:when>
                     
                     <%-- M 사이즈 --%>
                     <c:when test="${productDetailDTO.size == 1}">
                        
                        <%-- M 사이즈 재고가 있을 때 --%>
                        <c:if test="${productDetailDTO.inventory != 0}">
                           <input id="${productDetailDTO.pkProductDetailNo}" class="size" type="button" value="M" size="${productDetailDTO.size}"> 
                        </c:if>
                        <%-- M 사이즈 재고가 없을 때 --%>
                        <c:if test="${productDetailDTO.inventory == 0}">
                           <input id="${productDetailDTO.pkProductDetailNo}" class="noinventory" type="button" value="재고없음" size="${productDetailDTO.size}"> 
                        </c:if>
                        
                     </c:when>
                     
                     <%-- L 사이즈 --%>
                     <c:when test="${productDetailDTO.size == 2}">
                     
                        <%-- L 사이즈 재고가 있을 때 --%>
                        <c:if test="${productDetailDTO.inventory != 0}">
                           <input id="${productDetailDTO.pkProductDetailNo}" class="size" type="button" value="L" size="${productDetailDTO.size}"> 
                        </c:if>
                        <%-- L 사이즈 재고가 없을 때 --%>
                        <c:if test="${productDetailDTO.inventory == 0}">
                           <input id="${productDetailDTO.pkProductDetailNo}" class="noinventory" type="button" value="재고없음" size="${productDetailDTO.size}"> 
                        </c:if>
                        
                     </c:when>
                     
                     <%-- XL 사이즈 --%>
                     <c:when test="${productDetailDTO.size == 3}">
                     
                        <%-- XL 사이즈 재고가 있을 때 --%>
                        <c:if test="${productDetailDTO.inventory != 0}">
                           <input id="${productDetailDTO.pkProductDetailNo}" class="size" type="button" value="XL" size="${productDetailDTO.size}"> 
                        </c:if>
                        <%-- XL 사이즈 재고가 없을을 때 --%>
                        <c:if test="${productDetailDTO.inventory == 0}">
                           <input id="${productDetailDTO.pkProductDetailNo}" class="noinventory" type="button" value="재고없음" size="${productDetailDTO.size}"> 
                        </c:if>
                        
                     </c:when>
                     
                  </c:choose>
                  
               </c:forEach>
         
            </div>

            <div id="paymentbar">
               <input id="go_basket" class="paymentBtn" type="button" value="장바구니에 추가"> 
               <input id="go_payment" class="paymentBtn" type="button" value="바로 결제하기">
            </div>
            
            <%-- "장바구니에 추가하기" 클릭 시 사용되는 폼태그 --%>
            <form id="go_basketFrm" action="/Trid/basket.trd" method="post">
               <input type="hidden" name="go_basket" id="go_basketProductDetailNo" />
            </form>
            
            <%-- "바로 결제하기" 클릭 시 사용되는 폼태그 --%>
            <form id="go_paymentFrm" action="/Trid/payment/address.trd" method="get">
               <input type="hidden" name="go_payment" id="go_orderProductDetailNo" />
               <input type="hidden" name="instantPay" value="true" />
            </form>
            
            <%-- 
            <form id="go_basketFrm" action="/Trid/basket.trd" method="post">
               <input type="hidden" name="go_basket" id="go_basketProductDetailNo" />
            </form>
            --%>
            
         </div>
      </div>
   </div>
	
</body>
</html>