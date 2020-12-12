package com.fwtai.upgrade;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import com.fwtai.http.Downloader;
import com.fwtai.http.HttpCancel;
import com.fwtai.interfaces.IDownLoad;
import com.fwtai.widget.HintDialog;

import java.io.File;
import java.io.IOException;

/**
 * 转为后台下载apk更新文件
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2017年3月27日 21:16:21
 * @QQ号码 444141300
 * @主页 http://www.fwtai.com
*/
public final class ServiceUpgrade extends Service {

	private HttpCancel cancel;
	
	@Override
	public void onCreate(){
		super.onCreate();
	}
	
	/**注意不能在此访问网络或耗时操作;所以此处必然是一个线程,接收从activity传递过来的参数*/
	@Override
	public int onStartCommand(final Intent intent, final int flags,final int startId){
		final String apk_url = intent.getStringExtra("apk_url");
		downloadApk(apk_url);
		return super.onStartCommand(intent,flags,startId);
	}

	@Override
	public IBinder onBind(Intent intent){
		return null;
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
	}
	
	private void downloadApk(final String url){
		final HintDialog hintDialog = HintDialog.getInstance();
		final Context context = getApplicationContext();
		if (url == null || url.length() == 0){
			stopSelf();
			hintDialog.error(context,"无效的url下载路径");
			return;
		}
		cancel = Downloader.getInstance().download(url,null,false,new IDownLoad(){
			@Override
			public void onFailure(IOException e){
				hintDialog.error(context,"连接服务器失败,请检查网络状态");
				stopSelf();
				if(cancel != null)cancel.cancel();
			}
			@Override
			public void errorCode(final int code){
				switch(code){
				case 201:
					hintDialog.error(context,"更新失败,SD卡不可用");
					break;
				case 404:
					hintDialog.error(context,"更新失败,所下载的文件路径不存在");
					break;
				default:
					hintDialog.error(context,"更新失败,错误码:"+code);
					break;
				}
				stopSelf();
				if(cancel != null)cancel.cancel();
			}
			@Override
			public void onProgress(final int progress){}
			@Override
			public void onComplete(final File file){
				stopSelf();
				if(cancel != null)cancel.cancel();
				new Upgrade().install(context,file);
			}
		});
	}
}