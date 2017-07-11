package com.sist.r;

import org.rosuda.REngine.Rserve.RConnection;
import org.springframework.stereotype.Component;
@Component
public class RManager {
	public void rGraph(int no){
		switch(no){
			case 1: barplot();
				break;
			case 2: pie();
				break;
			case 3: pie3D();
				break;
			case 4: treemap();
				break;
			case 5:
				break;
			case 6:
				break;
			case 7:
				break;
		}
	}
	
	public void barplot()
	{
		try {
			RConnection rc = new RConnection();
			rc.voidEval("library(rJava)");
			rc.voidEval("library(devtools)");
			rc.voidEval("library(RMongo)");
			rc.voidEval("mongo<-mongoDbConnect(\"mydb\",\"211.238.142.98\",\"27017\")");
			// voidEval()일반 명령어 Eval()데이터를 얻어올 때 
			rc.voidEval("data<-dbGetQuery(mongo,\"student\",\"{}\")");
			rc.voidEval("png(\"/home/sist/springDev/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/RAndJavaFinalProject/main/graph.png\",width=700, height=500)");
			rc.voidEval("barplot(data$avg, names.arg=data$name,col=rainbow(15),cex.name=0.5,ylim=c(0,100))");
			rc.voidEval("dev.off()");
			rc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void pie()
	{
		try {
			RConnection rc = new RConnection();
			rc.voidEval("library(rJava)");
			rc.voidEval("library(devtools)");
			rc.voidEval("library(RMongo)");
			rc.voidEval("mongo<-mongoDbConnect(\"mydb\",\"211.238.142.98\",\"27017\")");
			// voidEval()일반 명령어 Eval()데이터를 얻어올 때 
			rc.voidEval("data<-dbGetQuery(mongo,\"student\",\"{}\")");
			rc.voidEval("png(\"/home/sist/springDev/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/RAndJavaFinalProject/main/graph.png\",width=700, height=500)");
			rc.voidEval("pie(data$avg, labels.arg=data$name,col=rainbow(15))");
			rc.voidEval("dev.off()");
			rc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void pie3D()
	{
		try {
			RConnection rc = new RConnection();
			rc.voidEval("library(rJava)");
			rc.voidEval("library(devtools)");
			rc.voidEval("library(RMongo)");
			rc.voidEval("library(plotrix)");
			rc.voidEval("mongo<-mongoDbConnect(\"mydb\",\"211.238.142.98\",\"27017\")");
			// voidEval()일반 명령어 Eval()데이터를 얻어올 때 
			rc.voidEval("data<-dbGetQuery(mongo,\"student\",\"{}\")");
			rc.voidEval("png(\"/home/sist/springDev/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/RAndJavaFinalProject/main/graph.png\",width=700, height=500)");
			rc.voidEval("lab=paste(data$name,'\n','(',round(data$avg,1),')')");
			rc.voidEval("pie3D(data$avg,labels = lab,col=rainbow(15),cex=0.3,explode = 0.05)");
			rc.voidEval("dev.off()");
			rc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void treemap()
	{
		try {
			RConnection rc = new RConnection();
			rc.voidEval("library(rJava)");
			rc.voidEval("library(devtools)");
			rc.voidEval("library(RMongo)");
			rc.voidEval("library(treemap)");
			rc.voidEval("mongo<-mongoDbConnect(\"mydb\",\"211.238.142.98\",\"27017\")");
			// voidEval()일반 명령어 Eval()데이터를 얻어올 때 
			rc.voidEval("data<-dbGetQuery(mongo,\"student\",\"{}\")");
			rc.voidEval("png(\"/home/sist/springDev/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/RAndJavaFinalProject/main/graph.png\",width=700, height=500)");
			rc.voidEval("treemap(data,index=c(\"subject\",\"name\"),vSize=\"avg\",vColor=\"avg\",type=\"value\")");
			rc.voidEval("dev.off()");
			rc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
