package com.sist.mapred;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MovieMapper extends Mapper<LongWritable, Text, Text, IntWritable>{ //앞두개는 입력값 뒤두개는 출력값
	//단어를 나눠주는애
	//1. read a book 
	//2. write a book
	//Mapper는 read:1, a:1, book:1, write:1, a:1, book:1 가지고 
	//셔플(쇼트)해서 a[1,1] book[1,1] read[1] write[1] 이렇게 준다.
	//Reducer는 a:2, book:2, read:1, write:1 해준다.
	private final IntWritable one = new IntWritable(1);
	private Text result = new Text();
	String[] feel = {"사랑","로맨스","매력","즐거움","스릴", "소름","긴장","공포","유머","웃음","개그",
			"행복","전율","경이","우울","절망","신비", "여운","희망","긴박","감동","감성","휴머니즘",
			"자극","재미","액션","반전","비극","미스테리", "판타지","꿈","설레임","흥미","풍경","일상",
			"순수","힐링","눈물","그리움","호러","충격","잔혹", "드라마","멜로","애정",
			"모험","느와르","다큐멘터리", "코미디","범죄","SF","애니메이션"	
	};
	@Override
	protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, IntWritable>.Context context)
			throws IOException, InterruptedException {
		Pattern[] p = new Pattern[feel.length];
		for(int i=0; i<p.length;i++){
			p[i]=Pattern.compile(feel[i]);
		}
		Matcher[] m = new Matcher[feel.length];
		for(int i=0;i<m.length;i++){
			m[i]=p[i].matcher(value.toString());
			while(m[i].find()){
				result.set(m[i].group());//여기서 말하는 그룹은 ()으로 분리해놓은 것들을 의미 ((그룹1)(그룹2)(그룹3)(그룹4)) 전체가 그룹
				//((d{1-3})\\.(d{1-3})\\.(d{1-3})\\.(d{1-3})) 이게 아이피 패턴임
				context.write(result, one);
			}
		}
		
	}



	
	
}
