package com.sist.manager;
/*
 * package
 * 1) model => @Controller
 * 2) vo => 사용자 정의 데이터형
 * 3) manage
 * 4) database
 * */
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.sist.vo.MovieVO;
@Component
public class MovieManager {
	
	public static void main(String[] arg){
		MovieManager manager = new MovieManager();
		manager.cgvMainData();
	}
	public List<MovieVO> cgvMainData(){
		List<MovieVO> list = new ArrayList<MovieVO>();
		try {
			Document doc = Jsoup.connect("http://www.cgv.co.kr/movies").get();	
			//title
			Elements telem=doc.select("div.box-contents a strong.title");
			//poster
			Elements pelem=doc.select("div.box-image span.thumb-image img");
			//reserve
			Elements relem=doc.select("div.score strong.percent span");
			//regdate
			Elements delem=doc.select("div.box-contents span.txt-info strong");
			//like
			Elements felem=doc.select("div.box-contents span.like span.count strong i");
			//link
			Elements lelem=doc.select("div.box-contents > a");
			
			for(int i=0;i<7;i++){
				Element title=telem.get(i);
				Element poster=pelem.get(i);
				Element regdate=delem.get(i);
				Element reserve=relem.get(i);
				Element like=felem.get(i);
				Element link=lelem.get(i);
				
				System.out.println(title.text()+" "+poster.attr("src")+" "+ regdate.text()+" "+reserve.text()+" "+like.text()+" "+link.attr("href"));
				
				MovieVO vo = new MovieVO();
				vo.setMno(i+1);
				vo.setTitle(title.text());
				vo.setPoster(poster.attr("src"));
				vo.setRegdate(regdate.text().substring(0, regdate.text().indexOf("개")).trim());
				vo.setReserve(reserve.text().replace("%","").trim());
				vo.setLink(link.attr("href"));
				vo.setLike(like.text());
				list.add(vo);
				System.out.println(vo.getReserve());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
