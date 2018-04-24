package cn.xy.hq;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.xy.hq.service.ExnService;
import cn.xy.hq.service.JobService;

public class Hq {
	
    private static ApplicationContext context;

	public static void main(String[] args){
		System.out.println("run hq start!!!");
		context = new ClassPathXmlApplicationContext("classpath:applicationContent.xml");
		ExnService exnService = (ExnService)context.getBean("exnService");
		exnService.parse();
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
