
$(document).ready(function(){
	
	const currentPwd = $("input#currentPwd");
	currentPwd.focus();
	
});// end of $(document).ready(function(){})--------------

let isCurrentPwd = false; // 현재 비밀번호가 테이블에 존재하는지 알아오기 위한 용도

let isNewPwd = false; // 새로운 비밀번호가 현재비밀번호와 같지 않은지 알아오기 위한 용도



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
	
	
	
	// "현재 비밀번호" 값에 들어온 비밀번호가 tbl_member 에 있는 비밀번호와 같고, 새로운 비밀번호가 현재 비밀번호와 같지 않을 경우 함수 실행//
	$.ajax({
			url: "pwdCheck.trd", // tbl_member에 회원 일련번호로 사용자의 비밀번호를 알아온다.
			data: {"currentPwd": $("input#currentPwd").val().trim(),
				   "pkNum": $("input:hidden[name='pkNum']").val()},
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
					$("input#currentPwd").val("").focus();
					isCurrentPwd = false;
				}

			},
			error: function(request, status, error) {
			//	console.log("에러");
				alert("code: " + request.status + "\n" + "message: " + request.responseText + "\n" + "error: " + error);
			}
		});// end of $.ajax()-------------------------------------------------------------
		
		
		
		// "새로운 비밀번호" 값이 현재 비밀번호와 같지 않을 경우 함수 실행 //
		$.ajax({
				url: "newPwdCheck.trd", // tbl_member에 pk_member_no와 같은 사용자의 비밀번호를 알아온다.
				data: {"newPwd": $("input#newPwd").val(),
					   "pkNum": $("input:hidden[name='pkNum']").val()}, 
				type: "post",
				async: true,
				dataType: "json",
				success: function(json) {
				//	alert("작동됩니다");
					if (json.isExists) {
						console.log("비밀번호 사용중");
						// 입력한 비밀번호가 사용중이라면
						$("div.newPwd_message").html("새로운 비밀번호를 입력하세요.").css({ "color": "red" });
						$("input#newPwd_message").val("").focus();
						isNewPwd = false;
					}
					else {
						 console.log("비밀번호 없음");
						// 입력한 비밀번호를 사용하고 있지 않는 경우라면
						$("div.newPwd_message").html("변경 가능한 비밀번호 입니다.").css({ "color": "navy" });
						isNewPwd = true;
					}

				},
				error: function(request, status, error) {
					console.log("에러");
					alert("code: " + request.status + "\n" + "message: " + request.responseText + "\n" + "error: " + error);
				}
			});
	
				
				
		if (isNewPwd && isCurrentPwd) {// 현재 비밀번호가 존재하고 현재와 같지 않은 새비밀번호를 작성했을 때 
		//	alert("DB에 사용자 정보를 수정하러 간다.");

			const frm = document.pwdUpdateFrm;
			frm.action = "updatePwdEnd.trd";
			frm.method = "post";
			frm.submit();
		}
	
	
	
}// end of function goUpdatePwd()---------------