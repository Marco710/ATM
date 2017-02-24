package com.njfu.activity;

import android.app.Application;

public class MyApp extends Application {

	public String realname;
	public String sex;
	public String identity;
	
	
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getIdentity() {
		return identity;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}
	
	public void onCreate(){
		super.onCreate();
		setRealname("ÕÅÈý");
		setSex("ÄÐ");
		setIdentity("110");
		
	}
}
