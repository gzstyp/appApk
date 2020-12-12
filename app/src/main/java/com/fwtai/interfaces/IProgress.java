package com.fwtai.interfaces;

/**文件|图片上传进度回调*/
public interface IProgress{
	public void onProgress(final long totalBytes,final long remainingBytes,final boolean done);
}