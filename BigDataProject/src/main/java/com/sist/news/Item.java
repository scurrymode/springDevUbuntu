package com.sist.news;

import javax.xml.bind.annotation.XmlElement;

/*
 *  <item>
<title>[����]�ڵ���, ���� �ϳ� �Ѱܺ���</title>
<link>
http://isplus.live.joins.com/news/article/aid.asp?aid=21240407
</link>
<description>
<![CDATA[
[ 2017 Ÿ�̾��ũ kbo ���� ���ξ߱� SK ���̹���- �ؼ� ������� ���� 8�� ��õSK�ູ�帲���忡�� ����ƴ�. �ؼ� �ڵ����� ����� Ÿ�� �Ʒ��� �ϰ� �ִ�. ��õ=�籤�� ����yang.gwangsam@joins.com/2017....
]]>
</description>
<pubDate>Thu, 08 Jun 2017 16:40:00 +0900</pubDate>
<author>�ϰ�������</author>
<category>������</category>
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





