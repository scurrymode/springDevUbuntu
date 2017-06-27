package com.sist.dao;

import org.springframework.stereotype.Component;

import java.util.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import java.io.*;
import java.net.*;

@Component
public class NewsManager {
	public List<Item> newsAllData(int page, String find){
		List<Item> list = new ArrayList<Item>();
		try{
			URL url = new URL("http://newssearch.naver.com/search.naver?where=rss&query="+URLEncoder.encode(find,"UTF-8"));
			JAXBContext jc = JAXBContext.newInstance(Rss.class); //루트를 가지고 하면 됨
			Unmarshaller un = jc.createUnmarshaller();
			Rss rss=(Rss)un.unmarshal(url);
			
			List<Item> temp = rss.getChannel().getItem();
			int i=0;
			int j=0;
			int startContent=(page*10)-10;
			
			for(Item item:temp){
				if(j<10 && i>=startContent){
					list.add(item);
					j++;
				}
				i++;
			}
			String data = "";
			for(Item item:list){
				data+=item.getDescription()+"\n";
			}
			data=data.substring(0,data.lastIndexOf("\n"));
			data=data.replaceAll("[^가-힣 ]",""); //한글만 출력할때 쓰는 정규식
			FileWriter fw = new FileWriter("/home/sist/r-data/news_data");
			fw.write(data);
			fw.close();
			
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
		
		return list;
	}
}
