package com.sist.music;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sist.daum.DaumManager;
import com.sist.manager.MusicManager;
import com.sist.manager.MusicVO;
import com.sist.naver.NaverManager;
import com.sist.review.DataDirectory;
import com.sist.weather.WeatherManager;
import com.sist.weather.WeatherVO;

@Controller
public class MusicController {
	@Autowired
	private MusicManager mgr;
	@Autowired
	private WeatherManager wgr;
	@Autowired
	private DataDirectory dd;
	@Autowired
	private DaumManager dm;
	@Autowired
	private NaverManager nm;
	@Autowired
	private HadoopManager hm;
	
	@RequestMapping(value="main/main.do", method=RequestMethod.GET)
	public String main_main(Model model){
		List<MusicVO> list = mgr.musicTop10();
		List<WeatherVO> wList = wgr.todayGetWeather();
		model.addAttribute("feel_data", dd.dataDir1());
		model.addAttribute("genre_data", dd.dataDir2());
		model.addAttribute("wList", wList);
		model.addAttribute("main_jsp", "default.jsp");
		model.addAttribute("list", list);
		return "main/main";
	}
	@RequestMapping("main/music_feel_find.do")
	public String main_feel_find(String feel_data, Model model){
		System.out.println(feel_data);
		return "main/main";
	}
	@RequestMapping("main/music_genre_find.do")
	public String main_genre_find(String genre_data, Model model){
		System.out.println(genre_data);
		return "main/main";
	}
	@RequestMapping("main/detail.do")
	public String main_detail(String title, Model model){
		dm.daumReview(title);
		nm.naverBlogData(title);
		nm.naverXmlParse();
		
		hm.hadoopFileDelete();
		hm.copyFromLocal();
		
		hm.mapReduceExecute();
		hm.copyToLocal();
		return "main/main";
	}
}
