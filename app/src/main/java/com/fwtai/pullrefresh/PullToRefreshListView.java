package com.fwtai.pullrefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.widget.ListView;
import com.fwtai.pullrefreshview.EmptyViewMethodAccessor;

/**
 * 下拉加载更多,上拉重置重新获取数据。
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2015年11月3日 21:13:31
 * @QQ号码 444141300
 * @官网 http://www.fwtai.com
*/
public final class PullToRefreshListView extends PullToRefreshAdapterViewBase<ListView> {

	class InternalListView extends ListView implements EmptyViewMethodAccessor {

		public InternalListView(Context context, AttributeSet attrs) {
			super(context, attrs);
		}

		@Override
		public void setEmptyView(View emptyView) {
			PullToRefreshListView.this.setEmptyView(emptyView);
		}

		@Override
		public void setEmptyViewInternal(View emptyView) {
			super.setEmptyView(emptyView);
		}

		public ContextMenuInfo getContextMenuInfo() {
			return super.getContextMenuInfo();
		}
	}

	public PullToRefreshListView(Context context) {
		super(context);
		this.setDisableScrollingWhileRefreshing(false);
	}

	public PullToRefreshListView(Context context, int mode) {
		super(context, mode);
		this.setDisableScrollingWhileRefreshing(false);
	}

	public PullToRefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setDisableScrollingWhileRefreshing(false);
	}

	@Override
	public ContextMenuInfo getContextMenuInfo() {
		return ((InternalListView) getRefreshableView()).getContextMenuInfo();
	}

	@Override
	protected final ListView createRefreshableView(Context context, AttributeSet attrs) {
		ListView lv = new InternalListView(context, attrs);
		lv.setId(android.R.id.list);
		return lv;
	}
}