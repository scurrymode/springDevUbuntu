package com.sist.food.dao;

import java.util.ArrayList;
import java.util.List;

public class FoodVO {
    private int no;
    private String name;
    private String poster;
    private double score;
    private String address;
    private String link;
    private String tel;
    private String type;
    private String price;
    private String good;
    private String soso;
    private String bad;
    private List<String> review = new ArrayList<String>();
    
	public List<String> getReview() {
		return review;
	}
	public void setReview(List<String> review) {
		this.review = review;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getGood() {
		return good;
	}
	public void setGood(String good) {
		this.good = good;
	}
	public String getSoso() {
		return soso;
	}
	public void setSoso(String soso) {
		this.soso = soso;
	}
	public String getBad() {
		return bad;
	}
	public void setBad(String bad) {
		this.bad = bad;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPoster() {
		return poster;
	}
	public void setPoster(String poster) {
		this.poster = poster;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	   
	   
}
