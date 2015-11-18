package com.example.demo1;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.view.KeyEvent;

public class keyboard extends Activity 
implements OnKeyboardActionListener, OnKeyListener {

	public keyboard() {

	}

	@Override
	public boolean onKey(DialogInterface arg0, int arg1, KeyEvent arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onKey(int arg0, int[] arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPress(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRelease(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onText(CharSequence arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void swipeDown() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void swipeLeft() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void swipeRight() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void swipeUp() {
		// TODO Auto-generated method stub
		
	}

}
