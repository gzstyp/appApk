package com.fwtai.ui;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.fwtai.activity.BaseActivity;
import com.fwtai.http.Downloader;
import com.fwtai.http.HttpCancel;
import com.fwtai.http.ToolHttp;
import com.fwtai.http.Uploader;
import com.fwtai.interfaces.IDownLoad;
import com.fwtai.interfaces.IRequest;
import com.fwtai.interfaces.IUploader;
import com.fwtai.interfaces.IViewTimer;
import com.fwtai.interfaces.TitleOnClickListener;
import com.fwtai.widget.HintDialog;
import com.fwtai.widget.TitleBar;
import com.fwtai.widget.ViewEvent;
import com.yinlz.cdc.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * okhttp下载应用
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2016年10月9日 18:06:58
 * @QQ号码 444141300
 * @官网 http://www.fwtai.com
*/
public class UIDownload extends BaseActivity implements View.OnClickListener {

	private TitleBar titleBar;
	private TextView tv_progress, request_data, tv_count,tv_time;
	private ProgressBar download_progress;
	private final HintDialog hintDialog = HintDialog.getInstance();
	private Dialog up;
	private HttpCancel cancelDownloadQQ = null;
	
	private final HashMap<String,HttpCancel> mapHttps = new HashMap<String,HttpCancel>(0);

	protected Activity activity = this;

	private int count = 60;
	private Handler handler = new Handler();
	private Runnable runnable;

	protected Button btn_time;
	private ProgressBar uploadProgress;

	protected String url_json_restful = "http://www.fwt.cloud/app/json/1";
	protected String url_json = "http://www.fwt.cloud/app/json?rows=1";
	protected String url_jsonArray = "http://www.fwt.cloud/app/jsonArray";
	final String url_img = "http://img.gzhxyc.com/fileMAImg";
	final String mobileqq = "https://qd.myapp.com/myapp/qqteam/AndroidQQ/mobileqq_android.apk";

	@Override
	protected void onCreate(final Bundle bundle){
		super.onCreate(bundle);
		setContentView(R.layout.layout_download);
		TitleBar.initSystemBar(this);
		initView();
	}

	private void initView(){
		titleBar = (TitleBar) findViewById(R.id.titlebar_download);
		titleBar.setTitle("okhttp下载应用");
		titleBar.setRightText("闪屏");
		titleBar.setRightClickListener(new TitleOnClickListener(){
			@Override
			public void onClick(final View view){
                nextActivityAnim(UILaunch.class);
			}
		});
		/**下载QQ*/
		new ViewEvent((Button)findViewById(R.id.btn_download_qq),2,"已开始下载","下载QQ",false,new IViewTimer() {
			@Override
			public void viewClick(View view){
				downloadImg(mobileqq,null);
			}
		});
		titleBar.setLeftClickListener(new TitleOnClickListener(){
			@Override
			public void onClick(View view){
				finish();
			}
		});
		tv_progress = (TextView) findViewById(R.id.layout_download_tv_progress);
		request_data = (TextView) findViewById(R.id.layout_download_data);
		tv_count = (TextView) findViewById(R.id.layout_download_tv_count);
		tv_time = (TextView) findViewById(R.id.layout_download_time);
		new ViewEvent(tv_time,10,"秒后重新获取验证码","重新获取验证码",true,new IViewTimer(){
			@Override
			public void viewClick(View view){
				request_data.setText("开始计时");
			}
		});
		tv_count.setOnClickListener(this);
		runnable = new Runnable(){
			@Override
			public void run(){
				handler.postDelayed(this, 1000);
				tv_count.setText(count-- + "s后重新获取");
				if (count < 0){
					count = 60;// 重新设置值
					handler.removeCallbacks(this);
					tv_count.setClickable(true);
					tv_count.setText("重新获取验证码");
				}
			}
		};
		request_data.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0){
				request_data.setText("请求数据");
			}
		});
		findViewById(R.id.layout_download_get).setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View view){
				get();
			}
		});
		findViewById(R.id.layout_download_post).setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View view){
				post();
			}
		});
		download_progress = (ProgressBar) findViewById(R.id.layout_download_progress);
		/** 单文件上传 */
		new ViewEvent(findViewById(R.id.layout_download_btn_upload),30,null,null,false,new IViewTimer(){
			@Override
			public void viewClick(View view){
				uploadSingle();
			}
		});
		/** 单文件上传 */
		/*findViewById(R.id.layout_download_btn_upload).setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View view){
				uploadSingle();
			}
		});*/
		uploadProgress = (ProgressBar) findViewById(R.id.upload_progress);
		/**多文件上传*/
		/*upload.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){
				uploadMulti();
			}
		});*/
		/**多文件上传*/
		new ViewEvent(findViewById(R.id.upload_mutl),30,null,null,false,new IViewTimer(){
			@Override
			public void viewClick(View view){
				uploadMulti(url_img);
			}
		});
		btn_time = (Button) findViewById(R.id.download_btn_time);
		new ViewEvent(btn_time,20,"秒后重试","重试",true,new IViewTimer() {
			@Override
			public void viewClick(View view){
				request_data.setText("开始请求……");
			}
		});
		new ViewEvent(findViewById(R.id.iv),5,null,null,false,new IViewTimer(){
			@Override
			public void viewClick(View view){
				hintDialog.normal(activity,"开始计时了");
			}
		});
		/**取消多文件上传*/
		new ViewEvent(findViewById(R.id.upload_mutl_cancel),10,null,null,false,new IViewTimer(){
			@Override
			public void viewClick(View view){
				removeHttpCancel(mapHttps,url_img);
			}
		});
		/**取消下载QQ任务*/
		new ViewEvent(findViewById(R.id.btn_upload_cancel),0,null,null,false,new IViewTimer(){
			@Override
			public void viewClick(View view){
				if(cancelDownloadQQ != null)cancelDownloadQQ.cancel();
				//removeHttpCancel(mapHttps,mobileqq);
			}
		});
		/**删除已下载QQ文件*/
		new ViewEvent(findViewById(R.id.btn_download_del),1,new IViewTimer(){
			@Override
			public void viewClick(View view){
				if(Downloader.SDCardExists()){
					String dir = Downloader.getSDCardDir();
					dir = dir + "/mobileqq_android.apk";
					boolean b = new File(dir).delete();
					hintDialog.normal(activity,b?"删除成功":"删除失败");
				}
			}
		});
	}

	/**单文件上传 */
	void uploadSingle(){
		final String root = Environment.getExternalStorageDirectory().toString();
		final String path = root + "/IMG_2535.JPG";
		//final String path = root + "/Pictures/Screenshots/20160210_175729.jpg";
		final File file = new File(path);
		final HttpCancel httpCancel = Uploader.getInstance().upload(url_img, file, "fileMImg", new IUploader(){
			@Override
			public void start() {
				up = hintDialog.createLoading(activity,"上传中……");
				up.show();
			}
			
			@Override
			public void onFailure(IOException exception){
				up.dismiss();
				tv_progress.setText(exception.getMessage());
			}

			@Override
			public void onComplete(boolean done){
				if(done){
					request_data.setText("上传完毕,正在处理……");
				}
			}

			@Override
			public void fileName(String name){
				request_data.setText(name);
			}

			@Override
			public void fileSize(long size){
				tv_progress.setText(size / 1024 + "kb");
			}

			@Override
			public void onProgress(final int progress){
				download_progress.setProgress(progress);
				/*if (progress == 100){
					request_data.setText("上传完毕,正在处理……");
				}*/
			}

			@Override
			public void onSuccess(String data){
				up.dismiss();
				request_data.setText(data);
				//ViewTimer.setClickable(findViewById(R.id.layout_download_btn_upload));
			}
		});
		mapHttps.put(url_img,httpCancel);
	}
	
	/**多文件上传*/
	void uploadMulti(final String url_){
		final String root = Environment.getExternalStorageDirectory().toString();
		final String path = root + "/IMG_2535.JPG";
		final File file = new File(path);
		final String path1 = root + "/IMG_20150618_160552.jpg";
		final String path2 = root + "/IMG_20150618_160554.jpg";
		final String path3 = root + "/IMG_20150618_160555.jpg";
		
		/*final String path = root + "/Pictures/Screenshots/20160210_175729.jpg";
		final File file = new File(path);
		final String path1 = root + "/Pictures/Screenshots/20160202_141651.jpg";
		final String path2 = root + "/Pictures/Screenshots/20160210_163944.jpg";
		final String path3 = root + "/Pictures/Screenshots/20160202_143552.jpg";*/
		
		final File file1 = new File(path1);
		final File file2 = new File(path2);
		final File file3 = new File(path3);
		final ArrayList<File> files = new ArrayList<File>();
		files.add(file);
		files.add(file1);
		files.add(file2);
		files.add(file3);
		final HttpCancel httpCancel = Uploader.getInstance().upload(url_,files,"fileMImg",new IUploader(){
			@Override
			public void onComplete(boolean done){
				if(done){
					request_data.setText("上传完毕,正在处理……");
				}
			}
			@Override
			public void fileSize(long size) {
				tv_progress.setText(size / 1024 + "kb");
			}
			@Override
			public void fileName(String name) {
				request_data.setText(name);
			}
			@Override
			public void onProgress(final int progress){
				uploadProgress.setProgress(progress);
				/*if (progress == 100){
					request_data.setText("上传完毕,正在处理……");
				}*/
			}
			@Override
			public void onSuccess(String data){
				request_data.setText(data);
			}
			@Override
			public void onFailure(IOException exception){
				tv_progress.setText(exception.getMessage());
			}
		});
		mapHttps.put(url_,httpCancel);
	}

	void get(){
		final HttpCancel httpCancel = ToolHttp.getInstance().requestGet(url_json, new IRequest(){
			@Override
			public void onSuccess(final String data){
				final HashMap<String, String> map = ToolHttp.parseJsonObject(data);
				if (ToolHttp.succeed(map)){
					final String msg = map.get(ToolHttp.msg);
					request_data.setText(msg);
				} else {
					ToolHttp.hintResult(activity, map);
				}
			}

			@Override
			public void onFailure(final IOException exception){
				request_data.setText("请求失败" + exception);
			}
		});
		mapHttps.put(url_json,httpCancel);
	}

	void post(){
		final HttpCancel httpCancel = ToolHttp.getInstance().requestPost(url_json_restful, new IRequest(){
			@Override
			public void onSuccess(final String data){
				final HashMap<String, String> map = ToolHttp.parseJsonObject(data);
				if (ToolHttp.succeed(map)){
					final String msg = map.get(ToolHttp.msg);
					request_data.setText(msg);
				} else {
					ToolHttp.hintResult(activity, map);
				}
			}
			@Override
			public void onFailure(final IOException exception){
				request_data.setText("请求失败" + exception);
			}
		});
		mapHttps.put(url_json_restful,httpCancel);
	}

	protected void downloadImg(final String url, final String fileName){
		cancelDownloadQQ = Downloader.getInstance().download(url,fileName, true, new IDownLoad(){
			@Override
			public void start(){
				hintDialog.normal(activity,"开始下载");
			}
			@Override
			public void onProgress(final int progress){
				tv_progress.setText("已完成:" + progress + "%");
				download_progress.setProgress(progress);
			}
			@Override
			public void onFailure(IOException e){
				hintDialog.error(activity,"连接服务器失败");
			}
			@Override
			public void errorCode(final int code){
				Downloader.getInstance().showError(activity,code);
			}

			@Override
			public void onComplete(final File file){
				tv_progress.setText("下载完成,存放在:" + file);
			}
		});
		//mapHttps.put(url,httpCancel);
	}

	@Override
	public void onClick(View view){
		switch (view.getId()){
		case R.id.layout_download_tv_count:
			handler.post(runnable);
			tv_count.setClickable(false);
			break;
		default:
			break;
		}
	}

	@Override
	protected void onDestroy(){
		super.onDestroy();
		removeHttpCancel(mapHttps);
		if(cancelDownloadQQ != null)cancelDownloadQQ.cancel();
	}
}