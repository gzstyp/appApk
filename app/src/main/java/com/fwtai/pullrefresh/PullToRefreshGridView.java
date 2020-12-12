package com.fwtai.pullrefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.widget.GridView;
import com.fwtai.pullrefreshview.EmptyViewMethodAccessor;
import com.yinlz.cdc.R;

/**
 * 下拉刷新控件
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2016年2月24日 18:20:27
 * @QQ号码 444141300
 * @官网 http://www.fwtai.com
*/
public final class PullToRefreshGridView extends PullToRefreshAdapterViewBase<GridView> {
	class InternalGridView extends GridView implements EmptyViewMethodAccessor {
		public InternalGridView(Context context, AttributeSet attrs) {
			super(context, attrs);
		}

		@Override
		public void setEmptyView(View emptyView) {
			PullToRefreshGridView.this.setEmptyView(emptyView);
		}

		@Override
		public void setEmptyViewInternal(View emptyView) {
			super.setEmptyView(emptyView);
		}

		@Override
		public ContextMenuInfo getContextMenuInfo() {
			return super.getContextMenuInfo();
		}
	}

	public PullToRefreshGridView(Context context) {
		super(context);
	}

	public PullToRefreshGridView(Context context, int mode) {
		super(context, mode);
	}

	public PullToRefreshGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected final GridView createRefreshableView(Context context, AttributeSet attrs) {
		GridView gv = new InternalGridView(context, attrs);
		gv.setId(R.id.gridview);
		return gv;
	}

	@Override
	public ContextMenuInfo getContextMenuInfo() {
		return ((InternalGridView) getRefreshableView()).getContextMenuInfo();
	}
}
