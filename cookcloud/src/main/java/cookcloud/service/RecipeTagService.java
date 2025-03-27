package cookcloud.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cookcloud.entity.Hashtag;
import cookcloud.entity.RecipeTag;
import cookcloud.repository.HashtagRepository;
import cookcloud.repository.RecipeTagRepository;

@Service
public class RecipeTagService {

	@Autowired
	private RecipeTagRepository recipeTagRepository;
	
	@Autowired
	private HashtagRepository hashtagRepository;

	// 특정 레시피의 해시태그들을 쉼표로 구분된 문자열로 반환
	public String getHashtagsForRecipe(Long recipeId) {
		List<RecipeTag> recipeTags = recipeTagRepository.findByRecipeId(recipeId);

		return recipeTags.stream().map(recipeTag -> recipeTag.getHashtag()) // Hashtag 객체를 직접 가져옴
				.filter(hashtag -> hashtag != null).map(Hashtag::getHashName) // 해시태그 이름을 가져옴
				.collect(Collectors.joining(", "));
	}

	// 해시태그 검색 (해당 해시태그가 포함된 RecipeTag 목록 반환)
	public List<RecipeTag> searchByHashtag(String hashtagName) {
		// 해시태그 이름으로 Hashtag 객체를 찾음
		Hashtag hashtag = hashtagRepository.findByHashName(hashtagName);

		// 해시태그가 존재하면 해당 해시태그에 연결된 RecipeTag 목록 반환
		if (hashtag != null) {
			// 해당 해시태그의 hashId를 기준으로 RecipeTag 목록 조회
			return recipeTagRepository.findByhashId(hashtag.getHashId());
		}

		// 해시태그가 존재하지 않으면 빈 리스트 반환
		return List.of();
	}

}
