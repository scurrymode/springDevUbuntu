package com.sist.recommand;

import java.net.InetSocketAddress;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import java.util.*;
public class RecommandDAO {
	private MongoClient mc; // Connection
	   private DB db; // ORCL(database)
	   private DBCollection dbc; // table
	   public RecommandDAO()
	   {
		   try
		   {
			   // mc=new MongoClient("localhost");
			   mc=new MongoClient(new ServerAddress(new InetSocketAddress("211.238.142.111",27017)));
			   db=mc.getDB("mydb");
			   dbc=db.getCollection("movie");//임의로 지정 
			   
		   }catch(Exception ex)
		   {
			   System.out.println(ex.getMessage());
		   }
	   }
	   public List<String> movieTitleAllData()
	   {
		   List<String> list=new ArrayList<String>();
		   try
		   {
			   DBCursor cursor=dbc.find();
			   while(cursor.hasNext())
			   {
				   BasicDBObject obj=
						   (BasicDBObject)cursor.next();
				   String data=obj.getString("title");
				   if(data.length()>1)
				   {
				     list.add(data);
				   }
			   }
		   }catch(Exception ex)
		   {
			   System.out.println(ex.getMessage());
		   }
		   return list;
	   }
}
