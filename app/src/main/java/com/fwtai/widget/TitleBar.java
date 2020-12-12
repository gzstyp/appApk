package com.fwtai.widget;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.fwtai.interfaces.TitleOnClickListener;
import com.yinlz.cdc.R;

/**
 * 自定义标题栏
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2015年9月9日 10:52:57
 * @QQ号码 444141300
 * @官网 http://www.fwtai.com
*/
public final class TitleBar extends RelativeLayout {
	
	private TitleOnClickListener mRightListener;
	private TitleOnClickListener mLeftListener;
	private TitleOnClickListener mTitleListener;

	public TitleBar(Context context){
		this(context, null);
	}
	
	public TitleBar(final Context context,final AttributeSet attrs){
		super(context, attrs);
		initView();
	}

	private final void initView(){
		LayoutInflater.from(getContext()).inflate(R.layout.widget_title_bar, this);
	}
	
	public final void setRightVisibility(final int visibility){
		findViewById(R.id.title_right).setVisibility(visibility);
	}

	public final void setLeftVisibility(final int visibility){
		findViewById(R.id.title_left).setVisibility(visibility);
	}
	
	/**标题*/
	public final void setTitle(String title){
		if(TextUtils.isEmpty(title)) title = "";
		((TextView) findViewById(R.id.title_title)).setText(title);
	}
	
	/**设置左边的文字*/
	public final void setLeftText(final String leftText){
		if(TextUtils.isEmpty(leftText)) return;
		((TextView) findViewById(R.id.title_left_tv)).setText(leftText);
		findViewById(R.id.title_left_tv).setVisibility(View.VISIBLE);
		findViewById(R.id.title_left_iv).setVisibility(View.GONE);
		
	}
	
	/**设置右边的文字*/
	public final void setRightText(final String rightText){
		if(TextUtils.isEmpty(rightText)) return;
		((TextView) findViewById(R.id.title_right_tv)).setText(rightText);
		findViewById(R.id.title_right_tv).setVisibility(View.VISIBLE);
		findViewById(R.id.title_right_iv).setVisibility(View.GONE);
	}

	public final void setRightBackgrounpDrawable(final int drawableId){
		if(drawableId==0) return;
		((TextView) findViewById(R.id.title_right_tv)).setBackgroundResource(drawableId);
		findViewById(R.id.title_right_tv).setVisibility(View.VISIBLE);
		findViewById(R.id.title_right_iv).setVisibility(View.GONE);
	}
	
	public final void setRightPadding(int left,int top,int right,int bottom){
		((TextView) findViewById(R.id.title_right_tv)).setPadding(left, top, right, bottom);
	}

	/**
	 * 设置右边View的属性
	 * @作者 田应平
	 * @创建时间 2016年11月14日 下午2:43:18
	 * @QQ号码 444141300
	 * @主页 http://www.yinlz.com
	*/
	public final void setRightMargins(int left,int top,int right,int bottom){
		final RelativeLayout lay = (RelativeLayout) findViewById(R.id.title_right);
		final RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams)lay.getLayoutParams(); 
		lp.setMargins(left, top, right, bottom);
		lay.setLayoutParams(lp);
		((RelativeLayout)findViewById(R.id.item_title_bar_root)).removeView(findViewById(R.id.title_right_iv));
	}
	
	public final void setRightIcon(int id){
		findViewById(R.id.title_right_iv).setBackgroundResource(id);
		findViewById(R.id.title_right_tv).setVisibility(View.GONE);
		findViewById(R.id.title_right_iv).setVisibility(View.VISIBLE);
	}
	
	public final void setLeftIcon(int id){
		findViewById(R.id.title_left_iv).setBackgroundResource(id);
	}
	
	public final void setTitleBackground(int id){
		findViewById(R.id.title_title).setBackgroundResource(id);
	}
	
	/**
	 * 删除左边和右边的View控件
	 * @作者 田应平
	 * @创建时间 2016年11月14日 下午3:01:05
	 * @QQ号码 444141300
	 * @主页 http://www.yinlz.com
	*/
	public final void removeBorderView(){
		((RelativeLayout)findViewById(R.id.item_title_bar_root)).removeView(findViewById(R.id.title_left));
		((RelativeLayout)findViewById(R.id.item_title_bar_root)).removeView(findViewById(R.id.title_right));
		findViewById(R.id.title_title).setPadding(0,0,0,0);
	}
	
	/**
	 * 移除标题栏左边的view,注意先后顺序,即先初始化后再调用
	 * @作者 田应平
	 * @返回值类型 void
	 * @创建时间 2016年3月31日 17:18:51
	 * @QQ号码 444141300
	 * @官网 http://www.yinlz.com
	 */
	public final void removeTitleLeftView(){
		((RelativeLayout)findViewById(R.id.item_title_bar_root)).removeView(findViewById(R.id.title_left));
	}
	
	/**
	 * 移除标题栏右边的view,注意先后顺序,即先初始化后再调用
	 * @作者 田应平
	 * @返回值类型 void
	 * @创建时间 2016年3月31日 17:18:36
	 * @QQ号码 444141300
	 * @官网 http://www.yinlz.com
	*/
	public final void removeTitleRightView(){
		((RelativeLayout)findViewById(R.id.item_title_bar_root)).removeView(findViewById(R.id.title_right));
	}
	
	public final void setLeftClickListener(final TitleOnClickListener l){
		if(l == null) return;
		findViewById(R.id.title_left).setOnClickListener(listener);
		mLeftListener = l;
	}

	public final void setRightClickListener(final TitleOnClickListener l){
		if(l == null) return;
		findViewById(R.id.title_right).setVisibility(View.VISIBLE);
		findViewById(R.id.title_right).setOnClickListener(listener);
		mRightListener = l;
	}
	
	public final void setTitleClickListener(final TitleOnClickListener l){
		if(l == null) return;
		findViewById(R.id.title_title).setOnClickListener(listener);
		mTitleListener = l;
	}
	
	private OnClickListener listener = new OnClickListener(){
		@Override
		public void onClick(final View v){
			switch(v.getId()){
			case R.id.title_left:
				if(mLeftListener != null) mLeftListener.onClick(v);
				break;
			case R.id.title_right:
				if(mRightListener != null) mRightListener.onClick(v);
				break;
			case R.id.title_title:
				if(mRightListener != null) mTitleListener.onClick(v);
				break;
			}
		}
	};

	/**改变状态栏的颜色使其与APP风格一体化
	 * @param activity
	 * @作者 田应平
	 * @返回值类型 void
	 * @创建时间 2015年11月16日 09:07:26
	 * @QQ号码 444141300
	 * @官网 http://www.yinlz.com
	*/
	public final static void initSystemBar(final Activity activity){
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
			setTranslucentStatus(activity, true);
		}
		final WindowStyle tintManager = new WindowStyle(activity);
		tintManager.setStatusBarTintEnabled(true);
		// 使用颜色资源
		tintManager.setStatusBarTintResource(R.color.view_top_layout_bg);
	}
	
	@TargetApi(19)
	private final static void setTranslucentStatus(final Activity activity,final boolean on){
		final Window win = activity.getWindow();
		final WindowManager.LayoutParams winParams = win.getAttributes();
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		if (on){
			winParams.flags |= bits;
		} else {
			winParams.flags &= ~bits;
		}
		win.setAttributes(winParams);
	}
	
	/**
	 * 默认样式
	 * @param activity
	 * @作者 田应平
	 * @返回值类型 void
	 * @创建时间 2015年11月20日 10:23:48
	 * @QQ号码 444141300
	 * @官网 http://www.yinlz.com
	*/
	public final static void defaultStyle(final Activity activity){
		final WindowStyle tintManager = new WindowStyle(activity);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setNavigationBarTintEnabled(true);
	}
	
	/**
	 * 指定颜色作为标题的风格化颜色
	 * @param activity
	 * @param color 指定颜色值
	 * @作者 田应平
	 * @返回值类型 void
	 * @创建时间 2016年11月8日 11:23:35
	 * @QQ号码 444141300
	 * @官网 http://www.yinlz.com
	*/
	public final static void initSystemBar(final Activity activity,int r_color_name){
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
			setTranslucentStatus(activity, true);
		}
		final WindowStyle tintManager = new WindowStyle(activity);
		tintManager.setStatusBarTintEnabled(true);
		// 使用颜色资源
		tintManager.setStatusBarTintResource(r_color_name);
	}
}