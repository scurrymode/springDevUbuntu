package com.sist.movie;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.mapreduce.JobRunner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sist.dao.MovieDAO;
import com.sist.manager.MovieInfoVO;
import com.sist.manager.MovieManager;
import com.sist.manager.MovieVO;
import com.sist.naver.NaverBlogManager;
import com.sist.r.RManager;
import com.sist.recommand.RecommandManager;

import java.io.*;
import java.util.*;

import javax.annotation.Resource;

@Controller
public class MovieController {
	@Autowired
	private Configuration conf; // 이렇게 하면 configuration bean에 있는 정보대로 올려주나봐~!
	@Autowired
	private NaverBlogManager nbm;
	@Autowired
	private MovieDAO dao;
	@Autowired
	private MovieManager mgr;
	@Resource(name="mj")
	private JobRunner jr;
	@Resource(name="rj")
	private JobRunner recomm;
	@Autowired
	private RecommandManager rcm;
	@Autowired
	private RManager rm;
	

	@RequestMapping("main/main.do")
	public String movie_main(String page, Model model) {
		if (page == null)
			page = "1";
		int curpage = Integer.parseInt(page);
		List<String> gList = dao.movieAllGenre();
		List<MovieVO> mList = dao.movieAllData(curpage);
		List<MovieInfoVO> rList = mgr.cgvMainData();
		int totalpage = dao.movieTotalPage();
		model.addAttribute("rList", rList);
		model.addAttribute("curpage", curpage);
		model.addAttribute("totalpage", totalpage);
		model.addAttribute("gList", gList);
		model.addAttribute("mList", mList);
		model.addAttribute("main_jsp", "default.jsp");
		return "main/main";
	}

	@RequestMapping("main/detail.do")
	public String main_detail(int mno, int page, Model model) {
		MovieVO vo = dao.movieDetail(mno);
		model.addAttribute("vo", vo);
		model.addAttribute("page", page);
		model.addAttribute("main_jsp", "detail.jsp");
		// 파일 만들기
		nbm.naverBlogData(vo.getTitle()); // 검색하고
		nbm.naverXmlParse(); // 파일 만들고
		hadoopFileDelete(); // 올라가있던 파일 지우고
		copyFromLocal(); // 새로 올린다.
		// 분석 결과값 =>local => R(통계) => 몽고디비에 저장 => Web에 데이터 전송(그래프)
		try {
			jr.call(); // 하둡 맵,리듀서로 분석 돌리기!
		} catch (Exception e) {
			e.printStackTrace();
		}
		copyToLocal();
		String json = rm.rFeelGraph(); //R 통계 내기
		model.addAttribute("json", json); //detail 페이지에서 갖다쓰라고
		model.addAttribute("title", vo.getTitle());
		return "main/main";
	}

	@RequestMapping("main/find.do")
	public String main_find(String sb, Model model) {
		List<MovieVO> list = dao.movieFind(sb);
		model.addAttribute("list", list);
		model.addAttribute("main_jsp", "find.jsp");
		return "main/main";
	}

	@RequestMapping("main/genre.do")
	public String main_genre(String data, Model model) {
		List<MovieVO> list = dao.movieGenreFind(data);
		String json = jsonCreate(list);
		System.out.println(json);
		model.addAttribute("json", json);
		model.addAttribute("list", list);
		model.addAttribute("genre", data);
		model.addAttribute("main_jsp", "../genre/genre.jsp");
		return "main/main";
	}
	
	@RequestMapping("main/recommand.do")
	public String main_recommand(String redata, Model model) {
		if(redata==null){
			redata="더운날";
		}
		redata=redata+"에 볼만한 영화";
		rcm.naverBlogData(redata);
		rcm.naverXmlParse();
		recommFileDelete();
		recommCopyFromLocal();
		
		try {
			recomm.call();
		} catch (Exception e) {
			e.printStackTrace();
		}
		recommCopyToLocal();
		List<MovieVO> list = rm.recommandData();
		String json = jsonCreate(list);
		model.addAttribute("json", json);
		model.addAttribute("genre", redata);
		model.addAttribute("main_jsp", "../genre/recommand.jsp");
		return "main/main";
	}
	

	public String jsonCreate(List<MovieVO> list) {
		String data = "";
		/*
		 * { movieTitle: "Ender's Game", movieDirector: "Gavin Hood",
		 * movieImage:
		 * "https://s3-us-west-2.amazonaws.com/s.cdpn.io/3/movie-endersgame.jpg"
		 * }
		 */
		try {
			JSONArray arr = new JSONArray();
			for (MovieVO vo : list) {
				JSONObject obj = new JSONObject();
				obj.put("movieTitle", vo.getTitle());
				obj.put("movieDirector", vo.getDirector());
				obj.put("movieImage", vo.getPoster());
				arr.add(obj);
			}
			data = arr.toJSONString();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return data;
	}

	// 1. 폴더 지우기(HDFS)
	public void hadoopFileDelete() {
		try {
			// hadoop fs
			FileSystem fs = FileSystem.get(conf);
			if (fs.exists(new Path("/input_movie_ns3/naver_ns3.txt"))) {
				// rm -rf
				fs.delete(new Path("/input_movie_ns3/naver_ns3.txt"), true);
			}
			if (fs.exists(new Path("/output_movie_ns3"))) {
				// rm -rf
				fs.delete(new Path("/output_movie_ns3"), true);
			}
			fs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 2. 파일 올리기
	// copyFromLocal(이건 사라졌고 appendToFile로 바뀜), copyToLocal(이 명령어는 아직 존재)
	public void copyFromLocal() {
		try {
			FileSystem fs = FileSystem.get(conf);
			fs.copyFromLocalFile(new Path("/home/sist/movie_data/naver.txt"), new Path("/input_movie_ns3/naver_ns3.txt")); 
			// 앞에꺼가 로컬, 뒤에꺼가 하둡
			// copyToLocal일때는 앞이 하둡, 뒤가 로컬일것!
			fs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void copyToLocal() {
		try {
			FileSystem fs = FileSystem.get(conf);
			fs.copyToLocalFile(new Path("/output_movie_ns3/part-r-00000"), new Path("/home/sist/movie_data/result"));
			fs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//여긴 추천하기 이렇게 할 필요 없고 매개변수로 하면 되는뎅.... 바보임...ㅋㅋ
	// 1. 폴더 지우기(HDFS)
		public void recommFileDelete() {
			try {
				// hadoop fs
				FileSystem fs = FileSystem.get(conf);
				if (fs.exists(new Path("/input_recom_ns3/recommand_ns3.txt"))) {
					// rm -rf
					fs.delete(new Path("/input_recom_ns3/recommand_ns3.txt"), true);
				}
				if (fs.exists(new Path("/output_recom_ns3"))) {
					// rm -rf
					fs.delete(new Path("/output_recom_ns3"), true);
				}
				fs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// 2. 파일 올리기
		// copyFromLocal(이건 사라졌고 appendToFile로 바뀜), copyToLocal(이 명령어는 아직 존재)
		public void recommCopyFromLocal() {
			try {
				FileSystem fs = FileSystem.get(conf);
				fs.copyFromLocalFile(new Path("/home/sist/movie_data/recommand_ns3.txt"), new Path("/input_recom_ns3/recommand_ns3.txt")); 
				// 앞에꺼가 로컬, 뒤에꺼가 하둡
				// copyToLocal일때는 앞이 하둡, 뒤가 로컬일것!
				fs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		public void recommCopyToLocal() {
			try {
				FileSystem fs = FileSystem.get(conf);
				fs.copyToLocalFile(new Path("/output_recom_ns3/part-r-00000"), new Path("/home/sist/movie_data/recommand_result"));
				
				fs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
}
