package cn.xy.hq.service;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.xy.hq.fee.Market;
import cn.xy.hq.vo.AskBid;

@Service
public class JobService extends LogService{
	
	@Autowired
	ExxHqService exxHqService;
	
	@Autowired
	ZbHqService zbHqService;
	
	public void work(){
		Iterator it = Market.map.keySet().iterator();
		while(it.hasNext()){
			String market = (String)it.next();
			AskBid exxab = exxHqService.getAskBid(market);
			AskBid zbab = zbHqService.getAskBid(market);
			double fee = Market.map.get(market);
			
			if(exxab.getAsk1() < zbab.getBid1()){
				double amount = Math.min(exxab.getAsk1_amount(), zbab.getBid1_amount());
				double totalexx = exxab.getAsk1()*amount*0.999;//exx 总共花费
				amount = amount-fee;//去掉转帐数量
				double totalzb = zbab.getBid1()*amount*0.999;//zb 总共花费
				if(totalzb>totalexx){
					logger.info(market+"从exx买："+exxab.getAsk1() +" 到zb卖："+zbab.getBid1());
				}
			}
			
			if(exxab.getBid1() > zbab.getAsk1()){
				double amount = Math.min(exxab.getAsk1_amount(), zbab.getBid1_amount());
				double totalzb = zbab.getAsk1()*amount*0.999;//zb 总共花费
				amount = amount-fee;//去掉转帐数量
				double totalexx = exxab.getBid1()*amount*0.999;//exx 总共花费
				if(totalexx > totalzb){
					logger.info(market+"从zb买："+zbab.getAsk1() +" 到exx卖："+zbab.getBid1());
				}
			}
		}

		
		
		logger.info(".");
	}
	
	
}
