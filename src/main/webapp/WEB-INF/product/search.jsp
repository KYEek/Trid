<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>검색</title>

<script type="text/javascript">
    var ctxPath = "${pageContext.request.contextPath}";
</script>


<%-- 공용 CSS --%>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.1/font/bootstrap-icons.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap-4.6.2-dist/css/bootstrap.min.css">

<%-- 공용 JS --%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script src="${pageContext.request.contextPath}/bootstrap-4.6.2-dist/js/bootstrap.bundle.min.js"></script>

<!-- 직접 만든 JQuery -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/category/search.js"></script>

<!-- 직접 만든 CSS -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/category/category_header.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/category/search.css" />

</head>
<body>

<jsp:include page="/WEB-INF/search_header.jsp" />

<article id="search_contents">

	<!-- 안내 멘트 -->
	<div id="coment">
		<span>
			무엇을 찾고 계신가요?
		</span>
	</div>
	
	<!-- 추천 카테고리 박스 -->
	<div id="header_menu">
		<button id="">
			<div>티셔츠</div>
		</button>
		<button id="">
			<div>티셔츠</div>
		</button>
		<button id="">
			<div>티셔츠</div>
		</button>
		<button id="">
			<div>티셔츠</div>
		</button>
		<button id="">
			<div>티셔츠</div>
		</button>
		<button id="">
			<div>티셔츠</div>
		</button>
		<button id="">
			<div>티셔츠</div>
		</button>
		<button id="">
			<div>티셔츠</div>
		</button>
		<button id="">
			<div>티셔츠</div>
		</button>
		<button id="">
			<div>티셔츠</div>
		</button>
	</div>
	
	<!-- 검색 바 -->
	<form name="search_container">
		<div id="search_box">
	        <input type="text" name="search_word" id="search_bar" placeholder="검색..." onfocus="this.placeholder=''" onblur="this.placeholder='검색...'" />
	    </div>
	</form>
	
	<h6>추천 아이템</h6>
		
	<!-- 상품 리스트 -->
    <div id="container" style="overflow-y: auto">
    	<c:forEach var="product" items="${requestScope.recommendProductList}">
	        <div id="product" data-type="${product.productNo}">
	            <div id="photo">
	                <img src="${pageContext.request.contextPath}${product.imageList[0].imagePath}" alt="상품 이미지" style="width: 100%; height: 100%; object-fit: fill;">
	            </div>
	            <div id="productInfo">
		            <div id="name">${product.productName}</div>
		            <div id="price">${product.price}원</div>
	            </div>
	        </div>
        </c:forEach>
        
        <c:if test="${empty requestScope.searchProductList}">
            <tr>
               <td colspan="5">데이터가 존재하지 않습니다.</td>
            </tr>
        </c:if>
    </div>
	

</article>


<script>
	$(document).ready(function() {
    
	    // #search_bar에 대한 엔터 키 이벤트
	    $("#search_bar").bind("keydown", function(e) {
	        if (e.keyCode == 13) { // enter 키
	        	console.log("검색어: " + $("#search_bar").val()); // 검색어 출력
	        	e.preventDefault(); // 기본 동작 방지
	            goSearch();
	        }
	    });// end of $("#search_bar").bind("keydown", function(e) --------------------
	    
	    // 상품 리스트 클릭시 해당 상품 상세페이지로 이동
	    $(document).on("click", "div#product", function(e){
	    	const productNo =($(e.target).closest("div#product").data("type"));
	    	
	    	location.href="/Trid/product/detail.trd?productNo="+productNo;
	    });// end of $(document).on("click", "div#product", function(e) ----------------------
	    
	 }); // end of $(document).ready(function() { });;
	 
	 function goSearch() {
	    
	    const form = document.search_container; // form 객체 가져오기
	    form.action = "search_list.trd";   // form 태그에 action 이 명기되지 않았으면 현재보이는 URL 경로로 submit 되어진다.
	    form.method = "get";   // form 태그에 method 를 명기하지 않으면 "get" 방식이다.
	    form.submit(); // 폼 제출
    
	}// end of function goSearch() -------
</script>

</body>
</html>