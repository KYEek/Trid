$(document).ready(function() {
	let currentIndex = 0;
	const $slides = $('.product-slide');
	const $thumbnails = $('.thumbnail');
	const totalSlides = $slides.length;
	let touchStartX = 0;
	let touchEndX = 0;

	// 첫 슬라이드 표시
	$slides.eq(0).show();
	$thumbnails.eq(0).addClass('active');

	// 썸네일 클릭 이벤트
	$thumbnails.click(function() {
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
	$('#main_img').on('wheel', function(e) {
		e.preventDefault();

		if (e.originalEvent.deltaY > 0 && currentIndex < totalSlides - 1) {
			moveToSlide(currentIndex + 1);
		} else if (e.originalEvent.deltaY < 0 && currentIndex > 0) {
			moveToSlide(currentIndex - 1);
		}
	});

	// 터치 스와이프 이벤트
	$('.touch-overlay').on('touchstart', function(e) {
		touchStartX = e.originalEvent.touches[0].clientX;
	});

	$('.touch-overlay').on('touchend', function(e) {
		touchEndX = e.originalEvent.changedTouches[0].clientX;
		handleSwipe();
	});

	function handleSwipe() {
		if (touchStartX - touchEndX > 50 && currentIndex < totalSlides - 1) {
			moveToSlide(currentIndex + 1);
		} else if (touchEndX - touchStartX > 50 && currentIndex > 0) {
			moveToSlide(currentIndex - 1);
		}
	}
});