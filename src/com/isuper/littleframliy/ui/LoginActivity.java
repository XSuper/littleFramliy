package com.isuper.littleframliy.ui;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;

import com.isuper.littleframliy.R;
import com.isuper.littleframliy.base.BaseActivity;
import com.isuper.littleframliy.bean.User;
import com.isuper.littleframliy.listener.SimpleSaveListener;
import com.isuper.littleframliy.util.StringUtils;
import com.isuper.littleframliy.view.PopOverView;
import com.isuper.littleframliy.view.SlidingButton;
import com.nineoldandroids.animation.ObjectAnimator;

public class LoginActivity extends BaseActivity implements OnClickListener {

	EditText userName;
	EditText userPass;
	Button submit;
	SlidingButton changeType;
	
	ImageView uname_ico;
	ImageView upass_ico;
	
	
	boolean isLogin = true;

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentViewWithTitleBar(R.layout.activity_login);
		isLogin = getIntent().getBooleanExtra("login", true);
		initView();
		
	}


	@Override
	public void onActivityLoad() {
		// TODO Auto-generated method stub
		super.onActivityLoad();
		if(isLogin){
			showPop(changeType,getString(R.string.login_changetype_pop_txt), new Point(300,100),PopOverView.PopoverArrowDirectionDown);
		}else{
			showPop(uname_ico,getString(R.string.mail_important), new Point(300,100),PopOverView.PopoverArrowDirectionDown);
		}

	}
	
	private void initView() {
		// TODO Auto-generated method stub

		mTitleBar.setTitleText(getString(isLogin ? R.string.login
				: R.string.register));
		

		uname_ico = (ImageView) findViewById(R.id.username_ico);
		upass_ico = (ImageView) findViewById(R.id.password_ico);
		userName = (EditText) findViewById(R.id.username);
		userPass = (EditText) findViewById(R.id.userpass);
		submit = (Button) findViewById(R.id.submit);
		changeType = (SlidingButton) findViewById(R.id.changeType);

		
		
		submit.setText(getString(isLogin ? R.string.login : R.string.register));

		changeType.setImageResource(R.drawable.btn_bottom,
				R.drawable.btn_frame, R.drawable.btn_mask,
				R.drawable.btn_pressed, R.drawable.btn_pressed);
		changeType.setChecked(isLogin);
		changeType.setFocusable(isLogin);
		changeType.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				isLogin = isChecked;
				if (isLogin) {
					submit.setText(getResources().getString(R.string.login));
				} else {
					submit.setText(getResources().getString(R.string.register));
					showPop(uname_ico,getString(R.string.mail_important), new Point(300,100),PopOverView.PopoverArrowDirectionDown);

				}
				mTitleBar.setTitleText(getString(isLogin ? R.string.login
						: R.string.register));
			}
		});

		submit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.submit:
			if(!userValidate())return;
			final User user = new User();
			user.setUsername(userName.getText().toString());
			user.setEmail(userName.getText().toString());
			user.setPassword(userPass.getText().toString());
			if (isLogin) {
				user.login(LoginActivity.this, new SimpleSaveListener(this,
						true) {
					@Override
					public void onSuccess() {
						// TODO Auto-generated method stub
						super.onSuccess();
						showToast("登陆成功");
						Intent intent = new Intent(getBaseContext(), HomeActivity.class);
						startActivity(intent);
						finish();
					}
				}.start(submit));

			} else {
				user.signUp(LoginActivity.this, new SimpleSaveListener(this,
						true) {
					@Override
					public void onSuccess() {
						showToast(getString(R.string.register_success));
						user.login(LoginActivity.this, new SimpleSaveListener(
								LoginActivity.this, true) {
							@Override
							public void onSuccess() {
								// TODO Auto-generated method stub
								super.onSuccess();
								showToast("登陆成功");
							}
						}.start());
					}
				}.start(submit));
			}
			break;

		default:
			break;
		}

	}



	/**
	 * 用户名密码验证
	 */
	private boolean userValidate() {
		// TODO Auto-generated method stub
		String unameStr = userName.getText().toString();
		//邮箱空判断
		if(StringUtils.isEmpty(unameStr)){
			ObjectAnimator.ofFloat(userName, "translationX", 0, 25, -25, 25, -25,15, -15, 6, -6, 0).setDuration(1000).start();
			return false;
		}else if(!StringUtils.isEmail(unameStr)){
			showPop(uname_ico, getString(R.string.mail_wrong), new Point(300,100), PopOverView.PopoverArrowDirectionDown);
			return false;
		}
		String upassStr = userPass.getText().toString();
		if(StringUtils.isEmpty(upassStr)){
			//
			ObjectAnimator.ofFloat(userPass, "translationX", 0, 25, -25, 25, -25,15, -15, 6, -6, 0).setDuration(1000).start();
			return false;
		}else if(StringUtils.isContainChinese(upassStr)){
			showPop(upass_ico,getString(R.string.pass_no_chinese),new Point(300,100), PopOverView.PopoverArrowDirectionDown);
			return false;
		}else if(upassStr.length()<6){
			showPop(upass_ico, getString(R.string.pass_more_six), new Point(300,100),PopOverView.PopoverArrowDirectionDown);
			return false;
		}
		return true;
	}
}
