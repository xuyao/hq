package cn.xy.hq.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.xy.hq.service.ExnService;
import cn.xy.hq.service.HttpService;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class WdfeeUtil {

	public static void main(String[] args){
		String wdfeePath = ConstsUtil.getValue("wdfee");
		ExnService exnService = new ExnService();
		exnService.parse();
		HttpService http = new HttpService();
		
		/** gateio */
		if(wdfeePath.contains("gateio")){
			String html = http.get("https://gateio.io/fee");
			Document doc = Jsoup.parse(html);
			Elements tbodys = doc.getElementsByTag("tbody");
			Element tbody = tbodys.get(0);
			Elements trs = tbody.getElementsByTag("tr");
			Iterator ittrs = trs.iterator();
			while(ittrs.hasNext()){
				Element tr = (Element)ittrs.next();
				Elements tds = tr.getElementsByTag("td");
				String coin = tds.get(1).text().toLowerCase();
				String fee = tds.get(6).text().split("\\+")[1];
//				System.out.println(coin+","+fee);
				if(exnService.feemap.get(coin)==null){
					exnService.feemap.put(coin, Double.parseDouble(fee));
				}else{//如果不为空，则比较大小
					double wdfee = exnService.feemap.get(coin);
					if(wdfee<Double.parseDouble(fee)){
						exnService.feemap.put(coin, Double.parseDouble(fee));
					}
				}
			}
		}
		
		
		/** 币安 */
		if(wdfeePath.contains("binance")){
		    File file = new File(wdfeePath);
			String json ="";
			try {
				json = FileUtils.readFileToString(file, "utf-8");
			} catch (IOException e) {
				e.printStackTrace();
			}
			JSONArray jsonArr= JSONArray.parseArray(json);
			Iterator it = jsonArr.iterator();
			while(it.hasNext()){
				JSONObject jsonObj = (JSONObject)it.next();
				String coin = jsonObj.getString("assetCode").toLowerCase();
				String fee = jsonObj.getString("transactionFee");
//				System.out.println(coin+","+fee);
				if(exnService.feemap.get(coin)==null){
					exnService.feemap.put(coin, Double.parseDouble(fee));
				}else{//如果不为空，则比较大小
					double wdfee = exnService.feemap.get(coin);
					if(wdfee<Double.parseDouble(fee)){
						exnService.feemap.put(coin, Double.parseDouble(fee));
					}
				}
			}
		}
		
		
		/** write to file*/
		List<String> result = new ArrayList<String>(); 
		Iterator it = exnService.feemap.keySet().iterator();  
        while (it.hasNext()) {  
            String s = (String)it.next();
            result.add(s+","+NumberUtil.big(exnService.feemap.get(s),5).toPlainString());
        } 
		
		try {
			URL url = ExnService.class.getClassLoader().getResource("wdfee.txt");
			FileUtils.writeLines(new File(url.getFile()), result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
