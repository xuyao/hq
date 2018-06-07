package cn.xy.hq.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.xy.hq.service.HttpService;

import com.alibaba.fastjson.JSONObject;

public class TestExgUtil {

	public static void main(String[] args) {
		HttpService http = new HttpService();
		String html = "";
		JSONObject jsonObj = null;
		Set keys = null;
		Iterator it = null;
		Map<String,List> map = new HashMap();
		List list = new ArrayList();
//	    String html = http.get("http://api.cex.com/api/v1/depth.do?symbol=ltc_btc");
	    html = http.get("https://www.bit-z.pro/index/infofresh?t=0.44508058696642516");
	    jsonObj= JSONObject.parseObject(html).getJSONObject("data");
		keys = jsonObj.keySet();
		it = keys.iterator();
		while(it.hasNext()){
			String symbol = (String)it.next();
			if(symbol.endsWith("_usdt") || symbol.endsWith("_btc") 
					|| symbol.endsWith("_eth")){
				if(map.get(symbol)==null){
					list = new ArrayList<String>();
					list.add("bit-z");
					map.put(symbol, list);
				}else{
					list = map.get(symbol);
					list.add("bit-z");
				}
			}
		}
		
	}
}
