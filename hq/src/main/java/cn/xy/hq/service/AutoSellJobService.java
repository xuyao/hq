package cn.xy.hq.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.xy.hq.vo.AskBid;

@Service
public class AutoSellJobService extends LogService{
	
	@Autowired
	MarketFactory marketFactory;
	
	@Autowired
	OkexService okexService;
	
	List<String> list = null;
	boolean isfirst = true;

	
	public void work(String exg, String currency, String market, String amount){
		
		BaseService baseService = marketFactory.getMarketService(exg);
		
		//1、拿到btc_usdt交易价格
		AskBid ab_btc_usdt = baseService.getAskBid("btc_usdt");
		
		//2、拿到usdt和btc各所有交易对
		if(isfirst) {
			list = okexService.getAllSymbol();
			isfirst = false;
		}

		//3.比价
		for(String s : list){
			String[] arr = s.split(",");
			AskBid ab_usdt = baseService.getAskBid(arr[0]);
			AskBid ab_btc = baseService.getAskBid(arr[1]);
			
		}
		
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
		
		
		
		System.out.println("------------auto run");
	}
	
}
