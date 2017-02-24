package com.njfu.activity;



import com.njfu.activity.LoginActivity.login_listener;
import com.njfu.activity.R.id;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class FuctionActivity extends Activity {

	private TextView msg;
	private Button btn_userinfo,btn_cardinfo,btn_applycard,btn_payfee,btn_checktradeinfo,btn_transfer;
	private MyApp myapp;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fuction);
		
		msg = (TextView) findViewById(R.id.msg);
		btn_userinfo=(Button) findViewById(R.id.fuction_btn_userinfo);
		btn_cardinfo=(Button) findViewById(R.id.fuction_btn_cardinfo);
		btn_applycard=(Button) findViewById(R.id.fuction_btn_registercard);
		btn_payfee=(Button) findViewById(R.id.fuction_btn_payfee);
		btn_checktradeinfo=(Button) findViewById(R.id.fuction_btn_checktradeinfo);
		btn_transfer=(Button) findViewById(R.id.fuction_btn_transfer);
		
		btn_userinfo.setOnClickListener(new userinfo_listener());
		btn_cardinfo.setOnClickListener(new cardinfo_listener());
		btn_applycard.setOnClickListener(new applycard_listener());
		btn_payfee.setOnClickListener(new payfee_listener());
		btn_checktradeinfo.setOnClickListener(new checktradeinfo_listener());
		btn_transfer.setOnClickListener(new transfer_listener());
		
		myapp=(MyApp) getApplication();
		String msgUname = myapp.getRealname();
		String msgSex=myapp.getSex();
//		String identity=getIntent().getStringExtra("identity");
		
		msg.setText(msgUname+msgSex+"»¶Ó­ÄãµÇÂ½£¡");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.fuction, menu);
		return true;
	}
	
	
	// °´¼ü¼àÌý
		class userinfo_listener implements OnClickListener{

				@Override
				public void onClick(View arg0) {
					
					Intent intent=new Intent();
					intent.setClass(FuctionActivity.this,UserInfoActivity.class);
					FuctionActivity.this.startActivity(intent);
					FuctionActivity.this.finish();
					
				}
		}
		
		//°´¼ü¼àÌý
		class cardinfo_listener implements OnClickListener{

				@Override
				public void onClick(View arg0) {
					
					Intent intent=new Intent();
					intent.setClass(FuctionActivity.this,CardInfoActivity.class);
					FuctionActivity.this.startActivity(intent);
					FuctionActivity.this.finish();		
							
				}
		}
		
		class applycard_listener implements OnClickListener{

			@Override
			public void onClick(View arg0) {
				
				Intent intent=new Intent();
				intent.setClass(FuctionActivity.this,ApplyCardActivity.class);
				FuctionActivity.this.startActivity(intent);
				FuctionActivity.this.finish();
				
			}
	    }
		
		class payfee_listener implements OnClickListener{

			@Override
			public void onClick(View arg0) {
				
				Intent intent=new Intent();
				intent.setClass(FuctionActivity.this,PayFeeActivity.class);
				FuctionActivity.this.startActivity(intent);
				FuctionActivity.this.finish();
				
			}
	    }
		
		class checktradeinfo_listener implements OnClickListener{

			@Override
			public void onClick(View arg0) {
				
				Intent intent=new Intent();
				intent.setClass(FuctionActivity.this,CheckTradeInfoActivity.class);
				FuctionActivity.this.startActivity(intent);
				FuctionActivity.this.finish();
				
			}
	    }
				

		class transfer_listener implements OnClickListener{

			@Override
			public void onClick(View arg0) {
				
				Intent intent=new Intent();
				intent.setClass(FuctionActivity.this,TransferActivity.class);
				FuctionActivity.this.startActivity(intent);
				FuctionActivity.this.finish();
				
			}
	    }
}
