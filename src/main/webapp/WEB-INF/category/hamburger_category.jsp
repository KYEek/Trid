<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    String ctxPath = request.getContextPath();
%>
    
<!DOCTYPE html>
<html>
<head>
<title></title>

<!-- 직접 만든 CSS -->
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/category/hamburger.css" />

<script src="<%= ctxPath %>/js/jquery-3.7.1.min.js"></script>
<script src="<%= ctxPath %>/js/category/genderbox.js"></script>

</head>

<script>
	/* 햄버거 메뉴 눌렀을때 카테고리 */
	$(document).ready(function() {
	   $('a#gender_female').click(function(event) {
	       $('ul#male_clothing').hide(); // 남성 카테고리 숨기기
	       $('ul#female_clothing').show(); // 여성 카테고리 보이기
	   });
	
	   $('a#gender_male').click(function(event) {
	       $('ul#female_clothing').hide(); // 여성 카테고리 숨기기
	       $('ul#male_clothing').show(); // 남성 카테고리 보이기
	   });
	});//end of $(document).ready(function() ------------------------------------
			
	let burger = $('.menu-trigger');

	burger.each(function(index) {
		$(this).on('click', function(e) {
			e.preventDefault();
			$(this).toggleClass('active-' + (index + 1));
			
			// 카테고리 메뉴 토글
			$('div#category_box').toggleClass('show');
	        $('ul.clothing_box').toggleClass('show');
		});
	});
			
</script>

<body>
	
	<div id="category_box">
	    <a id="gender_female" href="#">여성</a>
	    <a id="gender_male" href="#">남성</a>
	</div>
	
	
	<ul class="clothing_box" id="female_clothing">
		<a class="top" href="<%= ctxPath%>/product/category_list.trd">상의</a>
				
		<a href="">&emsp;&emsp;티셔츠</a>
		<a href="">&emsp;&emsp;셔츠</a>
		<a href="">&emsp;&emsp;블라우스</a>
		<a href="">&emsp;&emsp;맨투맨</a>
		<a href="">&emsp;&emsp;니트</a>
		<br>
		
		<a class="bottom" href="<%= ctxPath%>/product/category_list.trd">하의</a>
		
		<a href="">&emsp;&emsp;레깅스</a>
		<a href="">&emsp;&emsp;스커트</a>
		<a href="">&emsp;&emsp;스웻팬츠</a>
		<a href="">&emsp;&emsp;치노팬츠</a>
		<a href="">&emsp;&emsp;데님팬츠</a>
	</ul>
	
	<ul class="clothing_box" id="male_clothing">
		<a class="top" href="<%= ctxPath%>/product/category_list.trd">상의</a>
		
		<a href="">&emsp;&emsp;티셔츠</a>
		<a href="">&emsp;&emsp;셔츠</a>
		<a href="">&emsp;&emsp;맨투맨</a>
		<a href="">&emsp;&emsp;후디</a>
		<a href="">&emsp;&emsp;니트</a>
		<br>
		
		<a class="bottom" href="<%= ctxPath%>/product/category_list.trd">하의</a>
		
		<a href="">&emsp;&emsp;반바지</a>
		<a href="">&emsp;&emsp;치노팬츠</a>
		<a href="">&emsp;&emsp;스웻팬츠</a>
		<a href="">&emsp;&emsp;데님팬츠</a>
		<a href="">&emsp;&emsp;코듀로이</a>
	</ul>
	
	<ul class="clothing_box" id="female_clothing">
	    <c:forEach var="category" items="${Categories}">
		    <div id="category">
   		        <a href="<%= ctxPath%>/product/category_list.trd?categoryNo=${category.categoryNo}">
		            &emsp;&emsp;${category.categoryName}
		        </a>
		    </div>
	    </c:forEach>
	</ul>
	
	<ul class="clothing_box" id="male_clothing">
	    <c:forEach var="category" items="${Categories}">
		    <div id="category">
   		        <a href="<%= ctxPath%>/product/category_list.trd?categoryNo=${category.categoryNo}">
		            &emsp;&emsp;${category.categoryName}
		        </a>
		    </div>
	    </c:forEach>
	</ul>
	
	
	
<script>
$(document).ready(function() {
	/* 
	/* 카테고리 클릭시 해당 카테고리 상품 리스트 페이지로 이동 
	$(document).on("click", "ul#female_clothing a", function(e){
		const categoryNo =($(e.target).closest("ul#female_clothing a").data("type"));
		
		location.href="/Trid/product/category_list.trd?categoryNo="+categoryNo;
	});
	
	$(document).on("click", "ul#male_clothing a", function(e){
		const categoryNo =($(e.target).closest("ul#female_clothing a").data("type"));
		
		location.href="/Trid/product/category_list.trd?categoryNo="+categoryNo;
	});
 	*/	
}

</script>
</body>
</html>