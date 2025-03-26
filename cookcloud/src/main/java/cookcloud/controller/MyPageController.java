package cookcloud.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cookcloud.entity.Member;
import cookcloud.entity.MemberAllergyFood;
import cookcloud.entity.Message;
import cookcloud.entity.Recipe;
import cookcloud.entity.Review;
import cookcloud.service.*;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/mypage")
public class MyPageController {

    private final MemberAllergyFoodService memberAllergyFoodService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private AllergyService allergyService;

	@Autowired
	private RecipeService recipeService;

	@Autowired
	private FollowsService followsService;

	@Autowired
	private LikesService likesService;

	@Autowired
	private ReviewService reviewService;

	@Autowired
	private MessageService messageService;

    MyPageController(MemberAllergyFoodService memberAllergyFoodService) {
        this.memberAllergyFoodService = memberAllergyFoodService;
    }

	@GetMapping
	public String viewMyPage(Model model, Principal principal, HttpSession session) {
		// 로그인된 사용자(memNickname 기반) 조회
		Member member = memberService.getMember(principal.getName()).get();

		String memId = member.getMemId();
		session.setAttribute("memId", memId);

		// 필요한 데이터 조회
		List<Recipe> myRecipes = recipeService.getMyRecipes(memId);
		List<Member> followings = followsService.getMyFollowings(memId);
		List<Member> followers = followsService.getMyFollowers(memId);
		List<Recipe> likedRecipes = likesService.getLikedRecipes(memId);
		List<Review> myReviews = reviewService.getMyReviews(memId);
		List<Message> messages = messageService.getMessages(memId);

		// 모델에 데이터 전달
		model.addAttribute("member", member);
		model.addAttribute("myRecipes", myRecipes);
		model.addAttribute("followings", followings);
		model.addAttribute("followers", followers);
		model.addAttribute("likedRecipes", likedRecipes);
		model.addAttribute("myReviews", myReviews);
		model.addAttribute("messages", messages);
		model.addAttribute("allergyList", allergyService.getAllAllergies());

		return "mypage/main"; // Thymeleaf 템플릿 이름
	}

	// 메시지 읽음 처리
	@PostMapping("/message/{id}/read")
	@ResponseBody
	public ResponseEntity<Message> markMessageAsRead(@PathVariable Long messageId) {
		messageService.markMessageAsRead(messageId);
		return ResponseEntity.ok().build();
	}

	// 메시지 삭제 처리
	@PutMapping("/message/{id}")
	@ResponseBody
	public ResponseEntity<Message> deleteMessage(@PathVariable Long messageId) {
		messageService.deleteMessage(messageId);
		return ResponseEntity.ok().build();
	}

	@PutMapping("/updateMember/{memId}")
	@ResponseBody
	public Member updateMember(@PathVariable String memId, @RequestBody Member member) {
		member.setMemId(memId);
		return memberService.updateMember(member);
	}

}
