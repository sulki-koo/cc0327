package cookcloud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cookcloud.entity.Member;
import cookcloud.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long>{

	List<Review> findByMemberMemId(String memId);
	
	List<Review> findByRecipeId(Long recipeId);
	
}
