package cookcloud.service;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cookcloud.entity.Code;
import cookcloud.entity.CodeId;
import cookcloud.repository.CodeRepository;
import jakarta.annotation.PostConstruct;

@Service
public class CodeService {

	@Autowired
	private CodeRepository codeRepository;
	
	private static Map<CodeId, Code> codeMap;

	@PostConstruct // 서버 실행시 자동 실행
	public void loadCodes() {
		List<Code> codes = codeRepository.findAll();
		
		codeMap = codes.stream().collect(Collectors.toMap(
				code ->  new CodeId(code.getParentCode(), code.getChildCode()), 
				code -> code
				));
		 System.out.println("서비스-코드맵 : " + codeMap);  // 로딩된 map의 크기 확인
	}
	
	public Code getCodeInfo(CodeId codeId) {
		return codeMap.get(codeId);
	}
	
	public Map<CodeId, Code> getAllCode() {
		return codeMap;
	}

	// 레시피 유형 코드만 가져오는 메서드
    public Map<CodeId, Code> getRecipeTypes() {
        Map<CodeId, Code> recipeTypes = codeMap.entrySet().stream()
                .filter(entry -> entry.getKey().getParentCode() == 5L)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));  // 필터링된 코드만 반환

        return recipeTypes;
    }
	
}
