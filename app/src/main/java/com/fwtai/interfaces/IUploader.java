package com.fwtai.interfaces;

import java.io.IOException;

/**<strong style='color:#f69;'>文件|图片上传成功后回调<strong>*/
public class IUploader{
	
	/**开始上传*/
	public void start(){};
	/**上传进度百分比%*/
	public void onProgress(final int progress){};
	/**上传成功*/
	public void onSuccess(final String data){};
	/**上传失败*/
	public void onFailure(final IOException exception){};
	/**获取上传文件的大小*/
	public void fileSize(final long size){};
	/**获取文件名|多个文件时以逗号,隔开*/
	public void fileName(final String name){};
	/**文件上传完成*/
	public void onComplete(final boolean done){};
}