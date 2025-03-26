package cookcloud.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cookcloud.entity.MemberAllergyFood;
import cookcloud.entity.MemberAllergyFoodId;
import cookcloud.repository.MemberAllergyFoodRepository;

@Service
public class MemberAllergyFoodService {

	@Autowired
	private MemberAllergyFoodRepository memberAllergyFoodRepository;
	
	public Optional<MemberAllergyFood> getMemberAllergyFood(String memId, Long allergyId) {
		return memberAllergyFoodRepository.findById(new MemberAllergyFoodId(memId, allergyId));
	}

	@Transactional
	public void insertMemAllergyFood(MemberAllergyFood memberAllergyFood) {
		memberAllergyFood.setMemAllergyInsertAt(LocalDateTime.now());
		memberAllergyFood.setMemAllergyIsDeleted("N");
		memberAllergyFoodRepository.save(memberAllergyFood);
	}
	
	@Transactional
	public void updateMemberAllergyFood(String memId, Long allergyId){
		Optional<MemberAllergyFood> optionalFood = getMemberAllergyFood(memId, allergyId);

        if (optionalFood.isPresent()) {
            MemberAllergyFood findMemberAllergyFood = optionalFood.get();
            findMemberAllergyFood.setMemAllergyDeleteAt(LocalDateTime.now());
            findMemberAllergyFood.setMemAllergyIsDeleted("Y");
            memberAllergyFoodRepository.save(findMemberAllergyFood);
        }
	}
	
}
