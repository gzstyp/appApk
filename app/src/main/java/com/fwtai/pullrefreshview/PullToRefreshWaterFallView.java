package com.fwtai.pullrefreshview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.widget.GridView;
import com.fwtai.pullrefresh.PullToRefreshAdapterViewBase;
import com.yinlz.cdc.R;

public class PullToRefreshWaterFallView extends PullToRefreshAdapterViewBase<GridView> {

    class InternalGridView extends GridView implements EmptyViewMethodAccessor {

        public InternalGridView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        public void setEmptyView(View emptyView) {
            PullToRefreshWaterFallView.this.setEmptyView(emptyView);
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

    public PullToRefreshWaterFallView(Context context) {
        super(context);
    }

    public PullToRefreshWaterFallView(Context context, int mode) {
        super(context, mode);
    }

    public PullToRefreshWaterFallView(Context context, AttributeSet attrs) {
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