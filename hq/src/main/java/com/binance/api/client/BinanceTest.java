package com.binance.api.client;

import java.util.List;

import com.binance.api.client.domain.market.OrderBook;
import com.binance.api.client.domain.market.OrderBookEntry;

public class BinanceTest {

	public static void main(String[] args) {
		
		BinanceApiRestClient client = BinanceApiClientFactory.newInstance().newRestClient();
		OrderBook ob = client.getOrderBook("eth_btc",2);
		List<OrderBookEntry> asks = ob.getAsks();
		List<OrderBookEntry> bids = ob.getBids();
		for(OrderBookEntry o : asks) {
			System.out.println("asks: "+o.getPrice()+" "+o.getQty());;
		}
		for(OrderBookEntry o : bids) {
			System.out.println("bids: "+o.getPrice()+" "+o.getQty());;
		}
	}
	
	
}
