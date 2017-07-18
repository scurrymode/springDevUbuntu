package com.sist.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.*;
@Repository
public class MusicDAO {
	@Autowired
	private MongoTemplate mt;
	public List<MusicVO> getMyRankData(){
		List<MusicVO> list = new ArrayList<MusicVO>();
		Query query =new Query();
		query.with(new Sort(Sort.Direction.DESC, "rating"));
		list=(List<MusicVO>)mt.find(query, MusicVO.class,"myrank");
		int i=1;
		for(MusicVO vo:list){
			vo.setRank(i);
			i++;
		}
		return list;
	}
	public List<Integer> getMusicRating(String table){
		List<Integer> list = new ArrayList<Integer>();
		String[] title = new String[5];
		try {
			List<MusicVO> mlist = getMyRankData();
			int i=0;
			for(MusicVO vo:mlist){
				title[i]=vo.getTitle();
				i++;
				if(i>=5)break;
			}
			for(int j=0;j<title.length;j++){
				String temp = title[j].substring(0,3);
				BasicQuery query = new BasicQuery("{title:{$regex:'^"+temp.trim()+"'}}");
				MusicVO vo=(MusicVO)mt.findOne(query, MusicVO.class, table);
				list.add(50-vo.getRank());
			}
			} catch (Exception e) {
				e.printStackTrace();
		}
		return list;
	}
	
}
