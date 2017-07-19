package com.sist.mapred;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import java.io.*;

public class MusicMapper extends Mapper<LongWritable, Text, Text, IntWritable>{
	private final IntWritable one=new IntWritable(1);
	private Text res = new Text();
	@Override
	protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, IntWritable>.Context context)
			throws IOException, InterruptedException {
		FileReader fr = new FileReader("/home/sist/music_data/emotion.txt");
		int i=0;
		String data="";
		String[] mydata={
			"사랑/기쁨",
			"이별/슬픔",
			"스트레스/짜증",
			"우울할때",
			"멘붕/불안",
			"외로울때"
		};
		while((i=fr.read())!=-1){
			data+=String.valueOf((char)i);
		}
		String[] feel = data.split("\n");
		Pattern[] p=new Pattern[feel.length];
		for(int j=0;j<p.length;j++){
			p[j]=Pattern.compile(feel[j]);
		}
		Matcher[] m= new Matcher[feel.length];
		for(int j=0;j<m.length;j++){
			m[j]=p[j].matcher(value.toString());
			while(m[j].find()){
				if(j>=0 &&j<=10)res.set(mydata[0]);
				else if(j>=11 && j<=18)res.set(mydata[1]);
				else if(j>=19 && j<=31)res.set(mydata[2]);
				else if(j>=32 && j<=34)res.set(mydata[3]);
				else if(j>=35 && j<=40)res.set(mydata[4]);
//				else if(j>=11 && j<=12)res.set(mydata[5]);
				//res.set(m[j].group());
				context.write(res, one);
			}
		}
	}
	
	

}
