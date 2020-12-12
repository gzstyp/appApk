package com.fwtai.tool;

import android.app.Activity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import com.yinlz.cdc.R;

/**
 * 为控件 View添加提示动画工具类
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2015年3月15日 13:34:39
 * @QQ号码 444141300
 * @Email 444141300@qq.com
 * @官网 http://www.fwtai.com
*/
public final class ToolAnimation{

	private static ToolAnimation instance = null;

	private ToolAnimation(){}
	/**对外提供获取单例模式方法*/
	public final static ToolAnimation getInstance(){
		if (instance == null){
			instance = new ToolAnimation();
		}
		return instance;
	}
	
	/**
	 * 为控件 View添加提示动画(抖动)
	 * @param activity
	 * @param view
	 * @作者 田应平
	 * @创建时间 2015-3-15 下午1:30:11 
	 * @QQ号码 444141300
	 * @官网 http://www.fwtai.com
	 */
	public final void animation(final Activity activity,final View view){
		final Animation anim = AnimationUtils.loadAnimation(activity,R.anim.view_shake);
		view.startAnimation(anim);
		anim.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(final Animation animation) {
			}
			@Override
			public void onAnimationRepeat(final Animation animation) {
			}
			@Override
			public void onAnimationEnd(final Animation animation) {
			}
		});
	}
	
	/**
	 * 为控件 View添加提示动画(抖动)
	 * @param activity
	 * @param view
	 * @param r_anim_id 动画的id。如 R.anim.animation_dialog
	 * @作者 田应平
	 * @创建时间 2015-3-15 下午1:28:48 
	 * @QQ号码 444141300
	 * @官网 http://www.fwtai.com
	*/
	public final void animation(final Activity activity,final View view,final int r_anim_id){
		final Animation anim = AnimationUtils.loadAnimation(activity, r_anim_id);
		view.startAnimation(anim);
		anim.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}
			@Override
			public void onAnimationRepeat(Animation animation) {
			}
			@Override
			public void onAnimationEnd(Animation animation) {
			}
		});
	}
}