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
   
   /////////////////////////////// 캐러셀 js 이벤트 종료 ///////////////////////////////////
   
   // 필요 없는 친구들 감추기
   $("span").hide();
   
   
   // 사이즈 선택
   $("div#size_bar > input.size").on("click", function (e) {
       // 클릭한 사이즈 버튼
      const sizeBtn = $(e.target);

       // 데이터베이스 값 가져오기
       const size = sizeBtn.attr("size"); // DB 에 필요한 사이즈 (숫자로 구분) (0, 1, 2, 3)
       const value = sizeBtn.val(); // 버튼에 표시된 값 (S, M, L, XL)
      
       console.log(`표시된 값: ${value}, 데이터베이스 값: ${size}`);
      // 표시된 값: S, 데이터베이스 값: 0
       // 모든 버튼 초기화
       $("div#size_bar > input").removeClass("clicked");
      

       // 클릭된 버튼 스타일 적용
       sizeBtn.addClass("clicked");
      
   
      
       // DB 에 필요한 사이즈 + 상품상세번호 세팅
      $("input#go_basketProductDetailNo").val(sizeBtn.attr('id'));
      
      // console.log($("input#go_basketProductDetailNo").val());
      
      // console.log(sizeBtn.attr("id"));
   
   });
   
   // 재고가 없는 사이즈 클릭 시
   $("input.noinventory").on("click", function(e){
      
      alert("재고가 없습니다.");
      $(e.target).disabled = true;
      
   });
   
   
   
   ///////////////////////////////////////////////
   
});


