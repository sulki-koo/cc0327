package cookcloud.controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cookcloud.service.LikesService;

@Controller
public class LikesController {

	@Autowired
	private LikesService likesService;

	@PostMapping("/recipes/like/{recipeId}")
	@ResponseBody
	public Map<String, Boolean> likeRecipe(@PathVariable Long recipeId, @AuthenticationPrincipal User user) {
		boolean liked = likesService.toggleLike(recipeId, user.getUsername());
		return Collections.singletonMap("liked", liked);
	}

}
