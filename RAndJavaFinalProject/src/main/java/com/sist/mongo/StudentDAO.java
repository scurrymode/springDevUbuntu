package com.sist.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import java.util.*;
@Repository
public class StudentDAO {
   @Autowired
   private MongoTemplate mt;
   public List<StudentVO> stdAllData()
   {
	   Query query=new Query();// {name:''}
	   List<StudentVO> list=mt.find(query, 
			   StudentVO.class, "student");
	   return list;
   }
   public List<StudentVO> stdFindData(String fs,String ss)
   {
	   // {name:
	   // {subject:
	   // find({name:{$regex:'^이$'}}) $ ^ %이%
	   BasicQuery query=new BasicQuery("{"+fs+":{$regex:'.*"+ss+"'}}");
	   List<StudentVO> list=mt.find(query,
			   StudentVO.class,"student");
	   return list;
   }
}