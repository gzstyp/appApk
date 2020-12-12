package com.fwtai.tool;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串的工具类|含json对象或json数组解析
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2015年11月27日 18:25:12
 * @QQ号码 444141300
 * @官网 http://www.fwtai.com
*/
public final class ToolString{

    private static ToolString instance = null;

    private ToolString(){}
    /**对外提供获取单例模式方法*/
    public final static ToolString getInstance(){
        if (instance == null){
            instance = new ToolString();
        }
        return instance;
    }

	/**
	 * 转码
	 * @param object
	 * @return
	 * @作者 田应平
	 * @返回值类型 String
	 * @创建时间 2015-9-6 下午5:23:06 
	 * @QQ号码 444141300
	 * @官网 http://www.blidian.com
	*/
	public final static String transCoding(Object object){
		if(object != null){
			try {
				return new String(object.toString().getBytes("ISO8859-1"),"UTF-8");
			} catch (Exception e){
				return null;
			}
		}
		return null;
	}
	
	/**生成32位唯一的字符串*/
	public final static String getIdsChar32(){
		return UUID.randomUUID().toString().replace("-", "");//生成32位唯一的字符串
	}

    /**
	 * 验证是否为空,为空时返回true,否则返回false,含obj是否为 _单独的下划线特殊字符,是则返回true,否则返回false
	 * @作者: 田应平
	 * @主页 www.fwtai.com
	 * @创建日期 2016年8月18日 17:34:24
	*/
	public final static boolean isBlank(final Object obj){
		if(obj == null)return true;
		if(obj instanceof List<?>){
			final List<?> list = (List<?>) obj;
			if(list == null || list.size() <= 0)return true;
			return false;
		}
		if(obj instanceof Map<?, ?>){
			final Map<?,?> map = (Map<?, ?>) obj;
			if(map == null || map.size() <= 0)return true;
			return false;
		}
		if(obj instanceof String[]){
			boolean flag = false;
			final String[] require = (String[])obj;
			for(int i = 0; i < require.length; i++){
				if(require[i] == null || require[i].length() == 0 || require[i].replaceAll("\\s*", "").length() == 0){
					flag = true;
					break;
				}
			}
			return flag;
		}
		if(obj instanceof HashMap<?,?>){
			final HashMap<?, ?> hashMap = (HashMap<?,?>) obj;
			if(hashMap == null || hashMap.size() <= 0)return true;
			return false;
		}
		final String key = obj.toString().replaceAll("\\s*", "");
		if(key.equals("") || key.length() == 0 || obj.toString().toLowerCase(Locale.CHINESE).equals("null"))return true;
		if(key.length() == 1 && key.equals("_"))return true;
		return false;
	}
	
	/**
	 * 去除空格,如果为空则返回null,若有且只有_也只返回null否则去除前后空格后返回
	 * @param obj
	 * @作者 田应平
	 * @返回值类型 String
	 * @创建时间 2015-8-18 下午2:03:25 
	 * @QQ号码 444141300
	 * @注释 \s 匹配任何空白字符，包括空格、制表符、换页符,* 匹配前面的子表达式零次或多次。
	 * @官网 http://www.fwtai.com
	*/
	public final static String wipeSpace(final Object obj){
		if(isBlank(obj))return null;
		final String key = obj.toString().replaceAll("\\s*", "");
		if(key.length() == 1 && key.equals("_"))return null;
		return key;
	}
	
	/**
	 * 是否大于指定长度,若大于返回true否则返回false
	 * @param value
	 * @param length
	 * @作者 田应平
	 * @返回值类型 boolean
	 * @创建时间 2015-12-22 上午3:25:17 
	 * @QQ号码 444141300
	 * @官网 http://www.fwtai.com
	*/
	public final static boolean isTooLong(final String value,final int length){
		if(value.length() > length)return true;
		return false;
	}
	
	/**
	 * 验证输入值是否是正整数,是正整数返回true,否则返回false
	 * @param value
	 * @作者 田应平
	 * @返回值类型 boolean
	 * @创建时间 2015-12-9 下午6:11:36 
	 * @QQ号码 444141300
	 * @官网 http://www.fwtai.com
	*/
	public static boolean checkNumber(String value){
		String reg = "^\\d*[1-9]\\d*$";
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(value); 
		return m.matches(); 
	}
	
	/**
	 * 生成时间格式:yyyy-MM-dd HH:mm:ss
	 * @作者 田应平
	 * @返回值类型 String
	 * @创建时间 2017年1月10日 09:35:02 
	 * @QQ号码 444141300
	 * @官网 http://www.fwtai.com
	*/
	public final static String getTime(){
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINESE).format(new Date());//设置日期格式
	}
	
	/**
	 * ids字符串数组转换为list< String >
	 * @param ids 格式如:12958292,12951500,12977780,12997129
	 * @param splitFlag 以 splitFlag 为拆分标识
	 * @作者 田应平
	 * @返回值类型 List< String >
	 * @创建时间 2016年1月21日 12:20:36
	 * @QQ号码 444141300
	 * @官网 http://www.fwtai.com
	*/
	public final static ArrayList<String> keysToList(final String ids,String splitFlag){
		if(ToolString.isBlank(ids))return null;
		final ArrayList<String> list = new ArrayList<String>();
		if(isBlank(splitFlag)){
			splitFlag = ",";
		}
		final String[] arr = ids.split(splitFlag);
		if(arr.length == 0)return null;
		for(int i = 0; i < arr.length; i++){
			list.add(arr[i]);
		}
		return list;
	}
	
	/**
	 * ids字符串数组转换为String
	 * @param ids 格式如:12958292,12951500,12977780,12997129
	 * @param splitFlag 以 splitFlag 为拆分标识
	 * @作者 田应平
	 * @返回值类型 List< String >
	 * @创建时间 2017年1月10日 09:34:24
	 * @QQ号码 444141300
	 * @官网 http://www.fwtai.com
	*/
	public final static String[] keysToArarry(final String ids,String splitFlag){
		if(ToolString.isBlank(ids))return null;
		if(isBlank(splitFlag)){
			splitFlag = ",";
		}
		final String[] arr = ids.split(splitFlag);
		if(arr.length == 0)return null;
		return arr;
	}
	
	/**
	 * 根据完整文件路径删除文件
	 * @作者 田应平
	 * @返回值类型 boolean
	 * @创建时间 2017年1月10日 09:34:49 
	 * @QQ号码 444141300
	 * @官网 http://www.fwtai.com
	*/
	public final static boolean deleFileByRealPath(final String filePath){
		//删除附件
		if(!isBlank(filePath) && filePath.indexOf(".") != -1){
			try {
				File f = new File(filePath);
				if(f.exists()){
					f.delete();
				}
				return true ;
			} catch (Exception e){
			}
		}
		return false ;
	}
	
	/**
	 * 删除文件
	 * @作者 田应平
	 * @创建时间 2017年1月10日 上午10:50:23
	 * @QQ号码 444141300
	 * @主页 http://www.fwtai.com
	*/
	public final static void delete(final File file){
		if(file.isFile()){
			file.delete();
			return;
		}
		if(file.isDirectory()){
			File[] childFiles = file.listFiles();
			if(childFiles == null || childFiles.length == 0){
				file.delete();
				return;
			}
			for(int i = 0; i < childFiles.length; i++){
				delete(childFiles[i]);
			}
			file.delete();
		}
	}
	
	/**
	 * html网页的img替换规则
	 * @param html
	 * @作者 田应平
	 * @返回值类型 String
	 * @创建时间 2016年1月23日 17:33:21
	 * @QQ号码 444141300
	 * @官网 http://www.fwtai.com
	 * @示例 html = html.replaceAll("src=\"/upimg","src=\""+url+"/upimg");
	*/
	public final static String htmlReplace(final String html,final String flagOld,final String flagNew){
		return html.replaceAll(flagOld,flagNew);
	}
	
	/**
	 * 判断一个字符串是否是纯数字字符串
	 * @作者 田应平
	 * @返回值类型 boolean
	 * @创建时间 2017年3月8日 上午8:23:11
	 * @QQ号码 444141300
	 * @主页 http://www.fwtai.com
	*/
	public final static boolean isNumberStr(final String str){
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e){
			return false;
		}
	}
	
	/**获取文件的后缀名|扩展名*/
	public final static String getFileExtension(final String str){
		return str.substring(str.lastIndexOf('.')+1,str.length());
	}
	
	/**获取文件的文件名,不含扩展名后缀名*/
	public final static String getFileName(final String str){
		return str.substring(str.lastIndexOf('/')+1,str.lastIndexOf('.'));
	}
	
	/**获取文件的文件名,含扩展名后缀名*/
	public final static String getFileNameExt(final String str){
		return str.substring(str.lastIndexOf('/')+1);
	}
	
	/**
	 * 流转化字符串
	 * @param is
	 * @return
	*/
	public static String readStream(InputStream is){
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int len = 0;
			byte[] date = new byte[1024];
			while ((len = is.read(date)) != -1){
				bos.write(date, 0, len);
			}
			is.close();
			bos.close();
			byte[] result = bos.toByteArray();
			String temp = new String(result);
			return temp;
		} catch (IOException e){
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 截取字符串,并加省略号…
	 * @param obj  欲截取的obj
	 * @param length 截取的长度
	 * @作者 田应平
	 * @返回值类型 String
	 * @创建时间 2015年3月26日 13:30:21
	 * @QQ号码 444141300
	 * @官网 http://www.fwtai.com
	*/
	public final static String cutStr(Object obj,int length){
		if(!isBlank(obj)){
			String txt = String.valueOf(obj);
			if(txt.length() > length){
				return txt.substring(0, length-1)+"…";
			}
			return txt ;
		}
		return "" ;
	}
	
	/**
	 * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
	 * @param input
	 * @return boolean
	*/
	public final static boolean isEmpty(final String input){
		if(isBlank(input))return true;
		for (int i = 0; i < input.length(); i++){
			char c = input.charAt(i);
			if(c != ' ' && c != '\t' && c != '\r' && c != '\n'){
				return false;
			}
		}
		return true;
	}
	
	/**
     * 给定的值是否为空,null不算,为空则给个默认值显示
     * @param value 欲要判断的值
     * @param defaultValue 默认值
     * @作者 田应平
     * @返回值类型 String
     * @创建时间 2015年3月25日 12:17:24
     * @QQ号码 444141300
     * @官网 http://www.fwtai.com
    */
    public final static String replace(final Object value,final String defaultValue){
        if(isBlank(value)){
            return defaultValue;
        }
        return value.toString();
    }

    public final String replace(final Object value){
        if(isBlank(value)){
            return "";
        }
        return value.toString();
    }
    
    /**
	 * 将一个InputStream流转换成字符串
	 * @param inputStream
	 * @throws IOException 
	 * @作者 田应平 
	 * @创建时间 2014-9-13 
	 * @QQ号码 444141300 
	 * @主页 www.fwtai.com
	*/
	public final static byte[] readData(InputStream inputStream) throws IOException{
		if(inputStream != null){
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = - 1 ;
			while((len = inputStream.read(buffer)) != -1){
				outputStream.write(buffer, 0, len);
			}
			byte[] result = outputStream.toByteArray();
			outputStream.close();//关闭流
			inputStream.close();
			return result ;
		}
		return null;
	}
	
	/**
	 * 字符串转布尔值
	 * @param b
	 * @return 转换异常返回 false
	*/
	public final static boolean toBool(String b){
		try {
			return Boolean.parseBoolean(b);
		} catch (Exception e){
		}
		return false;
	}
	
	/**
     * 验证URL地址
     * @param url 格式：http://blog.csdn.net:80/xyang81/article/details/7705960? 或 http://www.csdn.net:80
     * @return 验证成功返回true，验证失败返回false
    */ 
    public final static boolean checkURL(String url){ 
        String regex = "(https?://(w{3}\\.)?)?\\w+\\.\\w+(\\.[a-zA-Z]+)*(:\\d{1,5})?(/\\w*)*(\\??(.+=.*)?(&.+=.*)?)?"; 
        return Pattern.matches(regex, url); 
    }
    
    /**
     * 验证中文
     * @param chinese 中文字符
     * @return 验证成功返回true，验证失败返回false
    */ 
    public final static boolean checkChinese(String chinese){ 
        String regex = "^[\u4E00-\u9FA5]+$"; 
        return Pattern.matches(regex,chinese); 
    }

    /**判断是否还有中文或英文字符串*/
    public final static int checkString(final String str){
		int res = -1;
		if(str != null){
			for (int i = 0; i < str.length(); i++){
				// 只要字符串中有中文则为中文
				if(!checkChar(str.charAt(i))){
					res = 0;// "中文";
					break;
				} else {
					res = 1;// "英文";
				}
			}
		}
		return res;
	}

    /**判断是否还有中文或英文字符串*/
	public final static boolean checkChar(final char ch){
		if((ch + "").getBytes().length == 1){
			return true;// 英文
		} else {
			return false;// 中文
		}
	}
}