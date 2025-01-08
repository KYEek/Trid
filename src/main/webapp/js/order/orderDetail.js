let count_number = 0;
//장바구니 목록을 가져오는 함수

//json문자열을 json객체로 변환
const orderDetail = JSON.parse(orderDetailStr);
const addrInfo = JSON.parse(addrInfoStr);

// dom이 로드되었을 때
document.addEventListener("DOMContentLoaded", function () {
  // html 삽입

  //각 데이터의 변수 선언
  let html = "";
  let PK_ORDER_NO = "";
  let ORDER_TOTAL_PRICE = "";
  let SUM_PRODUC_PRICE = "";
  let ORDER_STATUS = "";
  let ORDER_DATE = "";
  let FK_ADDR_NO = "";
  let PRODUCT_DETAIL_NO = "";
  let PRODUCT_PRICE = "";
  let PRODUCT_SIZE = "";
  let PRODUCT_NAME = "";
  let COLOR_NAME = "";
  let PRODUCT_IMAGE_PATH = "";
  let PRODUCT_IMAGE_NAME = "";
  let PRODUCT_NO = "";
  count_number = 0;
  total_price = 0;

  //각 상품의 정보를 html로 저장
  orderDetail.forEach((order) => {
    //   console.log(basket);
    PK_ORDER_NO = order.PK_ORDER_NO;
    ORDER_TOTAL_PRICE = order.ORDER_TOTAL_PRICE;
    SUM_PRODUC_PRICE = order.SUM_PRODUC_PRICE;
    ORDER_STATUS = order.ORDER_STATUS;
    ORDER_DATE = order.ORDER_DATE;
    FK_ADDR_NO = order.FK_ADDR_NO;
    PRODUCT_DETAIL_NO = order.PRODUCT_DETAIL_NO;
    PRODUCT_PRICE = order.PRODUCT_PRICE;
    PRODUCT_SIZE = order.PRODUCT_SIZE;
    PRODUCT_NAME = order.PRODUCT_NAME;
    COLOR_NAME = order.COLOR_NAME;
    PRODUCT_IMAGE_PATH = order.PRODUCT_IMAGE_PATH;
    PRODUCT_IMAGE_NAME = order.PRODUCT_IMAGE_NAME;
    PRODUCT_NO = order.PRODUCT_NO;

    html += `<div id="basket_${PK_ORDER_NO}" class="basket_item" data-basket_no="${PK_ORDER_NO}" >
            <div class="basket_img">
              <a href= "/Trid/product/detail.trd?productNo=${PRODUCT_NO}"><img src="/Trid/${PRODUCT_IMAGE_PATH}" /></a>
            </div>
            <div class="basket_product_info" data-product_detail_no="${PRODUCT_DETAIL_NO}">
              <div class="basket_product_info_header">
                <div><a href= "/Trid/product/detail.trd?productNo=${PRODUCT_NO}" class="product_link">${PRODUCT_NAME}</a></div>
              </div>
              <div class="basket_pruduct_price"><span class="price_text" data-price ="${PRODUCT_PRICE}">₩${PRODUCT_PRICE}</span></div>
              <div class="basket_pruduct_size_category">
                <span class="basket_product_size">${PRODUCT_SIZE}</span>&nbsp;|&nbsp;<span
                  class="basket_product_category"
                  >${COLOR_NAME}</span
                >
              </div>
            </div>
          </div>`;
    count_number = count_number + 1;
    total_price = total_price + Number(PRODUCT_PRICE);
  }); // end of forEach-------------------------------

  //배송지 정보를 한글로 변경
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
  //가격을 입력
  document.querySelector("span#order_status").textContent = `${ORDER_STATUS}`;
  document.querySelector(
    "div#order_price_sum"
  ).textContent = `₩ ${total_price}`;
  document.querySelector("div#delevery_cost").textContent = `₩ ${
    Number(ORDER_TOTAL_PRICE) - Number(total_price)
  }`;
  document.querySelector(
    "div#order_price_total"
  ).textContent = `₩ ${ORDER_TOTAL_PRICE}`;

  //날짜를 입력
  document.querySelector("div#order_date").textContent = ORDER_DATE.substring(
    0,
    11
  );

  //주문번호 입력
  document.querySelector("span#orderNo").textContent = ` ${PK_ORDER_NO}`;

  //값 저장
  const order_product_list = document.querySelector("div#basket_list");
  order_product_list.innerHTML = html;
  document.querySelector("span#item_count_number").textContent = count_number;
});
