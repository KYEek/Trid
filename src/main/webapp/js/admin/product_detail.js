$(document).ready(function() {

	// 삭제 버튼 클릭 시 이벤트 추가
	$(document).on("click", "#delete_product_button", function() {

		if (confirm("정말로 삭제하시겠습니까?")) {
			const frm = document.delete_product_frm;

			frm.action = "productDelete.trd";
			frm.method = "post";
			frm.submit();

		}
	});
});

// 뒤로가기 실행
function goBack() {
	window.history.go(-1);
}

