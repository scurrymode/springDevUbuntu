package com.sist.bigdata;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.mapreduce.JobRunner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import com.sist.food.dao.*;
import com.sist.naver.NaverBlogManager;
import com.sist.r.RManager;

import java.io.IOException;
import java.util.*;

import javax.annotation.Resource;

@Controller
public class FoodMainController {
	@Autowired
	private Configuration conf;
	@Resource(name="fr")
	private JobRunner fjob;
	
	@Autowired
	private RManager rm;
	
	@Autowired
	private NaverBlogManager nbm;
	
	@Autowired
	private FoodManager fmgr;
	
	@RequestMapping("main/main.do")
	public String main_page(Model model){
		
		List<CategoryVO> list = fmgr.categoryAllData();
		model.addAttribute("list",list);
		model.addAttribute("main_jsp","default.jsp");
		return "main/main";
	}
	@RequestMapping("main/loc.do")
	public String main_loc(Model model)
	{
		model.addAttribute("main_jsp", "food/food_loc.jsp");
		return "main/main";
	}
	@RequestMapping("main/foodmain.do")
	public String main_foodpage(String title,String link,Model model){
		
		List<FoodVO> list=fmgr.categoryDetailData(link);
		model.addAttribute("title", title);
		model.addAttribute("list", list);
		model.addAttribute("main_jsp","food/foodmain.jsp");
		return "main/main";
	}
	@RequestMapping("main/fooddetail.do")
	public String main_fooddetail(String link,String poster,Model model)
	{
		FoodVO vo=fmgr.foodDetailData(link);
		nbm.naverBlogData(vo.getName()); //검색하고
		nbm.naverXmlParse(); //결과 파싱해서 txt로 저장하고
		foodFileDelete(); //하둡에 있던 파일 지우고 (덮어쓰기 안되서)
		foodCopyFromLocal(); //로컬에 있던 파일 하둡에 올리고 
		try {
			fjob.call(); //하둡에서 Map Reduce처리하고 갯수 새 준다.
		} catch (Exception e) {
			e.printStackTrace();
		}
		foodCopyToLocal(); //하둡에서 분석한걸 로컬에 다운로드
		
		String json=rm.tasteData();//분석할걸 R로 정리해서 그걸 json으로 만들어서 highchart에 보여주기 좋은걸로 만들기
		model.addAttribute("json", json);
		model.addAttribute("vo", vo);
		model.addAttribute("poster", poster);
		model.addAttribute("main_jsp","food/fooddetail.jsp");
		return "main/main";
	}
	public void foodFileDelete(){
		try{
			FileSystem fs = FileSystem.get(conf);
			if(fs.exists(new Path("/food_input_ns3/naver.txt"))){
				fs.delete(new Path("/food_input_ns3/naver.txt"), true);
			}
			if(fs.exists(new Path("/food_input_ns3/site.txt"))){
				fs.delete(new Path("/food_input_ns3/site.txt"), true);
			}
			if(fs.exists(new Path("/food_output_ns3"))){
				fs.delete(new Path("/food_output_ns3"), true);
			}
			fs.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void foodCopyFromLocal(){
		try {
			FileSystem fs = FileSystem.get(conf);
			fs.copyFromLocalFile(new Path("/home/sist/food_data/naver.txt"), new Path("/food_input_ns3/naver.txt"));
			fs.copyFromLocalFile(new Path("/home/sist/food_data/site.txt"), new Path("/food_input_ns3/site.txt"));
			fs.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void foodCopyToLocal(){
		try {
			FileSystem fs = FileSystem.get(conf);
			fs.copyToLocalFile(new Path("/food_output_ns3/part-r-00000"), new Path("/home/sist/food_data/result"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}






