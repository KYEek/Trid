
 $(document).ready(function() {
    $('.gender_female').click(function(event) {
        $('#male_clothing').hide(); // 남성 카테고리 숨기기
        $('#female_clothing').show(); // 여성 카테고리 보이기
    });

    $('.gender_male').click(function(event) {
        $('#female_clothing').hide(); // 여성 카테고리 숨기기
        $('#male_clothing').show(); // 남성 카테고리 보이기
    });
});//end of $(document).ready(function() ------------------------------------



