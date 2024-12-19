/**
 *
 */

document.addEventListener("DOMContentLoaded", function () {
  //각 버튼의 요소를 가져온다
  const li_button = document.querySelectorAll("button.li_button");
  // console.log(li_button);
  //각 버튼에 이벤트를 추가한다
  li_button.forEach(function (item, index) {
    switch (index) {
      case 0:
        //구매내역
        item.addEventListener("click", function () {
          // alert("구매내역");
          location.href = "/Trid/orders.trd";
        });
        break;
      case 1:
        //장바구니
        item.addEventListener("click", function () {
          // alert("장바구니");
          location.href = "/Trid/basket.trd";
        });
        break;
      case 2:
        //알림
        item.addEventListener("click", function () {
          // alert("알림");
          location.href = "";
        });
        break;
      case 3:
        //프로필
        item.addEventListener("click", function () {
          // alert("프로필");
          location.href = "";
        });
        break;
    }
  });
});
