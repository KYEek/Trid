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
<script type="text/javascript" src="<%= ctx_Path%>/js/member/util.js"></script>

<style>

div#container {
    border: solid 0px red;
    width: 90%;
	height: 400px;
    margin: 15% auto;
}

input.requiredInfo {
    border: none;
    border-bottom: 2px solid #ccc;
    padding-bottom: 5px;
    margin-bottom: 2%;
    outline: none;
    width: 35%;
    font-size: 10pt;
}


div.box{
 	border: solid 0px blue;
    display: block;
    outline: none;
    font-size: 10pt;
    color: red;
    margin-bottom: 2%;
}

button#goOn {
    text-align: center;
    width: 35%;
    height: 40px;
    background-color: white;
    border: solid 1px black;
    font-size: 13pt;
    margin-top: 3%;
}

button#codecheck,
button#code {
	border: solid 1px gray;
	border-radius: 5px;
	font-size: 9pt;
	text-align: center;
	cursor: pointer;
	padding: 0.2%;
	background-color: white;
}

div#find{
	margin-bottom: 1.5%;
	font-weight: bold;
}

div#mobile {
	margin-bottom: 7%;
	font-size: 13pt;
}

div.code_message {
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

    <form name="FindPwdFrm" method="post">
		<div id="container">
			<div id="find">비밀번호 찾기</div>
			
			<div id="message">문자인증 후 비밀번호를 변경해주십시오.</div>
			
			
			<div class="box"> 
                <input type="text" name="mobile" id="mobile" maxlength="60" class="requiredInfo" placeholder="전화번호" />
                <button type="button" id="codecheck" onclick="sendCode()">인증번호 받기</button>
                <div class="message"><i class="fa-solid fa-circle-info"></i>&nbsp;전화번호를 입력해주십시오</div>
            </div>
	       
	        <input type="text" name="mobileCheck" id="mobileCheck" maxlength="4" class="requiredInfo" placeholder="인증번호" />
	        <button type="button" id="code" onclick="mobileCodeCheck()">인증확인</button>
	        <div class="code_message" style="color:red"><i class="fa-solid fa-circle-info"></i>&nbsp;인증번호 4자리를 입력해주세요.</div>
	        <input type="hidden" name="codeCheck" id="codeCheck" value="${sessionScope.certification_code}"/>
	        <br>
			<button type="button" id="goOn" onclick="goFindPwd()">계속</button>
		</div>
	</form>


	




</body>
</html>