package com.sist.mongo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import com.mongodb.*;
import com.sist.music.MusicVO;

import au.com.bytecode.opencsv.CSVReader;


public class MusicDAO {
	private MongoClient mc;
	private DB db;
	private DBCollection dbc;
	private String type;
	public MusicDAO(String type){
		try {
			this.type=type;
			mc = new MongoClient(new ServerAddress(new InetSocketAddress("211.238.142.111",27017)));
			db=mc.getDB("mydb");
			dbc=db.getCollection(type);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void musicInsert(MusicVO vo){
		int no = 0;
		DBCursor cursor = dbc.find();
		while(cursor.hasNext()){
			BasicDBObject obj = (BasicDBObject)cursor.next();
			int i = obj.getInt("no");
			if(no<i)no=i;
		}
		cursor.close();
		BasicDBObject obj = new BasicDBObject();
		obj.put("no", no+1);
		obj.put("rank", vo.getRank());
		obj.put("title", vo.getTitle());
		obj.put("singer", vo.getSinger());
		dbc.insert(obj);
	}
	public List<MusicVO> getMusicData(){
		List<MusicVO> list = new ArrayList<MusicVO>();
		
		DBCursor cursor = dbc.find();
		String csv=""; //csv파일 만들기, join을 위해서
		while(cursor.hasNext()){
			BasicDBObject obj = (BasicDBObject)cursor.next();
			MusicVO vo = new MusicVO();
			vo.setRank(obj.getInt("rank"));
			vo.setTitle(obj.getString("title"));
			vo.setSinger(obj.getString("singer"));
			list.add(vo);
			csv+=vo.getRank()+","+vo.getTitle()+","+vo.getSinger()+"\n";
		}
		cursor.close();
		csv=csv.substring(0,csv.lastIndexOf("\n"));
		try {
			FileWriter fw = new FileWriter("./music-data/"+type+".csv");
			fw.write(csv);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		return list;
	}
	public static void myCreateCSV(){
		try {
			FileReader fr = new FileReader("./music-data/genie-melon/part-00000");
			String data = "";
			int i=0;
			while((i=fr.read())!=-1){
				data+=String.valueOf((char)i);
			}
			fr.close();
			data=data.replace("(","");
			data=data.replace(")","");
			FileWriter fw = new FileWriter("./music-data/genie-melon/myrank.csv");
			fw.write(data);;
			fw.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void myRankInsert(){
		FileReader fr;
		try {
			dbc.drop();
			fr = new FileReader("./music-data/genie-melon/myrank.csv");
			String data = "";
			int i=0;
			while((i=fr.read())!=-1){
				data+=String.valueOf((char)i);
			}
			fr.close();
			String[] temp = data.split("\n");
			
			for(String s:temp){
				CSVReader csv = new CSVReader(new StringReader(s));
				String[] ss = csv.readNext();
				BasicDBObject obj = new BasicDBObject();
				obj.put("title", ss[0]);
				obj.put("rating", 100-(Integer.parseInt(ss[1].trim())+Integer.parseInt(ss[2].trim())));
				dbc.insert(obj);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
