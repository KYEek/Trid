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
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/category/hamburger_category.css" />

<script src="<%= ctxPath %>/js/jquery-3.7.1.min.js"></script>
<script src="<%= ctxPath %>/js/category/genderbox.js"></script>

</head>
<body>
	
	<div class="category_box">
	    <a class="gender_female" href="#">여성</a>
	    <a class="gender_male" href="#">남성</a>
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
	
	
</body>
</html>