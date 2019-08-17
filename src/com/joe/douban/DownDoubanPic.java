package com.joe.douban;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class DownDoubanPic {
	//static String urlpath="https://img1.doubanio.com/view/photo/raw/public/p2273834766.jpg";
	//static String referer="https://movie.douban.com/celebrity/1343344/photo/2273834766/";
	static String urlpath;
	static String referer;
	final static String  filepath="d:\\DownPic\\Douban";
	public DownDoubanPic(String urlpath, String referer) {
		super();
		this.urlpath = urlpath;
		this.referer = referer;
	}

    public void saveImageToDisk(String filename) throws IOException {
    	File f=new File(filepath);
    	if(!f.exists()) {
    		f.mkdirs();
    	}
        InputStream inputStream = getInputStream();
        byte[] data = new byte[1024];
        int len = 0;
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(filepath+"\\"+filename+".jpg");
            while ((len = inputStream.read(data)) != -1) {
                fileOutputStream.write(data, 0, len);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 获得服务器端数据，以InputStream形式返回
     * 
     * @return
     * @throws IOException
     */
    public static InputStream getInputStream() throws IOException {
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL(urlpath);
            if (url != null) {
                httpURLConnection = (HttpURLConnection) url.openConnection();
                // 设置连接网络的超时时间
                httpURLConnection.setConnectTimeout(3000);
                httpURLConnection.setDoInput(true);
                // 设置本次http请求使用get方式请求
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setRequestProperty("accept", "*/*");
                httpURLConnection.setRequestProperty("connection", "Keep-Alive");
                httpURLConnection.setRequestProperty("user-agent",
                        "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
                httpURLConnection.setRequestProperty("referer", referer);
                int responseCode = httpURLConnection.getResponseCode();
                if (responseCode == 200) {
                    // 从服务器获得一个输入流
                    inputStream = httpURLConnection.getInputStream();
                }
            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return inputStream;
    }
	
    
    
    
    
    
    /**
     * 向指定URL发送GET方法的请求
     * @param url  发送请求的URL         
     * @param param   请求参数，格式：name1=value1&name2=value2   
     * @return String 响应结果
     */
    public static String sendGet(String url) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection)realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setRequestProperty("referer", "https://movie.douban.com/celebrity/1337036/photo/2507182426/");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
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
    

}
