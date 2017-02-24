	package com.njfu.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class UserInfoDB extends SQLiteOpenHelper {

	public static final String DB_NAME="test.db_user";
	
	public static final int DB_VERSION=1;
	
	
	public UserInfoDB(Context context, String name, CursorFactory factory,
			int version) {
		super(context, DB_NAME, factory, DB_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String sql="create table if not exists tbl_userinfo(identity String primary key,logpwd String,realname String,sex String,tel String)";
		db.execSQL(sql);
		String sql1="create table if not exists tbl_cardinfo(cardno String primary key,password String,identity String,type String,balance double)";
		db.execSQL(sql1);
		String sql2="create table if not exists tbl_tradeinfo(cardno String,tradetime String,tradetype String,tradebalance double)";		
		db.execSQL(sql2);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
