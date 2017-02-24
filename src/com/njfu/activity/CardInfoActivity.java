package com.njfu.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.njfu.model.CardInfo;
import com.njfu.service.CardInfoService;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class CardInfoActivity extends Activity {

	private Button return_button;
	
	private ListView mylistview;
	List<CardInfo> cardinfo;
	private MyApp myapp;
	
	CardInfoService uis = new CardInfoService(CardInfoActivity.this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cardinfo);
		
		return_button = (Button)findViewById(R.id.return_btn);
		mylistview = (ListView)findViewById(R.id.listView);
		
		return_button.setOnClickListener(new return_listener());
		mylistview.setOnItemClickListener(new mylistview_onitemclicklistener());
		
		myapp=(MyApp) getApplication();
		String identity=myapp.getIdentity();
		cardinfo=uis.cardinfo(identity);
		
		//ʹ��Map��ֵ�Ե���ʽ�����洢userInfoʵ�����л�ȡ����Ϣ
		ArrayList<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		//ʹ��forѭ������UserInfo����ѭ��������ÿ�ν�userslist�е�һ����Ϣ����map��
		//Ȼ�� ��map �е���Ϣ ���뵽list  ��
		for(CardInfo cardInfo:cardinfo){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("cardno", cardInfo.getCardno());
			map.put("password", cardInfo.getPassword());
			map.put("type", cardInfo.getType());
			map.put("balance", cardInfo.getBalance());
			
			
			list.add(map);
			
			//ʹ��SimpleAdapter �Ĺ��췽��ʵ��ListView
			//��list�е�ֵ����������Ҫʹ�õ�layout�У�����list�е�ֵ����������д��ģ��user_list�У�ͨ������������Ϣ����cardinfo���layout����ʾ������Ҫ����Ϣ
			SimpleAdapter lisAdapter = new SimpleAdapter(CardInfoActivity.this,list,R.layout.cardinfo,new String[]{"cardno",
					"password","type","balance"},new int[]{R.id.textView1,R.id.textView2,R.id.textView3,R.id.textView4});
			
			mylistview.setAdapter(lisAdapter);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.card_info, menu);
		return true;
	}
	
	
	class return_listener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			Intent intent=new Intent();
			intent.setClass(CardInfoActivity.this,FuctionActivity.class);
			CardInfoActivity.this.startActivity(intent);
			CardInfoActivity.this.finish();
			
		}
		
	}
	
	//��mylistview  ���õ���¼�
		class mylistview_onitemclicklistener implements OnItemClickListener{

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				//����ÿ�ε�������ݲ�ͬ��ͨ����ͼ���ݲ�ͬ����Ϣ
				intent.putExtra("cardno", cardinfo.get((int)id).getCardno());
				intent.putExtra("password", cardinfo.get((int)id).getPassword());
				intent.putExtra("type", cardinfo.get((int)id).getType());
				intent.putExtra("balance", cardinfo.get((int)id).getBalance());
				
				intent.setClass(CardInfoActivity.this, DescActivity.class);
				CardInfoActivity.this.startActivity(intent);
			}
			
		}
	
	
		

}
