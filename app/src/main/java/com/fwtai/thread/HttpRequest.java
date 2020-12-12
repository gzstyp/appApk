package com.fwtai.thread;

import com.fwtai.tool.ToolString;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class HttpRequest{

    /**
     * 第一个参数是要传递到远程服务器的参数，第二个参数是远程服务器的Url请求路径,第3个参数是对返回值字符编码格式,如，utf-8---完整的写法 OK,更为强大,无参数
     * @param url
     * @返回值 只要判断其ToolsString.stringIsNotNull(result.get("result").toString())是否为true，是true说明服务器运行异常，<br />
     * 否则解析其result.get("result")的json数据后，如果返回的listResult == null || listResult.isEmpty()是否有数据或值
     * @return HashMap< String, Object>
     * @作者 田应平
     * @创建时间 2015年1月8日 03:23:12
     * @QQ号码 444141300
     * @主页URL http://www.yinlz.com
    */
    public final static HashMap<String,String> postRequest(final String url){
        final HashMap<String,String> resultHashMap = new HashMap<String,String>();
        try {
            //创建连接对象
            final HttpClient httpClient = new DefaultHttpClient();
            //请求超时，设置ConnectionTimeout：这定义了通过网络与服务器建立连接的超时时间。
            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 4500);
            //读取超时，这定义了Socket读数据的超时时间，即从服务器获取响应数据需要等待的时间，此处设置为4.5秒。
            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 4500);
            final HttpPost httpPost = new HttpPost(url);
            final List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
            // 请求实体HttpEntity也是一个接口，我们用它的实现类UrlEncodedFormEntity来创建对象，注意后面一个String类型的参数是用来指定编码的
            //HttpEntity entity = new UrlEncodedFormEntity(parameters, "UTF-8");
            //request.setEntity(entity);
            //纯文本表单，不包含文件上传不包含文件域
            final UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(parameters,"UTF-8");
            httpPost.setEntity(encodedFormEntity);
            final HttpResponse response = httpClient.execute(httpPost);//执行提交方法;
            int code = response.getStatusLine().getStatusCode();
            if (code == 200 ){
                final String result = EntityUtils.toString(response.getEntity(),"UTF-8");
                //resultHashMap.put(ToolHttp.code,ToolHttp.code200);
                //resultHashMap.put(ToolHttp.obj,result);
                return resultHashMap;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //resultHashMap.put(ToolHttp.code,ToolHttp.code199);
        //resultHashMap.put(ToolHttp.obj,"连接服务器失败");
        return resultHashMap;
    }

    /**
     * 第一个参数是要传递到远程服务器的参数，第二个参数是远程服务器的Url请求路径,第3个参数是对返回值字符编码格式,如，utf-8---完整的写法 OK,更为强大
     * @param url
     * @param params
     * @返回值 只要判断其ToolsString.stringIsNotNull(result.get("result").toString())是否为true，是true说明服务器运行异常，<br />
     * 否则解析其result.get("result")的json数据后，如果返回的listResult == null || listResult.isEmpty()是否有数据或值
     * @return HashMap< String, Object>
     * @作者 田应平
     * @创建时间 2015年1月8日 03:23:12
     * @QQ号码 444141300
     * @主页URL http://www.yinlz.com
    */
    public final static HashMap<String,String> postRequest(final String url,final HashMap<String,String> params){
        final HashMap<String,String> resultHashMap = new HashMap<String,String>();
        try {
            //创建连接对象
            final HttpClient httpClient = new DefaultHttpClient();
            //请求超时，设置ConnectionTimeout：这定义了通过网络与服务器建立连接的超时时间。
            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 4500);
            //读取超时，这定义了Socket读数据的超时时间，即从服务器获取响应数据需要等待的时间，此处设置为4.5秒。
            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 4500);
            final HttpPost httpPost = new HttpPost(url);
            final List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
            //表单的封装
            if (params != null && params.size() > 0){
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    final String keyName = entry.getKey();
                    String keyValue = entry.getValue();
                    final BasicNameValuePair valuePair = new BasicNameValuePair(keyName,keyValue);
                    parameters.add(valuePair);//循环添加add
                }
            }
            final UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(parameters,"UTF-8");
            httpPost.setEntity(encodedFormEntity);
            final HttpResponse response = httpClient.execute(httpPost);//执行提交方法;
            int code = response.getStatusLine().getStatusCode();
            if (code == 200 ){
                final String result = EntityUtils.toString(response.getEntity(),"UTF-8");
                //resultHashMap.put(ToolHttp.code,ToolHttp.code200);
                //resultHashMap.put(ToolHttp.obj,result);
                return resultHashMap;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //resultHashMap.put(ToolHttp.code,ToolHttp.code199);
        //resultHashMap.put(ToolHttp.obj,"连接服务器失败");
        return resultHashMap;
    }

    /**
     * (单|多)图片上传
     * @param url 处理接口
     * @param files pictures
     * @return String result of Service response
     * @throws IOException
    */
    public final static HashMap<String,String> uploadImages(final String url,final ArrayList<File> files){
        final HashMap<String,String> result = new HashMap<String,String>();
        try {
            final String boundary = ToolString.getIdsChar32();
            final String prefix = "--", linend = "\r\n";
            final String formData = "multipart/form-data";
            final URL uri = new URL(url);
            final HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
            conn.setReadTimeout(10 * 1000); // 缓存的最长时间
            conn.setDoInput(true);// 允许输入
            conn.setDoOutput(true);// 允许输出
            conn.setUseCaches(false); // 不允许使用缓存
            conn.setRequestMethod("POST");
            conn.setRequestProperty("connection","keep-alive");
            conn.setRequestProperty("Charsert", "UTF-8");
            conn.setRequestProperty("Content-Type", formData + ";boundary=" + boundary);
            // 首先组拼文本类型的参数
            final DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());
            // 发送文件数据
            for(int i = 0; i < files.size(); i++){
                final File file = files.get(i);
                StringBuilder sb1 = new StringBuilder();
                sb1.append(prefix);
                sb1.append(boundary);
                sb1.append(linend);
                sb1.append("Content-Disposition: form-data; name=images; filename=\"" + file.getName() + "\"" + linend);
                sb1.append("Content-Type: application/octet-stream; charset=" + "UTF-8" + linend);
                sb1.append(linend);
                outStream.write(sb1.toString().getBytes());
                final InputStream is = new FileInputStream(file);
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = is.read(buffer)) != -1) {
                    outStream.write(buffer, 0, len);
                }
                is.close();
                outStream.write(linend.getBytes());
            }
            // 请求结束标志
            byte[] end_data = (prefix + boundary + prefix + linend).getBytes();
            outStream.write(end_data);
            outStream.flush();
            // 得到响应码
            final int res = conn.getResponseCode();
            final InputStream in = conn.getInputStream();
            final StringBuilder sb2 = new StringBuilder();
            if (res == 200) {
                int ch;
                while ((ch = in.read()) != -1) {
                    sb2.append((char)ch);
                }
            }
            outStream.close();
            conn.disconnect();
            //result.put(ToolHttp.code,ToolHttp.code200);
            //result.put(ToolHttp.obj,sb2.toString());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            //result.put(ToolHttp.code,"199");
            //result.put(ToolHttp.obj,"网络链接失败");
            return result;
        }
    }

    /**
     * (单|多)视频上传
     * @param @param url 处理接口
     * @param files 视频
     * @return String result of Service response
     * @throws IOException
    */
    public final static HashMap<String,String> uploadVideo(final String url,final ArrayList<File> files){
        final HashMap<String,String> result = new HashMap<String,String>();
        try {
            final String boundary = ToolString.getIdsChar32();
            final String prefix = "--", linend = "\r\n";
            final String formData = "multipart/form-data";
            final URL uri = new URL(url);
            final HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
            conn.setReadTimeout(10 * 1000); // 缓存的最长时间
            conn.setDoInput(true);// 允许输入
            conn.setDoOutput(true);// 允许输出
            conn.setUseCaches(false); // 不允许使用缓存
            conn.setRequestMethod("POST");
            conn.setRequestProperty("connection","keep-alive");
            conn.setRequestProperty("Charsert", "UTF-8");
            conn.setRequestProperty("Content-Type", formData + ";boundary=" + boundary);
            // 首先组拼文本类型的参数
            final DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());
            // 发送文件数据
            for(int i = 0; i < files.size(); i++){
                final File file = files.get(i);
                final StringBuilder sb1 = new StringBuilder();
                sb1.append(prefix);
                sb1.append(boundary);
                sb1.append(linend);
                sb1.append("Content-Disposition: form-data; name=video; filename=\"" + file.getName() + "\"" + linend);
                sb1.append("Content-Type: application/octet-stream; charset=" + "UTF-8" + linend);
                sb1.append(linend);
                outStream.write(sb1.toString().getBytes());
                final InputStream is = new FileInputStream(file);
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = is.read(buffer)) != -1) {
                    outStream.write(buffer, 0, len);
                }
                is.close();
                outStream.write(linend.getBytes());
            }
            // 请求结束标志
            final byte[] end_data = (prefix + boundary + prefix + linend).getBytes();
            outStream.write(end_data);
            outStream.flush();
            // 得到响应码
            final int res = conn.getResponseCode();
            final InputStream in = conn.getInputStream();
            final StringBuilder sb2 = new StringBuilder();
            if (res == 200) {
                int ch;
                while ((ch = in.read()) != -1) {
                    sb2.append((char)ch);
                }
            }
            outStream.close();
            conn.disconnect();
            //result.put(ToolHttp.code,ToolHttp.code200);
            //result.put(ToolHttp.obj,sb2.toString());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            //result.put(ToolHttp.code,"199");
            //result.put(ToolHttp.obj,"网络链接失败");
            return result;
        }
    }

    /**
     * (单|多)头像上传
     * @param url 处理接口
     * @param files 头像文件
     * @throws IOException
     */
    public final static HashMap<String,String> uploadPhoto(final String url,final ArrayList<File> files){
        final HashMap<String,String> result = new HashMap<String,String>();
        try {
            final String boundary = ToolString.getIdsChar32();
            final String prefix = "--", linend = "\r\n";
            final String formData = "multipart/form-data";
            final URL uri = new URL(url);
            final HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
            conn.setReadTimeout(10 * 1000); // 缓存的最长时间
            conn.setDoInput(true);// 允许输入
            conn.setDoOutput(true);// 允许输出
            conn.setUseCaches(false); // 不允许使用缓存
            conn.setRequestMethod("POST");
            conn.setRequestProperty("connection","keep-alive");
            conn.setRequestProperty("Charsert", "UTF-8");
            conn.setRequestProperty("Content-Type",formData + ";boundary=" + boundary);
            // 首先组拼文本类型的参数
            final DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());
            // 发送文件数据
            for(int i = 0; i < files.size(); i++){
                final File file = files.get(i);
                final StringBuilder sb1 = new StringBuilder();
                sb1.append(prefix);
                sb1.append(boundary);
                sb1.append(linend);
                sb1.append("Content-Disposition: form-data; name=photo; filename=\"" + file.getName() + "\"" + linend);
                sb1.append("Content-Type: application/octet-stream; charset=" + "UTF-8" + linend);
                sb1.append(linend);
                outStream.write(sb1.toString().getBytes());
                final InputStream is = new FileInputStream(file);
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = is.read(buffer)) != -1) {
                    outStream.write(buffer, 0, len);
                }
                is.close();
                outStream.write(linend.getBytes());
            }
            // 请求结束标志
            byte[] end_data = (prefix + boundary + prefix + linend).getBytes();
            outStream.write(end_data);
            outStream.flush();
            // 得到响应码
            final int res = conn.getResponseCode();
            final InputStream in = conn.getInputStream();
            final StringBuilder sb2 = new StringBuilder();
            if (res == 200) {
                int ch;
                while ((ch = in.read()) != -1) {
                    sb2.append((char)ch);
                }
            }
            outStream.close();
            conn.disconnect();
            //result.put(ToolHttp.code,ToolHttp.code200);
            //result.put(ToolHttp.obj,sb2.toString());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            //result.put(ToolHttp.code,"199");
            //result.put(ToolHttp.obj,"网络链接失败");
            return result;
        }
    }

    /**
     * 支持(单|多)文件上传
     * @param url 处理接口
     * @param files 头像文件
     * @param flag 可选值 images|image|video|photo|videos|file
     * @throws IOException
     */
    public final static HashMap<String,String> uploadFile(final String url,final ArrayList<File> files,final String flag){
        final HashMap<String,String> result = new HashMap<String,String>();
        try {
            final String boundary = ToolString.getIdsChar32();
            final String prefix = "--", linend = "\r\n";
            final String formData = "multipart/form-data";
            final URL uri = new URL(url);
            final HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
            conn.setReadTimeout(10 * 1000); // 缓存的最长时间
            conn.setDoInput(true);// 允许输入
            conn.setDoOutput(true);// 允许输出
            conn.setUseCaches(false); // 不允许使用缓存
            conn.setRequestMethod("POST");
            conn.setRequestProperty("connection","keep-alive");
            conn.setRequestProperty("Charsert", "UTF-8");
            conn.setRequestProperty("Content-Type",formData + ";boundary=" + boundary);
            // 首先组拼文本类型的参数
            final DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());
            // 发送文件数据
            for(int i = 0; i < files.size(); i++){
                final File file = files.get(i);
                final StringBuilder sb1 = new StringBuilder();
                sb1.append(prefix);
                sb1.append(boundary);
                sb1.append(linend);
                sb1.append("Content-Disposition: form-data; name=\""+flag+"\"; filename=\"" + file.getName() + "\"" + linend);
                sb1.append("Content-Type: application/octet-stream; charset=" + "UTF-8" + linend);
                sb1.append(linend);
                outStream.write(sb1.toString().getBytes());
                final InputStream is = new FileInputStream(file);
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = is.read(buffer)) != -1) {
                    outStream.write(buffer, 0, len);
                }
                is.close();
                outStream.write(linend.getBytes());
            }
            // 请求结束标志
            final byte[] end_data = (prefix + boundary + prefix + linend).getBytes();
            outStream.write(end_data);
            outStream.flush();
            // 得到响应码
            final int res = conn.getResponseCode();
            final InputStream in = conn.getInputStream();
            final StringBuilder sb2 = new StringBuilder();
            if (res == 200) {
                int ch;
                while ((ch = in.read()) != -1) {
                    sb2.append((char)ch);
                }
            }
            outStream.close();
            conn.disconnect();
            //result.put(ToolHttp.code,ToolHttp.code200);
            //result.put(ToolHttp.obj,sb2.toString());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            //result.put(ToolHttp.code,"199");
            //result.put(ToolHttp.obj,"网络链接失败");
            return result;
        }
    }
}