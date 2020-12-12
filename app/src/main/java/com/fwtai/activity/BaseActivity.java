package com.fwtai.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import com.alibaba.fastjson.JSONObject;
import com.fwtai.config.App;
import com.fwtai.config.LocalConfig;
import com.fwtai.config.Variables;
import com.fwtai.http.HttpCancel;
import com.fwtai.http.ToolHttp;
import com.fwtai.interfaces.IRequest;
import com.fwtai.tool.ToolMethod;
import com.fwtai.tool.ToolString;
import com.fwtai.widget.AlertDialog;
import com.fwtai.widget.HintDialog;
import com.yinlz.cdc.R;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 实现即可
 * <p>
 */
public abstract class BaseActivity extends FragmentActivity implements View.OnClickListener{

	public Activity activity;
	
	public Context context;

    protected final ToolMethod toolMethod = ToolMethod.getInstance();
    protected final ToolString toolString = ToolString.getInstance();
    private final ToolHttp toolHttp = ToolHttp.getInstance();
    protected final HintDialog hintDialog = HintDialog.getInstance();
	
	protected String mClassName;

	public BaseActivity(){
		mClassName = getClass().getSimpleName();
	}

	@Override
	public void onAttachedToWindow(){
		super.onAttachedToWindow();
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState){
		super.onPostCreate(savedInstanceState);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		if (App.ACTIVITY_LIST == null)
			App.ACTIVITY_LIST = new ArrayList<Activity>();
		if (!App.ACTIVITY_LIST.contains(this)){
			App.ACTIVITY_LIST.add(this);
		}
		this.activity = this;
		this.context = this;
	}

	@Override
	public View onCreateView(String name, Context context, AttributeSet attrs){
		return super.onCreateView(name, context, attrs);
	}

	@Override
	protected void onNewIntent(Intent intent){
		super.onNewIntent(intent);
	}

	@Override
	protected void onRestart(){
		super.onRestart();
	}

	@Override
	protected void onStart(){
		super.onStart();
	}

	@Override
	protected void onPostResume(){
		super.onPostResume();
	}

	@Override
	public void onResume(){
		super.onResume();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig){
		super.onConfigurationChanged(newConfig);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onBackPressed(){
		super.onBackPressed();
	}

	@Override
	protected void onRestoreInstanceState(final Bundle bundle){
		try {
			super.onRestoreInstanceState(bundle);
		} catch (IllegalArgumentException e){
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	protected void onSaveInstanceState(final Bundle bundle){
		super.onSaveInstanceState(bundle);
	}

	@Override
	public void onPause(){
		super.onPause();
		hideIM();
	}

	@Override
	protected void onStop(){
		super.onStop();
	}

	@Override
	protected void onDestroy(){
		if (App.ACTIVITY_LIST != null && App.ACTIVITY_LIST.contains(this)){
			App.ACTIVITY_LIST.remove(this);
		}
		super.onDestroy();
	}

	@Override
	public void finish(){
		super.finish();
	}

	/**
     * 隐藏输入法-用于点击事件时输入法不隐藏时调用
     * @作者 田应平
     * @创建时间 2016年10月18日 17:44:23
     * @QQ号码 444141300
     * @主页 http://www.fwtai.com
    */
	public void hideIM(){
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm != null && getCurrentFocus() != null){
			imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
		}
	}
	
	/**移除|取消全部的请求对象*/
	public void removeHttpCancel(final HashMap<String,HttpCancel> maps){
		if (maps != null && maps.size() > 0){
			for(String key : maps.keySet()){
				if(maps.get(key) != null){
					maps.get(key).cancel();
				}
			}
		}
	}
	
	/**移除指定请求对象集合的key*/
	public void removeHttpCancel(final HashMap<String,HttpCancel> maps,final String key){
		if (maps != null && maps.size() > 0){
			if(!ToolString.isBlank(key)){
				for(String url : maps.keySet()){
					if(url.equals(key)){
						if(maps.get(key) != null){
							maps.get(key).cancel();
						}
					}
				}
			}
		}
	}

    protected final void viewGone(final View view){
        view.setVisibility(View.GONE);
    }

    protected final void viewGone(final int R_id_view){
        findViewById(R_id_view).setVisibility(View.GONE);
    }

    protected final void viewVisible(final View view){
        view.setVisibility(View.VISIBLE);
    }

    protected final void viewVisible(final int R_id_view){
        findViewById(R_id_view).setVisibility(View.VISIBLE);
    }

    protected final void viewInvisible(final View view){
        view.setVisibility(View.INVISIBLE);
    }

    protected final void viewInvisible(final int R_id_view){
        findViewById(R_id_view).setVisibility(View.INVISIBLE);
    }

    /**左边的进来,右边退出(下一步或下一个页面)*/
    protected final void fromLeftToRight(){
        toolMethod.fromLeftToRight(this);
    }

    /**左边的进来,右边退出(下一步或下一个页面)并结束当前的Activity*/
    protected final void fromLeftToRightFinish(){
        toolMethod.fromLeftToRightFinish(this);
    }

    /**从右边进,左边退出(上一步或返回页面)*/
    protected final void fromRightToLeft(){
        toolMethod.fromRightToLeft(this);
    }

    /**从右边进,左边退出(上一步或返回页面)并结束当前的Activity*/
    protected final void fromRightToLeftFinish(){
        toolMethod.fromRightToLeftFinish(this);
    }

    /**从底部往顶部滑动动画*/
    protected final void fromBottomToTop(){
        toolMethod.fromBottomToTop(this);
    }

    /**从底部往顶部滑动动画并结束当前的Activity*/
    protected final void fromBottomToTopFinish(){
        toolMethod.fromBottomToTopFinish(this);
    }

    /**从顶部往底部滑动,用于dialog?*/
    protected final void activityClose(){
        overridePendingTransition(0,R.anim.activity_close);
    }

    /**从底部往顶部滑动,用于dialog?*/
    protected final void activityOpen(){
        overridePendingTransition(R.anim.activity_open,0);
    }

    /**点事件返回*/
    protected final void goback(){
        fromRightToLeftFinish();
    }

    //按键盘的返回键返回
    @Override
    public boolean onKeyDown(final int keyCode,final KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                goback();
                break;
        }
        return super.onKeyDown(keyCode,event);
    }

    @Override
    public void onClick(final View view){}

    /**设置默认监听,初始化用法:setViewOnClick(R.id.tv,R.id.btn);*/
    public void setViewOnClick(final int... ids) {
        for (int i = 0; i < ids.length; i++){
            final View view = findViewById(ids[i]);
            view.setClickable(true);
            view.setOnClickListener(this);
        }
    }

    protected final void setTvValue(final TextView tv,final String value){
        tv.setText(toolString.replace(value));
    }

    /**不能在主线程运行(解决方案:可以写个方法来调用)*/
    protected void alertNormal(final String msg){
        if(this != null){
            new AlertDialog().create(this).alertNormal(msg);
        }
    }

    /**不能在主线程运行或在已finish的页面调用,如果出错,请用showIosDialogSuccess()替代*/
    protected void alertSucceed(final String msg){
        if(this != null){
            new AlertDialog().create(this).alertSucceed(msg);
        }
    }

    /**不能在主线程运行(解决方案:可以写个方法来调用)*/
    protected void alertError(final String msg){
        if(this != null){
            new AlertDialog().create(this).alertError(msg);
        }
    }

    /**请求失败时调用*/
    protected void failureAlert(){
        if(this != null){
            new AlertDialog().create(this).alertError("连接服务器失败");
        }
    }

    protected void ok(final String msg){
        hintDialog.ok(context,msg);
    }

    protected void error(final String msg){
        hintDialog.error(context,msg);
    }

    protected void normal(final String msg){
        hintDialog.normal(context,msg);
    }

    protected void nextActivity(final Class<?> cls) {
        nextActivity(cls,null);
    }

    /**下一页,带从右到左的动画*/
    protected void nextActivityAnim(final Class<?> cls) {
        nextActivityAnim(cls,null);
    }

    protected void nextActivity(final Class<?> cls,final Bundle bundle) {
        final Intent intent = new Intent(context,cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent,200);
    }

    /**下一页,带参数且从右到左的动画*/
    protected void nextActivityAnim(final Class<?> cls,final Bundle bundle) {
        final Intent intent = new Intent(context,cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent,200);
        fromLeftToRight();
    }

    /**异步POST请求,仅用于添加、删除、编辑*/
    protected void postData(final String url,@NonNull final HashMap<String,String> params,final IRequest iRequest){
        params.putAll(toolMethod.requestParams(context));
        toolHttp.requestPost(url,params,iRequest);
    }

    /**异步POST请求-上传文件(含单文件|多文件时不用结合后台是否可以使用相同的文件名)附加请求参数,注意参数name*/
    protected void postDataFile(final String url,@NonNull final HashMap<String,String> params,final ArrayList<File> files,final String name,final IRequest iRequest){
        params.putAll(toolMethod.requestParams(context));
        if(name == null || name.length() <= 0){
            toolHttp.requestPost(url,params,files,iRequest);
        }else{
            toolHttp.requestPost(url,params,files,name,iRequest);
        }
    }

    /**异步POST请求有单实体对象的参数*/
    protected void postPostBean(final String url,@NonNull final HashMap<String,Object> params,final IRequest iRequest){
        params.putAll(toolMethod.requestParams(context));
        toolHttp.requestPostBean(url,params,iRequest);
    }

    /** 封装多实体对象请求,即params里的value可以是基本类型(String,Integer)、实体、JSONObject对象 */
    protected void postPostJsonObject(final String url,@NonNull final JSONObject params,final IRequest iRequest){
        final String userId = LocalConfig.getInstance().getValue(context,Variables.key_userId);
        if(userId != null){
            params.put("userId",userId);
        }
        toolHttp.requestPostJsonObject(url,params,iRequest);
    }

    /**获取数据,不带分页参数*/
    protected void getData(final String url,@NonNull final HashMap<String,String> params,final IRequest iRequest){
        params.putAll(toolMethod.requestParams(context));
        toolHttp.requestGet(url,params,iRequest);
    }

    /**获取数据,带分页参数,仅适用于分页功能,仅传当前页即可*/
    protected void getDataPage(final String url,@NonNull final HashMap<String,String> params,final IRequest iRequest){
        params.put(Variables.page_size,String.valueOf(Variables.pageSize));
        getData(url,params,iRequest);
    }
}