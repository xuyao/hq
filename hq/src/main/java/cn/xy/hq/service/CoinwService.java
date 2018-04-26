package cn.xy.hq.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cn.xy.hq.util.ConstsUtil;
import cn.xy.hq.vo.AskBid;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Service
public class CoinwService extends LogService implements BaseService{

	@Autowired
	HttpService httpService;
	String coinwpath = ConstsUtil.getValue("coinw");
	public Map<String,String> map = new HashMap<String,String>();
	
	//得到挂单的买卖价格和数量
	public AskBid getAskBid(String market){
		String symbol = map.get(market);
		String ha = "https://www.coinw.me/appApi.html?action=depth&symbol="+symbol;
		String result = httpService.get(ha);
		if(StringUtils.isEmpty(result))//如果行情没取到直接返回
			return null;
		JSONObject jb = JSONObject.parseObject(result);
		jb = jb.getJSONObject("data");
		JSONArray asksArr = jb.getJSONArray("asks");
		JSONArray bidsArr = jb.getJSONArray("bids");
		if(asksArr==null || bidsArr==null
				|| asksArr.size()==0 || bidsArr.size()==0)
			return null;
		
		JSONObject asks1 = asksArr.getJSONObject(0);
		JSONObject bids1 = bidsArr.getJSONObject(0);
		
		if(!asks1.getString("id").equals("1") || !bids1.getString("id").equals("1")){
			logger.info("coinw 格式错误！！");
			return null;
		}
		
		AskBid ab = new AskBid();
		ab.setAsk1(Double.parseDouble(asks1.getString("price")));
		ab.setAsk1_amount(Double.parseDouble(asks1.getString("amount")));
		ab.setBid1(Double.parseDouble(bids1.getString("price")));
		ab.setBid1_amount(Double.parseDouble(bids1.getString("amount")));
		ab.setMarket(market);
		return ab;
	}
	
	
	public void init(){
		try {
			String coinw = FileUtils.readFileToString(new File(coinwpath),"utf-8");
			JSONObject jsonObj= JSONObject.parseObject(coinw);
			JSONArray jsonArr = jsonObj.getJSONArray("fMap");
			Iterator it = jsonArr.iterator();
			while(it.hasNext()){
				JSONObject jso = (JSONObject)it.next();
				String market = jso.getString("fname").toLowerCase();
				market = market+"_qc";
				String symbol = jso.getString("fid");
				map.put(market, symbol);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
}
