package com.sist.mapred;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class RecommandReducer extends Reducer<Text, IntWritable, Text, IntWritable> {//앞두개는 입력값 뒤두개는 출력값
	//단어를 모아주는애
	private IntWritable res = new IntWritable();
	
	@Override
	protected void reduce(Text key, Iterable<IntWritable> values,
			Reducer<Text, IntWritable, Text, IntWritable>.Context context) throws IOException, InterruptedException {
		int sum=0;
		String data=key.toString();
		data=data.replace(" ",",");
		//R에서 테이블을 사용하려고 하는데, 공백이 있으면 처리가 안되고, 나중에 영화이름 찾기도 힘드니깐, 일단 공백을 ,로 대체
		//하고 나중에 ,를 공백으로 바꿔서 검색하자~!
		
		for(IntWritable i:values){
			sum+=i.get();//IntWritable을 int로 변환하는 메서드
		}
		res.set(sum); //int를 IntWritable로 바꾸는 메서드
		key.set(data); //String을 Text로 바꾸는 메서드
		context.write(key, res);
	} 
}

