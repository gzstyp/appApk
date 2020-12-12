package com.fwtai.pullrefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;
import com.yinlz.cdc.R;

public final class PullToRefreshScrollView extends PullToRefreshBase<ScrollView> {

	private final OnRefreshListener defaultOnRefreshListener = new OnRefreshListener() {
		@Override
		public void onRefresh() {
			onRefreshComplete();
		}
	};


	public PullToRefreshScrollView(Context context) {
		super(context);
		setOnRefreshListener(defaultOnRefreshListener);
	}

	public PullToRefreshScrollView(Context context, int mode) {
		super(context, mode);
		setOnRefreshListener(defaultOnRefreshListener);
	}

	public PullToRefreshScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setOnRefreshListener(defaultOnRefreshListener);
	}

	@Override
	protected ScrollView createRefreshableView(Context context, AttributeSet attrs) {
		ScrollView scrollView = new ScrollView(context, attrs);
		scrollView.setId(R.id.webview);
		return scrollView;
	}

	@Override
	protected boolean isReadyForPullDown() {
		return refreshableView.getScrollY() == 0;
	}

	@Override
	protected boolean isReadyForPullUp() {
		ScrollView view = getRefreshableView();
		int off=view.getScrollY()+view.getHeight()-view.getChildAt(0).getHeight();
		if(off==0){
			return true;
		}else{
			return false;
		}
	}
}