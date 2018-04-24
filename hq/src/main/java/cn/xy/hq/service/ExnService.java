package cn.xy.hq.service;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import cn.xy.hq.vo.Exn;

@Service
public class ExnService extends LogService{
	
	public List<Exn> exnlist = new ArrayList<Exn>();
	public TreeMap<String,Double> feemap = new TreeMap<String,Double>();;
	
	public void parse(){
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
			exn.setExgs(Arrays.asList(s[1].split(",")));
			exnlist.add(exn);
		}
		
		//coin fee init
		URL urlfee = ExnService.class.getClassLoader().getResource("wdfee.txt");
		List<String> feelist = null;
	    try {
	    	feelist = FileUtils.readLines(new File(urlfee.getFile()),"utf-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	    for(String s : feelist){
	    	String[] sa = s.split(",");
	    	feemap.put(sa[0], Double.parseDouble((sa[1])));
	    }
	}

}
