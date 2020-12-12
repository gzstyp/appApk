package com.fwtai.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.fwtai.activity.BaseActivity;
import com.fwtai.interfaces.TitleOnClickListener;
import com.fwtai.widget.TextViewCollapsed;
import com.fwtai.widget.TextViewMore;
import com.fwtai.widget.TitleBar;
import com.yinlz.cdc.R;

/**
 * 收起|全文
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2017年3月21日 17:05:39
 * @QQ号码 444141300
 * @官网 http://www.fwtai.com
*/
public class UITextView extends BaseActivity implements OnClickListener{

	private Activity activity = this;
	private TitleBar titleBar;
	private TextViewCollapsed tv;
	private TextViewMore mtv_text;
	
	@Override
	protected void onCreate(final Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.layout_textview);
		TitleBar.initSystemBar(this);
		initView();
	}

	private void initView(){
		titleBar = (TitleBar) findViewById(R.id.titlebar_textview);
		titleBar.setTitle("文本收起|全文的应用");
		titleBar.setLeftClickListener(new TitleOnClickListener(){
			@Override
			public void onClick(View view){
				finish();
			}
		});
		titleBar.setRightText("输入框");
		titleBar.setRightClickListener(new TitleOnClickListener(){
			@Override
			public void onClick(View view){
				startActivity(new Intent(activity,UIEditText.class));
			}
		});
		
		tv = (TextViewCollapsed) findViewById(R.id.ctv_view);
		tv.setShowText("因为公司项目需要全文收起的功能，一共有2种UI所以需要写2个全文收起的控件，之前也是用过一个全文收起的控件，但是因为设计原因，在ListView刷新的时候会闪烁，我估计原因是因为控件本身的设计是需要先让TextView绘制完成，然后获取TextView一共有多少行，再判断是否需要全文收起按钮，如果需要，则吧TextView压缩回最大行数，添加全文按钮，这样就会造成ListView的Item先高后低，所以会发生闪烁，后面我也在网上找了几个，发现和之前的设计都差不多，虽然肯定是有解决了这个问题的控件，但是还是决定自己写了，毕竟找到控件后还需要测试，而现在的项目时间不充分啊（另外欢迎指教如何快速的找到自己需要的控件，有时候在Github上面搜索，都不知道具体该用什么关键字），而且自己写，也是一种锻炼。");
		mtv_text = (TextViewMore) findViewById(R.id.mtv_text);
		mtv_text.setText("因为公司项目需要全文收起的功能，一共有2种UI，所以需要写2个全文收起的控件，之前也是用过一个全文收起的控件，但是因为设计原因，在ListView刷新的时候会闪烁，我估计原因是因为控件本身的设计是需要先让TextView绘制完成，然后获取TextView一共有多少行，再判断是否需要全文收起按钮，如果需要，则吧TextView压缩回最大行数，添加全文按钮，这样就会造成ListView的Item先高后低，所以会发生闪烁，后面我也在网上找了几个，发现和之前的设计都差不多，虽然肯定是有解决了这个问题的控件，但是还是决定自己写了，毕竟找到控件后还需要测试，而现在的项目时间不充分啊（另外欢迎指教如何快速的找到自己需要的控件，有时候在Github上面搜索，都不知道具体该用什么关键字），而且自己写，也是一种锻炼。");
	}

	@Override
	public void onClick(View view){
	}
}