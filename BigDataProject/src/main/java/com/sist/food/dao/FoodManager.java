package com.sist.food.dao;
import java.io.FileWriter;
/*
 * 		$.ajax({
 * 			type:"POST",
 * 			url:"",
 * 			data:{
 * 				id:aa,pwd:1234
 * 			},
 * 			success:function(response){
 * 				200
 * 			}
 * 			error:function(){
 * 				404,500...etc
 * 			}
 * 		})
 * 		
 * 		 <div class="top_list_slide">
              <ul class="list-toplist-slider" style="width: 531px;">
                    <li>
                      <img class="center-croping"
                           src="https://mp-seoul-image-production-s3.mangoplate.com/keyword_search/meta/pictures/6mthe-5w3p5tg67w.jpg?fit=around|600:400&amp;crop=600:400;*,*&amp;output-format=jpg&amp;output-quality=80"
                           alt="6���� ���� ī�� ����Ʈ 20�� ����"
                           onerror="this.src='https://mp-seoul-image-production-s3.mangoplate.com/web/resources/kssf5eveeva_xlmy.jpg?fit=around|*:*&amp;crop=*:*;*,*&amp;output-format=jpg&amp;output-quality=80'"
                      />


                      <a href="/top_lists/1571_cafe_june"
                         onclick="common_ga('PG_MAIN','CLICK_LIST');">
                        <figure class="ls-item">
                          <figcaption class="info">
                            <div class="info_inner_wrap">
                              <span class="title">6���� ���� ī�� ����Ʈ 20��</span>

                              <p class="desc">"�� ���� �� �����ɿ�?"</p>

 * 
 * 
 * */
import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

@Component
public class FoodManager {
	public static void main(String[] args) {
		FoodManager fm = new FoodManager();
		fm.categoryAllData();
		
	}
	public List<CategoryVO> categoryAllData(){
		List<CategoryVO> list = new ArrayList<CategoryVO>();
		
		try{
			Document doc = Jsoup.connect("https://www.mangoplate.com/").get();
			Elements pelem=doc.select("ul.list-toplist-slider li img.center-croping");  //poster
			Elements lelem=doc.select("ul.list-toplist-slider li a");	//link
			Elements telem=doc.select("figure.ls-item div.info_inner_wrap span.title"); //title
			Elements selem=doc.select("figure.ls-item div.info_inner_wrap p.desc");//subject
			for(int i=0;i<9;i++){
				/*
				 * 		attr()
				 * 		text()
				 * 		html()
				 * */
				Element pe=pelem.get(i);
				Element le=lelem.get(i);
				Element te=telem.get(i);
				Element se=selem.get(i);
				
				String poster=pe.attr("src");
				String link=le.attr("href");
				String title=te.text();
				String subejct=se.text();
				//System.out.println(title + " -" +subejct);
				
				CategoryVO vo = new CategoryVO();
				vo.setCate_no(i+1);
				vo.setPoster(poster);
				vo.setCategory(title);
				vo.setSubject(subejct);
				vo.setLink(link);
				list.add(vo);
				
			}
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return list;
	} 
	/*
	 *   <li class="toplist_list">
	              <div class="with-review">
	                <figure class="restaurant-item">
	                  <a href="/restaurants/mjR_ZoQd1e"
                       onclick="common_ga('PG_TOPLIST', 'CLICK_PICTURE_BIG')">
                      <div class="thumb">
                        <img class="center-croping"
                             src="https://mp-seoul-image-production-s3.mangoplate.com/added_restaurants/277947_1471513817405142.jpg?fit=around|738:738&amp;crop=738:738;*,*&amp;output-format=jpg&amp;output-quality=80"
                             alt="�ͳ������� ���� - ����� ������ ��ġ�� 896-5"
                             onerror="this.src='https://mp-seoul-image-production-s3.mangoplate.com/web/resources/kssf5eveeva_xlmy.jpg?fit=around|*:*&amp;crop=*:*;*,*&amp;output-format=jpg&amp;output-quality=80'"
                        />
                      </div>
	                  </a>

	                  <figcaption>
	                    <div class="info">
	                      <!--<button class="btn-type-icon share-big" ng-click="open_share_layer('3000', 'mjR_ZoQd1e')">�����ϱ�</button>-->
	                      <div class="wannago_wrap">
	                        <button class="btn-type-icon favorite wannago_btn " data-restaurant_uuid="3000" data-action_id=""></button>
	                        <p class="wannago_txt">����ʹ� </p>
	                      </div>

	                      <span class="title"> <a href="/restaurants/mjR_ZoQd1e" onclick="common_ga('PG_TOPLIST', 'CLICK_RESTAURANT_NAME')">1.<h3> �ͳ�������</h3></a></span>
	                      <strong class="point"><span>4.5</span></strong>
	                      <p class="etc">����� ������ ��ġ�� 896-5</p>
	                    </div>
	                  </figcaption>
	                </figure>

	                <div class="review-content no-bottom">
	                  <figure class="user">
	                      <div class="thumb" style="background-image: url('http://mud-kage.kakao.co.kr/14/dn/btqfzSfSkAT/ZV8YIpeXbMnKNfuhEq6Kj0/o.jpg'), url('https://mp-seoul-image-production-s3.mangoplate.com/web/resources/jmcmlp180qwkp1jj.png?fit=around|*:*&crop=*:*;*,*&output-format=jpg&output-quality=80')">
	                      </div>
	                    <figcaption>
	                      ��
	                    </figcaption>
	                  </figure>
	                  <p class="short_review" onclick="common_ga('PG_TOPLIST', 'CLICK_FEATURED_REVIEW')" data-restaurant_key="mjR_ZoQd1e" data-is_long_reivew="">
	                      ���־��!! �̱��ϰ� ���麸���� �İ�...�ſ�� �峭�ƴϰ� ������� ���־ ��� �԰Եǿ�. �����Ѱ��̶� ������ ���־��.
	                  </p>

	                  <p class="long_review">
	                    ���־��!! �̱��ϰ� ���麸���� �İ�...�ſ�� �峭�ƴϰ� ������� ���־ ��� �԰Եǿ�. �����Ѱ��̶� ������ ���־��.
	                  </p>



	                </div>


	                <a href="/restaurants/mjR_ZoQd1e" class="btn-detail" onclick="common_ga('PG_TOPLIST', 'CLICK_MORE_INFO_BTN')">
	                  �ͳ������� ������ >
	                </a>
	              </div>
	            </li>

	 */
	public List<FoodVO> categoryDetailData(String link)
	{
		// /top_lists/1309_spicy
		// list.do?page=1&no=10
		// list.do/1/10
		// ����ȥ 
		// => 16 / 17 / 14
		List<FoodVO> list=new ArrayList<FoodVO>();
		try
		{
			Document doc=Jsoup.connect("http://www.mangoplate.com"+link).get();
			Elements nElem=doc.select("div.info span.title h3");
			Elements sElem=doc.select("div.info strong.point span");
			Elements aElem=doc.select("div.info p.etc");
			Elements pElem=doc.select("div.thumb img");
			Elements lElem=doc.select("div.with-review figure.restaurant-item a");
			int j=0;
			for(int i=0;i<nElem.size();i++)
			{
				Element name=nElem.get(i);
				Element poster=pElem.get(i);
				Element address=aElem.get(i);
				Element score=sElem.get(i);
				Element link2=lElem.get(j);
				FoodVO vo=new FoodVO();
				vo.setName(name.text().trim());
				vo.setPoster(poster.attr("src"));
				vo.setAddress(address.text());
				vo.setScore(Double.parseDouble(score.text().trim()));
				vo.setLink(link2.attr("href"));
				list.add(vo);
				j+=2;
			}
			
		}catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		return list;
	}
	/*
	 * <div class="list-photo_wrap">
          <figure class="list-photo">
            <meta content="https://mp-seoul-image-production-s3.mangoplate.com/added_restaurants/65460_1479194636961663.jpg?fit=around|512:512&amp;crop=512:512;*,*&amp;output-format=jpg&amp;output-quality=80"/>
            <figure class="restaurant-photos-item" onclick="common_ga('PG_RESTAURANT', 'CLICK_PICTURE');" ng-click="mp20_gallery_open(0)" role="img" aria-label="�Ŵ���ƮĿ�� | ������ ����ī�� - �����÷���Ʈ" title="�Ŵ���ƮĿ�� | ������ ����ī�� - �����÷���Ʈ">
              <img class="center-croping"
	 */
	public FoodVO foodDetailData(String link)
	{
		FoodVO vo=new FoodVO();
		try
		{
			Document doc=Jsoup.connect("http://www.mangoplate.com"+link).get();
			Element name=doc.select("span.title h1.restaurant_name").first();
			Element score=doc.select("span.title span.rate-point").first();
			Element poster=doc.select("div.list-photo_wrap figure.restaurant-photos-item img").first();
			Element address=doc.select("tbody td a.addr-txt").first();
			Element tel=doc.select("tbody td a.tel_area").first();
			Element type=doc.select("tbody tr:eq(2) td").first();
			Element price=doc.select("tbody tr:eq(3) td").first();
			Elements temp=doc.select("div.title_fliter_wrap li.review_fliter_item button");
			vo.setName(name.text());
			vo.setScore(Double.parseDouble(score.text().trim()));
			vo.setAddress(address.text());
			vo.setTel(tel.text());
			vo.setType(type.text());
			vo.setPrice(price.text());
			vo.setPoster(poster.attr("src"));
			for(int i=0;i<temp.size();i++)
			{
				Element elem=temp.get(i);
				if(i==1)
					vo.setGood(elem.text());
				else if(i==2)
					vo.setSoso(elem.text());
				else if(i==3)
					vo.setBad(elem.text());
			}
			Elements reviewElem=doc.select("span.short_review");
			String data ="";
			for(int i=0;i<reviewElem.size();i++){
				data+=reviewElem.get(i).text()+"\n";
			}
			data=data.substring(0,data.lastIndexOf("\n"));
			FileWriter fw = new FileWriter("/home/sist/food_data/site.txt");
			fw.write(data);
			fw.close();
		}catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		return vo;
	}
}







