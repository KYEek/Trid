
$(document).ready(function(){
	
	// 비밀번호 인증 포커스
	 $("input#currentPwd").focus();
	
	$("div.newPwdCheck_message").hide();
	$("div.newPwd_message").hide();
	
});// end of $(document).ready(function(){})--------------

let isCurrentPwd = false; // 현재 비밀번호가 테이블에 존재하는지 알아오기 위한 용도

let pwdValid = false;  // 새로운 비밀번호가 유효한지 알아오기 위한 용도

let pwdCheckValid = false; // 새로운 비밀번호 확인이 유효한지 알아오기 위한 용도 

function goUpdatePwd(){// 기존 비밀번호와 같다면 alert를 띄워준다, 기존 비밀번호를 알아와서 val()과 비교. 새로운 비밀번호시 변경완료(update)
//	alert("저장 버튼을 클릭했습니다.");

	// *** 필수입력사항에 모두 입력이 되었는지 검사하기 시작 *** //
	let e_requiredInfo = false;

	const requiredInfo_list = document.querySelectorAll("input.requiredInfo");

	for (let i = 0; i < requiredInfo_list.length; i++) {
		const val = requiredInfo_list[i].value.trim();

		if (val == "") {
			alert("현재 비밀번호와 변경하실 새 비밀번호를 모두 입력하세요.");

			e_requiredInfo = true;
			break;
		}
	}

	if (e_requiredInfo) {// 모든 입력란에 아무런 값이 들어오지 않았다면 
		return; //함수 종료
	}
	// *** 필수입력사항에 모두 입력이 되었는지 검사하기 끝 *** //
	
	
	// 현재 비밀번호 잘침
	// 새로운 비밀번호 입력에서 문제가 생
	

		// "현재 비밀번호" 값에 들어온 비밀번호가 tbl_member 에 있는 비밀번호와 같고, 새로운 비밀번호가 현재 비밀번호와 같지 않을 경우 함수 실행//
		$.ajax({
				url: "pwdCheck.trd", // tbl_member에 회원 일련번호로 사용자의 비밀번호를 알아온다.
				data: {
					"currentPwd": $("input#currentPwd").val().trim()
					},
				type: "post",
				async: true,
				dataType: "json",
				success: function(json) {
				//	alert("작동됩니다");
					if (json.isExists) {
					//	console.log("비밀번호 사용중");
						// 입력한 비밀번호가 사용자가 사용하는 비밀번호와 같을 경우라면
						$("div.currentPwd_message").html("비밀번호 확인 완료").css({ "color": "navy" });
						isCurrentPwd = true;
					}
					else {
					//	console.log("비밀번호 없음");
						// 입력한 비밀번호가 사용자가 사용하는 비밀번호와 같지 않을 경우라면
						$("div.currentPwd_message").html("비밀번호를 다시 확인해주세요.").css({ "color": "red" });
						$("input#currentPwd").val("");
						isCurrentPwd = false;
					}

				},
				error: function(request, status, error) {
				//	console.log("에러");
					alert("code: " + request.status + "\n" + "message: " + request.responseText + "\n" + "error: " + error);
				}
			});// end of $.ajax()-------------------------------------------------------------

	
		
		
		// 비밀번호 유효성 검사
		$("input#newPwd").blur((e) => {
			const pwd = $(e.target).val(); // blur 이벤트가 일어난 pwd input의 값
			$("div.newPwd_message").show();
			// 유효한 비밀번호를 입력하지 않았을 경우
			if(!checkPwd(pwd)) {
				$("div.newPwd_message").html(
					'<i class="fa-solid fa-circle-info"></i>&nbsp;안전한 비밀번호를 입력하세요. 영어대소문자,숫자 및 특수기호를 포함한 최소 8자리 이상이어야 합니다.'
				).show();
			}
			else{
				// 공백이 아닌 글자를 입력했을 경우
				$("div.newPwd_message").hide();
				pwdValid = true;
			}
		});
		
		// 비밀번호 확인 검사	
		$("input#newPwdCheck").blur((e) => {
			const newPwd = $("input#newPwd").val(); // 비밀번호 입력값
			const pwdCheck = $(e.target).val(); // 비밀번호 확인 입력값
			
			// 초기에 비밀번호 확인 경고 메시지가 보여진다
			$("div.newPwdCheck_message").show(); 
			
			// 비밀번호와 비밀번호 확인 값이 같을 경우
			if(newPwd == pwdCheck) {
				$("div.newPwdCheck_message").hide();
				pwdCheckValid = true;
			}
			// 같지 않은 경우
			else{
				$("div.newPwdCheck_message").show();
			}
		});
		
		// 직전 비밀번호와 새로운 비밀번호 비교 둘이 같으면 좋은게 나쁜거
		if (pwdValid && pwdCheckValid && isCurrentPwd) {

			if( $("input#currentPwd").val() == $("input#newPwd").val()){
					$("div.newPwd_message").html('<i class="fa-solid fa-circle-info"></i>&nbsp;기존 비밀번호와 동일합니다.')
					.show();
				$("input#newPwd").val("");
				$("input#newPwdCheck").val("");
				
				pwdValid = false;
			
			}
			else {
				$("div.newPwd_message").hide();
			}
		}
	
		
				
		if (pwdValid && pwdCheckValid && isCurrentPwd) {// 현재 비밀번호가 존재하고 현재와 같지 않은 새비밀번호를 작성했을 때 
		//	alert("DB에 사용자 정보를 수정하러 간다.");

			const frm = document.pwdUpdateFrm;
			frm.action = "updatePwdEnd.trd";
			frm.method = "post";
			frm.submit();
		}
	
	
	
}// end of function goUpdatePwd()---------------