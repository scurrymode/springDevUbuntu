package com.sist.flume;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.TextOutputFormat;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.twitter.TwitterUtils;

import com.sun.jersey.core.impl.provider.entity.XMLJAXBElementProvider.Text;

import scala.Tuple2;
import twitter4j.Status;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class SparkMain {//네이버 검색어 순위를 트위터 검색결과 스트림으로 가져와서 스파크로 분석하고 하둡에 넣기

	public static void main(String[] args) {
		try{
			//하둡연결
			Configuration hconf = new Configuration();
			hconf.set("fs.default.name", "hdfs://localhost:9000");
			JobConf jc = new JobConf(hconf);
			
			SparkConf conf = new SparkConf().setAppName("Twitter").setMaster("local[2]");
			String[] filter={"JkMI3GhgS80C4VusKAOHyr9P8",
					"NZLqTMbjYo9dZBcpAm6FkZ13JFy7E28lTuRgLZiGFHn8ojtFZo",
					"867996774627135488-97Lz8D5Q2JJkQutESn2CY9G0eTRUkxm",
					"o45mfTIv6omLn6QEkoShOtvI4IrQ4ZGrTLeYcOYwINr11"};
			String[] prop = {"twitter4j.oauth.consumerKey",
					"twitter4j.oauth.consumerSecret",
					"twitter4j.oauth.accessToken",
					"twitter4j.oauth.accessTokenSecret"};
			for(int i = 0; i<prop.length;i++){
				System.setProperty(prop[i],filter[i]);
			}
			List<String> list = RankData.naverRank();
			String[] data = new String[list.size()];
			int i=0;
			for(String s: list){
				data[i]=s;
				i++;
			}
			JavaStreamingContext jsc = new JavaStreamingContext(conf,new Duration(10000));
			JavaReceiverInputDStream<Status> status = TwitterUtils.createStream(jsc,data);
			/*
			 * 데이터 수집 방법
			 * Spark: File읽기 JavaRDD<String>
			 * 			Stream JavaDStream<String>
			 * */
			
			JavaDStream<String> datas = status.map(new Function<Status, String>() {
				@Override
				public String call(Status status) throws Exception {
					return status.getText();
				}
			});
			//분석
			final Pattern[] p = new Pattern[data.length];
			for(i=0; i<p.length;i++){
				p[i]=Pattern.compile(data[i]);
			}
			final Matcher[] m = new Matcher[data.length];
			//단어 분리
			JavaDStream<String> words = datas.flatMap(new FlatMapFunction<String, String>() {
				List<String> list = new ArrayList<String>();
				@Override
				public Iterable<String> call(String s) throws Exception {
					for(int i=0;i<m.length;i++){
						m[i]=p[i].matcher(s);
						while(m[i].find()){
							list.add(m[i].group().replace(" ","-"));
						}
					}
					return list;
				}
			});
			//1씩 주기
			JavaPairDStream<String, Integer> counts = words.mapToPair(new PairFunction<String, String, Integer>() {
				@Override
				public Tuple2<String, Integer> call(String s) throws Exception {
					return new Tuple2<String, Integer>(s, 1);
				}
			});
			//합치기
			JavaPairDStream<String, Integer> reduce = counts.reduceByKey(new Function2<Integer, Integer, Integer>() {
				@Override
				public Integer call(Integer sum, Integer i) throws Exception {
					return sum+i;
				}
			});
			reduce.print();
			reduce.saveAsHadoopFiles("hdfs://localhost:9000/user/spark/data/twitter", "", Text.class, IntWritable.class, TextOutputFormat.class, jc);
			//이렇게 하면 읽은 시간에 맞춰서 폴더를 만들어 주고 그 안에 분석 결과를 담은 파일들을 만들어 준다. part-00000 
			jsc.start(); //트위터 구동
			jsc.awaitTermination();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
