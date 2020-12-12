package com.fwtai.activity;

import android.os.Bundle;
import com.fwtai.config.App;
import com.fwtai.widget.HintDialog;

/**
 * 主页面-再按一次退出
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2015年9月11日 12:15:35
 * @QQ号码 444141300
 * @官网 http://www.fwtai.com
*/
public class FragmentExit extends BaseFragmentActivity{

	private long mLastPressBackTime = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onBackPressed(){
		if(System.currentTimeMillis()-mLastPressBackTime < 1500) {
			if(App.ACTIVITY_LIST != null && !App.ACTIVITY_LIST.isEmpty()) {
				int count = App.ACTIVITY_LIST.size();
				for(int i=0;i<count;i++) {
					App.ACTIVITY_LIST.get(i).finish();
				}
				App.ACTIVITY_LIST.clear();
				App.ACTIVITY_LIST = null;
			}
		} else {
			mLastPressBackTime = System.currentTimeMillis();
			HintDialog.getInstance().theme(this,"再按一次退出");
		}
	}
}