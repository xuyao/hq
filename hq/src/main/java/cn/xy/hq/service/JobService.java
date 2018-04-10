package cn.xy.hq.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.xy.hq.vo.AskBid;
import cn.xy.hq.vo.Exn;

@Service
public class JobService extends LogService{
	
	@Autowired
	ExnService exnService;
	@Autowired
	MarketFactory marketFactory;
	
	public void work(){
		List<Exn> list = exnService.exnlist;
		
		for(Exn exn : list){
			String exname = exn.getExn();//交易对
			double fee = exn.getWdfee();
			List<String> exgs = exn.getExgs();//交易所
			
			AskBid abLow = null;//ab最低价格
			AskBid abHigh = null;//ab最高价格
			for(String exg : exgs){
				BaseService bs = marketFactory.getMarketService(exg);
				AskBid ab = bs.getAskBid(exname);
				ab.setExg(exg);
				if(abLow==null)
					abLow = ab;
				if(abHigh==null)
					abHigh = ab;
				if(abLow.getAsk1()>ab.getAsk1())//如果最低的那个卖一高，换成当前
					abLow = ab;
				if(abHigh.getBid1()<ab.getBid1())//如果最低的那个买一低，换成当前
					abHigh = ab;
//				logger.info(exname+" 市场："+ab.getMarket()+" 卖一："+ab.getAsk1() + " 买一："+ab.getBid1());
			}
			
			if(abLow !=null && abHigh != null){
				double amount = Math.min(abLow.getAsk1_amount(), abHigh.getBid1_amount());
				double totallow = abLow.getAsk1()*amount*0.998;//exx 总共花费
				amount = amount-fee;//去掉转帐数量
				double totalhigh = abHigh.getBid1()*amount*0.998;//zb 总共花费
				if(totallow-totalhigh>10){
					logger.info(exname+"从"+abLow.getExg()+"买："+abLow.getAsk1() +" 到"+
							abHigh.getExg()+"卖："+abHigh.getBid1()+" "+(totallow-totalhigh));
				}
			}
		}
		
		logger.info(".");
	}
	
	
}
