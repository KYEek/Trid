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
<title>로그인 페이지</title>

<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- Bootstrap CSS -->
<link rel="stylesheet" type="text/css" href="bootstrap-4.6.2-dist/css/bootstrap.min.css" > 

<!-- Font Awesome 6 Icons -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.1/css/all.min.css">

<!-- Optional JavaScript -->
<script type="text/javascript" src="js/jquery-3.7.1.min.js"></script>
<script type="text/javascript" src="bootstrap-4.6.2-dist/js/bootstrap.bundle.min.js" ></script> 

<!-- jQueryUI CSS 및 JS -->
<link rel="stylesheet" type="text/css" href="jquery-ui-1.13.1.custom/jquery-ui.min.css" />
<script type="text/javascript" src="jquery-ui-1.13.1.custom/jquery-ui.min.js"></script> 

<!-- 직접 만든 JS -->
<script type="text/javascript" src="<%= ctx_Path%>/js/login/login.js"></script>

<style>

div#container {
    border: solid 0px red;
    width: 90%;
    margin: 15% auto;
    display: flex;
}

div#Frm > input {
    border: none;
    border-bottom: 2px solid #ccc;
    padding-bottom: 5px;
    display: block;
    margin-bottom: 7%;
    outline: none;
}

div.register{
    border: solid 0px red;
    margin-left: 15%;
    width: 100%;
}

button {
    text-align: center;
    width: 80%;
    height: 35px;
    background-color: white;
    border: solid 1px black;
    font-size: 10pt;
    margin-top: 3%;
}

button[type="button"] {
    border: none;
    font-size: 10pt;
    text-align: left;
}

button.login{
    text-align: center;
    width: 100%;
    height: 35px;
    background-color: white;
    border: solid 1px black;
    font-size: 10pt;
    margin-top: 10%;
    
}

div.message {
    display: none;
}

</style>

</head>
<body>

<jsp:include page="../header.jsp"/>

    <div id="container">
    	<%-- <%@ include file="../header.jsp" %>--%>
    
        <form name="loginFrm" action="<%= ctx_Path%>/login.trd" method="post">
            <h4>고객님의 계정에 엑세스하세요</h4>

            <br>

            <div id="Frm">
                <input type="text" name="email" id="email" maxlength="60" class="requiredInfo" placeholder="이메일" size="50%"/>
                <div class="message">메세지</div>

                <input type="password" name="pwd" id="pwd" maxlength="15" class="requiredInfo" placeholder="비밀번호" size="50%"/>
                <div class="message">메세지</div>
            
            
                <button class="login" type="button" onclick="goLogin()">로그인</button>
            </div>
        <br>
            <button type="button" onclick="findPwd()">비밀번호를 잊으셨습니까?</button>
        </form>

        <div class="register">
            <h4>계정이 필요하세요?</h4>
            <button type="submit" onclick="goRegister()">등록</button>
        </div>
    </div>
</body>
</html>