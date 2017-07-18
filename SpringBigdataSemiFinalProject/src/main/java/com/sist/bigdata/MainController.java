package com.sist.bigdata;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sist.dao.*;
import com.sist.news.Item;
import com.sist.news.NewsManager;
@Controller
public class MainController {
	@Autowired
	private MusicDAO dao;
	@Autowired
	private NewsManager mgr;
	@RequestMapping(value="main/ticket/main.do", method=RequestMethod.GET)
	public String ticket_main(Model model){
		List<MusicVO> list = dao.getMyRankData();
		List<Integer> gList = dao.getMusicRating("genie");
		List<Integer> mList = dao.getMusicRating("melon");
		
		String data = "[";
		for(int j=0;j<5;j++){
			String arr="[";
				arr+=mList.get(j)+","+gList.get(j)+","+list.get(j).getRating();
			arr+="]";
			data+="{name:'"+list.get(j).getTitle()+"',"+"data:"+arr+"},";
		}
		data=data.substring(0,data.lastIndexOf(","));
		data+="]";
		model.addAttribute("json", data);//그래프
		model.addAttribute("list", list);//음악목록
		model.addAttribute("main_jsp", "default.jsp");
		model.addAttribute("title", "T:CAT");//타이틀
		
		List<Item> nList =mgr.getNewsAllData("뮤직");
		for(Item i:nList){
			String s=i.getPubDate();
			Pattern p=Pattern.compile("[0-9]{2}:[0-9]{2}:[0-9]{2}");
			Matcher m=p.matcher(s);
			if(m.find()){
				i.setPubDate(m.group());
			}
		}
		model.addAttribute("nList", nList);
		
		return "ticket/main";
	}
}
