<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- pageContextPath --%>
<c:set var="ctxPath" value="${pageContext.request.contextPath}" />

<%-- 관리자 상품 상세 조회 페이지 --%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>관리자 상품 상세</title>

<%-- css --%>
<link rel="stylesheet" href="${ctxPath}/css/admin/product_detail.css">

<%-- js --%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script src="${ctxPath}/js/admin/product_detail.js"></script>
</head>

<body>
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

	<%-- 상품 상세 --%>
	<div id="product_manage_container">

		<%-- 관리자 사이드 네비게이션 --%>
		<%@ include file="../side_navigation.jsp"%>

		<div id="main_container">
		
			<div id="item_container">
			
				<div id="detail_header">
					<h2>상품 상세조회</h2>
					<div id="header_buttons">
						<%-- 돌아가기 버튼을 클릭 시 이전 페이지로 돌아간다. --%>
						<a id="return" href="productManage.trd">돌아가기</a> 
						<a href="productUpdate.trd?productNo=${productDTO.productNo}">수정하기</a>
						
						
						<%-- 이미 삭제 상태인 상품은 삭제버튼을 비활성화 한다. --%>
						<c:if test="${productDTO.status == 1}">
							<form name="delete_product_frm">
								<input type="hidden" name="productNo" value="${productDTO.productNo}" />
								<button id="delete_product_button" type="button">삭제하기</button>
							</form>
						</c:if>
					</div>
				</div>
	
				<%-- 상세 정보 --%>
				<div id="detail_container">
					<div class="item">
						<div>
							<span>상품 일련번호</span> 
							<span>${productDTO.productNo}</span>
						</div>
						<div>
							<span>카테고리</span> 
							<span>
								<c:if test="${categoryDTO.type == 0}">상의</c:if>
								<c:if test="${categoryDTO.type == 1}">하의</c:if>
							</span>
							<span>${categoryDTO.categoryName}</span>
						</div>
					</div>
					<div class="item">  
						<span>성별</span> 
						<span>
							<c:if test="${categoryDTO.gender == 0}">남</c:if>
							<c:if test="${categoryDTO.gender == 1}">여</c:if>
						</span> 
						
						
						<%-- 상품 상태 여부 --%>
						<span>삭제여부</span> 
						<c:choose>
							<c:when test="${productDTO.status == 0}">
								<span>비활성화</span>
							</c:when>
							<c:otherwise>
								<span>활성화</span>
							</c:otherwise>
						</c:choose>
					</div>
	
					<div class="item">
						<span>상품명</span> 
						<span>${productDTO.productName}</span>
						<span>가격</span> 
						<span>${productDTO.price}</span>
					</div>
					
					<div class="item" style="flex-direction : column"> 
						<span>상품설명</span> 
						<textarea>${productDTO.explanation}</textarea> 
					</div>
					
	
					<span>사이즈 별 재고 수</span>
					<div class="item">
						<%-- 사이즈 분류 --%>
						<table class="table border-bottom">
							<thead>
								<tr> 
									<th class="col-3">S</th>
									<th class="col-3">M</th>
									<th class="col-3">L</th>
									<th class="col-3">XL</th>
								</tr>
							</thead>
							<tbody>
								<tr> 
									<c:forEach items="${productDetailList}" var="productDetailDTO" varStatus="status">
										<c:if test="${productDetailDTO.size == status.index}">
											<td>${productDetailDTO.inventory}</td>
										</c:if>
									</c:forEach>
								</tr>
							</tbody>
						</table>
					</div>
				
					<div class="item">
						<span>등록일자</span> 
						<span>${productDTO.registerday}</span>
					</div> 
					<div class="item">
						<span>수정일자</span> 
						<span>${productDTO.updateday} </span>
					</div>
	
					
	
					<div class="item">
						<%-- 상품 색상 --%>
						<span>색상</span>
						<c:forEach items="${colorList}" var="colorDTO">
							<span>${colorDTO.colorName}</span>
							<div style="width:30px; height:30px; background-color:${colorDTO.colorCode}; border:solid 1px;"></div>
						</c:forEach>
					</div>
	
				</div>
			
			</div>
			
			<div style="height: 80vh; max-width: 500px">
				<%@ include file="../../image_carousel.jsp"%>
			</div>

		</div>

	</div>
	
	<script>
		$(document).ready(function() {
			// 삭제 버튼 클릭 시 이벤트 추가
			$(document).on("click", "#delete_product_button", function() {
				if (confirm("정말로 삭제하시겠습니까?")) {
					const frm = document.delete_product_frm;
	
					frm.action = "productDelete.trd";
					frm.method = "post";
					frm.submit();
				}
			});
		});
	</script>

</body>

</html>