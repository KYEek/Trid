let colorNameArr = [];
let colorCodeArr = [];

$(document).ready(function () {

	$("form[name='image_frm']").hide();

	$(document).on("click", "button#add_image_button", function () {
		$("form[name='image_frm']").show();
	});

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