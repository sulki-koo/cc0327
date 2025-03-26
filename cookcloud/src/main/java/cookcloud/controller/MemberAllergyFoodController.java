package cookcloud.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cookcloud.entity.MemberAllergyFood;
import cookcloud.service.MemberAllergyFoodService;
import jakarta.servlet.http.HttpSession;

@Controller
public class MemberAllergyFoodController {

	@Autowired
	private MemberAllergyFoodService memberAllergyFoodService;

	@PostMapping("/updateMemberAllergies")
	@ResponseBody
	public ResponseEntity<String> updateMemberAllergies(@RequestParam("memId") String memId,
			@RequestParam("selectedAllergies") String selectedAllergiesStr,
			@RequestParam("deletedAllergies") String deletedAllergiesStr) {

		// selectedAllergies 문자열을 콤마(,)로 분리하여 Long 리스트로 변환
		List<Long> selectedAllergies = new ArrayList<>();
		if (selectedAllergiesStr != null && !selectedAllergiesStr.isEmpty()) {
			selectedAllergies = Arrays.stream(selectedAllergiesStr.split(",")).map(Long::parseLong)
					.collect(Collectors.toList());
		}

		// deletedAllergies 문자열을 콤마(,)로 분리하여 Long 리스트로 변환
		List<Long> deletedAllergies = new ArrayList<>();
		if (deletedAllergiesStr != null && !deletedAllergiesStr.isEmpty()) {
			deletedAllergies = Arrays.stream(deletedAllergiesStr.split(",")).map(Long::parseLong)
					.collect(Collectors.toList());
		}

		// 여기에 서비스 로직을 호출하여 선택된 알러지와 삭제된 알러지를 처리합니다.
		// 예를 들어, 기존 알러지 삭제 처리
		for (Long allergyId : deletedAllergies) {
			memberAllergyFoodService.updateMemberAllergyFood(memId, allergyId);
		}
		// 새로운 알러지 추가 처리
		for (Long allergyId : selectedAllergies) {
			MemberAllergyFood newFood = new MemberAllergyFood();
			newFood.setMemId(memId);
			newFood.setAllergyId(allergyId);
			memberAllergyFoodService.insertMemAllergyFood(newFood);
		}

		return ResponseEntity.ok("알러지 정보 업데이트 완료");
	}

}
