package com.sist.temp;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.snu.ids.ha.ma.MExpression;
import org.snu.ids.ha.ma.MorphemeAnalyzer;
import org.snu.ids.ha.ma.Sentence;
public class MainClass {

	public static void main(String[] args) throws Exception{
		// string to analyze
		String string = "배우 송하윤이 안재홍을 언급하며 인터뷰 중 눈물을 흘렸다. 송하윤은 18일 오전 서울 삼청동 한 카페에서 진행된 KBS 2TV 월화극 '쌈 마이웨이' 종영 인터뷰에서 연기하다 실제 안재홍이 밉지 않았냐는 질문에 갑자기 눈물을 보였다. "
				+ "이에 그는 '실제로 얄밉다기 보다는 11부, 12부 찍을 때가 마음이 가장 아팠다. 감정신이 워낙 많았었다'며 눈물을 흘린 이유를 설명했다. "
				+ "극 중 오래된 연인으로 등장하는 두 사람은 안재홍이 회사 후배에게 한 눈을 팔자 헤어짐을 택한다. 이 연인의 사연에 공감했다는 시청자의 호평이 이어진 바 있다. "
				+ "이어 그는 '방송으론 덤덤하게 이별을 고하고 덤덤하게 촬영했다. 실제론 거의 대부분 계속 눈물이 났었다. 표예진 얼굴만 봐도 눈물났다'고 말했다. "
				+ "또 그는 '재홍이와는 대화를 진짜 많이 했다. 상대 배우와 이렇게까지 의견을 나눈 적이 있었나 할 정도다. 현장에서 드라마가 뒤로 갈수록 서로 눈을 많이 바라봤다. "
				+ "송하윤은 극 중 백설희 역을 맡아 출연했다. 김주만 역의 안재홍과 호흡을 맞추며 오래된 연인의 현실적 이야기를 그려 호평받았다. '쌈 마이웨이'가 동시간대 시청률 1위를 줄곧 지키며 인기를 모았고, 송하윤 또한 전작의 캐릭터를 지우고 백설희의 옷을 입었다는 평을 얻었다.";

		// init MorphemeAnalyzer
		MorphemeAnalyzer ma = new MorphemeAnalyzer();

		// create logger, null then System.out is set as a default logger
		ma.createLogger(null);

		// analyze morpheme without any post processing 
		List ret = ma.analyze(string);

		// refine spacing
		ret = ma.postProcess(ret);

		// leave the best analyzed result
		ret = ma.leaveJustBest(ret);

		// divide result to setences
		List<Sentence> stl = ma.divideToSentences(ret);

		// print the result
		for(int i = 0; i < stl.size(); i++ ) {
			Sentence st = stl.get(i);
			System.out.println("===>  " + st.getSentence());
			String[] data ={"NNG","NNM","NNP"};
			Pattern[] p=new Pattern[data.length];
			for( int k=0;k<p.length;k++){
				p[k]=Pattern.compile(data[k]);
			}
			Matcher[] m=new Matcher[data.length];
			for(int j = 0; j < st.size(); j++ ) {
				//System.out.println(st.get(j));
				for(int n=0;n<m.length;n++){
					m[n]=p[n].matcher(st.get(j).toString());
					if(m[n].find()){
						System.out.println(st.get(j).toString());
					}
				}
			}
		}

		ma.closeLogger();
	}

}
