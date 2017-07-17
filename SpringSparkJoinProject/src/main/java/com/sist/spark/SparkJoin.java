package com.sist.spark;

import java.io.File;
import java.io.StringReader;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.PairFunction;

import com.sist.mongo.MusicDAO;

import au.com.bytecode.opencsv.CSVReader;
import scala.Tuple2;

public class SparkJoin {
//50이 전체 순위이니깐 50에서 내 순위 빼서 값이 큰게 높은 놈~!
	public static void main(String[] args){
		File dir = new File("./music-data/genie-melon");
		if(dir.exists()){
			File[] files = dir.listFiles();//파일이 있으면 폴더가 안지워지니깐..
			for(File f:files){
				f.delete();
			}
			dir.delete(); // rm -rf와 같은 효과닷~!
			
		}
		SparkConf conf = new SparkConf().setAppName("Music").setMaster("local");
		JavaSparkContext sc = new JavaSparkContext(conf);
		JavaRDD<String> genie = sc.textFile("./music-data/genie.csv");
		JavaPairRDD<String, String> geniePair = genie.mapToPair(new PairFunction<String, String, String>() {
			@Override
			public Tuple2<String, String> call(String s) throws Exception {
				CSVReader csv = new CSVReader(new StringReader(s));
				String[] d = csv.readNext();
				try {
					return new Tuple2<String, String>(d[1], d[0]);
				} catch (Exception e) {
					// TODO: handle exception
				}				
				return new Tuple2<String, String>("-1", "1");//문장이 끝났다는 걸 알려줌
			}
		});
		JavaRDD<String> melon = sc.textFile("./music-data/melon.csv");
		JavaPairRDD<String, String> melonPair = melon.mapToPair(new PairFunction<String, String, String>() {
			@Override
			public Tuple2<String, String> call(String s) throws Exception {
				CSVReader csv = new CSVReader(new StringReader(s));
				String[] d = csv.readNext();
				try {
					return new Tuple2<String, String>(d[1], d[0]);
				} catch (Exception e) {
					// TODO: handle exception
				}				
				return new Tuple2<String, String>("-1", "1");//문장이 끝났다는 걸 알려줌
			}
		});
		// (key,1) ==> (key,(1,1))
		JavaPairRDD<String, Tuple2<String, String>> joinData = geniePair.join(melonPair);
		//JavaPairRDD<String, Tuple2<String, String>> st = joinData.sortByKey(true);
		joinData.saveAsTextFile("./music-data/genie-melon");
		MusicDAO.myCreateCSV();
		MusicDAO dao = new MusicDAO("myrank");
		dao.myRankInsert();
	}
}
