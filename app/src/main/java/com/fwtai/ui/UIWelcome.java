package com.fwtai.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import com.fwtai.activity.BaseActivity;
import com.fwtai.config.LocalConfig;
import com.fwtai.tab.TabInit;
import com.yinlz.cdc.R;

/**
 * 引导页面
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2016年10月9日 18:06:58
 * @QQ号码 444141300
 * @官网 http://www.fwtai.com
*/
public final class UIWelcome extends BaseActivity{
	
	private Activity activity = this;

	@Override
	protected void onCreate(final Bundle bundle){
		super.onCreate(bundle);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.layout_welcome);
		//给View加载动画
		final View view = findViewById(R.id.layout_welcome_logo);
		final AlphaAnimation anim = new AlphaAnimation(0.1f,1.0f);//透明度变化
		anim.setDuration(2000);//延续或执行的时间
		//给对象View添加绑定动画效果
		view.startAnimation(anim);
		//给动画添加一个事件监听
		anim.setAnimationListener(animListener);
	}
	
	private final AnimationListener animListener = new AnimationListener(){
		@Override
		public void onAnimationStart(final Animation animation){}
		@Override
		public void onAnimationRepeat(final Animation animation){}
		@Override
		public void onAnimationEnd(final Animation animation){
			final String exist = LocalConfig.getInstance().getValue(activity,LocalConfig.setup);
			if (exist == null){
				into(UIGuide.class);
			}else{
				into(TabInit.class);
			}
		}
	};
	
	void into(final Class<?> cls) {
	    nextActivityAnim(cls);
	}
}