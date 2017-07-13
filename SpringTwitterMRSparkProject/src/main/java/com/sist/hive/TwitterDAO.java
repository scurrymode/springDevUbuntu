package com.sist.hive;
import java.util.*;

import org.springframework.stereotype.Component;

import java.io.*;
import java.sql.*;
@Component
public class TwitterDAO {
	private String driver="org.apache.hive.jdbc.HiveDriver";
	private String url="jdbc:hive2://localhost:10000/default";
	private Connection conn;
	private Statement stmt;
	
	public TwitterDAO() {
		try {
			Class.forName(driver);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void getConnection(){
		try {
			conn=DriverManager.getConnection(url, "hive", "hive");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void disConnection(){
		try {
			if(stmt!=null) stmt.close();
			if(conn!=null) conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void twitterCreateTable(String tname){//하이브 파일 읽어서 테이블에 넣기
		try {
			getConnection();
			/*String sql = "DROP TABLE "+tname;
			stmt=conn.createStatement();
			stmt.executeQuery(sql);
			stmt.close();*/
			String sql = "CREATE TABLE "+tname+"(data string, count int) "
					+"ROW FORMAT DELIMITED FIELDS TERMINATED BY ','";
			stmt=conn.createStatement();
			stmt.executeQuery(sql);
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			disConnection();
		}
	}
	public void twitterDataInsert(String tname){
		
		try {
			getConnection();
			
			String sql="LOAD DATA LOCAL INPATH '/home/sist/springDev/SpringTwitterMRSparkProject/input/"+tname+"\\.csv' "
					+"OVERWRITE INTO TABLE "+tname;
			stmt=conn.createStatement();
			stmt.execute(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			disConnection();
		}
	}
	public List<TwitterVO> twiiterRankData(){
		List<TwitterVO> list = new ArrayList<TwitterVO>();
		try {
			getConnection();
			String sql = "SELECT d.data, d.count+n.count FROM daum d, naver n WHERE d.data=n.data";
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				TwitterVO vo = new TwitterVO();
				vo.setRankdata(rs.getString(1));
				vo.setCount(rs.getInt(2));
				list.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disConnection();
		}
		return list;
	}
}
