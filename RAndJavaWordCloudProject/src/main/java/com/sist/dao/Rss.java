package com.sist.dao;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

//이게 루트다
@XmlRootElement
public class Rss {
	private Channel channel = new Channel();

	public Channel getChannel() {
		return channel;
	}
	
	@XmlElement(name="channel")//이름은 구분 못하는 경우 제외하고는 꼭 안써도됨
	public void setChannel(Channel channel) {
		this.channel = channel;
	}

}
