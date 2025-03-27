package cookcloud.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import cookcloud.entity.Hashtag;
import cookcloud.repository.HashtagRepository;

public class HashtagService {

	@Autowired
	private HashtagRepository hashtagRepository;

	public Optional<Hashtag> getHashtag(Long hashtagId) {
		return hashtagRepository.findById(hashtagId);
	}

	@Transactional
	public Hashtag createOrGetHashtag(String hashtagName) {
		Hashtag hashtag = hashtagRepository.findByHashName(hashtagName);
		if (hashtag == null) {
			// 새로운 해시태그 등록
			hashtag = new Hashtag();
			hashtag.setHashName(hashtagName);
			hashtag.setHashUsageCount(1L); // 최초 사용
		} else {
			// 기존 해시태그 사용횟수 증가
			hashtag.setHashUsageCount(hashtag.getHashUsageCount() + 1);
		}
		return hashtagRepository.save(hashtag);
	}

}
