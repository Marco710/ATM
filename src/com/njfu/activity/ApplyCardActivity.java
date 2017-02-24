package com.njfu.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.njfu.model.CardInfo;
import com.njfu.service.CardInfoService;

public class ApplyCardActivity extends Activity {

	private Button btn_registercard;
	private Button btn_return;
	private MyApp myapp;
	CardInfoService uis = new CardInfoService(ApplyCardActivity.this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_apply_card);

		btn_registercard = (Button) findViewById(R.id.btn_registercard);
		btn_return = (Button) findViewById(R.id.btn_return);

		btn_registercard.setOnClickListener(new applycard_listener());
		btn_return.setOnClickListener(new return_listener());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.apply_card, menu);
		return true;
	}

	class applycard_listener implements OnClickListener {

		@Override
		public void onClick(View arg0) {

			myapp = (MyApp) getApplication();
			String identity = myapp.getIdentity();

			CardInfo cardinfo = new CardInfo();
			cardinfo.setIdentity(identity);
			int a = uis.CountCard(cardinfo);
			if (a > 2) {
				Toast.makeText(ApplyCardActivity.this, "对不起！你已经有3张储蓄卡，不能再申请了",
						Toast.LENGTH_LONG).show();
			} else {

				uis.AddCard(cardinfo);

				Toast.makeText(ApplyCardActivity.this, "银行卡申请成功！",
						Toast.LENGTH_LONG).show();

				Intent intent = new Intent();
				intent.setClass(ApplyCardActivity.this, CardInfoActivity.class);
				ApplyCardActivity.this.startActivity(intent);
				ApplyCardActivity.this.finish();
			}
		}

	}

	class return_listener implements OnClickListener {

		@Override
		public void onClick(View arg0) {

			Intent intent = new Intent();
			intent.setClass(ApplyCardActivity.this, FuctionActivity.class);
			ApplyCardActivity.this.startActivity(intent);
			ApplyCardActivity.this.finish();
		}
	}
}
