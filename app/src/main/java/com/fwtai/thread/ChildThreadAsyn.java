package com.fwtai.thread;

import android.os.Handler;
import android.os.Message;

import java.util.HashMap;

/**
 * 封装了子线程子类去读取处理远程的数据并返回-有无Activity或HashMap为参数都可以的，如有则写当前的Activity，没有则写null,同理HashMap
 * @作者 田应平
 * @版本 v1.0
 * @提示 一般情况下，类的构造方法里的参数是给该类的成员变量赋初始值的。
 * @创建时间 2015年2月3日 23:50:26
 * @QQ号码 444141300
 * @官网 http://www.yinlz.com
 */
public final class ChildThreadAsyn implements Runnable {

	/**请求远程服务参数*/
	private HashMap<String,String> params;
	/**请求远程服务器的URL地址路径*/
	private String url;
	/**子线程任务的ID标识,当前的Activity内不能有相同的ID标识*/
	private int task;
	/**采用通过Handler子线程读取数据然后更新UI*/
	private Handler handler;

    /**
     * 封装了子线程子类去读取处理远程的数据并返回-无HashMap为参数
     * @param handler 处理异步类
     * @param url 请求的远程的URL路径
     * @param task 任务数 注意，在同一个Activity里不要设置成一样数值
     * @作者 田应平
     * @创建时间 2015-2-3 下午11:48:00
     * @QQ号码 444141300
     * @官网 http://www.yinlz.com
    */
    public ChildThreadAsyn(final String url,final Handler handler,final int task){
        this.handler = handler;
        this.url = url;
        this.task = task;
    }

	/**
	 * 封装了子线程子类去读取处理远程的数据并返回-有HashMap为参数
	 * @param params 参数，即请求的参数,如果没有参数则写null
	 * @param handler 处理异步类
	 * @param url 请求的远程的URL路径
	 * @param task 任务数 注意，在同一个Activity里不要设置成一样数值
	 * @作者 田应平
	 * @创建时间 2015-2-3 下午11:48:00 
	 * @QQ号码 444141300
	 * @官网 http://www.yinlz.com
	*/
	public ChildThreadAsyn(final String url,final HashMap<String,String> params,final Handler handler,final int task){
		this.params = params;
		this.handler = handler;
		this.url = url;
		this.task = task;
	}
	
	@Override
	public void run(){
		//发送给UI主线程,子线程获取数据，Message携带的数据包括Object类型的数据，Message然后把数据传递给Handler
		final Message message = Message.obtain();//推荐使用Message.obtain();因为它同步使用了synchronized锁并是静态的减少内存的并提高性能
		HashMap<String,String> result = new HashMap<String,String>();
        if(params != null && params.size() > 0){
            result = HttpRequest.postRequest(url,this.params);
        }else {
            result = HttpRequest.postRequest(url);
        }
        message.what = task;
        message.obj = result ;
		//发送
		handler.sendMessage(message);
	}
}