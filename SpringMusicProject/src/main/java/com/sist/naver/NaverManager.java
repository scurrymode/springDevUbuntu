package com.sist.naver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.springframework.stereotype.Component;

@Component
public class NaverManager {
	public static void main(String[] arg){
		NaverManager n = new NaverManager();
		n.naverBlogData("박열");
		System.out.println("저장완료");
		n.naverXmlParse();
	}

    public void naverBlogData(String title) {
        String clientId = "8qNQiH_FItebuJu160WR";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "kbAvJiZBTF";//애플리케이션 클라이언트 시크릿값";
        try {
            String text = URLEncoder.encode(title, "UTF-8");
            //String apiURL = "https://openapi.naver.com/v1/search/blog?query="+ text; // json 결과
            String apiURL = "https://openapi.naver.com/v1/search/blog.xml?display=100&start=201&query="+ text; // xml 결과
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            FileWriter fw = new FileWriter("/home/sist/music_data/music_reply.xml");
            fw.write(response.toString());
            fw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        
    }
    public void naverXmlParse(){
    	try {
			File file = new File("//home/sist/music_data/music_reply.xml");
			JAXBContext jc = JAXBContext.newInstance(Rss.class);
			Unmarshaller un = jc.createUnmarshaller();
			Rss rss = (Rss)un.unmarshal(file);
			List<Item> list = rss.getChannel().getItem();
			String data="";
			for(Item i: list){
				data+=i.getDescription()+"\n";
			}
			data=data.substring(0,data.lastIndexOf("\n"));
			data=data.replaceAll("[^가-힣 ]", "");
			FileWriter fw = new FileWriter("/home/sist/music_data/naver.txt");
			fw.write(data);;
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
