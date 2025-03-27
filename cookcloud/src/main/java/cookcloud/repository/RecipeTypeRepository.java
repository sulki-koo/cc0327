package cookcloud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cookcloud.entity.Member;
import cookcloud.entity.Recipe;
import cookcloud.entity.RecipeType;

public interface RecipeTypeRepository extends JpaRepository<RecipeType, Long>{

	// 레시피 유형 코드로 검색
	@Query("SELECT r FROM Recipe r JOIN RecipeType rt ON r.recipeId = rt.recipeId WHERE rt.recipeTypeCode = :recipeTypeCode")
	List<Recipe> findByRecipeTypeCode(Long recipeTypeCode);
	
}
