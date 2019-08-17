package com.joe.tuchong;

import com.joe.util.JsonManager;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class DownByTag {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String url;
		String originJson;
		String formatJson = null;
		int count=0;
		for(int pageNum=1;pageNum<10;pageNum++) {
			url= "https://tuchong.com/rest/tags/%E5%A5%B3%E5%AD%A9/posts?page="+pageNum+"&count=20&order=weekly&before_timestamp=";
			originJson = JsonManager.getSourceFromUrl(url);
			formatJson = JsonManager.getFormatJson(originJson);
			JSONArray ja = JSONArray.fromObject(formatJson);
			JSONObject jo;
			for (int i = 1; i < ja.size()+1; i++) {
				jo = JSONObject.fromObject(ja.get(i-1));
				String imgurl=jo.get("url").toString();
				System.out.printf("µÚ%s×é"+imgurl+"%n",i+count);
				DownBySet.downBySet("d:/DownPic/TC_Å®º¢", imgurl);
				
			}
			count+=ja.size();
		}

		

		


	}

}
