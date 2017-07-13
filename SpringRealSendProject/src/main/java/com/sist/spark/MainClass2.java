package com.sist.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.twitter.TwitterUtils;

import com.sist.rank.RankData;

import twitter4j.Status;

import java.util.*;
public class MainClass2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			SparkConf conf = new SparkConf().setAppName("Twitter").setMaster("local[2]");
			String[] filter={"JkMI3GhgS80C4VusKAOHyr9P8",
					"NZLqTMbjYo9dZBcpAm6FkZ13JFy7E28lTuRgLZiGFHn8ojtFZo",
					"867996774627135488-97Lz8D5Q2JJkQutESn2CY9G0eTRUkxm",
					"o45mfTIv6omLn6QEkoShOtvI4IrQ4ZGrTLeYcOYwINr11"};
			JavaStreamingContext jsc = new JavaStreamingContext(conf,new Duration(10000));
			String[] prop = {"twitter4j.oauth.consumerKey",
					"twitter4j.oauth.consumerSecret",
					"twitter4j.oauth.accessToken",
					"twitter4j.oauth.accessTokenSecret"};
			for(int i = 0; i<prop.length;i++){
				System.setProperty(prop[i],filter[i]);
			}
			List<String> list =RankData.daumRank();
			String[] data = new String[list.size()];
			int i=0;
			for(String s:list){
				data[i]=s;
				System.out.println(data[i]);
				i++;
			}
			JavaReceiverInputDStream<Status> twitterStream = TwitterUtils.createStream(jsc,data); //데이터를 찾아서 스트림으로 넘겨줘라~!
			
			JavaDStream<String> status=twitterStream.map(new Function<Status, String>() {
				@Override
				public String call(Status status) throws Exception {
					return status.getText();
				}
			});
			status.print();
			jsc.start();
			jsc.awaitTermination();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
