package com.sist.review;

import org.springframework.stereotype.Component;

@Component
public class DataDirectory {
	public String[] dataDir1() {
		String[] data = {
				"해피", "슬픔", "휴식", "로맨틱", "스트레스", "힐링", "집중", "카페", "운동", "드라이브", "여행"
		};
		return data;
	}

	public String[] dataDir2() {
		String[] data = {
				"록", "여름", "HOT", "차트", "뮤지션", "시대", "발라드", "댄스", "업비트", "OST"
		};
		return data;
	}
}
