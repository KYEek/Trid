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
<title>회원 이메일 수정 페이지</title>

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
<script type="text/javascript" src="<%= ctx_Path%>/js/member/updateEmail.js"></script>
<script type="text/javascript" src="<%= ctx_Path%>/js/member/util.js"></script>

<style>

div#container {
    border: solid 0px red;
    width: 90%;
	height: 400px;
    margin: 10% auto;
}

div#container > input {
    border: none;
    border-bottom: 2px solid #ccc;
    padding-bottom: 5px;
    margin-bottom: 2.5%;
    outline: none;
    width: 35%;
}


button {
    text-align: center;
    width: 35%;
    height: 35px;
    background-color: white;
    border: solid 1px black;
    font-size: 10pt;
    margin-top: 3%;
}

div#updateEmail{
	margin-bottom: 1%;
}

div#currentEmail {
	margin-bottom: 7%;
	font-size: 10pt;
}

span#emailcheck {
	border: solid 1px gray;
	border-radius: 5px;
	font-size: 8pt;
	width: 80px;
	height: 30px;
	text-align: center;
	margin-left: 10px;
	cursor: pointer;
}

div.email_message,
div.pwd_message {
    font-size: 10pt;
}

</style>

</head>
<body>

<jsp:include page="../header.jsp"/>
	<form name="emailUpdateFrm" >
		<div id="container">
			<div id="updateEmail">이메일 변경</div>
			
			<div id="currentEmail">현재 이메일 : <span>${sessionScope.loginuser.member_email}</span></div>
			
			<input type="password" name="currentPwd" id="currentPwd" maxlength="15" class="requiredInfo" placeholder="현재 비밀번호"/>
	        <input type="hidden" name="memberNo" id="pkNum" value="${sessionScope.loginuser.pk_member_no}"/>
	        <div class="pwd_message"></div>
	        
			<br>

	        <input type="text" name="newEmail" id="newEmail" maxlength="30" class="requiredInfo" placeholder="새 이메일 주소"/>
	        <%-- 이메일중복체크 --%>
            <span id="emailcheck">이메일중복확인</span><br>
	        <div class="email_message" style="color:red"><i class="fa-solid fa-circle-info"></i>&nbsp;올바른 이메일을 입력하세요.</div>
			<div class="message" style="color:red"></div>
			
	
			<button type="button" onclick="goUpdateEmail()">이메일 변경</button><!-- 이메일 변경 함수를 클릭하면 호출되는 함수 만들기 -->
			
		</div>
	</form>
</body>
</html>