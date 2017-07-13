package com.sist.spark;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class MainClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Configuration conf = new Configuration();
			conf.set("fs.default.name","hdfs://localhost:9000");
			FileSystem fs = FileSystem.get(conf);
			FileStatus[] status=fs.listStatus(new Path("/user/hadoop/twitter_data/")); //ls
			String data="";
			for(FileStatus s: status){
				if(s.getPath().getName().endsWith("tmp")){ //tmp는 빼고 가져와야 하니깐~!
					continue;
				}
				System.out.println(s.getPath().getName());
				FSDataInputStream is = fs.open(new Path("/user/hadoop/twitter_data/"+s.getPath().getName()));
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				while(true){
					String line = br.readLine();
					if(line==null) break;
					data+=line+"\n";
				}
				br.close();
			}
			data=data.replaceAll("[^가-힣 ]", "");
			System.out.println(data);
			FileWriter fw = new FileWriter("./twitter.txt");
			fw.write(data);
			fw.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
