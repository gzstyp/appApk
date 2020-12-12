package com.fwtai.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import com.fwtai.activity.BaseActivity;
import com.fwtai.config.LocalConfig;
import com.fwtai.tab.TabInit;
import com.fwtai.widget.CirclePageIndicator;
import com.yinlz.cdc.R;

import java.util.ArrayList;

/**
 * 第一次安装本apk时引导界面
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2017年3月22日 18:00:51
 * @QQ号码 444141300
 * @官网 http://www.fwtai.com
*/
public class UIGuide extends BaseActivity{
	
	private Activity activity = this;
	private ArrayList<View> views = null;

	@Override
	protected void onCreate(Bundle bundle){
		super.onCreate(bundle);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.layout_guide);
		initViews();
	}
	
	/**载入自定义的View*/
	void initViews(){
		final LayoutInflater inflater = LayoutInflater.from(this);
		views = new ArrayList<View>();
		views.add(inflater.inflate(R.layout.item_guide_1,null,false));
		views.add(inflater.inflate(R.layout.item_guide_2,null,false));
		views.add(inflater.inflate(R.layout.item_guide_3,null,false));
		views.add(inflater.inflate(R.layout.item_guide_4,null,false));
		final GuideViewPagerAdapter pagerAdapter = new GuideViewPagerAdapter(views,this);
		final ViewPager guideViewPager = (ViewPager) findViewById(R.id.guide_viewpager);
		guideViewPager.setAdapter(pagerAdapter);
		final CirclePageIndicator indicator = (CirclePageIndicator) findViewById(R.id.layout_guide_indicator);
		indicator.setViewPager(guideViewPager);
		indicator.setFillColor(ContextCompat.getColor(activity,R.color.view_top_layout_bg));/**圆点颜色设置;原值white*/
		indicator.setPageColor(ContextCompat.getColor(activity,R.color.transparent));/**圆点颜色设置*/
		indicator.setStrokeColor(ContextCompat.getColor(activity,R.color.blue_normal));/**圆点颜色设置;原值stroke*/
		indicator.setCurrentItem(0);
	}
	
	public final class GuideViewPagerAdapter extends PagerAdapter implements OnClickListener{
		private ArrayList<View> views;
		public GuideViewPagerAdapter(final ArrayList<View> views, final Activity activity){
			this.views = views;
		}

		@Override
		public int getCount(){
			if (views != null){
				return views.size();
			}
			return 0;
		}

		@Override
		public void destroyItem(View view, int position, Object object){
			((ViewPager) view).removeView(views.get(position));
		}

		@Override
		public Object instantiateItem(View view, int position){
			((ViewPager) view).addView(views.get(position), 0);
			if (position == views.size() - 1){
				view.findViewById(R.id.guide_start).setOnClickListener(this);
			}
			if(position == 0){
			} else if(position == 1){
			}
			return views.get(position);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1){
			return (arg0 == arg1);
		}

		@Override
		public void onClick(View arg0){
			home();
		}
	}
	
	void home(){
		LocalConfig.getInstance().add(activity,LocalConfig.setup);
        nextActivityAnim(TabInit.class);
	}
}