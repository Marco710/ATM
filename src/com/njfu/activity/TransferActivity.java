package com.njfu.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.njfu.model.CardInfo;
import com.njfu.service.CardInfoService;
import com.njfu.service.TradeInfoService;

public class TransferActivity extends Activity {

	private EditText edit_acardno,edit_money,edit_pwd;
	private Button btn_ok,btn_cancel;
	private Spinner spincardno;
	private MyApp myapp;
	List<CardInfo> cardnolist;
	
	private String money,password,cardno,scardno,acardno;
	
	CardInfoService uis = new CardInfoService(TransferActivity.this);
	TradeInfoService uis1 = new TradeInfoService(TransferActivity.this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_transfer);
		
		edit_acardno=(EditText) findViewById(R.id.transfer_edit_acardno);
		edit_money=(EditText) findViewById(R.id.transfer_edit_money);
		edit_pwd=(EditText) findViewById(R.id.transfer_edit_pwd);
		
		btn_ok=(Button) findViewById(R.id.transfer_btn_ok);
		btn_cancel=(Button) findViewById(R.id.transfer_btn_cancel);
		spincardno = (Spinner) findViewById(R.id.spinner);
		myapp=(MyApp) getApplication();
		
		btn_ok.setOnClickListener(new ok_listener());
		btn_cancel.setOnClickListener(new cancel_listener());
		spincardno.setOnItemSelectedListener(new cardno_itemSelectedListener());
		
		String identity=myapp.getIdentity();
		//��ѯ�û��������п�
		cardnolist=uis.cardnolist(identity);
		
		//ʹ��Map��ֵ�Ե���ʽ�����洢cardInfoʵ�����л�ȡ����Ϣ
		//ArrayList<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		ArrayList<List<String> >list1=new ArrayList<List<String>>();
		for(CardInfo cardInfo:cardnolist){
			List<String> list2=new ArrayList<String>();
					list2.add(cardInfo.getCardno());
					list1.add(list2);
		}
        
        //������
		ArrayAdapter<List<String>> cardnoAdapter = new ArrayAdapter<List<String>>(TransferActivity.this,android.R.layout.simple_spinner_item,list1);
        //������ʽ
		cardnoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //����������
        spincardno.setAdapter(cardnoAdapter);
        
        
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.transfer, menu);
		return true;
	}

	class cancel_listener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			Intent intent=new Intent();
			intent.setClass(TransferActivity.this,FuctionActivity.class);
			TransferActivity.this.startActivity(intent);
			TransferActivity.this.finish();
		}
	}
		 //OnItemSelected������  
	    private class cardno_itemSelectedListener implements OnItemSelectedListener{          
	        @Override  
	        public void onItemSelected(AdapterView<?> adapter,View view,int position,long id) {  
	            //��ȡѡ������ֵ  
	            cardno=adapter.getItemAtPosition(position).toString();  
	            Toast.makeText(getApplicationContext(), cardno, Toast.LENGTH_LONG).show();             
	        }  
	  
	        @Override  
	        public void onNothingSelected(AdapterView<?> arg0) {  
	            String sInfo="ʲôҲûѡ��";  
	            Toast.makeText(getApplicationContext(),sInfo, Toast.LENGTH_LONG).show();  
	              
	        }  
	    }  
	
	
	    class ok_listener implements OnClickListener{

			@Override
			public void onClick(View arg0) {
				
				money=edit_money.getText().toString();
				password=edit_pwd.getText().toString();
				acardno=edit_acardno.getText().toString();
				
				String []token=cardno.split("\\[");
				String []token2=token[1].split("\\]");
				scardno=token2[0];
				
				if("".equals(money)||"".equals(password)||"".equals(scardno)||"".equals(acardno)){
					 Toast.makeText(TransferActivity.this, "�����Ϣ��д������", Toast.LENGTH_LONG).show();
				}else{
					//�ж�����Ŀ����Ƿ���ȷ
					boolean a=uis.CheckAcceptercard(acardno);
					if(a){
						CardInfo cardinfo=new CardInfo();
						cardinfo=uis.GetBalanceAndPassword(scardno);
						String pwd=cardinfo.getPassword();
						//�ж�֧������Բ���
						if(password.equals(pwd)){
							double balance=cardinfo.getBalance();
							//�жϿ����Ǯ������
							if(balance>=Double.parseDouble(money)){
								//��ת���ߵ����п����п۷�
								uis.UpdateBalanceAfterTransfer(scardno, acardno, money);
					
								Toast.makeText(TransferActivity.this, "ת�˳ɹ���", Toast.LENGTH_LONG).show();
								
								//���ɽ��׼�¼
								uis1.CreateTradeinfoAfterTansfer(scardno, acardno, money);
								
								Toast.makeText(TransferActivity.this, "���ɽ��׼�¼�У�", Toast.LENGTH_LONG).show();
								
								Intent intent=new Intent();
								intent.setClass(TransferActivity.this,FuctionActivity.class);
								TransferActivity.this.startActivity(intent);
								TransferActivity.this.finish();
							}else{
								Toast.makeText(TransferActivity.this, "���㣡", Toast.LENGTH_LONG).show();
							}
						}else{
							Toast.makeText(TransferActivity.this, "֧���������", Toast.LENGTH_LONG).show();
						}
					}else{
						Toast.makeText(TransferActivity.this, "�Է��˺Ų����ڣ�", Toast.LENGTH_LONG).show();
					}
				}
		
			}
			
		}
	    
	    
	    
	}
