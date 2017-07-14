package com.sist.spark;

import java.io.StringReader;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.PairFunction;

import au.com.bytecode.opencsv.CSVReader;
import scala.Tuple2;

public class SparkManager {

	public static void main(String[] args) {
/*		HadoopFileRead h = new HadoopFileRead();
		h.fileRead("daum");
		h.fileRead("naver");
		System.out.println("저장완료");*/
		
		try {
			SparkConf conf = new SparkConf().setAppName("daum").setMaster("local"); //setMaster는 하둡이 있는 위치를 의미한다. 여러명이 있어도 하둡은 다 갖고 있는거라서... 로컬하면됨
			JavaSparkContext sc=new JavaSparkContext(conf);
			JavaRDD<String> files = sc.textFile("./daum/daum.csv");
			
			JavaPairRDD<String, String> daum = files.mapToPair(new PairFunction<String, String, String>() {
				@Override
				public Tuple2<String, String> call(String s) throws Exception { //CSV에서 분석해둔 결과를 spark로 조인하는거하려고 이짓 중...
					CSVReader csv = new CSVReader(new StringReader(s));
					try{
						String[] d = csv.readNext();
						return new Tuple2<String, String>(d[0], d[1]);
					}catch(Exception ex){
						System.out.println(ex.getMessage());
					}
					return new Tuple2<String, String>("-1","1"); //문장이 끝났다는 소리
				}
			});
			files = sc.textFile("./naver/naver.csv");
			
			JavaPairRDD<String, String> naver = files.mapToPair(new PairFunction<String, String, String>() {
				@Override
				public Tuple2<String, String> call(String s) throws Exception {
					CSVReader csv = new CSVReader(new StringReader(s));
					try{
						String[] d = csv.readNext();
						return new Tuple2<String, String>(d[0], d[1]);
					}catch(Exception ex){
						System.out.println(ex.getMessage());
					}
					return new Tuple2<String, String>("-1","1"); //문장이 끝났다는 소리
				}
			});
			JavaPairRDD<String, Tuple2<String, String>> join = daum.join(naver); //두개의 RDD조인
			join.saveAsTextFile("./daum-naver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
