<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<% 
	String ctx_Path = request.getContextPath();
	//	   /Trid
%>    
   
  
   
    
<!DOCTYPE html>
<html>
<head>

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

button {
    text-align: center;
    width: 35%;
    height: 40px;
    color: red;
    border: solid 1px red;
    background-color: white;
    font-size: 10pt;
    margin-top: 3%;
}

button:hover {
    text-align: center;
    width: 35%;
    height: 40px;
    color: red;
    border: solid 1px red;
    background-color: #ffcccc;
    font-size: 10pt;
    margin-top: 3%;
}

div.message {
    display: none; /* 추후에 삭제하고 기능 넣어야함 */
    font: 13pt;
}

div#delete{
	margin-bottom: 2%;
	font-size: 13pt;
	font-weight: bold;
}

div#message {
	margin-bottom: 7%;
	font-size: 10pt;
}

</style>   
    

<meta charset="UTF-8">
<title>계정 삭제 확인 페이지</title>

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

<script>


</script>

</head>
<body>
<jsp:include page="../header.jsp"/>

    <form action="" method="post">
		<div id="container">
			<div id="delete">계정 삭제 확인</div>
			
			<div id="message">계속하려면 액세스 정보를 입력하세요.</div>
			
			<input type="password" name="pwd" id="pwd" maxlength="15" class="requiredInfo" placeholder="비밀번호" size="55%"/>
	        <div class="message">메세지</div>
	       
			<button type="submit" id="deleteMember">모두 삭제</button>
		</div>
	</form>

</body>
</html>