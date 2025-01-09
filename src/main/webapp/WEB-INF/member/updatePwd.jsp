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
<title>회원 비밀번호 수정 페이지</title>

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
<script type="text/javascript" src="<%= ctx_Path%>/js/member/updatePwd.js"></script>
<script type="text/javascript" src="<%= ctx_Path%>/js/member/util.js"></script> 


<style>

div#container {
    border: solid 0px red;
    width: 90%;
	height: 400px;
    margin: 10% auto;
}

div#container input {
    border: none;
    border-bottom: 2px solid #ccc;
    padding-bottom: 5px;
    display: block;
    outline: none;
    font-size: 13pt;
    width: 35%;
}

div.message {
    font: 10pt;
    /* margin-bottom: 3%; */
}

div#margin {
	margin-bottom: 4%;
}

button {
    text-align: center;
    width: 35%;
    height: 50px;
    background-color: white;
    border: solid 1px black;
    font-size: 13pt;
    margin-top: 3%;
}

button:hover {
    text-align: center;
    width: 35%;
    height: 50px;
    background-color: #ecf2f8;
    border: solid 1px black;
    font-size: 13pt;
    margin-top: 3%;
}

div#update{
	margin-bottom: 4%;
	font-size: 13pt;
	font-weight: bold;
}



</style>

</head>
<body>
<jsp:include page="../header.jsp"/>
	<form name="pwdUpdateFrm" action="">
		<div id="container">
			<div id="update">비밀번호 변경</div>
		
			<div id="margin">
				<input type="text" name="currentPwd" id="currentPwd" maxlength="15" class="requiredInfo" placeholder="현재 비밀번호" />
		        <input type="hidden" name="pkNum" id="pkNum" value="${sessionScope.loginuser.pk_member_no}"/>
		        <div class="currentPwd_message"></div>
	        </div>
			

	        <input type="password" name="newPwd" id="newPwd" maxlength="15" class="requiredInfo" placeholder="새 비밀번호" />
	        <div class="newPwd_message"><i class="fa-solid fa-circle-info"></i>&nbsp;안전한 비밀번호를 입력하세요. 영어대소문자,숫자 및 특수기호를 포함한 최소 8자리 이상이어야 합니다.</div>
	        
	        
	        <input type="password" name="newPwdCheck" id="newPwdCheck" maxlength="15" class="requiredInfo" placeholder="새 비밀번호 확인" />
	        <div class="newPwdCheck_message"><i class="fa-solid fa-circle-info"></i>&nbsp;비밀번호가 일치하지 않습니다.</div>
	        
		
	
			<button type="button" onclick="goUpdatePwd()">저장</button>
		</div>
	</form>
</body>
</html>