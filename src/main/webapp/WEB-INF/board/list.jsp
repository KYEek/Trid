<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Trid Q&A 게시판</title>

<%-- 공용 CSS --%>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.1/font/bootstrap-icons.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap-4.6.2-dist/css/bootstrap.min.css">
<%-- 공용 JS --%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script src="${pageContext.request.contextPath}/bootstrap-4.6.2-dist/js/bootstrap.bundle.min.js"></script>

<%-- 개인 CSS 및 JS --%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/board/list.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/board/list.js"></script>


</head>
<body>
	<c:set var="paging" value="${requestScope.pagingDTO}"></c:set>
	<jsp:include page="/WEB-INF/header.jsp" />
	
    <div id="container">
		
		<c:if test="${not empty sessionScope.loginuser}">
			<div id="register_btn" class="custom-btn btn-3" onclick="go_register()"><div id="items">질문하기</div></div>
		</c:if>

        <div id="list_header">

            <div id="seq">번호</div>
            <div id="title">제목</div>
            <div id="aswer_status">답변상태</div>
            <div id="register_day">작성일자</div>
            <div id="private">비밀여부</div>

        </div>

		<c:if test="${not empty requestScope.questionList}">
			<c:set value="${paging.firstRow}" var="firstRow"></c:set>
			<c:forEach var="boardDTO" items="${requestScope.questionList}" varStatus="status">
				
				<%-- 글 작성자와 로그인한 유저가 일치한지 비교 --%>
				<c:set var="is_writer" value="${boardDTO.fk_member_no eq sessionScope.loginuser.pk_member_no}"></c:set>
				
				<div class="list_item custom-btn btn-3" onclick="go_detail(${boardDTO.pk_question_no}, ${boardDTO.question_isprivate}, ${is_writer})">
					<div id="items">
					<div id="seq">${firstRow}</div>
					
					<%-- 질문을 '공개' 로 선택한 경우 --%>
					<c:if test="${boardDTO.question_isprivate == 0}">
						<%-- 제목 보여주기 --%>
						<div id="title">${boardDTO.question_title}</div>
					</c:if>
					
	            	<%-- 남이 등록한 "비공개"글일 경우 (제목 감추기) --%>
         			<c:if test="${boardDTO.question_isprivate == 1 && !is_writer}">
         				<%-- 제목 감추기 --%>
						<div id="title">비공개 글입니다.</div>
					</c:if>
	            	
	            	<%-- 로그인한 유저가 등록한 "비공개"글일 경우 (제목 보여주기)  --%>
       	         	<c:if test="${boardDTO.question_isprivate == 1 && is_writer}">
         				<%-- 제목 감추기 --%>
						<div id="title">${boardDTO.question_title}</div>
					</c:if>
	            	
		         
		            <%-- 답변이 아직 안 된 경우 --%>
		            <c:if test="${boardDTO.question_status == 0}">
		            	<div id="aswer_status">답변 중</div>
		            </c:if>
		            
		            <%-- 답변이 완료 된 경우 --%>
					<c:if test="${boardDTO.question_status == 1}">
		            	<div id="aswer_status">답변 완료</div>
		            </c:if>
		            
		            <div id="register_day">${boardDTO.question_registerday.substring(0,10)}</div>
		            
		            <%-- 질문을 '공개' 로 선택한 경우 --%>
		            <c:if test="${boardDTO.question_isprivate == 0}">
		            	<div id="private"></div>
		            </c:if>
		            
		            <%-- 질물은 '비공개' 롤 선택한 경우 --%>
					<c:if test="${boardDTO.question_isprivate == 1}">
		            	<div id="private"><i class="bi bi-lock-fill"></i></div>
		            </c:if>
		            </div>
	            </div>
	            
	            <c:set value="${firstRow + 1}" var="firstRow"></c:set>
			</c:forEach>
			
		</c:if>
		

		
		<!-- START : 페이지네이션  -->
		<nav class="text-center">
			<ul class="pagination">
				<!-- 첫 페이지  -->
				<div class="pageBtn_box">
					<li><a href="list.trd?curPage=1" data-page="1"><span aria-hidden="true"><img class="pageBtn" src="${pageContext.request.contextPath}/images/logo/go_first.svg" /></span></a></li>
					<!-- 이전 페이지 -->
					<c:if test="${paging.firstPage ne 1}">
						<li><a href="list.trd?curPage=${paging.firstPage-1}" data-page="${paging.firstPage-1}"><span aria-hidden="true"><img class="pageBtn" src="${pageContext.request.contextPath}/images/logo/prev.svg" /></span></a></li>
					</c:if>
				</div>
				
				<div id="pageNo_box">
					<!-- 페이지 넘버링  -->
					<c:forEach begin="${paging.firstPage}" end="${paging.lastPage}" var="i" >
						
						<c:if test="${paging.curPage ne i}">
							<li><a class="pageNo"  href="list.trd?curPage=${i}" data-page="${i}">${i}</a></li>
						</c:if>
						
						<c:if test="${paging.curPage eq i}">
							<li class="active"><a class="pageNo active" href="#">${i}</a></li>
						</c:if>
						
					</c:forEach>
				</div>
				
				<!-- 다음  페이지  -->
				<div class="pageBtn_box">
					<c:if test="${paging.lastPage ne paging.totalPageCount}">
					
						<li><a href="list.trd?curPage=${paging.lastPage+1}" data-page="${paging.lastPage+1}"><span aria-hidden="true"><img class="pageBtn" src="${pageContext.request.contextPath}/images/logo/next.svg" /></span></a></li>
					
					</c:if>
					
					<!-- 마지막 페이지 -->
					<li><a href="list.trd?curPage=${paging.totalPageCount}" data-page="${paging.totalPageCount}"><span aria-hidden="true"><img class="pageBtn" src="${pageContext.request.contextPath}/images/logo/go_last.svg" /></span></a></li>
				</div>
			</ul>
		</nav>
		<!-- END : 페이지네이션  -->
		
    </div>
    
    <jsp:include page="/WEB-INF/footer.jsp" />
    
</body>
</html>