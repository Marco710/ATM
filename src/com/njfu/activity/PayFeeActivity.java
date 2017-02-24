package com.njfu.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.njfu.model.CardInfo;
import com.njfu.model.TradeInfo;
import com.njfu.model.UserInfo;
import com.njfu.service.CardInfoService;
import com.njfu.service.TradeInfoService;
import com.njfu.service.UserInfoService;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class PayFeeActivity extends Activity {

	
	private RadioGroup group_type;
	private RadioButton radio_1,radio_2,radio_3,radio_4;
	private EditText edit_money,edit_pwd;
	private Button btn_ok,btn_cancel;
	private Spinner spinnercardno;
	private MyApp myapp;
	List<CardInfo> cardnolist;
	
	private String money,password,cardno,Cardno,type,Type;
	
	CardInfoService uis = new CardInfoService(PayFeeActivity.this);
	TradeInfoService uis1 = new TradeInfoService(PayFeeActivity.this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pay_fee);
		
		group_type=(RadioGroup) findViewById(R.id.payfee_group_type);
		radio_1=(RadioButton) findViewById(R.id.payfee_radio_1);
		radio_2=(RadioButton) findViewById(R.id.payfee_radio_2);
		radio_3=(RadioButton) findViewById(R.id.payfee_radio_3);
		radio_4=(RadioButton) findViewById(R.id.payfee_radio_4);
		edit_money=(EditText) findViewById(R.id.payfee_edit_money);
		edit_pwd=(EditText) findViewById(R.id.payfee_edit_pwd);
		btn_ok=(Button) findViewById(R.id.payfee_btn_ok);
		btn_cancel=(Button) findViewById(R.id.payfee_btn_cancel);
		spinnercardno = (Spinner) findViewById(R.id.spinner);
		myapp=(MyApp) getApplication();
		
		btn_ok.setOnClickListener(new ok_listener());
		btn_cancel.setOnClickListener(new cancel_listener());
		spinnercardno.setOnItemSelectedListener(new cardno_itemSelectedListener());
		group_type.setOnCheckedChangeListener(new group_type_listener());
		
		
		String identity=myapp.getIdentity();
		//查询用户所有银行卡
		cardnolist=uis.cardnolist(identity);
		
		//使用Map键值对的形式，来存储cardInfo实体类中获取的信息
		//ArrayList<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		//使用for循环，对cardInfo进行循环遍历，每次将cardnolist中的一组信息放入map中
		//然后 将map 中的信息 加入到list  中
//		for(CardInfo cardInfo:cardnolist){
//			Map<String, Object> map = new HashMap<String, Object>();
//			
//			map.put("", cardInfo.getCardno());
//					
//			list.add(map);
//		}
		ArrayList<List<String> >list1=new ArrayList<List<String>>();
		for(CardInfo cardInfo:cardnolist){
			List<String> list2=new ArrayList<String>();
					list2.add(cardInfo.getCardno());
					list1.add(list2);
		}
		
        //适配器
		ArrayAdapter<List<String>> cardnoAdapter = new ArrayAdapter<List<String>>(PayFeeActivity.this,android.R.layout.simple_spinner_item,list1);
//		ArrayAdapter<Map<String, Object>> cardnoAdapter = new ArrayAdapter<Map<String, Object>>(PayFeeActivity.this,android.R.layout.simple_spinner_item,list);
        //设置样式
		cardnoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		//cAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
//        spinnercardno.setAdapter(cardnoAdapter);
		 spinnercardno.setAdapter(cardnoAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pay_fee, menu);
		return true;
	}
	
	class cancel_listener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			Intent intent=new Intent();
			intent.setClass(PayFeeActivity.this,FuctionActivity.class);
			PayFeeActivity.this.startActivity(intent);
			PayFeeActivity.this.finish();
			
		}
		
	}
	
	class ok_listener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			
			money=edit_money.getText().toString();
			password=edit_pwd.getText().toString();
			Type=type;
			
			String []token=cardno.split("\\[");
			String []token2=token[1].split("\\]");
			Cardno=token2[0];
			
			if("".equals(money)||"".equals(password)||"".equals(Cardno)){
				 Toast.makeText(PayFeeActivity.this, "请把信息填写完整！", Toast.LENGTH_LONG).show();
			}else{
				CardInfo cardinfo=new CardInfo();
				cardinfo=uis.GetBalanceAndPassword(Cardno);
				
				String pwd=cardinfo.getPassword();
				if(password.equals(pwd)){
					double balance=cardinfo.getBalance();
					if(balance>=Double.parseDouble(money)){
						//对银行卡进行扣费
						cardinfo.setCardno(Cardno);
						cardinfo.setBalance(Double.parseDouble(money));
						uis.UpdateBalanceAfterPayFee(cardinfo);
						Toast.makeText(PayFeeActivity.this, "缴费成功！", Toast.LENGTH_LONG).show();
						//生成交易记录
						TradeInfo tradeinfo=new TradeInfo();
						tradeinfo.setCardno(Cardno);
						tradeinfo.setTradebalance(money);
						tradeinfo.setTradetype(Type);
						uis1.CreateTradeinfoAfterPayFee(tradeinfo);
						Toast.makeText(PayFeeActivity.this, "生成交易记录中！", Toast.LENGTH_LONG).show();
						
						Intent intent=new Intent();
						intent.setClass(PayFeeActivity.this,FuctionActivity.class);
						PayFeeActivity.this.startActivity(intent);
						PayFeeActivity.this.finish();
					}else{
						Toast.makeText(PayFeeActivity.this, "对不起，您的余额不足！", Toast.LENGTH_LONG).show();
					}
				}else{
					Toast.makeText(PayFeeActivity.this, "支付密码错误！", Toast.LENGTH_LONG).show();
				}
			}

			
			
			
		}
		
	}
	
	//rodiogroup按键监听
			class group_type_listener implements OnCheckedChangeListener{

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						// TODO Auto-generated method stub
						 if (radio_1.getId() == checkedId) { 
							type = radio_1.getText().toString();
							 Toast.makeText(PayFeeActivity.this, "水电费！", Toast.LENGTH_LONG).show();
						 }else if (radio_2.getId() == checkedId) { 
							 type = radio_2.getText().toString(); 
							 Toast.makeText(PayFeeActivity.this, "有线电视费！", Toast.LENGTH_LONG).show();
						 }else if (radio_3.getId() == checkedId) { 
							 type = radio_3.getText().toString(); 
							 Toast.makeText(PayFeeActivity.this, "固话费！", Toast.LENGTH_LONG).show();
						 }else if (radio_4.getId() == checkedId) { 
							 type = radio_4.getText().toString(); 
							 Toast.makeText(PayFeeActivity.this, "煤气费！", Toast.LENGTH_LONG).show();
						 }				
					}	
			}

			
			 //OnItemSelected监听器  
		    private class cardno_itemSelectedListener implements OnItemSelectedListener{          
		        @Override  
		        public void onItemSelected(AdapterView<?> adapter,View view,int position,long id) {  
		            //获取选择的项的值  
		            cardno=adapter.getItemAtPosition(position).toString();  
		            Toast.makeText(getApplicationContext(), cardno, Toast.LENGTH_LONG).show();             
		        }  
		  
		        @Override  
		        public void onNothingSelected(AdapterView<?> arg0) {  
		            String sInfo="什么也没选！";  
		            Toast.makeText(getApplicationContext(),sInfo, Toast.LENGTH_LONG).show();  
		              
		        }  
		    }  
}
