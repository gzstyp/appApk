package com.fwtai.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.fwtai.tool.ToolString;
import com.yinlz.cdc.R;

/**
 * 消息提示
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2015年9月11日 10:01:28
 * @QQ号码 444141300
 * @官网 http://www.fwtai.com
*/
public final class HintDialog {
	
	/**单例模式*/
	private HintDialog(){}
	
	private final static HintDialog instance = new HintDialog();

	public final static HintDialog getInstance(){
		return instance;
	}
	
	/**
	 * 错误提示
	 * @param activity 当前的Activity或getActivity()
	 * @param msg 消息字符串
	 * @作者 田应平 
	 * @创建时间 2014年7月11日 20:22:50 
	 * @QQ号码 444141300 
	 * @主页 www.fwtai.com
	*/
	public final void error(final Activity activity,final Object msg){
		if (ToolString.isBlank(activity))return;
		final Toast toast = new Toast(activity);
		final View view = LayoutInflater.from(activity).inflate(R.layout.dialog_hint,null,false);
		final TextView text = (TextView)view.findViewById(R.id.text);
		final ImageView image = (ImageView)view.findViewById(R.id.image);
        image.setImageResource(R.drawable.icon_error);
        text.setText(ToolString.isBlank(msg)?"系统提示":msg.toString());
        text.setTextSize(16);//字体大小
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0,200);
        toast.setView(view);
        toast.show();
	}
	
	/**
	 * 错误提示
	 * @param activity 当前的Activity或getActivity()
	 * @param msg 消息字符串
	 * @作者 田应平 
	 * @创建时间 2014年7月11日 20:22:50 
	 * @QQ号码 444141300 
	 * @主页 www.fwtai.com
	*/
	public final void error(final Context context,final Object msg){
		if (ToolString.isBlank(context))return;
		final Toast toast = new Toast(context);
		final View view = LayoutInflater.from(context).inflate(R.layout.dialog_hint,null,false);
		final TextView text = (TextView)view.findViewById(R.id.text);
		final ImageView image = (ImageView)view.findViewById(R.id.image);
        image.setImageResource(R.drawable.icon_error);
        text.setText(ToolString.isBlank(msg)?"系统提示":msg.toString());
        text.setTextSize(16);//字体大小
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0,200);
        toast.setView(view);
        toast.show();
	}

	/**
	 * OK提示
	 * @param Activity 上下文
	 * @param msg 消息字符串
	 * @作者 田应平 
	 * @创建时间 2014年7月11日 20:22:44
	 * @QQ号码 444141300 
	 * @主页 www.fwtai.com
	*/
	public final void ok(final Activity activity,final Object msg){
		if (ToolString.isBlank(activity))return;
		final Toast toast = new Toast(activity);
		final View view = LayoutInflater.from(activity).inflate(R.layout.dialog_hint,null,false);
		final TextView text = (TextView)view.findViewById(R.id.text);
		final ImageView image = (ImageView)view.findViewById(R.id.image);
        image.setImageResource(R.drawable.icon_ok);
        text.setTextColor(Color.rgb(00,00,00));//字体颜色
        //text.setBackgroundResource(R.drawable.finish);
        text.setText(ToolString.isBlank(msg)?"系统提示":msg.toString());
        text.setTextSize(16);//字体大小
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 200);
        toast.setView(view);
        toast.show();
	}
	
	/**
	 * OK提示
	 * @param Activity 上下文
	 * @param msg 消息字符串
	 * @作者 田应平 
	 * @创建时间 2014年7月11日 20:22:44
	 * @QQ号码 444141300 
	 * @主页 www.fwtai.com
	*/
	public final void ok(final Context context,final Object msg){
		if (ToolString.isBlank(context))return;
		final Toast toast = new Toast(context);
		final View view = LayoutInflater.from(context).inflate(R.layout.dialog_hint,null,false);
		final TextView text = (TextView)view.findViewById(R.id.text);
		final ImageView image = (ImageView)view.findViewById(R.id.image);
        image.setImageResource(R.drawable.icon_ok);
        text.setTextColor(Color.rgb(00,00,00));//字体颜色
        //text.setBackgroundResource(R.drawable.finish);
        text.setText(ToolString.isBlank(msg)?"系统提示":msg.toString());
        text.setTextSize(16);//字体大小
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 200);
        toast.setView(view);
        toast.show();
	}
	
	/**
	 * 友好提示
	 * @param context 上下文
	 * @param msg 消息字符串
	 * @作者 田应平 
	 * @创建时间 2014年7月11日 20:22:50 
	 * @QQ号码 444141300 
	 * @主页 www.fwtai.com
	*/
	public final void tips(final Activity activity,final Object msg){
		if (ToolString.isBlank(activity))return;
		final Toast toast = new Toast(activity);
		final View view = LayoutInflater.from(activity).inflate(R.layout.dialog_hint,null,false);
		final TextView text = (TextView)view.findViewById(R.id.text);
		final ImageView image = (ImageView)view.findViewById(R.id.image);
        image.setImageResource(R.drawable.icon_tips);
        text.setTextColor(Color.rgb(00,00,00));//字体颜色
        text.setText(ToolString.isBlank(msg)?"系统提示":msg.toString());
        text.setTextSize(16);//字体大小
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 200);
        toast.setView(view);
        toast.show();
	}
	
	/**
	 * 友好提示
	 * @param context 上下文
	 * @param msg 消息字符串
	 * @作者 田应平 
	 * @创建时间 2014年7月11日 20:22:50 
	 * @QQ号码 444141300 
	 * @主页 www.fwtai.com
	*/
	public final void tips(final Context context,final Object msg){
		if (ToolString.isBlank(context))return;
		final Toast toast = new Toast(context);
		final View view = LayoutInflater.from(context).inflate(R.layout.dialog_hint,null,false);
		final TextView text = (TextView)view.findViewById(R.id.text);
		final ImageView image = (ImageView)view.findViewById(R.id.image);
        image.setImageResource(R.drawable.icon_tips);
        text.setTextColor(Color.rgb(00,00,00));//字体颜色
        text.setText(ToolString.isBlank(msg)?"系统提示":msg.toString());
        text.setTextSize(16);//字体大小
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 200);
        toast.setView(view);
        toast.show();
	}
	
	/**
	 * 没有图标,黑色字体的提示
	 * @param activity 当前的Activity或getActivity()
	 * @param msg 消息字符串
	 * @作者 田应平 
	 * @创建时间 2015年9月11日 09:52:42
	 * @QQ号码 444141300 
	 * @主页 www.fwtai.com
	 */
	public final void normal(final Activity activity,final Object msg){
		if (ToolString.isBlank(activity))return;
		final Toast toast = new Toast(activity);
		final View view = LayoutInflater.from(activity).inflate(R.layout.dialog_hint,null,false);
		final TextView text = (TextView)view.findViewById(R.id.text);
		final ImageView image = (ImageView)view.findViewById(R.id.image);
	    image.setVisibility(View.GONE);//隐藏图标
        text.setTextColor(Color.rgb(00,00,00));//字体颜色
        text.setText(ToolString.isBlank(msg)?"系统提示":msg.toString());
        text.setTextSize(16);//字体大小
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER,0,200);
        toast.setView(view);
        toast.show();
	}
	
	/**
	 * 没有图标,黑色字体的提示
	 * @param activity 当前的Activity或getActivity()
	 * @param msg 消息字符串
	 * @作者 田应平 
	 * @创建时间 2015年9月11日 09:52:42
	 * @QQ号码 444141300 
	 * @主页 www.fwtai.com
	 */
	public final void normal(final Context context,final Object msg){
		if(ToolString.isBlank(context))return;
		final Toast toast = new Toast(context);
		final View view = LayoutInflater.from(context).inflate(R.layout.dialog_hint,null,false);
		final TextView text = (TextView)view.findViewById(R.id.text);
		final ImageView image = (ImageView)view.findViewById(R.id.image);
	    image.setVisibility(View.GONE);//隐藏图标
        text.setTextColor(Color.rgb(00,00,00));//字体颜色
        text.setText(ToolString.isBlank(msg)?"系统提示":msg.toString());
        text.setTextSize(16);//字体大小
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER,0,200);
        toast.setView(view);
        toast.show();
	}
	
	/**
	 * 没有图标,主题颜色字体的提示
	 * @param activity 当前的Activity或getActivity()
	 * @param msg 消息字符串
	 * @作者 田应平 
	 * @创建时间 2015年9月11日 09:52:42
	 * @QQ号码 444141300 
	 * @主页 www.fwtai.com
	 */
	public final void theme(final Activity activity,final Object msg){
		if (ToolString.isBlank(activity))return;
		final Toast toast = new Toast(activity);
		final View view = LayoutInflater.from(activity).inflate(R.layout.dialog_hint,null,false);
		final TextView text = (TextView)view.findViewById(R.id.text);
		final ImageView image = (ImageView)view.findViewById(R.id.image);
	    image.setVisibility(View.GONE);//隐藏图标
        text.setTextColor(Color.rgb(50,205,246));//自定义主题颜色
        text.setText(ToolString.isBlank(msg)?"系统提示":msg.toString());
        text.setTextSize(16);//字体大小
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER,0,200);
        toast.setView(view);
        toast.show();
	}
	
	/**
	 * 没有图标,主题颜色字体的提示
	 * @param activity 当前的Activity或getActivity()
	 * @param msg 消息字符串
	 * @作者 田应平 
	 * @创建时间 2015年9月11日 09:52:42
	 * @QQ号码 444141300 
	 * @主页 www.fwtai.com
	 */
	public final void theme(final Context context,final Object msg){
		if(ToolString.isBlank(context))return;
		final Toast toast = new Toast(context);
		final View view = LayoutInflater.from(context).inflate(R.layout.dialog_hint,null,false);
		final TextView text = (TextView)view.findViewById(R.id.text);
		final ImageView image = (ImageView)view.findViewById(R.id.image);
	    image.setVisibility(View.GONE);//隐藏图标
        text.setTextColor(Color.rgb(50,205,246));//自定义主题颜色
        text.setText(ToolString.isBlank(msg)?"系统提示":msg.toString());
        text.setTextSize(16);//字体大小
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER,0,200);
        toast.setView(view);
        toast.show();
	}
	
	/**
	 * 注意在 new Threan(new Runnable(){}).start();子线程里,调用会报错!!
	 * @param context
	 * @param msg
	 * @作者 田应平
	 * @返回值类型 Dialog
	 * @创建时间 2015年4月3日 19:01:08
	 * @QQ号码 444141300
	 * @官网 http://www.fwtai.com
	*/
	public final Dialog createLoading(final Context context,final String msg){
		final LayoutInflater inflater = LayoutInflater.from(context);
		final View v = inflater.inflate(R.layout.dialog_loading,null,false);// 得到加载view
		final LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
		// main.xml中的ImageView
		final ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);
		final TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字
		// 加载动画
		final Animation animation = AnimationUtils.loadAnimation(context, R.anim.loading);
		// 使用ImageView显示动画
		spaceshipImage.startAnimation(animation);
		tipTextView.setTextColor(Color.rgb(00,00,00));
		tipTextView.setText(ToolString.isBlank(msg)?"正在处理…":msg);// 设置加载信息
		final Dialog dialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog
		dialog.setCancelable(false);
		dialog.setContentView(layout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
		return dialog;
	}
	
	/**
	 * 注意在 new Thread(new Runnable(){}).start();子线程里,调用会报错!!
	 * @param context
	 * @param msg
	 * @用法 dialog = CustomToast.createLoadingDialog(ActivityLogin.this, "正在登录...",R.drawable.winnower);
	 * @param resId 自定义动画的图片
	 * @作者 田应平
	 * @返回值类型 Dialog
	 * @创建时间 2015年6月19日 00:36:55
	 * @QQ号码 444141300
	 * @官网 http://www.fwtai.com
	 */
	public final Dialog createLoading(final Context context,final String msg,int resId){
		final LayoutInflater inflater = LayoutInflater.from(context);
		final View v = inflater.inflate(R.layout.dialog_loading,null,false);// 得到加载view
		final LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
		// main.xml中的ImageView
		final ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);
		//setImageDrawable(drawable);
		//setImageBitmap(bm);
		//setImageResource(resId);
		spaceshipImage.setImageResource(R.drawable.loading_winnower);//加载动画的图片
		final TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字
		// 加载动画
		final Animation animation = AnimationUtils.loadAnimation(context, R.anim.loading);
		// 使用ImageView显示动画
		spaceshipImage.startAnimation(animation);
		tipTextView.setTextColor(Color.rgb(00,00,00));
		tipTextView.setText(ToolString.isBlank(msg)?"正在处理…":msg);// 设置加载信息
		final Dialog dialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog
		dialog.setCancelable(false);
		dialog.setContentView(layout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
		return dialog;
	}
}