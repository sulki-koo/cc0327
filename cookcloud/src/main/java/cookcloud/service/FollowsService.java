package cookcloud.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cookcloud.entity.Follows;
import cookcloud.entity.Member;
import cookcloud.repository.FollowsRepository;
import cookcloud.service.FollowsService;

@Service
public class FollowsService {
	
	@Autowired
    private FollowsRepository followsRepository;
	
	// 내가 구독한(팔로잉) 사용자 목록 조회
    public List<Member> getMyFollowings(String memId) {
        return followsRepository.findByFollowerId(memId)
                .stream().map(Follows::getFollowing).collect(Collectors.toList());
    }
    
    // 나를 구독중인(팔로워) 사용자 목록 조회
    public List<Member> getMyFollowers(String memId) {
        return followsRepository.findByFollowingId(memId)
                .stream().map(Follows::getFollower).collect(Collectors.toList());
    }

	public boolean toggleFollow(Long recipeId, String username) {
		// TODO Auto-generated method stub
		return false;
	}

}
