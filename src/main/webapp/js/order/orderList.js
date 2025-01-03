document.addEventListener("DOMContentLoaded", function () {
  let html = "";
  const order_list_box = document.querySelector("#order_list_box");
  let FK_ORDER_NO = "";
  let ORDER_DATE = "";
  let ORDER_STATUS = "";
  let ORDER_TOTAL_PRICE = "";
  let FK_PRODUCT_DETAIL_NO = "";
  let PRODUCT_IMAGE_PATH = "";
  let PRODUCT_IMAGE_NAME = "";
  //json 데이터를 반복
  orderList.forEach((item) => {
    //console.log(item);
    // for (let item of order) {
    //처음 들어온 데이터
    if (FK_ORDER_NO == "") {
      FK_ORDER_NO = item.FK_ORDER_NO;
      ORDER_DATE = item.ORDER_DATE;
      ORDER_STATUS = item.ORDER_STATUS;
      ORDER_TOTAL_PRICE = item.ORDER_TOTAL_PRICE;
      FK_PRODUCT_DETAIL_NO = item.FK_PRODUCT_DETAIL_NO;
      PRODUCT_IMAGE_PATH = item.PRODUCT_IMAGE_PATH;
      PRODUCT_IMAGE_NAME = item.PRODUCT_IMAGE_NAME;
      //배송상태 한글로 변경
      switch (ORDER_STATUS) {
        case 0:
          ORDER_STATUS = "결제완료";
          break;
        case 1:
          ORDER_STATUS = "상품준비";
          break;
        case 2:
          ORDER_STATUS = "배송중";
          break;
        case 3:
          ORDER_STATUS = "배송완료";
          break;
      }
      //html에 추가
      html += `<div class="order_list_item">
          <div class="order_itme_header">
            <div>${ORDER_STATUS}</div>
            <div class="order_itme_price_detail">
              <span>₩${ORDER_TOTAL_PRICE}</span> <a href="orders/detail.trd?orderNo=${FK_ORDER_NO}">주문 보기</a>
            </div>
          </div>
          <div class="order_item_images">
            <img src="/Trid/${PRODUCT_IMAGE_PATH}" />`;
    } //end of if(FK_ORDER_NO == "")
    //처음 들어온 데이터가 아닐 때
    else {
      //새로운 주문번호가 들어왔을 때
      if (FK_ORDER_NO != item.FK_ORDER_NO) {
        FK_ORDER_NO = item.FK_ORDER_NO;
        ORDER_DATE = item.ORDER_DATE;
        ORDER_STATUS = item.ORDER_STATUS;
        ORDER_TOTAL_PRICE = item.ORDER_TOTAL_PRICE;
        FK_PRODUCT_DETAIL_NO = item.FK_PRODUCT_DETAIL_NO;
        PRODUCT_IMAGE_PATH = item.PRODUCT_IMAGE_PATH;
        PRODUCT_IMAGE_NAME = item.PRODUCT_IMAGE_NAME;

        //배송상태 한글로 변경
        switch (ORDER_STATUS) {
          case 0:
            ORDER_STATUS = "결제완료";
            break;
          case 1:
            ORDER_STATUS = "상품준비";
            break;
          case 2:
            ORDER_STATUS = "배송중";
            break;
          case 3:
            ORDER_STATUS = "배송완료";
            break;
        }
        //
        html += `</div>
        </div>`;
        html += `<div class="order_list_item">
          <div class="order_itme_header">
            <div>${ORDER_STATUS}</div>
            <div class="order_itme_price_detail">
              <span>₩${ORDER_TOTAL_PRICE}</span> <a href="orders/detail.trd?orderNo=${FK_ORDER_NO}">주문 보기</a>
            </div>
          </div>
          <div class="order_item_images">
            <img src="/Trid/${PRODUCT_IMAGE_PATH}" />`;
      } //end of else if(FK_ORDER_NO != item.FK_ORDER_NO)
      //기존 주문번호와 같은 주문번호가 들어왔을 때
      else {
        //이미지가 다를 때만 추가 (같은 이미지가 연속으로 들어오는 것 방지)
        if (PRODUCT_IMAGE_PATH != item.PRODUCT_IMAGE_PATH) {
          PRODUCT_IMAGE_PATH = item.PRODUCT_IMAGE_PATH;
          html += `<img src="/Trid/${PRODUCT_IMAGE_PATH}" />`;
        }
      } //end of else
    }
  }); //end of forEach

  //마지막 데이터 처리
  html += `</div>
        </div>`;
  //html을 order_list_box에 추가
  order_list_box.innerHTML = html;
});
