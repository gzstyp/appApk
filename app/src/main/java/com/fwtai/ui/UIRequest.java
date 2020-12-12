package com.fwtai.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.fwtai.activity.BaseActivity;
import com.fwtai.http.HttpCancel;
import com.fwtai.http.ToolHttp;
import com.fwtai.interfaces.IRequest;
import com.fwtai.interfaces.TitleOnClickListener;
import com.fwtai.widget.TitleBar;
import com.squareup.picasso.Picasso;
import com.yinlz.cdc.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**okHttp使用示例*/
public final class UIRequest extends BaseActivity implements OnClickListener{

	private TitleBar titleBar;
	private Activity activity = this;
	private TextView tv_use_show,tv_use_result,tv_result_show,tv_result;
	private ImageView iv_show;
	
	final String url_image = "http://www.huokebao.net//static/up/adm/20160414/1460619989753343.png";
	final String url = "http://api.yinlz.com/answer/quranswer?helpId=631";
	final String url_img = "http://img.gzhxyc.com/fileMAImg";
	final String url_json  = "http://hguo.org/app/json?rows=1";
	final String url_array = "http://hguo.org/app/jsonArray";
	final String url_params  = "http://hguo.org/app/json?";
	final String img = "http://image98.360doc.com/DownloadImg/2016/07/0614/75410617_13.jpg";
	final String img2 = "http://img-arch.pconline.com.cn/images/upload/upc/tx/photoblog/1302/07/c7/18053032_18053032_1360231173971.jpg";
	
	private final HashMap<String,HttpCancel> mapHttps = new HashMap<String,HttpCancel>(0);
	
	@Override
	protected void onCreate(final Bundle bundle){
		super.onCreate(bundle);
		setContentView(R.layout.layout_request);
		TitleBar.initSystemBar(this);
		initView();
	}
	
	protected void initView(){
		titleBar = (TitleBar) findViewById(R.id.titlebar_request);
		titleBar.setTitle("okHttp应用");
		titleBar.setLeftClickListener(new TitleOnClickListener(){
			@Override
			public void onClick(View view){
				fromRightToLeftFinish();
			}
		});
		titleBar.setRightText("图片");
		titleBar.setRightClickListener(new TitleOnClickListener(){
			@Override
			public void onClick(View view){
				startActivity(new Intent(activity,UIImage.class));
			}
		});
		findViewById(R.id.btn_1).setOnClickListener(this);
		findViewById(R.id.btn_2).setOnClickListener(this);
		findViewById(R.id.btn_3).setOnClickListener(this);
		findViewById(R.id.btn_4).setOnClickListener(this);
		findViewById(R.id.btn_5).setOnClickListener(this);
		findViewById(R.id.btn_6).setOnClickListener(this);
		findViewById(R.id.btn_7).setOnClickListener(this);
		findViewById(R.id.btn_8).setOnClickListener(this);
		findViewById(R.id.btn_9).setOnClickListener(this);
		findViewById(R.id.btn_10).setOnClickListener(this);
		findViewById(R.id.btn_11).setOnClickListener(this);
		
		tv_use_show = (TextView) findViewById(R.id.tv_use_show);
		tv_use_result = (TextView) findViewById(R.id.tv_use_result);
		tv_result_show = (TextView) findViewById(R.id.tv_result_show);
		tv_result = (TextView) findViewById(R.id.tv_result);
		
		iv_show = (ImageView) findViewById(R.id.request_iv_show);
		getImage();
	}
	
	void getImage(){
		Picasso.with(activity).load(url_image).placeholder(R.drawable.temp_icon_18).error(R.drawable.temp_icon_20).into(iv_show);
	}
	
	void setShow(final String value){
		tv_use_show.setText(value);
		tv_use_result.setText(value);
	}
	
	void setResult(final String value){
		tv_use_result.setText(value);
	}
	
	@Override
	public void onClick(View view){
		final String root = Environment.getExternalStorageDirectory().toString();
		final String path1 = root+"/IMG_20150618_160552.jpg";//|/storage/sdcard0/IMG_20150618_160554.jpg
		final String path2 = root+"/IMG_20150618_160554.jpg";
		final String path3 = root+"/IMG_20150618_160555.jpg";
		final File file1 = new File(path1);
		final File file2 = new File(path2);
		final File file3 = new File(path3);
		
		final ArrayList<File> files = new ArrayList<File>();
		final HashMap<String,String> params = new HashMap<String,String>();
		params.put("rows",String.valueOf(1));
		HttpCancel httpCancel = new HttpCancel();
		switch (view.getId()){
		case R.id.btn_1:
			setShow("方法1开始请求");
			httpCancel = ToolHttp.getInstance().requestGet(url,new IRequest(){
				@Override
				public void onFailure(IOException exception){
					setResult("方法1请求失败"+exception);
				}
				@Override
				public void onSuccess(final String data){
					final ArrayList<HashMap<String, String>> list = ToolHttp.parseJsonArray(data);
					String comment = "";
					for (HashMap<String, String> hashMap : list){
						for (String key : hashMap.keySet()){
							if (key.equals("key_comment")){
								HashMap<String, String> map = ToolHttp.parseJsonObject(hashMap.get(key));
								final String contents = "contents";
								if (map.containsKey(contents)){
									if (comment.length() > 0)
										comment += "\n";
									comment += map.get(contents);
								}
							}
						}
					}
					setResult(comment);
				}
			});
			mapHttps.put(url,httpCancel);
			break;
		case R.id.btn_2:
			setShow("方法2开始请求");
			httpCancel = ToolHttp.getInstance().requestPost(url_json,new IRequest(){
				@Override
				public void onSuccess(final String data){
					final HashMap<String, String> map = ToolHttp.parseJsonObject(data);
					if (ToolHttp.succeed(map)){
						final String msg = map.get(ToolHttp.msg);
						setResult(msg);
					} else {
						ToolHttp.hintResult(activity,map);
					}
				}
				@Override
				public void onFailure(final IOException exception){
					setResult("方法2请求失败"+exception);
				}
			});
			mapHttps.put(url_json,httpCancel);
			break;
		case R.id.btn_3:
			setShow("方法2开始请求");
			httpCancel = ToolHttp.getInstance().requestPost(url_params,params,new IRequest(){
				@Override
				public void onSuccess(final String data){
					final HashMap<String, String> map = ToolHttp.parseJsonObject(data);
					if (ToolHttp.succeed(map)){
						final String msg = map.get(ToolHttp.msg);
						setResult(msg);
					} else {
						ToolHttp.hintResult(activity,map);
					}
				}
				@Override
				public void onFailure(final IOException exception){
					setResult("方法2请求失败"+exception);
				}
			});
			mapHttps.put(url_params,httpCancel);
			break;
		case R.id.btn_4:
			files.add(file1);
			files.add(file2);
			files.add(file3);
			setShow("方法4开始请求-文件上传");
			ToolHttp.getInstance().requestPost(url_img,files,"fileMImg",new IRequest(){
				@Override
				public void onSuccess(final String data){
					setResult("方法4请求成功"+data);
				}
				@Override
				public void onFailure(final IOException exception){
					setResult("方法4请求失败"+exception);
				}
			});
			break;
		case R.id.btn_5:
			files.add(file1);
			files.add(file2);
			files.add(file3);
			setShow("方法5开始请求-文件上传");
			ToolHttp.getInstance().requestPost(url_img,params,files,"fileMImg",new IRequest(){
				@Override
				public void onSuccess(final String data){
					setResult("方法5请求成功"+data);
				}
				@Override
				public void onFailure(final IOException exception){
					setResult("方法5请求失败"+exception);
				}
			});
			break;
		case R.id.btn_6:
			tv_result_show.setText("方法6-1请求开始");
			tv_result.setText("方法6-2请求开始");
			ToolHttp.getInstance().requestGet(url_json,new IRequest(){
				@Override
				public void onSuccess(String data) {
					tv_result_show.setText("方法6-1请求成功"+data);
				}
				@Override
				public void onFailure(IOException exception) {
					tv_result_show.setText("方法6-1请求失败"+exception);
				}
			});
			ToolHttp.getInstance().requestGet(url_array,new IRequest(){
				@Override
				public void onSuccess(String data){
					tv_result.setText("方法6-2请求成功"+data);
				}
				@Override
				public void onFailure(IOException exception) {
					tv_result.setText("方法6-2请求失败"+exception);
				}
			});
			break;
		case R.id.btn_7:
			tv_result_show.setText("方法7-1请求开始");
			tv_result.setText("方法7-2请求开始");

			files.add(file1);
			files.add(file2);
			files.add(file3);
			ToolHttp.getInstance().requestPost(url_img,files,"fileMImg",new IRequest(){
				@Override
				public void onSuccess(String data){
					tv_result_show.setText("方法7-1请求成功"+data);
				}
				@Override
				public void onFailure(IOException exception) {
					tv_result_show.setText("方法7-1请求失败"+exception);
				}
			});
			ToolHttp.getInstance().requestPost(url_img,params,files,"fileMImg",new IRequest(){
				@Override
				public void onSuccess(String data) {
					tv_result.setText("方法7-2请求成功"+data);
				}
				@Override
				public void onFailure(IOException exception) {
					tv_result.setText("方法7-2请求失败"+exception);
				}
			});
			break;
		case R.id.btn_8:
			break;
		case R.id.btn_9:
			break;
		case R.id.btn_10:
			final Intent intent = new Intent(this,UIDownload.class);
			startActivity(intent);
			break;
		case R.id.btn_11:
			tv_use_show.setText("");
			tv_use_result.setText("");
			tv_result_show.setText("");
			tv_result.setText("");
			break;
		default:
			break;
		}
	}
	
	@Override
	protected void onDestroy(){
		super.onDestroy();
		removeHttpCancel(mapHttps);
	}
}