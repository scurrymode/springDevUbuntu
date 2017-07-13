package com.sist.twitter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.rosuda.REngine.Rserve.RConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.hadoop.mapreduce.JobRunner;
import org.springframework.stereotype.Component;

import com.sist.hive.TwitterDAO;
import com.sist.hive.TwitterVO;
import com.sist.spark.TwitterSpark;

@Component
public class MainClass {
	@Autowired
	private TwitterSpark ts;
	@Autowired
	private Configuration conf;
	@Autowired
	private JobRunner jr;
	@Autowired
	private TwitterDAO dao;
	
	public static void main(String[] args) {
		String[] xml = {"app.xml", "app-hadoop.xml"};
		ApplicationContext app = new ClassPathXmlApplicationContext(xml);
		MainClass m = (MainClass)app.getBean("mainClass");
		/*m.hadoopFileDelete();
		m.hadoopCopyFromLocal();
		try {
			m.jr.call();
		} catch (Exception e) {
			e.printStackTrace();
		}
		m.hadoopCopyToLocal();
		m.createNameCSV();*/ //네이버 csv만드는 과정
//		m.dao.twitterCreateTable("naver");
//		m.dao.twitterCreateTable("daum"); //테이블 만들기
//		System.out.println("테이블 완료");
//		m.dao.twitterDataInsert("naver");
//		m.dao.twitterDataInsert("daum");
//		System.out.println("insert 완료");//insert 하기
		List<TwitterVO> list = m.dao.twiiterRankData();
		try {
			String data="";
			for(TwitterVO vo : list){
				System.out.println(vo.getRankdata()+" "+vo.getCount());
				data+=vo.getRankdata()+" "+vo.getCount()+"\n";
			}
			data=data.substring(0, data.lastIndexOf("\n"));
			FileWriter fw = new FileWriter("./input/total.txt");
			fw.write(data);
			fw.close();
			m.rgraph();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public void rgraph(){
		try {
			RConnection rc = new RConnection();
			rc.voidEval("data<-read.table(\"/home/sist/springDev/SpringTwitterMRSparkProject/input/total.txt\")");
			rc.voidEval("png(\"/home/sist/r-data/total.png\")");
			rc.voidEval("barplot(data$V2, names.arg=data$V1,col=rainbow(10))");
			rc.voidEval("dev.off()");
			rc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
				sss+=st.nextToken().trim()+","+st.nextToken().trim()+"\n";
			}
			sss=sss.replace("\"", ""); //사실 reducer에서 아예 "를 안줘도 됬다...
			System.out.println(sss);
			FileWriter fw = new FileWriter("./input/naver.csv");
			fw.write(sss);
			fw.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	

}
