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
		$(this).parent().remove();
	});

	// 폼 제출 시 선택된 알러지 정보도 함께 전송
	$('form').on('submit', function() {
		const selectedAllergies = [];
		$('#selected-allergies .allergy-btn').each(function() {
			selectedAllergies.push($(this).data('id'));
		});
		// 숨겨진 필드로 선택된 알러지 ID들 전송
		$('<input>').attr({
			type: 'hidden',
			name: 'selectedAllergies',
			value: selectedAllergies.join(',')
		}).appendTo('form');
	});
});

let selectedAllergies = [];  // 선택된 알러지 ID
let deletedAllergies = [];   // 삭제된 알러지 ID

// 알러지 항목 클릭 시 선택/해제 처리
$('.allergy-item').on('click', function() {
  let allergyId = $(this).data('id');  // 클릭된 알러지 ID

  if ($(this).hasClass('selected')) {
    // 이미 선택된 경우 => 선택 해제
    $(this).removeClass('selected');
    selectedAllergies = selectedAllergies.filter(function(item) {
      return item !== allergyId;
    });
    deletedAllergies.push(allergyId); // 삭제된 알러지 목록에 추가
    $('#selected-allergies').find(`[data-id="${allergyId}"]`).remove();  // 선택 해제된 알러지 UI에서 제거
  } else {
    // 선택되지 않은 경우 => 선택
    $(this).addClass('selected');
    selectedAllergies.push(allergyId); // 선택된 알러지 목록에 추가
    deletedAllergies = deletedAllergies.filter(function(item) {
      return item !== allergyId;
    });

    // 선택된 알러지 항목을 UI에 표시
    $('#selected-allergies').append(
      `<span class="selected-item" data-id="${allergyId}">${$(this).text()} <button class="remove-allergy" data-id="${allergyId}">삭제</button></span>`
    );
  }
});

// 알러지 항목 삭제 버튼 클릭 시
$(document).on('click', '.remove-allergy', function() {
  let allergyId = $(this).data('id');
  selectedAllergies = selectedAllergies.filter(function(item) {
    return item !== allergyId;
  });
  deletedAllergies.push(allergyId); // 삭제된 알러지 목록에 추가
  $(this).parent().remove();  // UI에서 해당 알러지 항목 삭제
});

// 알러지 정보 전송 함수
function sendAllergyData() {
  var memId = 'yourMemId'; // 여기에 실제 memId 값 삽입해야 함

  // 서버로 선택된 알러지와 삭제된 알러지 정보 전송
  $.ajax({
    url: '/updateMemberAllergies',  // 서버 URL
    type: 'POST',
    contentType: 'application/json',
    data: JSON.stringify({
      memId: memId,
      selectedAllergies: selectedAllergies,  // 새로 선택된 알러지들
      deletedAllergies: deletedAllergies    // 해제된 알러지들
    }),
    success: function(response) {
      console.log('알러지 정보가 업데이트 되었습니다.');
    },
    error: function(error) {
      console.log('오류 발생:', error);
    }
  });
}
