package com.fwtai.tool;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.fwtai.config.ConfigFile;

import java.net.URL;
import java.util.Locale;

/**
 * TextView特效-如：给文字一个动感动画
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2015年10月25日 04:07:24
 * @QQ号码 444141300
 * @官网 http://www.fwtai.com
 */
public final class ToolTextView extends TextView {

	private LinearGradient mLinearGradient;
	private Matrix mGradientMatrix;
	private Paint mPaint;
	private int mViewWidth = 0;
	private int mTranslate = 0;

	private boolean mAnimating = true;

	public ToolTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		if (mViewWidth == 0) {
			mViewWidth = getMeasuredWidth();
			if (mViewWidth > 0) {
				mPaint = getPaint();
				//第一个是前景色、第二个是渐变色、第三个是结束的颜色
				mLinearGradient = new LinearGradient(-mViewWidth, 0, 0, 0, new int[] { 0xffffffff, 0x7f080025, 0x7f0800a5}, new float[] { 0, 0.4f, 1}, Shader.TileMode.CLAMP);
				mPaint.setShader(mLinearGradient);
				mGradientMatrix = new Matrix();
			}
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (mAnimating && mGradientMatrix != null) {
			mTranslate += mViewWidth / 10;
			if (mTranslate > 2 * mViewWidth) {
				mTranslate = -mViewWidth;
			}
			mGradientMatrix.setTranslate(mTranslate, 0);
			mLinearGradient.setLocalMatrix(mGradientMatrix);
			postInvalidateDelayed(50);
		}
	}
	
	/**
	 * 指定颜色给TextView添加下划线效果,同时TextView的颜色将会和指定颜色一致
	 * @param tv 需要给TextView添加下划线效果的对象
	 * @param color 指定颜色,如：Color.rgb(79, 170, 240);或 R.color.xxx
	 * @作者 田应平
	 * @返回值类型 void
	 * @创建时间 2015年11月20日 10:40:51
	 * @QQ号码 444141300
	 * @官网 http://www.fwtai.com
	 */
	public final static void addUnderLine(final TextView tv,int color){
		tv.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG ); //下划线
		tv.getPaint().setAntiAlias(true);//抗锯齿
		tv.setTextColor(color);//添加颜色
	}
	
	/**
	 * 内容过长加省略号，点击显示全部内容或收缩隐藏
	 * @作者 田应平
	 * @param 参数button用来控制是否显示或隐藏
	 * @param 参数tv显示文件的控件
	 * @param 参数showLines要和xml文件的控件TextView属性maxLines的值要一致
	 * @创建时间 2016年10月22日 下午2:10:10 
	 * @QQ号码 444141300
	 * @主页 http://www.fwtai.com
	*/
	public static final void clickShowOrHide(final Button button,final TextView tv,final int showLines){
		button.setOnClickListener(new OnClickListener(){
			Boolean flag = true;
			@Override
			public void onClick(View v){
				if (flag){
					flag = false;
					tv.setEllipsize(null);//展开
					tv.setSingleLine(flag);
					button.setText("收缩隐藏");
				}else{
					flag = true;
					tv.setEllipsize(TextUtils.TruncateAt.valueOf("END"));//收缩
					tv.setLines(showLines);
					button.setText("显示全文");
				}
			}
		});
	}
	
	/**
	 * 内容过长加省略号，点击显示全部内容或收缩隐藏
	 * @作者 田应平
	 * @param 参数tvClick用来控制是否显示或隐藏
	 * @param 参数tv显示文件的控件
	 * @param 参数showLines要和xml文件的控件TextView属性maxLines的值要一致
	 * @创建时间 2016年10月22日 16:23:17
	 * @QQ号码 444141300
	 * @主页 http://www.fwtai.com
	*/
	public static final void clickShowOrHide(final TextView tvClick,final TextView tv,final int showLines){
		tvClick.setOnClickListener(new OnClickListener(){
			Boolean flag = true;
			@Override
			public void onClick(View v){
				if (flag){
					flag = false;
					tv.setEllipsize(null);//展开
					tv.setSingleLine(flag);
					tvClick.setText("收缩隐藏");
				}else{
					flag = true;
					tv.setEllipsize(TextUtils.TruncateAt.valueOf("END"));//收缩
					tv.setLines(showLines);
					tvClick.setText("显示全文");
				}
			}
		});
	}
	
	/**
	 * 内容过长加省略号，点击显示全部内容或收缩隐藏
	 * @作者 田应平
	 * @param 参数tv用来控制是否显示或隐藏
	 * @param 参数tv显示文件的控件
	 * @param 参数showLines要和xml文件的控件TextView属性maxLines的值要一致
	 * @创建时间 2016年10月22日 14:19:06
	 * @QQ号码 444141300
	 * @主页 http://www.fwtai.com
	*/
	public static final void clickShowOrHide(final TextView tv,final int showLines){
		tv.setOnClickListener(new OnClickListener(){
			Boolean flag = true;
			@Override
			public void onClick(View v){
				if (flag){
					flag = false;
					tv.setEllipsize(null);//展开
					tv.setSingleLine(flag);
				}else{
					flag = true;
					tv.setEllipsize(TextUtils.TruncateAt.valueOf("END"));//收缩
					tv.setLines(showLines);
				}
			}
		});
	}
	
	/**
	 * 把TextView处理成webview来使用
	 * @param tv 欲显示的TextView对象
	 * @param html 带有html的字符串
	 * @作者 田应平
	 * @创建时间 2016年11月13日 下午5:01:35
	 * @QQ号码 444141300
	 * @主页 http://www.fwtai.com
	*/
	public static final void TextViewSetHtml(final Activity activity,final TextView tv,String html){
		final ImageGetter imgGetter = new Html.ImageGetter(){
			public Drawable getDrawable(String source){
				Drawable drawable = null;
				URL url;
				String strUrl = "";
				try {
					if (source.toLowerCase(Locale.CHINESE).indexOf("http://") != -1){
						strUrl = source;
					}else {
						strUrl = ConfigFile.base_url + source;
					}
					url = new URL(strUrl);
					drawable = Drawable.createFromStream(url.openStream(), ""); // 获取网路图片
					if (drawable != null){
						// 图片高宽处理
						int height = drawable.getIntrinsicHeight();
						int width = drawable.getIntrinsicWidth();
						if (width < 100) {
							drawable.setBounds(0, 0, 50, 50);
						} else {
							double scale = (ScreenDisplay.getInstance().getScreenWidth(activity) - 40) / width;
							width = ScreenDisplay.getInstance().getScreenWidth(activity) - 40;
							height = (int) scale * height;
							drawable.setBounds(0, 0, width, height);
						}
					}
				} catch (Exception e) {
					return null;
				}
				return drawable;
			}
		};
		final CharSequence charSequence  = Html.fromHtml(html,imgGetter,null);
		tv.setText(charSequence);
		tv.setMovementMethod(ScrollingMovementMethod.getInstance());// 设置可滚动
	}
	
	public final static void setTvShow(final TextView tv,final Object value,final String defaultText){
		if(ToolString.isBlank(value)){
			tv.setText(defaultText);
		}else {
			tv.setText(String.valueOf(value));
		}
	}
}