package com.sist.bigdata;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {
	@RequestMapping(value="main/ticket/main.do", method=RequestMethod.GET)
	public String ticket_main(Model model){
		model.addAttribute("main_jsp", "default.jsp");
		model.addAttribute("title", "T:CAT");
		return "ticket/main";
	}
}
