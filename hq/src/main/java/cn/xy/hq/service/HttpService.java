package cn.xy.hq.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class HttpService extends LogService{
	
	public final String ACCESS_KEY = "";
	public final String SECRET_KEY = "";
	public final String URL_PREFIX = " https://trade.exx.com/api/";// 测试环境,测试环境是ttapi测试不通
	
	
	public String get(String urlAll) {
		BufferedReader reader = null;
		String result = null;
		StringBuffer sbf = new StringBuffer();
		String userAgent = "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36";// 模拟浏览器
		try {
			URL url = new URL(urlAll);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setReadTimeout(30000);
			connection.setConnectTimeout(30000);
			connection.setRequestProperty("User-agent", userAgent);
			connection.connect();
			InputStream is = null;
			try{
				is = connection.getInputStream();
			}catch(Exception e){
				
			}
			if(is==null)
				return null;
			reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
			String strRead = null;
			while ((strRead = reader.readLine()) != null) {
				sbf.append(strRead);
				sbf.append("\r\n");
			}
			reader.close();
			result = sbf.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
