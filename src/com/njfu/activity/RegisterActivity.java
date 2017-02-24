package com.njfu.activity;

import com.njfu.activity.MainActivity.register_listener;
import com.njfu.model.UserInfo;
import com.njfu.service.UserInfoService;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class RegisterActivity extends Activity {

	private EditText edit_realname,edit_identity,edit_tel,edit_logpwd;
	private RadioGroup group_sex;

	private RadioButton radio_female;
	private RadioButton radio_male;
	private Button ok,cancel,reset;
	private String realname,sex,Sex,identity,tel,logpwd;
	
	UserInfoService uis=new UserInfoService(RegisterActivity.this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		edit_realname=(EditText)findViewById(R.id.register_edit_realname);
		edit_identity=(EditText)findViewById(R.id.register_edit_identity);
		edit_tel=(EditText)findViewById(R.id.register_edit_tel);
		edit_logpwd=(EditText)findViewById(R.id.register_edit_logpwd);
		
		group_sex= (RadioGroup) findViewById(R.id.register_group_sex);
		radio_female=(RadioButton) findViewById(R.id.register_radio_female);
		radio_male=(RadioButton) findViewById(R.id.register_radio_male);
		//绑定按钮
		ok=(Button) findViewById(R.id.register_btn_ok);
		cancel=(Button) findViewById(R.id.register_btn_cancel);
		
		
        //设置监听
		group_sex.setOnCheckedChangeListener(new register_radiogroup_listener());
        ok.setOnClickListener(new register_ok_listener());
        cancel.setOnClickListener(new register_cancel_listener());
//        reset.setOnClickListener(new register_listener());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}
	
	//Ok按键监听
	class register_ok_listener implements OnClickListener{

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				
				realname=edit_realname.getText().toString();
				identity=edit_identity.getText().toString();
				tel=edit_tel.getText().toString();
				logpwd=edit_logpwd.getText().toString();
				Sex=sex;
				
				UserInfo userinfo =new UserInfo();
				userinfo.setRealname(realname);
				userinfo.setIdentity(identity);
				userinfo.setTel(tel);
				userinfo.setLogpwd(logpwd);
				userinfo.setSex(Sex);
				
				if("".equals(realname)&&"".equals(identity)&&"".equals(tel)&&"".equals(logpwd)){
					Toast.makeText(RegisterActivity.this, "信息填写不完整！", Toast.LENGTH_LONG).show();
				}else{
					boolean a=uis.CheckRegister(userinfo);
					if(a){
						uis.save(userinfo);
						
						Toast.makeText(RegisterActivity.this, "注册成功！", Toast.LENGTH_LONG).show();
						Intent intent=new Intent();
						intent.setClass(RegisterActivity.this,MainActivity.class);
						RegisterActivity.this.startActivity(intent);
						RegisterActivity.this.finish();
					}else{
						Toast.makeText(RegisterActivity.this, "您的身份证已注册过！", Toast.LENGTH_LONG).show();
						
					}
				}
			}
	    	
	    }
	
	//rodiogroup按键监听
		class register_radiogroup_listener implements OnCheckedChangeListener{

				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					// TODO Auto-generated method stub
					 if (radio_female.getId() == checkedId) { 
						 sex = radio_female.getText().toString();
						 Toast.makeText(RegisterActivity.this, "女！", Toast.LENGTH_LONG).show();
						 
					 }else if (radio_male.getId() == checkedId) { 
						 sex = radio_male.getText().toString(); 
						 Toast.makeText(RegisterActivity.this, "男！", Toast.LENGTH_LONG).show();
					 }	
					 

				}

				
		}
	

		class register_cancel_listener implements OnClickListener{

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(RegisterActivity.this,MainActivity.class);
				RegisterActivity.this.startActivity(intent);
				RegisterActivity.this.finish();
			}
		}
}
