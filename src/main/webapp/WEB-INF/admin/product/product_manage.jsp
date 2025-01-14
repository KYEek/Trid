<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- pageContextPath --%>
<c:set var="ctxPath" value="${pageContext.request.contextPath}" />
<%-- 관리자 명 --%>
<c:set var="adminName" value="${sessionScope.adminName}" />
<%-- ProductDTO List --%>
<c:set var="productList" value="${requestScope.productList}" />
<%-- CategoryDTO List --%>
<c:set var="categoryList" value="${requestScope.categoryList}" />

<%-- 상품 리스트 조회 페이지 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title>Trid Product Manage</title>

<%-- css --%>
<link rel="stylesheet" href="${ctxPath}/css/admin/manage.css">

<script type="text/javascript" src="${ctxPath}/js/admin/util.js"></script>
</head>
<body>

<div class="main_container">

	<%-- 관리자 사이드 네비게이션 --%>
	<%@ include file="../side_navigation.jsp"%>

	<div class="content_container">

		<%-- 검색 카테고리 --%>
		<div class="search_container">
			<form id="sort_frm" name="sort_frm">

				<div class="manage_header">
					<div style="display : flex; align-items : center">
						<h1 class="main_heading">Product Manage</h1>	 
						<button type="button" class="button--ujarak register_button" onclick="location.href='productRegister.trd'">상품추가</button>
					</div>
					<input type="search" class="manage_name" name="searchWord" placeholder="상품명"  />
				</div>

				<div class="manage_category">
					<div class="category_box">
						<select id="category_select" name="categoryNo">

							<option value="">전체</option>

							<c:forEach items="${categoryList}" var="categoryDTO">
								<option value="${categoryDTO.pkCategoryNo}">
									<c:if test="${categoryDTO.gender eq 0}">남자&nbsp;</c:if>
									<c:if test="${categoryDTO.gender eq 1}">여자&nbsp;</c:if>
									<c:if test="${categoryDTO.type eq 0}">상의&nbsp;:</c:if>
									<c:if test="${categoryDTO.type eq 1}">하의&nbsp;:</c:if> ${categoryDTO.categoryName}
								</option>
							</c:forEach>

						</select>

						<div class="range">
							<span>가격대(&#8361;)</span> 
							<input type="text" id="price_min_input" name="priceMin"/>
								&nbsp;~&nbsp;
							<input type="text" id="price_max_input" name="priceMax"/>
						</div>

						<div class="range">
							<span>기간</span> 
							<input type="date" id="date_min_input" name="dateMin"/>
								&nbsp;~&nbsp;
							<input type="date" id="date_max_input" name="dateMax"/>
						</div>

					</div>

					<div class="sort_box">
						<select id="sort_select" name="sortCategory">
							<option value="0">최신순</option>
							<option value="1">오래된순</option>
							<option value="2">높은 가격순</option>
							<option value="3">낮은 가격순</option>
						</select>

						<button type="button" class="button--ujarak" id="search_button" >검색</button>
					</div>
				</div>

			</form>
		</div>

		<span>총 ${pagingDTO.totalRowCount}개 상품</span>

		<%-- 상품 리스트 테이블 --%>
		<table class="table">
			<thead>
				<tr>
					<th scope="col">상품 일련번호</th>
					<th scope="col">성별 및 상/하의</th>
					<th scope="col">카테고리</th>
					<th scope="col">상품명</th>
					<th scope="col">상품 가격(&#8361;)</th>
					<th scope="col">상태</th>
					<th scope="col">등록일자</th>
					<th scope="col">수정일자</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${not empty productList}">
					<c:forEach items="${productList}" var="productDTO">
						<tr class="product_item">
							<td>${productDTO.productNo}</td>
							<td>
								<c:if test="${productDTO.categoryDTO.gender == 0}">남자</c:if>
								<c:if test="${productDTO.categoryDTO.gender == 1}">여자</c:if>
								<c:if test="${productDTO.categoryDTO.type == 0}">상의</c:if>
								<c:if test="${productDTO.categoryDTO.type == 1}">하의</c:if>
							</td>
							<td>${productDTO.categoryDTO.categoryName}</td>
							<td>${productDTO.productName}</td>
							<td><fmt:formatNumber value="${productDTO.price}" pattern="#,###" /></td>
							
							<c:choose>
								<c:when test="${productDTO.status == 0}">
									<td>판매중단</td>
								</c:when>
								<c:otherwise>
									<td>판매중</td>
								</c:otherwise>
							</c:choose>
							
							<td>${productDTO.registerday}</td>
							<td>${productDTO.updateday}</td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>

		<%-- 페이징 --%>
		<div>
			<%@ include file="../../paging.jsp" %>
		</div>
	</div>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		
		let oldSearchWord = "${requestScope.paraMap.searchWord}"; // 검색어
		let oldCategoryNo = "${requestScope.paraMap.categoryNo}"; // 카테고리 일련번호
		let oldSortCategory = "${requestScope.paraMap.sortCategory}"; // 정렬 번호 0:최신순 1:오래된순 2:높은가격순 3: 낮은 가격순
		
		let oldPriceMin = "${requestScope.paraMap.priceMin}"; // 기존 최소값 
		let oldPriceMax = "${requestScope.paraMap.priceMax}"; // 기존 최대값
		let oldDateMin = "${requestScope.paraMap.dateMin}"; // 기존 최소 등록일
		let oldDateMax = "${requestScope.paraMap.dateMax}"; // 기존 최대 등록일
		
		let url = ""; // URL

		// 페이징 시 기존 요청정보를 유지하기 위한 처리
		keepSearchConditions();
		
		// 상품 검색창 엔터 처리
		$(document).on("keydown", "input.manage_name" , function(e) {
			if(e.keyCode == 13) {
				$("button#search_button").click();	
			}
		});

		// 검색버튼 클릭 시 정렬, 검색 조건이 포함되어 페이지 요청
		$(document).on("click","#search_button",function() {
			const frm = document.sort_frm;
			frm.action = 'productManage.trd?curPage='+ ${paging.curPage};
			
			frm.searchWord.value = frm.searchWord.value.trim();  // 검색어 trim
			
			let priceMin = frm.priceMin.value; // 최소값 입력
			let priceMax = frm.priceMax.value; // 최대값 입력
			let dateMin = frm.dateMin.value; // 최소 등록일
			let dateMax = frm.dateMax.value; // 최대 등록일
			
			// 최소 최대 가격에 값이 들어있지만 유효한 값이 아닌 경우
			if(!isBlank(priceMin) && !isBlank(priceMax)) {
				if(!(isValidNumber(priceMin) && isValidNumber(priceMax))) {
					alert("유효한 가격 값이 아닙니다.");
					frm.priceMin.value = "";
					frm.priceMax.value = "";
					return false;			
				}
			}
			
			// 두 가격 값이 유효할 때 최소가가 최대가보다 큰 경우 검사
			if(isValidNumber(priceMin) && isValidNumber(priceMax)){
				if(Number(priceMin) > Number(priceMax)){
					alert("최소가는 최대가보다 작아야 합니다.");
					frm.priceMin.value = "";
					frm.priceMax.value = "";
					return false;	
				}
			}
		
			// 최소 최대 날짜에 값이 들어있지만 유효한 날짜 형식이 아닌 경우
			if(!isBlank(dateMin) && !isBlank(dateMax)) {
				if(!(isValidDate(dateMin) && isValidDate(dateMax))) {
					alert("유효한 날짜 형식이 아닙니다.");
					frm.dateMax.value = "";
					frm.dateMax.value = "";
					return false;			
				}
			}
				
			// 두 날짜 값이 유효할 때 최소 날짜가 최대 날짜보다 이후인 경우
			if (isValidDate(dateMin) && isValidDate(dateMax)) {
				if(dateMin > dateMax){
					alert("시작일은 마지막일보다 앞서야 합니다.");
					frm.dateMin.value = "";
					frm.dateMax.value = "";
					return false;
				}
			}

			frm.submit();
		});

		// 상품 리스트에서 요소 클릭 시 상품 상세로 이동
		$(document).on("click","tr.product_item", function(e) {
			const productNo = $(this).find("td").eq(0).text();
			location.href = "productDetail.trd?productNo=" + productNo;
		});
		
		// 페이징 시 기존 요청값 불러와 요소에 저장하는 함수
		function keepSearchConditions() {
			
			if (!isBlank(oldSearchWord)) {
				$("input[name='searchWord']").val(oldSearchWord);
				url += "&searchWord=" + oldSearchWord;
			}

			if (!isBlank(oldCategoryNo)) {
				$("select[name='categoryNo']").val(oldCategoryNo);
				url += "&categoryNo=" + oldCategoryNo;
			}

			if (!isBlank(oldSortCategory)) {
				$("select[name='sortCategory']").val(oldSortCategory);
				url += "&sortCategory=" + oldSortCategory;
			}
			
			if (!isBlank(oldPriceMin)) {
				$("input#price_min_input").val(oldPriceMin);
				url += "&priceMin=" + oldPriceMin;
			}
			
			if (!isBlank(oldPriceMax)) {
				$("input#price_max_input").val(oldPriceMax);
				url += "&priceMax=" + oldPriceMax;
			}
			
			if (!isBlank(oldDateMin)) {
				$("input#date_min_input").val(oldDateMin);
				url += "&dateMin=" + oldDateMin;
			}
			
			if (!isBlank(oldDateMax)) {
				$("input#date_max_input").val(oldDateMax);
				url += "&dateMax=" + oldDateMax;
			}
		}
		
		// 페이징 처리 버튼 이벤트
		$(document).on("click", "a.page_button", function() {
			const page = $(this).data("page");

			location.href = "productManage.trd?curPage="+ page + url;
		});

	});
</script>
</body>

</html>