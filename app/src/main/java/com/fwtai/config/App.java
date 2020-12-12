package com.fwtai.config;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统的全局变量
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2015-10-31 上午1:28:42
 * @QQ号码 444141300
 * @官网 http://www.fwtai.com
 */
public final class App extends Application {

	public static final int RELEASE = 0;
	public static final int DEBUG = 1;

	public static boolean ALARM_SOUND = true;
	public static boolean ALARM_VIBRATE = true;
	public static int screenWidth = 0;
	public static int screenHeight = 0;
	public static float screenDensity = 0;
	public static long ALARM_TIME;

	private static App mInstance = null;

	public static ArrayList<Activity> ACTIVITY_LIST;

	public String VERSION_NAME;
	public int VERSION_CODE = -1;

	public static App getInstance(){
		return mInstance;
	}

	@Override
	public void onCreate(){
		super.onCreate();
		mInstance = this;
		screenWidth = getResources().getDisplayMetrics().widthPixels;
		screenHeight = getResources().getDisplayMetrics().heightPixels;
		screenDensity = getResources().getDisplayMetrics().density;
		// 为了防止NoClassDefFoundError
		final AsyncTask<Object,Object,Object> task = new AsyncTask<Object, Object, Object>(){
			@Override
			protected Object doInBackground(Object... params){
				return null;
			}
		};
		task.execute();
		getVersionInfo();
		initEnvironment();
	}

	private void initEnvironment(){
		
	}

	private void getVersionInfo(){
		try {
			final PackageInfo pinfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_CONFIGURATIONS);
			VERSION_NAME = pinfo.versionName;
			VERSION_CODE = pinfo.versionCode;
		} catch (NameNotFoundException e){
			e.printStackTrace();
		}
	}
	
	/**判断本机是否已安装某应用的app软件*/
	public final static boolean isExistApp(final Context context,final String packageName){
		final PackageManager packageManager = context.getPackageManager();
		final List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
		for (int i = 0; i < pinfo.size(); i++){
			if (((PackageInfo) pinfo.get(i)).packageName.equalsIgnoreCase(packageName))
				return true;
		}
		return false;
	}
}