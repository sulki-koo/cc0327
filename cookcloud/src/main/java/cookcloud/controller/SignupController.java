package cookcloud.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cookcloud.entity.Member;
import cookcloud.service.MemberService;

@Controller
@RequestMapping("/signup")
public class SignupController {

	@Autowired
	private MemberService memberService;

	// 회원가입 페이지
	@GetMapping
	public String showSignupPage(Model model) {
		model.addAttribute("member", new Member());
		return "signup"; // signup.html 페이지 반환
	}

	@PostMapping("/checkDuplicate")
	@ResponseBody
	public Map<String, Boolean> checkDuplicate(@RequestBody Map<String, String> request) {
		Map<String, Boolean> resultMap = new HashMap<>();
		String memId = request.get("memId");
		String memNickname = request.get("memNickname");
		boolean result = memberService.isDuplicate(memId, memNickname);
		resultMap.put("result", result);
		return resultMap;
	}

	// 회원가입 처리
	@PostMapping("/insertMember")
	@ResponseBody
	public void insertMember(@RequestBody Member member, Model model) {
		member.setMemId(member.getMemId());
		member.setMemPassword(member.getMemPassword());
		member.setMemName(member.getMemName());
		member.setMemNickname(member.getMemNickname());
		member.setMemEmail(member.getMemEmail());
		member.setMemPhone(member.getMemPhone());
		memberService.insertMember(member);

	}

}
