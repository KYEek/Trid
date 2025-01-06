
let isCurrentMobile = false;

let e_requiredInfo = false;

$(document).ready(function(){
	const newMobile = $("input#newMobile")
	newMobile.focus();
	
	$("div.mobile_message").hide();
	$("div.message").hide();
	
	$("input#newMobile").blur((e) => {
			
		const regExp_newMobile = new RegExp("^(0[2-9]{1,2}|01[016789])[-]*([0-9]{3,4})[-]*([0-9]{4})$");
				
		const newMobile = regExp_newMobile.test($(e.target).val());
		
		if(!newMobile) {
			$("input").prop("disabled", true);
			$(e.target).prop("disabled", false);
			$(e.target).val("").focus();
			
			$(e.target).parent().find("div.message").show();
		}
		else {
			$("input").prop("disabled", false);
			$(e.target).parent().find("div.message").hide();
		}
		
	});	

});// end of $(document).ready(function(){})--------------------------



function goMobileUpdate(){
	
	// *** 필수입력사항에 모두 입력이 되었는지 검사하기 시작 *** //
	const requiredInfo_list = document.querySelector("input.requiredInfo").value.trim();

	if (requiredInfo_list == "") {
		alert("변경하실 새로운 전화번호를 입력하세요.");
		e_requiredInfo = true;
	}
	
	if (e_requiredInfo) {
			return; // 함수 종료
	}
	// *** 필수입력사항에 모두 입력이 되었는지 검사하기 끝 *** //
	
	
	// *** 업데이트할 전화번호를 tbl_member 에 넣어주기 *** // 
	$.ajax({
		url: "updateMobileCheck.trd",
		data: {"newMobile": $("input#newMobile").val(),
			   "pkNum":$("input#pkNum").val()}, 
		type: "post", 

		async: true,   
		dataType: "json",
		
		success: function(json) {

			if (json.isExists) {
				// 입력한 전화번호가 존재하지 않는 경우라면 
				$("div.message").html("변경 가능한 전화번호 입니다.").css({ "color": "navy", "display":"block", "font-size": "10pt "});
				isCurrentMobile = true;
			}
			else {
				// 입력한 전화번호가 사용중이라면
				$("div.message").html($("input#newMobile").val() +"은(는) 기존 전화번호 입니다. 새로운 전화번호를 입력해주세요.").css({ "color": "red" , "display":"block", "font-size": "10pt "});
				$("input#newMobile").val("");
				isCurrentMobile = false;
			}
		},
		
		error: function(request, error) {
			alert("code: " + request.status + "\n" + "message: " + request.responseText + "\n" + "error: " + error);
			isCurrentMobile = false;
		}
	});
	
		if (isCurrentMobile) {// 변경한 전화번호가 새로운 전화번호일 경우

				const frm = document.mobileUpdateFrm;
				frm.action = "updateMobileEnd.trd";
				frm.method = "post";
				frm.submit();
		}	
	
}// end of function goMobileUpdate()------------------


