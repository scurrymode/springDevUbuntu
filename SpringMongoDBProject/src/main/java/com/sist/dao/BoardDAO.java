package com.sist.dao;

import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.*;

import org.springframework.stereotype.Repository;

import com.mongodb.*;

@Repository
public class BoardDAO {
	private MongoClient mc; //connection
	private DB db; //ORCL(database)
	private DBCollection dbc; //table
	
	public BoardDAO(){
		try{
			//mc = new MongoClient("localhost"); 로컬에서 할때는 이런식으로
			mc= new MongoClient(new ServerAddress(new InetSocketAddress("211.238.142.111",27017)));
			db=mc.getDB("mydb"); //db선택 없으면 mongo에서 use mydb 하면 데이터베이스 만들어짐
			dbc=db.getCollection("board"); //임의로 지정 테이블 이름이 몽고에 미리 없어도 된다ㅋ	
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	public void boardInsert(BoardVO vo){
		try {
			DBCursor cursor=dbc.find();
			/*
			 * DBCursor = ResultSet
			 * dbc.find() = SELECT * FROM table명
			 * NoSQL       /    SQL
			 * */
			int no=0; //cursor.count()
			while(cursor.hasNext()){ //rs.next()
				// {no:1, name:"", subject:""...} => BasicDBObject로 읽어, 이게 데이터 한줄
				BasicDBObject obj = (BasicDBObject)cursor.next();
				int max=obj.getInt("no");
				if(no<max){
					no=max; //SELECT max(no) FROM table
				}
			}
			cursor.close();
				
			BasicDBObject insertObj = new BasicDBObject();
			insertObj.put("no", no+1);
			insertObj.put("name", vo.getName());
			insertObj.put("subject", vo.getSubject());
			insertObj.put("content", vo.getContent());
			insertObj.put("pwd", vo.getPwd());
			insertObj.put("regdate", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			insertObj.put("hit", 0);
			dbc.insert(insertObj);	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public List<BoardVO> boardAllData(int page){
		List<BoardVO> list = new ArrayList<BoardVO>();
		try {
			int rowSize=10;
			int skip=(page*rowSize)-rowSize;
			/*
			 * 0~9
			 * 10~19
			 * 20~29
			 * ...
			 * */
			BasicDBObject order = new BasicDBObject();
			order.put("no", -1); // order by -1:DESC, 1:ASC
			DBCursor cursor=dbc.find().sort(order).skip(skip).limit(rowSize);//순서대로 세우고, 버릴만큼 버리고, 가져올 갯수
			//오라클에서는 Between 1 and 10 할 수 밖에 없지만 몽고는 skip을 사용한다.
			//SQL대신 NoSQL이라서 위의 함수 묶음이 SQL문을 대체한다.
			while(cursor.hasNext()){
				BasicDBObject obj = (BasicDBObject)cursor.next();
				BoardVO vo = new BoardVO();
				vo.setNo(obj.getInt("no"));
				vo.setName(obj.getString("name"));
				vo.setSubject(obj.getString("subject"));
				vo.setRegdate(obj.getString("regdate"));
				vo.setHit(obj.getInt("hit"));
				list.add(vo);
			}
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
}
