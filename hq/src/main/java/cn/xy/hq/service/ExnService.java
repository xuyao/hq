package cn.xy.hq.service;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import cn.xy.hq.vo.Exn;

@Service
public class ExnService extends LogService{
	
	public List<Exn> exnlist = new ArrayList<Exn>();
	public Map<String,List<String>> blackmap = new HashMap<String,List<String>>();;
	
	public void parse(){
		
		//black list
		URL urlBlack = ExnService.class.getClassLoader().getResource("black.txt");
		List<String> blackList = null;
	    try {
	    	blackList = FileUtils.readLines(new File(urlBlack.getFile()),"utf-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	    for(String s : blackList){
	    	String[] sa = s.split(":");
	    	blackmap.put(sa[0], Arrays.asList(sa[1].split(",")));
	    }
	    
	    
	    
		//exn init
		URL url = ExnService.class.getClassLoader().getResource("coinexg.txt");
	    File file = new File(url.getFile());
		List<String> txts = null;
		try {
			txts = FileUtils.readLines(file, "utf-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(String txt : txts){
			String[] s = txt.split(":");
			Exn exn = new Exn();
			exn.setExn(s[0]);
			List<String> ll = new ArrayList(Arrays.asList(s[1].split(",")));
			if(blackmap.get(s[0])!=null)
				ll.removeAll(blackmap.get(s[0]));
			exn.setExgs(ll);
			exnlist.add(exn);
		}
		

	}

}
