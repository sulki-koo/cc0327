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

import cookcloud.service.FollowsService;

@Controller
public class FollowsController {
	
	@Autowired
	private FollowsService followsService;

	@PostMapping("/recipes/follow/{recipeId}")
	@ResponseBody
	public Map<String, Boolean> followRecipe(@PathVariable Long recipeId, @AuthenticationPrincipal User user) {
	    boolean followed = followsService.toggleFollow(recipeId, user.getUsername());
	    return Collections.singletonMap("followed", followed);
	}

}
