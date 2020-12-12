package com.fwtai.tool;

import android.app.Activity;
import android.widget.CheckBox;

/**
 * EditText工具类
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2017年3月22日 上午8:46:39
 * @QQ号码 444141300
 * @主页 http://www.fwtai.com
*/
public final class ToolCheckBox{
	
	private final static ToolCheckBox instance = new ToolCheckBox();

	private ToolCheckBox(){}
	/**对外提供获取单例模式方法*/
	public final static ToolCheckBox getInstance(){
		return instance;
	}

	/**实例化CheckBox*/
	public final CheckBox init(final Activity activity,final int editTextId){
		return(CheckBox)activity.findViewById(editTextId);
	}
	
	/**获取输入框的文本信息*/
	public final boolean isChecked(final CheckBox checkBox){
		return checkBox.isChecked();
	}
}