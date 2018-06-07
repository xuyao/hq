package cn.xy.hq;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.xy.hq.service.AutoSellJobService;
import cn.xy.hq.service.MarketFactory;

public class AutoMan {

    private static ApplicationContext context;

	public static void main(String[] args){
//		if(args.length!=3)
//			return;
		System.out.println("run autosell start!!!");
		context = new ClassPathXmlApplicationContext("classpath:applicationContent.xml");
		String exg = "okex";
		AutoSellJobService autoSellJobService = (AutoSellJobService)context.getBean("autoSellJobService");
		MarketFactory marketFactory = (MarketFactory)context.getBean("marketFactory");
		marketFactory.initExgs();
		while(true){
			autoSellJobService.work(exg);
			try {
				Thread.sleep(50000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
    }
	
}
