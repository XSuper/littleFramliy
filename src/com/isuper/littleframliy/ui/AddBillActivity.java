package com.isuper.littleframliy.ui;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.listener.SaveListener;

import com.isuper.littleframliy.R;
import com.isuper.littleframliy.bean.Bill;
import com.isuper.littleframliy.bean.User;
import com.isuper.littleframliy.util.StringUtils;

public class AddBillActivity extends Activity implements OnClickListener{
	Bill bill;
	User user;
	BmobDate Bdate;
	//view
	EditText content;
	EditText price;
	TextView date;
	ImageButton addimg;
	Button submit;
	
	Handler handler = new Handler(){
		@Override
		public void dispatchMessage(Message msg) {
			// TODO Auto-generated method stub
			super.dispatchMessage(msg);
			switch (msg.what) {
			case 1:
				Bdate = new BmobDate(new Date());
				date.setText(Bdate.getDate());
				break;

			default:
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addbill);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		
		content = (EditText) findViewById(R.id.content);
		date = (TextView) findViewById(R.id.date);
		addimg = (ImageButton) findViewById(R.id.addimgbtn);
		submit = (Button) findViewById(R.id.submit);
		price = (EditText) findViewById(R.id.price);
		
		user = BmobUser.getCurrentUser(getBaseContext(), User.class);
		Bdate = new BmobDate(new Date());
		
		date.setText(Bdate.getDate());
		
		submit.setOnClickListener(this);
		
		TimerTask task = new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				handler.sendEmptyMessage(1);
			}
		};
		Timer timer = new Timer();
		timer.schedule(task, 0, 1000);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.submit:
			bill = new Bill();
			bill.setAuthor(user);
			bill.setDate(Bdate);
			bill.setPrice(StringUtils.toInt(price.getText().toString(), 0));
			bill.setContent(content.getText().toString());
			bill.save(getBaseContext(),new SaveListener() {
				
				@Override
				public void onSuccess() {
					// TODO Auto-generated method stub
					Toast.makeText(getBaseContext(), "success", Toast.LENGTH_LONG).show();
					
				}
				
				@Override
				public void onFailure(int arg0, String arg1) {
					// TODO Auto-generated method stub
					Toast.makeText(getBaseContext(), "failure"+arg1, Toast.LENGTH_LONG).show();
					
				}
			});
			break;

		default:
			break;
		}
		
	}

	
}
