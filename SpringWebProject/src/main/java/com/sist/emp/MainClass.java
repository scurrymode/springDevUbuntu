package com.sist.emp;
import java.io.FileWriter;
import java.sql.*;

import org.rosuda.REngine.Rserve.RConnection;

/*
 * 오라클 (emp) ==> R ==> 그래프 ==> 웹에 출력
 * 
 * */
public class MainClass {

	public static void main(String[] args) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url="jdbc:oracle:thin:@211.238.142.109:1521:ORCL";
			Connection con = DriverManager.getConnection(url,"scott", "tiger");
			String sql = "SELECT ename,sal FROM emp";
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			String data="";
			while(rs.next()){
				//System.out.println(rs.getString(1)+" "+rs.getInt(2));
				data+=rs.getString(1)+" "+rs.getInt(2)+"\n";
			}
			rs.close();
			FileWriter fw = new FileWriter("/home/sist/emp.txt");
			fw.write(data.substring(0, data.lastIndexOf("\n")));
			fw.close();
			rGraph();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void rGraph(){
		try {
			RConnection rc = new RConnection();
			rc.voidEval("data<-read.table(\"/home/sist/emp.txt\")");
			rc.voidEval("png(\"/home/sist/emp.png\",width=1100,height=900)");
			rc.voidEval("barplot(data$V2,names.arg=data$V1,col=rainbow(15))");
			rc.voidEval("dev.off()");
			rc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
