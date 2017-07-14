package com.sist.spark;
import java.util.*;
import java.io.*;
public class CreateCSV {

	public static void main(String[] args) {
		TwitterDAO dao = new TwitterDAO("naver");
		List<TwitterVO> list = dao.naverRankAllData();
		String csv="";
		for(TwitterVO vo : list){
			csv+=vo.getRankdata()+","+vo.getCount()+"\n";
		}
		csv=csv.substring(0, csv.lastIndexOf("\n"));
		try{
		FileWriter fw = new FileWriter("./naver/naver.csv");
		fw.write(csv);
		fw.close();
		System.out.println("완료");
		}catch(Exception e ){
			e.printStackTrace();
		}
	}
}
