$(document).ready(function() {    
	
	// 카테고리 박스 선택 함수
	$(document).on("click", "div#categoryName", function() {
	    $("div#header_menu")

	    if($(this).hasClass("choose_category_active")) {
	        $(this).removeClass("choose_category_active");
	    }
	    else {
	        $(this).addClass("choose_category_active");
	    }
	});// end of $(document).on("click", "button#choose_color_button", function() ----------------------------

	// 색상 필터 박스 숨김
    $("div#choose_color_box").hide();
	
    // 색상 선택 함수
    $(document).on("click", "button#choose_color_button", function() {
        $("div#choose_color_box")

        const color_box = $("div#choose_color_box");
		console.log("color_box 값 :",color_box)

        if($(this).hasClass("choose_color_active")) {
            color_box.hide();
            $(this).removeClass("choose_color_active");
            alert("색상 선택 해제")
        }
        else {
            color_box.show();
            $(this).addClass("choose_color_active");
            alert("색상 선택됨")
        }
    });// end of $(document).on("click", "button#choose_color_button", function() ----------------------------

    // 색상 다중 선택 함수
    $(document).on("click", "div#choose_color_box button", function() {
        
        console.log($(this));

        if($(this).hasClass("color_active")) {
            $(this).removeClass("color_active");
        }
        else {
            $(this).addClass("color_active");
        }
    });// end of $(document).on("click", "div#choose_color_box button", function() ---------------------
	
	// 가격 선택 함수
    $("div#price_box").hide();

    // 가격 버튼 클릭시 토글
    $(document).on("click", "button#choose_price_button", function() {
        $("div#price_box")

		// 가격 설정 박스
        const price_box = $("div#price_box");
		
        if($(this).hasClass("choose_price_active")) {
            price_box.hide();
            $(this).removeClass("choose_price_active");
            alert("가격 선택 해제")
        }
        else {
            price_box.show();
            $(this).addClass("choose_price_active");
            alert("가격 선택됨")
        }
    });// end of $(document).on("click", "button#choose_color_button", function() ----------------------------

	
});// end of $(document).ready(function() -------------------------------

