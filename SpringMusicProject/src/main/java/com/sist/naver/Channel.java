package com.sist.naver;

import java.util.*;

import com.sun.xml.txw2.annotation.XmlElement;
public class Channel {
	private List<Item> item = new ArrayList<Item>();

	public List<Item> getItem() {
		return item;
	}

	public void setItem(List<Item> item) {
		this.item = item;
	}

}
