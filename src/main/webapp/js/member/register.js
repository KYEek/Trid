



let b_emailcheck_click = false;
// "이메일중복확인" 을 클릭했는지 클릭을 안했는지 여부를 알아오기 위한 용도

let pwdValid = false; // 새로운 비밀번호가 유효한 경우 true

let pwdCheckValid = false; // 확인 비밀번호가 유효한 경우 true

let nameCheckVaild = false; // 이름이 유효한 경우 true

let birthdayCheckVaild = false; // 생년월일이 유효한 경우 true

let mobileCheckVaild = false; // 전화번호가 유효한 경우 true

let codeCheck_click = false; // 인증확인 버튼을 클릭했는지 여부를 알아오기 위한 용도

$(document).ready(function(){
	
	$("div.message").hide();
	$("div#mobile_check_box").hide();
	$("input#email").focus();
	
	// "이메일중복확인" 을 클릭했을 때 이벤트 처리하기
	$("button#emailcheck").click(function() {

		const email = $("input#email").val();
		
		b_emailcheck_click = true;
		
		if(!checkEmail(email)){
		//	alert("이메일 유효성 검사");
			$("div.email_message").html("이메일 형식을 정확하게 입력해주세요").css({ "color": "red" });
			$("div.email_message").show();
			b_emailcheck_click = false;
			$("input#email").val("");
			return false;
		}
		
		$.ajax({
			url: "member/emailDuplicateCheck.trd", 
			//tbl_member 테이블에 같은 이메일을 사용하는 사용자가 있는지 알아온다.
			
			data: {
				"newEmail": $("input#email").val(),
			}, 
			type: "post",  

			async: true,   

			dataType: "json",

			success: function(json) {

				if (json.isExists) {
					// 입력한 email 이 이미 사용중이라면
					$("div.email_message").html($("input#email").val() + " 은 현재 사용중 이므로 다른 이메일을 입력하세요").css({ "color": "red" });
					$("div.email_message").show();
					$("input#email").val("");
					b_emailcheck_click = false;
				}
				else {
					// 입력한 email 이 존재하지 않는 경우라면 
					$("div.email_message").html($("input#email").val() + " 은 사용가능 합니다").css({ "color": "navy" });
					$("div.email_message").show();
					b_emailcheck_click = true;
				}

			},

			error: function(request,error) {
				alert("code: " + request.status + "\n" + "message: " + request.responseText + "\n" + "error: " + error);
			}
		});
					
	});
	
	// 비밀번호 유효성 검사
	$("input#pwd").blur((e) => {
		const pwd = $(e.target).val(); // blur 이벤트가 일어난 pwd input의 값
		$("div.pwd_message").show();
		// 유효한 비밀번호를 입력하지 않았을 경우
		if(!checkPwd(pwd)) {
			$("div.pwd_message").show();
		}
		else{
			// 공백이 아닌 글자를 입력했을 경우
			$("div.pwd_message").hide();
			pwdValid = true;
		}
	});
	
	// 비밀번호 확인 검사	
	$("input#pwdCheck").blur((e) => {
		const pwd = $("input#pwd").val(); // 비밀번호 입력값
		const pwdCheck = $(e.target).val(); // 비밀번호 확인 입력값
		
		// 초기에 비밀번호 확인 경고 메시지가 보여진다
		$("div.pwd_checkMessage").show(); 
		
		// 비밀번호와 비밀번호 확인 값이 같을 경우
		if(pwd == pwdCheck) {
			$("div.pwd_checkMessage").hide();
			pwdCheckValid = true;
		}
		// 같지 않은 경우
		else{
			$("div.pwd_checkMessage").show();
		}
	});
	
	//이름 유효성 검사
	$("input#name").blur((e) => {
		const name = $(e.target).val(); // 이름
		
		if(!checkName(name)) {
			$("div.name_message").show();
		}
		else{
			// 공백이 아닌 글자를 입력했을 경우
			$("div.name_message").hide();
			nameCheckVaild = true;
		}
	});
	
	// 생년월일 유효성 검사
	$("input#birthday").blur((e) => {
		const birthday = $(e.target).val(); // 생년월일
		
		if(!checkBirthday(birthday)) {
			$("div.birthday_message").show();
		}
		else{
			// 공백이 아닌 글자를 입력했을 경우
			$("div.birthday_message").hide();
			birthdayCheckVaild = true;
		}
	});	
	
	// 전화번호 유효성 검사
	$("input#mobile").blur((e) => {
		const mobile = $(e.target).val(); // 전화번호 
		
		if(!checkMobile(mobile)) {
			$("div.mobile_message").show();
		}
		else {
			// 공백이 아닌 글자를 입력했을 경우
			$("div.mobile_message").hide();
			mobileCheckVaild = true;
		}
	});		
	
   
   // *** 인증번호에 값을 입력했는지 검사하기 시작 *** // 
   $("input#mobileCheck").blur((e) => {
	
	const regExp_mobileCheck = new RegExp(/^\d{4}$/);
				
	const mobileCheck = regExp_mobileCheck.test($(e.target).val());
		
		if(!mobileCheck) {
			$(e.target).parent().find("div.code_message").show();
		}
		else{
			// 공백이 아닌 글자를 입력했을 경우
			$(e.target).parent().find("div.code_message").hide();
		}
	
	});
   // *** 인증번호에 값을 입력했는지 검사하기 끝 *** //
   
   
   
});// end of $(document).ready(function()----------


function goRegister() {

	
	const checkbox_checked_length = $("input:checkbox[name='agree']:checked").length;
	
	if(checkbox_checked_length != 2){
		alert("이용약관에 모두 동의하셔야 합니다.");
		return;
	}
	
	
	// *** "이메일중복확인" 을 클릭했는지 검사하기 시작 *** //
	   if(!b_emailcheck_click) {
	     // "이메일중복확인" 을 클릭 안 했을 경우
	     alert("이메일 중복확인을 클릭하셔야 합니다.");
	     return;
	   }
	// *** "이메일중복확인" 을 클릭했는지 검사하기 끝 *** //	
		
		if(!pwdValid || !pwdCheckValid) {
			// 비밀번호를 입력하지 않을 경우
			alert("비밀번호를 다시 입력해 주세요.");
			return;
		}

		if (!nameCheckVaild) {
			// 이름을 입력하지 않을 경우
			alert("성명을 입력하여 주세요");
			return;
		}
	
		if (!birthdayCheckVaild) {
			// 생년월일 입력하지 않을 경우
			alert("생년월일 입력하여 주세요");
			return;
		}
	
	
		if (!mobileCheckVaild) {
			// 전화번호를 입력하지 않을 경우
			alert("전화번호를 입력하여 주세요");
			return;
		}
				   	
	
	
   // *** 성별을 선택했는지 검사하기 시작 *** //
   const radio_checked_length = $("input:radio[name='member_gender']:checked").length;  

   if(radio_checked_length == 0){
       alert("성별을 선택하셔야 합니다.");
       return;
   }
   // *** 성별을 선택했는지 검사하기 끝 *** //
	
   // *** 인증번호를 입력했는지 검사하기 시작 *** //
   const code = $("input#mobileCheck").val();
   	
   	if(code == ""){
   		alert("인증번호를 입력하셔야합니다!!");
   		return;
   	}
   // *** 인증번호를 입력했는지 검사하기 끝 *** //
   
   
   // *** "인증번호확인" 을 클릭했는지 검사하기 시작 *** //
   	   if(!codeCheck_click) {
   	     // "인증번호확인" 을 클릭 안 했을 경우
   	     alert("인증번호확인을 클릭하셔야 합니다.");
   	     return;
   	   }
   	// *** "인증번호확인" 을 클릭했는지 검사하기 끝 *** //	

	
	
   const frm = document.registerFrm;
   frm.action = "register.trd";
   frm.method = "post";
   frm.submit();
   
   
   
}// end of function goRegister()----------------



function sendCode() {
	
	// *** 인증번호 받기를 클릭했을 때 인증번호를 전송하기 전에 전화번호가 중복되는지 검사하기 시작 *** //
	$.ajax({
		url: "member/mobileDuplicateCheck.trd", // 인증번호 확인을 클릭하면 전화번호가 중복되는지 검사한다. 
		data: {
			"mobile": $("input#mobile").val()
		},
		type: "post",

		async: true,  
		
		dataType: "json", 

		success: function(json) {
			
			if (json.isExists) {
				// 입력한 mobile 이 이미 사용중이라면
				$("div#mobileDuplicate_message").html("현재 사용중인 전화번호입니다. 번호를 다시 확인해주세요!!").css({ "color": "red", "display":"block"});
				$("input#mobileCheck").val("");
			}
			else {
				// 입력한 mobile 이 존재하지 않는 경우라면 
				$("div#mobileDuplicate_message").hide();
				
				// 
				$.ajax({
					url: "member/smsSend.trd", //인증하기 버튼을 클릭하면 작성된 '전화번호'로 랜덤문자 인증키를 보낸다. 
					data: {"mobile": $("input#mobile").val()},
					type: "get",
			
					async: true,  
					
					dataType: "json", 
			
					success: function(json) {
						
						if(json.success_count == 1) {
							$("input#codeCheck").val(json.certification_code);	
							$("div.code_message").html("");
			                $("div.code_message").html("<span style='color:black; font-weight:bold;'>문자전송이 성공되었습니다.^^</span>");
						    $("div#mobile_check_box").show(); // 문자 전송 성공 시 인증번호 확인 div show
						   
		                }
		                else if(json.error_count != 0) {
		                   $("div.code_message").html("<span style='color:red; font-weight:bold;'>문자전송이 실패되었습니다.ㅜㅜ</span>");
		                }
			               $("div.code_message").show();
			               $("input#mobileCheck").val("");
					},
			
					error: function(request,error) {
					//	console.log("에러");
						alert("code: " + request.status + "\n" + "message: " + request.responseText + "\n" + "error: " + error);
					}
				});
			}
		},
		error: function(request,error) {
		//	console.log("에러");
			alert("code: " + request.status + "\n" + "message: " + request.responseText + "\n" + "error: " + error);
		}
	});	
	
	// *** 인증번호 받기를 클릭했을 때 인증번호를 전송하기 전에 전화번호가 중복되는지 검사하기 끝 *** //
	
	
}// end of function sendCode()------------


// 문자로 받은 인증번호가 일치한지 확인하는 함수
function MobileCodeCheck(){

	const mobileCheck = $("input#mobileCheck").val().trim();
	const codeCheck = $("input#codeCheck").val().trim();
	
	if(!(mobileCheck == codeCheck)){
		alert("인증번호가 잘못 입력되었습니다. 다시 시도하세요.");
	}
	else {
		alert("인증성공!!");
		codeCheck_click = true;
		
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








