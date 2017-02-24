package com.njfu.activity;


import com.njfu.db.UserInfoDB;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	private Button btn_register;
	private Button btn_login;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        UserInfoDB userinfodb=new UserInfoDB(MainActivity.this,"test.db_user",null,1);
        SQLiteDatabase db=userinfodb.getWritableDatabase();
       
        
        
        //绑定按钮
        btn_register=(Button) findViewById(R.id.main_btn_register);
        btn_login=(Button) findViewById(R.id.main_btn_login);
        //设置监听
        btn_register.setOnClickListener(new register_listener());
        btn_login.setOnClickListener(new login_listener());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    class register_listener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			
			Intent intent=new Intent();
			intent.setClass(MainActivity.this, RegisterActivity.class);
			MainActivity.this.startActivity(intent);
			//结束当前页面
			MainActivity.this.finish();
		}
    	
    }
    
    class login_listener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			
			Intent intent=new Intent();
			intent.setClass(MainActivity.this, LoginActivity.class);
			MainActivity.this.startActivity(intent);
			//结束当前页面
			MainActivity.this.finish();
		}
    	
    }
    
}
