
$(document).ready(function(){
	const newMobile = $("input#newMobile")
	newMobile.focus();
});

let isCurrentMobile = false;

let e_requiredInfo = false;

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
	
	
	// 업데이트할 전화번호를 db(tbl_member)에 넣어주기 // 
	$.ajax({
		url: "updateMobileCheck.trd",
		data: {"newMobile": $("input#newMobile").val(),
			   "pkNum":$("input#pkNum").val()}, 
		type: "post",  //  type 을 생략하면 type:"get" 이다.

		async: true,   // async:true 가 비동기 방식을 말한다. async 을 생략하면 기본값이 비동기 방식인 async:true 이다.
		// async:false 가 동기 방식이다. 지도를 할때는 반드시 동기방식인 async:false 을 사용해야만 지도가 올바르게 나온다.   

		dataType: "json",
		// 만약에 dataType:"xml" 으로 해주면 /MyMVC/member/emailDuplicateCheck.up 로 부터 받아오는 결과물은 xml 형식이어야 한다. 
		// 만약에 dataType:"json" 으로 해주면 /MyMVC/member/emailDuplicateCheck.up 로 부터 받아오는 결과물은 json 형식이어야 한다. 

		success: function(json) {
		//	alert("ajax가 성공적으로 작동됩니다.");

			if (json.isExists) {
				// 입력한 전화번호가 존재하지 않는 경우라면 
				$("div.message").html("변경 가능한 전화번호 입니다.").css({ "color": "navy" });
				isCurrentMobile = true;
			}
			else {
				// 입력한 전화번호가 사용중이라면
				$("div.message").html($("input#newMobile").val() +"은(는) 기존 전화번호 입니다. 새로운 전화번호를 입력해주세요.").css({ "color": "red" });
				$("input#newMobile").val("");
				isCurrentMobile = false;
			}
		},
		
		error: function(request, status, error) {
			alert("code: " + request.status + "\n" + "message: " + request.responseText + "\n" + "error: " + error);
			isCurrentMobile = false;
		}
	});
	
		if (isCurrentMobile) {// 변경한 전화번호가 새로운 전화번호일 경우
			//	alert("DB에 사용자 정보를 수정하러 간다.");

				const frm = document.mobileUpdateFrm;
				frm.action = "updateMobileEnd.trd";
				frm.method = "post";
				frm.submit();
		}	
	
}// end of function goMobileUpdate()------------------


