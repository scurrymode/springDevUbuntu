package com.sist.spark;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sist.data.RankData;

import scala.Tuple2;

@Component
@Scope("prototype")
public class TwitterSpark implements Serializable{
	public static void main(String[] args) {
		TwitterSpark ts = new TwitterSpark();
		try {
			File dir = new File("./output_daum");
			if(dir.exists()){
				File[] list = dir.listFiles();
				for(File f: list){
					f.delete();
				}
				dir.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		ts.execute();
		ts.createCSV();
	}
	
	public void execute(){
		SparkConf conf = new SparkConf().setAppName("daum").setMaster("local"); //setMaster는 하둡이 있는 위치를 의미한다. 여러명이 있어도 하둡은 다 갖고 있는거라서... 로컬하면됨
		JavaSparkContext sc=new JavaSparkContext(conf);
		JavaRDD<String> files = sc.textFile("./input/daum.txt");
		
		List<String> list = RankData.daumRank();
		final Pattern[] p = new Pattern[list.size()];
		for(int i=0; i<p.length;i++){
			p[i] = Pattern.compile(list.get(i));
		}
		
		final List<String> wordList=new ArrayList<String>();
		JavaRDD<String> words = files.flatMap(new FlatMapFunction<String, String>() {
			@Override
			public Iterable<String> call(String s) throws Exception {
				System.out.println(s);
				Matcher[] m = new Matcher[p.length];
				for(int i =0; i<m.length;i++){
					m[i]=p[i].matcher(s);
					while(m[i].find()){
						System.out.println("find:"+m[i].group());
						wordList.add(m[i].group()); //문장 전체를 가져올때 쓴다. 	
					}
				}
				return wordList;
			}
		});
		JavaPairRDD<String, Integer> counts = words.mapToPair(new PairFunction<String, String, Integer>() {
			@Override
			public Tuple2<String, Integer> call(String s) throws Exception {
				return new Tuple2<String, Integer>(s,1);
			}
		});
		JavaPairRDD<String, Integer> res = counts.reduceByKey(new Function2<Integer, Integer, Integer>() {
			@Override
			public Integer call(Integer sum, Integer i) throws Exception {
				return sum+i;
			}
		});
		res.join(res);
		res.saveAsTextFile("./output_daum/");
	}
	
	public void createCSV(){
		try {
			String data="";
			FileReader fr = new FileReader("./output_daum/part-00000");
			int i = 0;
			while((i=fr.read())!=-1){
				data+=String.valueOf((char)i);//아스키코드로 읽어오기 때문에 차로 바꾸고 읽어야 됨
			}
			fr.close();
			data=data.replace("(","");
			data=data.replace(")","");
			System.out.println(data);
			FileWriter fw = new FileWriter("./input/daum.csv");
			fw.write(data);
			fw.close();
		} catch (Exception e) {
		}
	}
}
