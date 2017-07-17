package com.sist.music;

import java.util.*;
import com.sist.music.*;

public class RankManager {
	public static void main(String[] args){
		RankPlaceDAO dao = new RankPlaceDAO("tripadvisor");
		TripAdvisorManager t = new TripAdvisorManager();
		List<RankPlaceVO> tList = t.top25();
		for(RankPlaceVO vo:tList){
			dao.placeInsert(vo);
		}
		
		System.out.println("저장완료");
		
		/*TripDAO dao = new TripDAO("tripadvisor");
		dao.getMusicData();
*/	}
	
	
	
}
