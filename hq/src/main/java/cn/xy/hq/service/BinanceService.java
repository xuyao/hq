package cn.xy.hq.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.market.OrderBook;
import com.binance.api.client.domain.market.OrderBookEntry;

import cn.xy.hq.vo.AskBid;

@Service
public class BinanceService extends LogService implements BaseService{
	
	@Autowired
	HttpService httpService;
	
	//得到挂单的买卖价格和数量
	public AskBid getAskBid(String market){
		BinanceApiRestClient client = BinanceApiClientFactory.newInstance().newRestClient();
		market = market.replaceAll("_", "").toUpperCase();
		OrderBook ob = client.getOrderBook(market,5);
		List<OrderBookEntry> asks = ob.getAsks();
		List<OrderBookEntry> bids = ob.getBids();
		AskBid ab = new AskBid();
		ab.setAsk1(Double.parseDouble(asks.get(1).getPrice()));
		ab.setAsk1_amount(Double.parseDouble(asks.get(1).getQty()));
		ab.setBid1(Double.parseDouble(bids.get(0).getPrice()));
		ab.setBid1_amount(Double.parseDouble(bids.get(0).getQty()));
		ab.setMarket(market);
		return ab;
	}

}
