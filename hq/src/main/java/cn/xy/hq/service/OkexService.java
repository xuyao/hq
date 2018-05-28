package cn.xy.hq.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cn.xy.hq.vo.AskBid;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Service
public class OkexService extends LogService implements BaseService{

	@Autowired
	HttpService httpService;
	
	//得到挂单的买卖价格和数量
	public AskBid getAskBid(String market){
		String ha = "https://www.okex.com/api/v1/depth.do?symbol="+market+"&size=2";
		String result = httpService.get(ha);
		if(StringUtils.isEmpty(result))//如果行情没取到直接返回
			return null;

		JSONObject jsonobj = JSON.parseObject(result);
		JSONArray asksArr = jsonobj.getJSONArray("asks");
		JSONArray bidsArr = jsonobj.getJSONArray("bids");
		if(asksArr==null || bidsArr==null
				|| asksArr.size()==0 || bidsArr.size()==0)
			return null;
		JSONArray asks1 = asksArr.getJSONArray(asksArr.size()-1);
		JSONArray bids1 = bidsArr.getJSONArray(0);
		
		AskBid ab = new AskBid();
		ab.setAsk1(asks1.getDouble(0));//卖一
		ab.setAsk1_amount(asks1.getDouble(1));
		ab.setBid1(bids1.getDouble(0));//买一
		ab.setBid1_amount(bids1.getDouble(1));
		ab.setMarket(market);
		return ab;
	}

	
	//得到交易所btc和usdt的symbol
	public List<String> getAllSymbol(){
		List<String> list = new ArrayList<String>();
		Map<String, String> map_usdt = new HashMap<String, String>();
		Map<String, String> map_btc = new HashMap<String, String>();
		String html = httpService.get("https://www.okex.com/v2/spot/markets/products");
		JSONObject jsonObj= JSONObject.parseObject(html);
		JSONArray jsonArr = jsonObj.getJSONArray("data");
		Iterator it = jsonArr.iterator();
		while(it.hasNext()){
			JSONObject obj = (JSONObject)it.next();
			String symbol = obj.getString("symbol");
//			System.out.println(symbol);
//			if(symbol.endsWith("_usdt") || symbol.endsWith("_btc") || symbol.endsWith("_eth")){
			if(symbol.endsWith("_usdt")){
				String[] arrs = symbol.split("_");
				map_usdt.put(arrs[0], symbol);
			}
			if(symbol.endsWith("_btc")){
				String[] arrs = symbol.split("_");
				map_btc.put(arrs[0], symbol);
			}
		}
		
		Set<String> s = map_usdt.keySet();
		it = s.iterator();
		while(it.hasNext()){
			String key = (String)it.next();
			if(map_btc.get(key) != null){
				list.add(map_usdt.get(key) + "," + map_btc.get(key));
			}
		}
		
		return list;
	}
	
	
//	@Override
//	public List<String> queryUnfinish(Balance bl) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void order(Balance bl) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void cancelOrder(String orderId) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public Balance getBalance(Balance bl) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Balance getPrecision(String currency, String market) {
//		// TODO Auto-generated method stub
//		return null;
//	}
	
	
}
