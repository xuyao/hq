package cn.xy.hq;

import cn.xy.hq.fx.Coin;
import cn.xy.hq.fx.TaskService;

public class FxHq {
	
    public static void main(String[] args){
		System.out.println("run fx start!!!");
		Coin.init();
		TaskService taskService = new TaskService();
		while(true){
			taskService.run();
		}
		
//    	String url = "https://app.fxh.io/api/coin/coinrank?page=10000&sortfield=vol&asc=0&update=1";
//    	HttpService service = new HttpService();
//    	String result = service.get(url);
//    	JSONObject json = JSONObject.parseObject(result);
//    	JSONArray array = JSONArray.parseArray(json.get("data").toString());
//    	Iterator it = array.iterator();
//    	StringBuilder sb = new StringBuilder();
//    	while(it.hasNext()){
//    		JSONObject jo = (JSONObject)it.next();
//    		sb.append(jo.getString("code"))
//    		.append(",")
//    		.append(jo.getString("name")).append("\n");
//    	}
//    	try {
//			FileUtils.writeStringToFile(new File("f:\\coin.txt"), sb.toString(),"utf-8");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
    	
    }

}
