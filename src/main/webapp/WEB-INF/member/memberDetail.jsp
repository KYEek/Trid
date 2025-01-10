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
 
 function goAddress(){
		
	location.href="/Trid/member/address.trd";
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

div#container {
	border:solid 0px red;
	margin: 6% 3% 8% 4%;
	width: 80%;
}

div#memberInfo {
	padding: 2% 1% 0 1%;
	border:solid 1px black;
	width: 60%;
	padding-top: 2%;
	margin-bottom: 1%;
	margin-top: 1%;
}

label.arrow {
	width: 100%;
	margin: 0;
	margin-bottom: 1%;

}
div.button {
	width: 100%;
	margin: 0 0 4% 2%;
	cursor: pointer;
}

button.line {
	background-color: white;
	color: gray;
	border: none;
	font-size: 10pt;
	border-bottom: solid 1px gray;
	margin-bottom: 1%;
	font-size: 8pt;
}

button.button {
	border:solid 1px black;
	display: none;
	cursor: pointer;
}

span#name {
	display:inline-block;
	width: 30%;
	margin: auto;
	cursor: pointer;
}

i{
	margin-left: 65%;
	cursor: pointer;
}

div#name {
	margin-left: 1%;
}

div.change{
	font-size: 8pt;
}

</style>

</head>
<body>
<jsp:include page="../header.jsp"/>

    <div id="container">

        <div id="name">${sessionScope.loginuser.member_name}님의 마이페이지</div>

        <div id="memberInfo">

            <div class="button-list">

                <div class="button address">

                <div class="button">
                	<label for="address" class="arrow"><button type="button" id="address" class="button" onclick="goAddress()"></button><span id="name">주소</span><i class="fa-solid fa-greater-than"></i></label>
                	<%-- <div id="address" class="change">${sessionScope.loginuser.member_address}</div> --%>
                </div>
                
                <div class="button">
                    <label for="email" class="arrow"><button type="button" id="email" class="button"  onclick="updateEmail()"></button><span id="name">이메일</span><i class="fa-solid fa-greater-than"></i></label>
                    <div id="email" class="change">${sessionScope.loginuser.member_email}</div>

                </div>

                <div class="button">
                     <label for="mobile" class="arrow"><button type="button" id="mobile" class="button" onclick="updateMobile()"></button><span id="name">전화번호</span><i class="fa-solid fa-greater-than"></i></label>
                     <div id="mobile" class="change">${fn:substring(sessionScope.loginuser.member_mobile, 0, 3)}&nbsp;-&nbsp;${fn:substring(sessionScope.loginuser.member_mobile, 3, 7)}&nbsp;-&nbsp;${fn:substring(sessionScope.loginuser.member_mobile, 7, 11)}</div>
                </div>

                <div class="button">
                     <label for="pwd" class="arrow"><button type="button" id="pwd" class="button" onclick="updatePwd()"></button><span id="name">비밀번호</span><i class="fa-solid fa-greater-than"></i></label>
                     <div class="change">**********</div>
                </div>  
        </div>

        <div>
            <button type="button" class="line" onclick="javascript:goLogOut('<%= ctx_Path%>')">로그아웃</button>
            <br>
            <button type="button" class="line" onclick="javascript:goMemberDelete('<%= ctx_Path%>')">계정 삭제</button>
        </div>
    </div>

</body>
</html>