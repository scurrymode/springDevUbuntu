package com.sist.flume;

import java.net.InetSocketAddress;
import java.util.*;

import org.apache.commons.beanutils.BaseDynaBeanMapDecorator;

import com.mongodb.*;
public class TwitterDAO {
	private MongoClient mc;
	private DB db;
	private DBCollection dbc;
	public TwitterDAO(){
		try {
			//몽고디비 연결
			mc=new MongoClient(new ServerAddress(new InetSocketAddress("211.238.142.111", 27017)));
			db=mc.getDB("mydb");
			dbc=db.getCollection("naver_rank");
					
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void naverRankInsert(TwitterVO vo){
		try {
			BasicDBObject where = new BasicDBObject(); //{} 들어가는 식
			where.put("rankdata", vo.getRankdata()); //SELECT COUNT(*) FROM naver_rank WHERE rankdata='???';
			DBCursor cursor = dbc.find(where); //검색한 결과를 받기
			int count = cursor.count();
			cursor.close();
			switch(count){
			case 0: //insert
				BasicDBObject obj = new BasicDBObject();
				obj.put("rankdata", vo.getRankdata());
				obj.put("count", vo.getCount());
				dbc.insert(obj); //insert
				break;
			case 1: //update
				BasicDBObject obj2 = (BasicDBObject)dbc.findOne(where);
				int cnt = obj2.getInt("count"); // 기존에 값이 있으니깐 기존 값을 가져와서 더해서 넣어줘야 하니깐 일단 가져와
				cnt=cnt+vo.getCount();
				BasicDBObject up = new BasicDBObject(); //더한 걸 만들어서
				up.put("count", cnt);
				dbc.update(where, new BasicDBObject("$set",up)); //업데이트 문 날리기~
				break;
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public List<TwitterVO> naverRankAllData(){
		List<TwitterVO> list = new ArrayList<TwitterVO>();
		try {
			DBCursor cursor = dbc.find().sort(new BasicDBObject("count", -1));// 1 asc, -1 desc
			while(cursor.hasNext()){
				BasicDBObject obj = (BasicDBObject)cursor.next();
				TwitterVO vo = new TwitterVO();
				vo.setRankdata(obj.getString("rankdata"));
				vo.setCount(obj.getInt("count"));
				list.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
}
