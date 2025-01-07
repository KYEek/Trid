
let b_emailcheck_click = false;
// "이메일중복확인" 을 클릭했는지 안했는지 여부를 알아오기 위한 용도

let b_email_change = false;
// 이메일값을 변경했는지 여부를 알아오기 위한 용도

let isNewEmail = true;
// 새로운 이메일인지 아닌지 확인하기 위한 용도

let isCurrentPwd = false;
// 현재비밀번호가 맞는지 아닌지 확인하기 위한 용도


$(document).ready(function() {

	const currentPwd = $("input#currentPwd");  
	currentPwd.focus();
	
	$("div.email_message").hide();
	
	
	// "이메일중복확인" 을 클릭했을 때 이벤트 처리하기
	$("span#emailcheck").click(function() {

		const newEmail = $("input#newEmail").val();
		
		b_emailcheck_click = true;
		
		console.log(!checkEmail(newEmail));
		
		if(!checkEmail(newEmail)){
		//	alert("이메일 유효성 검사");
			$("div.message").html("이메일 형식을 정확하게 입력해주세요").css({ "color": "red" });
			b_emailcheck_click = false;
			$("input#newEmail").val("");
		}
		else{
			
			$.ajax({
				url: "emailDuplicateCheck.trd", 
				//tbl_member 테이블에 같은 이메일을 사용하는 사용자가 있는지 알아온다.
				
				data: {
					"newEmail": $("input#newEmail").val()
				}, 
				type: "post",  
	
				async: true,   
	
				dataType: "json",
	
				success: function(json) {
	
					if (json.isExists) {
						// 입력한 email 이 이미 사용중이라면
						$("div.message").html($("input#newEmail").val() + " 은 현재 사용중 이므로 다른 이메일을 입력하세요").css({ "color": "red" });
						$("input#newEmail").val("");
						isNewEmail = false;
					}
					else {
						// 입력한 email 이 존재하지 않는 경우라면 
						$("div.message").html($("input#newEmail").val() + " 은 사용가능 합니다").css({ "color": "navy" });
						isNewEmail = true;
					}
	
				},
	
				error: function(request,error) {
					alert("code: " + request.status + "\n" + "message: " + request.responseText + "\n" + "error: " + error);
				}
			});
		}
		
	});

	$("input#newEmail").bind("change", function() {

		b_emailcheck_click = false;
		// "이메일중복확인" 을 클릭했는지 클릭을 안했는지 여부를 알아오기 위한 용도  

		b_email_change = true;
		// 이메일값을 변경했는지 여부를 알아오기 위한 용도
	});

});// end of $(document).ready(function() --------------------------------------




function goUpdateEmail() {
	
	$.ajax({
			url: "pwdCheck.trd",
			data: {
				"currentPwd": $("input#currentPwd").val(),
				"memberNo": $("input:hidden[name='memberNo']").val()
			}, 
			type: "post",
			
			dataType: "json", 
			
			success: function(json) {

				if (json.isExists) {
					// 입력한 비밀번호가 사용중이라면
					$("div.pwd_message").html("비밀번호 확인 완료").css({ "color": "navy" });
					isCurrentPwd = true;
					
				}
				else {
					// 입력한 비밀번호가 이 존재하지 않는 경우라면 
					$("div.pwd_message").html("올바른 비밀번호를 입력하세요").css({ "color": "red" });
					isCurrentPwd = false;
				}

			},

			error: function(request,error) {
				alert("code: " + request.status + "\n" + "message: " + request.responseText + "\n" + "error: " + error);
				isCurrentPwd = false;
			}
		});
	
	
		
		
		
		
		
	// *** 필수입력사항에 모두 입력이 되었는지 검사하기 시작 *** //
	let e_requiredInfo = false;

	const requiredInfo_list = document.querySelectorAll("input.requiredInfo");

	for (let i = 0; i < requiredInfo_list.length; i++) {
		const val = requiredInfo_list[i].value.trim();

		if (val == "") {
			alert("비밀번호와 변경하실 새 이메일 주소를 모두 입력하세요.");

			e_requiredInfo = true;
			break;
		}
	}

	if (e_requiredInfo) {
		return;
	}
	// *** 필수입력사항에 모두 입력이 되었는지 검사하기 끝 *** //


	// *** 이메일값을 수정한 다음에 "이메일중복확인" 을 클릭했는지 검사하기 시작 *** //
	if (b_email_change && !b_emailcheck_click) {
		// 이메일값을 수정 후 "이메일중복확인" 을 클릭 안 했을 경우
		alert("이메일 중복확인을 클릭하셔야 합니다.");
		return; 
	}
	// *** 이메일값을 수정한 다음에 "이메일중복확인" 을 클릭했는지 검사하기 끝 *** //

	
	if (isNewEmail && isCurrentPwd) {// 변경한 이메일이 새로운 이메일일 경우

		const frm = document.emailUpdateFrm;
		frm.action = "updateEmailEnd.trd";
		frm.method = "post";
		frm.submit();
	}

}// end of function goUpdateEmail()------------------


	