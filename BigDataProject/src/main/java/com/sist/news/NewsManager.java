package com.sist.news;
import java.util.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.*;
/*
 *   1 (16) => 1시간
 *   2 (23) => 2시간
 *   3 (30) => 4시간 
 *   
 *   vo ,dto (X)
 *   @Component
 *   ~manager
 *   @Repository
 *   ~DAO : DAO
 *   @Service
 *   ~service : DAO+DAO
 *   @controller
 *   ~controller : model
 */

@Component
//@Scope("prototype")
public class NewsManager {
   public List<Item> newsAllData(String data)
   {
	   List<Item> list=new ArrayList<Item>();
	   try
	   {
		   URL url=new URL("http://newssearch.naver.com/search.naver?where=rss&query="
				   +URLEncoder.encode(data, "UTF-8"));
		   JAXBContext jc=JAXBContext.newInstance(Rss.class);
		   Unmarshaller un = jc.createUnmarshaller();
		   Rss rss = (Rss)un.unmarshal(url);
		   list = rss.getChannel().getItem();
	   }catch(Exception ex)
	   {
		   System.out.println(ex.getMessage());
	   }
	   return list;
   }
}







