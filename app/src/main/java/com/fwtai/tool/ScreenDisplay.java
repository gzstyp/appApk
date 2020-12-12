package com.fwtai.tool;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * 获取手持设备屏幕分辨率
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2015年3月18日 12:59:27
 * @QQ号码 444141300
 * @Email 444141300@qq.com
 * @官网 http://www.yinlz.com
*/
public final class ScreenDisplay{

	private static ScreenDisplay instance = null;
	
	private ScreenDisplay(){}
	
	/**懒汉式单例模式*/
	public final static ScreenDisplay getInstance(){
		if (instance == null)
			instance = new ScreenDisplay();
		return instance;
	}
	
	/**
	 * 获取当前手持设备的屏幕分辨率
	 * @param activity
	 * @return HashMap《String,Integer》,key=dpi密度,width宽度,height高度
	 * @作者 田应平
	 * @返回值类型 HashMap<String,Integer>
	 * @创建时间 2015-3-18 下午1:37:33 
	 * @QQ号码 444141300
	 * @官网 http://www.yinlz.com
	*/
	public final HashMap<String, Integer> getScreenDisplay(Activity activity){
		final HashMap<String,Integer> map = new LinkedHashMap<String, Integer>();
		final DisplayMetrics metric = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
		final int width = metric.widthPixels;  // 屏幕宽度（像素）
		final int height = metric.heightPixels;  // 屏幕高度（像素）
		final float density = metric.density;  // 屏幕密度（0.75 / 1.0 / 1.5）
		final int densityDpi = metric.densityDpi;  // 屏幕密度DPI（120 / 160 / 240）
		int dpi = 0 ;
	    
	    if((density >= 0.75 && density < 1.0 ) || (densityDpi >= 120 && densityDpi < 160)){//小分辨率
	    	dpi = 1 ;
	    }else if ((density >= 1.0 && density < 1.5 ) || (densityDpi >= 160 && densityDpi < 240)) {//中分辨率
	    	dpi = 2 ;
		}else if (density >= 1.5 || densityDpi >= 240) {//高分辨率
			dpi = 3 ;
		}else { //未知分辨率
			dpi = 0 ;
		}
	    map.put("dpi",dpi);
	    map.put("width", width);
    	map.put("height", height);
    	return map;
	}
	
	/**
	 * 获得屏幕宽度
	 * @param activity
	*/
	public final int getScreenWidth(final Activity activity) {
		WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.widthPixels;
	}
	
	public final int getScreenWidth(final Context context) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.widthPixels;
	}


	/**
	 * 获得屏幕高度
	 * @param activity
	*/
	public final int getScreenHeight(final Activity activity) {
		WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.heightPixels;
	}
	
	/**
	 * px转dp--得到的是密度
	 * @param activity
	 * @作者 田应平
	 * @返回值类型 float
	 * @创建时间 2015-6-30 下午4:15:18 
	 * @QQ号码 444141300
	 * @官网 http://www.yinlz.com
	*/
	public final float pxTodp(Activity activity) {
		final float scale = activity.getResources().getDisplayMetrics().density;
		return scale;
	}

	/**
	 * 获得状态栏的高度
	 * @param context
	*/
	public int getStatusHeight(Context context) {
		int statusHeight = -1;
		try {
			Class<?> clazz = Class.forName("com.android.internal.R$dimen");
			Object object = clazz.newInstance();
			int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
			statusHeight = context.getResources().getDimensionPixelSize(height);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return statusHeight;
	}

	/**
	 * 获取当前屏幕截图，包含状态栏
	 * @param activity
	*/
	public final Bitmap snapShotWithStatusBar(Activity activity) {
		final View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		final Bitmap bmp = view.getDrawingCache();
		int width = getScreenWidth(activity);
		int height = getScreenHeight(activity);
		Bitmap bp = null;
		bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
		view.destroyDrawingCache();
		return bp;
	}

	/**
	 * 获取当前屏幕截图，不包含状态栏
	 * @param activity
	*/
	public final Bitmap snapShotWithoutStatusBar(Activity activity) {
		final View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bmp = view.getDrawingCache();
		Rect frame = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;
		int width = getScreenWidth(activity);
		int height = getScreenHeight(activity);
		Bitmap bp = null;
		bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height - statusBarHeight);
		view.destroyDrawingCache();
		return bp;
	}
	
	/**
	 * 不改变控件位置，修改控件大小
	 * @param v
	 * @param W
	 * @param H
	 * @作者 田应平
	 * @创建时间 2015-5-26 上午11:09:51 
	 * @QQ号码 444141300
	 * @官网 http://www.yinlz.com
	*/
	public final void changeWH(View v, int W, int H) {
		final LayoutParams params = (LayoutParams) v.getLayoutParams();
		params.width = W;
		params.height = H;
		v.setLayoutParams(params);
	}

	/**
	 * 修改控件的高
	 * @param v
	 * @param H
	 * @作者 田应平
	 * @创建时间 2015-5-26 上午11:09:56 
	 * @QQ号码 444141300
	 * @官网 http://www.yinlz.com
	*/
	public final void changeH(View v, int H) {
		final LayoutParams params = (LayoutParams) v.getLayoutParams();
		params.height = H;
		v.setLayoutParams(params);
	}
	
	/**
	 * 修改整个界面所有控件的字体
	 * @param root
	 * @param path
	 * @param act
	 * @作者 田应平
	 * @创建时间 2015年5月26日 11:11:44
	 * @QQ号码 444141300
	 * @官网 http://www.yinlz.com
	*/
	public final void changeFonts(ViewGroup root, String path, Activity act) {
		// path是字体路径
		final Typeface tf = Typeface.createFromAsset(act.getAssets(), path);
		for (int i = 0; i < root.getChildCount(); i++) {
			View v = root.getChildAt(i);
			if (v instanceof TextView) {
				((TextView) v).setTypeface(tf);
			} else if (v instanceof Button) {
				((Button) v).setTypeface(tf);
			} else if (v instanceof EditText) {
				((EditText) v).setTypeface(tf);
			} else if (v instanceof ViewGroup) {
				changeFonts((ViewGroup) v, path, act);
			}
		}
	}

	/**
	 * 修改整个界面所有控件的字体大小
	 * @param root
	 * @param size
	 * @param act
	 * @作者 田应平
	 * @创建时间 2015年5月26日 11:11:49 
	 * @QQ号码 444141300
	 * @官网 http://www.yinlz.com
	*/
	public final void changeTextSize(ViewGroup root, int size, Activity act) {
		for (int i = 0; i < root.getChildCount(); i++) {
			View v = root.getChildAt(i);
			if (v instanceof TextView) {
				((TextView) v).setTextSize(size);
			} else if (v instanceof Button) {
				((Button) v).setTextSize(size);
			} else if (v instanceof EditText) {
				((EditText) v).setTextSize(size);
			} else if (v instanceof ViewGroup) {
				changeTextSize((ViewGroup) v, size, act);
			}
		}
	}
	
	/**
	 * 根据view来生成bitmap图片，可用于截图功能
	 * @param view
	 * @作者 田应平
	 * @返回值类型 Bitmap
	 * @创建时间 2016-2-18 下午5:10:06 
	 * @QQ号码 444141300
	 * @官网 http://www.yinlz.com
	*/
	public final Bitmap getViewBitmap(View view) {
		view.clearFocus();
		view.setPressed(false);
		// 能画缓存就返回false
		boolean willNotCache = view.willNotCacheDrawing();
		view.setWillNotCacheDrawing(false);
		int color = view.getDrawingCacheBackgroundColor();
		view.setDrawingCacheBackgroundColor(0);
		if (color != 0) {
			view.destroyDrawingCache();
		}
		view.buildDrawingCache();
		Bitmap cacheBitmap = view.getDrawingCache();
		if (cacheBitmap == null) {
			return null;
		}
		Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);
		view.destroyDrawingCache();
		view.setWillNotCacheDrawing(willNotCache);
		view.setDrawingCacheBackgroundColor(color);
		return bitmap;
	}
	
	/**
	 * 截取webView可视区域的截图
	 * @注意 webView 前提：WebView要设置webView.setDrawingCacheEnabled(true);
	 * @param webView
	 * @作者 田应平
	 * @返回值类型 Bitmap
	 * @创建时间 2016-2-18 下午5:19:38 
	 * @QQ号码 444141300
	 * @官网 http://www.yinlz.com
	*/
	public final Bitmap captureWebViewVisibleSize(final WebView webView) {
		return webView.getDrawingCache();
	}
	
	/**
	 * 手机屏幕的快照
	 * @param activity
	 * @作者 田应平
	 * @返回值类型 Bitmap
	 * @创建时间 2016-2-18 下午5:29:47 
	 * @QQ号码 444141300
	 * @官网 http://www.yinlz.com
	*/
	public final Bitmap captureScreen(final Activity activity){
		final View cv = activity.getWindow().getDecorView();
	    Bitmap bmp = Bitmap.createBitmap(cv.getWidth(), cv.getHeight(),Config.ARGB_8888);
	    Canvas canvas = new Canvas(bmp);
	    cv.draw(canvas);
	    return bmp;
	}
	
	/**
	 * 获取屏幕分辨率
	 * @param context
	 * @return
	*/
	@SuppressWarnings("deprecation")
	public static int[] getScreenDispaly(final Context context) {
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		int width = windowManager.getDefaultDisplay().getWidth();// 手机屏幕的宽度
		int height = windowManager.getDefaultDisplay().getHeight();// 手机屏幕的高度
		int result[] = { width, height };
		return result;
	}
}