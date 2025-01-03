let colorNameArr = [];
let colorCodeArr = [];

$(document).ready(function () {

    $("form[name='image_frm']").hide();

    // 색상 추가 버튼 이벤트
    $(document).on("click", "button#add_color_button", function () {
        const colorCode = $("input#color_code_input").val();
        const colorName = $("select#color_select").val();

        colorNameArr.push(colorName);
        colorCodeArr.push(colorCode);

        if (colorCode == "" || colorName == "") {
            return false;
        }

        const html = `<div class="color_item">
							<span>색상명 : ${colorName}</span>
							<span>색상코드 : ${colorCode}</span>
							<div style="border:solid 1px; width:30px; height:30px; background-color : ${colorCode};" ></div>
							<button type="button" class="delete_color_button">색상 제거</button>
					  </div>`;

        $("div#color_box").append(html);
    });

    $(document).on("click", "button.delete_color_button", function () {

        const index = $("button.delete_color_button").index(this);

        colorNameArr.splice(index, 1);
        colorCodeArr.splice(index, 1);

        $(this).parent().remove();
    });

    $("#file_upload_button").click(function () {
        $("#image_input").click();
    });

});

function addColorItem(colorName, colorCode) {
    const html = `<div class="color_item">
						<span>색상명 : ${colorName}</span>
						<span>색상코드 : ${colorCode}</span>
						<div style="border:solid 1px; width:30px; height:30px; background-color : ${colorCode};" ></div>
						<button type="button" class="delete_color_button">색상 제거</button>
					</div>`;

    $("div#color_box").append(html);
}

// 상품 등록 전 등록 요소 유효성 검사
function validateProductDetailsOnRegister() {
    const $productName = $("input:text[name='productName']"); // 상품명
    const $productExplanation = $("textarea#explanation_textarea"); // 상품 설명
    const $productPrice = $("input:text[name='price']"); // 상품 가격
    const $inventory = $("input.size"); // 사이즈 별 재고
    const $category = $("select[name='categoryNo']"); // 카테고리

    // 1. 상품명 유효성 검사
    if (isBlank($productName.val()) || $productName.val().length > 30) {
        alert("올바른 상품명을 입력하세요 (최대 30글자)");
        $productName.val("");
        $productName.focus();
        return false;
    }

    // 2. 상품 설명 유효성 검사
    if (isBlank($productExplanation.val()) || $productExplanation.val().length > 300) {
        alert("올바른 상품 설명을 입력하세요 (최대 300글자)");
        $productExplanation.val("");
        $productExplanation.focus();
        return false;
    }

    // 3. 상품 가격 유효성 검사
    if (isBlank($productPrice.val()) || !isValidNumber($productPrice.val())) {
        alert("올바른 상품 가격을 입력하세요");
        $productPrice.val("");
        $productPrice.focus();
        return false;
    }

    // 4. 카테고리 유효성 검사
    if (isBlank($category.val())) {
        console.log($category.val());
        alert("카테고리를 선택해주세요");
        return false;
    }

    // 5. 사이즈 재고 유효성 검사
    let inventoryValid = true;

    $inventory.each(function (index, element) {
        const inventory = $(element).val();
        if (isBlank(inventory) || !isValidNumber(inventory)) {
            inventoryValid = false;
            return false;
        }
    });

    if (!inventoryValid) {
        alert("올바른 재고를 입력하세요");
        return false;
    }

    // 6.색상 유효성 검사
    if ($("div#color_box").html() == "") {
        alert("색상을 입력해주세요");
        return false;
    }

    // 7. 이미지 유효성 검사
    if (fileList.items.length == 0) {
        alert("이미지를 업로드해주세요");
        return false;
    }

    return true;

}

// 상품 등록 전 등록 요소 유효성 검사
function validateProductDetailsOnUpdate() {
    const $productName = $("input:text[name='productName']"); // 상품명
    const $productExplanation = $("textarea#explanation_textarea"); // 상품 설명
    const $productPrice = $("input:text[name='price']"); // 상품 가격
    const $inventory = $("input.size"); // 사이즈 별 재고

    // 1. 상품명 유효성 검사
    if (isBlank($productName.val()) || $productName.val().length > 30) {
        alert("올바른 상품명을 입력하세요 (최대 30글자)");
        $productName.val("");
        $productName.focus();
        return false;
    }

    // 2. 상품 설명 유효성 검사
    if (isBlank($productExplanation.val()) || $productExplanation.val().length > 300) {
        alert("올바른 상품 설명을 입력하세요 (최대 300글자)");
        $productExplanation.val("");
        $productExplanation.focus();
        return false;
    }

    // 3. 상품 가격 유효성 검사
    if (isBlank($productPrice.val()) || !isValidNumber($productPrice.val())) {
        alert("올바른 상품 가격을 입력하세요");
        $productPrice.val("");
        $productPrice.focus();
        return false;
    }

    // 4. 사이즈 재고 유효성 검사
    let inventoryValid = true;

    $inventory.each(function (index, element) {
        const inventory = $(element).val();
        if (isBlank(inventory) || !isValidNumber(inventory)) {
            inventoryValid = false;
            return false;
        }
    });

    if (!inventoryValid) {
        alert("올바른 재고를 입력하세요");
        return false;
    }

    // 5.색상 유효성 검사
    if ($("div#color_box").html() == "") {
        alert("색상을 입력해주세요");
        return false;
    }

    // 6. 이미지 유효성 검사
    if (fileList.items.length == 0) {
        alert("이미지를 업로드해주세요");
        return false;
    }

    return true;

}