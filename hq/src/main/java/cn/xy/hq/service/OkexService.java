package cn.xy.hq.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cn.xy.hq.vo.AskBid;
import cn.xy.hq.vo.Balance;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

@Service
public class OkexService extends LogService implements BaseService{

	@Autowired
	HttpService httpService;
	
	//得到挂单的买卖价格和数量
	public AskBid getAskBid(String market){
		String ha = "https://www.okex.cn/api/v1/depth.do?symbol="+market+"&size=2";
		String result = httpService.get(ha);
		if(StringUtils.isEmpty(result))//如果行情没取到直接返回
			return null;

		JSONArray asksArr = JSON.parseObject(result).getJSONArray("asks");
		JSONArray bidsArr = JSON.parseObject(result).getJSONArray("bids");
		if(asksArr==null || bidsArr==null
				|| asksArr.size()==0 || bidsArr.size()==0)
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

	@Override
	public List<String> queryUnfinish(Balance bl) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void order(Balance bl) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cancelOrder(String orderId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Balance getBalance(Balance bl) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Balance getPrecision(String currency, String market) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
