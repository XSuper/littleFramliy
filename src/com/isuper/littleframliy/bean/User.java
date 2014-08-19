package com.isuper.littleframliy.bean;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

public class User extends BmobUser{

	
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(String nick, char sex, BmobFile face, Group group) {
		super();
		this.nick = nick;
		this.sex = sex;
		this.face = face;
		this.group = group;
	}

	private String nick;
	private char sex;
	private BmobFile face;
	private Group group;

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public char getSex() {
		return sex;
	}

	public void setSex(char sex) {
		this.sex = sex;
	}

	public BmobFile getFace() {
		return face;
	}

	public void setFace(BmobFile face) {
		this.face = face;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}
	
}
