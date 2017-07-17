package com.sist.mongo;

import java.util.*;
import com.sist.music.*;

public class MusicManager {
	public static void main(String[] args){
		/*MusicDAO dao = new MusicDAO("genie");
		GenieManager g = new GenieManager();
		List<MusicVO> gList = g.genieTop100();
		for(MusicVO vo:gList){
			dao.musicInsert(vo);
		}
		dao=new MusicDAO("melon");
		MelonManager m = new MelonManager();
		List<MusicVO> mList = m.melonTop100();
		for(MusicVO vo:mList){
			dao.musicInsert(vo);
		}
		System.out.println("저장완료");*/
		
		MusicDAO dao = new MusicDAO("genie");
		dao.getMusicData();
		dao=new MusicDAO("melon");
		dao.getMusicData();
	}
	
	
	
}
