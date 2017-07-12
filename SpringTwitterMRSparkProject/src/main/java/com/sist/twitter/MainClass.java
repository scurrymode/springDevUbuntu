package com.sist.twitter;

import java.io.File;
import java.io.FileReader;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.hadoop.mapreduce.JobRunner;
import org.springframework.stereotype.Component;

import com.sist.spark.TwitterSpark;

@Component
public class MainClass {
	@Autowired
	private TwitterSpark ts;
	@Autowired
	private Configuration conf;
	@Autowired
	private JobRunner jr;
	
	public static void main(String[] args) {
		String[] xml = {"app.xml", "app-hadoop.xml"};
		ApplicationContext app = new ClassPathXmlApplicationContext(xml);
		MainClass m = (MainClass)app.getBean("mainClass");
		m.hadoopFileDelete();
		m.hadoopCopyFromLocal();
		try {
			m.jr.call();
		} catch (Exception e) {
			e.printStackTrace();
		}
		m.hadoopCopyToLocal();
		m.createNameCSV();
	}
	public void hadoopFileDelete(){
		try {
			FileSystem fs = FileSystem.get(conf);
			if(fs.exists(new Path("/twitter_input"))) fs.delete(new Path("/twitter_input"), true); //input 지우기
			if(fs.exists(new Path("/twitter_output"))) fs.delete(new Path("/twitter_output"),true); //output 지우기
			fs.close();
			
			File dir = new File("./output_daum"); //폴더에 있는 것도 지우기
			if(dir.exists()){
				File[] list = dir.listFiles();
				for(File f: list){
					f.delete();
				}
				dir.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void hadoopCopyFromLocal(){
		try {
			FileSystem fs = FileSystem.get(conf);
			fs.copyFromLocalFile(new Path("./input/naver.txt"), new Path("/twitter_input/naver.txt"));
			fs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void hadoopCopyToLocal(){
		try {
			FileSystem fs = FileSystem.get(conf);
			fs.copyToLocalFile(new Path("/twitter_output/part-r-00000"), new Path("./input/naver"));
			fs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void createNameCSV(){
		try {
			FileReader fr = new FileReader("./input/naver");
			int i=0;
			String data="";
			while((i=fr.read())!=-1){
				data=data+String.valueOf((char)i);
			}
			fr.close();
			String[] str = data.split("\n");
			String sss="";
			for(String s : str){
				StringTokenizer st = new StringTokenizer(s);
				sss+=st.nextToken()+","+st.nextToken().trim()+"\n";
			}
			sss=sss.replace("\"", ""); //사실 reducer에서 아예 "를 안줘도 됬다...
			System.out.println(sss);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	

}
