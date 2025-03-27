package cookcloud.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cookcloud.entity.Likes;
import cookcloud.entity.Recipe;
import cookcloud.entity.Review;
import cookcloud.repository.LikesRepository;
import cookcloud.service.LikesService;

@Service
public class LikesService {

    @Autowired
    private LikesRepository likesRepository;
    
    // 좋아요한 레시피 조회
    public List<Recipe> getLikedRecipes(String memId) {
        return likesRepository.findByMemberMemId(memId)
                .stream().map(Likes::getRecipe).collect(Collectors.toList());
    }

	public boolean toggleLike(Long recipeId, String username) {
		// TODO Auto-generated method stub
		return false;
	}



}
