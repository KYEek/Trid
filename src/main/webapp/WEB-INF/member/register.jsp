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
<title>회원가입 페이지</title>

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

<style>

div#container {
    border: solid 0px red;
    width: 90%;
    margin: 5% auto;
}

div#Frm > input {
    border: none;
    border-bottom: 2px solid #ccc;
    padding-bottom: 5px;
    display: block;
    margin-bottom: 2%;
    outline: none;
}


h6{
    border: solid 0px blue;
    margin-bottom: 5%;
}

p{
    font-size: 10pt;
}

div.message {
    display: none; /* 추후에 삭제하고 기능 넣어야함 */
    font: 10pt;
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

label {
    font-size: 10pt;
    border: solid 0px red;
}

span#line {
    text-decoration: underline;
}

</style>

</head>

<body>
<jsp:include page="../header.jsp"/>

    <form action="" name="registerFrm" method="post">
        <div id="container">
            <h4>개인 정보</h4>

            <div id="Frm">
                <input type="text" name="email" id="email" maxlength="60" class="requiredInfo" placeholder="이메일" size="50%"/>
                <div class="message">메세지</div>

                <input type="password" name="pwd" id="pwd" maxlength="15" class="requiredInfo" placeholder="비밀번호" size="50%"/>
                <div class="message">메세지</div>

                <input type="text" name="name" id="name" maxlength="60" class="requiredInfo" placeholder="이름" size="50%"/>
                <div class="message">메세지</div>

                <input type="text" name="mobile" id="mobile" maxlength="60" class="requiredInfo" placeholder="전화번호" size="50%"/>
                <div class="message">메세지</div>
            </div>  

            <br>

            <p>휴대폰 인증을 위해 SMS를 보내드립니다.</p>

            <br>

            <div>
                <div>
                    <input type="checkbox" id="agree" />&nbsp;&nbsp;<label for="agree">모든 항목에 동의</label>
                </div>

                <br>

                <div>
                    <input type="checkbox" id="agree1" />&nbsp;&nbsp;<label for="agree1">*만 14세 이상입니다</label>
                </div>

                <br>

                <div>
                    <input type="checkbox" id="agree2" />&nbsp;&nbsp;<label for="agree2">*<span id="line">필수적 개인정보의 수집 및 이용</span>에 대한 동의</label>
                </div>    
            </div>

            <button type="submit">계정 만들기</button>
        </div>
    </form>
</body>
</html>