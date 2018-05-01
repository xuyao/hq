package cn.xy.hq.service;

import java.util.List;

import cn.xy.hq.vo.AskBid;
import cn.xy.hq.vo.Balance;

public interface BaseService {

	/**询价*/
	public AskBid getAskBid(String market);
	
//	/**查询未成交订单*/
//	public List<String> queryUnfinish(Balance bl);
//	
//	/**下单*/
//	public void order(Balance bl);
//	
//	/**撤单*/
//	public void cancelOrder(String orderId);
//	
//	/**获得balance*/
//	public Balance getBalance(Balance bl);
//	
//	/**获得精度*/
//	public Balance getPrecision(String currency, String market);
	

}
