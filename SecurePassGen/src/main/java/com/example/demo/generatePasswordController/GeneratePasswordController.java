package com.example.demo.generatePasswordController;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class GeneratePasswordController {

	private final PasswordGenerateService service;

	@GetMapping("/generate")
	public String showGeneratePasswordWindow(Model model) {
		model.addAttribute("passwordConfigForm", new PasswordConfigForm());
		return "generate";
	}
	
	@PostMapping("/generate")
	public String backToGeneratePasswordWindow(@ModelAttribute PasswordConfigForm form,
			BindingResult result) {
		
		if(result.hasErrors()) {
			throw new IllegalArgumentException();
		}
		
		return "generate";
	}

	// password生成
	@PostMapping("/generatePassword")
	public String generatePassword(@ModelAttribute @Validated PasswordConfigForm form,
			BindingResult bindingResult,
			Model model) {

		if (bindingResult.hasErrors()) {
	        System.out.println("Validation errors: " + bindingResult.getAllErrors());
	        return "generate";
	    }

		try {
			System.out.println("Received form: " + form);
			List<PasswordEntity> passwordList = service.generate(form.toEntity());
			model.addAttribute("passwordList", passwordList);
			return "displayPasswordList";
			
		} catch (Exception e) {
			System.err.println("Error during password generation: " + e.getMessage());
			e.printStackTrace();
			model.addAttribute("error", "パスワード生成中にエラーが発生しました");
			return "generate";
		}
	}
}
