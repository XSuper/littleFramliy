package com.isuper.littleframliy.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobFile;

public class Bill extends BmobObject{
	private String content;
	private double price;
	private User author;
	private BmobDate date;
	private BmobFile img;
	private boolean isShare;//�жϸö��������������ѻ���һ��������
	private Group group;
	
	
	public Group getGroup() {
		return group;
	}
	public void setGroup(Group group) {
		this.group = group;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public User getAuthor() {
		return author;
	}
	public void setAuthor(User author) {
		this.author = author;
	}
	public BmobDate getDate() {
		return date;
	}
	public void setDate(BmobDate date) {
		this.date = date;
	}
	public BmobFile getImg() {
		return img;
	}
	public void setImg(BmobFile img) {
		this.img = img;
	}
	public boolean isShare() {
		return isShare;
	}
	public void setShare(boolean isShare) {
		this.isShare = isShare;
	}
	
	
}
