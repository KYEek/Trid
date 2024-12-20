async function add_list(url) {
  try {
    const response = await fetch(url);
    const data = await response.json();
    return data;
  } catch (error) {
    console.error("에러 발생", error);
  }
}

document.addEventListener("DOMContentLoaded", function () {
  // 메뉴 ul을 선택
  const menu_ul = document.querySelector("ul#menu_ul");
  let html = "";

  // 메뉴 ul에 li 추가
  add_list("json/address.json").then((data) => {
    // console.log(data);
    //json의 객체를 추출
    data.forEach((item) => {
      let is_default = false;
      html += "<li><div><ul class='address_contents'>";
      //각 객체를 순회하면서 키 값으로 데이터를 추출
      for (let info in item) {
        // console.log(item[info]);
        // li태그 생성
        //기본 주소인 경우
        if (info == "addr_isdefault") {
          if (item[info] == 1) {
            html +=
              "<li style ><div id='is_default_address'>기본 주소</div></li>";
            is_default = true;
          }
        } else {
          //기본 주소가 아닌 경우
          html = html + "<li>" + item[info] + "</li>";
        }
      }
      html += `</ul><div class="dropdown">
		  <button class="btn dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
		    <i class='fa-solid fa-ellipsis'></i>
		  </button>
		  <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">`;

      if (is_default) {
        html += `<a class="dropdown-item" href="#">편집</a>`;
      } else {
        html += `<a class="dropdown-item" href="#">편집</a>
         <a class="dropdown-item" href="#">삭제</a>`;
      }
      html += `</div>
		</div></div></li>`;
    });
    menu_ul.innerHTML = html;
  }); //end of add_list--------------------------
  //메뉴 ul에 li 추가 끝

  //주소 추가 버튼 클릭시 이벤트
  document
    .querySelector("div#address_header >div")
    .addEventListener("click", () => {
      this.location.href = "addressAdd.trd";
    });
  //...버튼 클릭시 이벤트
  document.addEventListener(
    "click",
    () => {
      console.log("click");
    },
    "div.i_tag"
  );
});
