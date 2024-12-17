$(document).ready(function () {

    // "등록하기" 버튼 클릭 시 등록 페이지로 이동
    $("div#register_btn").click(function() {
        location.href="/Trid/board/register.trd";
    });

});// end of $(document).ready(function() {})---------------------

// 리스트 상세로 보내는 함수(리스트 한 줄 클릭 시 실행)
function go_detail(pk_question_no) {
	
	location.href=`/Trid/board/detail.trd?pk_question_no=${pk_question_no}`;
	
}// end of 리스트 상세로 보내느 함수