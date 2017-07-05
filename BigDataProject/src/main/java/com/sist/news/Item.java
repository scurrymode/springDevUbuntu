package com.sist.news;

import javax.xml.bind.annotation.XmlElement;

/*
 *  <item>
<title>[포토]박동원, 오늘 하나 넘겨볼까</title>
<link>
http://isplus.live.joins.com/news/article/aid.asp?aid=21240407
</link>
<description>
<![CDATA[
[ 2017 타이어뱅크 kbo 리그 프로야구 SK 와이번스- 넥센 히어로즈 전이 8일 인천SK행복드림구장에서 진행됐다. 넥센 박동원이 경기전 타격 훈련을 하고 있다. 인천=양광삼 기자yang.gwangsam@joins.com/2017....
]]>
</description>
<pubDate>Thu, 08 Jun 2017 16:40:00 +0900</pubDate>
<author>일간스포츠</author>
<category>스포츠</category>
<media:thumbnail url="http://imgnews.naver.net/image/thumb140/241/2017/06/08/2680357.jpg"/>
</item>
 */
public class Item {
    private String title;
    private String link;
    private String description;
	public String getTitle() {
		return title;
	}
	@XmlElement
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLink() {
		return link;
	}
	@XmlElement
	public void setLink(String link) {
		this.link = link;
	}
	public String getDescription() {
		return description;
	}
	@XmlElement
	public void setDescription(String description) {
		this.description = description;
	}
	   
}





