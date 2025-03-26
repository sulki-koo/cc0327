package cookcloud.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import cookcloud.entity.MemberAllergyFood;
import cookcloud.service.MemberAllergyFoodService;
import jakarta.servlet.http.HttpSession;

@Controller
public class MemberAlleryFoodController {

	@Autowired
	private MemberAllergyFoodService memberAllergyFoodService;
	
	@Autowired
	private HttpSession session;

	@PostMapping("/updateMemberAllergies")
	@ResponseBody
	public ResponseEntity<String> updateMemberAllergies(@RequestBody Map<String, Object> requestData) {
		try {
			// ✅ 세션에서 memId 가져오거나 회원가입 시 입력된 아이디 가져오기
			String memId = (String) requestData.get("memId");
			if (memId == null) {
				memId = (String) session.getAttribute("memId"); // 세션에서 가져오기
				if (memId == null) {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원 ID를 찾을 수 없습니다.");
				}
			}

			List<Integer> selectedAllergies = (List<Integer>) requestData.get("selectedAllergies");
			List<Integer> deletedAllergies = (List<Integer>) requestData.get("deletedAllergies");

			// ✅ 기존 알러지 삭제 처리
			for (Integer allergyId : deletedAllergies) {
				memberAllergyFoodService.updateMemberAllergyFood(memId, allergyId.longValue());
			}

			// ✅ 새로운 알러지 추가
			for (Integer allergyId : selectedAllergies) {
				MemberAllergyFood newFood = new MemberAllergyFood();
				newFood.setMemId(memId);
				newFood.setAllergyId(allergyId.longValue());
				newFood.setMemAllergyInsertAt(LocalDateTime.now());
				newFood.setMemAllergyIsDeleted("N");
				memberAllergyFoodService.insertMemAllergyFood(newFood);
			}

			return ResponseEntity.ok("알러지 정보가 업데이트 되었습니다.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("알러지 정보 업데이트 실패");
		}
	}

}
