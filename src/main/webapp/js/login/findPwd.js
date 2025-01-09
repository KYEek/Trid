
let isMobile = false; // 전화번호를 입력했는지 알아오는 용도

let SendCode = false; // 인증번호 받기를 클릭했는지 알아오는 용도

let codeCheck_click = false; // 인증확인 버튼을 클릭했는지 여부를 알아오기 위한 용도

$(document).ready(function(){
	
	const mobile = $("input#mobile");
	mobile.focus();
	
	$("div.message").hide();
	$("div.code_message").hide();

	// 전화번호 유효성 검사
	$("input#mobile").blur((e) => {
		const mobile = $(e.target).val(); // 전화번호 
		$("input#mobileCheck").prop("disabled", true);
		if(!checkMobile(mobile)) {
			$("div.message").show();
		}
		else { // 공백이 아닌 올바른 전화번호를 입력했을 경우
			$("div.message").hide();
		}
	});		
	
   
   // *** 인증번호에 값을 입력했는지 검사하기 시작 *** // 
   $("input#mobileCheck").blur((e) => {
	
	const regExp_mobileCheck = new RegExp(/^\d{4}$/);
				
	const mobileCheck = regExp_mobileCheck.test($(e.target).val());
	
		if(!mobileCheck) {// 공백을 입력했을 경우
			$(e.target).parent().find("div.code_message").show();
		}
		else{ // 공백이 아닌 인증번호를 입력했을 경우
			$(e.target).parent().find("div.code_message").hide();
		}
	
	});
   // *** 인증번호에 값을 입력했는지 검사하기 끝 *** //
	
});// $(document).ready(function(){})---------------

















function sendCode() {
	SendCode = true;
	// *** 인증번호 받기를 클릭했을 때 인증번호를 전송하기 전에 전화번호가 tbl_member 테이블에 존재하는지 검사하기 시작 *** //
	$.ajax({
		url: "/Trid/member/mobileDuplicateCheck.trd", // 인증번호 받기를 클릭하면 전화번호가 중복되는지 검사한다. 
		data: {
			"mobile": $("input#mobile").val()
		},
		type: "post",

		async: true,  
		
		dataType: "json", 

		success: function(json) {
			
			if (json.isExists) { // 입력한 mobile 이 tbl_member 테이블에 있다면
				
				$.ajax({
					url: "/Trid/member/smsSend.trd", //인증하기 버튼을 클릭하면 작성된 '전화번호'로 랜덤문자 인증키를 보낸다. 
					data: {"mobile": $("input#mobile").val()},
					type: "get",
			
					async: true,  
					
					dataType: "json", 
			
					success: function(json) {
						
						if(json.success_count == 1) {
							$("input#codeCheck").val(json.certification_code);	
							$("div.code_message").html("");
			                alert("인증번호를 확인해주세요.");
							$("input#mobileCheck").prop("disabled", false);
					//	    $("div#mobile_check_box").show(); // 문자 전송 성공 시 인증번호 확인 div show
						   
		                }
		                else if(json.error_count != 0) {
		                  alert("인증번호 전송 실패.\n다시 시도해주세요.");
		                }
			               $("div.code_message").show();
			               $("input#mobileCheck").val("");
					},
			
					error: function(request,error) {
					//	console.log("에러");
						alert("code: " + request.status + "\n" + "message: " + request.responseText + "\n" + "error: " + error);
					}
					
					
				});
				
				$("div#message").hide();
				$("input#mobileCheck").focus();
				
			}
			else {
				// 입력한 mobile 이 존재하지 않는 경우라면 
				alert("사용자가 존재하지 않습니다. 올바른 전화번호를 입력해주세요.");
			}
		},
		error: function(request,error) {
		//	console.log("에러");
			alert("code: " + request.status + "\n" + "message: " + request.responseText + "\n" + "error: " + error);
		}
	});	
	
	// *** 인증번호 받기를 클릭했을 때 인증번호를 전송하기 전에 전화번호가 중복되는지 검사하기 끝 *** //
	
	
}// end of function sendCode()------------
	

// 문자로 받은 인증번호가 일치하는지 확인하는 함수
function mobileCodeCheck(){
	
	const mobileCheck = $("input#mobileCheck").val().trim();
	const codeCheck = $("input#codeCheck").val().trim();
	
	if(!(mobileCheck == codeCheck)){
		alert("인증번호가 잘못 입력되었습니다. 다시 시도하세요.");
		$("input#mobileCheck").val("").focus();
	}
	else {
		alert("인증성공!!");
		$("input#mobileCheck").val("");
		codeCheck_click = true;
	}
}// end of function mobileCodeCheck()-----------------------------


function goFindPwd(){
	
	// *** "인증확인" 을 클릭했는지 검사하기 시작 *** //
   	   if(!codeCheck_click) {
   	     // "인증확인" 을 클릭 안 했을 경우
   	     alert("인증확인을 클릭하셔야 합니다.");
   	     return;
   	   }
   	// *** "인증확인" 을 클릭했는지 검사하기 끝 *** //
	
	
	if(SendCode && codeCheck_click){ // 인증번호 받기와 인증확인을 클릭해야 계속 버튼을 눌렀을 때 비밀번호 변경 페이지로 넘어간다.
		
		const frm = document.FindPwdFrm;
		      frm.action = "/Trid/member/findUpdatePwd.trd";
		      frm.method = "post";
		      frm.submit();
	}
	else{
		
	}
	
	
}// end of function goFindPwd()---------------
