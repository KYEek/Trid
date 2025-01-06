<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>카테고리 상품리스트</title>

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
<script type="text/javascript" src="${pageContext.request.contextPath}/js/category/category_list.js"></script>

<!-- 직접 만든 CSS -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/category/category_header.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/category/category_list.css" />

</head>
<body>
<c:set var="categoryList" value="${requestScope.categoryList}" />
<c:set var="chooseGender" value="${requestScope.chooseGender}" />
<c:set var="chooseType" value="${requestScope.chooseType}" />
<c:set var="chooseCategoryNo" value="${requestScope.chooseCategoryNo}" />

<jsp:include page="/WEB-INF/header.jsp" />

<!-- 헤더 카테고리 -->
<div id="header_menu">
	<div id="menu_all">전체</div>

	
	<c:forEach var="categoryDTO" items="${categoryList}">
		<c:if test="${categoryDTO.gender == chooseGender && categoryDTO.type == chooseType }">
		
			<c:if test="${not empty categoryDTO.pkCategoryNo}">
				<a href="${pageContext.request.contextPath}/product/category_list.trd?chooseGender=${categoryDTO.gender}&chooseType=${categoryDTO.type}&chooseCategoryNo=${categoryDTO.pkCategoryNo}">
					<div id="categoryName">${categoryDTO.categoryName}</div>
				</a>
			</c:if>
			
		</c:if>
	</c:forEach>
	
</div>


<div id="filter_container">

	<!-- 색상 선택 버튼 -->
	<button id="choose_color_button" type="button">
		색상
	</button>
	
	<div id="choose_color_box">
		<button id="red" class="onclick" type="button" style="background-color: red;"></button>
		<button id="orange" class="onclick" type="button"  style="background-color: orange "> </button>
		<button id="yellow" class="onclick" type="button" style="background-color: yellow "></button>
		<button id="green" class="onclick" type="button" style="background-color: green "></button>
		<button id="blue" class="onclick" type="button" style="background-color: blue "></button>
		<button id="purple" class="onclick" type="button" style="background-color: purple "></button>
		<button id="gray" class="onclick" type="button" style="background-color: gray "></button>
		<button id="white" class="onclick" type="button" style="background-color: white "></button>
		<button id="black" class="onclick" type="button" style="background-color: black "></button>
		<button id="brown" class="onclick" type="button" style="background-color: brown "></button>
	</div>
	
	<button id="choose_price_button" type="button">
		가격
	</button>
	
	<!-- 가격 범위 설정 bootstrap -->
	<div id="choose_price_box">
	    <div class="row">
	        <div class="col-md-3">
	            <div class="filter-section border p-3 rounded">
	
	                <!-- 가격 범위 설정 -->
	                <div class="ms-3">
	                    <label for="customRange2" class="form-label">가격</label>
	                    <input type="range" class="form-range" min="5000" max="200000" step="500" id="customRange2" onchange="updatePriceLabel()">
	                    <p><span id="priceLabel"></span>원 이하 제품 검색</p>
	                </div>
	            </div>
	        </div>
	    </div>
	</div>
	
	<button id="apply_filter_button" type="button">
		적용
	</button>
	
	<button class="logo" id="2box">
		<img src="../images/logo/2box.svg" alt="2box" />
	</button>
	
	<button class="logo" id="4box">
		<img src="../images/logo/4box.svg" alt="4box" />
	</button>
	
</div>

    <div id="container" style="overflow-y: auto">
       
    	
    </div>

<script>

let pageNumber = 1;
let pageSize = 18; // 한 번에 불러올 아이템 수
let totalRowCount = 19;

$(document).ready(function() {
	
	// 필터 적용 버튼 클릭 시 필터 적용 함수
	$("#apply_filter_button").on("click", function() {
		
		$("div#container").empty();
		pageNumber = 1;
		
	    const selectedColors = [];
	    $(".color_active").each(function() {
	        selectedColors.push($(this).attr("id"));
	    });
		
	    const selectedPrice = $("span#priceLabel").text();

				
	    console.log("선택한 색상:", selectedColors);  // 색상 로그 출력
	    console.log("선택한 가격:", selectedPrice);   // 가격 로그 출력

	    $.ajax({
	        url: ctxPath + '/product/category_list.trd',
	        type: 'post',
	        headers: {
	            'ajaxHeader': 'true',  // 사용자 정의 헤더 추가 ajax 확인용
	        },
	        data: {
	            chooseColor: selectedColors,  // 선택 색상
	            choosePrice: selectedPrice,   // 선택 가격
	        	chooseGender: "${chooseGender}",
	        	chooseType: "${chooseType}",
	        	chooseCategoryNo: "${chooseCategoryNo}"
	        },
	        dataType: 'json',
			success: function(response) {
			    // console.log("서버 응답 데이터 확인:", response); // `productName` 필드 확인
				// 상품 리스트 업데이트 함수 호출
				updateProductList(response);
			    pageNumber++;
	        },
			error: function(xhr, status, error) {
				console.error("AJAX 요청 실패:", status, error);
				console.log("AJAX 요청 실패 응답 상태 코드:", xhr.status); // 상태 코드 확인
				console.log("AJAX 요청 실패 응답 내용:", xhr.responseText); // 응답 본문 확인
			}

	    });
	});
	
    loadMoreProducts();
    /* 무한스크롤 처리 함수 */
    $(window).scroll(function() {
        if ($(window).scrollTop() + $(window).height() >= $(document).height()) {
            loadMoreProducts();
        }
    });

 	// 상품 리스트 클릭시 해당 상품 상세페이지로 이동
    $(document).on("click", "div#product", function(e){
    	const productNo = ($(e.target).closest("div#product").data("type"));
    	
    	location.href="/Trid/product/detail.trd?productNo="+productNo;
    });
    
});// end of $(document).ready(function() -------------------------

const URL = location.href;
		
/* 무한스크롤 처리 ajax */
function loadMoreProducts() {
	
	console.log("호출");
	console.log(pageNumber * pageSize);
	console.log(totalRowCount);
	
    const selectedColors = [];
    $(".color_active").each(function() {
        selectedColors.push($(this).attr("id"));
    });
	
    const selectedPrice = $("span#priceLabel").text();
    
    
    if(((pageNumber - 1) * pageSize) < totalRowCount ){
    	$.ajax({
            url: URL,
            type: 'get',
            headers: {
                'ajaxHeader': 'true',
            },
            data: {
                pageNumber: pageNumber,
                chooseColor: selectedColors,  // 선택 색상
                choosePrice: selectedPrice,   // 선택 가격
            	chooseGender: "${chooseGender}",
            	chooseType: "${chooseType}",
            	chooseCategoryNo: "${chooseCategoryNo}"
            },
            dataType: 'json',
            success: function(response) {
                pageNumber++; // 다음 페이지 로드 준비
                updateProductList(response);
            },
            error: function(xhr, status, error) {
                console.error("AJAX 요청 실패:", status, error);
            }
        });	
    }
}

	// 가격 범위 필터 설정 함수
	function updatePriceLabel() {
	    const rangeInput = document.getElementById('customRange2');
	    const priceLabel = document.getElementById('priceLabel'); // 최대 가격
	    priceLabel.textContent = rangeInput.value;
	    
		console.log("내가 설정한 최대 가격", priceLabel);
	}
	
	// 카테고리에 맞는 상품 리스트 업데이트 함수
	function updateProductList(products) {
 	    // console.log("업데이트할 상품 데이터:", products);

	    const productListDiv = $("div#container");

	    if (!products || products.length === 0) {
	        productListDiv.append("<p>상품이 없습니다.</p>");
	        return;
	    }

	    // 서버에서 받은 JSON 데이터를 기반으로 HTML 생성
	    products.forEach(product => {
	    	
	    	const path = "${pageContext.request.contextPath}" + product.imagePath;
	    	const name = product.productName;
	    	totalRowCount = Number(product.totalRowCount);
	    	console.log(name);
	    	
	    	
	    	// console.log(path);
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
				        <div id="price">` + product.price + `&#8361;</div>
				    </div>
				</div>`;
	        productListDiv.append(productHtml);
	    });
	}// end of function updateProductList(products) --------------------------------
	
</script>

</body>
</html>