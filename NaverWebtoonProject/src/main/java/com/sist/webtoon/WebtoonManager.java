package com.sist.webtoon;

import java.net.URLEncoder;
import java.util.HashMap;

import javax.imageio.ImageIO;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.net.*;
import java.util.*;

public class WebtoonManager {
	public void saveImage(String title){
		try{
			Document doc = Jsoup.connect("http://comic.naver.com/search.nhn?keyword="+title).get();
			Elements ids = doc.select("div.resultBox ul.resultList a");
			Element id = ids.get(0);
			String titleLink = id.attr("href");
			String titleId = titleLink.substring(titleLink.lastIndexOf("=")+1, titleLink.length());
			String listLink= "http://comic.naver.com/webtoon/list.nhn?titleId="+titleId;
			System.out.println(listLink);
			
			doc=Jsoup.connect(listLink).get();
			Elements episodes = doc.select("div#content.webtoon table.viewList tbody tr td.title a");
			Element episode = episodes.get(0);
			String episodeLink = episode.attr("href");
			String day = episodeLink.substring(episodeLink.lastIndexOf("=")+1, episodeLink.length()); //요일명을 알아내기
			String maxNo = episodeLink.substring(episodeLink.lastIndexOf("no=")+3, episodeLink.lastIndexOf("&"));
			System.out.println("weekday="+day+":maxNo="+maxNo);

			
			
			
			for(int i=Integer.parseInt(maxNo); i>0; i--){
				String realEpisodeLink = "http://comic.naver.com/webtoon/detail.nhn?titleId="+titleId+"&no="+i+"&weekday="+day;
				
				Connection.Response res = Jsoup.connect(realEpisodeLink).userAgent("Mozilla/5.0").timeout(0).method(Method.GET).execute();
				doc=Jsoup.connect(realEpisodeLink).userAgent("Mozilla/5.0").timeout(0).referrer("http://comic.naver.com").method(Method.GET).get();
				Elements images = doc.select("div#content div.section_cont div#comic_view_area div.wt_viewer img");
				
				File fileFolder = new File("/home/sist/webtoon/"+title+"/"+i);
				fileFolder.mkdirs();
				
				for(int j=0;j<images.size();j++){
					Element image = images.get(j);
					String imageLink = image.attr("src");
					System.out.println(imageLink);
					//doc=Jsoup.connect(imageLink).headers(res.headers()).ignoreContentType(true).referrer("http://comic.naver.com/index.nhn").get();
					//Elements imaggg = doc.select("img.shrinkToFit");
					//Element igg= imaggg.get(0);
					//String imm = igg.attr("src");
					//System.out.println(imm);
					
					
					URL url = new URL(imageLink);//그냥 이 링크로 접속하려고 하면 에러를 뱉어내기때문에 네이버 서버가 용인해줄 방법으로 접근 필요
					HttpURLConnection hc = (HttpURLConnection)url.openConnection();
					hc.setRequestMethod("GET");
					hc.setRequestProperty("Upgrade-Insecure-Requests", "1");
					hc.setRequestProperty("Cache-control", "max-age=2592000");
					hc.setRequestProperty("Referer", "http://comic.naver.com/index.nhn");
					hc.setUseCaches(false);
					hc.setDoInput(true);
					hc.setDoOutput(true);
					
					BufferedImage img = ImageIO.read(hc.getURL());
			       File file=new File("/home/sist/webtoon/"+title+"/"+i+"/"+j+".jpg");
			       ImageIO.write(img, "jpg", file);
				}
			}
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		WebtoonManager wm = new WebtoonManager();
		wm.saveImage("복학왕");		
	}
}
