package com.sist.bigdata;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sist.news.Item;
import com.sist.news.NewsManager;

@Controller
public class NewsController {
	@Autowired
	private NewsManager nm;
	
	@RequestMapping("main/news.do")
	public String newsFindData(String data,Model model){
		if(data==null){
			data="½Å´º½º";
		}
		List<Item> list =nm.newsAllData(data); 
		model.addAttribute("main_jsp","news/news.jsp");
		model.addAttribute("list",list);
		return "main/main";
	}
}
