//true면 무한 스크롤 활성화
let is_scroll = false;
let startNum = 6;

document.addEventListener("DOMContentLoaded", function () {
  const order_list_box = document.querySelector("#order_list_box");

  createHTML(orderList, order_list_box);

  //무한 스크롤을 위한 스크롤 이벤트
  window.addEventListener("scroll", (e) => {
    //console.log(is_scroll);
    //무한 스크롤 활성화 상태일 때 이벤트 실행
    if (is_scroll) {
		//스크롤 값을 가져온다
      const scrollY = window.scrollY;
      const window_height = document.documentElement.scrollHeight;
      const client_height = document.documentElement.clientHeight;
      const scroll_percent = (
        scrollY /
        (window_height - client_height)
      ).toFixed(2);
      //스크롤 위치가 0.8 위치일 때 실행
      if (scroll_percent > 0.7) {
        is_scroll = false;
        //json값을 가져온다
        const response = infiniteScroll();
        response
          .then((data) => {
            //console.log(data);
            createHTML(data, order_list_box);
          })
          .catch((error) => {
            console.error(error);
          });

        //시작 번호를 설정
        startNum += 5;
      }
    }
  });
});

//무한 스크롤 비동기 함수
async function infiniteScroll() {
  const response = await fetch("/Trid/orders.trd", {
    method: "POST",
    header: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ startNum: `${startNum}` }),
  });
  const data = await response.json();
  return data;
}

//html을 생성해서 삽입하는 코드
function createHTML(orderList, order_list_box) {
  let html = "";
  let FK_ORDER_NO = "";
  let ORDER_DATE = "";
  let ORDER_STATUS = "";
  let ORDER_TOTAL_PRICE = "";
  let FK_PRODUCT_DETAIL_NO = "";
  let PRODUCT_IMAGE_PATH = "";
  let PRODUCT_IMAGE_NAME = "";
  let count_num = 0;

  //json 데이터를 반복
  orderList.forEach((item) => {
    //console.log(item);
    // for (let item of order) {
    //처음 들어온 데이터
    if (FK_ORDER_NO == "") {
      //카운트 숫자를 증가
      count_num++;
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
	              <span>₩${Number(
                  ORDER_TOTAL_PRICE
                ).toLocaleString()}</span> <a href="orders/detail.trd?orderNo=${FK_ORDER_NO}">주문 보기</a>
	            </div>
	          </div>
	          <div class="order_item_images">
	            <img src="/Trid/${PRODUCT_IMAGE_PATH}" />`;
    } //end of if(FK_ORDER_NO == "")
    //처음 들어온 데이터가 아닐 때
    else {
      //새로운 주문번호가 들어왔을 때
      if (FK_ORDER_NO != item.FK_ORDER_NO) {
        //카운트 숫자를 증가
        count_num++;
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
	              <span>₩${Number(
                  ORDER_TOTAL_PRICE
                ).toLocaleString()}</span> <a href="orders/detail.trd?orderNo=${FK_ORDER_NO}">주문 보기</a>
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
	//console.log(count_num);
  //마지막 데이터 처리
  html += `</div>
	        </div>`;
  //html을 order_list_box에 추가
  order_list_box.insertAdjacentHTML("beforeend", html);
  //만약 카운트 숫자가 7 이하면 (불러온 상품의 개수가 7개 미만이라면) 무한스크롤 비활성화(상품을 모두 불러온 상태)
  if (count_num < 4) {
    is_scroll = false;
  }
  else {
	is_scroll = true;
  }
}
