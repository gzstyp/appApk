package com.fwtai.widget;

import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.fwtai.interfaces.IViewTimer;

/**
 * 自定义定时器-防止重复提交|操作
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2017年3月17日 15:24:35
 * @QQ号码 444141300
 * @主页 http://www.fwtai.com
 * @注意 如果想动态显示文字及毫秒数的话请用TextView或Button控件并通过showSecond来控制
 * @用法 new ViewEvent(view,5,null,null,false,new IViewTimer(){});
*/
public final class ViewEvent implements Runnable,View.OnClickListener{
	
	private int second = 3;
	private int value = 3;
	private Handler handler = new Handler();
	private TextView tv;
	private Button btn;
	private IViewTimer runnable;
	private String proceedMsg;
	private String completeMsg;
	private boolean showSecond;
	private View view;
	
	/**启用激活View点击事件*/
	public final static void setClickable(final View viewClickable){
		viewClickable.setClickable(true);
	}
	
	/**
	 * 可以防止提交|操作
	 * @param second 多少秒数值
	 * @param proceedMsg 执行中的显示提示信息,如:后重新获取验证码
	 * @param completeMsg 执行完成后显示的信息,如:重新获取验证码
	 * @param showSecond 是否显示 second 数值
	 * @用法 new ViewEvent(tv_time,10,"秒后重新获取验证码","重新获取验证码",true,new IViewTimer(){});
	 * @注意 如果不想改变TextView上的文字则推荐使用 new ViewEvent(findViewById(R.id.btn2),5,new IViewTimer(){});
	 * @作者 田应平
	 * @创建时间 2017年3月17日 下午1:16:49
	 * @QQ号码 444141300
	 * @主页 http://www.fwtai.com
	*/
	public ViewEvent(final TextView tv,final int second,final String proceedMsg,final String completeMsg,final boolean showSecond,final IViewTimer runnable){
		this.tv = tv;
		this.proceedMsg = proceedMsg;
		this.completeMsg = completeMsg;
		this.showSecond = showSecond;
		this.runnable = runnable;
		this.tv.setOnClickListener(this);
		this.second = second;
		this.value = second;
	}
	
	/**
	 * 可以防止提交|操作,仅支持Button???
	 * @param second 多少秒数值
	 * @param proceedMsg 执行中的显示提示信息,如:后重新获取验证码
	 * @param completeMsg 执行完成后显示的信息,如:重新获取验证码
	 * @param showSecond 是否显示 second 数值
	 * @用法 new ViewEvent(btn1,2,"启动","重试",false,new IViewTimer(){});
	 * @注意 如果不想改变Button上的文字则推荐使用 new ViewEvent(findViewById(R.id.btn2),5,new IViewTimer(){});
	 * @作者 田应平
	 * @创建时间 2017年3月17日 14:51:12
	 * @QQ号码 444141300
	 * @主页 http://www.fwtai.com
	*/
	public ViewEvent(final Button btn,final int second,final String proceedMsg,final String completeMsg,final boolean showSecond,final IViewTimer runnable){
		this.btn = btn;
		this.proceedMsg = proceedMsg;
		this.completeMsg = completeMsg;
		this.showSecond = showSecond;
		this.runnable = runnable;
		this.btn.setOnClickListener(this);
		this.second = second;
		this.value = second;
	}
	
	/**
	 * 可以防止提交|操作,如果不需要显示毫秒数及提示信息的则可以用 new ViewEvent(findViewById(R.id.btn2),5,new IViewTimer(){});
	 * @param view 指定的view事件控件
	 * @param second 多少秒数值
	 * @param proceedMsg 执行中的显示提示信息,如:后重新获取验证码
	 * @param completeMsg 执行完成后显示的信息,如:重新获取验证码
	 * @param showSecond 是否显示 second 数值
	 * @用法 new ViewEvent(findViewById(R.id.btn2),5,"启动","重试",false,new IViewTimer(){});
	 * @作者 田应平
	 * @创建时间 2017年3月17日 15:22:09
	 * @QQ号码 444141300
	 * @主页 http://www.fwtai.com
	*/
	public ViewEvent(final View view,final int second,final String proceedMsg,final String completeMsg,final boolean showSecond,final IViewTimer runnable){
		this.view = view;
		this.proceedMsg = proceedMsg;
		this.completeMsg = completeMsg;
		this.showSecond = showSecond;
		this.runnable = runnable;
		this.view.setOnClickListener(this);
		this.second = second;
		this.value = second;
	}
	
	/**
	 * 可以防止提交|操作
	 * @param view 指定的view事件控件
	 * @param second 多少秒数值
	 * @用法 new ViewEvent(findViewById(R.id.btn2),5,new IViewTimer(){});
	 * @作者 田应平
	 * @创建时间 2017年3月17日 15:22:09
	 * @QQ号码 444141300
	 * @主页 http://www.fwtai.com
	*/
	public ViewEvent(final View view,final int second,final IViewTimer runnable){
		this.view = view;
		this.runnable = runnable;
		this.view.setOnClickListener(this);
		this.second = second;
		this.value = second;
	}
	
	@Override
	public void run(){
		handler.postDelayed(this,1000);
		final int current = second--;
		if (tv != null){
			if (showSecond){
				tv.setText(current + proceedMsg);
			}else {
				tv.setText(proceedMsg);
			}
		}else if(btn != null){
			if (showSecond){
				btn.setText(current + proceedMsg);
			}else {
				btn.setText(proceedMsg);
			}
		}
		if(second < 0){
			second = value;
			handler.removeCallbacks(this);
			if (tv != null) {
				tv.setClickable(true);
				tv.setText(completeMsg);
			}else if(btn != null){
				btn.setClickable(true);
				btn.setText(completeMsg);
			}else if (view != null){
				view.setClickable(true);
			}
		}
	}

	@Override
	public void onClick(final View v){
		handler.post(this);
		if (tv != null) {
			tv.setClickable(false);
			runnable.viewClick(tv);
		}else if(btn != null){
			btn.setClickable(false);
			runnable.viewClick(btn);
		}else if(view != null){
			view.setClickable(false);
			runnable.viewClick(view);
		}
	}
}