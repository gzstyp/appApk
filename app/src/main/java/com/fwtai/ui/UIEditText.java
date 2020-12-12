package com.fwtai.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import com.fwtai.activity.BaseActivity;
import com.fwtai.interfaces.IViewTimer;
import com.fwtai.interfaces.TitleOnClickListener;
import com.fwtai.tool.ToolAnimation;
import com.fwtai.tool.ToolButton;
import com.fwtai.tool.ToolCheckBox;
import com.fwtai.tool.ToolEditText;
import com.fwtai.widget.HintDialog;
import com.fwtai.widget.TitleBar;
import com.fwtai.widget.ViewEvent;
import com.yinlz.cdc.R;

/**
 * 输入框应用
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2017年3月22日 09:18:09
 * @QQ号码 444141300
 * @官网 http://www.fwtai.com
*/
public class UIEditText extends BaseActivity{

	private Activity activity = this;
	private TitleBar titleBar;
	private ToolEditText toolEditText = null;
	private final HintDialog hintDialog = HintDialog.getInstance();
	
	EditText et3,et2;
	Button btn1;
	CheckBox checkBox;
	
	@Override
	protected void onCreate(final Bundle bundle){
		super.onCreate(bundle);
		setContentView(R.layout.layout_edittext);
		TitleBar.initSystemBar(this);
		toolEditText = ToolEditText.getInstance();
		initView();
	}

	private void initView(){
		titleBar = (TitleBar) findViewById(R.id.titlebar_edittext);
		titleBar.setTitle("输入框应用");
		titleBar.removeTitleRightView();
		titleBar.setLeftClickListener(new TitleOnClickListener(){
			@Override
			public void onClick(View view){
				finish();
			}
		});
		et3 = toolEditText.init(activity,R.id.et3);
		et2 = toolEditText.init(activity,R.id.et2);
		btn1 = ToolButton.getInstance().init(activity,R.id.btn1);
		new ViewEvent(btn1,2,new IViewTimer(){
			@Override
			public void viewClick(View view){
				String value = toolEditText.getValue(activity,R.id.et1);
				hintDialog.normal(activity,(value == null)?"空值":value);
			}
		});
		new ViewEvent(findViewById(R.id.btn2),5,new IViewTimer(){
			@Override
			public void viewClick(View view){
				ToolAnimation.getInstance().animation(activity,et2);
				toolEditText.setFocus(et2);
				boolean isChecked = checkBox.isChecked();
				hintDialog.normal(activity,isChecked?"可见":"隐藏");
			}
		});
		checkBox = (CheckBox) findViewById(R.id.edittext_checkBox);
		checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
				hintDialog.normal(activity,isChecked?"显示":"隐藏");
				toolEditText.visibility(et3,isChecked);
			}
		});
		final CheckBox cBox = ToolCheckBox.getInstance().init(activity,R.id.editview_cb);
		cBox.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
				hintDialog.normal(activity,isChecked?"选中":"取消");
			}
		});
	}
}