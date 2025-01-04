

let b_emailcheck_click = false;
// "이메일중복확인" 을 클릭했는지 클릭을 안했는지 여부를 알아오기 위한 용도

let b_email_change = false;
// 이메일값을 변경했는지 여부를 알아오기 위한 용도

let isNewEmail = true;

let isCurrentPwd = false;

$(document).ready(function() {

	const currentPwd = $("input#currentPwd");  
	currentPwd.focus();// 첫 화면에 focus 주기



	// "이메일중복확인" 을 클릭했을 때 이벤트 처리하기
	$("span#emailcheck").click(function() {

		b_emailcheck_click = true;
		// "이메일중복확인" 을 클릭했는지 클릭을 안했는지 여부를 알아오기 위한 용도 

		// 변경된 암호가 현재 사용중인 암호이라면 현재 사용중인 암호가 아닌 새로운 암호로 입력해야 한다.!!! 
		
		
		$.ajax({
			url: "emailDuplicateCheck2.trd", //tbl_member에 같은 이메일을 사용하는 사용자가 있는지 알아온다.
			data: {
				"newEmail": $("input#newEmail").val(),
				"pkNum": $("input:hidden[name='pkNum']").val()
			}, // data 속성은 http://localhost:9090/MyMVC/member/emailDuplicateCheck.up 로 전송해야할 데이터를 말한다. 
			type: "post",  //  type 을 생략하면 type:"get" 이다.

			async: true,   // async:true 가 비동기 방식을 말한다. async 을 생략하면 기본값이 비동기 방식인 async:true 이다.
			// async:false 가 동기 방식이다. 지도를 할때는 반드시 동기방식인 async:false 을 사용해야만 지도가 올바르게 나온다.   

			dataType: "json", // Javascript Standard Object Notation.  dataType은 /MyMVC/member/emailDuplicateCheck.up 로 부터 실행되어진 결과물을 받아오는 데이터타입을 말한다. 
			// 만약에 dataType:"xml" 으로 해주면 /MyMVC/member/emailDuplicateCheck.up 로 부터 받아오는 결과물은 xml 형식이어야 한다. 
			// 만약에 dataType:"json" 으로 해주면 /MyMVC/member/emailDuplicateCheck.up 로 부터 받아오는 결과물은 json 형식이어야 한다. 

			success: function(json) {

				if (json.isExists) {
					// 입력한 email 이 이미 사용중이라면
					$("div.email_message").html($("input#newEmail").val() + " 은 현재 사용중 이므로 다른 이메일을 입력하세요").css({ "color": "red" });
					$("input#newEmail").val("");
					isNewEmail = false;
				}
				else {
					// 입력한 email 이 존재하지 않는 경우라면 
					$("div.email_message").html($("input#newEmail").val() + " 은 사용가능 합니다").css({ "color": "navy" });
					isNewEmail = true;
				}

			},

			error: function(request, status, error) {
				alert("code: " + request.status + "\n" + "message: " + request.responseText + "\n" + "error: " + error);
			}
		});
		
	});


	// 이메일값이 변경되면 수정하기 버튼을 클릭시 "이메일중복확인" 을 클릭했는지 클릭안했는지를 알아보기위한 용도 초기화 시키기  
	$("input#newEmail").bind("change", function() {

		b_emailcheck_click = false;
		// "이메일중복확인" 을 클릭했는지 클릭을 안했는지 여부를 알아오기 위한 용도  

		b_email_change = true;
		// 이메일값을 변경했는지 여부를 알아오기 위한 용도
	});
	
	
	
	
	// == 이메일 정규식표현 == //
	const regExp_newEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i; 
			
	const newEmail = regExp_newEmail.test($(e.target).val());
	
	$("div.message").hide();
	$("input#email").focus();
	
	if(!newEmail){
		// 입력하지 않거나 공백만 입력했을 경우
		
		$("input").prop("disabled", true);
		$(e.target).prop("disabled", false); 
		$(e.target).val("").focus();
		
		$(e.target).parent().find("div.email_message").show();
		
	}
	else{
		// 공백이 아닌 글자를 입력했을 경우
		$("input").prop("disabled", false);
		$(e.target).parent().find("div.email_message").hide();
	}

});




function goUpdateEmail() {// 이메일 변경을 클릭하면 호출되는 함수
	
	// 현재 비밀번호가 db에 있는 비밀번호와 같을 경우 함수 실행 가능//
	$.ajax({
			url: "currentPwdCheck.trd", // tbl_member에 pk_member_no와 같은 사용자의 비밀번호를 알아온다.
			data: {
				"currentPwd": $("input#currentPwd").val(),
				"pkNum": $("input:hidden[name='pkNum']").val()
			}, // data 속성은 http://localhost:9090/MyMVC/member/emailDuplicateCheck.up 로 전송해야할 데이터를 말한다. 
			type: "post",  //  type 을 생략하면 type:"get" 이다.

			async: true,   // async:true 가 비동기 방식을 말한다. async 을 생략하면 기본값이 비동기 방식인 async:true 이다.
			// async:false 가 동기 방식이다. 지도를 할때는 반드시 동기방식인 async:false 을 사용해야만 지도가 올바르게 나온다.   

			dataType: "json", // Javascript Standard Object Notation.  dataType은 /MyMVC/member/emailDuplicateCheck.up 로 부터 실행되어진 결과물을 받아오는 데이터타입을 말한다. 
			// 만약에 dataType:"xml" 으로 해주면 /MyMVC/member/emailDuplicateCheck.up 로 부터 받아오는 결과물은 xml 형식이어야 한다. 
			// 만약에 dataType:"json" 으로 해주면 /MyMVC/member/emailDuplicateCheck.up 로 부터 받아오는 결과물은 json 형식이어야 한다. 

			success: function(json) {

				if (json.isExists) {
				//	console.log("비밀번호 사용중");
					// 입력한 비밀번호가 사용중이라면
					$("div.pwd_message").html("비밀번호 확인 완료").css({ "color": "navy" });
					$("input#newEmail").val("");
					isCurrentPwd = true;
				}
				else {
				//	console.log("비밀번호 없음");
					// 입력한 비밀번호가 이 존재하지 않는 경우라면 
					$("div.pwd_message").html("올바른 비밀번호를 입력하세요.").css({ "color": "red" });
					isCurrentPwd = false;
					
				}

			},

			error: function(request, status, error) {
				console.log("에러");
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

	////////////////////////////////////////////////////

	// *** 이메일값을 수정한 다음에 "이메일중복확인" 을 클릭했는지 검사하기 시작 *** //
	if (b_email_change && !b_emailcheck_click) {
		// 이메일값을 수정한 다음에 "이메일중복확인" 을 클릭 안 했을 경우
		alert("이메일 중복확인을 클릭하셔야 합니다.");
		return; // goEdit() 함수를 종료한다.
	}
	// *** 이메일값을 수정한 다음에 "이메일중복확인" 을 클릭했는지 검사하기 끝 *** //

	//////////////////////////////////////////////////////////

	if (isNewEmail && isCurrentPwd) {// 변경한 이메일이 새로운 이메일일 경우
		//   alert("DB에 사용자 정보를 수정하러 간다.");

		const frm = document.emailUpdateFrm;
		frm.action = "updateEmailEnd.trd";
		frm.method = "post";
		frm.submit();
	}

}		