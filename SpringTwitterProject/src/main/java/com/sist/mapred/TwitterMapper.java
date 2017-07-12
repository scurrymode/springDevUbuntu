package com.sist.mapred;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import com.sist.html.NaverRealTimeRanking;

public class TwitterMapper extends Mapper<LongWritable, Text, Text, IntWritable>{
	private final IntWritable one= new IntWritable(1);
	private Text res=new Text();
	/*private String[] data = {"전소민","추자현","류석춘","우효광","원펀치","강연재","학교2017","수소차","넉살","스타크래프트 리마스터"};*/
	@Override
	protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, IntWritable>.Context context)
			throws IOException, InterruptedException {
		List<String> list = NaverRealTimeRanking.naverRank();
		Pattern[] p=new Pattern[list.size()];
		for(int i=0;i<p.length; i++){
			p[i]=Pattern.compile(list.get(i));
		}
		Matcher[] m=new Matcher[list.size()];
		for(int i=0; i<m.length;i++){
			m[i]=p[i].matcher(value.toString());
			while(m[i].find()){
				res.set(m[i].group());
				context.write(res, one);
			}
		}
	}
	
}
