package com.fwtai.ui;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import com.fwtai.activity.BaseActivity;
import com.yinlz.cdc.R;

import java.util.Timer;
import java.util.TimerTask;

public final class UILaunch extends BaseActivity{

	private TextView tv_jump_over;
	
	private int recLen = 5;//跳过倒计时提示5秒
    final Timer timer = new Timer();

	@Override
	protected void onCreate(final Bundle bundle){
		super.onCreate(bundle);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.layout_launch);
		tv_jump_over = findViewById(R.id.tv_jump_over);
		timer.schedule(task,1000,1000);//时间一秒停顿时间一秒
		tv_jump_over.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(final View view) {
				if (timer != null) {
                    timer.cancel();
                }
				jump();
			}
		});
	}
	
	final TimerTask task = new TimerTask(){
		@Override
		public void run() {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					recLen--;
					tv_jump_over.setText("跳过 " + recLen);
					if (recLen < 1) {
						timer.cancel();
						jump();
					}
				}
			});
		}
	};
	
	private void jump(){
		finish();
	}
}