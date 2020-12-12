package com.fwtai.http;

import android.os.Handler;
import android.os.Looper;
import com.fwtai.interfaces.IProgress;
import com.fwtai.interfaces.IUploader;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;
import okio.Source;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * 文件上传处理器
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2017年3月17日 上午10:36:42
 * @QQ号码 444141300
 * @主页 http://www.fwtai.com
*/
public final class Uploader{
	
	private static Uploader instance = null;
	
	private final ArrayList<WeakReference<HttpCancel>> listCancel = new ArrayList<WeakReference<HttpCancel>>(0);
	private static final Handler mHandler = new Handler(Looper.getMainLooper());
	private OkHttpClient client = HttpBase.getInstance().clientGet();
	/**判断是否有效的文件*/
	private static boolean b = true;
	
	/**单例模式*/
	private Uploader(){
		client = HttpBase.getInstance().clientGet();
	}
	
	/**对外提供获取单例模式方法*/
	public static Uploader getInstance(){
		if (instance == null ){
			instance = new Uploader();
		}
		return instance;
	}

	/**单文件上传*/
	public final HttpCancel upload(final String url,final File file,final String name,final IUploader callback){
		b = true;
		final HttpCancel httpCancel = new HttpCancel();
		if(Downloader.SDCardExists()){
			mHandler.post(new Runnable(){
				public void run(){
					if (file == null || file.length() <= 0 || file.isDirectory()){
						callback.onFailure(new IOException("文件有误"));
						b = false;
						return;
					}
				}
			});
			if(b){
				final MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
				try {
					builder.addFormDataPart(name,file.getName(),getRequestBody(file,new IProgress(){
						@Override
						public void onProgress(final long totalBytes,final long remainingBytes,final boolean done){
							final int progress = (int)((totalBytes - remainingBytes) * 100 / totalBytes);
							mHandler.post(new Runnable(){
								public void run(){
									callback.onProgress(progress);
									callback.onComplete(done);
								}
							});
						}
					}));
				} catch (IOException e){
					callback.onFailure(new IOException("上传失败"));
					return httpCancel;
				}
				RequestBody requestBody = builder.build();
				mHandler.post(new Runnable(){
					public void run(){
						callback.fileSize(file.length());
						callback.fileName(file.getName());
						callback.start();
					}
				});
				final Request request = new Request.Builder().url(url).post(requestBody).build();
				httpCancel.call = client.newCall(request);
				listCancel.add(new WeakReference<HttpCancel>(httpCancel));
				handlerUI(httpCancel,callback);
			}
		}else{
			mHandler.post(new Runnable(){
				public void run(){
					callback.onFailure(new IOException("SD卡不可用"));
					return;
				}
			});
		}
		return httpCancel;
	}
	
	/**多文件上传*/
	public final HttpCancel upload(final String url,final ArrayList<File> files,final String name,final IUploader callback){
		b = true;
		final HttpCancel httpCancel = new HttpCancel();
		if (Downloader.SDCardExists()){
			if(files == null || files.size() <= 0 ){
				callback.onFailure(new IOException("文件有误"));
				b = false;
				return httpCancel;
			}else{
				for(File file : files){
					if (file == null || file.length() <= 0 || file.isDirectory()){
						b = false;
						callback.onFailure(new IOException("不是有效的文件"));
						break;
					}
				}
			}
			if(b){
				final RequestBody requestBody = HttpBase.getInstance().initRequestBody(files,name);
				mHandler.post(new Runnable(){
					public void run(){
						try {
							callback.fileSize(requestBody.contentLength());
						} catch (IOException e){
							callback.onFailure(e);
						}
						String fileName = "";
						for (File file : files){
							if (fileName.length() > 0)
								fileName += ",";
							fileName += file.getName();
						}
						callback.fileName(fileName);
						callback.start();
					}
				});
				// 进行包装，使其支持进度回调
				final Request request = new Request.Builder().url(url).post(getRequestBody(requestBody,new IProgress(){
					@Override
					public void onProgress(final long progress, final long total, final boolean done){
						final int p = (int)((100 * progress) / total);
						mHandler.post(new Runnable(){
							public void run(){
								callback.onProgress(p);
								callback.onComplete(done);
							}
						});
					}
				})).build();
				httpCancel.call = client.newCall(request);
				listCancel.add(new WeakReference<HttpCancel>(httpCancel));
				handlerUI(httpCancel,callback);
			}
		}else{
			mHandler.post(new Runnable(){
				public void run(){
					callback.onFailure(new IOException("SD卡不可用"));
					return;
				}
			});
		}
		return httpCancel;
	}
	
	/** 发送消息处理数据更新UI */
	private final void handlerUI(final HttpCancel httpCancel,final IUploader callback){
		if (httpCancel != null && !httpCancel.call.isCanceled()){
			httpCancel.call.enqueue(new Callback(){
				@Override
				public void onResponse(final Call mCall, final Response response){
					if(mCall.isCanceled())return;
					try{
						final String data = response.body().string();
						mHandler.post(new Runnable(){
							public void run(){
								callback.onSuccess(data);
							}
						});
					} catch (final IOException e){
						mHandler.post(new Runnable(){
							public void run(){
								callback.onFailure(e);
							}
						});
					}
				}
				@Override
				public void onFailure(final Call mCall, final IOException exception){
					if(mCall.isCanceled())return;
					mHandler.post(new Runnable(){
						public void run(){
							callback.onFailure(exception);
						}
					});
				}
			});
		}else{}
	}
	
	/**自定义RequestBody对象请求体*/
	private static RequestBody getRequestBody(final File file,final IProgress progress)throws IOException{
		return new RequestBody(){
			@Override
			public long contentLength() throws IOException{
				return file.length();
			}
			@Override
			public MediaType contentType(){
				return MultipartBody.FORM;
			}
			@Override
			public void writeTo(BufferedSink sink) throws IOException{
				final Source source = Okio.source(file);
				final Buffer buf = new Buffer();
				Long remaining = contentLength();
				for (long readCount; (readCount = source.read(buf,2048)) != -1;){
					sink.write(buf, readCount);
					progress.onProgress(contentLength(), remaining -= readCount, remaining == 0);
				}
			}
		};
	}
	
	/**自定义RequestBody对象请求体*/
	private static RequestBody getRequestBody(final RequestBody requestBody,final IProgress progress){
		return new RequestBody(){
		    private BufferedSink bufferedSink;//包装完成的BufferedSink
			@Override
			public long contentLength() throws IOException{
				return requestBody.contentLength();
			}
			@Override
			public MediaType contentType(){
				return requestBody.contentType();
			}
			/**
		     * 重写进行写入
		     * @param sink BufferedSink
		     * @throws IOException 异常
		    */
		    @Override
		    public void writeTo(BufferedSink sink) throws IOException {
		        if (bufferedSink == null){
		            //包装
		            bufferedSink = Okio.buffer(sink(sink));
		        }
		        //写入
		        requestBody.writeTo(bufferedSink);
		        //必须调用flush，否则最后一部分数据可能不会被写入
		        bufferedSink.flush();
		    }

		    /**
		     * 写入，回调进度接口
		     * @param sink Sink
		     * @return Sink
		    */
		    private Sink sink(Sink sink){
		        return new ForwardingSink(sink){
		            //当前写入字节数
		            long bytesWritten = 0L;
		            //总字节长度，避免多次调用contentLength()方法
		            long contentLength = 0L;
		            @Override
		            public void write(Buffer source, long byteCount) throws IOException {
		                super.write(source, byteCount);
		                if (contentLength == 0){
		                    contentLength = contentLength();
		                }
		                bytesWritten += byteCount;//增加当前写入的字节数
		                //回调
		                if (progress!=null){
		                	progress.onProgress(bytesWritten,contentLength, bytesWritten == contentLength);
		                }
		            }
		        };
		    }
		};
	}
}