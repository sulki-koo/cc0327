package cookcloud.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cookcloud.entity.Code;
import cookcloud.entity.CodeId;
import cookcloud.service.CodeService;

@RestController
@RequestMapping("/code")
public class CodeController {

	@Autowired
	private CodeService codeService;

	@GetMapping
	public ResponseEntity<Map<CodeId, Code>> getAllCode() {
		Map<CodeId, Code> codeData = codeService.getAllCode();

		if (codeData != null && !codeData.isEmpty()) {
			return ResponseEntity.ok(codeData);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@GetMapping("/recipe-types")
	public Map<CodeId, Code> getRecipeTypes() {
		return codeService.getRecipeTypes();
	}

}
