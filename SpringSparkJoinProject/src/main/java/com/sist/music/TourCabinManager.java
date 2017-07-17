package com.sist.music;

import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
public class TourCabinManager {
	public static void main(String[] args) {
		TourCabinManager gm = new TourCabinManager();
		gm.getSell();
	}
	public List<RankPlaceVO> getSell(){
		List<RankPlaceVO> list = new ArrayList<RankPlaceVO>();
		
		try {
			//Document doc = Jsoup.connect("http://www.tourcabin.com/good/imm/imm.l.jsp#c=P/////1/").get();
			Document doc = Jsoup.connect("http://www.tourcabin.com/").get();
			Elements trips = doc.select("div.xmcard-pabal li.xcon span.xmca-title");
			Elements dates = doc.select("div.xmcard-pabal li.xcon span.xmca-title em");
			Elements prices = doc.select("div.xmcard-pabal li.xcon span.xmca-price");
			Elements links = doc.select("div.xmcard-pabal li.xcon a");
			for(int i = 0;i<11;i++){
				System.out.println(trips.get(i).text()+" "+dates.get(i).text()+" "+prices.get(i).text()+" "+links.get(i).attr("href"));
				/*SellTripVO vo = new SellTripVO();
				
				list.add(vo);*/
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
