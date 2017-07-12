package com.sist.spark;

import java.util.Arrays;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;

import scala.Tuple2;

public class SparkMain {

	public static void main(String[] args) {
		Configuration hadoopConf= new Configuration();
		hadoopConf.set("fs.default.name", "hdfs://localhost:9000");
		JobConf jconf=new JobConf(hadoopConf);
		try {
			// 0. spark 연결
			SparkConf conf =new SparkConf().setAppName("wordcount").setMaster("local");
			JavaSparkContext sc = new JavaSparkContext(conf);
			
			// 1. 파일읽기, 실시간
			JavaRDD<String> input = sc.textFile("./spark.txt");//JavaRDD가 List다.
			
			// 2. 한줄씩 읽어서 단어를 분리
			JavaRDD<String> words = input.flatMap(new FlatMapFunction<String, String>() { //흐름은 뒤가 받는거고 앞이 결과물
				@Override
				public Iterable<String> call(String s) throws Exception {
					System.out.println(s);
					return Arrays.asList(s.split(" ")); //여기서는 한줄 읽어와서 (뒤 String) 띄어쓰기로 분리해서 앞String에 담아준다.
				}
			});
			// 3. 단어별 1씩 부여
			JavaPairRDD<String, Integer> counts = words.mapToPair(new PairFunction<String, String, Integer>() {
				@Override
				public Tuple2<String, Integer> call(String s) throws Exception { //
					// TODO Auto-generated method stub
					return new Tuple2<String, Integer>(s, 1); // 단어별로 1씩 부여한다.
				}
			});
			// 4. 통계
			JavaPairRDD<String, Integer> res= counts.reduceByKey(new Function2<Integer, Integer, Integer>() { // 1들을 더해준다. sum=sum+1 인 꼴이다.
				
				@Override
				public Integer call(Integer x, Integer y) throws Exception { //여기가 sum+1
					// TODO Auto-generated method stub
					return x+y;
				}
			});
			// 5. 결과 저장
			//res.saveAsTextFile("./output");
			res.saveAsNewAPIHadoopFile("/spark", Text.class, IntWritable.class, TextOutputFormat.class, jconf);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
