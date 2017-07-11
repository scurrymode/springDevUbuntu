package com.sist.total;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;
import com.sist.mongo.*;
import com.sist.r.RManager;

@Controller
public class StudentController {
	@Autowired
	private StudentDAO dao;
	@Autowired
	private RManager rm;
	
	@RequestMapping("main/list.do")
	public String std_list(Model model)
	{
		List<StudentVO> list = dao.stdAllData();
		model.addAttribute("list", list);
		return "main/list";
	}
	
	 @RequestMapping("main/find.do")
	 public String std_find(String ss,String fs,Model model)
	 {
		 List<StudentVO> list=dao.stdFindData(fs, ss);
		 model.addAttribute("list", list);
		 return "main/find";
	 }
	@RequestMapping("main/graph.do")
	public String std_graph(int no){
		rm.rGraph(no);
		System.out.println(no);
		return "main/graph";
	}
	
}
