package com.sist.mapred;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class WordReducer extends Reducer<Text, IntWritable, Text, IntWritable>{//텍스트, 갯수를 받아서 텍스트, 합계를 보낸다.
	private IntWritable res = new IntWritable();

	@Override
	protected void reduce(Text key, Iterable<IntWritable> values,
			Reducer<Text, IntWritable, Text, IntWritable>.Context context) throws IOException, InterruptedException {
		int sum=0;
		for(IntWritable i:values){
			sum+=i.get(); //IntWritable 을 int로 바꾸는게 get
		}
		res.set(sum);
		context.write(key, res);
	}
}
