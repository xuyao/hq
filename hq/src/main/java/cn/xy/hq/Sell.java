package cn.xy.hq;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.xy.hq.service.AutoSellJobService;

public class Sell {

    private static ApplicationContext context;

	public static void main(String[] args){
//		if(args.length!=3)
//			return;
		System.out.println("run autosell start!!!");
		context = new ClassPathXmlApplicationContext("classpath:applicationContent.xml");
		String exg = "huobi";
		String symbol = "btc";
		String market = "usdt";
		String amount = "100";
		AutoSellJobService autoSellJobService = (AutoSellJobService)context.getBean("autoSellJobService");
		while(true){
			autoSellJobService.work(exg, symbol, market, amount);
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
    }
	
}
