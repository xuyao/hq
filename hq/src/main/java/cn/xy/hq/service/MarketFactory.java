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
	@Autowired
	BitfinexService bitfinexService;
	@Autowired
	BitzService bitzService;
	
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
		    case "zb":return (map.get("zb")==null?null:zbService);
		    case "exx":return (map.get("exx")==null?null:exxService);
		    case "okex":return (map.get("okex")==null?null:okexService);
		    case "gateio":return (map.get("gateio")==null?null:gateIoService);
		    case "binance":return (map.get("binance")==null?null:binanceService);
		    case "huobi":return (map.get("huobi")==null?null:huobiService);
		    case "aex":return (map.get("aex")==null?null:aexService);
		    case "coinw":return (map.get("coinw")==null?null:coinwService);
		    case "poloniex":return (map.get("poloniex")==null?null:poloniexService);
		    case "bit-z":return (map.get("bit-z")==null?null:bitzService);
//		    case "bitfinex":return (map.get("bitfinex")==null?null:bitfinexService);
		    default:return null ;
		}
	}

}
