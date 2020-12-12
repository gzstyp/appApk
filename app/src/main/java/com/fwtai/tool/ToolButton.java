package com.fwtai.tool;

import android.app.Activity;
import android.widget.Button;

/**
 * EditText工具类
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2017年3月22日 上午8:46:39
 * @QQ号码 444141300
 * @主页 http://www.fwtai.com
*/
public final class ToolButton {
	
	private final static ToolButton instance = new ToolButton();

	private ToolButton(){}
	/**对外提供获取单例模式方法*/
	public final static ToolButton getInstance(){
		return instance;
	}
	
	/**实例化Button*/
	public final Button init(final Activity activity,final int buttonId){
		return(Button)activity.findViewById(buttonId);
	}
}