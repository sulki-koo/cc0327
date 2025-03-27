package cookcloud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cookcloud.entity.Review;
import cookcloud.service.ReviewService;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

	@Autowired
	private ReviewService reviewService;
	
	@GetMapping("/{recipeId}")
	public String getReviews(@PathVariable Long recipeId, Model model) {
	    List<Review> reviews = reviewService.getReviews(recipeId);
	    model.addAttribute("reviews", reviews);
	    return "reviews";
	}

	
}
