package com.sist.mapred;


import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.sist.dao.MovieDAO;
import com.sist.recommand.RecommandDAO;

public class RecommandMapper extends Mapper<LongWritable, Text, Text, IntWritable>{ //앞두개는 입력값 뒤두개는 출력값
	//단어를 나눠주는애
	//1. read a book 
	//2. write a book
	//Mapper는 read:1, a:1, book:1, write:1, a:1, book:1 가지고 
	//셔플(쇼트)해서 a[1,1] book[1,1] read[1] write[1] 이렇게 준다.
	//Reducer는 a:2, book:2, read:1, write:1 해준다.
	
	private final IntWritable one = new IntWritable(1);
	private Text result = new Text();
	private RecommandDAO dao = new RecommandDAO();// 이렇게 한 이유는 2가지 이 Mapper가 하둡 xml로 인해 메모리에 올라가서 spring으로 올리는 @Autowired를 사용할 수 없고,
	//굳이 MovidDAO가 아닌 새로운 RecommandDAO를 만든 이유는 MovieDAO의 경우 몽고디비 연결이 @Autowired로 되어있었기 때문이다.
	
	@Override
	protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, IntWritable>.Context context)
			throws IOException, InterruptedException {
		List<String> list = dao.movieTitleAllData(); //DB안의 영화 제목과 비교하기~!
		Pattern[] p = new Pattern[list.size()];
		for(int i=0; i<p.length;i++){
			String str=list.get(i);
			p[i]=Pattern.compile(str);
		}
		Matcher[] m = new Matcher[list.size()];
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

