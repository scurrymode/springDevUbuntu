package com.sist.twitter;

import com.sist.html.NaverRealTimeRanking;

import twitter4j.FilterQuery;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import java.util.*;

public class TwitterMain {
/*
    1 전소민
    데이터랩 그래프 보기
    2 추자현
    데이터랩 그래프 보기
    3 류석춘
    데이터랩 그래프 보기
    4 우효광
    데이터랩 그래프 보기
    5 원펀치
    데이터랩 그래프 보기
    6 강연재
    데이터랩 그래프 보기
    7 학교2017
    데이터랩 그래프 보기
    8 수소차
    데이터랩 그래프 보기
    9 넉살
    데이터랩 그래프 보기
    10 스타크래프트 리마스터
    데이터랩 그래프 보기
 * */
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			/*String[] data = {"전소민","추자현","류석춘","우효광","원펀치","강연재","학교2017","수소차","넉살","스타크래프트 리마스터"};*/
			List<String> nList=NaverRealTimeRanking.naverRank();
			String[] data = new String[nList.size()];
			int i=0;
			for(String s : nList){
				data[i]=s;
				i++;
			}
		
			TwitterStream ts = new TwitterStreamFactory().getInstance();
			TwitterListener list = new TwitterListener();
			ts.addListener(list);//내가 정해놓은 방식으로 저장되도록 설정한거다!
			
			FilterQuery fq = new FilterQuery();
			fq.track(data);//검색될 필터
			
			ts.filter(fq);//검색 실행
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
