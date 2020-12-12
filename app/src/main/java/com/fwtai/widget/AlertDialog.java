package com.fwtai.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.fwtai.tool.ScreenDisplay;
import com.fwtai.tool.ToolString;
import com.yinlz.cdc.R;

/**
 * 自定义对话框,包含一个内容体[有标题和无标题]和一个按钮或2个按钮<br />
 * 有标题:在new AlertDialog时设置setTitle(str);<br />
 * 无标题：在new AlertDialog时无setTitle(str);
 * @提示 一个按钮写法只有setPositiveButton或只有setNegativeButton<br />两个按钮就包含setPositiveButton和setNegativeButton方法.
 * @作者 田应平,App.mInstance
 * @版本 v1.0
 * @创建时间 2015-1-25 下午2:55:47
 * @QQ号码 444141300
 * @提示 可以设置是否Dialog点击屏幕外不消失->.setCancelable(false),如果是单个按钮不要.setCancelable(false)否则报错
 * @官网 http://www.yinlz.com
*/
public final class AlertDialog {

	private String dialogTitle = "系统提示";
	private Dialog dialog;
	private LinearLayout lLayout_bg;
	private TextView txt_title;
	private TextView txt_msg;
	private Button btn_neg;
	private Button btn_pos;
	private ImageView img_line;
	private boolean showTitle = false;
	private boolean showMsg = false;
	private boolean showPosBtn = false;
	private boolean showNegBtn = false;

	public AlertDialog(){}

    /**仅适用于提示框,用法:new AlertDialog().create(activity).alertNormal("操作成功");*/
    public AlertDialog create(final Context context){
        // 获取Dialog布局
        final View view = LayoutInflater.from(context).inflate(R.layout.dialog_alert,null,false);
        // 获取自定义Dialog布局中的控件
        lLayout_bg = (LinearLayout) view.findViewById(R.id.lLayout_bg);
        txt_title = (TextView) view.findViewById(R.id.txt_title);
        txt_title.setVisibility(View.GONE);
        txt_msg = (TextView) view.findViewById(R.id.txt_msg);
        btn_neg = (Button) view.findViewById(R.id.btn_neg);
        btn_neg.setVisibility(View.GONE);
        btn_pos = (Button) view.findViewById(R.id.btn_pos);
        btn_pos.setVisibility(View.GONE);
        view.findViewById(R.id.alert_line).setVisibility(View.INVISIBLE);
        img_line = (ImageView) view.findViewById(R.id.img_line);
        img_line.setVisibility(View.GONE);
        // 定义Dialog布局和参数
        dialog = new Dialog(context,R.style.AlertDialogStyle);
        dialog.setContentView(view);
        // 调整dialog背景大小
        lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams((int)(ScreenDisplay.getInstance().getScreenWidth(context) * 0.80), LayoutParams.WRAP_CONTENT));
        txt_msg.setVisibility(View.VISIBLE);
        txt_msg.setGravity(Gravity.CENTER);
        txt_msg.setSingleLine(true);
        //dialog.getWindow().setGravity(Gravity.TOP);
        dialog.getWindow().setDimAmount(0f);
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener(){
            @Override
            public boolean onKey(final DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode== KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
                    return false;
                }else{
                    return false;
                }
            }
        });
        return this;
    }

    public void alertNormal(final String msg){
        alertShow(msg,1);
    }

    public void alertSucceed(final String msg){
        alertShow(msg,2);
    }

    public void alertError(final String msg){
        alertShow(msg,3);
    }

    /**提示颜色:1正常;2成功;3警告;*/
    private void alertShow(final String msg,final int type){
        if(dialog != null && dialog.isShowing()){
            dialog.dismiss();
        }
        switch (type){
            case 1:
                txt_msg.setTextColor(Color.rgb(0,0,0));
                break;
            case 2:
                txt_msg.setTextColor(Color.rgb(0,148,255));
                break;
            case 3:
                txt_msg.setTextColor(Color.rgb(255,0,0));
                break;
            default:
                txt_msg.setTextColor(Color.rgb(0,0,0));
                break;
        }
        txt_msg.setText(msg);
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        },900);
    }
	
	public AlertDialog builder(final Activity activity){
		// 获取Dialog布局
		final View view = LayoutInflater.from(activity).inflate(R.layout.dialog_alert,null,false);
		// 获取自定义Dialog布局中的控件
		lLayout_bg = (LinearLayout) view.findViewById(R.id.lLayout_bg);
		txt_title = (TextView) view.findViewById(R.id.txt_title);
		txt_title.setVisibility(View.GONE);
		txt_msg = (TextView) view.findViewById(R.id.txt_msg);
		txt_msg.setVisibility(View.GONE);
		txt_msg.setTextColor(Color.rgb(52,126,206));
		btn_neg = (Button) view.findViewById(R.id.btn_neg);
		btn_neg.setVisibility(View.GONE);
		btn_pos = (Button) view.findViewById(R.id.btn_pos);
		btn_pos.setVisibility(View.GONE);
		img_line = (ImageView) view.findViewById(R.id.img_line);
		img_line.setVisibility(View.GONE);
		// 定义Dialog布局和参数
		dialog = new Dialog(activity,R.style.AlertDialogStyle);
		dialog.setContentView(view);
		// 调整dialog背景大小
		lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams((int)(ScreenDisplay.getInstance().getScreenWidth(activity)* 0.85), LayoutParams.WRAP_CONTENT));
		return this;
	}
	
	public AlertDialog builder(final Context context){
		// 获取Dialog布局
		final View view = LayoutInflater.from(context).inflate(R.layout.dialog_alert,null,false);
		// 获取自定义Dialog布局中的控件
		lLayout_bg = (LinearLayout) view.findViewById(R.id.lLayout_bg);
		txt_title = (TextView) view.findViewById(R.id.txt_title);
		txt_title.setVisibility(View.GONE);
		txt_msg = (TextView) view.findViewById(R.id.txt_msg);
		txt_msg.setVisibility(View.GONE);
		txt_msg.setTextColor(Color.rgb(52,126,206));
		btn_neg = (Button) view.findViewById(R.id.btn_neg);
		btn_neg.setVisibility(View.GONE);
		btn_pos = (Button) view.findViewById(R.id.btn_pos);
		btn_pos.setVisibility(View.GONE);
		img_line = (ImageView) view.findViewById(R.id.img_line);
		img_line.setVisibility(View.GONE);
		// 定义Dialog布局和参数
		dialog = new Dialog(context,R.style.AlertDialogStyle);
		dialog.setContentView(view);
		// 调整dialog背景大小
		lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams((int)(ScreenDisplay.getInstance().getScreenWidth(context)* 0.85), LayoutParams.WRAP_CONTENT));
		return this;
	}

	public AlertDialog setTitle(final String title){
		showTitle = true;
		if ("".equals(title)){
			txt_title.setText("系统提示");
		} else {
			txt_title.setText(title);
		}
		return this;
	}

	public AlertDialog setMsg(final String msg){
		showMsg = true;
		if (TextUtils.isEmpty(msg)){
			txt_msg.setText("警告");
		} else {
			txt_msg.setText(msg);
		}
		return this;
	}

	public AlertDialog setCancelable(final boolean cancel){
		dialog.setCancelable(cancel);
		return this;
	}

	public AlertDialog setPositiveButton(final String text,final OnClickListener listener){
		showPosBtn = true;
		if (text == null || "".equals(text)){
			btn_pos.setText("确定");
		} else {
			btn_pos.setText(text);
		}
		btn_pos.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				if(listener != null){
					listener.onClick(v);
				}
				dialog.dismiss();
			}
		});
		return this;
	}
	
	/**
	 * 按钮
	 * @param text
	 * @param listener
	 * @作者 田应平
	 * @返回值类型 DialogAlertAction
	 * @创建时间 2015-1-25 下午2:57:16 
	 * @QQ号码 444141300
	 * @官网 http://www.yinlz.com
	*/
	public AlertDialog setNegativeButton(final String text, final OnClickListener listener){
		showNegBtn = true;
		if (text == null || "".equals(text)){
			btn_neg.setText("取消");
		} else {
			btn_neg.setText(text);
		}
		btn_neg.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				if (listener != null){
					listener.onClick(v);
				}
				dialog.dismiss();
			}
		});
		return this;
	}

	private void setLayout(){
		if (!showTitle && !showMsg){
			txt_title.setText("提示");
			txt_title.setVisibility(View.VISIBLE);
		}
		if (showTitle){
			txt_title.setVisibility(View.VISIBLE);
		}
		if (showMsg){
			txt_msg.setVisibility(View.VISIBLE);
		}
		if (!showPosBtn && !showNegBtn){
			btn_pos.setText("确定");
			btn_pos.setVisibility(View.VISIBLE);
			btn_pos.setBackgroundResource(R.drawable.alert_single_selector);
			btn_pos.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v){
					dialog.dismiss();
				}
			});
		}
		if (showPosBtn && showNegBtn){
			btn_pos.setVisibility(View.VISIBLE);
			btn_pos.setBackgroundResource(R.drawable.alert_right_selector);
			btn_neg.setVisibility(View.VISIBLE);
			btn_neg.setBackgroundResource(R.drawable.alert_left_selector);
			img_line.setVisibility(View.VISIBLE);
		}
		if (showPosBtn && !showNegBtn){
			btn_pos.setVisibility(View.VISIBLE);
			btn_pos.setBackgroundResource(R.drawable.alert_single_selector);
		}
		if (!showPosBtn && showNegBtn){
			btn_neg.setVisibility(View.VISIBLE);
			btn_neg.setBackgroundResource(R.drawable.alert_single_selector);
		}
	}
	
	public void show(){
		setLayout();
		dialog.show();
	}
	
	/**
	 * 单个按钮-带标题+内容+按钮-用于只做提示或确认用,如果点击没有事件则OnClickListener为null
	 * @param activity
	 * @param title 标题
	 * @param contentMsg 提示的内容信息
	 * @param btnMsg 按钮的文字
	 * @param cancelable 是否Dialog点击屏幕外不消失,false则点击屏幕外边不消失,默认是true也就是或可以点击屏幕外边消失
	 * @用法 new AlertDialog(activity,"系统提示","对话框单个按钮带标题有事件","朕知道了",false,new OnClickListener(){});
	 * @作者 田应平
	 * @创建时间 2015年7月4日 18:22:14
	 * @QQ号码 444141300
	 * @官网 http://www.yinlz.com
	*/
	public AlertDialog(final Activity activity,final Object contentMsg,final String title,final String btnMsg,final boolean cancelable,final OnClickListener listener){
		new AlertDialog().builder(activity).setTitle(title).setCancelable(cancelable).setMsg(ToolString.isBlank(contentMsg)?"^_^":contentMsg.toString()).setPositiveButton(btnMsg,listener).show();
	}
	
	/**
	 * 单个按钮-没标题+内容+按钮-用于只做提示确认用,如果点击没有事件则OnClickListener为null
	 * @param activity 当前的Activity
	 * @param contentMsg 提示的内容信息
	 * @param btnMsg 按钮的文字
	 * @param cancelable 是否Dialog点击屏幕外不消失,false则点击屏幕外边不消失,默认是true也就是或可以点击屏幕外边消失
	 * @用法 new AlertDialog(activity,"对话框单个按钮没标题","好的",false,null);
	 * @作者 田应平
	 * @创建时间 2015年6月10日 15:24:12
	 * @QQ号码 444141300
	 * @官网 http://www.yinlz.com
	*/
	public AlertDialog(final Activity activity,final Object contentMsg,final String btnMsg,final boolean cancelable,final OnClickListener listener){
		new AlertDialog().builder(activity).setMsg(ToolString.isBlank(contentMsg)?"^_^":contentMsg.toString()).setCancelable(cancelable).setPositiveButton(btnMsg,listener).show();
	}
	
	/**单个按钮-标题为系统提示+内容+按钮-用于只做提示确认用,有点击事件*/
	public AlertDialog(final Context context,final Object contentMsg,final String btnMsg,final OnClickListener listener){
		new AlertDialog().builder(context).setTitle(dialogTitle).setMsg(ToolString.isBlank(contentMsg)?"^_^":contentMsg.toString()).setCancelable(false).setPositiveButton(btnMsg,listener).show();
	}
	
	/**单个按钮-无标题+内容+按钮-用于只做提示确认用,有点击事件*/
	public AlertDialog(final Activity activity,final Object contentMsg,final String btnMsg,final OnClickListener listener){
		new AlertDialog().builder(activity).setMsg(ToolString.isBlank(contentMsg)?"^_^":contentMsg.toString()).setCancelable(false).setPositiveButton(btnMsg,listener).show();
	}
	
	/**
	 * 两个按钮且标题为'系统提示',当点击取消时没有事件的则可以为 null
	 * @param activity 当前的Activity
	 * @param contentMsg 提示的内容信息
	 * @param cancelable 是否Dialog点击屏幕外不消失,false则点击屏幕外边不消失,默认是true也就是或可以点击屏幕外边消失
	 * @param listenerOk 确定要处理的事件
	 * @param listenerCancel 取消的事件,如不做任何处理则为 null
	 * @作者 田应平
	 * @创建时间 2017年3月24日 下午1:06:10
	 * @QQ号码 444141300
	 * @主页 http://www.yinlz.com
	*/
	public AlertDialog(final Activity activity,final Object contentMsg,final boolean cancelable,final OnClickListener listenerOk,final OnClickListener listenerCancel){
		new AlertDialog().builder(activity).setCancelable(cancelable).setTitle(dialogTitle).setMsg(ToolString.isBlank(contentMsg)?"^_^":contentMsg.toString()).setPositiveButton("确定",listenerOk).setNegativeButton("取消",listenerCancel).show();
	}
	
	/**
	 * 两个按钮且标题为'系统提示',当点击取消时没有事件的则可以为 null
	 * @param activity 当前的Activity
	 * @param contentMsg 提示的内容信息
	 * @param btnOk 确定按钮上的文字
	 * @param btnCancel 取消按钮上的文字
	 * @param cancelable 是否Dialog点击屏幕外不消失,false则点击屏幕外边不消失,默认是true也就是或可以点击屏幕外边消失
	 * @param listenerOk 确定要处理的事件
	 * @param listenerCancel 取消的事件,如不做任何处理则为 null
	 * @作者 田应平
	 * @创建时间 2017年3月24日 下午1:10:54
	 * @QQ号码 444141300
	 * @主页 http://www.yinlz.com
	*/
	public AlertDialog(final Activity activity,final Object contentMsg,final String btnOk,final String btnCancel,final boolean cancelable,final OnClickListener listenerOk,final OnClickListener listenerCancel){
		new AlertDialog().builder(activity).setCancelable(cancelable).setTitle(dialogTitle).setMsg(ToolString.isBlank(contentMsg)?"^_^":contentMsg.toString()).setPositiveButton(btnOk,listenerOk).setNegativeButton(btnCancel,listenerCancel).show();
	}
	
	/**
	 * 两个按钮,完全自定义的对话框;当点击取消时没有事件的则可以为 null
	 * @param activity 当前的Activity
	 * @param contentMsg 提示的内容信息
	 * @param btnOk 确定按钮上的文字
	 * @param btnCancel 取消按钮上的文字
	 * @param title 对话框的标题
	 * @param cancelable 是否Dialog点击屏幕外不消失,false则点击屏幕外边不消失,默认是true也就是或可以点击屏幕外边消失
	 * @param listenerOk 确定要处理的事件
	 * @param listenerCancel 取消的事件,如不做任何处理则为 null
	 * @作者 田应平
	 * @创建时间 2017年3月24日 13:34:09
	 * @QQ号码 444141300
	 * @主页 http://www.yinlz.com
	*/
	public AlertDialog(final Activity activity,final Object contentMsg,final String btnOk,final String btnCancel,final String title,final boolean cancelable,final OnClickListener listenerOk,final OnClickListener listenerCancel){
		new AlertDialog().builder(activity).setCancelable(cancelable).setTitle(title).setMsg(ToolString.isBlank(contentMsg)?"^_^":contentMsg.toString()).setPositiveButton(btnOk,listenerOk).setNegativeButton(btnCancel,listenerCancel).show();
	}
}