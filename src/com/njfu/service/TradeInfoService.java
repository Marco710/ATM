package com.njfu.service;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.njfu.db.UserInfoDB;
import com.njfu.model.CardInfo;
import com.njfu.model.TradeInfo;

public class TradeInfoService {

private UserInfoDB userinfodb;
	
	public TradeInfoService(Context context){
		this.userinfodb=new UserInfoDB(context,"test.db_user",null,1);
	}
	
	//缴费后生成交易记录
	public void CreateTradeinfoAfterPayFee(TradeInfo tradeinfo){
		SQLiteDatabase db =userinfodb.getReadableDatabase();
		String sql ="insert into tbl_tradeinfo (cardno,tradetime,tradetype,tradebalance) values (?,datetime('now'),?,?)";
		Object[] obj=new Object[]{tradeinfo.getCardno(),tradeinfo.getTradetype(),tradeinfo.getTradebalance()};
		db.execSQL(sql, obj);
		db.close();
	}
	
	//通过选择银行卡号查询相应的交易记录
	public List<TradeInfo> tradeinfo(String cardno) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = userinfodb.getWritableDatabase();
		String sql = "select * from tbl_tradeinfo where cardno=?";
		Cursor cursor = db.rawQuery(sql, new String[]{cardno});
		

		List<TradeInfo> list = new ArrayList<TradeInfo>();
		while(cursor.moveToNext()){
			TradeInfo tradeinfo = new TradeInfo();
			
			String getcardno = cursor.getString(cursor.getColumnIndex("cardno"));
			String gettime = cursor.getString(cursor.getColumnIndex("tradetime"));
			String gettype = cursor.getString(cursor.getColumnIndex("tradetype"));
			String getbalance = cursor.getString(cursor.getColumnIndex("tradebalance"));
			
			tradeinfo.setCardno(getcardno);
			tradeinfo.setTradetime(gettime);
			tradeinfo.setTradetype(gettype);
			tradeinfo.setTradebalance(getbalance);
			
			list.add(tradeinfo);
		}
		db.close();
		cursor.close();
		return list;
	}
	
	//转账成功后生成交易记录
	public void CreateTradeinfoAfterTansfer(String sendercardno,String acceptercardno,String tradebalance){
		SQLiteDatabase db =userinfodb.getReadableDatabase();
		
		String sql = "insert into tbl_tradeinfo (cardno,tradetime,tradetype,tradebalance)values(?,datetime('now'),?,?)";
		Object[] obj=new Object[]{sendercardno,"个人转账","支出"+tradebalance+"元"};
		db.execSQL(sql, obj);
		
		String sql2 = "insert into tbl_tradeinfo (cardno,tradetime,tradetype,tradebalance)values(?,datetime('now'),?,?)";
		Object[] obj2=new Object[]{acceptercardno,"个人转账","收入"+tradebalance+"元"};
		db.execSQL(sql2, obj2);
		
		db.close();
	}
}
