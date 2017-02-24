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
		
		//使用Map键值对的形式，来存储userInfo实体类中获取的信息
		ArrayList<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		//使用for循环，对UserInfo进行循环遍历，每次将userslist中的一组信息放入map中
		//然后 将map 中的信息 加入到list  中
		for(CardInfo cardInfo:cardinfo){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("cardno", cardInfo.getCardno());
			map.put("password", cardInfo.getPassword());
			map.put("type", cardInfo.getType());
			map.put("balance", cardInfo.getBalance());
			
			
			list.add(map);
			
			//使用SimpleAdapter 的构造方法实现ListView
			//将list中的值放入我们需要使用的layout中，并将list中的值加载入我们写的模板user_list中，通过适配器将信息放入cardinfo这个layout中显示我们需要的信息
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
	
	//给mylistview  设置点击事件
		class mylistview_onitemclicklistener implements OnItemClickListener{

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				//根据每次点击的内容不同，通过意图传递不同的信息
				intent.putExtra("cardno", cardinfo.get((int)id).getCardno());
				intent.putExtra("password", cardinfo.get((int)id).getPassword());
				intent.putExtra("type", cardinfo.get((int)id).getType());
				intent.putExtra("balance", cardinfo.get((int)id).getBalance());
				
				intent.setClass(CardInfoActivity.this, DescActivity.class);
				CardInfoActivity.this.startActivity(intent);
			}
			
		}
	
	
		

}
