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
			String day = episodeLink.substring(episodeLink.lastIndexOf("=")+1, episodeLink.length()); 
			//요일명을 알아내기
			String maxNo = episodeLink.substring(episodeLink.lastIndexOf("no=")+3, episodeLink.lastIndexOf("&"));
			System.out.println("weekday="+day+":maxNo="+maxNo);
			
			for(int i=Integer.parseInt(maxNo); i>0; i--){
				String realEpisodeLink = "http://comic.naver.com/webtoon/detail.nhn?titleId="+titleId+"&no="+i+"&weekday="+day;
				
				Connection.Response res = Jsoup.connect(realEpisodeLink).userAgent("Mozilla/5.0").timeout(0).method(Method.GET).execute();
				doc=Jsoup.connect(realEpisodeLink).userAgent("Mozilla/5.0").timeout(0).referrer("http://comic.naver.com").method(Method.GET).get();
				Elements images = doc.select("div#content div.section_cont div#comic_view_area div.wt_viewer img");
				//저장위치도 직접 정할 수 있도록 해주면 더 좋을듯~!
				File fileFolder = new File("/home/sist/webtoon/"+title+"/"+i);
				fileFolder.mkdirs();
				
				for(int j=0;j<images.size();j++){
					Element image = images.get(j);
					String imageLink = image.attr("src");
					System.out.println(imageLink);				
					URL url = new URL(imageLink);
					//그냥 이 링크로 접속하려고 하면 에러를 뱉어내기때문에 네이버 서버가 용인해줄 방법으로 접근 필요
					URLConnection urlConn = url.openConnection();
					urlConn.setRequestProperty("Referer", "http://comic.naver.com/index.nhn");
					//이제 이미지 다운로드 //이후 이미지들을 이어붙여서 하나의 파일로까지 만들어주는거 고려해보자~!				
					BufferedImage img = ImageIO.read(urlConn.getInputStream());
			       File file=new File("/home/sist/webtoon/"+title+"/"+i+"/"+j+".jpg");
			       ImageIO.write(img, "jpg", file);
				}
			}
			System.out.println(title+"다운로드 완료");
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		WebtoonManager wm = new WebtoonManager();
		wm.saveImage("신의 탑");		
	}
}
