package com.joe.tuchong;
import java.io.IOException;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.joe.util.*;
public class DownBySet {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		while(true) {
			Scanner s=new Scanner(System.in);
			System.out.println("输入网址：");
			String url=s.nextLine();
			//String url="https://tuchong.com/1982205/21889851/";
			downBySet("d:/DownPic/TuChong",url);

		}
		
	}
	public static void downBySet(String path,String url) {
		// TODO Auto-generated method stub
		try {
			Document doc = (Jsoup.connect(url).get());
			//System.out.println(doc);
			Elements es=doc.select("img[src*=/photo]");
			//System.out.println(es.get(0));
			int count=0;
			for(Element e:es) {
				String imgUrl=e.absUrl("abs:src");
				new downFromUrl(path, imgUrl);
				downFromUrl.downImages();
				System.out.println(imgUrl+" 下载完成");
				count++;
			}
			System.out.println("图片总数："+count);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
