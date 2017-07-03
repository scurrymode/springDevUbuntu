package com.sist.movie;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sist.dao.MovieDAO;
import com.sist.manager.MovieVO;

@Controller
public class MovieController {
	@Autowired
	private MovieDAO dao;
	@RequestMapping("main/main.do")
	public String movie_main(String page, Model model){
		if(page==null) page="1";
		int curpage = Integer.parseInt(page);
		int totalpage = dao.movieTotalPage();
		List<String> gList = dao.movieAllGenre();
		List<MovieVO> mList = dao.movieAllData(curpage);
		model.addAttribute("curpage", curpage);
		model.addAttribute("totalpage", totalpage);
		model.addAttribute("gList", gList);
		model.addAttribute("mList", mList);
		model.addAttribute("main_jsp", "default.jsp");
		return "main/main";
	}
	@RequestMapping("main/detail.do")
	public String movie_detail(int mno, int page, Model model){
		MovieVO vo = dao.movieDetailData(mno);
		model.addAttribute("vo",vo);
		model.addAttribute("page", page);
		model.addAttribute("main_jsp", "detail.jsp");
		return "main/main";
	}
}
