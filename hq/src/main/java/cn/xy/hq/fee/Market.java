package cn.xy.hq.fee;

import java.util.HashMap;

public class Market {

	public static HashMap<String,Double> map = new HashMap<String,Double>();
	
	public static void init(){
		map.put("btc_qc", 0.001);//5,20
		map.put("eth_qc", 0.01);//20,100
		map.put("etc_qc", 0.01);//1000,5000
		map.put("tv_qc", 0.1);//50000,300000
		map.put("qtum_qc", 0.01);//1000,5000
		map.put("hsr_qc", 0.001);//1000,5000
		map.put("ltc_qc", 0.005);//100,500
		map.put("ink_qc", 20.0);//5000,30000
//		map.put("ubtc_qc", 0.005);//100,500
//		map.put("gram_qc", 5000.0);//1000,5000
		
	}
}
