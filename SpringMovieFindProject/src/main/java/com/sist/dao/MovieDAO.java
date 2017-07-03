package com.sist.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.sist.manager.MovieVO;

@Repository
public class MovieDAO {
	@Autowired
	private MongoTemplate mt;
	
	public void movieInsert(MovieVO vo){
		mt.insert(vo, "movie");
	}
	public List<String> movieAllGenre(){
		//SELECT DISTINCT genre FROM movie
		return mt.getCollection("movie").distinct("genre");
	}
	public List<MovieVO> movieAllData(int page){
		Query query = new Query();
		int rowSize=5;
		int skip=(page*rowSize)-rowSize;
		query.skip(skip).limit(rowSize);
		List<MovieVO> list = mt.find(query, MovieVO.class, "movie");
		return list;
	}
	public int movieTotalPage(){
		//SELECT CEIL(count()/5) FROM movie
		//혹시 조건이 있을수도 있으니깐 count(id)처럼 query를 넣어달라고함..
		Query query = new Query();
		//BasicQuery query = new BasicQuery("{no:1}"); 조건이 걸릴때는 Query보단 BasicQuery가 편하다
		return (int)(Math.ceil(mt.count(query, "movie")/5.0));
	}
	public MovieVO movieDetailData(int mno){
		BasicQuery query = new BasicQuery("{mno:"+mno+"}");
		return mt.findOne(query, MovieVO.class, "movie");
	}
	public List<MovieVO> movieFind(String data){
		List<MovieVO> list = new ArrayList<MovieVO>();
		BasicQuery query = new BasicQuery("{title:{'$regex':'"+data+".*'}"); 
		return mt.find(query, MovieVO.class, "movie");
	}
}
