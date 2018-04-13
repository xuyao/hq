package cn.xy.hq.fx;

import java.util.List;

public class TaskService{
	
	ParseService parseService = new ParseService();
	
	public void run() {
		List<String> list = Coin.list;
		
		int i = 0;
		for(String s : list){
			String[] as = s.split(",");
			parseService.getTikcer(as[0]);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			i++;
			if(i>100)
				break;
		}
		
		
		try {
			Thread.sleep(1000*60);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
