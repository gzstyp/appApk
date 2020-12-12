package com.fwtai.pullrefreshview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.yinlz.cdc.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 第一次进入页面显示的loading
*/
public final class EmptyLoadingLayout extends RelativeLayout {

	private TextView mTitle;
	private TextView mTime;

	public EmptyLoadingLayout(Context context) {
		this(context, null);
	}
	
	public EmptyLoadingLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	private void initView() {
		LayoutInflater.from(getContext()).inflate(R.layout.pull_to_refresh_empty_loading_layout, this);
		mTitle = (TextView) findViewById(R.id.empty_loading_title);
		mTime = (TextView) findViewById(R.id.empty_loading_time);
		StringBuilder sb = new StringBuilder();
		sb.append(getContext().getString(R.string.refresh_at)).append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINA).format(new Date()));
		mTime.setText(sb.toString());
	}

	/**
	 * @param title
	 */
	public void setEmptyTitle(final String title) {
		if (mTitle != null) mTitle.setText(title);
	}
	
	/**
	 * @param time
	 */
	public void setEmptyTime(final String time) {
		if (mTime != null)  mTime.setText(time);
	}

	public void showLayout() {
		final StringBuilder sb = new StringBuilder();
		sb.append(getContext().getString(R.string.refresh_at)).append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINA).format(new Date()));
		mTime.setText(sb.toString());
	}
}