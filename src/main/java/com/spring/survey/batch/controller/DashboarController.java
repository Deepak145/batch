package com.spring.survey.batch.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DashboarController {

	@RequestMapping(value = "/")
	public String getDashboard(Model model) {
		model.addAttribute("greeting", "Hello World!");
	   return "dashboard";
	}
}
