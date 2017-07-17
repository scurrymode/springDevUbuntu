package com.sist.music;

import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
public class TripAdvisorManager {
	public static void main(String[] args) {
		TripAdvisorManager gm = new TripAdvisorManager();
		gm.top25();
	}
	public List<RankPlaceVO> top25(){
		List<RankPlaceVO> list = new ArrayList<RankPlaceVO>();
		
		try {
			Document doc = Jsoup.connect("https://www.tripadvisor.co.kr/TravelersChoice-Destinations-cTop-g1").get();
			Elements ranks = doc.select("div.winnerLayer div.posn span.posn_inner");
			Elements titles = doc.select("div.winnerLayer div.mainName");
			Elements imgs = doc.select("div.winnerInfo_lb li.fl img");
			for(int i = 0;i<11;i++){
				String placeTitle=titles.get(i).text();
				System.out.println(ranks.get(i).text()+" "+placeTitle.substring(0, placeTitle.lastIndexOf(","))+" "+imgs.get(i).attr("src"));
				RankPlaceVO vo = new RankPlaceVO();
				vo.setRank(Integer.parseInt(ranks.get(i).text().trim()));
				vo.setTitle(placeTitle.substring(0, placeTitle.lastIndexOf(",")));
				vo.setImg(imgs.get(i).attr("src"));
				list.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
