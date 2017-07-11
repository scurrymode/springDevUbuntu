package com.sist.hive;

import java.sql.*;
public class HiveMainClass {
	public static void main(String[] args) {
		try {
			Class.forName("org.apache.hive.jdbc.HiveDriver");
			String url = "jdbc:hive2://localhost:10000/default"; //데이터베이스 디폴트에 저장해서 이렇게 됨
			Connection conn = DriverManager.getConnection(url,"hive","hive");
			Statement stmt = conn.createStatement();
			String sql = "SELECT * FROM dept";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
