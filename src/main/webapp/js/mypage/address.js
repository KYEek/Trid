document.addEventListener("DOMContentLoaded", function () {
  // 메뉴 ul을 선택
  const menu_ul = document.querySelector("ul#menu_ul");

  let html = "";
  //주소가 없을 경우 주소가 없다고 표시해줍니다.
  if (addrList == null) {
    html =
      "<li><div><ul class='address_contents'><li>주소가 없습니다 주소를 추가해 주세요</li></ul></div></li>";
  } else {
    //json의 객체를 추출
    addrList.forEach((item) => {
      // html 코드를 담을 변수를 설정합니다.
      let member_name = "";
      let addr_address = "";
      let addr_detail = "";
      let addr_extraaddr = "";
      let addr_post_no = "";
      let member_mobile = "";
      let addr_isdefault = "";
      let pk_addr_no = "";
      let is_default = false;
      html += "<li><div><ul class='address_contents'>";
      //각 객체를 순회하면서 키 값으로 데이터를 추출
      for (let info in item) {
        let htmlForList = "";
        // console.log(item[info]);
        // li태그 생성
        //기본 주소인 경우 기본주소라고 표시해줍니다
        if (info == "addr_isdefault") {
          if (item[info] == 1) {
            addr_isdefault =
              "<li style ><div id='is_default_address'>기본 주소</div></li>";
            is_default = true;
          }
        } else {
          //기본 주소가 아닌 경우 각 이름에 맞게 변수를 설정합니다.
          switch (info) {
            case "member_name":
              member_name = "<li>" + item[info] + "</li>";
              break;
            case "addr_address":
              addr_address = "<li>" + item[info] + "</li>";
              break;
            case "addr_detail":
              addr_detail = "<li>" + item[info] + "</li>";
              break;
            case "addr_extraaddr":
              addr_extraaddr = "<li>" + item[info] + "</li>";
              break;
            case "addr_post_no":
              addr_post_no = "<li>" + item[info] + "</li>";
              break;
            case "member_mobile":
              member_mobile = "<li>" + item[info] + "</li>";
              break;
            case "addr_isdefault":
              addr_isdefault = "<li>" + item[info] + "</li>";
              break;
            case "pk_addr_no":
              pk_addr_no = item[info];
          }
        }
      }
      //순서에 맞게 값을 넣습니다
      html =
        html +
        member_name +
        addr_address +
        addr_detail +
        addr_extraaddr +
        addr_post_no +
        member_mobile +
        addr_isdefault;

      //html에 표시해줌줌
      html += `</ul><div class="dropdown">
				  <button class="btn dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
				    ...
				  </button>
				  <div class="dropdown-menu" aria-labelledby="dropdownMenuButton"><form action="addressButtonController.trd" method = "post">
				  <input name="address_no" type="text"
				  	value = "${pk_addr_no}" id="address_no" style="display: none"/>`;

      //기본 주소인지 아닌지에 따라 표시되는 것이 다릅니다.
      if (is_default) {
        html += `<button class="dropdown-item" name="action" value="edit">편집</button>`;
      } else {
        html += `<button class="dropdown-item" name="action" value="edit">편집</button>
				<button class="dropdown-item" name="action" value="setDefault">기본주소로 설정</button>
		         <button class="dropdown-item" name="action" value="delete">삭제</button>`;
      }
      html += `</form></div>
				</div></div></li>`;
    });
  }
  menu_ul.innerHTML = html;
  //메뉴 ul에 li 추가 끝

  //주소 추가 버튼 클릭시 이벤트
  document
    .querySelector("div#address_header >div")
    .addEventListener("click", () => {
      this.location.href = "addressAdd.trd";
    });
  //...버튼 클릭시 이벤트
  //		  document.addEventListener(
  //		    "click",
  //		    () => {
  //		      console.log("click");
  //		    },
  //		    "div.i_tag"
  //		  );
});
