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
<title>회원탈퇴 페이지</title>

<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- Bootstrap CSS -->
<link rel="stylesheet" type="text/css" href="bootstrap-4.6.2-dist/css/bootstrap.min.css" > 

<!-- Font Awesome 6 Icons -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.1/css/all.min.css">

<!-- Optional JavaScript -->
<script type="text/javascript" src="js/jquery-3.7.1.min.js"></script>
<script type="text/javascript" src="bootstrap-4.6.2-dist/js/bootstrap.bundle.min.js" ></script> 

<!-- jQueryUI CSS 및 JS -->
<link rel="stylesheet" type="text/css" href="jquery-ui-1.13.1.custom/jquery-ui.min.css" />
<script type="text/javascript" src="jquery-ui-1.13.1.custom/jquery-ui.min.js"></script> 

<script type="text/javascript">

function memberDelete(){
	
	location.href="<%= ctx_Path%>/member/checkDelete.trd"
	
}

</script>

<style>

div#container {
    border: solid 0px red;
    width: 90%;
	height: 400px;
    margin: 10% auto;
}

div#container > input {
    border: none;
    border-bottom: 2px solid #ccc;
    padding-bottom: 5px;
    display: block;
    margin-bottom: 2%;
    outline: none;
}

div#delete{
	margin-bottom: 1.5%;
	font-size: 15pt;
	font-weight: bold;
}

div#message {
	margin: 3% 0;
	font-size: 13pt;
}

div.message {
    display: none; /* 추후에 삭제하고 기능 넣어야함 */
    font-size: 10pt;
}

div.note{
	line-height: 3;
	font-size: 13pt;
}
div.note > span{
	font-size: 13pt;
}

button {
    text-align: center;
    width: 40%;
    height: 40px;
    background-color: white;
    border: solid 1px black;
    font-size: 14pt;
    margin-top: 3%;
}

</style>
</head>
<body>
<jsp:include page="../header.jsp"/>

	<div id="container">
			<div id="delete">계정 삭제</div>
			
			<div id="message">계정 삭제 절차를 시작하고 계십니다. 모든 관련 정보는 시스템에서 영원히 삭제됩니다.<br>이 작업은 취소되지 않습니다.</div>
			<div class="note"><span>유의</span> : <br>구매, 반품 및/또는 교환을 추적할 수 없게 됩니다.<br>Trid에서 계정에 액세스할 수 없게 됩니다.<br>구매하신 상품의 반품/교환 기간이 만료되면 계정이 삭제될 것입니다.</div>
	       
			<br>

			<button type="submit" onclick="memberDelete()">계속</button>
	</div>

</body>
</html>