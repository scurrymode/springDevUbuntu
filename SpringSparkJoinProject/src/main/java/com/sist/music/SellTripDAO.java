package com.sist.music;

import com.mongodb.*;
import java.net.*;

public class SellTripDAO {
	private MongoClient mc;
	private DB db;
	private DBCollection dbc;
	private String type;
	public SellTripDAO(String type){
		this.type=type;
		try {
			mc=new MongoClient(new ServerAddress(new InetSocketAddress("211.238.142.111", 27017)));
			db=mc.getDB("mydb");
			dbc=db.getCollection(type);
			dbc.drop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void sellTripInsert(SellTripVO vo){
		int no=0;
		DBCursor cursor = dbc.find();
		while(cursor.hasNext()){
			BasicDBObject obj = (BasicDBObject)cursor.next();
			int i = obj.getInt("no");
			if(no<i)no=i;
		}
		cursor.close();
		BasicDBObject obj = new BasicDBObject();
		obj.put("no", no+1);
		obj.put("name", vo.getName());
		obj.put("price", vo.getPrice());
		obj.put("date", vo.getDate());
		obj.put("link", vo.getLink());
		dbc.insert(obj);
	}
}
