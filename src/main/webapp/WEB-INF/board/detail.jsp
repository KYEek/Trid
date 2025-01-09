<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Q&A 게시판 상세 페이지</title>

<%-- 공용 CSS --%>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.1/font/bootstrap-icons.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap-4.6.2-dist/css/bootstrap.min.css">
<%-- 공용 JS --%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script src="${pageContext.request.contextPath}/bootstrap-4.6.2-dist/js/bootstrap.bundle.min.js"></script>

<%-- 개인 CSS 및 JS --%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/board/detail.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/board/detail.js"></script>

</head>
<body>

	<jsp:include page="/WEB-INF/header.jsp" />

    <div id="container">
            
        <div id="main_box">

            <div id="first_row">
            
                <div id="title" class="sidebar">제목</div>
                <div id="user_title">${boardDTO.question_title}</div>
                
            </div>

            <div id="second_row">
            
                <div id="content" class="sidebar">내용</div>
                <div id="user_content">${boardDTO.question_content}</div>

            </div>

            <div id="last_row">
            
                <div id="answer" class="sidebar">답변</div>
                <div id="admin_answer">${boardDTO.question_answer}</div>
                
            </div>
        </div>

        <div id="button">            
            <input type="button" value="돌아가기" onclick="goBack()" />
        </div>    

    </div>  

	<jsp:include page="/WEB-INF/footer.jsp" />

</body>
</html>