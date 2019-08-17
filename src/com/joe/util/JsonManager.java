package com.joe.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class JsonManager {
    public static String getSourceFromUrl(String urlStr) {
        URL url = null;
        HttpURLConnection httpConn = null;
        BufferedReader in = null;
        StringBuffer sb = new StringBuffer();
        try {
            url = new URL(urlStr); in = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            String str = null;
            // 一行一行进行读入
            while ((str = in .readLine()) != null) {
                sb.append(str);
                sb.append("\n\t");
            }
        } catch (Exception ex) {} finally {
            try {
                if ( in != null) { in .close(); // 关闭流
                }
            } catch (IOException ex) {}
        }
        String result = sb.toString();
        return result;
    }
    public static String getFormatJson(String origin) {
        int begin = origin.indexOf("[");
        int end = origin.lastIndexOf("]");
        String format = origin.substring(begin, end + 1);
        return format;
    }
}
