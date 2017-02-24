package com.njfu.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.njfu.model.UserInfo;
import com.njfu.service.UserInfoService;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class UserInfoActivity extends Activity {

	
	private Button list_button;
	private Button return_button;
	private ListView mylistview;
	List<UserInfo> userinfo;
	private MyApp myapp;
	UserInfoService uis = new UserInfoService(UserInfoActivity.this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_userinfo);
		
		list_button = (Button)findViewById(R.id.list_btn);
		return_button=(Button)findViewById(R.id.return_btn);
		mylistview = (ListView)findViewById(R.id.listView);
		
		list_button.setOnClickListener(new list_listener());
		return_button.setOnClickListener(new return_listener());
		
		myapp=(MyApp) getApplication();
		String identity=myapp.getIdentity();
		userinfo=uis.userinfo(identity);
		
		//ʹ��Map��ֵ�Ե���ʽ�����洢userInfoʵ�����л�ȡ����Ϣ
		ArrayList<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		//ʹ��forѭ������UserInfo����ѭ��������ÿ�ν�userslist�е�һ����Ϣ����map��
		//Ȼ�� ��map �е���Ϣ ���뵽list  ��
		for(UserInfo userInfo:userinfo){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("realname", userInfo.getRealname());
			map.put("sex", userInfo.getSex());
			map.put("tel", userInfo.getTel());
			map.put("identity", userInfo.getIdentity());
			map.put("logpwd", userInfo.getLogpwd());
			
			list.add(map);
			
			//ʹ��SimpleAdapter �Ĺ��췽��ʵ��ListView
			//��list�е�ֵ����������Ҫʹ�õ�layout�У�����list�е�ֵ����������д��ģ��user_list�У�ͨ������������Ϣ����users_list���layout����ʾ������Ҫ����Ϣ
			SimpleAdapter lisAdapter = new SimpleAdapter(UserInfoActivity.this,list,R.layout.userinfo,new String[]{"realname",
					"sex","tel","identity","logpwd"},new int[]{R.id.textView1,R.id.textView2,R.id.textView3,R.id.textView4,R.id.textView5});
			
			mylistview.setAdapter(lisAdapter);
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_user, menu);
		return true;
	}
	
	class list_listener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			Intent intent=new Intent();
			intent.setClass(UserInfoActivity.this,UpdateLogpwdActivity.class);
			UserInfoActivity.this.startActivity(intent);
			UserInfoActivity.this.finish();
			
		}
		
	}
	
	class return_listener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			Intent intent=new Intent();
			intent.setClass(UserInfoActivity.this,FuctionActivity.class);
			UserInfoActivity.this.startActivity(intent);
			UserInfoActivity.this.finish();
			
		}
		
	}
	

}
