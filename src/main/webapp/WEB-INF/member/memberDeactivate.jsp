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



<script type="text/javascript" src="<%= ctxPath%>/js/member/register.js"></script>

</head>
<body>

<jsp:include page="/WEB-INF/header.jsp" />

<div id="container">

	<h2>휴면 해제 하기</h2>
	
	<div id="update_box">
		<h3>휴대폰 문자인증</h3>
     	<!-- 2단계: 인증 코드 입력 -->
	    <div id="codeStep">
	        <div id="authCodeText">인증번호</div>
	        <div id="authCodeContents" class="input-group mb-3">
	            <input type="text" id="authCode" placeholder="인증번호 입력" onfocus="this.placeholder=''" onblur="this.placeholder='인증번호 입력'" >
	            <button id="authCodeButton">인증하기</button>
	        </div>
	        <small id="authCodeRequest">문자로 받은 인증번호를 입력해주세요.</small>
	    </div>
	</div>
	
</div>

<div class="box"> 
    <input type="text" name="mobile" id="mobile" maxlength="60" class="requiredInfo" placeholder="전화번호" size="50%"/>
    <div class="mobile_message message"><i class="fa-solid fa-circle-info"></i>&nbsp;전화번호를 입력해주십시오</div>
    <button type="button" id="codecheck" onclick="sendCode()">인증번호 받기</button>
</div>

<p>휴대폰 인증을 위해 SMS를 보내드립니다.</p>
	<input type="text" name="mobileCheck" id="mobileCheck" maxlength="60" placeholder="인증번호를 입력하세요."/>
	<div class="code_message message"><i class="fa-solid fa-circle-info"></i>&nbsp;</div>
	<button type="button" id="code" onclick="MobileCodeCheck()">인증확인</button>
	<input type="hidden" name="codeCheck" id="codeCheck" value="${sessionScope.certification_code}"/>
<br>

<script>

function sendCode() {// 인증하기를 클릭하면 해당 전화번호로 인증 문자를 보낸다.
	   
//  alert("인증번호 받기를 클릭했습니다.");
  
  $.ajax({
        url: "member/smsSend.trd", //인증하기 버튼을 클릭하면 작성된 '전화번호'로 랜덤문자 인증키를 보낸다. 
        data: {"mobile": $("input#mobile").val()},
        type: "get",
  
        async: true,  
        
        dataType: "json", 
  
        success: function(json) {
        //   alert("인증번호 받기를 성공.");
           
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
        //   console.log("에러");
           alert("code: " + request.status + "\n" + "message: " + request.responseText + "\n" + "error: " + error);
        }
     });
  
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
  
}// end of function sendCode()------------

</script>

</body>
</html>
