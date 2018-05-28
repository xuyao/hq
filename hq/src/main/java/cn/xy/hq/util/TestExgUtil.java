package cn.xy.hq.util;

import java.util.Iterator;

import cn.xy.hq.service.HttpService;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class TestExgUtil {

	public static void main(String[] args) {
		HttpService http = new HttpService();
//	    String html = http.get("http://api.cex.com/api/v1/depth.do?symbol=ltc_btc");
		String html = http.get("http://cex.plus/Index/CurrencyList.html");
	    JSONArray jsonarr= JSONArray.parseArray(html);
	    Iterator it = jsonarr.iterator();
		while(it.hasNext()){
			JSONObject jsonObj = (JSONObject)it.next();
			System.out.println(jsonObj.getString("currency_mark")+
					" "+jsonObj.getString("tmark"));
		}
		
	}
}
