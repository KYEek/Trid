

let isEmail = true;

let b_emailcheck_click = false;
// "이메일중복확인" 을 클릭했는지 클릭을 안했는지 여부를 알아오기 위한 용도

let codeCheck_click = false;
// 인증확인 버튼을 클릭했는지 여부를 알아오기 위한 용도

$(document).ready(function(){
	
	$("div.message").hide();
	$("input#email").focus();
	
	$("input#email").blur((e) => {
		
		const regExp_email = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i; 
		
		const email = regExp_email.test($(e.target).val());
		
		if(!email){
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
	
	
	$("input#pwd").blur((e) => {
		const regExp_pwd = new RegExp(/^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).*$/g);
		
		const pwd = regExp_pwd.test($(e.target).val());
		
		if(!pwd) {
			$("input").prop("disabled", true);
			$(e.target).prop("disabled", false);
			$(e.target).val("").focus();
			
			$(e.target).parent().find("div.pwd_message").show();
		}
		else{
			// 공백이 아닌 글자를 입력했을 경우
			$("input").prop("disabled", false);
			$(e.target).parent().find("div.pwd_message").hide();
		}
		
	});	
	
	
	$("input#name").blur((e) => {
	
		const regExp_name = new RegExp("^[가-힣]{3,10}$");
		
		const name = regExp_name.test($(e.target).val());
		
		if(!name) {
			$("input").prop("disabled", true);
			$(e.target).prop("disabled", false);
			$(e.target).val("").focus();
			
			$(e.target).parent().find("div.name_message").show();
		}
		else{
			// 공백이 아닌 글자를 입력했을 경우
			$("input").prop("disabled", false);
			$(e.target).parent().find("div.name_message").hide();
		}
	});
	
	
	
	$("input#mobile").blur((e) => {
		
		const regExp_mobile = new RegExp("^(0[2-9]{1,2}|01[016789])[-]*([0-9]{3,4})[-]*([0-9]{4})$");
				
		const mobile = regExp_mobile.test($(e.target).val());
		
		if(!mobile) {
			$("input").prop("disabled", true);
			$(e.target).prop("disabled", false);
			$(e.target).val("").focus();
			
			$(e.target).parent().find("div.mobile_message").show();
		}
		else {
			// 공백이 아닌 글자를 입력했을 경우
			$("input").prop("disabled", false);
			$(e.target).parent().find("div.mobile_message").hide();
		}
		
	});	
	
	
	
	
	
	// "이메일중복확인" 을 클릭했을 때 이벤트 처리하기
	$("span#emailcheck").click(function() {

		b_emailcheck_click = true;
		// "이메일중복확인" 을 클릭했는지 클릭을 안했는지 여부를 알아오기 위한 용도 

		// 변경된 암호가 현재 사용중인 암호이라면 현재 사용중인 암호가 아닌 새로운 암호로 입력해야 한다.!!! 
		$.ajax({
			url: "member/emailDuplicateCheck3.trd", 
			data: {
				"email": $("input#email").val().trim(),
				"pkNum": $("input:hidden[name='pkNum']").val()
			}, 
			type: "post",  
			async: true,
			
			dataType: "json", 
			
			success: function(json) {

				if (json.isExists) {
					// 입력한 email 이 이미 사용중이라면
					$("div#email_message").html($("input#email").val().trim() + " 은 현재 사용중 이므로 다른 이메일을 입력하세요.").css({ "color": "red" });
					$("input#email").val("");
					isEmail = false;
				}
				else if(!(json.isExists) && $("input#email").val().trim()){
					// 입력한 email 이 존재하지 않는 경우라면 
					$("div#email_message").html($("input#email").val().trim() + " 은 사용가능 합니다.").css({ "color": "navy" });
					isEmail = true;
				}

			},

			error: function(request, status, error) {
				alert("code: " + request.status + "\n" + "message: " + request.responseText + "\n" + "error: " + error);
			}
		});
		
	});
	
	
	// *** 생년월일의 값을 입력했는지 검사하기 시작 *** //
	$("input#birthday").blur((e) => {
			const regExp_birthday = new RegExp(/^(\d{2})(0[1-9]|1[0-2])(0[1-9]|[12]\d|3[01])$/);
			
			const birthday = regExp_birthday.test($(e.target).val());
			
			if(!birthday) {
				$("input").prop("disabled", true);
				$(e.target).prop("disabled", false);
				$(e.target).val("").focus();
				
				$(e.target).parent().find("div.birthday_message").show();
			}
			else{
				// 공백이 아닌 글자를 입력했을 경우
				$("input").prop("disabled", false);
				$(e.target).parent().find("div.birthday_message").hide();
			}
			
	});	
   // *** 생년월일의 값을 입력했는지 검사하기 끝 *** //

});// end of $(document).ready(function()----------


function goRegister() {
	
	const checkbox_checked_length = $("input:checkbox[name='agree']:checked").length;
	
	if(checkbox_checked_length != 2){
		alert("이용약관에 모두 동의하셔야 합니다.");
		return;
	}
	
	codeCheck_click = true;

   // *** 성별을 선택했는지 검사하기 시작 *** //
   const radio_checked_length = $("input:radio[name='member_gender']:checked").length;  

   if(radio_checked_length == 0){
       alert("성별을 선택하셔야 합니다.");
       return; // 함수를 종료한다.
   }
   // *** 성별을 선택했는지 검사하기 끝 *** //
	
   
   const frm = document.registerFrm;
   frm.action = "/register.trd";
   frm.method = "post";
   frm.submit();
   
   
   
}// end of function goRegister()----------------



function sendCode() {// 인증하기를 클릭하면 해당 전화번호로 인증 문자를 보낸다.
	
//	alert("인증번호 받기를 클릭했습니다.");
	
	$.ajax({
			url: "member/smsSend.trd", //인증하기 버튼을 클릭하면 작성된 '전화번호'로 랜덤문자 인증키를 보낸다. 
			data: {"mobile": $("input#mobile").val()},
			type: "get",
	
			async: true,  
			
			dataType: "json", 
	
			success: function(json) {
			//	alert("인증번호 받기를 성공.");
				
				if(json.success_count == 1) {
					alert("ajax 페이지 이동중");
				$("input#codeCheck").val(json.certification_code);	
				$("div.code_message").html("");
	               $("div.code_message").html("<span style='color:black; font-weight:bold;'>문자전송이 성공되었습니다.^^</span>");
                }
                else if(json.error_count != 0) {
                   $("div.code_message").html("<span style='color:red; font-weight:bold;'>문자전송이 실패되었습니다.ㅜㅜ</span>");
                }
	               $("div.code_message").show();
	               $("input#mobileCheck").val("");
			},
	
			error: function(request, status, error) {
			//	console.log("에러");
				alert("code: " + request.status + "\n" + "message: " + request.responseText + "\n" + "error: " + error);
			}
		});
	
}// end of function sendCode()------------



function MobileCodeCheck(){
	
	alert("인증확인 버튼을 누르셨습니다.");

	const mobileCheck = $("input#mobileCheck").val().trim();
	const codeCheck = $("input#codeCheck").val().trim();
	
	if(!(mobileCheck == codeCheck)){
		codeCheck_click = false;
		alert("인증번호가 잘못 입력되었습니다. 다시 시도하세요.");
	}
	else {
		codeCheck_click = true;
		alert("인증성공!!");
	}
	
	
}// end of function codeCheck()------------------------------





// ==== 체크박스 전체선택/전체해제 ==== //
function func_allCheck(bool){// bool = 체크박스의 체크여부. 체크가 되어지면 true, 체크가 해제되면 false 를 리턴함.
	
	const checkbox_list = document.querySelectorAll("input[name='agree']");// checkbox 는 name 값이 동일.
	
	for(let checkbox of checkbox_list){
		
		checkbox.checked = bool;
		
	}// end of for----------------
	
}// end of function func_allCheck(bool)-----------------------


// === 체크박스 전체선택/전체해제 에서 
//     하위 체크박스에 체크가 1개라도 체크가 해제되면 체크박스 전체선택/전체해제 체크박스도 체크가 해제되고
//     하위 체크박스에 체크가 모두 체크가 되어지면 체크박스 전체선택/전체해제 체크박스도 체크가 되어지도록 하는 것 === //
function func_Check(bool) {
	
//	if(!bool) {
// 또는 	
	if(bool == false) {
		// 체크박스 2개중 클릭한 체크박스가 체크가 해제 되어진 상태로 넘어온 경우
		document.querySelector("input[id='agree1']").checked = false;
	}
	else {
		// 체크박스 2개중 클릭한 체크박스가 체크가 되어진 상태로 넘어온 경우
		const checkbox_list = document.querySelectorAll("input[name='agree']");
		
		let is_all_checked = true;
		for(let checkbox of checkbox_list){ // 체크박스 2개를 반복할때, 해당 체크박스가 체크가 해제 되어진 경우라면
			if(!checkbox.checked){
				is_all_checked = false;
				break;
			}
		}// end of for-----------------------------------
		
		document.querySelector("input[ id='agree1']").checked = is_all_checked;
		
	}
	
}// end of function func_usaCheck(bool){}-----------------------








