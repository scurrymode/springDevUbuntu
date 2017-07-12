package com.sist.spark;

import java.util.Arrays;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.TextOutputFormat;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

import scala.Tuple2;

/* 파일상태
 * 최지민 하희진 이태수
 * 하희진 최지민
 * 1. 파일읽기
 * 	JavaRDD<String> file=sc.textFile("./spark.txt"); 
 * 	한줄씩 String으로 만들어서 JavaRDD 리스트에 넣음
 * 최지민 하희진 이태수
 * 하희진 최지민
 * 2. 단어별로 자르기(" "으로 구분)
 * 	JavaRDD<String> words=file.flatMap(
 * new FlatMapFunction<String, String>(){
 * 	전체 파일에서 한줄씩 읽기
 * 	@Override
	public Iterable<String> call(String s) throws Exception {
	띄어쓰기로 단어 분리해서 Iterable<String>에 담기
		System.out.println(s);
		return Arrays.asList(s.split(" "));
		}
 * 	});
 * 최지민
 * 하희진
 * 이태수
 * 하희진
 * 최지민
 *  
 * 3. 단어별로 1씩 주기
 * JavaPairRDD<String, Integer> counts = words.mapToPair(new PairFunction<String, String, Integer>() {
	@Override																		 원본		한줄 		1
	public Tuple2<String, Integer> call(String s) throws Exception { //한줄씩 처리
		return new Tuple2<String, Integer>(s, 1); // 단어별로 1씩 부여한다.
	});
 * 최지민 1
 * 하희진 1
 * 이태수 1
 * 하희진 1
 * 최지민 1
 * 자동으로 최지민[1,1] 하희진[1,1], 이태수[1] 로 셔플해준다.
 * 4. 갯수 누적하기
 * JavaPairRDD<String, Integer> result = counts.reduceByKey(new Function2<Integer, Integer, Integer>() { // 1들을 더해준다. sum=sum+1 인 꼴이다.
		@Override
		public Integer call(Integer x, Integer y) throws Exception { //여기가 sum+1
			// TODO Auto-generated method stub
			return x+y;
		}
	});
	최지민 2, 하희진 2, 이태수 1
 * */
public class SparkMain2 {

	public static void main(String[] args) {
		Configuration hadoopConf= new Configuration();
		hadoopConf.set("fs.default.name", "hdfs://localhost:9000");
		JobConf jconf=new JobConf(hadoopConf);
		try {
			SparkConf conf = new SparkConf().setAppName("netcat").setMaster("local[2]");
			//JavaSparkContext sc = new JavaSparkContext(conf);
			JavaStreamingContext sc = new JavaStreamingContext(conf, new Duration(1000)); //이게 소켓이다. 1초에 한번씩 접속
			JavaReceiverInputDStream<String> lines=sc.socketTextStream("localhost", 9999);
			JavaDStream<String> words=lines.flatMap(new FlatMapFunction<String, String>() {
				@Override
				public Iterable<String> call(String s) throws Exception {
					return Arrays.asList(s.split(" "));
				}
			});
			JavaPairDStream<String, Integer> counts = words.mapToPair(new PairFunction<String, String, Integer>() {
				@Override
				public Tuple2<String, Integer> call(String s) throws Exception {
					return new Tuple2<String, Integer>(s, 1);
				}
			});
			JavaPairDStream<String, Integer> res = counts.reduceByKey(new Function2<Integer, Integer, Integer>() {
				@Override
				public Integer call(Integer sum, Integer i) throws Exception {
					return sum+i;
				}
			});
			res.saveAsHadoopFiles("/stream-", "", Text.class, IntWritable.class, TextOutputFormat.class, jconf); //시간 이름으로 파일이 저장된다.
			res.print();
			sc.start();
			sc.awaitTermination();//들어올때까지 기다려라 
			//hadoop 켜놓고 nc -lk 9999로 들어가서 입력되자마자 인식한다. 1초별로 위 이름처럼 저장하고
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
