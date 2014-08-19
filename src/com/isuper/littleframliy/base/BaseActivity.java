package com.isuper.littleframliy.base;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.isuper.littleframliy.R;
import com.isuper.littleframliy.view.BottomBar;
import com.isuper.littleframliy.view.PopOverView;
import com.isuper.littleframliy.view.TitleBar;



public class BaseActivity extends Activity{
	public LayoutInflater mInflater;
	public View Layout_base;
	public RelativeLayout contentLayout;
	public TitleBar mTitleBar;
	public LayoutParams layoutParamsFF;
	public LayoutParams layoutParamsFW;
	public LayoutParams layoutParamsWF;
	public LayoutParams layoutParamsWW;
	public BottomBar mAbBottomBar;
	
	/*pop 相关*/
	private PopOverView popoverView;
	private TextView popTxt;
	
	private int focusCount = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		mInflater = LayoutInflater.from(this);
		
		layoutParamsFF = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		layoutParamsFW = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		layoutParamsWF = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
		layoutParamsWW = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
		if(focusCount==0){
			onActivityLoad();
		}
		focusCount++;
	}
	public void onActivityLoad() {
		// TODO Auto-generated method stub
		
	}

	//为了得到Layout_base
	@Override
	public void setContentView(int layoutResID) {
		// TODO Auto-generated method stub
		setContentView(mInflater.inflate(layoutResID, null));
		
	}
	@Override
	public void setContentView(View view) {
		// TODO Auto-generated method stub
		super.setContentView(view);
		Layout_base = (ViewGroup) view;
	}
	@Override
	public void setContentView(View view,
			android.view.ViewGroup.LayoutParams params) {
		// TODO Auto-generated method stub
		super.setContentView(view, params);
		Layout_base = (ViewGroup) view;
	}
	/**
	 *  activity显示titlebar
	 * @param layoutResID
	 */
	public void setContentViewWithTitleBar(int layoutResID){
		setContentViewWithTitleBar(mInflater.inflate(layoutResID, null));
	}
	public void setContentViewWithTitleBar(View contentView){
		//主标题栏
		mTitleBar = new TitleBar(this);
		
		//初始化TitleBar
		mTitleBar.setLogo(R.drawable.common_btn_home);
		LayoutParams params = new LayoutParams(80, 80);
		params.gravity = Gravity.CENTER;
		mTitleBar.getLogoView().setLayoutParams(params);
		mTitleBar.setTitleBarBackground(R.color.mainColor);
		mTitleBar.setTitleTextMargin(10, 0, 0, 0);
		mTitleBar.setLogoLine(R.color.black);
		
		//最外层布局
		RelativeLayout Layout_base = new RelativeLayout(this);
		Layout_base.setBackgroundColor(Color.rgb(255, 255, 255));
		//内容布局
		contentLayout = new RelativeLayout(this);
		contentLayout.setPadding(0, 0, 0, 0);
		
		contentLayout.removeAllViews();
		contentLayout.addView(contentView,layoutParamsFF);
		
		//副标题栏
		mAbBottomBar = new BottomBar(this);
		
        //填入View
		Layout_base.addView(mTitleBar,layoutParamsFW);
		
		RelativeLayout.LayoutParams layoutParamsFW2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		layoutParamsFW2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
		Layout_base.addView(mAbBottomBar, layoutParamsFW2);
		
		RelativeLayout.LayoutParams layoutParamsFW1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		layoutParamsFW1.addRule(RelativeLayout.BELOW, mTitleBar.getId());
		layoutParamsFW1.addRule(RelativeLayout.ABOVE, mAbBottomBar.getId());
		Layout_base.addView(contentLayout, layoutParamsFW1);
		
		//设置ContentView
        setContentView(Layout_base,layoutParamsFF);
	}
	
	/**
	 * 
	 * @return 是否成功显示
	 */
	public boolean showTitleProgress(boolean show){
		if(mTitleBar==null){
			return false;
		}
		if(show){
			ProgressBar progressBar = new ProgressBar(this);
			android.view.ViewGroup.LayoutParams params = new LayoutParams(50, 50);
			progressBar.setLayoutParams(params );
			mTitleBar.getRightLayout().addView(progressBar);
			mTitleBar.getRightLayout().setVisibility(View.VISIBLE);
		}else{
			mTitleBar.getRightLayout().removeAllViews();
			mTitleBar.getRightLayout().setVisibility(View.GONE);
		}
		return true;
	}
	
	public void showToast(String str){
		Toast.makeText(this, str, Toast.LENGTH_LONG).show();
	}
	public void showPop(View v,String str,Point point,int arrowDirections) {
		// TODO Auto-generated method stub
		if(popoverView==null){
			popoverView = new PopOverView(this);
			popoverView.setBackgroundDrawable(this.getResources().getDrawable(
					R.drawable.pop_bg));
			popoverView.setArrowLeftDrawable(this.getResources().getDrawable(
					R.drawable.pop_left));
			popoverView.setArrowRightDrawable(this.getResources().getDrawable(
					R.drawable.pop_right));
			popoverView.setArrowDownDrawable(this.getResources().getDrawable(
					R.drawable.pop_down));
			popoverView.setArrowUpDrawable(this.getResources().getDrawable(
					R.drawable.pop_up));
			popTxt = new TextView(this);
			popTxt.setTextColor(Color.WHITE);
			popTxt.setTextSize(13);
		}
		
		popoverView.setContentSizeForViewInPopover(point);
		popTxt.setText(str);
		popoverView.setPopoverContentView(popTxt);
		popoverView.showPopoverFromRectInViewGroup((ViewGroup)Layout_base,
				PopOverView.getFrameForView(v),
				arrowDirections, true);

	}

}
