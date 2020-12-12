package com.fwtai.interfaces;

import java.io.File;
import java.io.IOException;

/**下载文件回调*/
public abstract class IDownLoad{
	
	/**开始下载*/
	public void start(){};
    public abstract void onComplete(final File file);
    /**下载失败*/
    public void onFailure(final IOException e){};
    /**201:SD卡不可用;202:网络出现异常;404:下载失败,所下载的文件路径不存在;416:下载失败,该文件已下载;*/
    public void errorCode(final int code){};
    /**0-100*/
    public void onProgress(final int progress){};
    public void onProgress(final long current,final long total){};
}