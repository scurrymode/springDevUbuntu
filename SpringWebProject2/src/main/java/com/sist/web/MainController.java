package com.sist.web;

import java.io.UnsupportedEncodingException;
import java.util.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MainController {
	@RequestMapping("main/input.do")
	public String main_input(){
		return "main/input";
	}
	
	@RequestMapping("main/input_ok.do")
	public String main_input_ok(String name, String sex, RedirectAttributes attr) 
			throws UnsupportedEncodingException{
		//return "redirect:/main/output.do?name="+URLEncoder.encode(name,"UTF-8")+"&sex="+URLEncoder.encode(sex,"UTF-8");
		/*attr.addAttribute("name",name);
		attr.addAttribute("sex",sex);*/
		
		Map map = new HashMap();
		map.put("name", name);
		map.put("sex", sex);
		attr.addFlashAttribute("map", map);
		//attr.addFlashAttribute("sex", sex);
		
		return "redirect:/main/output.do";
	}
	
	@RequestMapping("main/output.do")
	public String main_output(String name, String sex, Model model){
		model.addAttribute("name", name);
		model.addAttribute("sex",sex);
		return "main/output";
	}
	@RequestMapping("temp/input.do")
	public String temp_input(){
		return "temp/input";
	}
	@RequestMapping("temp/success.do")
	public String temp_success(){
		return "temp/success";
	}
	@RequestMapping("temp/fail.do")
	public String temp_fail(){
		return "temp/fail";
	}
	
}
