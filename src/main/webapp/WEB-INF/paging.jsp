<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/paging.css">

<%-- 페이징 --%>
<c:set var="paging" value="${requestScope.pagingDTO}" />

<nav class="text-center">
	<ul class="pagination">
		<%-- 첫 페이지 --%>
		<div class="pageBtn_box">
			<li><a class="page_button" href="javascript:void(0);" data-page="1"><span aria-hidden="true"><img class="pageBtn"
						src="${pageContext.request.contextPath}/images/logo/go_first.svg" /></span></a></li>
			<%-- 이전 페이지 --%>
			<c:if test="${paging.firstPage ne 1}">
				<li><a class="page_button" href="javascript:void(0);" data-page="${paging.firstPage-1}"><span aria-hidden="true"><img class="pageBtn"
							src="${pageContext.request.contextPath}/images/logo/prev.svg" /></span></a></li>
			</c:if>
		</div>
		<div id="pageNo_box">
			<%-- 페이지 넘버링 --%>
			<c:forEach begin="${paging.firstPage}" end="${paging.lastPage}" var="i">
				<c:if test="${paging.curPage ne i}">
					<li><a class="page_button pageNo" href="javascript:void(0);" data-page="${i}">${i}</a></li>
				</c:if>
				<c:if test="${paging.curPage eq i}">
					<li class="active"><a class="pageNo" href="#">${i}</a></li>
				</c:if>

			</c:forEach>
		</div>
		<div class="pageBtn_box">
			<%-- 다음 페이지 --%>
			<c:if test="${paging.lastPage ne paging.totalPageCount}">
				<li><a class="page_button" href="javascript:void(0);" data-page="${paging.lastPage+1}"><span aria-hidden="true"><img class="pageBtn"
							src="${pageContext.request.contextPath}/images/logo/next.svg" /></span></a></li>
			</c:if>

			<%-- 마지막 페이지 --%>
			<li><a class="page_button" href="javascript:void(0);" data-page="${paging.totalPageCount}"><span aria-hidden="true"><img class="pageBtn"
						src="${pageContext.request.contextPath}/images/logo/go_last.svg" /></span></a></li>
		</div>
	</ul>
</nav>