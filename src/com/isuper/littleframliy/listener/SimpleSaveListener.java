package com.isuper.littleframliy.listener;

import android.util.Log;
import android.view.View;
import cn.bmob.v3.listener.SaveListener;

import com.isuper.littleframliy.R;
import com.isuper.littleframliy.base.BaseActivity;
import com.isuper.littleframliy.util.AnimalUtil;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

public class SimpleSaveListener implements SaveListener {

	BaseActivity activity;
	View[] views;
	boolean showTitleProgress;

	public SimpleSaveListener(BaseActivity baseActivity,
			boolean showTitleProgress) {
		this.activity = baseActivity;
		this.showTitleProgress = showTitleProgress;
	}

	/**
	 * 启动后设置控件无法点击，防止重复操作
	 * 
	 * @param views
	 * @return
	 */
	public SimpleSaveListener start(View... _views) {
		views = _views;
		
		if (showTitleProgress) {
			activity.showTitleProgress(true);
		}
		for (View view : views) {
			view.setClickable(false);

			AnimatorSet animatorSet = new AnimatorSet();
			ObjectAnimator a = ObjectAnimator.ofFloat(view, "scaleY", 1, 1.1f,
					1);
			a.setRepeatMode(ObjectAnimator.INFINITE);
			a.setRepeatCount(ObjectAnimator.INFINITE);
			ObjectAnimator b = ObjectAnimator.ofFloat(view, "scaleX", 1, 1.1f,
					1);
			b.setRepeatCount(ObjectAnimator.INFINITE);
			b.setRepeatMode(ObjectAnimator.INFINITE);
			animatorSet.playTogether(a, b);
			animatorSet.start();
			view.setTag(animatorSet);
			
		}
		return this;
	}

	@Override
	public void onFailure(int arg0, String arg1) {
		clearProgressStateAndNotClick();
		activity.showToast(activity.getString(R.string.netErro));
	}

	@Override
	public void onSuccess() {
		// TODO Auto-generated method stub
		clearProgressStateAndNotClick();

	}

	private void clearProgressStateAndNotClick() {
		for (View view : views) {
			
			view.setClickable(true);
			AnimatorSet animatorSet = (AnimatorSet) view.getTag();
			if(animatorSet!= null)animatorSet.end();
			AnimalUtil.reset(view);

		}
		if (showTitleProgress) {
			activity.showTitleProgress(false);
		}
	}
}
