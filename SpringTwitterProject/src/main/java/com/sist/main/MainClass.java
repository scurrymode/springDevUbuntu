package com.sist.main;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.hadoop.mapreduce.JobRunner;
import org.springframework.stereotype.Component;

@Component
public class MainClass {
	@Autowired
	private Configuration conf;
	@Autowired
	private JobRunner jr;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ApplicationContext app = new ClassPathXmlApplicationContext("app.xml");
		MainClass mc = (MainClass)app.getBean("mainClass");
		/*mc.hadoopFileDelete();
		mc.hadoopFileInput();*/
		mc.hadoopMapReduce(); //aop로 자동처리해놨기 때문에 위에꺼들은 안해도 자동으로 해줌
	}
	//hadoop: 수집데이터 보내기
	public void hadoopFileDelete(){
		try {
			FileSystem fs = FileSystem.get(conf);
			if(fs.exists(new Path("/tweet_output"))){
				fs.delete(new Path("/tweet_output"), true); //true = rmr
			}
			if(fs.exists(new Path("/tweet_input"))){
				fs.delete(new Path("/tweet_input"), true); //rmr
			}
			fs.close();
			System.out.println("하둡 폴더 삭제 완료");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void hadoopFileInput(){
		try {
			FileSystem fs = FileSystem.get(conf);
			fs.copyFromLocalFile(new Path("./app.log"), new Path("/tweet_input/app.log"));
			fs.close();
			System.out.println("수집 데이터 하둡 전송");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//hadoop: 분석(mapred)
	public void hadoopMapReduce(){
		try {
			jr.call();
			System.out.println("하둡 데이터분석 완료");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//hive: 분석결과 보내기
	public void hadoopResultSend(){
		try {
			FileSystem fs = FileSystem.get(conf);
			fs.copyToLocalFile(new Path("/tweet_ouput/part-r-00000"), new Path("/home/sist/r-data/twitter_result"));
			fs.close();
			System.out.println("분석 결과 가지고 옴");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//hive: JOIN
	
	//R: 통계, 그래프 그리기
	
}
