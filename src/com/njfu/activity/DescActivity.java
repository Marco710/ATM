package com.njfu.activity;

import com.njfu.model.CardInfo;
import com.njfu.service.CardInfoService;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DescActivity extends Activity {

	
	private TextView textView1,textView2,textView3;
	private EditText editText1;
	private Button btn_update,btn_return;
	CardInfoService uis = new CardInfoService(DescActivity.this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_desc);
		
		String cardno= getIntent().getStringExtra("cardno");
		String password = getIntent().getStringExtra("password");
		String type = getIntent().getStringExtra("type");
		String balance =getIntent().getStringExtra("balance");
		
		
		textView1 = (TextView)findViewById(R.id.textView1);
		editText1 = (EditText)findViewById(R.id.EditText1);
		textView2 = (TextView)findViewById(R.id.textView2);
		textView3 = (TextView)findViewById(R.id.textView3);
		btn_return = (Button)findViewById(R.id.btn_return);
		btn_update = (Button)findViewById(R.id.btn_update);
		//将意图中得到的值，放入加载在textView1,editText1,editText2,editText3中
		textView1.setText(cardno);
		editText1.setText(password);
		textView2.setText(type);
		textView3.setText(balance);
		//给按钮添加监听
		btn_return.setOnClickListener(new return_listener());
		btn_update.setOnClickListener(new update_listener());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.desc_main, menu);
		return true;
	}
	
	class update_listener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			//将修改后的信息获取，放入yserInfo 实体类中
			CardInfo cardinfo = new CardInfo();
			cardinfo.setCardno(textView1.getText().toString());
			cardinfo.setPassword(editText1.getText().toString());
			
			//执行修改方法
			uis.UpdatePassword(cardinfo);
			Toast.makeText(DescActivity.this, "修改密码成功！", Toast.LENGTH_LONG).show();
			//跳转
			Intent intent = new Intent();
			intent.setClass(DescActivity.this, CardInfoActivity.class);
			DescActivity.this.startActivity(intent);
			DescActivity.this.finish();
		}
		
	}
	
	class return_listener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(DescActivity.this, CardInfoActivity.class);
			DescActivity.this.startActivity(intent);
			DescActivity.this.finish();
		}
		
	}

}
