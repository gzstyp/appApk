package com.fwtai.thread;

import android.os.Handler;
import android.os.Message;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 封装了子线程子类去读取处理远程的数据并返回-OK
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2015年6月12日 12:22:16
 * @QQ号码 444141300
 * @官网 http://www.yinlz.com
*/
public final class ChildThreadRun{
	
	private Handler handler;
	
	public ChildThreadRun(final Handler handler){
		this.handler = handler ;
	}

	/**
	 * 请求数据-调用本类的子线程读取请求
	 * @param url
	 * @param task
	 * @作者 田应平
	 * @返回值类型 void
	 * @创建时间 2015-6-12 上午12:28:36 
	 * @QQ号码 444141300
	 * @官网 http://www.yinlz.com
	*/
	public final void postRequest(final String url,final int task){
		new Thread(new Runnable(){
			@Override
			public void run(){
				final Message message = Message.obtain();
				message.what = task;
				message.obj = HttpRequest.postRequest(url);
				handler.sendMessage(message);
			}
		}).start();
	}

    public final void postRequest(final String url,final int task,final HashMap<String,String> params){
        new Thread(new Runnable(){
            @Override
            public void run(){
                final Message message = Message.obtain();
                HashMap<String,String> result = new HashMap<String,String>();
                if(params != null && params.size() > 0){
                    result = HttpRequest.postRequest(url,params);
                }else {
                    result = HttpRequest.postRequest(url);
                }
                message.what = task;
                message.obj = result;
                handler.sendMessage(message);
            }
        }).start();
    }

    public final void uploadVideo(final String url,final int task,final ArrayList<File> files){
        new Thread(new Runnable(){
            @Override
            public void run(){
                final Message message = Message.obtain();
                message.what = task;
                message.obj = HttpRequest.uploadVideo(url,files);
                handler.sendMessage(message);
            }
        }).start();
    }

    public final void uploadImages(final String url,final int task,final ArrayList<File> files){
        new Thread(new Runnable(){
            @Override
            public void run(){
                final Message message = Message.obtain();
                message.what = task;
                message.obj = HttpRequest.uploadImages(url,files);
                handler.sendMessage(message);
            }
        }).start();
    }

    public final void uploadPhoto(final String url,final int task,final ArrayList<File> files){
        new Thread(new Runnable(){
            @Override
            public void run(){
                final Message message = Message.obtain();
                message.what = task;
                message.obj = HttpRequest.uploadPhoto(url,files);
                handler.sendMessage(message);
            }
        }).start();
    }
}