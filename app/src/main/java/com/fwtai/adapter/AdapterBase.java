package com.fwtai.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 基本的Adapter，继承本类只需重写 getView 即可！必须实例化当前的子类。[子类的构造方法里的参数是给该子类的成员变量赋初始值的。]
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2015年2月4日 15:35:24
 * @QQ号码 444141300
 * @官网 http://www.fwtai.com
*/
public abstract class AdapterBase extends BaseAdapter{
	
	/**继承本类的都要super(list)*/
	private ArrayList<HashMap<String, String>> list;
	
	/**
	 * 初始化数据,继承本类的都要super(list)
	 * @param list - String类型的
	 * @作者 田应平
	 * @创建时间 2015年2月5日 11:59:11 
	 * @QQ号码 444141300
	 * @官网 http://www.fwtai.com
	*/
	public AdapterBase(final ArrayList<HashMap<String, String>> list){
		this.list = list;
	}
	
	@Override
	public final int getCount(){
		return list == null ? 0 : list.size();
	}
	
	/**
	 * 上拉加载更多，下拉重新加载第1页,已做notifyDataSetChanged()和notifyDataSetInvalidated()处理
	 * @作者 田应平
	 * @param data 新数据
	 * @param b 是否清空已有数据
	 * @创建时间 2016年11月3日 下午2:04:21
	 * @QQ号码 444141300
	 * @主页 http://www.fwtai.com
	*/
	public final void update(final ArrayList<HashMap<String, String>> data,final boolean b){
		if (data == null || data.size() <= 0){
			notifyDataSetInvalidated();
			return;
		}
		if(b){
			this.list.addAll(data);
		}else{
			this.list.clear();
			this.list.addAll(data);
		}
		notifyDataSetChanged();
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
	 * 继承时实现本抽象方法即可
	 * @作者 田应平
	 * @param convertView 就是ListView里Item控件View
	 * @position position 就是定位ListView里Item控件哪一个View
	 * @创建时间 2016年11月9日 10:05:06
	 * @QQ号码 444141300
	 * @主页 http://www.fwtai.com
	*/
	@Override
	public abstract View getView(int position, View convertView, ViewGroup parent);
}