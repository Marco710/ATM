package com.njfu.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.njfu.model.UserInfo;
import com.njfu.service.UserInfoService;

public class LoginActivity extends Activity {

	private EditText edit_identity,edit_logpwd;
	private Button ok;
	private String identity,logpwd,sex;
	private MyApp myapp;
	
	UserInfoService uis=new UserInfoService(LoginActivity.this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		edit_identity=(EditText)findViewById(R.id.login_edit_identity);
		edit_logpwd=(EditText)findViewById(R.id.login_edit_logpwd);
		
		ok=(Button) findViewById(R.id.login_btn_ok);
		ok.setOnClickListener(new login_listener());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
	
	//Ok按键监听
	class login_listener implements OnClickListener{

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				identity=edit_identity.getText().toString();
				logpwd=edit_logpwd.getText().toString();
				
				UserInfo userinfo =new UserInfo();
				userinfo.setIdentity(identity);
				userinfo.setLogpwd(logpwd);
				
				if("".equals(identity)&&"".equals(logpwd)){
					Toast.makeText(LoginActivity.this, "登陆信息不能有空！！", Toast.LENGTH_LONG).show();
					
				}else{
					boolean a =uis.CheckLogin(userinfo);
					if(a){
						UserInfo u=new UserInfo();
						u=uis.GetRealnameAndSex(identity);
						if("男".equals(u.getSex())){
							sex="先生";
						}else{
							sex="女士";
						}
						Toast.makeText(LoginActivity.this, "登陆成功！", Toast.LENGTH_LONG).show();
						
						myapp=(MyApp) getApplication();
						
						myapp.setRealname(u.getRealname());
						myapp.setSex(sex);
						myapp.setIdentity(identity);
						
						Intent intent=new Intent();
						intent.setClass(LoginActivity.this,FuctionActivity.class);
						LoginActivity.this.startActivity(intent);
						LoginActivity.this.finish();		
					}else{
						Toast.makeText(LoginActivity.this, "用户名或密码错误！", Toast.LENGTH_LONG).show();
					}
				}
			}
	    	
	    }
	

}
