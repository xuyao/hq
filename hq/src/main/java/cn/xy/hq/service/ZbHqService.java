package cn.xy.hq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cn.xy.hq.vo.AskBid;
import cn.xy.hq.vo.Ticker;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Service
public class ZbHqService extends LogService{

	@Autowired
	HttpService httpService;
	
	//得到挂单的买卖价格和数量
	public AskBid getAskBid(String market){
		String ha = "http://api.bitkk.com/data/v1/depth?market="+market+"&size=2";
		String result = httpService.get(ha);
//		System.out.println(market+result);
		if(StringUtils.isEmpty(result))//如果行情没取到直接返回
			return null;

		JSONArray asksArr = JSON.parseObject(result).getJSONArray("asks");
		JSONArray bidsArr = JSON.parseObject(result).getJSONArray("bids");
		if(asksArr==null || bidsArr==null)
			return null;
		JSONArray asks1 = asksArr.getJSONArray(1);
		JSONArray bids1 = bidsArr.getJSONArray(0);
		
		AskBid ab = new AskBid();
		ab.setAsk1(asks1.getDouble(0));//卖二
		ab.setAsk1_amount(asks1.getDouble(1));
		ab.setBid1(bids1.getDouble(0));//买一
		ab.setBid1_amount(bids1.getDouble(1));
		ab.setMarket(market);
		return ab;
	}
	
	
	public Ticker getTicker(String currency) {
		Ticker ticker = null;
		try {
			// 请求地址
			String url = "http://api.bitkk.com/data/v1/ticker?market=" + currency;
			String result = httpService.get(url);
			JSONObject jsonObj = JSONObject.parseObject(result);
			jsonObj = jsonObj.getJSONObject("ticker");
			ticker = new Ticker();
			ticker.setBuy(jsonObj.getDouble("buy"));
			ticker.setHigh(jsonObj.getDouble("high"));
			ticker.setLast(jsonObj.getDouble("last"));
			ticker.setLow(jsonObj.getDouble("low"));
			ticker.setSell(jsonObj.getDouble("sell"));
			ticker.setVol(jsonObj.getDouble("vol"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ticker;
	}
	

	public HttpService getHttpService() {
		return httpService;
	}


	public void setHttpService(HttpService httpService) {
		this.httpService = httpService;
	}
	
	
}
