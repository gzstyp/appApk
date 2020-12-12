package com.fwtai.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import com.fwtai.activity.BaseActivity;
import com.fwtai.http.ToolHttp;
import com.fwtai.interfaces.IRequest;
import com.fwtai.interfaces.TitleOnClickListener;
import com.fwtai.pullrefresh.PullToRefreshBase.OnRefreshListener;
import com.fwtai.pullrefresh.PullToRefreshListView;
import com.fwtai.ui.adapter.ImageLvAdapter;
import com.fwtai.widget.HintDialog;
import com.fwtai.widget.TitleBar;
import com.yinlz.cdc.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 图片的应用
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2017年3月20日 14:55:20
 * @QQ号码 444141300
 * @官网 http://www.fwtai.com
*/
public class UIImage extends BaseActivity implements OnClickListener,OnRefreshListener{

	private ImageLvAdapter adapter;
	private final HintDialog hintDialog = HintDialog.getInstance();
	private Activity activity = this;
	private TitleBar titleBar;
	private boolean up = false;
	private int currentPage = 1;
	private static int showCount = 10;
	private ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String,String>>();
	
	final String url_api = "http://api.yinlz.com/help/getHelpForPaging?";
	private PullToRefreshListView listView;
	
	@Override
	protected void onCreate(final Bundle bundle){
		super.onCreate(bundle);
		setContentView(R.layout.layout_image);
		TitleBar.initSystemBar(this);
		initView();
		ajaxRequestPage(1);
	}

	private void initView(){
		titleBar = (TitleBar) findViewById(R.id.titlebar_imager);
		titleBar.setTitle("图片的应用");
		titleBar.setLeftClickListener(new TitleOnClickListener(){
			@Override
			public void onClick(View view){
				finish();
			}
		});
		titleBar.setRightText("文本");
		titleBar.setRightClickListener(new TitleOnClickListener(){
			@Override
			public void onClick(View view){
				startActivity(new Intent(activity,UITextView.class));
			}
		});
		listView = (PullToRefreshListView) findViewById(R.id.image_lv_refresh);
		listView.setOnRefreshListener(this);
	}
	
	/**
	 * 获取数据-带分页
	 * @param status 为1第一次加载;2下拉更多;3是上拉重新加载第1页数据
	 * @作者 田应平
	 * @创建时间 2016年10月13日 下午6:03:58 
	 * @QQ号码 444141300
	 * @主页 http://www.fwtai.com
	*/
	private void ajaxRequestPage(final int status){
		switch (status){
		case 1:
		case 3:
			currentPage = 1;
			break;
		}
		final HashMap<String,String> params = new HashMap<String,String>();
		params.put("isopen","0");
		params.put("currentPage",String.valueOf(currentPage));
		ToolHttp.getInstance().requestGet(url_api,params,new IRequest(){
			@Override
			public void onFailure(IOException exception){
				listView.onRefreshComplete();
			}
			@Override
			public void onSuccess(String json){
				listView.onRefreshComplete();
				final ArrayList<HashMap<String, String>> list = ToolHttp.parseJsonArray(json);
				if (adapter == null){
					adapter = new ImageLvAdapter(activity,list);
				}
				if (list != null && list.size() > 0){
					switch (status){
					case 1://第1次加载数据
						if (list.size() < showCount)
							currentPage = 1;
						currentPage = 2;
						listView.getRefreshableView().setAdapter(adapter);
						break;
					case 2://下拉加载更多
						currentPage++;
						if (up){
							up = false;
							data.clear();
						}
						data.addAll(list);
						adapter.update(data,true);
						break;
					case 3://上拉重新加载第1页数据
						up = true;
						if(list.size() < showCount)currentPage = 1;
						currentPage = 2;
						adapter.update(list,false);
						break;
					}
					adapter.notifyDataSetChanged();
				}else{
					if(status == 2){
						hintDialog.theme(activity,"已经到最后一页");
					}
				}
			}
		});
	}
	
	@Override
	public void onClick(View view){
	}

	@Override
	public void onRefresh(){
		if (((ListView)listView.getRefreshableView()).getCount() == 0 || ((ListView) listView.getRefreshableView()).getFirstVisiblePosition() == 0){
			ajaxRequestPage(3);//清空数据!
		} else if(((ListView)listView.getRefreshableView()).getLastVisiblePosition() == ((ListView) listView.getRefreshableView()).getCount() - 1){
			ajaxRequestPage(2);//下拉加载更多
		}
	}
}