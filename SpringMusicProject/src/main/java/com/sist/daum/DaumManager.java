package com.sist.daum;

import java.util.*;
import java.net.*;
import java.io.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.springframework.stereotype.Component;
@Component
public class DaumManager {
	public void daumReview(String title){
		try {
			String data= "";
			for(int i=1;i<=3;i++){
				data+=daumReviewData(i, title);
			}
			FileWriter fw = new FileWriter("/home/sist/music_data/daum.txt");
			fw.write(data);
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public String daumReviewData(int i, String title){
		String review="";
		try {
			String key="b913e860e422b892a0760bf1f2b132c7";
			String strUrl="https://apis.daum.net/search/blog?apiKey="+key+"&result=20&output=xml&pageno="+i+"&q="+URLEncoder.encode(title,"UTF-8");
			URL url = new URL(strUrl);
			JAXBContext jc = JAXBContext.newInstance(Channel.class);
			Unmarshaller um = jc.createUnmarshaller();
			Channel channel = (Channel)um.unmarshal(url);
			List<Item> list = channel.getItem();
			for(Item item:list){
				review+=item.getDescription()+"\n";
			}
			review=review.substring(0, review.lastIndexOf("\n"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return review;
	}
}
