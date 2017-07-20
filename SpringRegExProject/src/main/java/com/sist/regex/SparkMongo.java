package com.sist.regex;

import java.io.FileReader;
import java.io.Serializable;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.SparkSession;
import org.bson.Document;

import com.mongodb.spark.MongoSpark;
import com.mongodb.spark.config.WriteConfig;

import java.util.*;

public class SparkMongo implements Serializable {
	public static void main(String[] args){
		SparkMongo sm = new SparkMongo();
		sm.mongoInsert();
	}
	public void mongoInsert(){
		try {//config 앞에는 인풋(주소에서/{db}/{collection}), 뒤는 아웃풋
			//스파크 몽고디비 연결
			SparkSession spark = SparkSession.builder().master("local").appName("SM").config("spark.mongodb.input.uri", "mongodb://211.238.142.111/mydb.apache").config("spark.mongodb.output.uri", "mongodb://211.238.142.111/mydb.apache").getOrCreate();
			JavaSparkContext jsc = new JavaSparkContext(spark.sparkContext());
			Map<String, String> option = new HashMap<String, String>();
			option.put("collection", "apache");
			WriteConfig config =WriteConfig.create(jsc).withOptions(option);
			
			//스파크로 분석해서 저장해둔 파일을 읽어와서 몽고에 넣기 좋은 방식으로 바꿔~ vo에 넣고 그걸 json으로
			FileReader fr = new FileReader("/home/sist/apache/part-00000");
			String data="";
			int i=0;
			while((i=fr.read())!=-1){
				data+=String.valueOf((char)i);
			}
			fr.close();
			data=data.replace("(", "");
			data=data.replace(")", "");
			String[] temp=data.split("\n");
			List<MyVO> list = new ArrayList<MyVO>();
			for(i=0;i<temp.length;i++){
				StringTokenizer st = new StringTokenizer(temp[i], ",");
				MyVO vo = new MyVO();
				vo.setIp(st.nextToken());
				vo.setCount(Integer.parseInt(st.nextToken()));
				list.add(vo);
			}
			JavaRDD<Document> sd = jsc.parallelize(list).map(new Function<MyVO, Document>() {
				@Override
				public Document call(MyVO vo) throws Exception {
					return new Document().parse("{ip:'"+vo.getIp()+"',count:"+vo.getCount()+"}");
				}
			});
			MongoSpark.save(sd,config);
			jsc.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
