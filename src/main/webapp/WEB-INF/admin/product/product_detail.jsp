<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%-- pageContextPath --%>
<c:set var="ctxPath" value="${pageContext.request.contextPath}" />
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

<%-- 관리자 상품 상세 조회 페이지 --%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>Trid Admin Product Detail</title>
<%-- css --%>
<link rel="stylesheet" href="${ctxPath}/css/admin/product_detail.css">
<link rel="stylesheet" href="${ctxPath}/css/admin/button.css">

</head>

<body>
	<%-- 상품 상세 --%>
	<div id="container">

		<%-- 관리자 사이드 네비게이션 --%>
		<%@ include file="../side_navigation.jsp"%>

		<%@ include file="../../image_carousel.jsp"%>

		<div id="main_container">

			<div id="item_container">

				<div id="detail_header">


					<div class="item" style="margin : 50px 0 0 0">
						<c:if test="${categoryDTO.gender == 0}">남성</c:if>
						<c:if test="${categoryDTO.gender == 1}">여성</c:if>
						<c:if test="${categoryDTO.type == 0}">상의</c:if>
						<c:if test="${categoryDTO.type == 1}">하의</c:if>

						${categoryDTO.categoryName}
					</div>


					<div id="header_buttons">
						<%-- 상품 수정 --%>
						<button type="button" class="button--ujarak" onclick="location.href='productUpdate.trd?productNo=${productDTO.productNo}';">수정하기</button>

						<%-- 이미 삭제 상태인 상품은 삭제버튼을 비활성화 한다. --%>
						<c:if test="${productDTO.status == 1}">
							<form name="delete_product_frm">
								<input type="hidden" name="productNo" value="${productDTO.productNo}" />
								<button type="button" class="button--ujarak" id="delete_product_button">삭제하기</button>
							</form>
						</c:if>

						<%-- 돌아가기 버튼을 클릭 시 이전 페이지로 돌아간다. --%>
						<button type="button" class="button--ujarak" onclick="location.href='productManage.trd';">돌아가기</button>
					</div>

				</div>

				<%-- 상세 정보 --%>
				<div id="detail_container">
					<h2>${productDTO.productName}</h2>
					<div class="item">
						<span>product no. ${productDTO.productNo}</span>
					</div>

					<div class="item">
						<c:if test="${productDTO.status == 0}">
							<span>판매중단</span>
						</c:if>
						<c:if test="${productDTO.status == 1}">
							<span>판매중</span>
						</c:if>
					</div>

					<div class="item">
						<span><fmt:formatNumber value="${productDTO.price}" pattern="#,###" /></span>
					</div>

					<p id="explanation">${productDTO.explanation}</p>

					<span>재고</span>
					
					<div class="item">
						<%-- 사이즈 분류 --%>
						<table class="table border-bottom table-sm">
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
						<span>등록일자</span>&nbsp;&nbsp;<span>${productDTO.registerday}</span>
					</div>
					
					<div class="item">
						<span>수정일자</span>&nbsp;&nbsp; <span>${productDTO.updateday} </span>
					</div>

					
					<div class="item">
						<c:forEach items="${colorList}" var="colorDTO">
							<span>${colorDTO.colorName}&nbsp;&nbsp;</span>
							<div style="margin-right:10px; width:30px; height:30px; background-color:${colorDTO.colorCode}; border:solid 1px;"></div>
						</c:forEach>
					</div>

				</div>

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