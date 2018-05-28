package cn.xy.hq.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.xy.hq.util.ConstsUtil;
import cn.xy.hq.util.MsgUtil;
import cn.xy.hq.util.NumberUtil;
import cn.xy.hq.vo.AskBid;
import cn.xy.hq.vo.Exn;

@Service
public class JobService extends LogService{
	
	@Autowired
	ExnService exnService;
	@Autowired
	MarketFactory marketFactory;
	
	String no = ConstsUtil.getValue("no");
	double percent = Double.parseDouble(ConstsUtil.getValue("percent"));
	
	public void work(){
		List<Exn> list = exnService.exnlist;
		StringBuilder sb = new StringBuilder();
		
		for(Exn exn : list){
			String exname = exn.getExn();//交易对
			List<String> exgs = exn.getExgs();//交易所
			if(exgs==null || exgs.size()<2)//交易所小于2个无法比较
				continue;//skip this loop
			
			AskBid abLow = null;//ab最低价格
			AskBid abHigh = null;//ab最高价格
			for(String exg : exgs){
				BaseService bs = marketFactory.getMarketService(exg);
				if(bs==null)
					continue;
//				System.out.println(exname+" "+exg);
				AskBid ab = bs.getAskBid(exname);
				if(ab==null)
					continue;
				ab.setExg(exg);
				if(abLow==null)
					abLow = ab;
				if(abHigh==null)
					abHigh = ab;
				if(abLow.getAsk1()>ab.getAsk1())//如果最低的那个卖一高，换成当前
					abLow = ab;
				if(abHigh.getBid1()<ab.getBid1())//如果最低的那个买一低，换成当前
					abHigh = ab;
//				logger.info(exname+" 市场："+ab.getExg()+" 卖一："+ab.getAsk1() + " 买一："+ab.getBid1());
			}
			
			
			if(abLow !=null && abHigh != null){
				double totallow = abLow.getAsk1()*0.998;//exx 总共花费
				double totalhigh = abHigh.getBid1()*0.998;//zb 总共花费
				if((totalhigh-totallow)/totallow>percent){
					sb.append(exname+" "+abLow.getExg()+"买："+NumberUtil.big(abLow.getAsk1(), 8) 
							+"|"+ abLow.getAsk1_amount()+" "+
							abHigh.getExg()+"卖："+NumberUtil.big(abHigh.getBid1(), 8) +"|"+abHigh.getBid1_amount()
							+" "+(totalhigh/totallow-1));
					sb.append("\n");
					sb.append("-------------------------------------");
					sb.append("\n");
				}
			}
		}
		if(sb.length()!=0){
			MsgUtil.sendText(sb.toString());
			logger.info(sb.toString());
		}
		logger.info("**********************"+no);
	}
	
}
