<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<% 
	String ctx_Path = request.getContextPath();
	//	   /Trid
%>      
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입 페이지</title>

<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- Bootstrap CSS -->
<link rel="stylesheet" type="text/css" href="bootstrap-4.6.2-dist/css/bootstrap.min.css" > 

<!-- Font Awesome 6 Icons -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.1/css/all.min.css">

<!-- Optional JavaScript -->
<script type="text/javascript" src="<%= ctx_Path%>/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript" src="bootstrap-4.6.2-dist/js/bootstrap.bundle.min.js" ></script> 

<!-- jQueryUI CSS 및 JS -->
<link rel="stylesheet" type="text/css" href="jquery-ui-1.13.1.custom/jquery-ui.min.css" />
<script type="text/javascript" src="jquery-ui-1.13.1.custom/jquery-ui.min.js"></script> 

<script type="text/javascript" src="<%= ctx_Path%>/js/member/register.js"></script> 
	

<style>

div#container {
    border: solid 0px red;
    width: 90%;
    margin: 5% auto;
}

input.requiredInfo {
    border: none;
    border-bottom: 2px solid #ccc;
    display: inline;
    outline: none;
    font-size: 10pt;
}

div.box{
 	border: solid 0px blue;
    display: block;
    outline: none;
    font-size: 10pt;
    color: red;
    margin-bottom: 2%;
}
	
h6{
    border: solid 0px blue;
    margin-bottom: 5%;
}

p{
    font-size: 10pt;
}

div.message {
    font-size: 10pt;
    color: red;
    margin: none;
}

button#button {
    text-align: center;
    width: 35%;
    height: 35px;
    background-color: white;
    border: solid 1px black;
    font-size: 10pt;
    margin-top: 3%;
}

label {
    font-size: 10pt;
}

a#line {
    text-decoration: underline;
}

i{
	font-size: 10pt;
    color: red;
    padding-bottom: 5px;
}


button#code {
	border: solid 1px gray;
	border-radius: 5px;
	font-size: 9pt;
	text-align: center;
	cursor: pointer;
	margin: 1% 0 0 1%;
	padding: 0.2%;
	background-color: white;
}

button#codecheck{
	border: solid 1px gray;
	border-radius: 5px;
	font-size: 9pt;
	text-align: center;
	cursor: pointer;
	padding: 0.2%;
	background-color: white;
}

 input#mobileCheck{
	margin-bottom: 2%;
	border: none;
    border-bottom: 2px solid #ccc;
    outline: none;
    font-size: 10pt;
    width: 20%;
}

div[class='modal-body']{
	font-size: 10pt;
	text-align: left;
}

span#emailcheck {
	border: solid 1px gray;
	border-radius: 5px;
	font-size: 9pt;
	text-align: center;
	cursor: pointer;
	padding: 0.2%;
	background-color: white;
	color: black;
}


input#email {
	display: 
}

</style>

</head>

<body>
<jsp:include page="../header.jsp"/>

    <form action="" name="registerFrm" method="post">
        <div id="container">
            <h5>개인 정보</h5>

            <div id="Frm">
				 
				<div class="box">          
	                <input type="text" name="email" id="email" maxlength="60" class="requiredInfo" placeholder="이메일" size="50%"/>
	                <%-- 이메일중복체크 --%>
            		<span id="emailcheck">이메일중복확인</span><br>
            		<input type="hidden" name="pkNum" id="pkNum" value="${sessionScope.loginuser.pk_member_no}"/>
	                <div class="email_message message"><i class="fa-solid fa-circle-info"></i>&nbsp;올바른 이메일을 입력하세요.</div>
	                 <div id="email_message"></div>
	                
				</div> 
				
				<div class="box"> 
	                <input type="password" name="pwd" id="pwd" maxlength="15" class="requiredInfo" placeholder="비밀번호" size="50%"/>
	                <div class="pwd_message message"><i class="fa-solid fa-circle-info"></i>&nbsp;안전한 비밀번호를 입력하세요. 영어대소문자,숫자 및 특수기호를 포함한 최소 8자리 이상이어야 합니다.</div>
				</div>
				
				<div class="box"> 
	                <input type="text" name="name" id="name" maxlength="60" class="requiredInfo" placeholder="이름" size="50%"/>
	                <div class="name_message message"><i class="fa-solid fa-circle-info"></i>&nbsp;이름을 입력해주십시오</div>
				</div>
				
				<div class="box"> 
	                <input type="text" name="mobile" id="mobile" maxlength="60" class="requiredInfo" placeholder="전화번호" size="50%"/>
	                <div class="mobile_message message"><i class="fa-solid fa-circle-info"></i>&nbsp;전화번호를 입력해주십시오</div>
	                <button type="button" id="codecheck" onclick="sendCode()">인증번호 받기</button>
	                <div id="mobileDuplicate_message" class="message"></div>
            	</div>
            	
            	<div class="box"> 
	                <input type="text" name="member_birthday" id="birthday" maxlength="60" class="requiredInfo" placeholder="생년월일 (예: 970520)" size="50%"/>
	                <div class="birthday_message message"><i class="fa-solid fa-circle-info"></i>&nbsp;생년월일 6자리를 입력해주십시오</div>
            	</div>
            	
            	<div class="box"> 
	                <input type="radio" name="member_gender" value="1" id="male" /><label for="male" style="margin-left: 1.5%; color:black;">남자</label>
                    <input type="radio" name="member_gender" value="2" id="female" style="margin-left: 10%;" /><label for="female" style="margin-left: 1.5%; color:black;">여자</label>
            	</div>
            	
            </div>  
	
            <br>
	
            <p>휴대폰 인증을 위해 SMS를 보내드립니다.</p>
            <input type="text" name="mobileCheck" id="mobileCheck" maxlength="60" placeholder="인증번호를 입력하세요."/>
			<button type="button" id="code" onclick="MobileCodeCheck()">인증확인</button>
			<div class="code_message message"><i class="fa-solid fa-circle-info"></i>&nbsp;인증번호를 입력하세요.</div>
			<input type="hidden" name="codeCheck" id="codeCheck" value="${sessionScope.certification_code}"/>
            <br>
	
            <div>
                <div>
                    <input type="checkbox" id="agree1" onclick="func_allCheck(this.checked)"/>&nbsp;&nbsp;<label for="agree1">모든 항목에 동의</label>
                </div>

                <br>

                <div>
                    <input type="checkbox" name="agree" id="agree2" onclick="func_Check(this.checked)"/>&nbsp;&nbsp;<label for="agree2" >*만 14세 이상입니다</label>
                </div>

                <br>

                <div>
                    <input type="checkbox" name="agree" id="agree3" onclick="func_Check(this.checked)"/>&nbsp;&nbsp;<label for="agree3">*<a id="line" style="cursor: pointer;" data-toggle="modal" data-target="#userIdfind" data-dismiss="modal">필수적 개인정보의 수집 및 이용</a>에 대한 동의</label>
                </div>    
            </div>

            <button id="button" type="button" onclick ="goRegister()">계정 만들기</button>
        </div>
    </form>  
    
  
  
  
  
 
  <div class="modal fade" id="userIdfind" data-backdrop="static"> <%-- 만약에 모달이 안보이거나 뒤로 가버릴 경우에는 모달의 class 에서 fade 를 뺀 class="modal" 로 하고서 해당 모달의 css 에서 zindex 값을 1050; 으로 주면 된다. --%>  
    <div class="modal-dialog">
      <div class="modal-content">
      
        <!-- Modal header -->
        <div class="modal-header">
          <h5 class="modal-title">필수적 개인정보의 수집 및 이용</h5>
          <button type="button" class="close idFindClose" data-dismiss="modal" style="background-color: green">&times;</button>
        </div>
        
        <!-- Modal body -->
        <div class="modal-body">
        수집목적&nbsp;회원계정&nbsp;관리<br><br>

		제품&nbsp;구매&nbsp;계약의&nbsp;체결&nbsp;및&nbsp;이행<br><br>
		
		고객상담채널을&nbsp;통한&nbsp;민원사항&nbsp;처리<br><br>
		
		부정사용 방지<br><br>
		수집항목 이메일주소, 비밀번호, 이름, 휴대폰 전화번호<br><br>
		<h6>보유기간 회원탈퇴시까지</h6><br>
		* 귀하는 개인정보 수집 및 이용을 거부할 수 있습니다. 필수적 개인정보 수집을 거부하는 경우 계정을 생성할 수 없습니다.<br>
        </div>
        
        <!-- Modal footer -->
        <div class="modal-footer">
          <button type="button" class="btn idFindClose" data-dismiss="modal" style="background-color:#ff471a">Close</button>
        </div>
      </div>
      
    </div>
  </div>


    
</body>
</html>