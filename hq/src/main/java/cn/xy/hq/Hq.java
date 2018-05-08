package cn.xy.hq;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.xy.hq.service.CoinwService;
import cn.xy.hq.service.ExnService;
import cn.xy.hq.service.JobService;
import cn.xy.hq.service.MarketFactory;

public class Hq {
	
    private static ApplicationContext context;

	public static void main(String[] args){
		System.out.println("run hq start!!!");
		context = new ClassPathXmlApplicationContext("classpath:applicationContent.xml");
		MarketFactory marketFactory = (MarketFactory)context.getBean("marketFactory");
		marketFactory.initExgs();
		ExnService exnService = (ExnService)context.getBean("exnService");
		exnService.parse();
		CoinwService coinwService = (CoinwService)context.getBean("coinwService");
		coinwService.init();
		
		JobService jobService = (JobService)context.getBean("jobService");
		while(true){
			jobService.work();
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
    }

}
