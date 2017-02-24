package com.njfu.service;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.njfu.db.UserInfoDB;
import com.njfu.model.UserInfo;

public class UserInfoService {

	private UserInfoDB userinfodb;
	
	public UserInfoService(Context context){
		this.userinfodb=new UserInfoDB(context,"test.db_user",null,1);
	}
	
	//保存注册信息
	public void save(UserInfo userinfo){
		SQLiteDatabase db =userinfodb.getReadableDatabase();
		String sql ="insert into tbl_userinfo(realname,logpwd,sex,tel,identity) values (?,?,?,?,?)";
		Object[] obj=new Object[]{userinfo.getRealname(),userinfo.getLogpwd(),userinfo.getSex(),userinfo.getTel(),userinfo.getIdentity()};
		db.execSQL(sql, obj);
		db.close();
	}
	
	//根据用户名和密码判断登陆是否成功
	public boolean CheckLogin(UserInfo userinfo){
		SQLiteDatabase db =userinfodb.getReadableDatabase();
		String sql ="select * from tbl_userinfo where identity=? and logpwd=?";
		Cursor cursor =db.rawQuery(sql, new String[]{userinfo.getIdentity(),userinfo.getLogpwd()});
		
		if(cursor.moveToFirst()){
			return true;		
		}
	
		db.close();
		cursor.close();
		return false;	
	}
	
	//根据用户名判断注册是否成功
		public boolean CheckRegister(UserInfo userinfo){
			SQLiteDatabase db =userinfodb.getReadableDatabase();
			String sql ="select * from tbl_userinfo where identity=?";
			Cursor cursor =db.rawQuery(sql, new String[]{userinfo.getIdentity()});
			
			if(cursor.moveToFirst()){
				return false;		
			}
			
			db.close();
			cursor.close();
			return true;	
		}
		
	//登陆成功时获取真实姓名，性别
		
		public UserInfo GetRealnameAndSex(String identity){
			SQLiteDatabase db =userinfodb.getReadableDatabase();
			String sql ="select * from tbl_userinfo where identity=?";
			Cursor cursor =db.rawQuery(sql, new String[]{identity});
			
			if(cursor.moveToFirst()){
				do{
					String getrealname=cursor.getString(cursor.getColumnIndex("realname"));
					String getsex=cursor.getString(cursor.getColumnIndex("sex"));
					UserInfo userinfo =new UserInfo();
					userinfo.setRealname(getrealname);
					userinfo.setSex(getsex);
					return userinfo;
				  }while(cursor.moveToNext());		
			}
			
			db.close();
			cursor.close();
			return null;	
		}
		//加载用户信息
		public List<UserInfo> userinfo(String identity) {
			// TODO Auto-generated method stub
			SQLiteDatabase db = userinfodb.getWritableDatabase();
			String sql = "select * from tbl_userinfo where identity=?";
			Cursor cursor = db.rawQuery(sql, new String[]{identity});
			

			List<UserInfo> list = new ArrayList<UserInfo>();
			while(cursor.moveToNext()){
				UserInfo userinfo = new UserInfo();
				String getrealname = cursor.getString(cursor.getColumnIndex("realname"));
				String getlogpwd = cursor.getString(cursor.getColumnIndex("logpwd"));
				String gettel = cursor.getString(cursor.getColumnIndex("tel"));
				String getsex = cursor.getString(cursor.getColumnIndex("sex"));
				String getidentity = cursor.getString(cursor.getColumnIndex("identity"));
				
				userinfo.setRealname(getrealname);
				userinfo.setLogpwd(getlogpwd);
				userinfo.setSex(getsex);
				userinfo.setTel(gettel);
				userinfo.setIdentity(getidentity);
				
				
				list.add(userinfo);
//				list.add(new UserInfo(getUname,getPwd,getAge,getMobile));
			}
			db.close();
			cursor.close();
			
			return list;
		}
		
		//修改登陆密码
		public void UpdateLogpwd(UserInfo userinfo){
			SQLiteDatabase db =userinfodb.getReadableDatabase();
			String sql ="update tbl_userinfo set logpwd=? where identity=?";
			Object[] obj=new Object[]{userinfo.getLogpwd(),userinfo.getIdentity()};
			db.execSQL(sql, obj);
			db.close();
		}
}
