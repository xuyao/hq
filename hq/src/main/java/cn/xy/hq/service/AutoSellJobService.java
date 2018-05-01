package cn.xy.hq.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.xy.hq.vo.Balance;

@Service
public class AutoSellJobService extends LogService{
	
//	@Autowired
//	MarketFactory marketFactory;
//	
//	@Autowired
//	BaseService baseService;
//	
//	boolean isfirst = true;
//	Balance bl = null;
	
	public void work(String exg, String currency, String market, String amount){
		
//		baseService = marketFactory.getMarketService(exg);
//		
//		if(isfirst) {
//			bl = baseService.getPrecision(currency, market);
//			isfirst = false;
//		}
//		
//		//1、先查询未成交，并撤单
//		List<String> orderids = baseService.queryUnfinish(bl);
//		for(String orderId : orderids) {
//			baseService.cancelOrder(orderId);
//		}
//		
//		try {
//			Thread.sleep(500);//休整500ms
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		
//		//2、拿到账户信息，剩余数量
//		bl = baseService.getBalance(bl);
//		
//		//3、下单
//		baseService.order(bl);
		
		
		
		System.out.println("------------sell");
	}
	
}
