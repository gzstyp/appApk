package com.fwtai.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * 所有Adapter的基类
 * @param <T>
 */
public abstract class ArrayAdapter<T> extends BaseAdapter {
    protected Context mContext;
    protected ArrayList<T> mData;
    protected boolean mDataValid;

    public ArrayAdapter(Context context) {
        mContext = context;
        mDataValid = false;
    }
    
    /**
     * 分页更新页面  --- 注意内容不叠加
     * @param data
     */
    public void addAll(ArrayList<T> data){
        if (data != null) {
            mDataValid = true;
            if(mData != null){
            	mData.clear();
            	mData.addAll(data);
            }else{
            	mData = data;
            }
            notifyDataSetChanged();
        } else {
            mDataValid = false;
            notifyDataSetInvalidated();
        }
    }

    public void updateData(ArrayList<T> data) {
        if (data != null) {
            mDataValid = true;
            mData = data;
            notifyDataSetChanged();
        } else {
            mDataValid = false;
            notifyDataSetInvalidated();
        }
    }

    public ArrayList<T> getData() {
        return mData;
    }

    @Override
    public int getCount() {
        if (mDataValid && mData != null) {
            return mData.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        if (mDataValid && mData != null) {
            return mData.get(position);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        if (mDataValid && mData != null) {
            return position;
        } else {
            return 0;
        }
    }

	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (!mDataValid) {
            throw new IllegalStateException("这些数据在调用是有效的");
        }
        if (position < 0 || position >= mData.size()) {
            throw new IllegalStateException("不存在可视的View对象" + position);
        }
        T data = mData.get(position);
        View v;
        if (convertView == null) {
            v = newView(mContext, data, parent, getItemViewType(position));
        } else {
            v = convertView;
        }
        bindView(v, position, data);
        return v;
    }

    public abstract View newView(Context context, T data, ViewGroup parent, int type);

    public abstract void bindView(View view, int position, T data);
}