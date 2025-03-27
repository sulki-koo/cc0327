package cookcloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cookcloud.entity.Hashtag;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {

	// 해시태그 이름으로 조회
	Hashtag findByHashName(String hashName);
	
}
