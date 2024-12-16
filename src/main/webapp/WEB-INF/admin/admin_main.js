$(document).ready(function () {

    // 로그아웃 이벤트 처리
    $(document).on("click", "button#logout_button", () => {
        const frm = document.logout_frm;
        frm.method = "post";
        frm.action = "logout.trd";
        frm.submit();
    });
});