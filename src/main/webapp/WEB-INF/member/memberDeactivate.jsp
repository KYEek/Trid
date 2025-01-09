<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    String ctxPath = request.getContextPath();
%>     
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>휴면 해제 페이지</title>

<%-- 직접 만든 CSS --%>
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/member/MemberDeactivate.css" />

</head>
<body>

<jsp:include page="/WEB-INF/header.jsp" />

<form name="deactivateFrm" method="post">

	<div id="container">
	
		<h2>휴면 해제 하기</h2>
		
		<div id="update_box">
			<h3>휴대폰 문자인증</h3>
	     	<%-- 2단계: 인증 코드 입력 --%>
		    <div class="box"> 
			    <button type="button" id="codecheck" onclick="sendCode()">인증번호 받기</button>
			</div>
			
			<p class="box">휴대폰 인증을 위해 SMS를 보내드립니다.</p>
				<input type="text" name="mobileCheck" id="mobileCheck" maxlength="60" placeholder="인증번호를 입력하세요."/>
				<div class="code_message message"><i class="fa-solid fa-circle-info"></i>&nbsp;</div>
				<button type="button" id="code" onclick="MobileCodeCheck()">인증확인</button>
				<input type="hidden" name="codeCheck" id="codeCheck" value="${sessionScope.certification_code}"/>
				
				<button type="button" id="Deactivate" onclick ="goDeactivate()">휴면 해제하기</button>
				
			<br>
		</div>
		
	</div>

</form>


<script>

function sendCode() {
	
		$.ajax({
			url: "./smsSend.trd", //인증하기 버튼을 클릭하면 작성된 '전화번호'로 랜덤문자 인증키를 보낸다. 
			data: {"mobile": "${sessionScope.memberMobile}"},
			type: "post",
	
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
	

}// end of function sendCode()-----------

// 입력값이 공백인지 체크하는 메소드
function isBlank(value) {
    return (value == null) || (value == undefined) || (value.trim() == "");
}


//문자로 받은 인증번호가 일치한지 확인하는 함수
function MobileCodeCheck(){

   const mobileCheck = $("input#mobileCheck").val().trim();
   const codeCheck = $("input#codeCheck").val().trim();
   
   
   if (isBlank(mobileCheck) || isBlank(codeCheck)) {
	   <%-- 인증번호가 공백인 경우 --%>
      alert("인증번호가 잘못 입력되었습니다. 다시 시도하세요.");   
   }
   else if(!(mobileCheck == codeCheck)){
	  <%-- 인증번호가 틀린 경우 --%>
      alert("인증번호가 잘못 입력되었습니다. 다시 시도하세요.");
   }
   else {
      alert("인증성공!!");
      codeCheck_click = true;
   }
   
}// end of function codeCheck()------------------------------


// 휴면 해제를 실행해주는 메소드
function goDeactivate() {
	
	// *** 인증번호를 입력했는지 검사하기 시작 *** //
	const code = $("input#mobileCheck").val();

	if (code == "") {
		alert("인증번호를 입력하셔야합니다!!");
		return;
	}
	// *** 인증번호를 입력했는지 검사하기 끝 *** //

	// *** "인증번호확인" 을 클릭했는지 검사하기 시작 *** //
	if (!codeCheck_click) {
		// "인증번호확인" 을 클릭 안 했을 경우
		alert("인증번호확인을 클릭하셔야 합니다.");
		return;
	}
	// *** "인증번호확인" 을 클릭했는지 검사하기 끝 *** //	

	const frm = document.deactivateFrm;
	frm.action = "memberDeactivate.trd";
	frm.method = "post";
	frm.submit();

}// end of function goRegister()----------------
</script>

</body>
</html>
