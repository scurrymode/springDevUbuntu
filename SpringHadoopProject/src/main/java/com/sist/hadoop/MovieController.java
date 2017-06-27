package com.sist.hadoop;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sist.manager.MovieManager;
import com.sist.vo.MovieVO;

@Controller
public class MovieController {
	@Autowired
	private MovieManager mgr;
	
	@RequestMapping("movie/main.do")
	public String movie_main(Model model){
		List<MovieVO> list = mgr.cgvMainData();
		model.addAttribute("list", list);
		return "movie/main";
	}
	
}
