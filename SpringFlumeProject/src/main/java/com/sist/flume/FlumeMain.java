package com.sist.flume;

import java.util.*;
import java.io.*;

public class FlumeMain {
	public static void main(String[] args) {
		try {
			List<String> list = RankData.daumRank();
			FileWriter fw = new FileWriter("/usr/local/apache-flume-1.7.0/conf/data_input");
			String data="";
			for(String s : list){
				data+=s+",";
			}
			data = data.substring(0, data.lastIndexOf(","));
			System.out.println(data);
			fw.write(data);
			fw.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
