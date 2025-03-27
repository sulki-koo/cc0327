package cookcloud.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cookcloud.entity.Code;
import cookcloud.entity.CodeId;
import cookcloud.entity.Member;
import cookcloud.entity.Recipe;
import cookcloud.service.CodeService;
import cookcloud.service.MemberService;
import cookcloud.service.RecipeService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/recipes")
public class RecipeController {

	@Autowired
	private RecipeService recipeService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private CodeService codeService;

	@GetMapping
	public String getRecipes(Model model, Principal principal, HttpSession session) {
		if (principal != null) {
			Member member = memberService.getMember(principal.getName()).get();

			String memId = member.getMemId();
			session.setAttribute("memId", memId);
		}

		List<Recipe> recipes = recipeService.getRecipes();
		Map<CodeId, Code> recipeTypes = codeService.getRecipeTypes();
		model.addAttribute("recipeTypes", recipeTypes);
		model.addAttribute("recipes", recipes);
		return "recipe/list";
	}

	@GetMapping("/recipe/{recipeId}")
	public String getRecipe(@PathVariable Long recipeId, Model model) {
		Recipe recipe = recipeService.getRecipe(recipeId).get();
		model.addAttribute("recipe", recipe);
		return "recipe/view";
	}

	@GetMapping("/search/{nickname}")
	public String getMemberRecipes(@PathVariable String nickname, Model model) {
		List<Recipe> recipes = recipeService.getMemNicknameRecipes(nickname);
		model.addAttribute("recipes", recipes);
		model.addAttribute("nickname", nickname);
		return "recipe/list";
	}

	@GetMapping("/search/{keyword}")
	public String searchRecipes(@PathVariable String keyword, Model model) {
		List<Recipe> recipes = recipeService.searchRecipes(keyword);
		model.addAttribute("recipes", recipes);
		return "recipe/list";
	}

	// 레시피 유형 가져오기 (동적으로 코드맵에서 가져오기)
	@GetMapping("/create")
	public String createRecipeForm(Model model) {
		Map<CodeId, Code> recipeTypes = codeService.getRecipeTypes();
		model.addAttribute("recipeTypes", recipeTypes);
		return "recipe/create";
	}

	@PostMapping("/create")
	public String createRecipe(Recipe recipe, Principal principal, HttpSession session) {
		Member member = memberService.getMember(principal.getName()).get();
		String memId = member.getMemId();
		session.setAttribute("memId", memId);

		recipe.setRecipeTitle(recipe.getRecipeTitle());
		recipe.setRecipeContent(recipe.getRecipeContent());
		recipe.setMemId(memId);

		recipeService.createRecipe(recipe);
		return "redirect:/recipes";
	}

	@GetMapping("/update/{recipeId}")
	public String updateRecipeForm(@PathVariable Long recipeId, Model model, Principal principal, HttpSession session) {
		Member member = memberService.getMember(principal.getName()).get();
		String memId = member.getMemId();
		session.setAttribute("memId", memId);
		
		Recipe recipe = recipeService.getRecipe(recipeId).get(); // 레시피 조회
		Map<CodeId, Code> recipeTypes = codeService.getRecipeTypes(); // 코드 서비스로부터 레시피 유형들 조회
		String hashtags = recipeService.getHashtagsForRecipe(recipeId); // 레시피에 대한 해시태그 조회
		if(memId != recipe.getMemId()) {
			return "recipe/list";
		}

		model.addAttribute("recipe", recipe);
		model.addAttribute("recipeTypes", recipeTypes);
		model.addAttribute("hashtags", hashtags); // 쉼표로 구분된 해시태그 문자열 전달
		return "recipe/update"; // 수정 페이지로 이동
	}

	@PutMapping("/update/{recipeId}")
	public String updateRecipe(@PathVariable Long recipeId, Recipe recipe) {
		recipe.setRecipeTitle(recipe.getRecipeTitle());
		recipe.setRecipeContent(recipe.getRecipeContent());
		
		recipeService.updateRecipe(recipeId, recipe);
		return "recipe/view";
	}

	@PostMapping("/delete/{id}")
	public String deleteRecipe(@PathVariable Long id) {
		recipeService.deleteRecipe(id);
		return "redirect:/recipes/list";
	}

}
