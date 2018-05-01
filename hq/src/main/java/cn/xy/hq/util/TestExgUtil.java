package cn.xy.hq.util;

import java.util.Iterator;
import java.util.Set;

import com.alibaba.fastjson.JSONObject;

import cn.xy.hq.service.HttpService;

public class TestExgUtil {

	public static void main(String[] args) {
		HttpService http = new HttpService();
	    String html = http.get("https://poloniex.com/public?command=returnTicker");
	    JSONObject jsonObj= JSONObject.parseObject(html);
	    Set<String> keys = jsonObj.keySet();
	    Iterator it = keys.iterator();
		while(it.hasNext()){
			String symbol = (String)it.next();
			String[] sarray = symbol.split("_");
			symbol = sarray[1]+"_"+sarray[0];
			System.out.println(symbol.toLowerCase());
		}
		
	}
}
