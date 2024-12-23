<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
		<c:set var="ctxPath" value="${pageContext.request.contextPath}" />

		<link rel="stylesheet" href="${ctxPath}/css/image_carousel.css">


		<div class="product-container">
			<div class="product-viewer">
			</div>
			<div class="thumbnail-container">
			</div>
			<span id="image_name"></span>
		</div>

		<button id="delete_image_button" type="button">삭제</button>

		<script>
			let currentIndex = 0;
			let $slides = $('.product-slide');
			let $thumbnails = $('.thumbnail');
			let totalSlides = $slides.length;
			let touchStartY = 0;
			let touchEndY = 0;

			let fileList = new DataTransfer();

			$(document).ready(function () {

				// 첫 슬라이드 표시
				$slides.eq(0).show();
				$thumbnails.eq(0).addClass('active');

				// 썸네일 클릭 이벤트
				$(document).on('click', '.thumbnail', function () {
					const newIndex = $thumbnails.index(this);
					if (newIndex !== currentIndex) {
						moveToSlide(newIndex);
					}
				});

				// 마우스 휠 이벤트
				document.querySelector('.product-container').addEventListener('wheel', function (e) {
					e.preventDefault();

					if (e.deltaY > 0 && currentIndex < totalSlides - 1) {
						moveToSlide(currentIndex + 1);
					} else if (e.deltaY < 0 && currentIndex > 0) {
						moveToSlide(currentIndex - 1);
					}
				}, { passive: false });


				$(document).on("click", "button#delete_image_button", function () {
					$slides.eq(currentIndex).remove();
					$thumbnails.eq(currentIndex).remove();

					currentIndex = 0;
					$slides = $('.product-slide');
					$thumbnails = $('.thumbnail');
					totalSlides = $slides.length;

					$slides.eq(0).show();
					$thumbnails.eq(0).addClass('active');


					removeImage();
				});

				$(document).on("click", "button#reset", function () {

					fileList = new DataTransfer();
					$("input#image_input").files = null;

					$slides.remove();
					$thumbnails.remove();


					currentIndex = 0;
					totalSlides = $slides.length;
				});

			});

			function moveToSlide(newIndex) {
				$slides.fadeOut(300);
				$slides.eq(newIndex).fadeIn(300);

				$thumbnails.removeClass('active');
				$thumbnails.eq(newIndex).addClass('active');

				const current_file = fileList.files[currentIndex];
				if (current_file != null) {
					$("span#image_name").val(current_file.name);

				}

				currentIndex = newIndex;
			}

			function readURL(input) {
				console.log("초기 fileList:", fileList.files); // 초기 fileList 상태

				if (input.files && input.files[0]) {

					console.log("선택된 파일들:", input.files); // 선택된 파일 확인

					Array.from(input.files).forEach((file, index) => {

						console.log(`${index}번째 파일:`, file); // 각 파일 정보 확인

						let reader = new FileReader();

						reader.onload = function (e) {

							const src = e.target.result;

							// 백틱이 아닌 따옴표로 변경하고, src를 + 연산자로 연결
							let product_image = '<div class="product-slide" style="display: block;"><img src="' + src + '" /></div>';
							$(".product-viewer").append(product_image);

							let thumbnail_image = '<div class="thumbnail"><img src="' + src + '" /></div>';
							$(".thumbnail-container").append(thumbnail_image);

							currentIndex = 0;
							$slides = $('.product-slide');
							$thumbnails = $('.thumbnail');
							totalSlides = $slides.length;

							moveToSlide(totalSlides - 1);

							fileList.items.add(file);

							console.log(`${index}번째 파일 추가 후 fileList:`, fileList.files); // 파일 추가 후 상태
							console.log("현재 fileList 길이:", fileList.files.length); // 파일 개수 확인
						};

						reader.readAsDataURL(file);

					});

				}

			}

			function removeImage() {
				const newFileList = new DataTransfer();

				Array.from(fileList.files).filter((_, i) => i !== currentIndex)
					.forEach(file => newFileList.items.add(file));

				fileList = newFileList;

				console.log(fileList);

				$("input#image_input").files = newFileList.files;

			}

		</script>