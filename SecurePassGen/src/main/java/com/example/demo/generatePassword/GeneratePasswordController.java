package com.example.demo.generatePassword;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.PasswordEntity;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class GeneratePasswordController {

	private final PasswordGenerateService service;

//	パスワード生成画面を表示する
	@GetMapping("/generate")
	public String showGeneratePasswordWindow(Model model) {
		model.addAttribute("passwordConfigForm", new PasswordConfigForm());
		return "generate";
	}
	
//パスワード生成画面に設定を引き継いで戻る
	@PostMapping("/generate")
	public String backToGeneratePasswordWindow(@ModelAttribute PasswordConfigForm form,
			BindingResult result) {
		
		if(result.hasErrors()) {
			throw new IllegalArgumentException();
		}
		
		return "generate";
	}

//	パスワード生成→結果を表示する
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
			return "result";
			
		} catch (Exception e) {
			System.err.println("Error during password generation: " + e.getMessage());
			e.printStackTrace();
			model.addAttribute("error", "パスワード生成中にエラーが発生しました");
			return "generate";
		}
	}
}
