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
				res.set(m[j].group());
				context.write(res, one);
			}
		}
	}
	
	

}
