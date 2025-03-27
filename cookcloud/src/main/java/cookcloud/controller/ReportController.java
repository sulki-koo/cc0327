package cookcloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cookcloud.service.ReportService;

@Controller
public class ReportController {

	@Autowired
	private ReportService reportService;
	
	@PostMapping("/recipes/report/{recipeId}")
	@ResponseBody
	public void reportRecipe(@PathVariable Long recipeId) {
		reportService.reportRecipe(recipeId);
	}

}
