package cn.xy.hq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MarketFactory {

	@Autowired
	ExxService exxService;
	@Autowired
	ZbService zbService;
	@Autowired
	OkexService okexService;
	@Autowired
	GateIoService gateIoService;
	@Autowired
	BinanceService binanceService;
	@Autowired
	HuobiService huobiService;
	@Autowired
	AexService aexService;
	@Autowired
	CoinwService coinwService;
	@Autowired
	PoloniexService poloniexService;
	
	public BaseService getMarketService(String market){
		switch (market)
		{
		    case "zb":return zbService;
		    case "exx":return exxService;
		    case "okex":return okexService;
		    case "gateio":return gateIoService;
		    case "binance":return binanceService;
		    case "huobi":return huobiService;
		    case "aex":return aexService;
		    case "coinw":return coinwService;
		    case "poloniex":return poloniexService;
		    default:return null ;
		}
		
	}

}
