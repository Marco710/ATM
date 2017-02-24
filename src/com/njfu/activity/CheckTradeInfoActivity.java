package com.njfu.activity;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.njfu.model.CardInfo;
import com.njfu.model.TradeInfo;
import com.njfu.service.CardInfoService;
import com.njfu.service.TradeInfoService;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class CheckTradeInfoActivity extends Activity {

	private Spinner spcardno;
	private MyApp myapp;
	List<CardInfo> cardnolist;
	private Button btn_return;
	private ListView mylistview;
	List<TradeInfo> tradeinfo;
	private ArrayAdapter<String> adapter;
	
	private String cardno,Cardno,_cardno;
	CardInfoService uis = new CardInfoService(CheckTradeInfoActivity.this);
	TradeInfoService uis1 = new TradeInfoService(CheckTradeInfoActivity.this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_check_trade_info);
		
		btn_return=(Button) findViewById(R.id.return_btn);
		spcardno=(Spinner) findViewById(R.id.spinner);
		mylistview = (ListView)findViewById(R.id.listView);
		myapp=(MyApp) getApplication();
		
		btn_return.setOnClickListener(new return_listener());
		spcardno.setOnItemSelectedListener(new cardno_itemSelectedListener());
		
		
		String identity=myapp.getIdentity();
		
		//查询用户所有银行卡
		cardnolist=uis.cardnolist(identity);
		
		ArrayList<String> list1=new ArrayList<String>();
		for(CardInfo cardInfo:cardnolist){
			list1.add(cardInfo.getCardno());			
		}
		
		String[] characters = (String[])list1.toArray(new String[list1.size()]);  
		
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,characters);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spcardno.setAdapter(adapter);
	}        
        
//		ArrayList<List<String> >list1=new ArrayList<List<String>>();
//		for(CardInfo cardInfo:cardnolist){
//			List<String> list2=new ArrayList<String>();
//					list2.add(cardInfo.getCardno());
//					list1.add(list2);
//		}
//		        
//		        //适配器
//				ArrayAdapter<List<String>> cardnoAdapter = new ArrayAdapter<List<String>>(CheckTradeInfoActivity.this,android.R.layout.simple_spinner_item,list1);
//		        //设置样式
//				cardnoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		        //加载适配器
//		        spcardno.setAdapter(cardnoAdapter);
//		        
//	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.check_trade_info, menu);
		return true;
	}
	
	 //OnItemSelected监听器  
    private class cardno_itemSelectedListener implements OnItemSelectedListener{          
        @Override  
        public void onItemSelected(AdapterView<?> adapter,View view,int position,long id) {  
            //获取选择的项的值  
            cardno=adapter.getItemAtPosition(position).toString();  
//          String []token=cardno.split("\\[");
//			String []token2=token[1].split("\\]");
//			Cardno=token2[0];
            Toast.makeText(getApplicationContext(), cardno, Toast.LENGTH_LONG).show();  
            
	        _cardno=cardno;
	        //通过选择银行卡号查询相应的交易记录
            tradeinfo=uis1.tradeinfo(_cardno);
            
          //使用Map键值对的形式，来存储userInfo实体类中获取的信息
    		ArrayList<Map<String, Object>> list2 = new ArrayList<Map<String,Object>>();
    		//使用for循环，对UserInfo进行循环遍历，每次将userslist中的一组信息放入map中
    		//然后 将map 中的信息 加入到list  中
    		for(TradeInfo tradeInfo:tradeinfo){
    			Map<String, Object> map = new HashMap<String, Object>();
    			map.put("cardno", tradeInfo.getCardno());
    			map.put("tradetime", tradeInfo.getTradetime());
    			map.put("tradetype", tradeInfo.getTradetype());
    			map.put("tradebalance", tradeInfo.getTradebalance());
    			
    			list2.add(map);
    			
    			//使用SimpleAdapter 的构造方法实现ListView
    			//将list中的值放入我们需要使用的layout中，并将list中的值加载入我们写的模板user_list中，通过适配器将信息放入cardinfo这个layout中显示我们需要的信息
    			SimpleAdapter lisAdapter = new SimpleAdapter(CheckTradeInfoActivity.this,list2,R.layout.tradeinfo,new String[]{"cardno",
    					"tradetime","tradetype","tradebalance"},new int[]{R.id.textView1,R.id.textView2,R.id.textView3,R.id.textView4});
    			
    			mylistview.setAdapter(lisAdapter);
    		}
        }
  
        @Override  
        public void onNothingSelected(AdapterView<?> arg0) {  
            String sInfo="什么也没选！";  
            Toast.makeText(getApplicationContext(),sInfo, Toast.LENGTH_LONG).show();  
              
        }  
    }  
    
    class return_listener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			Intent intent=new Intent();
			intent.setClass(CheckTradeInfoActivity.this,FuctionActivity.class);
			CheckTradeInfoActivity.this.startActivity(intent);
			CheckTradeInfoActivity.this.finish();
			
		}
		
	}

}
