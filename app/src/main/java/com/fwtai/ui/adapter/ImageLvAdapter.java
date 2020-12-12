package com.fwtai.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.fwtai.adapter.AdapterBase;
import com.fwtai.tool.ToolImageView;
import com.yinlz.cdc.R;

import java.util.ArrayList;
import java.util.HashMap;

public final class ImageLvAdapter extends AdapterBase{

	private Activity activity;
	private ArrayList<HashMap<String, String>> list;
	
	public ImageLvAdapter(final Activity activity,final ArrayList<HashMap<String, String>> list){
		super(list);
		this.list = list;
		this.activity = activity;
	}

	@Override
	public View getView(final int position,View convertView,final ViewGroup parent){
		ViewHolder holder;
		final HashMap<String, String> map = list.get(position);
		if (convertView == null){
			//convertView = LayoutInflater.from(activity).inflate(R.layout.item_image_t,null);
			//第一个参数传入布局的资源ID，生成fragment视图;
			//第二个参数是视图的父视图，通常我们需要父视图来正确配置组件。第三个参数告知布局生成器是否将生成的视图添加给父视图
			//root不为空的情况:
			//1.如果attachToRoot为true,就直接将这个布局添加到root父布局了,并且返回的view就是父布局
			//2.如果attachToRoot为false,就不会添加这个布局到root父布局,返回的view为resource指定的布局
			convertView = LayoutInflater.from(activity).inflate(R.layout.item_image_t,parent,false);
			holder = new ViewHolder();
			holder.iv_imageurl = (ImageView) convertView.findViewById(R.id.item_image_iv_imageurl);
			holder.tv_contents = (TextView) convertView.findViewById(R.id.item_image_tv_contents);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv_contents.setText(map.get("contents"));
		final String url_image = "http://api.yinlz.com/"+map.get("imageurl");
		ToolImageView.getInstance().loadImage(url_image,holder.iv_imageurl);
		return convertView;
	}
	
	final class ViewHolder{
		private TextView tv_contents;
		private ImageView iv_imageurl;
	}
}