package com.fwtai.tool;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;

public final class ToolsFile {
	private static final String BaseFilePath = Environment.getExternalStorageState()+"/msjy/";
	public static final String sdcardImagePath = BaseFilePath + "image/";
	public static long getFileSizes(File f) throws Exception {
		long s = 0;
		if (f.exists()) {
			FileInputStream fis = null;
			fis = new FileInputStream(f);
			s = fis.available();
			fis.close();
		} else {
			f.createNewFile();
		}
		return s;
	}

	public static long getFileSize(File f) throws Exception {
		if(!f.exists())
			return 0;
		long size = 0;
		File flist[] = f.listFiles();
		for (int i = 0; i < flist.length; i++) {
			File file = flist[i];
			if (file.isDirectory()) {
				size = size + getFileSize(file);
			} else {
				size = size + file.length();
			}
		}
		return size;
	}

	public static String FormetFileSize(long fileS) {// 转换文件大小
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "";
		if (fileS == 0 )
			fileSizeString = "暂无缓存";
		else if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "K";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "M";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "G";
		}
		return fileSizeString;
	}

	public static long getlist(final File f) {// 递归求取目录文件个数
		long size = 0;
		File flist[] = f.listFiles();
		size = flist.length;
		for (int i = 0; i < flist.length; i++) {
			File file = flist[i];
			if (file.isDirectory()) {
				size = size + getlist(file);
				size--;
			}
		}
		return size;
	}

	public final static void delete(final File file){
		if (file.isFile()) {
			file.delete();
			return;
		}
		if (file.isDirectory()) {
			File[] childFiles = file.listFiles();
			if (childFiles == null || childFiles.length == 0) {
				file.delete();
				return;
			}
			for (int i = 0; i < childFiles.length; i++) {
				delete(childFiles[i]);
			}
			file.delete();
		}
	}
	
	/**
	 * 删除指定文件夹下的所有文件，不删除该文件夹下的目录
	 * @param file
	 * @作者 田应平
	 * @返回值类型 void
	 * @创建时间 2016年4月7日 下午1:45:08 
	 * @QQ号码 444141300
	 * @官网 http://www.yinlz.com
	*/
	public final static void deleteFile(final File file){
		if (file.isFile()){ 
            file.delete(); 
            return; 
        }
		 if (file.isDirectory()){
             File[] childFile = file.listFiles(); 
             if (childFile == null || childFile.length == 0) { 
                 return; 
             } 
             for (File f : childFile) { 
            	 deleteFile(f); 
             } 
         }
	}
	
	/**
	 * 获取uri返回的真是路径
	 * @param a_iUri
	 * @param context
	 * @return
	 */
	public static String getUriPath(Uri a_iUri, Context context) {//content://media/external/images/media/334894
		String l_uriScheme = a_iUri.getScheme();
		if ("content".equals(l_uriScheme)) {
			return getUriRealPathFromContent(a_iUri, context);//content://media/external/images/media/334894
		}
		if ("file".equals(l_uriScheme)) {
			return a_iUri.getPath();//从SD卡传递来的文件地址
		}
		return null;
	}
	
	/**
	 * 获取相册 传递过来的URI的地址
	 * @param yuanUri
	 * @return
	 */
	private static String getUriRealPathFromContent(Uri yuanUri, Context context) {
		String pathString = "";
		ContentResolver cr = context.getContentResolver();
		//String[] proj = {MediaStore.Images.Media.DATA};//
		//Cursor cursor = cr.query(yuanUri, proj, null, null, null);//
		Cursor cursor = cr.query(yuanUri, null, null, null, null);
		//按我个人理解 这个是获得用户选择的图片的索引值
        //int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		if (cursor == null)
			return null;
		cursor.moveToFirst();//将光标移至开头 ，这个很重要，不小心很容易引起越界
		for (int i = 0; i < cursor.getColumnCount(); i++) {
			if (cursor.getColumnName(i).equals("_data")) {
				pathString = cursor.getString(i);
				break;
			}
		}
		return pathString;
	}
	
	/**
	 * 判断手机内存卡是否存在?存在返回true,否则返回false
	 * @作者 田应平
	 * @返回值类型 boolean
	 * @创建时间 2016年2月21日 19:00:49
	 * @QQ号码 444141300
	 * @官网 http://www.yinlz.com
	*/
	public static final boolean checkSDcard(){
		if(Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))return true;
		return false;
	}
	
	/**
	 * 获取内存卡的根目录,使用前必须if(FileTools.checkSDcard()){}判断是否存在内存卡！
	 * @作者 田应平
	 * @返回值类型 String
	 * @创建时间 2016年2月21日 19:03:39
	 * @QQ号码 444141300
	 * @官网 http://www.yinlz.com
	*/
	public static final String getRootDir(){
		return String.valueOf(Environment.getExternalStorageDirectory());
	}
	
	public final static File inputstreamToFile(InputStream ins, File file) {
		try {
			OutputStream os = new FileOutputStream(file);
			int bytesRead = 0;
			byte[] buffer = new byte[2048];
			while ((bytesRead = ins.read(buffer, 0, 2048)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
			os.close();
			ins.close();
			return file;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}