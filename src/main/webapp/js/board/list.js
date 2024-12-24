
// 리스트 상세로 보내는 함수(리스트 한 줄 클릭 시 실행)
function go_detail(pk_question_no, question_isprivate, is_writer) {
	
	if(question_isprivate == 0) {
		// "전체공개" 인 경우 질문 상세 페이지로 보내기 
		location.href=`/Trid/board/detail.trd?pk_question_no=${pk_question_no}&question_isprivate=${question_isprivate}`;	
	}
	else if(question_isprivate == 1 && is_writer == true) {
		// "비공개" 이고 작성자와 로그인한 유저가 일치한 경우 질문 상세 페이지로 보내기
		location.href=`/Trid/board/detail.trd?pk_question_no=${pk_question_no}&question_isprivate=${question_isprivate}`;
	}
	else {
		// "비공개" 이고 작성자와 로그인한 유저가 일치하지 않는 경우
		alert("비공개 게시글입니다.");
	}
	
}// end of 리스트 상세로 보내는 함수

function go_register() {

	location.href=`/Trid/board/register.trd`;
	
}