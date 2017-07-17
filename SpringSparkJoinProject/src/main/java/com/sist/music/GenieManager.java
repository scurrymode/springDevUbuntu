package com.sist.music;

import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
public class GenieManager {
	public static void main(String[] args) {
		GenieManager gm = new GenieManager();
		gm.genieTop100();
	}
	public List<MusicVO> genieTop100(){
		List<MusicVO> list = new ArrayList<MusicVO>();
		
		try {
			Document doc = Jsoup.connect("http://www.genie.co.kr/chart/top100").get();
			Elements rank = doc.select("div.list span.number");
			Elements title = doc.select("div.list span.music a.title");
			Elements siger = doc.select("div.list span.meta a.artist");
			for(int i = 0;i<rank.size();i++){
				System.out.println(rank.get(i).text()+" "+title.get(i).text()+" "+siger.get(i).text());
				MusicVO vo = new MusicVO();
				vo.setRank(Integer.parseInt(rank.get(i).text().trim()));
				vo.setTitle(title.get(i).text());
				vo.setSinger(siger.get(i).text());
				list.add(vo);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
