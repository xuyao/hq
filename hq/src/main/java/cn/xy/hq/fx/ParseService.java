package cn.xy.hq.fx;

import java.util.Iterator;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Service
public class ParseService extends LogService{

	HttpService httpService = new HttpService();
	
	//得到挂单的买卖价格和数量
	public void getTikcer(String coin){
		String url = "https://app.fxh.io/api/coin/get?name="+coin+"&page=1";
		String result = httpService.get(url);
		JSONObject json = JSONObject.parseObject(result);
		if(json==null)
			return;
		JSONArray array = JSONArray.parseArray(json.get("data").toString());
		Iterator it = array.iterator();
		
		Exn exnlow = null;//价格最低的exn
		Exn exnhigh = null;//价格最高的exn
		while(it.hasNext()){
			JSONObject obj = (JSONObject)it.next();
			double volum_24h = obj.getDoubleValue("volum_24h");
			String platform = obj.getString("platform");
			if(volum_24h==0)
				continue;
			if(!isValid(platform))
				continue;
			Exn exn = new Exn();
			exn.setVolum_24h(volum_24h);
			exn.setCurrent_price(obj.getDoubleValue("current_price"));
			exn.setCurrent_price_cny(obj.getDoubleValue("current_price_cny"));
			exn.setCode(obj.getString("code"));
			exn.setName(obj.getString("name"));
			exn.setPlatform(platform);
			exn.setPlatform_name(obj.getString("platform_name"));
			exn.setMarket(obj.getString("market"));
			
			if(exnlow == null)
				exnlow = exn;
			
			if(exnhigh == null)
				exnhigh = exn;
			
			if(exn.getCurrent_price_cny()<exnlow.getCurrent_price_cny())
				exnlow = exn;
			
			if(exn.getCurrent_price_cny()>exnhigh.getCurrent_price_cny())
				exnhigh = exn;
		}
		
		if(exnlow==null || exnhigh==null)
			return;
		if((exnhigh.getCurrent_price_cny()-exnlow.getCurrent_price_cny())
				/(exnlow.getCurrent_price_cny())>0.08){
			String buy = exnlow.getPlatform()+","+exnlow.getPlatform_name()+"买价:"+exnlow.getCurrent_price()
					+" "+exnlow.getCurrent_price_cny()+" 交易对"+exnlow.getMarket();
			String sell = exnhigh.getPlatform()+","+exnhigh.getPlatform_name()+"卖价："+exnhigh.getCurrent_price()
					+" "+exnhigh.getCurrent_price_cny()+" 交易对："+exnhigh.getMarket();
			logger.info(exnlow.getName()+" "+buy+"==="+sell);
		}

		
	}
	
	private boolean isValid(String key){
		if(Coin.exgmap.get(key)!=null)
			return true;
		else
			return false;
	}

}
