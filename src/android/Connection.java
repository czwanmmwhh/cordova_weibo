package org.zy.yuancheng.weibo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Connection {
	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String sendGet(String url, String param,Map<String,String> headers) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = null;
			if(param != null ) urlNameString = url + "?" + param;
			else urlNameString = url;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			if(headers != null){
				Set<String> keySet = headers.keySet();
				for(String key : keySet){
					connection.setRequestProperty(key, headers.get(key));
				}
			}
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			// 定义 BufferedReader输入流来读取URL的响应
			
			in = new BufferedReader(
					new InputStreamReader(connection.getInputStream(),"UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}
	
	public static String sendGet(String url, String param) {
		String contentType = "text/html;charset=utf-8";
		Map<String,String> headers = new HashMap<String,String>();
		headers.put("Content-type", contentType);
		return sendGet(url,param,headers);
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String param,Map<String,String> headers) {
//		PrintWriter out = null;
		OutputStream out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			if(headers != null){
				Set<String> keySet = headers.keySet();
				for(String key : keySet){
					conn.setRequestProperty(key, headers.get(key));
				}
			}
			
			conn.setDoOutput(true);
			conn.setDoInput(true);
			
			out = conn.getOutputStream();
			out.write(param.getBytes("UTF-8"));
			// 获取URLConnection对象对应的输出流
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream(),"UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
	
	public static String sendPost(String url, String param) {
		String contentType = "text/html;charset=utf-8";
		Map<String,String> headers = new HashMap<String,String>();
		headers.put("Content-type", contentType);
		return sendPost(url,param,headers);
	}
	
	
}
