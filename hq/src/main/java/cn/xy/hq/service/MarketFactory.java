package cn.xy.hq.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.xy.hq.util.ConstsUtil;

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
	
	private Map<String,String> map = new HashMap<String,String>();
	
	public void initExgs(){
		String exgs = ConstsUtil.getValue("exgs");
		String[] exgarr = exgs.split(",");
		for(String exg : exgarr){
			map.put(exg, exg);
		}
	}
	
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
