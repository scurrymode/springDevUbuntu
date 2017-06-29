package com.sist.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.*;

public class ReplyDAO {
	@Autowired
	private MongoTemplate mt;
	public void replyInsert(ReplyVO vo){
		mt.insert(vo, "movie");
	}
	public List<ReplyVO> replyAllData(){
		Query query = new Query();
		return mt.find(query, ReplyVO.class, "movie");
	}
}
