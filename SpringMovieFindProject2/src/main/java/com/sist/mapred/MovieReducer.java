package com.sist.mapred;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class MovieReducer extends Reducer<Text, IntWritable, Text, IntWritable> { //앞두개는 입력값 뒤두개는 출력값
	//단어를 모아주는애

}
