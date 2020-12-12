package com.fwtai.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * 数据适配器
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2016年10月24日 上午11:47:08
 * @QQ号码 444141300
 * @主页 http://www.fwtai.com
*/
public abstract class ListAdapter extends BaseAdapter{
	
	private ArrayList<String> list;
	
	public ListAdapter(ArrayList<String> list){
		this.list = list;
	}
	
	@Override
	public final int getCount() {
		return list == null ? 0 : list.size();
	}
	
	@Override
	public final Object getItem(int position) {
		return null == list ? null : list.get(position);
	}
	
	@Override
	public final long getItemId(int position) {
		return position;
	}
	
	/**
	 * 继承时实现即可
	*/
	@Override
	public abstract View getView(int position, View convertView, ViewGroup parent);
}