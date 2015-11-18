package com.example.demo1;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
public class MainActivity extends Activity{
	

	CustomDrawableView mCustomDrawableView;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//removes app name in title bar
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		mCustomDrawableView = new CustomDrawableView(this);
		mCustomDrawableView.setBackgroundColor(Color.WHITE);
		setContentView(mCustomDrawableView);
		
/*	    DisplayMetrics metrics = this.getResources().getDisplayMetrics();
	    int width = metrics.widthPixels;
	    int height = metrics.heightPixels;*/

		
//		DisplayMetrics metrics = new DisplayMetrics();
//		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		
/*		//this gets pixel size but im not sure how it works on phone with touch navigation bar
	    Display display = getWindowManager().getDefaultDisplay();
	    Point size = new Point();
	    display.getSize(size);
	    int width = size.x;
	    int height = size.y;*/
	    
	    
			
	}		
	
	
	
}
	

