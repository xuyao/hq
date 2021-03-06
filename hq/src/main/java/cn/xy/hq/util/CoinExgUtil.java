package cn.xy.hq.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import cn.xy.hq.service.CoinwService;
import cn.xy.hq.service.HttpService;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.general.ExchangeInfo;
import com.binance.api.client.domain.general.SymbolInfo;

public class CoinExgUtil {

	public static void main(String[] args){
		String coinexgpath1 = ConstsUtil.getValue("coinexgpath1");
		String coinexgpath2 = ConstsUtil.getValue("coinexgpath2");
		String coinexgpath3 = ConstsUtil.getValue("coinexgpath3");
		HttpService http = new HttpService();
		
		TreeMap<String,List<String>> map = new TreeMap<String,List<String>>();
		List<String> list = null;
		
		/** gateio */
		String html = http.get("http://data.gateio.io/api2/1/pairs");
		JSONArray jsonArr= JSONArray.parseArray(html);
		Iterator it = jsonArr.iterator();
		while(it.hasNext()){
			String exn = (String)it.next();
//			System.out.println(exn);
			if(exn.endsWith("_USDT") || exn.endsWith("_BTC") || exn.endsWith("_ETH")){
				if(map.get(exn.toLowerCase())==null){
					list = new ArrayList<String>();
					list.add("gateio");
					map.put(exn.toLowerCase(), list);
				}else{
					list = map.get(exn.toLowerCase());
					list.add("gateio");
				}
			}
		}
		
		
		/** okex */
	    html = http.get("https://www.okex.com/v2/spot/markets/products");
		JSONObject jsonObj= JSONObject.parseObject(html);
		jsonArr = jsonObj.getJSONArray("data");
		it = jsonArr.iterator();
		while(it.hasNext()){
			JSONObject obj = (JSONObject)it.next();
			String symbol = obj.getString("symbol");
//			System.out.println(symbol);
			if(symbol.endsWith("_usdt") || symbol.endsWith("_btc") || symbol.endsWith("_eth")){
				if(map.get(symbol)==null){
					list = new ArrayList<String>();
					list.add("okex");
					map.put(symbol, list);
				}else{
					list = map.get(symbol);
					list.add("okex");
				}
			}
		}
		
		/** huobi */
	    html = http.get("https://api.huobi.br.com/v1/common/symbols");
		jsonObj= JSONObject.parseObject(html);
		jsonArr = jsonObj.getJSONArray("data");
		it = jsonArr.iterator();
		while(it.hasNext()){
			JSONObject obj = (JSONObject)it.next();
			String base_currency = obj.getString("base-currency");
			String quote_currency = obj.getString("quote-currency");
//			System.out.println(symbol);
			String symbol = base_currency+"_"+quote_currency;
			if(map.get(symbol)==null){
				list = new ArrayList<String>();
				list.add("huobi");
				map.put(symbol, list);
			}else{
				list = map.get(symbol);
				list.add("huobi");
			}
		}
		
		/** binance */
		BinanceApiRestClient client = BinanceApiClientFactory.newInstance().newRestClient();
		ExchangeInfo ei = client.getExchangeInfo();
		List<SymbolInfo> symbols = ei.getSymbols();
		for(SymbolInfo sif : symbols){
			String symbol = sif.getSymbol();
			
			if(symbol.endsWith("BTC")){
				symbol = symbol.replaceAll("BTC", "_btc").toLowerCase();
			}else if(symbol.endsWith("USDT")){
				symbol = symbol.replaceAll("USDT", "_usdt").toLowerCase();
			}else if(symbol.endsWith("ETH")){
				symbol = symbol.replaceAll("ETH", "_eth").toLowerCase();
			}else{
				continue;
			}
//			System.out.println(symbol);
			if(map.get(symbol)==null){
				list = new ArrayList<String>();
				list.add("binance");
				map.put(symbol, list);
			}else{
				list = map.get(symbol);
				list.add("binance");
			}
		}
		
		/** zb */
	    html = http.get("http://api.zb.com/data/v1/markets");
		jsonObj= JSONObject.parseObject(html);
		Set<String> keys = jsonObj.keySet();
		it = keys.iterator();
		while(it.hasNext()){
			String symbol = (String)it.next();
//			System.out.println(symbol);
			if(map.get(symbol)==null){
				list = new ArrayList<String>();
				list.add("zb");
				map.put(symbol, list);
			}else{
				list = map.get(symbol);
				list.add("zb");
			}
		}
		
		/** exx */
	    html = http.get("https://api.exxvip.com/data/v1/markets");
		jsonObj= JSONObject.parseObject(html);
		keys = jsonObj.keySet();
		it = keys.iterator();
		while(it.hasNext()){
			String symbol = (String)it.next();
//			System.out.println(symbol);
			if(symbol.endsWith("_cnyt"))
				symbol = symbol.replaceAll("_cnyt", "_qc");
			if(map.get(symbol)==null){
				list = new ArrayList<String>();
				list.add("exx");
				map.put(symbol, list);
			}else{
				list = map.get(symbol);
				list.add("exx");
			}
		}
		
		
		
		/** aex */
	    html = http.get("https://www.bit.cc/httpAPIv2.php?n=0.5447456580455957");
	    jsonObj= JSONObject.parseObject(html);
		keys = jsonObj.keySet();
		it = keys.iterator();
		while(it.hasNext()){
			String key = (String)it.next();
			if(key.contains("_")){
				continue;
			}else{
			String symbol = key.replaceAll("2", "_");
			if(symbol.endsWith("_usdt") || symbol.endsWith("_btc") 
					|| symbol.endsWith("_eth") ||symbol.endsWith("_cnc")){
				if(symbol.endsWith("_cnc"))
					symbol = symbol.replaceAll("cnc", "qc");
				if(map.get(symbol)==null){
					list = new ArrayList<String>();
					list.add("aex");
					map.put(symbol, list);
				}else{
					list = map.get(symbol);
					list.add("aex");
				}
			}
			}
		}
		
		
		/** coinw*/
		CoinwService cs = new CoinwService();
		cs.init();
		Map<String,String> coinwMap = cs.map;
		it = coinwMap.keySet().iterator();
		while(it.hasNext()){
			String market = (String)it.next();
			if(map.get(market)==null){
			list = new ArrayList<String>();
			list.add("coinw");
			map.put(market, list);
		}else{
			list = map.get(market);
			list.add("coinw");
		}
		}
		
		
		/** poloniex */
	    html = http.get("https://poloniex.com/public?command=returnTicker");
	    jsonObj= JSONObject.parseObject(html);
		keys = jsonObj.keySet();
		it = keys.iterator();
		while(it.hasNext()){
			String symbol = (String)it.next();
			String[] sarray = symbol.split("_");
			symbol = sarray[1]+"_"+sarray[0];
			symbol = symbol.toLowerCase();
			if(symbol.endsWith("_usdt") || symbol.endsWith("_btc") 
					|| symbol.endsWith("_eth")){
				if(map.get(symbol)==null){
					list = new ArrayList<String>();
					list.add("poloniex");
					map.put(symbol, list);
				}else{
					list = map.get(symbol);
					list.add("poloniex");
				}
			}
		}
		
		
		/** bit-z : dkkt,btc,eth*/
	    html = http.get("https://www.bit-z.com/api_v1/tickerall");
	    jsonObj= JSONObject.parseObject(html).getJSONObject("data");
		keys = jsonObj.keySet();
		it = keys.iterator();
		while(it.hasNext()){
			String symbol = (String)it.next();
			if(symbol.endsWith("_dkkt"))
				symbol = symbol.replaceAll("_dkkt", "_qc");
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
		
		
		/** write to file*/
		List<String> result1 = new ArrayList<String>();
		List<String> result2 = new ArrayList<String>();
		List<String> result3 = new ArrayList<String>();
		Iterator itmap = map.keySet().iterator();
		boolean go = true;//是否写入
        while (itmap.hasNext()) {
            String s = (String)itmap.next();
            if(s.startsWith("i")) {
            	go = false;
            	break;
            }
            if(go)
            result1.add(s+":"+StringUtils.join(map.get(s).toArray(), ","));
        }
        
        itmap = map.keySet().iterator();
        while (itmap.hasNext()) {
        	String s = (String)itmap.next();
        	if(s.startsWith("i")) {
        		go = true;
        	}
        	if(s.startsWith("s")) {
        		go = false;
        		break;
        	}
        	if(go)
            result2.add(s+":"+StringUtils.join(map.get(s).toArray(), ","));
        }
        
        
        itmap = map.keySet().iterator();
        while (itmap.hasNext()) {
        	String s = (String)itmap.next();
        	if(s.startsWith("s")) {
        		go = true;
        	}
        	if(go)
            result3.add(s+":"+StringUtils.join(map.get(s).toArray(), ","));
        }
        

		try {
			FileUtils.writeLines(new File(coinexgpath1), result1);
			FileUtils.writeLines(new File(coinexgpath2), result2);
			FileUtils.writeLines(new File(coinexgpath3), result3);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
}
