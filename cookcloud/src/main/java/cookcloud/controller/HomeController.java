package cookcloud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // 메인 페이지
    @GetMapping("/")
    public String mainPage() {
        return "main"; // main.html
    }

    // 로그인 페이지
    @GetMapping("/login")
    public String login() {
        // 로그인 페이지로 리다이렉트
        return "login";  // login.html 페이지를 렌더링
    }

    // 냉장고 털기 페이지
    @GetMapping("/fridge")
    public String fridgePage() {
        return "fridge"; // fridge.html
    }

    // 취향 선택 추천 페이지
    @GetMapping("/recommendation")
    public String recommendationPage() {
        return "recommendation"; // recommendation.html
    }

    // 공지 & 문의 페이지
    @GetMapping("/notice")
    public String noticePage() {
        return "notice"; // notice.html
    }
    
}
