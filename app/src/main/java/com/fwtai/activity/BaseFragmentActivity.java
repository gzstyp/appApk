package com.fwtai.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import com.fwtai.config.App;
import com.fwtai.http.HttpCancel;
import com.fwtai.tool.ToolString;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 总页面总框架Fragment
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2015年9月11日 12:17:38
 * @QQ号码 444141300
 * @官网 http://www.fwtai.com
*/
public class BaseFragmentActivity extends FragmentActivity{

	protected String mClassName;

	public BaseFragmentActivity() {
		mClassName = getClass().getSimpleName();
	}

	@Override
	public void onAttachedToWindow() {
		super.onAttachedToWindow();
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		if (App.ACTIVITY_LIST == null)
			App.ACTIVITY_LIST = new ArrayList<Activity>();
		if (!App.ACTIVITY_LIST.contains(this)) {
			App.ACTIVITY_LIST.add(this);
		}
	}

	@Override
	public View onCreateView(String name, Context context, AttributeSet attrs) {
		return super.onCreateView(name, context, attrs);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
	}

	@Override
	protected void onRestart() {
		super.onRestart();
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onPostResume() {
		super.onPostResume();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		try {
			super.onRestoreInstanceState(savedInstanceState);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		savedInstanceState = null;
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onPause() {
		super.onPause();
		hideIM();
	}

	public void hideIM() {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm != null && getCurrentFocus() != null) {
			imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (App.ACTIVITY_LIST != null && App.ACTIVITY_LIST.contains(this)) {
			App.ACTIVITY_LIST.remove(this);
		}
	}

	@Override
	public void finish() {
		super.finish();
	}
	
	/**移除|取消全部的请求对象*/
	public void removeHttpCancel(final HashMap<String,HttpCancel> maps){
		if (maps != null && maps.size() > 0){
			for(String key : maps.keySet()){
				if(maps.get(key) != null){
					maps.get(key).cancel();
				}
			}
		}
	}
	
	/**移除指定请求对象集合的key*/
	public void removeHttpCancel(final HashMap<String,HttpCancel> maps,final String key){
		if (maps != null && maps.size() > 0){
			if(!ToolString.isBlank(key)){
				for(String url : maps.keySet()){
					if(url.equals(key)){
						if(maps.get(key) != null){
							maps.get(key).cancel();
						}
					}
				}
			}
		}
	}
}