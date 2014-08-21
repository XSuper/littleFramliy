
package com.isuper.littleframliy.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import cn.bmob.v3.BmobUser;

import com.isuper.littleframliy.R;
import com.isuper.littleframliy.base.BaseActivity;
import com.isuper.littleframliy.bean.User;
public class WelcomeActivity extends BaseActivity implements OnClickListener{
	
	
	private LinearLayout userLayout;
	private Button login,register;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		initView();
		User user = BmobUser.getCurrentUser(this, User.class);
		if(user!=null){
			Intent intent = new Intent(this, HomeActivity.class);
			startActivity(intent);
			finish();
		}else{
			userLayout.setVisibility(View.VISIBLE);
			login.setOnClickListener(this);
			register.setOnClickListener(this);
		}
	}
	private void initView() {
		userLayout = (LinearLayout)findViewById(R.id.tologin);
		login = (Button) findViewById(R.id.login);
		register = (Button) findViewById(R.id.register);
		
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, LoginActivity.class);
		switch (v.getId()) {
		case R.id.login:
			intent.putExtra("login", true);
			break;
		case R.id.register:
			intent.putExtra("login", false);
			break;
		}

		
//		ObjectAnimator ofFloat = ObjectAnimator.ofFloat(v, "rotation", 0, 10, -10, 6, -6, 3, -3, 0);
//		ofFloat.setDuration(1000);
//		ofFloat.setRepeatMode(ObjectAnimator.INFINITE);
//		ofFloat.start();
		startActivity(intent);
		//finish();
	};
}
