package com.isuper.littleframliy.base;

import android.app.Application;
import cn.bmob.v3.Bmob;

import com.isuper.littleframliy.bean.Group;

public class MApplication extends Application{
	
	
	Group group;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		Bmob.initialize(this, "741e0b3c6e7145a8cb3850c748238a5c");

	}
}
