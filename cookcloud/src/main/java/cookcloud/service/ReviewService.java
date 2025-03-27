package cookcloud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cookcloud.entity.Review;
import cookcloud.repository.RecipeRepository;
import cookcloud.repository.ReviewRepository;
import cookcloud.service.ReviewService;

@Service
public class ReviewService {

	@Autowired
    private ReviewRepository reviewRepository;

	// 내가 작성한 리뷰 조회
    public List<Review> getMyReviews(String memId) {
        return reviewRepository.findByMemberMemId(memId);
    }

	public List<Review> getReviews(Long recipeId) {
		return reviewRepository.findByRecipeId(recipeId);
	}

}
