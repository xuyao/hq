package cn.xy.hq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cn.xy.hq.vo.AskBid;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Service
public class HuobiService extends LogService implements BaseService{

	@Autowired
	HttpService httpService;
	
	//得到挂单的买卖价格和数量
	public AskBid getAskBid(String market){
		String mk = market.replaceAll("_", "");
		String ha = "https://api.huobi.br.com/market/depth?symbol="+mk+"&type=step1";
		String result = httpService.get(ha);
		if(StringUtils.isEmpty(result))//如果行情没取到直接返回
			return null;
		JSONObject jsonobject = JSONObject.parseObject(result);
		jsonobject = jsonobject.getJSONObject("tick");
		JSONArray asksArr = jsonobject.getJSONArray("asks");
		JSONArray bidsArr = jsonobject.getJSONArray("bids");
		if(asksArr==null || bidsArr==null)
			return null;
		JSONArray asks1 = asksArr.getJSONArray(asksArr.size()-1);
		JSONArray bids1 = bidsArr.getJSONArray(0);
		
		AskBid ab = new AskBid();
		ab.setAsk1(asks1.getDouble(0));//卖二
		ab.setAsk1_amount(asks1.getDouble(1));
		ab.setBid1(bids1.getDouble(0));//买一
		ab.setBid1_amount(bids1.getDouble(1));
		ab.setMarket(market);
		return ab;
	}
	
	
}
