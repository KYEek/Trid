<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    String ctxPath = request.getContextPath();
    //    /MyMVC
%>     
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>휴면 해제 페이지</title>

<%-- 직접 만든 CSS --%>
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/member/MemberDeactivate.css" />

</head>
<body>

<jsp:include page="/WEB-INF/header.jsp" />

<div id="container">

	<h2>휴면 해제 하기</h2>
	
	<div id="update_box">
		<h3>휴대폰 문자인증</h3>
     	<!-- 2단계: 인증 코드 입력 -->
	    <div id="codeStep">
	        <div id="authCodeText">인증번호</div>
	        <div id="authCodeContents" class="input-group mb-3">
	            <input type="text" id="authCode" placeholder="인증번호 입력" onfocus="this.placeholder=''" onblur="this.placeholder='인증번호 입력'" >
	            <button id="authCodeButton">인증하기</button>
	        </div>
	        <small id="authCodeRequest">문자로 받은 인증번호를 입력해주세요.</small>
	    </div>
	</div>
	
</div>

</body>
</html>
