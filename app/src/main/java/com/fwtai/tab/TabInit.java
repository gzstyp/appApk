package com.fwtai.tab;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TabHost;
import com.fwtai.activity.BaseFragment;
import com.fwtai.activity.FragmentExit;
import com.yinlz.cdc.R;

import java.util.HashMap;

/**
 * 进入tab页,默认是第0个页面
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2016年10月9日 18:06:10
 * @QQ号码 444141300
 * @官网 http://www.fwtai.com
 */
public final class TabInit extends FragmentExit implements OnClickListener {

    private static final String TAB = "tab";
    private static final String TAB_INDEX = "tab_index";//首页
    private static final String TAB_TASK = "tab_task";//任务
    private static final String TAB_RELEASE = "tab_release";//发布
    private static final String TAB_ORDER = "tab_order";//订单
    private static final String TAB_OWNER = "tab_owner";//我的

    private RefreshReceive mRefreshReceive;// 广播
    private int mLastTabIndex = -1;
    private TabHost mTabHost;
    private TabManager mTabManager;// 切换
    private final static HashMap<String,TabManager.TabInfo> mTabs = new HashMap<String,TabManager.TabInfo>();

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(final Context context,final Intent intent) {
        }
    };

    @Override
    protected void onCreate(final Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.layout_home);
        configMiddle(bundle);
        final IntentFilter intentFilter = new IntentFilter();
        registerReceiver(broadcastReceiver,intentFilter);
    }

    private void configMiddle(final Bundle bundle) {
        mTabHost = findViewById(android.R.id.tabhost);
        mTabHost.setup();
        initTabHost();
        initCurrentTab(bundle);
    }

    @Override
    protected void onStart() {
        super.onStart();
        final Intent intent = getIntent();
        final String function = intent.getStringExtra("function");
        if (!TextUtils.isEmpty(function)) {
            if (function.equals("mine")) {
                mTabHost.setCurrentTab(2);
            }
        }
    }

    private void initTabHost() {
        mTabManager = new TabManager(this, mTabHost, R.id.realtabcontent);
        final View tabBarView = LayoutInflater.from(this).inflate(R.layout.layout_tabs, null);

        final View viewIndex = tabBarView.findViewById(R.id.tab_rl_index);
        ((ViewGroup) viewIndex.getParent()).removeViewAt(0);
        mTabManager.addTab(mTabHost.newTabSpec(TAB_INDEX).setIndicator(viewIndex), TabIndex.class, null);//首页
        mTabHost.getTabWidget().getChildAt(0).setOnClickListener(this);

        final View viewTabTask = tabBarView.findViewById(R.id.tab_rl_task);
        ((ViewGroup) viewTabTask.getParent()).removeViewAt(0);
        mTabManager.addTab(mTabHost.newTabSpec(TAB_TASK).setIndicator(viewTabTask), TabTask.class, null);//任务
        mTabHost.getTabWidget().getChildAt(1).setOnClickListener(this);

        final View viewRelease = tabBarView.findViewById(R.id.tab_rl_release);
        ((ViewGroup) viewRelease.getParent()).removeViewAt(0);
        mTabManager.addTab(mTabHost.newTabSpec(TAB_RELEASE).setIndicator(viewRelease), TabRelease.class, null);//发布
        mTabHost.getTabWidget().getChildAt(2).setOnClickListener(this);

        final View viewOrder = tabBarView.findViewById(R.id.tab_rl_order);
        ((ViewGroup) viewOrder.getParent()).removeViewAt(0);
        mTabManager.addTab(mTabHost.newTabSpec(TAB_ORDER).setIndicator(viewOrder), TabOrder.class, null);//订单
        mTabHost.getTabWidget().getChildAt(2).setOnClickListener(this);

        final View viewOwner = tabBarView.findViewById(R.id.tab_rl_owner);
        ((ViewGroup) viewOwner.getParent()).removeViewAt(0);
        mTabManager.addTab(mTabHost.newTabSpec(TAB_OWNER).setIndicator(viewOwner), TabOwner.class, null);//我的
        mTabHost.getTabWidget().getChildAt(3).setOnClickListener(this);
    }

    public void initCurrentTab(final Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mTabHost.getTabWidget().getChildAt(savedInstanceState.getInt(TAB)).performClick();
        } else {
            String tab = getIntent().getStringExtra("tab_id");
            if (!TextUtils.isEmpty(tab)) {
                mTabHost.getTabWidget().getChildAt(Integer.valueOf(tab)).performClick();
            } else {
                mTabHost.getTabWidget().getChildAt(0).performClick();// 默认加载第1个
            }
        }
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.tab_rl_index:
                setBackgroundDrawable(0);
                mTabHost.setCurrentTab(0);
                mLastTabIndex = 0;
                break;
            case R.id.tab_rl_task:
                setBackgroundDrawable(1);
                mTabHost.setCurrentTab(1);
                mLastTabIndex = 1;
                break;
            case R.id.tab_rl_release:
                setBackgroundDrawable(2);
                mTabHost.setCurrentTab(2);
                mLastTabIndex = 2;
                break;
            case R.id.tab_rl_order:
                setBackgroundDrawable(3);
                mTabHost.setCurrentTab(3);
                mLastTabIndex = 3;
                break;
            case R.id.tab_rl_owner:
                setBackgroundDrawable(4);
                mTabHost.setCurrentTab(4);
                mLastTabIndex = 4;
                break;
        }
    }

    private void setBackgroundDrawable(final int index) {
        if (mLastTabIndex != -1) {
            mTabHost.getTabWidget().getChildAt(mLastTabIndex).setSelected(false);
        }
        mTabHost.getTabWidget().getChildAt(index).setSelected(true);
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(TAB, mTabHost.getCurrentTab());
    }

    @Override
    public void onBackPressed() {
        switch (mTabHost.getCurrentTab()) {
            case 0:
                if (((BaseFragment) mTabs.get(TAB_INDEX).fragment).onBackPressed()){//首页
                    return;
                }
                break;
            case 1:
                if (((BaseFragment) mTabs.get(TAB_TASK).fragment).onBackPressed()){//任务
                    return;
                }
                break;
            case 2:
                if (((BaseFragment) mTabs.get(TAB_RELEASE).fragment).onBackPressed()){//发布
                    return;
                }
                break;
            case 3:
                if (((BaseFragment) mTabs.get(TAB_ORDER).fragment).onBackPressed()){//订单
                    return;
                }
                break;
            case 4:
                if (((BaseFragment) mTabs.get(TAB_OWNER).fragment).onBackPressed()){//我的
                    return;
                }
                break;
        }
        super.onBackPressed();
    }

    @Override
    public void onActivityResult(final int requestCode,final int resultCode,final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (mTabHost.getCurrentTab()) {
            case 0:
                (mTabs.get(TAB_INDEX).fragment).onActivityResult(requestCode, resultCode, data);////首页
                break;
            case 1:
                (mTabs.get(TAB_TASK).fragment).onActivityResult(requestCode, resultCode, data);//任务
                break;
            case 2:
                (mTabs.get(TAB_RELEASE).fragment).onActivityResult(requestCode, resultCode, data);//发布
                break;
            case 3:
                (mTabs.get(TAB_ORDER).fragment).onActivityResult(requestCode, resultCode, data);//订单
                break;
            case 4:
                (mTabs.get(TAB_OWNER).fragment).onActivityResult(requestCode, resultCode, data);//我的
                break;
        }
    }

    @Override
    protected void onDestroy() {
        if (mRefreshReceive != null) {
            unregisterReceiver(mRefreshReceive);
        }
        super.onDestroy();
    }

    private class RefreshReceive extends BroadcastReceiver {
        @Override
        public void onReceive(final Context context,final Intent intent) {
        }
    }

    public static class TabManager implements TabHost.OnTabChangeListener {
        private final FragmentActivity mActivity;
        private final TabHost mTabHost;
        private final int mContainerId;
        private TabInfo mLastTab;
        static final class TabInfo {
            private final String tag;
            private final Class<?> clss;
            private final Bundle args;
            private Fragment fragment;
            TabInfo(final String _tag,final Class<?> _class,final Bundle _args) {
                tag = _tag;
                clss = _class;
                args = _args;
            }
        }
        static class DummyTabFactory implements TabHost.TabContentFactory {
            private final Context mContext;
            public DummyTabFactory(final Context context) {
                mContext = context;
            }
            @Override
            public View createTabContent(final String tag) {
                View v = new View(mContext);
                v.setMinimumWidth(0);
                v.setMinimumHeight(0);
                return v;
            }
        }

        public TabManager(final FragmentActivity activity,final TabHost tabHost,final int containerId) {
            mActivity = activity;
            mTabHost = tabHost;
            mContainerId = containerId;
            mTabHost.setOnTabChangedListener(this);
        }

        public void addTab(TabHost.TabSpec tabSpec,final Class<?> clss,final Bundle args) {
            tabSpec.setContent(new DummyTabFactory(mActivity));
            final String tag = tabSpec.getTag();
            final TabInfo info = new TabInfo(tag, clss, args);
            info.fragment = mActivity.getSupportFragmentManager().findFragmentByTag(tag);
            if (info.fragment != null && !info.fragment.isDetached()) {
                final FragmentTransaction ft = mActivity.getSupportFragmentManager().beginTransaction();
                ft.detach(info.fragment);
                ft.commit();
            }
            mTabs.put(tag, info);
            mTabHost.addTab(tabSpec);
        }

        @Override
        public void onTabChanged(final String tabId) {
            final TabInfo newTab = mTabs.get(tabId);
            if (mLastTab != newTab) {
                final FragmentTransaction ft = mActivity.getSupportFragmentManager().beginTransaction();
                if (mLastTab != null) {
                    if (mLastTab.fragment != null) {
                        ft.detach(mLastTab.fragment);
                    }
                }
                if (newTab != null) {
                    if (newTab.fragment == null) {
                        newTab.fragment = Fragment.instantiate(mActivity, newTab.clss.getName(), newTab.args);
                        ft.add(mContainerId, newTab.fragment, newTab.tag);
                    } else {
                        ft.attach(newTab.fragment);
                    }
                }
                mLastTab = newTab;
                ft.commit();
                mActivity.getSupportFragmentManager().executePendingTransactions();
            }
        }
    }
}