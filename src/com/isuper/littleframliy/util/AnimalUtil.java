package com.isuper.littleframliy.util;

import android.view.View;

import com.nineoldandroids.view.ViewHelper;

public class AnimalUtil {
	
	 public static void reset(View target) {
	        ViewHelper.setAlpha(target, 1);
	        ViewHelper.setScaleX(target, 1);
	        ViewHelper.setScaleY(target, 1);
	        ViewHelper.setTranslationX(target, 0);
	        ViewHelper.setTranslationY(target, 0);
	        ViewHelper.setRotation(target, 0);
	        ViewHelper.setRotationY(target, 0);
	        ViewHelper.setRotationX(target, 0);
	        ViewHelper.setPivotX(target, target.getMeasuredWidth() / 2.0f);
	        ViewHelper.setPivotY(target, target.getMeasuredHeight() / 2.0f);
	    }

}
