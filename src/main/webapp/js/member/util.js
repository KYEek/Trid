
// 이메일 유효성 검사
function checkEmail(email){	
	const regExp_email = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i; 
	return regExp_email.test(email);
}

// 비밀번호 유효성 검사
function checkPwd(pwd){
	const regExp_pwdCheck = new RegExp(/^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).*$/g);
	return regExp_pwdCheck.test(pwd);
}

// 이름 유효성 검사
function checkName(name){
	const regExp_name = new RegExp("^[가-힣]{3,10}$");
	return regExp_name.test(name);
}

// 전화번호 유효성 검사
function checkMobile(mobile){
	const regExp_mobile = new RegExp("^(0[2-9]{1,2}|01[016789])[-]*([0-9]{3,4})[-]*([0-9]{4})$");
	return regExp_mobile.test(mobile);
}

// 생년월일 유효성 검사
function checkBirthday(birthday){
	const regExp_birthday = new RegExp(/^(\d{2})(0[1-9]|1[0-2])(0[1-9]|[12]\d|3[01])$/);
	return regExp_birthday.test(birthday);
}

