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
		
		//��ѯ�û��������п�
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
//		        //������
//				ArrayAdapter<List<String>> cardnoAdapter = new ArrayAdapter<List<String>>(CheckTradeInfoActivity.this,android.R.layout.simple_spinner_item,list1);
//		        //������ʽ
//				cardnoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		        //����������
//		        spcardno.setAdapter(cardnoAdapter);
//		        
//	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.check_trade_info, menu);
		return true;
	}
	
	 //OnItemSelected������  
    private class cardno_itemSelectedListener implements OnItemSelectedListener{          
        @Override  
        public void onItemSelected(AdapterView<?> adapter,View view,int position,long id) {  
            //��ȡѡ������ֵ  
            cardno=adapter.getItemAtPosition(position).toString();  
//          String []token=cardno.split("\\[");
//			String []token2=token[1].split("\\]");
//			Cardno=token2[0];
            Toast.makeText(getApplicationContext(), cardno, Toast.LENGTH_LONG).show();  
            
	        _cardno=cardno;
	        //ͨ��ѡ�����п��Ų�ѯ��Ӧ�Ľ��׼�¼
            tradeinfo=uis1.tradeinfo(_cardno);
            
          //ʹ��Map��ֵ�Ե���ʽ�����洢userInfoʵ�����л�ȡ����Ϣ
    		ArrayList<Map<String, Object>> list2 = new ArrayList<Map<String,Object>>();
    		//ʹ��forѭ������UserInfo����ѭ��������ÿ�ν�userslist�е�һ����Ϣ����map��
    		//Ȼ�� ��map �е���Ϣ ���뵽list  ��
    		for(TradeInfo tradeInfo:tradeinfo){
    			Map<String, Object> map = new HashMap<String, Object>();
    			map.put("cardno", tradeInfo.getCardno());
    			map.put("tradetime", tradeInfo.getTradetime());
    			map.put("tradetype", tradeInfo.getTradetype());
    			map.put("tradebalance", tradeInfo.getTradebalance());
    			
    			list2.add(map);
    			
    			//ʹ��SimpleAdapter �Ĺ��췽��ʵ��ListView
    			//��list�е�ֵ����������Ҫʹ�õ�layout�У�����list�е�ֵ����������д��ģ��user_list�У�ͨ������������Ϣ����cardinfo���layout����ʾ������Ҫ����Ϣ
    			SimpleAdapter lisAdapter = new SimpleAdapter(CheckTradeInfoActivity.this,list2,R.layout.tradeinfo,new String[]{"cardno",
    					"tradetime","tradetype","tradebalance"},new int[]{R.id.textView1,R.id.textView2,R.id.textView3,R.id.textView4});
    			
    			mylistview.setAdapter(lisAdapter);
    		}
        }
  
        @Override  
        public void onNothingSelected(AdapterView<?> arg0) {  
            String sInfo="ʲôҲûѡ��";  
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
