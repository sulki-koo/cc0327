$(document).ready(function() {
    // 알러지 항목을 클릭하면 선택 목록에 추가
    $(document).on('click', '.allergy-item', function() {
        const allergyId = $(this).data('id');
        const allergyName = $(this).text();  // 알러지 이름

        // 이미 선택된 알러지가 아닐 경우에만 추가
        if ($('#selected-allergies .allergy-btn[data-id="' + allergyId + '"]').length === 0) {
            $('#selected-allergies').append('<button type="button" class="allergy-btn" data-id="' + allergyId + '">' +
                allergyName + ' <span class="remove-btn">X</span></button>');
        }
    });

    // 선택된 알러지 항목에서 'X' 버튼 클릭 시 제거
    $(document).on('click', '.remove-btn', function() {
        // 삭제된 알러지 목록에 추가
        const allergyId = $(this).parent().data('id');
        // 기존에 이미 삭제 배열에 없으면 추가(중복 방지)
        if (!window.deletedAllergies) {
            window.deletedAllergies = [];
        }
        window.deletedAllergies.push(allergyId);
        $(this).parent().remove();
    });

    // 폼 제출 시 선택된 알러지 정보와 삭제된 알러지 정보도 함께 전송
    $('form').on('submit', function() {
        const selectedAllergies = [];
        $('#selected-allergies .allergy-btn').each(function() {
            selectedAllergies.push($(this).data('id'));
        });
        // 삭제된 알러지 배열이 없으면 빈 문자열 전송
        const deletedAllergies = window.deletedAllergies ? window.deletedAllergies : [];

        // 숨겨진 필드로 선택된 알러지 ID들 전송
        $('<input>').attr({
            type: 'hidden',
            name: 'selectedAllergies',
            value: selectedAllergies.join(',')
        }).appendTo('form');

        // 숨겨진 필드로 삭제된 알러지 ID들 전송
        $('<input>').attr({
            type: 'hidden',
            name: 'deletedAllergies',
            value: deletedAllergies.join(',')
        }).appendTo('form');
    });
});
