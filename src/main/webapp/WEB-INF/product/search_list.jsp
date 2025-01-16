<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>검색 상품리스트</title>

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
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/category/search_list.css" />

</head>
<body>

<jsp:include page="/WEB-INF/search_header.jsp" />

<article id="search_contents">
	
	<!-- 검색 바 -->
	<form name="search_container">
		<div id="search_box">
	        <input type="text" name="search_word" id="search_bar" placeholder="무엇을 찾고 계신가요?" onfocus="this.placeholder=''" onblur="this.placeholder='무엇을 찾고 계신가요?'" />
	    </div>
	</form>
	
    <!-- 검색어 출력 -->
    <div id="search_word">검색어: <strong>${requestScope.searchWord}</strong></div>
    
    <!-- 검색 결과 상품 개수 출력 -->
    <div id="search_product_count">검색된 상품 개수: <strong>${requestScope.searchRowCount}</strong>개</div>
	
	<!-- 상품 리스트 -->
    <div id="container" style="height: 100%;">

    </div>
	

</article>

<script>


$(document).ready(function() {
	
    // #search_bar에 대한 엔터 키 이벤트
    $("#search_bar").bind("keydown", function(e) {
        if (e.keyCode == 13) {
        	console.log("검색어: " + $("#search_bar").val()); // 검색어 출력
            goSearch();
        }
    });//end of $("#search_bar").bind("keydown", function(e) ---------------------
    
    // 시작 데이터 로드
    loadMoreProducts();
    
    /* 무한스크롤 처리 함수 */
    $(window).scroll(function() {
        if ((window).scrollTop() + $(window).height() >= $(document).height()) {
            loadMoreProducts();
        }
    });
    		
    // 상품 리스트 클릭시 해당 상품 상세페이지로 이동
    $(document).on("click", "div#product", function(e){
    	const productNo =($(e.target).closest("div#product").data("type"));
    	
    	location.href="/Trid/product/detail.trd?productNo="+productNo;
    });// end of $(document).on("click", "div#product", function(e) ----------------------

}); // end of $(document).ready(function() { });
	 
// 엔터 입력시 검색 함수
function goSearch() {
    
    const form = document.search_container;
    form.action = "search_list.trd";   // form 태그에 action 이 명기되지 않았으면 현재보이는 URL 경로로 submit 되어진다.
    form.method = "get";   // form 태그에 method 를 명기하지 않으면 "get" 방식이다.
    form.submit();
   
}// end of function goSearch() -------
	
let pageNumber = 1;
let pageSize = 18; // 한 번에 불러올 아이템 수
let searchRowCount = 350;
	
const URL = location.href;
/* 무한스크롤 처리 ajax */
function loadMoreProducts() {
	
	console.log("호출");
	console.log(pageNumber * pageSize);
	console.log(searchRowCount);
	
    if(((pageNumber - 1) * pageSize) < searchRowCount ){
    	$.ajax({
            url: URL,
            type: 'get',
            headers: {
                'ajaxHeader': 'true',
            },
            data: {
                search_word: $("#search_bar").val(),
                pageNumber: pageNumber,
            },
            dataType: 'json',
            success: function(response) {
                console.log("AJAX 응답 데이터:", response);
                pageNumber++; // 다음 페이지 로드 준비
                updateProductList(response);
            },
            error: function(xhr, status, error) {
                console.error("AJAX 요청 실패:", status, error);
                console.log("AJAX 요청 실패 응답 상태 코드:", xhr.status); // 상태 코드 확인
				console.log("AJAX 요청 실패 응답 내용:", xhr.responseText); // 응답 본문 확인
            }
        });	
    }
}// end of function loadMoreProducts() -----------------------

function updateProductList(products) {
	
    const productListDiv = $("div#container");
    
	// 서버에서 받은 JSON 데이터를 기반으로 HTML 생성
    products.forEach(product => {
    	
    	const path = "${pageContext.request.contextPath}" + product.imagePath;
    	const name = product.productName;
    	
    	saerchRowCount = Number(product.saerchRowCount);
    	console.log(name);
    	
        let productHtml = `
            <div id="product" data-type=` + product.productNo + `>
            	<div id="photo">
			        <img src=`
			        	productHtml += path;
			        	
			        	productHtml +=
	                `${path} alt="상품 이미지" style="width: 100%; height: 100%; object-fit: cover;">
	            </div>
	            <div id="productInfo">
	                <div id="name">` + product.productName + `</div>
	                <div id="price">` + product.price + `원</div>
            	</div>
            </div>`;
    	productListDiv.append(productHtml);
    });
}// end of function updateProductList(products) ------------------------

</script>

</body>
</html>