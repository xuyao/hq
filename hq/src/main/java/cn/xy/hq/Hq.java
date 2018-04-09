package cn.xy.hq;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.xy.hq.fee.Market;

public class Hq {
	
    public static void main(String[] args){
	System.out.println("run hq start!!!");
	Market.init();
	ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContent.xml");
    }
    
}
