package com.sist.data;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import java.io.*;

public class TwitterListener implements StatusListener {

	@Override
	public void onException(Exception e) {
		e.printStackTrace();
	}

	@Override
	public void onDeletionNotice(StatusDeletionNotice arg0) {
	}

	@Override
	public void onScrubGeo(long arg0, long arg1) {
	}

	@Override
	public void onStallWarning(StallWarning arg0) {
	}

	@Override
	public void onStatus(Status status) { //한줄을 읽을 때마다 호출되는 이벤트
		String data = status.getText();
		//data=data.replace("[^가-힣 ]", "");
		try {
			FileWriter fw = new FileWriter("./input/daum.txt",true);
			fw.write(data);
			fw.close();
		} catch (Exception e) {
			
		}
	}

	@Override
	public void onTrackLimitationNotice(int arg0) {
	}
	

}
