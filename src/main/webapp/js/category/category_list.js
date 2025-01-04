$(document).ready(function() {    

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
    $("div#choose_price_box").hide();

    // 가격 버튼 클릭시 토글
    $(document).on("click", "button#choose_price_button", function() {
        $("div#choose_price_box")

		// 가격 설정 박스
        const price_box = $("div#choose_price_box");
		
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

		
	// 필터 적용 버튼 클릭 시 필터 적용 함수
	$("#apply_filter_button").on("click", function() {
	    const selectedColors = [];
	    $(".color_active").each(function() {
	        selectedColors.push($(this).attr("id"));
	    });
		
	    const selectedPrice = $("span#priceLabel").text();

		/*		
			if(Array.isArray(selectedColors)) {
				console.log("배열입니다");
			}
		*/
		
	    console.log("선택한 색상:", selectedColors);  // 색상 로그 출력
	    console.log("선택한 가격:", selectedPrice);   // 가격 로그 출력

	    $.ajax({
	        url: ctxPath + '/product/category_list.trd',
	        type: 'post',
	        headers: {
	            'ajaxHeader': 'true',  // 사용자 정의 헤더 추가 ajax 확인용
	        },
	        data: {
	            chooseColor: selectedColors,  // 선택 색상
	            choosePrice: selectedPrice    // 선택 가격
	        },
	        dataType: 'json',
			success: function(response) {
			    // console.log("서버 응답 데이터 확인:", response); // `productName` 필드 확인
				// 상품 리스트 업데이트 함수 호출
				updateProductList(response);
	        },
			error: function(xhr, status, error) {
				console.error("AJAX 요청 실패:", status, error);
				console.log("AJAX 요청 실패 응답 상태 코드:", xhr.status); // 상태 코드 확인
				console.log("AJAX 요청 실패 응답 내용:", xhr.responseText); // 응답 본문 확인
			}

	    });
	});

	
});// end of $(document).ready(function() -------------------------------

