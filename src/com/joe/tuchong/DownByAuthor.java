package com.joe.tuchong;

import java.io.IOException;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.joe.util.JsonManager;
import com.joe.util.downFromUrl;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * 通过作者下载图片
 * @author Joe
 *
 */
public class DownByAuthor {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long timetamp=new Date().getTime();
		String url="http://tuchong.com/rest/2/sites/4014041/posts?count=200&page=1&before_timestamp="+timetamp;
		String originJson=JsonManager.getSourceFromUrl(url);
		
		//Document doc = (Jsoup.connect(url).get());
		//System.out.println(doc);
		String formatJson=JsonManager.getFormatJson(originJson);
		
		JSONArray ja=JSONArray.fromObject(formatJson);
		JSONObject jo;
		String author_id=JSONObject.fromObject(ja.get(0)).get("author_id").toString();
		for(int i=0;i<ja.size();i++) {
			jo=JSONObject.fromObject(ja.get(i));
			String Url=("https://tuchong.com/"+author_id+"/"+jo.get("post_id"));
			downfromTuChongAuthor(Url);
		}
		
		
	}
	private static void downfromTuChongAuthor(String url) {
		try {
			Document doc = (Jsoup.connect(url).get());
			//System.out.println(doc);
			Elements es=doc.select("img[src*=/photo]");
			//System.out.println(es.get(0));
			int count=0;
			for(Element e:es) {
				String imgUrl=e.absUrl("abs:src");
				new downFromUrl("d:/DownPic/TuChong4014041", imgUrl);
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
