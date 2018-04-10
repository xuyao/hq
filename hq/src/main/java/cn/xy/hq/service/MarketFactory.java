package cn.xy.hq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MarketFactory {

	@Autowired
	ExxHqService exxHqService;
	@Autowired
	ZbHqService zbHqService;
	@Autowired
	OkexService okexService;
	
	public BaseService getMarketService(String market){
		switch (market)
		{
		    case "zb":return zbHqService;
		    case "exx":return exxHqService;
		    case "okex":return okexService;
		    default:return null ;
		}
		
	}

}
