package com.njfu.service;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.njfu.db.UserInfoDB;
import com.njfu.model.CardInfo;
import com.njfu.model.UserInfo;

public class CardInfoService {

private UserInfoDB userinfodb;
	
	public CardInfoService(Context context){
		this.userinfodb=new UserInfoDB(context,"test.db_user",null,1);
	}
	
	//统计用户储蓄卡的数量
	public int CountCard(CardInfo cardinfo){
		SQLiteDatabase db =userinfodb.getReadableDatabase();
		String sql ="select count(*) from tbl_cardinfo where identity=?";
		Cursor cursor = db.rawQuery(sql, new String[]{cardinfo.getIdentity()});
		int count=0;
		while(cursor.moveToNext()){
			count=Integer.parseInt(cursor.getString(cursor.getColumnIndex("count(*)")));
		}
		db.close();
		cursor.close();
		return count;
	}
	
	
	//生成银行卡
	public void AddCard(CardInfo cardinfo){
		SQLiteDatabase db =userinfodb.getReadableDatabase();
		String sql ="insert into tbl_cardinfo (cardno,identity,password,type,balance) values (?,?,?,?,?)";
		Object[] obj=new Object[]{cardinfo.getCardno(),cardinfo.getIdentity(),cardinfo.getPassword(),cardinfo.getType(),cardinfo.getBalance()};
		db.execSQL(sql, obj);
		db.close();
	}
	
	
	//将用户所有银行卡的信息显示再页面上
	public List<CardInfo> cardinfo(String identity) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = userinfodb.getWritableDatabase();
		String sql = "select * from tbl_cardinfo where identity=?";
		Cursor cursor = db.rawQuery(sql, new String[]{identity});
		

		List<CardInfo> list = new ArrayList<CardInfo>();
		while(cursor.moveToNext()){
			CardInfo cardinfo = new CardInfo();
			
			String getcardno = cursor.getString(cursor.getColumnIndex("cardno"));
			String getpassword = cursor.getString(cursor.getColumnIndex("password"));
			String gettype = cursor.getString(cursor.getColumnIndex("type"));
			Double getbalance = cursor.getDouble(cursor.getColumnIndex("balance"));
			
			cardinfo.setCardno(getcardno);
			cardinfo.setPassword(getpassword);
			cardinfo.setType(gettype);
			cardinfo.setBalance(getbalance);
			
			list.add(cardinfo);
		}
		db.close();
		cursor.close();
		return list;
	}
	
	
	//修改银行卡密码
	public void UpdatePassword(CardInfo cardinfo){
		SQLiteDatabase db =userinfodb.getReadableDatabase();
		String sql ="update tbl_cardinfo set password=?where cardno=?";
		Object[] obj=new Object[]{cardinfo.getPassword(),cardinfo.getCardno()};
		db.execSQL(sql, obj);
		db.close();
	}
	
	
	//根据身份证查找对应的所有银行卡号码
	public List<CardInfo> cardnolist(String identity) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = userinfodb.getWritableDatabase();
		String sql = "select * from tbl_cardinfo where identity=?";
		Cursor cursor = db.rawQuery(sql, new String[]{identity});
		

		List<CardInfo> list = new ArrayList<CardInfo>();
		while(cursor.moveToNext()){
			CardInfo cardinfo = new CardInfo();
			
			String getcardno = cursor.getString(cursor.getColumnIndex("cardno"));
			
			cardinfo.setCardno(getcardno);
			
			list.add(cardinfo);
		}
		db.close();
		cursor.close();
		return list;
	}
	
	//根据选择的银行卡号查询此卡的余额及密码
	public CardInfo GetBalanceAndPassword(String cardno){
		SQLiteDatabase db =userinfodb.getReadableDatabase();
		String sql ="select * from tbl_cardinfo where cardno=?";
		Cursor cursor =db.rawQuery(sql, new String[]{cardno});
		
		if(cursor.moveToFirst()){
			do{
				String getbalance=cursor.getString(cursor.getColumnIndex("balance"));
				String getpassword=cursor.getString(cursor.getColumnIndex("password"));
				CardInfo cardinfo =new CardInfo();
				cardinfo.setBalance(Double.parseDouble(getbalance));
				cardinfo.setPassword(getpassword);
				return cardinfo;
			  }while(cursor.moveToNext());		
		}
		
		db.close();
		cursor.close();
		return null;	
	}
	
	
	//信息无误后付费扣钱
	public void UpdateBalanceAfterPayFee(CardInfo cardinfo){
		SQLiteDatabase db =userinfodb.getReadableDatabase();
		String sql ="update tbl_cardinfo set balance=balance-? where cardno=?";
		Object[] obj=new Object[]{cardinfo.getBalance(),cardinfo.getCardno()};
		db.execSQL(sql, obj);
		db.close();
	}
	
	//查找这张有没有被转账的卡号
	public boolean CheckAcceptercard(String cardno){
		SQLiteDatabase db =userinfodb.getReadableDatabase();
		String sql ="select * from tbl_cardinfo where cardno=?";
		Cursor cursor =db.rawQuery(sql, new String[]{cardno});
		while(cursor.moveToNext()){
		     return true;
		}
		db.close();
		return false;
	}
	
	//信息无误后付费扣钱
	public void UpdateBalanceAfterTransfer(String sendercardno,String accepterercardno,String money){
		SQLiteDatabase db =userinfodb.getReadableDatabase();
		String sql = "update tbl_cardinfo set balance=balance-? where cardno=?";
		Object[] obj=new Object[]{Double.parseDouble(money),sendercardno};
		db.execSQL(sql, obj);
		
		String sql2 = "update tbl_cardinfo set balance=balance+? where cardno=?";
		Object[] obj2=new Object[]{Double.parseDouble(money),accepterercardno};
		db.execSQL(sql2, obj2);
		
		db.close();
	}
}
