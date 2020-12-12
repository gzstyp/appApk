package com.fwtai.config;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.HashMap;

/**
 * 对SharedPreferences的封装
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2017年3月22日 下午4:53:48
 * @QQ号码 444141300
 * @主页 http://www.fwtai.com
*/
public final class LocalConfig {

	/**判断是否是首次安装并进入向导页面*/
	public final static String setup = "setup";
	
	private static final String app_setting = "app_setting";
	
	private LocalConfig(){}
	private static LocalConfig instance = null;
	public final static synchronized LocalConfig getInstance(){
		if (instance == null){
			instance = new LocalConfig();
		}
		return instance;
	}

	/**
	 * 保存key和value数据,但是本方法的value是key而已
	 * @param key
	 * @param value
	 * @作者 田应平
	 * @创建时间 2017年3月22日 17:56:09
	 * @QQ号码 444141300
	 * @官网 http://www.fwtai.com
	*/
	public final boolean add(final Activity activity,final String key){
		final SharedPreferences sp = activity.getSharedPreferences(app_setting,Activity.MODE_PRIVATE);
		final Editor editor = sp.edit();
		editor.putString(key,key);
		return editor.commit();
	}
	
	/**
	 * 根据key和value保存单条的数据
	 * @param key
	 * @param value
	 * @作者 田应平
	 * @返回值类型 boolean
	 * @创建时间 2016-3-24 下午12:07:38
	 * @QQ号码 444141300
	 * @官网 http://www.fwtai.com
	*/
	public final boolean add(final Activity activity,final String key,final String value){
		final SharedPreferences sp = activity.getSharedPreferences(app_setting,Activity.MODE_PRIVATE);
		final Editor editor = sp.edit();
		editor.putString(key,value);
		return editor.commit();
	}
	
	/**
	 * 根据HashMap的key和value保存多条的数据
	 * @作者 田应平
	 * @返回值类型 boolean
	 * @创建时间 2016年3月24日 12:03:10
	 * @QQ号码 444141300
	 * @官网 http://www.fwtai.com
	*/
	public final boolean add(final Activity activity,final HashMap<String,String> map){
		final SharedPreferences sp = activity.getSharedPreferences(app_setting,Activity.MODE_PRIVATE);
		final Editor editor = sp.edit();
		for(String key:map.keySet()){
			editor.putString(key,map.get(key));
		}
		return editor.commit();
	}
	
	/**
	 * 根据key移除删除指定数据
	 * @作者 田应平
	 * @用法 LocalConfig.getInstance().remove(activity,key);
	 * @创建时间 2017年3月22日 21:54:31
	 * @QQ号码 444141300
	 * @官网 http://www.fwtai.com
	*/
	public final boolean remove(final Activity activity,final String key){
		return activity.getSharedPreferences(app_setting,Activity.MODE_PRIVATE).edit().remove(key).commit();
	}

    /**
     * 根据key移除删除指定数据
     * @作者 田应平
     * @用法 LocalConfig.getInstance().remove(activity,key);
     * @创建时间 2017年3月22日 21:54:31
     * @QQ号码 444141300
     * @官网 http://www.fwtai.com
     */
    public final boolean remove(final Context context,final String key){
        return context.getSharedPreferences(app_setting,Activity.MODE_PRIVATE).edit().remove(key).commit();
    }

    /**
     * 根据key读取获取已保存的单条数据信息
     * @param context
     * @return
     * @作者 田应平
     * @返回值类型 HashMap< String,String>
     * @创建时间 2015年10月27日 03:18:14
     * @QQ号码 444141300
     * @官网 http://www.fwtai.com
     */
    public final String getValue(final Context context,final String key){
        final SharedPreferences sp = context.getSharedPreferences(app_setting,Activity.MODE_PRIVATE);
        if (sp != null){
            return sp.getString(key,null);
        }
        return null;
    }

	/**
     * 根据key读取获取已保存的单条数据信息
     * @param context
     * @return
     * @作者 田应平
     * @返回值类型 HashMap< String,String>
     * @创建时间 2015年10月27日 03:18:14
     * @QQ号码 444141300
     * @官网 http://www.fwtai.com
    */
	public final String getValue(final Activity activity,final String key){
		final SharedPreferences sp = activity.getSharedPreferences(app_setting,Activity.MODE_PRIVATE);
		if (sp != null){
			return sp.getString(key,null);
		}
		return null;
	}
	
	/**
     * 根据key读取获取已保存的多条数据信息
     * @param context
     * @return
     * @作者 田应平
     * @返回值类型 HashMap< String,String>
     * @创建时间 2015年10月27日 03:18:14
     * @QQ号码 444141300
     * @官网 http://www.fwtai.com
    */
	public final HashMap<String,String> getHashMap(final Activity activity,final String... keys){
		final SharedPreferences sp = activity.getSharedPreferences(app_setting,Activity.MODE_PRIVATE);
		final HashMap<String, String> map = new HashMap<String, String>();
		if (sp != null){
		    for(int x = 0; x < keys.length; x++){
		        final String key = keys[x];
                map.put(key,sp.getString(key,null));//第二个参数是要是为空时设置个默认值
		    }
			return map;
		}
		return map;
	}
	
	public final void clear(final Context context){
        final SharedPreferences sp = context.getSharedPreferences(app_setting,Activity.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sp.edit();
        editor.clear().apply();
    }
}