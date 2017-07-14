package com.sist.spark;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
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

import scala.Tuple2;
import twitter4j.Status;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DaumNaverRank {
	private static JavaStreamingContext jsc;
	
	public static void main(String[] args){
		try {
			String type="naver";
			Configuration hconf = new Configuration();
			hconf.set("fs.default.name", "hdfs://localhost:9000");
			JobConf jc = new JobConf(hconf);
			SparkConf conf = new SparkConf().setAppName("DaumTwitter").setMaster("local[2]"); //왜 2번 인가? 0은 마스터 1은 세컨드리 2번부터 node다~ 조사는 DataNode들이 하는거니
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
			jsc = new JavaStreamingContext(conf, new Duration(1000));
			
			List<String> list = new ArrayList<String>();
			if(type.equals("daum")){
				list=RankData.daumRank();
			}else{
				list=RankData.naverRank();
			}
			
			String[] data = new String[list.size()];
			int i=0;
			for(String s:list){
				data[i]=s;
				i++;
			}
			JavaReceiverInputDStream<Status> tstream= TwitterUtils.createStream(jsc, data); //1초에 한번씩 모아주기 시작한다.
			JavaDStream<String> datas = tstream.map(new Function<Status, String>() {
				@Override
				public String call(Status s) throws Exception {
					return s.getText();
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
			reduce.saveAsHadoopFiles("hdfs://localhost:9000/user/spark/"+type, "", Text.class, IntWritable.class, TextOutputFormat.class, jc);
			jsc.start();
			jsc.awaitTermination();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void daumNaverStop(){
		jsc.stop();
	}
}
