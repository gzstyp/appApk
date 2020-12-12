package com.fwtai.widget;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout.LayoutParams;
import com.fwtai.tool.ScreenDisplay;
import com.fwtai.tool.ToolAnimation;
import com.yinlz.cdc.R;

import java.util.HashMap;

/**
 * 通用自定义Dialog组件，Dialog默认所在屏幕的比例大小是85%<br/>
 * 只需传递当前的Activity,加载xml布局文件的R.layout.Xxx
 * @作者 田应平,App.mInstance
 * @创建时间 2014-11-16 下午6:17:55
 * @QQ号码 444141300
 * @提示 根据业务可以设置是否Dialog点击屏幕外不消失
 * @主页 http://www.fwtai.com
*/
public final class BaseDialog extends Dialog{

	/**传递来的R.layout.xxx布局文件生成dialog的View*/
	private View layoutView;
	/**设置Dialog所在屏幕的比例大小0.1f到1f*/
	private float diaphaneity = -0.01f ;
	/**dialog的宽高度*/
	private HashMap<String, Integer> mapDisplay ;
	/**当前的Activity*/
	private Activity activity;

	/**
	 * 私有构造方法
	 * @param activityCurrent
	 * @param theme 主题
	 * @param r_layout_id R.layout.xx布局文件,注意根节点必须是LinearLayout,不然无法控制dialog所占屏幕的比例大小
	 * @param diaphaneity dialog所占屏幕的比例大小的值 0.85f
	 * @作者 田应平
	 * @创建时间 2015年4月1日 16:08:56
	 * @QQ号码 444141300
	 * @官网 http://www.fwtai.com
	*/
	private BaseDialog(final Activity activity,final int theme,final int r_layout_id,final float diaphaneity) {
		super(activity,theme);
		this.activity = activity ;
		this.diaphaneity = diaphaneity ;
		layoutView = LayoutInflater.from(activity).inflate(r_layout_id,null,false);
	}
	
	@Override
	protected void onCreate(final Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		this.setContentView(layoutView);
		mapDisplay = ScreenDisplay.getInstance().getScreenDisplay(activity);
		if(diaphaneity > 0 && diaphaneity <= 1){
			layoutView.setLayoutParams(new FrameLayout.LayoutParams((int) (mapDisplay.get("width") * diaphaneity),LayoutParams.WRAP_CONTENT));
		}else{
			layoutView.setLayoutParams(new FrameLayout.LayoutParams((int) (mapDisplay.get("width") * 0.85), LayoutParams.WRAP_CONTENT));
		}
		ToolAnimation.getInstance().animation(activity,layoutView,R.anim.bottom_to_top);
	}

	/**
	 * 私有构造方法对外提供API访问接口
	 * @作者 田应平
	 * @param r_layout_id 载入的布局文件的id,如R.layout.xxx
	 * @param diaphaneity 设置Dialog所在屏幕的比例大小0.1f到1f
	 * @param cancelable 设置是否Dialog点击屏幕外或返回键不消失
	 * @创建时间 2017年3月24日 下午6:03:12
	 * @QQ号码 444141300
	 * @主页 http://www.fwtai.com
	*/
	public static final BaseDialog create(final Activity activity,final int r_layout_id,final float diaphaneity,final boolean cancelable){
		final BaseDialog dialog = new BaseDialog(activity,R.style.dialog_view_style,r_layout_id,diaphaneity);
		dialog.setCancelable(cancelable);
		dialog.show();
		return dialog;
	}
	
	/**
	 * 私有构造方法对外提供API访问接口,默认点击屏幕外或返回键不消失
	 * @作者 田应平
	 * @param r_layout_id 载入的布局文件的id,如R.layout.xxx
	 * @param diaphaneity 设置Dialog所在屏幕的比例大小0.1f到1f
	 * @创建时间 2017年3月24日 下午6:03:12
	 * @QQ号码 444141300
	 * @主页 http://www.fwtai.com
	*/
	public static final BaseDialog create(final Activity activity,final int r_layout_id,final float diaphaneity){
		final BaseDialog dialog = new BaseDialog(activity,R.style.dialog_view_style,r_layout_id,diaphaneity);
		dialog.setCancelable(false);
		dialog.show();
		return dialog;
	}
	
	public View getCustomView(){
		return layoutView;
	}
}