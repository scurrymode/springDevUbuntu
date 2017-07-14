package com.sist.flume;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class RankData {
	public static List<String> daumRank() {
		List<String> list = new ArrayList<String>();
		try {
			Document document = Jsoup.connect("http://www.daum.net").get();
			if (null != document) {
				Elements elements = document.select("div.rank_cont span.txt_issue");

				for (int i = 0; i < 20; i += 2) {
					list.add(elements.get(i).text());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static List<String> naverRank() {
		List<String> list = new ArrayList<String>();
		try {
			Document document = Jsoup.connect("http://www.naver.com").get();

			if (null != document) {
				Elements elements = document.select("div.ah_roll_area span.ah_k");

				for (int i = 0; i < 10; i++) {
					list.add(elements.get(i).text());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
