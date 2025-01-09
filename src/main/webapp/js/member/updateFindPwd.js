
let isCurrentPwd = false; // 현재 비밀번호가 테이블에 존재하는지 알아오기 위한 용도

let pwdValid = false;  // 새로운 비밀번호가 유효한지 알아오기 위한 용도

let pwdCheckValid = false; // 새로운 비밀번호 확인이 유효한지 알아오기 위한 용도 


$(document).ready(function(){
	
	// 비밀번호 인증 포커스
	 $("input#newPwd").focus();
	
	 $("div.newPwd_message").hide();
	 $("div.newPwdCheck_message").hide();
	
	
	// 비밀번호 유효성 검사
	$("input#newPwd").blur((e) => {
		const newPwd = $(e.target).val(); // blur 이벤트가 일어난 pwd input의 값
		$("div.newPwd_message").show();
		// 유효한 비밀번호를 입력하지 않았을 경우
		if(!checkPwd(newPwd)) {
			$("div.newPwd_message").show();
			$("input#newPwd").val("");
			$("input#newPwdCheck").prop("disabled",true);
		}
		else{
			// 유효한 비밀번호를 입력했을 경우
			$("div.newPwd_message").hide();
			$("input#newPwdCheck").prop("disabled",false);
			pwdValid = true;
		}
	});
	
	// 비밀번호 확인 검사	
	$("input#newPwdCheck").blur((e) => {
		const newPwd = $("input#newPwd").val(); // 비밀번호 입력값
		const newPwdCheck = $(e.target).val();  // 비밀번호 확인 입력값
		
		// 초기에 비밀번호 확인 경고 메시지가 보여진다
		$("div.newPwdCheck_message").show(); 
		
		// 비밀번호와 비밀번호 확인 값이 같을 경우
		if(newPwd == newPwdCheck) {
			$("div.newPwdCheck_message").hide();
			pwdCheckValid = true;
		}
		// 같지 않은 경우
		else{
			$("div.newPwdCheck_message").show()
			pwdCheckValid = false;
		}
	});
			
});// end of $(document).ready(function(){})--------------














function goUpdatePwd(){// 기존 비밀번호와 같다면 alert를 띄워준다, 기존 비밀번호를 알아와서 val()과 비교. 새로운 비밀번호시 변경완료(update)
//	alert("저장 버튼을 클릭했습니다.");

	// *** 필수입력사항에 모두 입력이 되었는지 검사하기 시작 *** //
	let e_requiredInfo = false;

	const requiredInfo_list = document.querySelectorAll("input.requiredInfo");

	for (let i = 0; i < requiredInfo_list.length; i++) {
		const val = requiredInfo_list[i].value.trim();

		if (val == "") {
			e_requiredInfo = true;
			alert("새 비밀번호를 입력하세요.");
			break;
		}
	}

	if (e_requiredInfo) {// 모든 입력란에 아무런 값이 들어오지 않았다면 
		return; //함수 종료
	}
	// *** 필수입력사항에 모두 입력이 되었는지 검사하기 끝 *** //
	
	if (pwdValid && pwdCheckValid) { // 새비밀번호가 유효하고 비밀번호확인 값과 같다면 ajax 실행

		// tbl_member에 동일한 전화번호를 가진 회원의 비밀번호를 수정해준다.
		$.ajax({
				url: "/Trid/member/findUpdatePwdEnd.trd", 
				data: {"mobile":$("input#mobile").val(),
					   "newPwd":$("input#newPwd").val()},
				type: "post",
				async: true,
				dataType: "json",
				success: function(response) {
				    if(response.n == 1) {
				        alert("비밀번호가 성공적으로 변경되었습니다.");
				        location.href = "/Trid/login.trd"; // 로그인 페이지로 이동
				    } else {
				        alert("비밀번호 변경에 실패했습니다.");
				    }
				},
				
				error: function(request, error) {
					console.log("에러");
					alert("code: " + request.status + "\n" + "message: " + request.responseText + "\n" + "error: " + error);
				}
			});// end of $.ajax()-------------------------------------------------------------

	}// end of if (pwdValid && pwdCheckValid)----------------------
	else{
		alert("비밀번호 확인값 다름");
		return;
	}
	
}// end of function goUpdatePwd()---------------