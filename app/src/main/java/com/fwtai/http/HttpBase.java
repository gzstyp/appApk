package com.fwtai.http;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fwtai.widget.AlertDialog;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * <strong style='color:#03f;'>封装及配置okhttp3.6.0远程请求、操作数据的基础类</strong>
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2017年3月14日 21:15:21
 * @QQ号码 444141300
 * @官网 http://www.fwtai.com
*/
public class HttpBase{

	private static final HttpBase instance = new HttpBase();
	
	private final static long CONNECT_TIMEOUT = 1000 * 60;
	private final static long READ_TIMEOUT = 1000 * 60;
	private final static long WRITE_TIMEOUT = 1000 * 120;
	
	/**单例模式*/
	private HttpBase(){}
	
	/**对外提供获取单例模式方法*/
	public static HttpBase getInstance(){
		return instance;
	}
	
	/**判断网络是否可用,返回true可用,否则不可用*/
	public final boolean networkState(final Context context){
		final ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (manager.getActiveNetworkInfo() != null){
			return manager.getActiveNetworkInfo().isAvailable();
		}
		return false;
	}
	
	/**网络未连接时，调用设置方法 */
	public final void showNetwork(final Activity activity){
		new AlertDialog(activity,"网络不可用,是否要设置网络", "设置","取消",true,new View.OnClickListener(){
			@Override
			public void onClick(View v){
				Intent intent = new Intent();
				if (android.os.Build.VERSION.SDK_INT > 10){
					intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
				} else {
					final ComponentName component = new ComponentName("com.android.settings","com.android.settings.WirelessSettings");
					intent.setComponent(component);
					intent.setAction("android.intent.action.VIEW");
				}
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				activity.startActivity(intent);
			}
		},null);
	}
	
	/**判断上网方式是否是wifi*/
	public final boolean isWifi(final Context context){
		final ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		final NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
	    if (activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI)return true;
	    return false;
	}
	
	/**配置和设置GET提交方式的OkHttpClient超时参数*/
	protected OkHttpClient clientGet(){
		return new OkHttpClient().newBuilder()
		.readTimeout(READ_TIMEOUT,TimeUnit.MILLISECONDS)
		.connectTimeout(CONNECT_TIMEOUT,TimeUnit.MILLISECONDS).build();
	}
	
	/**配置和设置POST提交方式的OkHttpClient超时参数*/
	protected OkHttpClient clientPost(){
		return new OkHttpClient().newBuilder()
		.readTimeout(READ_TIMEOUT,TimeUnit.MILLISECONDS)
		.connectTimeout(CONNECT_TIMEOUT,TimeUnit.MILLISECONDS)
		.writeTimeout(WRITE_TIMEOUT,TimeUnit.MILLISECONDS).build();
	}
	
	/**获取Request对象的GET请求提交方式*/
	protected Request requestGet(final String url){
		return new Request.Builder().url(url).build();
	}

    /**获取Call对象的POST单个实体-带请求参数*/
    protected final Call callPostBean(final String url,final HashMap<String,Object> params){
        return clientPost().newCall(requestPostBean(url,params));
    }
	
	/**获取Request对象的GET请求提交方式且带请求参数*/
	protected Request requestGet(String url,final HashMap<String,String> params){
		StringBuilder sb = new StringBuilder("?"); 
		if(url.indexOf("?") != -1){
			sb = new StringBuilder();
		}
		if(params != null && params.size() > 0){
	        for (HashMap.Entry<String, String> entry : params.entrySet()){
	            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
	        }
	        sb.deleteCharAt(sb.length()-1);
			url = url + sb.toString();
	    }
		return new Request.Builder().url(url).build();
	}
	
	/**获取Request对象的POST请求-不带请求参数或请求参数在url里*/
	protected Request requestPost(final String url){
		return new Request.Builder().url(url).post(new FormBody.Builder().build()).build();
	}
	
	/**获取Request对象的POST请求,有带请求参数*/
	protected Request requestPost(final String url,final HashMap<String,String> params){
		final FormBody.Builder builder = new FormBody.Builder();
		if(params != null && params.size() > 0){
            for (HashMap.Entry<String, String> entry : params.entrySet()){
                builder.add(entry.getKey(),entry.getValue());
            }
        }
		return new Request.Builder().url(url).post(builder.build()).build();
	}

	/**获取Request对象的POST请求,仅仅只含上传文件功能参数;仅含有文件上传的封装*/
	protected Request requestPost(final String url,final ArrayList<File> files,final String name){
		final MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
		if(files != null && files.size() > 0){
			for (int i = 0; i < files.size(); i++){
				final File file = files.get(i);
				if(file != null && file.exists()){
					final RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"),file);
					builder.addFormDataPart(name,file.getName(),fileBody);
				}
			}
		}
		return new Request.Builder().url(url).post(builder.build()).build();
	}

    /**封装单实体对象请求*/
    private Request requestPostBean(final String url,final HashMap<String,Object> params){
        final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),JSON.toJSONString(params));
        return new Request.Builder().post(requestBody).tag(url+params).url(url).build();
    }

    /** 封装多实体对象请求,即params里的value可以是基本类型(String,Integer)、实体、JSONObject对象 */
    protected final Call callPostJsonObject(final String url,final JSONObject params){
        return clientPost().newCall(requestPostJsonObject(url,params));
    }

    /**封装多实体对象请求,即params里的value可以是基本类型(String,Integer)、实体、JSONObject对象*/
    protected static Request requestPostJsonObject(final String url,final JSONObject params){
        final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),JSON.toJSONString(params));
        return new Request.Builder().post(requestBody).tag(url+params).url(url).build();
    }

    /**获取Request对象的POST请求,有带请求参数且带[(多文件上传时不能用一个文件名)多单文件取决于后台的接收处理接口]文件上传功能的封装*/
    protected Request requestPost(final String url,final HashMap<String,String> params,final ArrayList<File> files){
        final MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if(params != null && params.size() > 0){
            for(final String key : params.keySet()){
                final String value = params.get(key);
                if(value != null && value.length() > 0){
                    builder.addFormDataPart(key,value.trim());
                }
            }
        }
        if(files != null && files.size() > 0){
            for (int i = 0; i < files.size(); i++){
                final File file = files.get(i);
                if(file != null && file.exists()){
                    final RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"),file);
                    builder.addFormDataPart(file.getName(),file.getName(),fileBody);
                }
            }
        }
        return new Request.Builder().url(url).post(builder.build()).build();
    }
	
	/**获取Request对象的POST请求,有带请求参数且带文件上传功能的封装*/
	protected Request requestPost(final String url,final HashMap<String,String> params,final ArrayList<File> files,final String name){
		final MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
		if(params != null && params.size() > 0){
			for(String key : params.keySet()){
				builder.addFormDataPart(key,params.get(key));
			}
		}
		if(files != null && files.size() > 0){
			for (int i = 0; i < files.size(); i++){
				final File file = files.get(i);
				if(file != null && file.exists()){
					final RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"),file);
					builder.addFormDataPart(name,file.getName(),fileBody);
				}
			}
		}
		return new Request.Builder().url(url).post(builder.build()).build();
	}
	
	/**获取Call对象的GET请求,不带参数或参数在url里*/
	protected Call callGet(final String url){
		return clientGet().newCall(requestGet(url));
	}
	
	/**获取Call对象的GET请求且带请求参数*/
	protected Call callGet(final String url,final HashMap<String,String> params){
		return clientGet().newCall(requestGet(url,params));
	}
	
	/**获取Call对象的POST请求,请求参数在url里*/
	protected Call callPost(final String url){
		return clientPost().newCall(requestPost(url));
	}
	
	/**获取Call对象的POST请求-带请求参数*/
	protected Call callPost(final String url,final HashMap<String,String> params){
		return clientPost().newCall(requestPost(url,params));
	}
	
	/**获取Call对象的POST请求-仅仅做单文件或多文件上传*/
	protected Call callPost(final String url,final ArrayList<File> files,final String name){
		return clientPost().newCall(requestPost(url,files,name));
	}

    /**获取Call对象的POST请求-带请求参数且单文件或多文件上传(多文件时不用结合后台是否可以使用相同的文件名)*/
    protected Call callPost(final String url,final HashMap<String,String> params,final ArrayList<File> files){
        return clientPost().newCall(requestPost(url,params,files));
    }
	
	/**获取Call对象的POST请求-带请求参数且单文件或多文件上传*/
	protected Call callPost(final String url,final HashMap<String,String> params,final ArrayList<File> files,final String name){
		return clientPost().newCall(requestPost(url,params,files,name));
	}
	
	/**获取Response对象的GET同步请求,不含参数或参数在url里*/
	protected Response responseGet(final String url)throws IOException{
		return clientGet().newCall(requestGet(url)).execute();
	}
	
	/**获取Response对象的POST同步请求,不含参数或参数在url里*/
	protected Response responsePost(final String url)throws IOException{
		return clientPost().newCall(requestPost(url)).execute();
	}
	
	/**获取Response对象的POST同步请求,含参数*/
	protected Response responsePost(final String url,final HashMap<String,String> params)throws IOException{
		return clientPost().newCall(requestPost(url,params)).execute();
	}
	
	/**获取Response对象的POST同步请求,仅仅含单文件|多文件上传*/
	protected Response responsePost(final String url,final ArrayList<File> files,final String name)throws IOException{
		return clientPost().newCall(requestPost(url,files,name)).execute();
	}
	
	/**获取Response对象的POST同步请求,连参数且单文件|多文件一起上传提交*/
	protected Response responsePost(final String url,final HashMap<String,String> params,final ArrayList<File> files,final String name)throws IOException{
		return clientPost().newCall(requestPost(url,params,files,name)).execute();
	}
	
	/**获取RequestBody对象,HashMap的key作为表单的name,HashMap的value作为表单的value*/
	protected RequestBody initRequestBody(final HashMap<String,String> params){
		final MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
		if(params != null && params.size() > 0){
			for(String key : params.keySet()){
				builder.addFormDataPart(key,params.get(key));
			}
		}
		return builder.build();
	}
	
	/**获取RequestBody对象,用于文件单文件|多文件上传*/
	public RequestBody initRequestBody(final ArrayList<File> files,final String name){
		final MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
		if(files != null && files.size() > 0){
			for(int i = 0; i < files.size(); i++){
				final File file = files.get(i);
				if(file != null && file.exists()){
					final RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"),file);
					builder.addFormDataPart(name,file.getName(),fileBody);
				}
			}
		}
		return builder.build();
	}
	
	/**获取RequestBody对象*/
	protected RequestBody initRequestBody(final HashMap<String,String> params,final ArrayList<File> files,final String name){
		final MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
		if(params != null && params.size() > 0){
			for(String key : params.keySet()){
				builder.addFormDataPart(key,params.get(key));
			}
		}
		if(files != null && files.size() > 0){
			for(int i = 0; i < files.size(); i++){
				final File file = files.get(i);
				if(file != null && file.exists()){
					final RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"),file);
					builder.addFormDataPart(name,file.getName(),fileBody);
				}
			}
		}
		return builder.build();
	}
	
	/**获取Call对象的POST请求-带请求参数*/
    protected Call callPostHashMap(final String url,final HashMap<String,HashMap<String,Object>> params){
        return clientPost().newCall(requestPostHashMap(url,params));
    }
    
    /**获取Call对象的POST请求-带请求参数*/
    protected Call callPostJSONObject(final String url,final HashMap<String,JSONObject> params){
        return clientPost().newCall(requestPostJSONObject(url,params));
    }

	/**封装多实体对象请求*/
    private Request requestPostHashMap(final String url,final HashMap<String,HashMap<String,Object>> params){
        final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),JSON.toJSONString(params));
        return new Request.Builder().post(requestBody).tag(url+params).url(url).build();
    }

    /**封装多实体对象请求*/
    private Request requestPostJSONObject(final String url,final HashMap<String,JSONObject> params){
        final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),JSON.toJSONString(params));
        return new Request.Builder().post(requestBody).tag(url+params).url(url).build();
    }
}