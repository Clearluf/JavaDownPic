package com.joe.douban;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Demo {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String url="https://movie.douban.com/celebrity/1098533/photo/1294491154/";
		int count=(getCount(url));
		
		for(int i=0;i<count;i++){
			try {
				Thread.sleep(1000);
				String picID=getPicID(url);
				String rawPicUrl="https://img1.doubanio.com/view/photo/raw/public/p"+picID+".jpg";
				DownDoubanPic dd=new DownDoubanPic(rawPicUrl, url);
				dd.saveImageToDisk(picID+".jpg");
				System.out.print("共"+count+"张，");
				System.out.println("下载完成------"+(i+1)+"张");
				url=getNextUrl(url);
			} catch (Exception e) {
				// TODO: handle exception
				continue;
			}

		}


	}
	private static int getCount(String url) {
		// TODO Auto-generated method stub
		String count = null;
		try {
			Document doc = (Jsoup.connect(url).get());
			Elements es=doc.select("span[class*=opt-left]");
			count= (es.get(0).text());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String total=count.split("/")[1];
		int len=total.length();
		return Integer.parseInt(total.substring(1, len-1));
	}
	public static String getNextUrl(String url) {
		String next = null;
		try {
			Document doc = (Jsoup.connect(url).get());
			Elements es=doc.select("a[id*=next_photo]");
			next= (es.get(0).absUrl("abs:href"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return next;
		
	}
	private static String getPicID(String url) {
		// TODO Auto-generated method stub
		return url.split("/")[6];
	}

}
