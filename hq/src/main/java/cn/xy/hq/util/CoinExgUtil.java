package cn.xy.hq.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import cn.xy.hq.service.HttpService;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.general.ExchangeInfo;
import com.binance.api.client.domain.general.SymbolInfo;

public class CoinExgUtil {

	public static void main(String[] args){
		String coinexgpath = ConstsUtil.getValue("coinexgpath");
		HttpService http = new HttpService();
		
		TreeMap<String,List<String>> map = new TreeMap<String,List<String>>();
		List<String> list = null;
		
//		/** gateio */
//		String html = http.get("http://data.gateio.io/api2/1/pairs");
//		JSONArray jsonArr= JSONArray.parseArray(html);
//		Iterator it = jsonArr.iterator();
//		while(it.hasNext()){
//			String exn = (String)it.next();
////			System.out.println(exn);
//			if(exn.endsWith("_usdt") || exn.endsWith("_btc") || exn.endsWith("_eth")){
//				if(map.get(exn)==null){
//					list = new ArrayList<String>();
//					list.add("gateio");
//					map.put(exn, list);
//				}else{
//					list = map.get(exn);
//					list.add("gateio");
//				}
//			}
//		}
//		
//		
//		/** okex */
//	    html = http.get("https://www.okex.cn/v2/spot/markets/products");
//		JSONObject jsonObj= JSONObject.parseObject(html);
//		jsonArr = jsonObj.getJSONArray("data");
//		it = jsonArr.iterator();
//		while(it.hasNext()){
//			JSONObject obj = (JSONObject)it.next();
//			String symbol = obj.getString("symbol");
////			System.out.println(symbol);
//			if(symbol.endsWith("_usdt") || symbol.endsWith("_btc") || symbol.endsWith("_eth")){
//				if(map.get(symbol)==null){
//					list = new ArrayList<String>();
//					list.add("okex");
//					map.put(symbol, list);
//				}else{
//					list = map.get(symbol);
//					list.add("okex");
//				}
//			}
//		}
//		
//		/** huobi */
//	    html = http.get("https://api.huobi.br.com/v1/common/symbols");
//		jsonObj= JSONObject.parseObject(html);
//		jsonArr = jsonObj.getJSONArray("data");
//		it = jsonArr.iterator();
//		while(it.hasNext()){
//			JSONObject obj = (JSONObject)it.next();
//			String base_currency = obj.getString("base-currency");
//			String quote_currency = obj.getString("quote-currency");
////			System.out.println(symbol);
//			String symbol = base_currency+"_"+quote_currency;
//			if(map.get(symbol)==null){
//				list = new ArrayList<String>();
//				list.add("huobi");
//				map.put(symbol, list);
//			}else{
//				list = map.get(symbol);
//				list.add("huobi");
//			}
//		}
		
		/** binance */
		BinanceApiRestClient client = BinanceApiClientFactory.newInstance().newRestClient();
		ExchangeInfo ei = client.getExchangeInfo();
		List<SymbolInfo> symbols = ei.getSymbols();
		for(SymbolInfo sif : symbols){
			String symbol = sif.getSymbol();
			System.out.println(symbol);
			if(map.get(symbol)==null){
				list = new ArrayList<String>();
				list.add("binance");
				map.put(symbol, list);
			}else{
				list = map.get(symbol);
				list.add("binance");
			}
		}
		
//		/** zb */
//	    html = http.get("http://api.zb.com/data/v1/markets");
//		jsonObj= JSONObject.parseObject(html);
//		Set<String> keys = jsonObj.keySet();
//		it = keys.iterator();
//		while(it.hasNext()){
//			String symbol = (String)it.next();
////			System.out.println(symbol);
//			if(map.get(symbol)==null){
//				list = new ArrayList<String>();
//				list.add("zb");
//				map.put(symbol, list);
//			}else{
//				list = map.get(symbol);
//				list.add("zb");
//			}
//		}
//		
//		/** exx */
//	    html = http.get("https://api.exx.com/data/v1/markets");
//		jsonObj= JSONObject.parseObject(html);
//		keys = jsonObj.keySet();
//		it = keys.iterator();
//		while(it.hasNext()){
//			String symbol = (String)it.next();
////			System.out.println(symbol);
//			if(map.get(symbol)==null){
//				list = new ArrayList<String>();
//				list.add("exx");
//				map.put(symbol, list);
//			}else{
//				list = map.get(symbol);
//				list.add("exx");
//			}
//		}
//		
//		
//		
//		/** aex */
//	    html = http.get("https://www.aex.com/httpAPIv2.php?n=0.5287456580455957");
//	    jsonObj= JSONObject.parseObject(html);
//		keys = jsonObj.keySet();
//		it = keys.iterator();
//		while(it.hasNext()){
//			String key = (String)it.next();
//			if(key.contains("_")){
//				continue;
//			}else{
//			String symbol = key.replaceAll("2", "_");
//			if(symbol.endsWith("_usdt") || symbol.endsWith("_btc") || symbol.endsWith("_eth")){
//				if(map.get(symbol)==null){
//					list = new ArrayList<String>();
//					list.add("aex");
//					map.put(symbol, list);
//				}else{
//					list = map.get(symbol);
//					list.add("aex");
//				}
//			}
//			}
//		}
//		
//		
//		/** write to file*/
//		List<String> result = new ArrayList<String>();
//		Iterator itmap = map.keySet().iterator();
//        while (itmap.hasNext()) {
//            String s = (String)itmap.next();
//            result.add(s+":"+StringUtils.join(map.get(s).toArray(), ","));
//        }
//
//		try {
//			FileUtils.writeLines(new File(coinexgpath), result);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		
	}
}
