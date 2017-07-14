package com.sist.flume;

import org.rosuda.REngine.Rserve.RConnection;

public class RManager {
	public static void main(String[] args) {
		try {
			RConnection rc = new RConnection();
			rc.voidEval("library(rJava)");
			rc.voidEval("library(devtools)"); // don't know why i had to use this before library(RMongo)
			rc.voidEval("library(RMongo)");
			rc.voidEval("mongo<-mongoDbConnect(\"mydb\",\"211.238.142.111\",27017)");
			rc.voidEval("data<-dbGetQuery(mongo,\"naver_rank\",\"{}\")");
			rc.voidEval("png('/home/sist/r-data/naver.png')");
			rc.voidEval("barplot(data$count,names.arg=data$rankdata,col=rainbow(10),cex.name=0.3,ylim=c(0,max(data$count)))"); //cex.name: label font, ylim:range
			rc.voidEval("dev.off()");
			rc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
