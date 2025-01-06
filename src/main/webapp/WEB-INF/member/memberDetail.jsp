<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>     
<%
	String ctx_Path = request.getContextPath();
%>    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 마이페이지 페이지</title>

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

<script>

function updateEmail(){
	
	location.href="/Trid/member/updateEmail.trd";
}

function updateMobile(){
	
	location.href="/Trid/member/updateMobile.trd";
}

 function updatePwd(){
	
	location.href="/Trid/member/updatePwd.trd";
} 


 function goLogOut(ctx_Path){
	if(confirm("정말 로그아웃 하시겠습니까?")){
		location.href="<%= ctx_Path%>/login/logout.trd";
	}
} 
 
function goMemberDelete(){
//	alert("계정삭제 클릭됨.");
	location.href= "<%= ctx_Path%>/member/memberDelete.trd"
}
 
 
</script>

<style>

div#container{
    border: solid 0px blue;
    width: 70%;
    margin: 10% 0 10% 3%;
}

button > span.line{
    color: gray;
    text-decoration: underline;
    font-size: 10pt;
}

div.button-list{
    border: solid 2px black;
    width: 90%;
    height: 400px;
    margin: 2% 3% 3% 0;
    padding-bottom: 0%;
}

button{
    background-color: white;
    border: none;
}

div.button{
    border: solid 0px red;
    width: 95%;
    height: 60px;
    margin: 0 2% 0 2%;
    padding: 4% 0  0  0;

}

label.arrow {
    float: right;
    color: #595959;
    font-size: 20pt;
}

button[type="button"]{
    margin-bottom: 1%;
    font-size: 13pt;
}

div.change{
    font-size: 10pt;
    padding: 0 0 0 1%;
}

i {
    font-size: 13pt;
}

div.button {
	margin-bottom: 4%;
}

</style>

</head>
<body>
<jsp:include page="../header.jsp"/>

    <div id="container">

        <div>${sessionScope.loginuser.member_name}님의 마이페이지</div>

        <div id="memberInfo">
            <div class="button-list">

                <div class="button address">
                    <button type="button" id="address">주소</button><label for="address" class="arrow"><i class="fa-solid fa-greater-than"></i></label>
                </div>

                <div class="button">
                        <button type="button" id="email" onclick="updateEmail()">이메일</button><label for="email" class="arrow"><i class="fa-solid fa-greater-than"></i></label>
                        <div id="email" class="change">${sessionScope.loginuser.member_email}</div>
                </div>

                <div class="button">
                        <button type="button" id="mobile" onclick="updateMobile()">전화번호</button><label for="mobile" class="arrow"><i class="fa-solid fa-greater-than"></i></label>
                        <div id="mobile" class="change">${fn:substring(sessionScope.loginuser.member_mobile, 0, 3)}&nbsp;-&nbsp;${fn:substring(sessionScope.loginuser.member_mobile, 3, 7)}&nbsp;-&nbsp;${fn:substring(sessionScope.loginuser.member_mobile, 7, 11)}</div>
                </div>

                <div class="button">
                        <button type="button" id="pwd" onclick="updatePwd()">비밀번호</button><label for="pwd" class="arrow"><i class="fa-solid fa-greater-than"></i></label>
                        <div class="change">**********</div>
                </div>        
            </div>
        </div>

        <div>
            <button type="button" class="line" onclick="javascript:goLogOut('<%= ctx_Path%>')"><span class="line">로그아웃</span></button>
            <br>
            <br>
            <button type="button" class="line"><span class="line" onclick="javascript:goMemberDelete('<%= ctx_Path%>')">계정 삭제</span></button>
        </div>
    </div>

</body>
</html>