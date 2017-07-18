package com.sist.news;

import java.util.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.stereotype.Component;

import java.net.*;
import java.io.*;
@Component
public class NewsManager {
	public List<Item> getNewsAllData(String main){
		List<Item> list = new ArrayList<Item>();
		try {
			try {
				URL url = new URL("http://newssearch.naver.com/search.naver?where=rss&query="+URLEncoder.encode(main,"UTF-8"));
				try {
					JAXBContext jc = JAXBContext.newInstance(Rss.class);
					Unmarshaller un = jc.createUnmarshaller();
					Rss rss = (Rss)un.unmarshal(url);
					list=rss.getChannel().getItem();
				} catch (JAXBException e) {
					e.printStackTrace();
				}
				
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return list;
	}
}
