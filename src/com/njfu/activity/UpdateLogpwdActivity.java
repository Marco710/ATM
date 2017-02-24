package com.njfu.activity;

import com.njfu.activity.RegisterActivity.register_ok_listener;
import com.njfu.model.UserInfo;
import com.njfu.service.UserInfoService;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateLogpwdActivity extends Activity {

	private EditText edit_newlogpwd;
	private Button btn_ok;
	private String newlogpwd;
	private MyApp myapp;
	
	UserInfoService uis=new UserInfoService(UpdateLogpwdActivity.this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_logpwd);
		
		edit_newlogpwd=(EditText) findViewById(R.id.edit_newlogpwd);
		btn_ok=(Button) findViewById(R.id.btn_ok);
		
		btn_ok.setOnClickListener(new ok_listener());
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.update_logpwd, menu);
		return true;
	}
	
	class ok_listener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			newlogpwd=edit_newlogpwd.getText().toString();
			
			myapp=(MyApp) getApplication();
			String identity=myapp.getIdentity();
			
			UserInfo userinfo=new UserInfo();
			userinfo.setLogpwd(newlogpwd);
			userinfo.setIdentity(identity);
			
			uis.UpdateLogpwd(userinfo);
			Toast.makeText(UpdateLogpwdActivity.this, "ÐÞ¸ÄÃÜÂë³É¹¦£¡", Toast.LENGTH_LONG).show();
			Intent intent=new Intent();
			intent.setClass(UpdateLogpwdActivity.this,FuctionActivity.class);
			UpdateLogpwdActivity.this.startActivity(intent);
			UpdateLogpwdActivity.this.finish();
		}
	}

}
