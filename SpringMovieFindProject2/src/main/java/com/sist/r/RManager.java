package com.sist.r;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.rosuda.REngine.REXP;
import org.rosuda.REngine.Rserve.RConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.JsonObject;
import com.sist.dao.MovieDAO;
import com.sist.manager.MovieVO;

import twitter4j.internal.org.json.JSONArray;

@Component
public class RManager {
	@Autowired
	private MovieDAO dao;
	// /home/sist/springDev/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/SpringMovieFindProject2/
	public String rFeelGraph(){
		String result="[";
		try {
			RConnection rc = new RConnection();
			rc.voidEval("data<-read.table(\"/home/sist/movie_data/result\")");
			/*rc.voidEval("png(\"/home/sist/springDev/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/SpringMovieFindProject2/main/feel.png\")");
			rc.voidEval("barplot(data$V2,names.arg = data$V1, col=rainbow(10))");
			rc.voidEval("dev.off()"); //여기까지는 그냥 R로 결과보여줄라고 한 코딩*/
			//이제 라이브러리로 파이차트 보여주기~!
			REXP p=rc.eval("data$V1");//R에서 값 가져오기
			String[] feel = p.asStrings();
			p=rc.eval("data$V2");
			int[] count = p.asIntegers();
			
			for(int i=0;i<feel.length;i++){
				result+="['"+feel[i]+"',"+count[i]+"],";
			}
			result=result.substring(0, result.lastIndexOf(","));
			result+="]";
			rc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	//추천 그래프 그리기
	public List<MovieVO> recommandData(){
		List<MovieVO> list = new ArrayList<MovieVO>();
		try {
			RConnection rc = new RConnection();
			rc.voidEval("data<-read.table(\"/home/sist/movie_data/recommand_result\")");
			rc.voidEval("data1<-data[order(data$V2,decreasing=T),c(\"V1\",\"V2\")]");
			REXP p=rc.eval("data1$V1");
			String[] title=p.asStrings();
			
			for(int i=0; i<12;i++){
				MovieVO vo=dao.movieRecommandData(title[i].replace(",", " "));
				list.add(vo);
			}
			rc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
