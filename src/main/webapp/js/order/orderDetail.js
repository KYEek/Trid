let count_number = 0;
//장바구니 목록을 가져오는 함수
async function getBasketList() {
  try {
    const response = await fetch("json/basketList.json", {
      method: "get",
      headers: { contentType: "application/json" },
    });
    const basketList = await response.json();

    //각 json객체를 순환

    let html = "";
    let PK_BASKET_NO = "";
    let FK_MEMBER_NO = "";
    let FK_PRODUCT_DETAIL_NO = "";
    let BASKET_QUANTITY = "";
    let PRODUCT_NO = "";
    let PRODUCT_SIZE = "";
    let PRODUCT_INVENTORY = "";
    let PRODUCT_NAME = "";
    let PRODUCT_PRICE = "";
    let COLOR_NAME = "";
    let PRODUCT_IMAGE_PATH = "";
    let PRODUCT_IMAGE_NAME = "";
    count_number = 0;
    basketList.forEach((basket) => {
      //   console.log(basket);
      //   json 객체의 값을 추출하기 위한 for문
      for (key in basket) {
        // console.log(basket[key]);
        switch (key) {
          case "PK_BASKET_NO":
            PK_BASKET_NO = basket[key];
            break;
          case "FK_MEMBER_NO":
            FK_MEMBER_NO = basket[key];
            break;
          case "FK_PRODUCT_DETAIL_NO":
            FK_PRODUCT_DETAIL_NO = basket[key];
            break;
          case "BASKET_QUANTITY":
            BASKET_QUANTITY = basket[key];
            break;
          case "PRODUCT_NO":
            PRODUCT_NO = basket[key];
            break;
          case "PRODUCT_SIZE":
            PRODUCT_SIZE = basket[key];
            break;
          case "PRODUCT_INVENTORY":
            PRODUCT_INVENTORY = basket[key];
            break;
          case "PRODUCT_NAME":
            PRODUCT_NAME = basket[key];
            break;
          case "PRODUCT_PRICE":
            PRODUCT_PRICE = basket[key];
            break;
          case "COLOR_NAME":
            COLOR_NAME = basket[key];
            break;
          case "PRODUCT_IMAGE_PATH":
            PRODUCT_IMAGE_PATH = basket[key];
            break;
          case "PRODUCT_IMAGE_NAME":
            PRODUCT_IMAGE_NAME = basket[key];
            break;
        }
      } // end of for-------------------------------
      html += `<div id="basket_${PK_BASKET_NO}" class="basket_item" data-basket_no="${PK_BASKET_NO}" >
            <div class="basket_img">
              <a><img src="${PRODUCT_IMAGE_PATH}" /></a>
            </div>
            <div class="basket_product_info" data-product_detail_no="${FK_PRODUCT_DETAIL_NO}">
              <div class="basket_product_info_header">
                <div><a class="product_link">${PRODUCT_NAME}</a></div>
              </div>
              <div class="basket_pruduct_price"><span class="price_text" data-price ="${PRODUCT_PRICE}">₩${
        PRODUCT_PRICE * BASKET_QUANTITY
      }</span></div>
              <div class="basket_pruduct_size_category">
                <span class="basket_product_size">${PRODUCT_SIZE}</span>&nbsp;|&nbsp;<span
                  class="basket_product_category"
                  >${COLOR_NAME}</span
                >
              </div>
            </div>
          </div>`;
      count_number = count_number + 1;
    }); // end of forEach-------------------------------
    return html;
  } catch {
    console.log("error");
  }
}

// dom이 로드되었을 때
document.addEventListener("DOMContentLoaded", function () {
  const basket_list = document.querySelector("div#basket_list");
  //장바구니 목록을 가져오기—
  getBasketList().then((html) => {
    basket_list.innerHTML = html;
    document.querySelector("span#item_count_number").textContent = count_number;
  }); // end of getBasketList().then((html) => {
});
