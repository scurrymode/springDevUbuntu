package com.sist.r;

import org.apache.hadoop.yarn.webapp.hamlet.HamletSpec.A;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.rosuda.REngine.REXP;
import org.rosuda.REngine.Rserve.RConnection;
import org.springframework.stereotype.Component;

@Component
public class RManager {
	public void wordcloud(){
		try{
			RConnection rc = new RConnection();
			rc.voidEval("library(rJava)");
			rc.voidEval("library(KoNLP)");
			rc.voidEval("library(wordcloud)");
			rc.voidEval("data<-readLines(\"/home/sist/r-data/news_data\")");
			rc.voidEval("png(\"/home/sist/springDev/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/RAndJavaWordCloudProject/main/news.png\")");
			rc.voidEval("data2<-sapply(data,extractNoun,USE.NAMES = F)");
			rc.voidEval("data3<-unlist(data2)");
			rc.voidEval("data4<-Filter(function(x){nchar(x)>=2},data3)");
			rc.voidEval("data5<-table(data4)");
			rc.voidEval("wordcloud(names(data5),freq=data5, scale=c(5,1), rot.per=0.25, min.freq=1, random.order = F, random.color = T, colors = rainbow(15))");
			rc.voidEval("dev.off()");
			rc.close();
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	public String wordcloudData(){
		//{ label: 'Back to top', url: 'https://www.jqueryscript.net/tags.php?/Back%20to%20top/', target: '_top' },
		String data="";
		try {
			RConnection rc = new RConnection();
			rc.voidEval("library(rJava)");
			rc.voidEval("library(KoNLP)");
			rc.voidEval("data<-readLines(\"/home/sist/r-data/news_data\")");
			rc.voidEval("data2<-sapply(data,extractNoun,USE.NAMES = F)");
			rc.voidEval("data3<-unlist(data2)");
			rc.voidEval("data4<-Filter(function(x){nchar(x)>=2},data3)");
			REXP p = rc.eval("data4");
			String[] temp=p.asStrings();
			rc.close();
			
			JSONArray arr = new JSONArray();
			
			for(String s:temp){
				JSONObject obj = new JSONObject();
				obj.put("label", s);
				obj.put("url", "#");
				obj.put("target", "_top");
				arr.add(obj);
			}
			data=arr.toJSONString();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
}
