package com.example.demo.showTopViewController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ShowTopViewController {

	@GetMapping("/")
	public String showTopView() {
		return "top";
	}
}
