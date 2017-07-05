package com.sist.r;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.rosuda.REngine.REXP;
import org.rosuda.REngine.Rserve.RConnection;
import org.springframework.stereotype.Component;

@Component
public class RManager {
/*					[ {
						"taste" : "Lithuania",
						"count" : 501.9
					}, {
						"taste" : "The Netherlands",
						"count" : 50
					} ]
					*/
	
	public String tasteData(){
		String json="";
		try {
			RConnection rc = new RConnection();
			rc.voidEval("data<-read.table(\"/home/sist/food_data/result\")");
			REXP p = rc.eval("data$V1");
			String[] taste = p.asStrings();
			p=rc.eval("data$V2");
			int[] count = p.asIntegers();
			
			JSONArray arr=new JSONArray();
			for(int i=0; i<taste.length;i++){
				JSONObject obj = new JSONObject();
				obj.put("taste", taste[i]);
				obj.put("count", count[i]);
				arr.add(obj);
			}
			json=arr.toJSONString();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return json;
	}
}
