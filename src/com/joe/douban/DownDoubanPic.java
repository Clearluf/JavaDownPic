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
     * ��÷����������ݣ���InputStream��ʽ����
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
                // ������������ĳ�ʱʱ��
                httpURLConnection.setConnectTimeout(3000);
                httpURLConnection.setDoInput(true);
                // ���ñ���http����ʹ��get��ʽ����
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setRequestProperty("accept", "*/*");
                httpURLConnection.setRequestProperty("connection", "Keep-Alive");
                httpURLConnection.setRequestProperty("user-agent",
                        "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
                httpURLConnection.setRequestProperty("referer", referer);
                int responseCode = httpURLConnection.getResponseCode();
                if (responseCode == 200) {
                    // �ӷ��������һ��������
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
     * ��ָ��URL����GET����������
     * @param url  ���������URL         
     * @param param   �����������ʽ��name1=value1&name2=value2   
     * @return String ��Ӧ���
     */
    public static String sendGet(String url) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url;
            URL realUrl = new URL(urlNameString);
            // �򿪺�URL֮�������
            HttpURLConnection connection = (HttpURLConnection)realUrl.openConnection();
            // ����ͨ�õ���������
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setRequestProperty("referer", "https://movie.douban.com/celebrity/1337036/photo/2507182426/");
            // ����ʵ�ʵ�����
            connection.connect();
            // ��ȡ������Ӧͷ�ֶ�
            Map<String, List<String>> map = connection.getHeaderFields();
            // �������е���Ӧͷ�ֶ�
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // ���� BufferedReader����������ȡURL����Ӧ
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("����GET��������쳣��" + e);
            e.printStackTrace();
        }
        // ʹ��finally�����ر�������
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
