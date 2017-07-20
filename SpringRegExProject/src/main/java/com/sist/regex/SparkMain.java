package com.sist.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;

import scala.Tuple2;

import java.io.Serializable;
import java.util.*;

public class SparkMain implements Serializable{

	public static void main(String[] args) {
		SparkMain sm = new SparkMain();
		sm.execute();
	}
	public void execute(){
		try {
			SparkConf conf = new SparkConf().setAppName("RegEx").setMaster("local");
			JavaSparkContext sc = new JavaSparkContext(conf);
			JavaRDD<String> files=sc.textFile("/home/sist/access_log");
			
			final String regex ="[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}";
			//final String regex="[0-9]{2}/[A-Za-z]{3}/[0-9]{4}";
			final Pattern p = Pattern.compile(regex);
			
			JavaRDD<String> words=files.flatMap(new FlatMapFunction<String, String>() {
				@Override
				public Iterator<String> call(String s) throws Exception {
					List<String> list = new ArrayList<String>();
					Matcher m = p.matcher(s);
					while(m.find()){
						list.add(m.group());
					}
					return list.iterator();
				}
			});
			JavaPairRDD<String, Integer> counts=words.mapToPair(new PairFunction<String, String, Integer>() {
				@Override
				public Tuple2<String, Integer> call(String s) throws Exception {
					Integer one = new Integer(1);
					return new Tuple2<String, Integer>(s, one);
				}
			});
			JavaPairRDD<String, Integer> reduce = counts.reduceByKey(new Function2<Integer, Integer, Integer>() {
				@Override
				public Integer call(Integer sum, Integer i) throws Exception {
					return sum+i;
				}
			});
			reduce.saveAsTextFile("/home/sist/apache");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
