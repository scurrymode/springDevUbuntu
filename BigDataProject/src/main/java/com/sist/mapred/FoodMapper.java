package com.sist.mapred;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import java.io.*;

public class FoodMapper extends Mapper<LongWritable, Text, Text, IntWritable>{
	//줄번호, 내용, 내용, 갯수(1씩)
	
	private final IntWritable one = new IntWritable(1); 
	private Text res = new Text();
	@Override
	protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, IntWritable>.Context context)
			throws IOException, InterruptedException {
		FileReader fr = new FileReader("/home/sist/food_data/food_directory");
		int i=0;
		String data="";
		while((i=fr.read())!=-1){
			data+=String.valueOf((char)i); //파일에서 읽어온 아스키코드를 글자로 변환해서 스트링에 넣기
		}
		fr.close();
		String[] feel = data.split("\n"); //글자 패턴들 개별 저장 완료
		Pattern[] p = new Pattern[feel.length];
		String taste="";
		for(i=0;i<p.length;i++){
			p[i]=Pattern.compile(feel[i]); //글자 패턴의 패턴화 완료			
		}
		Matcher[] m = new Matcher[feel.length];
		for(i=0;i<m.length;i++){
			m[i]=p[i].matcher(value.toString()); //보내온 value(한줄)을 매칭한다.
			while(m[i].find()){
				if(i>=0 && i<=3){
					taste="매운맛";
				}else if(i>=4 && i<=6){
					taste="싱거운맛";
				}else if(i>=7 && i<=8){
					taste="짠맛";
				}else if(i>=9 && i<=12){
					taste="단맛";
				}else if(i>=13 && i<=15){
					taste="쓴맛";
				}else if(i>=16 && i<=17){
					taste="신맛";
				}else{
					taste="기타맛";
				}
				res.set(taste);//스트링을 텍스트로 변경
				context.write(res, one);
			}
		}
	}
}
