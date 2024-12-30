<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="ctxPath" value="${pageContext.request.contextPath}" />

<link rel="stylesheet" href="${ctxPath}/css/image_carousel.css">

<c:set var="imageList" value="${requestScope.productDTO.imageList}" />

<div class="product-container">
	<div class="product-viewer">
		<c:forEach items="${imageList}" var="imageDTO">
			<div class="product-slide" style="display: block;">
				<img src="${pageContext.request.contextPath}/${imageDTO.imagePath}" />
			</div>
		</c:forEach>
	</div>
	<div class="thumbnail-container">
		<c:forEach items="${imageList}" var="imageDTO">
			<div class="thumbnail">
				<img src="${pageContext.request.contextPath}/${imageDTO.imagePath}" />
			</div>
		</c:forEach>
	</div>
	

</div>

<script>
				$(document).ready(function () {
					let currentIndex = 0;

					const $slides = $('.product-slide');
					const $thumbnails = $('.thumbnail');
					const totalSlides = $slides.length;

					let touchStartY = 0;
					let touchEndY = 0;

					// 첫 슬라이드 표시
					$slides.eq(0).show();
					$thumbnails.eq(0).addClass('active');

					// 썸네일 클릭 이벤트
					$thumbnails.click(function () {
						const newIndex = $thumbnails.index(this);

						if (newIndex !== currentIndex) {
							moveToSlide(newIndex);
						}
					});

					function moveToSlide(newIndex) {
						$slides.fadeOut(300);
						$slides.eq(newIndex).fadeIn(300);

						$thumbnails.removeClass('active');
						$thumbnails.eq(newIndex).addClass('active');

						currentIndex = newIndex;
						
					}

					// 마우스 휠 이벤트
					$('.product-container').on('wheel', function (e) {
						e.preventDefault();

						if (e.originalEvent.deltaY > 0 && currentIndex < totalSlides - 1) {
							moveToSlide(currentIndex + 1);
						} else if (e.originalEvent.deltaY < 0 && currentIndex > 0) {
							moveToSlide(currentIndex - 1);
						}
					});
				});
			</script>