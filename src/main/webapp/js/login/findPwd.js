
$(document).ready(function(){
	
	const email = $("input#email");
	email.focus();
	
});// $(document).ready(function(){})---------------

let isEmail = true;

function goFindPwd(){
	
	// == 이메일 입력란에 값이 들어갔는지 확인하기 시작 == //
	const requiredInfo = $("input.requiredInfo").val().trim();
	const email = $("input#email");
		
	if(requiredInfo.length <= 0 ){
		alert("이메일을 입력하세요.");
		email.focus();
	}
	// == 이메일 입력란에 값이 들어갔는지 확인하기 끝 == //
	
	else{ // 이메일이 입력되었다면 해당 이메일이 tbl_member 에 있는 이메일인지 확인해야한다.
		
		$.ajax({
			url: "findPwdEmailCheck.trd", //tbl_member에 해당 이메일을 사용하는 유저가 있는지 알아온다.
			data: {"email": $("input#email").val().trim()}, // data 속성은 http://localhost:9090/MyMVC/member/emailDuplicateCheck.up 로 전송해야할 데이터를 말한다. 
			type: "post",  //  type 을 생략하면 type:"get" 이다.\
			
			async: true,   // async:true 가 비동기 방식을 말한다. async 을 생략하면 기본값이 비동기 방식인 async:true 이다.
			// async:false 가 동기 방식이다. 지도를 할때는 반드시 동기방식인 async:false 을 사용해야만 지도가 올바르게 나온다.   
	
			dataType: "json", // Javascript Standard Object Notation.  dataType은 /MyMVC/member/emailDuplicateCheck.up 로 부터 실행되어진 결과물을 받아오는 데이터타입을 말한다. 
			// 만약에 dataType:"xml" 으로 해주면 /MyMVC/member/emailDuplicateCheck.up 로 부터 받아오는 결과물은 xml 형식이어야 한다. 
			// 만약에 dataType:"json" 으로 해주면 /MyMVC/member/emailDuplicateCheck.up 로 부터 받아오는 결과물은 json 형식이어야 한다. 
	
			success: function(json) {
	
				if (json.isExists) {
					// 입력한 email 이 tbl_member 테이블에 있다면
					alert("이메일을 보내드렸습니다!!");
					$("input#email").val("");
					$("div.message").html("");
					isEmail = false;
				}
				else {
					// 입력한 email 이  tbl_member 테이블에 존재하지 않는 경우라면
					$("div.message").html("사용자가 존재하지 않습니다. 이메일을 재확인해주세요.").css({ "color": "red" });
					isEmail = true;
				}
	
			},
	
			error: function(request, status, error) {
				alert("code: " + request.status + "\n" + "message: " + request.responseText + "\n" + "error: " + error);
			}
		});

	}
	
	
}// end of function goFindPwd()---------------