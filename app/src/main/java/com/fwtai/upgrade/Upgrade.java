package com.fwtai.upgrade;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.fwtai.http.Downloader;
import com.fwtai.http.HttpBase;
import com.fwtai.http.HttpCancel;
import com.fwtai.interfaces.IDownLoad;
import com.fwtai.interfaces.IViewTimer;
import com.fwtai.widget.AlertDialog;
import com.fwtai.widget.BaseDialog;
import com.fwtai.widget.HintDialog;
import com.fwtai.widget.ViewEvent;
import com.yinlz.cdc.R;

import java.io.File;
import java.io.IOException;

/**
 * 更新app自动升级工具类
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2017年3月27日 下午12:22:18
 * @QQ号码 444141300
 * @主页 http://www.fwtai.com
*/
public final class Upgrade {
	
	private HttpCancel cancel;
	
	/**服务器端的版本号*/
	private int version = 0;
	/**当前的ui活动界面*/
	private Activity activity;
	
	/**此构造方法用于安装apk文件且同包下访问*/
	protected Upgrade(){}
	
	/**此构造方法用于更新app应用参数*/
	public Upgrade(final Activity activity,final int version){
		this.activity = activity;
		this.version = version;
	}
	
	/**
	 * 获取手机端的软件版本号
	 * @param context
	 * @return
	 */
	private int getVersionCode(){
		int versionCode = 0;
		try {
			versionCode = activity.getPackageManager().getPackageInfo("com.yinlz.apk", 0).versionCode;
		} catch (NameNotFoundException e){
		}
		return versionCode;
	}
	
	public boolean compareVersion(){
		return this.version > getVersionCode();
	}
	
	/**apk_url为下载apk的全路径*/
	public void downloadApk(final String apk_url){
		final HttpBase instance = HttpBase.getInstance();
		if(!instance.networkState(activity)){
			instance.showNetwork(activity);
			return;
		}
		if(instance.isWifi(activity)){
			download(apk_url);
		}else{
			new AlertDialog(activity,"当前网络不是WiFi连接,是否下载?","土豪下载","放弃更新",false,new View.OnClickListener(){
				@Override
				public void onClick(View v){
					download(apk_url);
				}
			},null);
		}
	}
	
	protected void download(final String apk_url){
		final BaseDialog viewDialog = BaseDialog.create(activity, R.layout.layout_upgrade, 0.80f,false);
		final ProgressBar mProgress = (ProgressBar) viewDialog.findViewById(R.id.progress);
		final TextView tv_message = (TextView) viewDialog.findViewById(R.id.tv_message);
		/**取消更新*/
		new ViewEvent(viewDialog.findViewById(R.id.upgrade_cancel),1,new IViewTimer(){
			@Override
			public void viewClick(View view){
				viewDialog.dismiss();
				if(cancel != null)cancel.cancel();
			}
		});
		/**转为后台进程下载*/
		new ViewEvent(viewDialog.findViewById(R.id.upgrade_service),1,new IViewTimer(){
			@Override
			public void viewClick(View view){
				viewDialog.dismiss();
				final Intent intent = new Intent(activity,ServiceUpgrade.class);
				intent.putExtra("apk_url",apk_url);
				activity.startService(intent);
				if(cancel != null)cancel.cancel();
			}
		});
		final String apkPath = apk_url.substring(apk_url.lastIndexOf("/")+1,apk_url.length());
		final Downloader downloader = Downloader.getInstance();
		final HintDialog hintDialog = HintDialog.getInstance();
		cancel = downloader.download(apk_url,apkPath,false,new IDownLoad(){
			@Override
			public void onFailure(IOException e){
				viewDialog.dismiss();
				if(cancel != null)cancel.cancel();
				hintDialog.error(activity,"连接失败,请检查网络");
			}
			@Override
			public void errorCode(int code){
				switch (code){
				case 201:
					hintDialog.error(activity,"更新失败,SD卡不可用");
					break;
				case 404:
					hintDialog.error(activity,"下载失败,所下载的文件路径不存在");
					break;
				case 416:
					hintDialog.error(activity,"下载失败,该文件已下载");
					break;
				default:
					hintDialog.error(activity,"下载失败,错误码:"+code);
					break;
				}
				viewDialog.dismiss();
				if(cancel != null)cancel.cancel();
			}
			@Override
			public void onProgress(int progress){
				mProgress.setProgress(progress);
				tv_message.setText("已经完成" + progress + "%");
			}
			@Override
			public void onComplete(final File file){
				viewDialog.dismiss();
				if(cancel != null)cancel.cancel();
				install(activity,file);
			}
		});
	}
	
	/**安装apk*/
	protected void install(final Context context,final File apkfile){
		if(!apkfile.exists())return;
		final Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
		context.startActivity(intent);
		android.os.Process.killProcess(android.os.Process.myPid());
	}
}