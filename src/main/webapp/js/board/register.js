
let register_complete = true;

// "등록"버튼을 눌렀을 때 실행되는 메소드
function goRegister() {
	
	const title = $("input#title").val;
	
	if(title == "") {
		register_complete = false;
	}
	/*
	const content = $("input#content").val.trim();

	if(content == "" || content < 4) {
		register_complete = false;
	}


	*/
	if(register_complete == false) {
		alert("질문을 양식에 맞게 작성하세요!");
		return;
	}
	else {
		const frm = document.registerFrm;
		frm.action = "register.trd";
		frm.method = "post";
		frm.submit();
		
	}

}// end of "등록"버튼을 눌렀을 때 실행되는 메소드


// "취소" 버튼을 눌렀으 때 실행되는 메소드
function goReset() {
	// 리스트 페이지로 보내기
	location.href="/Trid/board/list.trd";
}// end of "취소" 버튼을 눌렀으 때 실행되는 메소드 