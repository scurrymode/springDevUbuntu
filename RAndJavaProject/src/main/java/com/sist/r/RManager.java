package com.sist.r;

import org.rosuda.REngine.Rserve.RConnection;
import org.springframework.stereotype.Component;

@Component
public class RManager {
	public void sawonSalGraph(){
		try {
			//R server Connection
			RConnection rc = new RConnection();
			//voidEval(); 명령문 전송 / eval(); 데이터값 받을때
			rc.voidEval("data<-read.csv(\"/home/sist/emp.csv\", header=T, sep=\",\")");
			//read.csv("path", header존재여부, seperator) read.table("path") readLindes("path")
			rc.voidEval("png(\"/home/sist/springDev/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/RAndJavaProject/main/sal.png\")");
			rc.voidEval("barplot(data$sal,names.arg=data$ename, col=rainbow(15))");
			rc.voidEval("dev.off()");
			rc.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
