<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
 <meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Q&A 게시판 등록 페이지</title>

<%-- 공용 CSS --%>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.1/font/bootstrap-icons.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap-4.6.2-dist/css/bootstrap.min.css">
<%-- 공용 JS --%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script src="${pageContext.request.contextPath}/bootstrap-4.6.2-dist/js/bootstrap.bundle.min.js"></script>

<%-- 개인 CSS 및 JS --%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/board/register.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/board/register.js"></script>

</head>
<body>

	<jsp:include page="/WEB-INF/header.jsp" />

	<div id="container">

        <form name="registerFrm">
            
            <div id="main_box">

                <div id="first_row">
                
                    <div id="title" class="sidebar">제목</div>
                    <input type="text" name="title" id="title" maxlength="20" placeholder="제목은 5~20 글자 이내로 입력하세요."/>
                    
                </div>

                <div id="second_row">
                
                    <div id="isprivate" class="sidebar">공개여부</div>
                    
                    <div id="radio">
                        <input type='radio' name='isprivate' value='0' checked/> 전체공개
                        <input type='radio' name='isprivate' value='1' /> 비공개
                    </div>
                    
                </div>

                <div id="last_row">
                
                    <div id="content" class="sidebar">내용</div>
                    <input type="text" name="content" id="content" maxlength="100" placeholder="내용은 5~100 글자 이내로 입력하세요."/>
                    
                </div>
            </div>

            <div id="button">            
                <input type="button" value="등록" onclick="goRegister()" />
                <input type="reset" value="취소" onclick="goReset()" />
            </div>    
        </form>

    </div>  

</body>
</html>