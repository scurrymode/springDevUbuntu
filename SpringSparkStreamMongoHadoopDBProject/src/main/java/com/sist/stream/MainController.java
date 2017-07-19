package com.sist.stream;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import javax.annotation.PostConstruct;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sist.mongo.MusicRankDAO;
import com.sist.mongo.MusicRankVO;
import java.util.*;
@Controller
public class MainController {
	@Autowired
	private Configuration conf;
	@Autowired
	private MusicRankDAO dao;
	
	@RequestMapping("main/main.do")
	public String main_main(){
		return "main/main";
	}
	@RequestMapping("main/graph.do")
	public String main_graph(Model model){
		hadoopFileRead();//여기서 계속 호출하겠다~!
		String[] color ={"#FF0F00","#FF6600","#FF9E01","#FCD202","#F8FF01","#B0DE09","#04D215","#0D8ECF","#0D52D1","#2A0CD0"};
		List<MusicRankVO> list = dao.musicAllData();
		JSONArray arr = new JSONArray();
		int i=0;
		for(MusicRankVO vo : list){
			JSONObject obj = new JSONObject();
			try {
				obj.put("name", vo.getName());
				obj.put("count", vo.getCount());
				obj.put("color", color[i]);
				arr.add(obj);
				i++;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		/*
		 * [{
		    "country": "USA",
		    "visits": 4025,
		    "color": "#FF0F00"
		  }, {
		    "country": "Taiwan",
		    "visits": 338,
		    "color": "#333333"
		  }, {
		    "country": "Poland",
		    "visits": 328,
		    "color": "#000000"
		  }]
		 * */	
				
		model.addAttribute("json", arr.toJSONString());
		return "main/graph";
	}
	
	public void hadoopFileRead(){
		try {
			FileSystem fs = FileSystem.get(conf);
			FileStatus[] status = fs.listStatus(new Path("/user/hadoop"));
			for(FileStatus sss:status){
				String temp = sss.getPath().getName();
				if(!temp.startsWith("music"))
					continue;
				FileStatus[] status2 = fs.listStatus(new Path("/user/hadoop/"+sss.getPath().getName()));
				for(FileStatus ss:status2){
					String name = ss.getPath().getName();
					if(!name.equals("_SUCCESS")){
						FSDataInputStream is = fs.open(new Path("/user/hadoop/"+sss.getPath().getName()+"/"+name));
						BufferedReader br = new BufferedReader(new InputStreamReader(is));
						while(true){
							String line = br.readLine();
							if(line==null)break;
							StringTokenizer st = new StringTokenizer(line);
							MusicRankVO vo = new MusicRankVO();
							vo.setName(st.nextToken().trim().replace("$", " "));
							vo.setCount(Integer.parseInt(st.nextToken().trim()));
							dao.musicInsert(vo);
						}
						br.close();
					}
				}
			//	fs.delete(new Path("/user/hadoop/"+sss.getPath().getName()),true); //이걸키고 sparkMain을 돌려두면 실시간 읽어온걸 반영
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@PostConstruct
	public void init(){//시작하자마자 실행되어야 하기에~
		hadoopFileRead();
	}
	
}
