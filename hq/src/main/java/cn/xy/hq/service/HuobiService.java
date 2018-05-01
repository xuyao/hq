package cn.xy.hq.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huobi.rest.HuobiUtil;

import cn.xy.hq.util.NumberUtil;
import cn.xy.hq.vo.AskBid;
import cn.xy.hq.vo.Balance;

@Service
public class HuobiService extends LogService implements BaseService{

	@Autowired
	HttpService httpService;
	
    private SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");

    private SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
	
    private String apiKey = "340de027-425b5c48-8c93e922-c7598";
    private String secret = "431e96fe-07eca6b3-00ff32a2-426dd";
//    private String apiKey = "598ca4b9-6eec9e98-85558cae-c94a0";
//    private String secret = "e9b6921b-bacc2dcf-9f3a626f-5b389";
	
	
	//得到挂单的买卖价格和数量
	public AskBid getAskBid(String market){
		String mk = market.replaceAll("_", "");
		String ha = "https://api.huobi.br.com/market/depth?symbol="+mk+"&type=step1";
		String result = httpService.get(ha);
		if(StringUtils.isEmpty(result))//如果行情没取到直接返回
			return null;
		JSONObject jsonobject = JSONObject.parseObject(result);
		jsonobject = jsonobject.getJSONObject("tick");
		JSONArray asksArr = jsonobject.getJSONArray("asks");
		JSONArray bidsArr = jsonobject.getJSONArray("bids");
		if(asksArr==null || bidsArr==null
				|| asksArr.size()==0 || bidsArr.size()==0)
			return null;
		JSONArray asks1 = asksArr.getJSONArray(asksArr.size()-1);
		JSONArray bids1 = bidsArr.getJSONArray(0);
		
		AskBid ab = new AskBid();
		ab.setAsk1(asks1.getDouble(0));//卖二
		ab.setAsk1_amount(asks1.getDouble(1));
		ab.setBid1(bids1.getDouble(0));//买一
		ab.setBid1_amount(bids1.getDouble(1));
		ab.setMarket(market);
		return ab;
	}
	
	
	 /**查询未成交单据,返回未成交订单id，需要复写overwrite*/
	public List<String> queryUnfinish(Balance bl) {
		String path = "/v1/order/orders";
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("symbol", bl.getCurrency()+bl.getMarket());//
        map.put("states", "pre-submitted,submitted,partial-filled");//未成交状态,pre-submitted 准备提交, submitted 已提交, partial-filled 部分成交
        TreeMap<String, String> treemap = buildQueryMap(map, path);
        logger.info("查询未成交：");
        String result = httpService.post("https://api.huobipro.com", path, treemap);
        JSONObject jsonobj = JSONObject.parseObject(result);
        JSONArray jsonarr = jsonobj.getJSONArray("data");
        Iterator it = jsonarr.iterator();
        List<String> list = null;
        if(it.hasNext())
        	list = new ArrayList<String>();
        while(it.hasNext()) {
        	JSONObject jsono = (JSONObject)it.next();
        	list.add(jsono.getString("id"));
        }
		return list;
	}
	
	
	 /**下单接口,需要复写overwrite*/
	public void order(Balance bl) {
        String path = "/v1/order/orders/place";
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("account-id", "2009303");//账户号
        map.put("amount", String.valueOf(NumberUtil.formatDouble(bl.getBalance(), bl.getPrecision())));//数量
        map.put("symbol", bl.getCurrency()+bl.getMarket());//交易对
        map.put("type", "sell-market");//sell-market：市价卖, sell-limit：限价卖,
        TreeMap<String, String> treemap = buildQueryMap(map, path);
        logger.info("下单："+bl.getCurrency()+bl.getMarket());
        httpService.post("https://api.huobipro.com", path, treemap);
	}
	
	
	 /**撤单接口,需要复写overwrite*/
	public void cancelOrder(String orderId) {
		String path = "/v1/order/orders/"+orderId+"/submitcancel";
        TreeMap<String, String> treemap = buildQueryMap(null, path);
        logger.info("撤单："+orderId);
        httpService.post("https://api.huobipro.com", path, treemap);
	}
	
	
	 /**获得账户对象剩余币,需要复写overwrite*/
	public Balance getBalance(Balance bl) {
		String path = "/v1/account/accounts/2009303/balance";
        TreeMap<String, String> map = buildQueryMap(null, path);
        String query = HuobiUtil.buildQuery(map);
        String result = httpService.get("https://api.huobipro.com"+path+"?"+query);
//        System.out.println(path+"?"+query);
//        System.out.println(result);
        JSONObject jsonobj = JSONObject.parseObject(result);
        JSONObject jsondata = jsonobj.getJSONObject("data");
        JSONArray list = jsondata.getJSONArray("list");
        Iterator it = list.iterator();
        while(it.hasNext()) {
        	JSONObject jsonObj = (JSONObject)it.next();
        	if(bl.getCurrency().equalsIgnoreCase(jsonObj.getString("currency"))
        			&&"trade".equalsIgnoreCase(jsonObj.getString("type"))) {
        		bl.setBalance(Double.parseDouble(jsonObj.getString("balance")));//设置余额
        		break;
        	}
        }
        
        return bl;
	}
	
	
    /**获得精度对象并且赋值,需要复写overwrite*/
    public Balance getPrecision(String currency, String market){
    	Balance bl = null;
    	String path = "/v1/common/symbols";
        TreeMap<String, String> map = buildQueryMap(null, path);
        String query = HuobiUtil.buildQuery(map);
        String result = httpService.get("https://api.huobipro.com"+path+"?"+query);
        JSONObject jsonobj = JSONObject.parseObject(result);
        JSONArray jsonarr = jsonobj.getJSONArray("data");
        Iterator it = jsonarr.iterator();
        while(it.hasNext()) {
        	JSONObject jo = (JSONObject)it.next();
        	if(currency.equalsIgnoreCase(jo.getString("base-currency"))) {//如果和传入的币种一样
        		bl = new Balance();
        		bl.setCurrency(currency);
        		bl.setMarket(market);
        		bl.setPrecision(jo.getIntValue("amount-precision"));//数量精度
        	}
        }
    	return bl;
    }
    
	
	//或者账户id,huobi必须先查询到id
    public void accounts(){//id:2009303
        String path = "/v1/account/accounts";
        TreeMap<String, String> map = buildQueryMap(null, path);
        String query = HuobiUtil.buildQuery(map);
        String result = httpService.get("https://api.huobipro.com"+path+"?"+query);
//      System.out.println(path+"?"+query);
//      System.out.println(result);
    }
	
    
    private TreeMap<String, String> buildQueryMap(Map<String, String> map, String path){
        TreeMap<String, String> query = new TreeMap<>();
        query.put("AccessKeyId", apiKey);
        query.put("SignatureMethod","HmacSHA256");
        query.put("SignatureVersion","2");
        Date now = new Date();
        sdfDate.setTimeZone(TimeZone.getTimeZone("UTC"));
        sdfTime.setTimeZone(TimeZone.getTimeZone("UTC"));
        String timestamp = String.format("%sT%s",sdfDate.format(now),sdfTime.format(now));
        query.put("Timestamp", timestamp);

        if((map != null) && !map.isEmpty()){
            for (Map.Entry<String, String> e : map.entrySet()){
                query.put(e.getKey(), e.getValue());
            }
        }
        String data = String.format("%s\napi.huobipro.com\n%s\n%s","GET",path,HuobiUtil.buildQuery(query));
        String sign = HuobiUtil.hashMac256(data, secret);
        query.put("Signature", sign);
        return query;
    }
    
    
}
