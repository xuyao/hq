package cn.xy.hq.fx;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;

public class Coin {

	public static final Properties prop = new Properties();
	public static List<String> list = null;
	public static Map<String,String> exgmap = new HashMap<String,String>();
	public static void init(){
		InputStream is = Coin.class.getClassLoader().getResourceAsStream("conf.properties");
		try {
			prop.load(is);
			String file = prop.getProperty("coinpath");
			list = FileUtils.readLines(new File(file),"utf-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		exgmap.put("zb", "ZB网");
		exgmap.put("coinw", "币赢");
		exgmap.put("gate-io", "比特儿海外版");
		exgmap.put("binance", "币安网");
		exgmap.put("okex", "OKEX");
		exgmap.put("huobipro", "火币Pro-Huobi");
		exgmap.put("coinegg", "币蛋");
		exgmap.put("poloniex", "P网");
		exgmap.put("gdax", "GDAX");
		exgmap.put("bitfinex", "Bitfinex");
//		exgmap.put("upbit", "Upbit");
		
	}
}
