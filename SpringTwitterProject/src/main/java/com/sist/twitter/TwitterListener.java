package com.sist.twitter;

import java.io.FileWriter;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.internal.logging.Logger;

public class TwitterListener implements StatusListener{
	Logger logger=Logger.getLogger(TwitterListener.class);
	
	@Override
	public void onException(Exception ex) {//가져오다 오류 났을때,
		ex.printStackTrace();		
	}

	@Override
	public void onDeletionNotice(StatusDeletionNotice arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onScrubGeo(long arg0, long arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStallWarning(StallWarning arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onStatus(Status status) {// 정상적으로 가져왔을때
		String data="@"+status.getUser().getScreenName()//글쓴 사람 아이디 가져오기
				+":"+status.getText()//글내용
				+"("+status.getCreatedAt()+")\n";//글쓴날짜
		System.out.println(data);
		//logger.info(data);
		try {
			FileWriter fw = new FileWriter("./app.log", true);
			fw.write(data);
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onTrackLimitationNotice(int arg0) {
		// TODO Auto-generated method stub
		
	}

}
