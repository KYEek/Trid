<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
    String ctxPath = request.getContextPath();
%>
    
<!-- 직접 만든 CSS -->
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/category/hamburger.css" />

<script src="<%= ctxPath %>/js/jquery-3.7.1.min.js"></script>
<script src="<%= ctxPath %>/js/category/genderbox.js"></script>



<script>
	
	$(document).ready(function() {
		
	 	// 색상 선택 함수
	    $(document).on("click", "div#category_box span", function() {
	        // 모든 요소에서 choose_gender_active 클래스 제거
	        $("div#category_box span").removeClass("choose_gender_active");
	        
	        // 클릭한 요소에만 choose_gender_active 클래스 추가
	        $(this).addClass("choose_gender_active");
	    });
		
	   /* 햄버거 메뉴 눌렀을때 카테고리 */
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

	});// end of $(document).ready(function() ---------------------------------
			
</script>

<body>
<c:set var="categoryList" value="${requestScope.categoryList}" />
	
	
	<!-- a 태그를 쓰지 않아도 됨 -->
	<div id="category_box">
	    <a id="gender_female" href="#">
	    	<span id="gender_female">여성</span>
	    </a>
	    <a id="gender_male" href="#">
	    	<span id="gender_male">남성</span>
	    </a>
	    <a id="all_product" href="${pageContext.request.contextPath}/product/category_list.trd?chooseGender=&chooseType=">
	    	<span id="all_product">전체</span>
	    </a>
	</div>

		<!-- 남성을 클릭했을때 나오는 햄버거 -->
	<ul class="clothing_box" id="male_clothing">
		<a class="top" href="<%= ctxPath%>/product/category_list.trd?chooseGender=0&chooseType=0">
			<span class="clothing_category">
				상의
			</span>
		</a>
		
		<c:forEach var="categoryDTO" items="${categoryList}">
			<%-- "남자" 를 클릭했을 때 --%>
			<c:if test="${categoryDTO.gender == 0}">
				<%-- 상의 --%>
				<c:if test="${categoryDTO.type == 0 }">
					<a href="<%= ctxPath%>/product/category_list.trd?chooseGender=0&chooseType=0&chooseCategoryNo=${categoryDTO.pkCategoryNo}">
						<span>
							&emsp;&emsp;
						</span>
						<span class="clothing_category">
							${categoryDTO.categoryName}
						</span>
					</a>
				</c:if>
			</c:if>
		</c:forEach>
				
		<a class="bottom" href="<%= ctxPath%>/product/category_list.trd?chooseGender=0&chooseType=1">
			<span class="clothing_category">
				하의
			</span>
		</a>		
		<c:forEach var="categoryDTO" items="${categoryList}">
			<%-- "남자" 를 클릭했을 때 --%>
			<c:if test="${categoryDTO.gender == 0}">
				<%-- 하의 --%>
				<c:if test="${categoryDTO.type == 1 }">
						<a href="<%= ctxPath%>/product/category_list.trd?chooseGender=0&chooseType=1&chooseCategoryNo=${categoryDTO.pkCategoryNo}">
							<span>
								&emsp;&emsp;
							</span>
							<span class="clothing_category">
								${categoryDTO.categoryName}
							</span>
						</a>
				</c:if>
			</c:if>
		</c:forEach>				
	</ul>

	<!-- 여성을 클릭했을때 나오는 햄버거 -->
	<ul class="clothing_box" id="female_clothing">
		<a class="top" href="<%= ctxPath%>/product/category_list.trd?chooseGender=1&chooseType=0">
			<span class="clothing_category">
				상의
			</span>
		</a>
		
		<c:forEach var="categoryDTO" items="${categoryList}">
			<%-- "여자" 를 클릭했을 때 --%>
			<c:if test="${categoryDTO.gender == 1}">
				<%-- 상의 --%>
				<c:if test="${categoryDTO.type == 0 }">
					<a href="<%= ctxPath%>/product/category_list.trd?chooseGender=1&chooseType=0&chooseCategoryNo=${categoryDTO.pkCategoryNo}">
						<span>
							&emsp;&emsp;
						</span>
						<span class="clothing_category">
							${categoryDTO.categoryName}
						</span>
					</a>
				</c:if>
			</c:if>
		</c:forEach>
				
		<a class="bottom" href="<%= ctxPath%>/product/category_list.trd?chooseGender=1&chooseType=1">
			<span class="clothing_category">
				하의
			</span>
		</a>		
		<c:forEach var="categoryDTO" items="${categoryList}">
			<%-- "여자" 를 클릭했을 때 --%>
			<c:if test="${categoryDTO.gender == 1}">
				<%-- 하의 --%>
				<c:if test="${categoryDTO.type == 1 }">
					<a href="<%= ctxPath%>/product/category_list.trd?chooseGender=1&chooseType=1&chooseCategoryNo=${categoryDTO.pkCategoryNo}">
						<span>
							&emsp;&emsp;
						</span>
						<span class="clothing_category">
							${categoryDTO.categoryName}
						</span>
					</a>
				</c:if>
			</c:if>
		</c:forEach>				
	</ul>

<script>


</script>
</body>
</html>