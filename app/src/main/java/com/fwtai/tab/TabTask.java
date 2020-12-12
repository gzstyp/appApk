package com.fwtai.tab;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import com.fwtai.activity.BaseFragment;
import com.fwtai.interfaces.TitleOnClickListener;
import com.fwtai.ui.UIButton;
import com.fwtai.widget.AlertDialog;
import com.fwtai.widget.TitleBar;
import com.gyf.immersionbar.ImmersionBar;
import com.yinlz.cdc.R;

/**
 * 任务
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2016年10月9日 18:08:00
 * @QQ号码 444141300
 * @官网 http://www.fwtai.com
*/
public final class TabTask extends BaseFragment{
	
	private Activity activity = null;
	
	private View mView;
	private TitleBar titleBar;
	
	@Override
	public View onCreateView(final LayoutInflater inflater,final ViewGroup container,final Bundle bundle) {
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarColor(R.color.base_blue).navigationBarColor(R.color.base_blue).barColor(R.color.base_blue).statusBarDarkFont(true).keyboardEnable(true).init();//更改本主题默认的状态栏颜色
		if (mView != null) {
			final ViewGroup parentView = (ViewGroup) mView.getParent();
			if (parentView != null) {
				parentView.removeViewAt(0);
			}
			return mView;
		}
		mView = inflater.inflate(R.layout.tab_layout_task,null,false);
		initView();
		activity = getActivity();
		initOnClick();
		return mView;
	}

	private final void initView(){
		titleBar = (TitleBar) mView.findViewById(R.id.titlebar_tab_task);
		titleBar.setTitle(getString(R.string.tab_flag_tv_task));
		titleBar.setRightText("按钮");
		titleBar.removeTitleLeftView();
		titleBar.setRightClickListener(new TitleOnClickListener(){
			@Override
			public void onClick(View view){
				startActivity(new Intent(activity,UIButton.class));
			}
		});
	}
	
	//初始化事件
	void initOnClick(){
		mView.findViewById(R.id.task_tv_click).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View v){
				new AlertDialog(activity,"最新任务","系统提示","知道了",false,null);
			}
		});
	}
}