package com.sist.mapred;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import java.util.*;

public class WordMapper extends Mapper<LongWritable, Text, Text, IntWritable>{//줄번호, 텍스트 받아서 텍스트와 갯수로 보낸다.
	private final IntWritable one=
			new IntWritable(1);
	private Text res = new Text();
	@Override
	protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, IntWritable>.Context context)
			throws IOException, InterruptedException {
		StringTokenizer st=new StringTokenizer(value.toString());
		while(st.hasMoreTokens()){
			res.set(st.nextToken()); //String=> Text 할때 set메서드 사용
			context.write(res, one);
		} //이제 한줄내의 단어와 갯수를 정리했음
	}
}
