package com.sist.twitter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import scala.Tuple2;
import twitter4j.Status;

@Component
public class SparkMain {
	@Autowired
	private Configuration hconf;
	private JobConf jobConf;
	

	public static void main(String[] args) {
		try {//스파크는 매개변수로 하면 안된다... 그래서 별 수 없이 main에서 다 해야 한다.
			SparkMain sm = new SparkMain();
			SparkConf sconf;
			JavaStreamingContext jsc;
			
			sconf=new SparkConf().setAppName("Twitter-Real").setMaster("local[2]");//사실 하둡크러스터를 이용할때 2번으로 하는거다 그거부터면 datanode니깐...ㅋ
			jsc = new JavaStreamingContext(sconf, new Duration(10000));
			sm.hconf = new Configuration();
			sm.hconf.set("fs.default.name","hdfs://localhost:9000");
			sm.jobConf = new JobConf();
			
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
			final String[] data = {
					"헤이즈", "볼빨간사춘기",	"지코",	"아이유", "싸이", "레드벨벳", "마마무", "TWICE", "BLACKPINK", "G-DRAGON"};
			JavaReceiverInputDStream<Status> twitterStream=TwitterUtils.createStream(jsc, data);
			JavaDStream<String> status=twitterStream.map(new Function<Status, String>() {
				@Override
				public String call(Status s) throws Exception {
					return s.getText();
				}
			});
			final Pattern[] p = new Pattern[data.length];
			for(int i=0;i<p.length;i++){
				p[i]=Pattern.compile(data[i]);
			}
			final Matcher[] m = new Matcher[data.length];
			JavaDStream<String> words = status.flatMap(new FlatMapFunction<String, String>() {
				List<String> list = new ArrayList<String>();
				@Override
				public Iterable<String> call(String s) throws Exception {
					for(int i=0;i<m.length;i++){
						m[i]=p[i].matcher(s);
						while(m[i].find()){
							
							list.add(m[i].group().replace(" ", "$"));//띄어쓰기가 있었다면 그걸 나중에 다시 되돌리기
							System.out.println(m[i].group());
						}
					}
					return list;
				}
			});
			JavaPairDStream<String, Integer> counts = words.mapToPair(new PairFunction<String, String, Integer>() {
				@Override
				public Tuple2<String, Integer> call(String s) throws Exception {
					return new Tuple2<String, Integer>(s, 1);
				}
			});
			JavaPairDStream<String, Integer> reduce = counts.reduceByKey(new Function2<Integer, Integer, Integer>() {
				@Override
				public Integer call(Integer sum, Integer i) throws Exception {
					return sum+i;
				}
			});
			reduce.print();
			sm.hadoopGetFile(reduce);
			jsc.start();
			jsc.awaitTermination();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sparkInit() {
		try {
	/*		sconf=new SparkConf().setAppName("Twitter-Real").setMaster("local[2]");//사실 하둡크러스터를 이용할때 2번으로 하는거다 그거부터면 datanode니깐...ㅋ
			jsc = new JavaStreamingContext(sconf, new Duration(10000));
			hconf = new Configuration();
			hconf.set("fs.default.name","hdfs://localhost:9000");
			jobConf = new JobConf();*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void twitterStart(){
		try {
			//Hadoop 저장
			/*String[] filter={"JkMI3GhgS80C4VusKAOHyr9P8",
					"NZLqTMbjYo9dZBcpAm6FkZ13JFy7E28lTuRgLZiGFHn8ojtFZo",
					"867996774627135488-97Lz8D5Q2JJkQutESn2CY9G0eTRUkxm",
					"o45mfTIv6omLn6QEkoShOtvI4IrQ4ZGrTLeYcOYwINr11"};
			JavaStreamingContext jsc = new JavaStreamingContext(sconf, new Duration(10000));
			String[] prop = {"twitter4j.oauth.consumerKey",
					"twitter4j.oauth.consumerSecret",
					"twitter4j.oauth.accessToken",
					"twitter4j.oauth.accessTokenSecret"};
			for(int i = 0; i<prop.length;i++){
				System.setProperty(prop[i],filter[i]);
			}
			String[] data = {
					"헤이즈", "볼빨간사춘기",	"지코",	"아이유", "싸이", "레드벨벳", "마마무", "TWICE", "BLACKPINK", "G-DRAGON"};
			JavaReceiverInputDStream<Status> twitterStream=TwitterUtils.createStream(jsc, data);
			JavaDStream<String> status=twitterStream.map(new Function<Status, String>() {
				@Override
				public String call(Status s) throws Exception {
					return s.getText();
				}
			});
			status.print();
			jsc.start();
			jsc.awaitTermination();*/
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void hadoopGetFile(JavaPairDStream<String, Integer> jps){
		try {
			//Mongodb 전송
			jps.saveAsHadoopFiles("hdfs://localhost:9000/user/hadoop/music", "", Text.class, IntWritable.class, TextOutputFormat.class, jobConf);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
