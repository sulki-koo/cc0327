package cookcloud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cookcloud.entity.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

	List<Recipe> findByMemberMemId(String memId);

	@Query("SELECT r FROM Recipe r WHERE r.recipeTitle LIKE %:keyword% OR r.memId LIKE %:keyword%")
	List<Recipe> searchByKeyword(@Param("keyword") String keyword);

}
