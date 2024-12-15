/**
 *
 */

document.addEventListener("DOMContentLoaded", function () {
  const group_list = document.querySelectorAll(".group_list");

  //   테스트용;
  //   console.log(group_list);

  //스크롤 발생 시 실행할 이벤트
  group_list.forEach((element) => {
    element.addEventListener("scroll", (e) => {
      //이벤트 객체 안의 li를 찾는다
      const li = element.querySelectorAll("li");
      const size_of_screen = element.scrollHeight / li.length;
      //   console.log(li);
      const li_index = Math.floor(element.scrollTop / size_of_screen);
      //   console.log(li_index);
      // 스크롤 값의 첫번째 소수점 자리로 만든다
      const current_place = Number(
        (element.scrollTop / size_of_screen).toFixed(4)
      );
      // console.log(current_place);
      const animation_position = current_place - li_index;
      const scaleNum = 1.68 - animation_position;
      // console.log(li_index);
	  
	  //이미지 축소하기
      if (animation_position > 0.7) {
        // console.log("scaneNum : ", scaleNum);
        li[li_index].style.transform = `scale(${scaleNum})`;
      } else {
        li[li_index].style.transform = `scale(1)`;
      }
    });
  });
});
