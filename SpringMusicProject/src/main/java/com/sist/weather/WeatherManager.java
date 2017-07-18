package com.sist.weather;
import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
@Component
public class WeatherManager {
	public static void main(String[] args) {
		WeatherManager wm = new WeatherManager();
		wm.todayGetWeather();
	}
	public List<WeatherVO> todayGetWeather(){
		List<WeatherVO> list = new ArrayList<WeatherVO>();
		try{
			Document doc = Jsoup.connect("http://www.kma.go.kr/index.jsp").get();
			Elements dates = doc.select("dl.region_weather_g dd");
			Elements locations = doc.select("dl.region_weather_e dt a");
			Elements images = doc.select("dl.region_weather_e dd a img");
			
			String date = dates.get(0).text();
			for(int i = 0; i<locations.size();i++){
				String location = locations.get(i).text();
				String image = images.get(i).attr("src");
				String weather = images.get(i).attr("alt");
				System.out.println(date+" "+location+" "+image+" "+weather);
				
				WeatherVO vo = new WeatherVO();
				vo.setDate(date);
				vo.setLocation(location);
				vo.setImage("http://www.kma.go.kr/"+image);
				vo.setWeather(weather);
				list.add(vo);
			}
		}catch(Exception e){
			
		}
		return list;
	}
}
