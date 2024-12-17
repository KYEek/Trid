
$(document).ready(function () {
   
    $("input#pwd").bind("keydown", (e) => {
        if (e.keyCode == 13) {// 암호입력란에 엔터를 했을 경우
            goLogin(); // 로그인 시도한다.
        }
    });

});// end of $(document).ready(function() {})---------------------

// Function Declaration
function goLogin() {
    // alert("확인용 로그인 처리하러간다.");

    if ($("input#email").val().trim() == "") {
        alert("이메일을 입력하세요!!");
        $("input#email").val("").focus();
        return; // goLogin() 함수 종료
    }

    if ($("input#pwd").val().trim() == "") {
        alert("비밀번호를 입력하세요!!");
        $("input#pwd").val("").focus();
        return; // goLogin() 함수 종료
    }
	const frm = document.loginFrm;
	frm.submit();
}
