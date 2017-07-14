package com.sist.spark;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class HadoopFileRead {//하둡에 있는 파일 읽어와서 디비에 넣기
	public static void fileRead(String type) {
		// TODO Auto-generated method stub
		try{
			TwitterDAO dao = new TwitterDAO(type);
			Configuration conf = new Configuration();
			conf.set("fs.default.name", "hdfs://localhost:9000");
			FileSystem fs = FileSystem.get(conf);
			String def_path="/user/spark";
			FileStatus[] status=fs.listStatus(new Path(def_path));
			for(FileStatus s: status){
				String spc_path=s.getPath().getName();
				System.out.println(spc_path);
				if(spc_path.equals("data"))continue;
				String dirType=spc_path.substring(0, spc_path.lastIndexOf("-"));
				if(dirType.equals(type)){
					FileStatus[] ss = fs.listStatus(new Path(def_path+"/"+spc_path));
					for(FileStatus sss: ss){
						String fileName=sss.getPath().getName();
						if(!fileName.equals("_SUCCESS")){
							//System.out.println(fileName);
							FSDataInputStream is = fs.open(new Path(def_path+"/"+spc_path+"/"+fileName));
							BufferedReader br = new BufferedReader(new InputStreamReader(is));
							String data="";
							while(true){
								String line = br.readLine();
								if(line==null) break;
								//data+=line+"\n";
								System.out.println(line);
								StringTokenizer st = new StringTokenizer(line);
								TwitterVO vo = new TwitterVO();
								vo.setRankdata(st.nextToken().replace("-"," ")); //known the size of Token
								vo.setCount(Integer.parseInt(st.nextToken().trim()));
								dao.naverRankInsert(vo); //insert to mongoDB
							}
							//System.out.println(data);
							br.close();
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
