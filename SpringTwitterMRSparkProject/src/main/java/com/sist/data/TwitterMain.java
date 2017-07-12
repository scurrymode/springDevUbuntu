package com.sist.data;

import java.util.List;

import twitter4j.FilterQuery;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

public class TwitterMain {
	public static void main(String[] args) {
		try {
			List<String> list = RankData.daumRank();
			String[] data = new String[list.size()];
			int i=0;
			for(String s:list){
				data[i]=s;
				i++;
			}
			TwitterStream ts=new TwitterStreamFactory().getInstance();
			TwitterListener tList = new TwitterListener();
			ts.addListener(tList);
			FilterQuery fq = new FilterQuery();
			fq.track(data);
			ts.filter(fq);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
