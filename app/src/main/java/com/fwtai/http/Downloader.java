package com.fwtai.http;

import android.app.Activity;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import com.fwtai.config.ConfigFile;
import com.fwtai.interfaces.IDownLoad;
import com.fwtai.widget.HintDialog;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * 图片|文件下载器
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2017年3月15日 下午4:57:13
 * @QQ号码 444141300
 * @主页 http://www.fwtai.com
*/
public final class Downloader{
	
private static Downloader instance = null;
	
	private final ArrayList<WeakReference<HttpCancel>> listCancel = new ArrayList<WeakReference<HttpCancel>>(0);
	
    private OkHttpClient client;
	
    /**单例模式*/
	private Downloader(){
        client = HttpBase.getInstance().clientGet();
	}
    
	/**对外提供获取单例模式方法*/
	public static Downloader getInstance(){
		if (instance == null ){
			instance = new Downloader();
		}
		return instance;
	}
	
	public static final Handler mHandler = new Handler(Looper.getMainLooper());
	
	/**检查SD卡是否存在,存在返回true,否则false*/
	public final static boolean SDCardExists(){
		return Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
	}
	
	/**
	 * 获取SD卡的下载目录,如果返回空则说明没有可用的SD卡,否则存在可用SD卡,返回根目录下的/download
	 * @作者 田应平
	 * @创建时间 2017年3月15日 下午4:55:46
	 * @QQ号码 444141300
	 * @主页 http://www.fwtai.com
	*/
	public final static String getSDCardDir(){
		if (SDCardExists()){
			final String root_dir = Environment.getExternalStorageDirectory().toString();//获取跟目录
			final String download_dir = root_dir + "/download";
			final File file = new File(download_dir);
			if (!file.exists()){
				file.mkdir();
			}
			return file.toString();
		}
		return null;
	}

	/**
	 * 图片|文件下载
	 * @param url 要下载文件|图片的url路径
	 * @param fileName 如果为空则以下载的文件的文件名命名
	 * @param breakpoint 是否支持断点续传下载;当fileName为空时且参数breakpoint为true时支持断点续传;否则重新下载
	 * @作者 田应平
	 * @创建时间 2017年3月15日 16:31:57
	 * @QQ号码 444141300
	 * @主页 http://www.fwtai.com
	*/
	public final HttpCancel download(final String url,final String fileName,boolean breakpoint, final IDownLoad callback){
		final HttpCancel httpCancel = new HttpCancel();
		try {
			String rangeHeaderValue = null;
			if(!SDCardExists()){
				mHandler.post(new Runnable(){
					public void run(){
						callback.errorCode(ConfigFile.code201);
					}
				});
			}else{
				final String dir = getSDCardDir();
				final String ext = url.substring(url.lastIndexOf('.')+1,url.length());
				final String pathname = ((fileName == null || fileName.length() <= 0)?(dir+"/"+url.substring(url.lastIndexOf('/')+1)):(dir+"/"+fileName+"."+ext));
				final File fullPathFile = new File(pathname);
				if(breakpoint && (fullPathFile != null && fullPathFile.exists() && !fullPathFile.isDirectory())){
					rangeHeaderValue = "bytes=" + fullPathFile.length() + "-";
				} else {
					fullPathFile.delete();
					breakpoint = false;
				}
				final long hasDownLen = breakpoint ? fullPathFile.length() : 0;
				final Request.Builder builder = new Request.Builder().url(url).get();
				if(breakpoint)builder.addHeader("Range",rangeHeaderValue);
				httpCancel.call = client.newCall(builder.build());
				listCancel.add(new WeakReference<HttpCancel>(httpCancel));
				if(httpCancel.call != null && !httpCancel.call.isCanceled()){
					httpCancel.call.enqueue(new Callback(){
						public void onResponse(final Call call, final Response response) throws IOException{
							if (call.isCanceled())return;
							if (response.isSuccessful()){
								mHandler.post(new Runnable(){
									public void run(){
										callback.start();
									}
								});
								long total;
								try {
									total = Long.parseLong(response.header("Content-Length"));
								} catch (Exception e){
									total = 0;
								}
								final BufferedInputStream in = new BufferedInputStream(response.body().byteStream());
								final byte[] buffer = new byte[1024 * 8];
								long hasWriteLen = hasDownLen;
								int len;
								final RandomAccessFile out = new RandomAccessFile(fullPathFile,"rwd");
								out.skipBytes((int) hasWriteLen);
								long lastPublishTime = System.currentTimeMillis() - 334;
								while ((len = in.read(buffer)) != -1){
									out.write(buffer, 0,len);
									hasWriteLen += len;
									long now;
									if ((now = System.currentTimeMillis()) - lastPublishTime > 333){
										uiProgress(callback, total, hasWriteLen);
										lastPublishTime = now;
									}
								}
								uiProgress(callback,total,total);
								in.close();
								out.close();
								mHandler.post(new Runnable(){
									public void run(){
										callback.onComplete(fullPathFile);
									}
								});
							}else{
								mHandler.post(new Runnable(){
									public void run(){
										callback.errorCode(response.code());
									}
								});
							}
						}
						public void onFailure(final Call call, final IOException e){
							if (call.isCanceled())return;
							mHandler.post(new Runnable(){
								public void run(){
									callback.onFailure(e);
								}
							});
						}
					});
				}
			}
		} catch (Exception e){
			callback.errorCode(ConfigFile.code202);
		}
		return httpCancel;
	}
	
	/**处理下载进度,有两处地方调用*/
	private void uiProgress(final IDownLoad callback,final long total,final long current){
		mHandler.post(new Runnable(){
			public void run(){
				callback.onProgress(current,total);
				final int progress = (int)((total - (total-current)) * 100 / total);
				callback.onProgress(progress);
			}
		});
	}
	
	public final void showError(final Activity activity,final int code){
		final HintDialog hintDialog = HintDialog.getInstance();
		switch (code){
		case ConfigFile.code201:
			hintDialog.error(activity,"SD卡不可用");
			break;
		case ConfigFile.code404:
			hintDialog.error(activity,"下载失败,所下载的文件路径不存在");
			break;
		case ConfigFile.code416:
			hintDialog.error(activity,"下载失败,该文件已下载");
			break;
		default:
			hintDialog.error(activity,"下载失败,错误码:"+code);
			break;
		}
	}
}