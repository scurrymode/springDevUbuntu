package com.sist.music;

import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
public class MelonManager {
	public static void main(String[] args) {
		MelonManager gm = new MelonManager();
		gm.melonTop100();
	}
	public List<MusicVO> melonTop100(){
		List<MusicVO> list = new ArrayList<MusicVO>();
		
		try {
			Document doc = Jsoup.connect("http://www.melon.com/chart/index.htm").get();
			Elements rank = doc.select("tr.lst50 td.t_left div.wrap span.rank");
			Elements title = doc.select("tr.lst50 td.t_left div.ellipsis strong a");
			Elements siger = doc.select("tr.lst50 td.t_left div.ellipsis a.play_artist span");
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
