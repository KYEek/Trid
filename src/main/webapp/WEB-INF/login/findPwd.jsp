<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 
<% 
	String ctx_Path = request.getContextPath();
	//	   /Trid
%>     
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 찾기 페이지</title>

<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- Bootstrap CSS -->
<link rel="stylesheet" type="text/css" href="<%= ctx_Path%>/bootstrap-4.6.2-dist/css/bootstrap.min.css" > 

<!-- Font Awesome 6 Icons -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.1/css/all.min.css">

<!-- Optional JavaScript -->
<script type="text/javascript" src="<%= ctx_Path%>/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript" src="<%= ctx_Path%>/bootstrap-4.6.2-dist/js/bootstrap.bundle.min.js" ></script> 

<!-- jQueryUI CSS 및 JS -->
<link rel="stylesheet" type="text/css" href="<%= ctx_Path%>/jquery-ui-1.13.1.custom/jquery-ui.min.css" />
<script type="text/javascript" src="<%= ctx_Path%>/jquery-ui-1.13.1.custom/jquery-ui.min.js"></script> 

<!-- 직접 만든 JS -->
<script type="text/javascript" src="<%= ctx_Path%>/js/login/findPwd.js"></script>


<style>

div#container {
    border: solid 0px red;
    width: 90%;
	height: 400px;
    margin: 15% auto;
}

div#container > input {
    border: none;
    border-bottom: 2px solid #ccc;
    padding-bottom: 5px;
    display: block;
    margin-bottom: 2%;
    outline: none;
    width: 35%;
}

button {
    text-align: center;
    width: 35%;
    height: 40px;
    background-color: white;
    border: solid 1px black;
    font-size: 13pt;
    margin-top: 3%;
}

div#find{
	margin-bottom: 1.5%;
	font-weight: bold;
}

div#mobile {
	margin-bottom: 7%;
	font-size: 13pt;
}

div.message {
    font-size: 10pt;
}

div#message {
	margin: 2% 0 7% 0;
	font-size: 11pt;
}
</style>
</head>
<body>

<jsp:include page="../header.jsp"/>

    <form action="">
		<div id="container">
			<div id="find">비밀번호 찾기</div>
			
			<div id="message">복구 지침이 포함된 이메일을 보내드립니다.</div>
			
			<input type="text" name="email" id="email" maxlength="20" class="requiredInfo" placeholder="이메일" />
	        <div class="message"></div>
	       
			<button type="button" onclick="goFindPwd()">계속</button>
		</div>
	</form>

</body>
</html>