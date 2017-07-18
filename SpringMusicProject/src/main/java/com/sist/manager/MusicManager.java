package com.sist.manager;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

@Component
public class MusicManager {
	public static void main(String[] args) {
		MusicManager mm = new MusicManager();
		mm.musicTop10();
	}
	public List<MusicVO> musicTop10(){
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
				vo.setLink(youtubeGetLink(vo.getTitle()));
//				System.out.println(vo.getTitle()+"==="+vo.getLink());
				list.add(vo);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	public String youtubeGetLink(String title){
		String url="";
		try {
			Document doc = Jsoup.connect("https://www.youtube.com/results?search_query="+title).get();
			Elements te=doc.select("h3.yt-lockup-title a");
			Element tElem=te.get(0);
			String temp=tElem.attr("href");
			String ti=tElem.attr("title");
			temp=temp.substring(temp.lastIndexOf("=")+1, temp.length());
			url="https://www.youtube.com/embed/"+temp;
//			System.out.println(temp+"제목은:"+ti);
		} catch (Exception e) {
		}
		return url;
	}
}
