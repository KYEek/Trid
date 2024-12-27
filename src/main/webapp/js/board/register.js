
let register_complete = true;

// "등록"버튼을 눌렀을 때 실행되는 메소드
function goRegister() {
	
	const title = $("input#title").val().trim();	// "제목" 값 가져오기
	const content = $("input#content").val().trim();	// "내용" 값 가져오기
	
	let register_complete = true;	// 유효성 검사 여부 반환값
	
	// 제목 길이 검사하기
	if(title.length < 5 || title.length > 20) {
		alert("제목은 5~20 글자 이내로 작성해야 합니다.")
		$("input#title").focus();
		register_complete = false;	// 유효성 검사 false
	}
	// 내용 길이 검사하기
	else if (content.length < 5 || content.length > 100) {
		alert("내용은 5~100 글자 이내로 작성해야 합니다.");
		$("input#content").focus();
		register_complete = false;	// 유효성 검사 false
	}
	
	// 유효성 검사를 통과하지 못했을 떄
	if(!register_complete) {
		return;	// "등록" 버튼을 눌렀을 때 실행되는 메소드 종료
	}
	
	// 보내주기
	const frm = document.registerFrm;
	frm.action = "register.trd";
	frm.method = "post";
	frm.submit();
	
}// end of "등록"버튼을 눌렀을 때 실행되는 메소드


// "취소" 버튼을 눌렀으 때 실행되는 메소드
function goReset() {
	// 리스트 페이지로 보내기
	location.href="/Trid/board/list.trd";
}// end of "취소" 버튼을 눌렀으 때 실행되는 메소드 

