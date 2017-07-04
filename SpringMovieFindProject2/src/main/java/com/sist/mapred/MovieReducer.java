package com.sist.mapred;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class MovieReducer extends Reducer<Text, IntWritable, Text, IntWritable> {//앞두개는 입력값 뒤두개는 출력값
	//단어를 모아주는애
	private IntWritable res = new IntWritable();
	
	@Override
	protected void reduce(Text key, Iterable<IntWritable> values,
			Reducer<Text, IntWritable, Text, IntWritable>.Context context) throws IOException, InterruptedException {
		int sum=0;
		for(IntWritable i:values){
			sum+=i.get();//IntWritable을 int로 변환하는 메서드
		}
		res.set(sum); //int를 IntWritable로 바꾸는 메서드
		context.write(key, res);
	} 

}
