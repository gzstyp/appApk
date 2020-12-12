package com.fwtai.tool;

import android.app.Activity;
import android.text.InputType;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.EditText;
import com.yinlz.cdc.R;

/**
 * EditText工具类
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2017年3月22日 上午8:46:39
 * @QQ号码 444141300
 * @主页 http://www.fwtai.com
*/
public final class ToolEditText {
	
	private final static ToolEditText instance = new ToolEditText();

	private ToolEditText(){}
	/**对外提供获取单例模式方法*/
	public final static ToolEditText getInstance(){
		return instance;
	}

	/**监听并改变背景样式*/
	public final void initFocusChange(final EditText editText){
		editText.setOnFocusChangeListener(new OnFocusChangeListener(){
			@Override
			public void onFocusChange(final View v,final boolean hasFocus){
				if (hasFocus){
					editText.setBackgroundResource(R.drawable.edit_view_press);
				} else {
					editText.setBackgroundResource(R.drawable.edit_view_normal);
				}
			}
		});
	}
	
	/**获取输入框的文本信息*/
	public final String getValue(final EditText editText){
		final String value = editText.getText().toString().trim();
		return ToolString.isBlank(value)?null:value;
	}
	
	/**获取输入框的文本信息*/
	public final String getValue(final Activity activity,final int editTextId){
		final String value = ((EditText)activity.findViewById(editTextId)).getText().toString().trim();
		return ToolString.isBlank(value)?null:value;
	}
	
	/**实例化EditText*/
	public final EditText init(final Activity activity,final int editTextId){
		return(EditText)activity.findViewById(editTextId);
	}
	
	/**实例化并初始化获取焦点及失去焦点的事件*/
	public final EditText initEditTextFocus(final Activity activity,final int editTextId){
		EditText editText = (EditText)activity.findViewById(editTextId);
		initFocusChange(editText);
		return editText;
	}
	
	public final void visibility(final EditText editText,final boolean visibility){
		if (visibility){
			editText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
		}else {
			editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
		}
	}
	
	/**
     * EditText强制获取焦点
     * @param editText
     * @作者 田应平
     * @创建时间 2015-3-15 下午2:23:58 
     * @QQ号码 444141300
     * @官网 http://www.fwtai.com
    */
    public final void setFocus(final EditText editText){
    	editText.requestFocus();// 获取焦点;
		editText.setFocusable(true);// 获取焦点;
		editText.setFocusableInTouchMode(true);// 获取焦点;
    }
    
    /**
     * EditText去除移除焦点
     * @param editText
     * @作者 田应平
     * @创建时间 2015-3-15 下午3:00:18 
     * @QQ号码 444141300
     * @官网 http://www.fwtai.com
    */
    public final void removeFocus(final EditText editText){
    	editText.clearFocus(); //失去焦点
		editText.setFocusable(false);
    }
	
	/**
	 * 判断某个layout或ViewGroup存在某个EditText控件-然后监听是否获取焦点
	 * @param group
	 * @作者 田应平
	 * @创建时间 2015-3-17 上午1:13:01 
	 * @QQ号码 444141300
	 * @官网 http://www.fwtai.com
	*/
	public final void findViewForEditText(final ViewGroup group){
		if (group != null) {
			for (int i = 0; i < group.getChildCount(); i++) {
				View child = group.getChildAt(i);
				if (child instanceof EditText) {
					EditText et = (EditText)child;
					initFocusChange(et);
				}else if(child instanceof ViewGroup){//判断下一个子节点
					ViewGroup vg = (ViewGroup)child;
					for (int j = 0; j < vg.getChildCount(); j++) {
						View v = vg.getChildAt(j);
						if(v instanceof EditText ){
							EditText e = (EditText)v;
							initFocusChange(e);
						}else if(v instanceof ViewGroup){//判断下一个子节点
							ViewGroup vgp = (ViewGroup)v;
							for (int k = 0; k < vgp.getChildCount(); k++) {
								View vw = vgp.getChildAt(k);
								if(vw instanceof EditText ){
									EditText ett = (EditText)vw;
									initFocusChange(ett);
								}
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * 判断某个layout或ViewGroup存在某个EditText控件
	 * @param group
	 * @作者 田应平
	 * @返回值类型 EditText
	 * @创建时间 2015-3-17 上午1:15:33 
	 * @QQ号码 444141300
	 * @官网 http://www.fwtai.com
	*/
	public final EditText findEditText(final ViewGroup group){
		if (group != null) {
			for (int i = 0, j = group.getChildCount(); i < j; i++) {
				final View child = group.getChildAt(i);
				if (child instanceof EditText) {
					EditText et = (EditText)child;
					initFocusChange(et);
					return et ;
				}else if (child instanceof ViewGroup){
					EditText et = findEditText((ViewGroup)child);
					initFocusChange(et);
					return et;
				}
			}
		}
		return null;
	}
	
	/**
     * 不可编辑状态
     * @作者 田应平
     * @QQ 444141300
     * @创建时间 2019/11/16 15:54
    */
    public final void disabled(final EditText editText){
        editText.setFocusableInTouchMode(false);//不可编辑
        editText.setKeyListener(null);//不可粘贴，长按不会弹出粘贴框
        editText.setClickable(false);//不可点击，但是这个效果我这边没体现出来，不知道怎没用
        editText.setFocusable(false);//不可编辑
        editText.setKeyListener(null);//不可粘贴，长按不会弹出粘贴框
        editText.setEnabled(false);
    }
}