package cn.xy.hq;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.xy.hq.service.ExnService;

public class Hq {
	
    public static void main(String[] args){
		System.out.println("run hq start!!!");
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContent.xml");
		ExnService exnService = (ExnService)context.getBean("exnService");
		exnService.parse();
    }

}
