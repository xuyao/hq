package cn.xy.hq.service;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import cn.xy.hq.vo.Exn;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Service
public class ExnService extends LogService{
	
	public List<Exn> exnlist = new ArrayList<Exn>();
	public TreeMap<String,Double> feemap = new TreeMap<String,Double>();;
	
	public void parse(){
		//exn init
		URL url = ExnService.class.getClassLoader().getResource("usdt.json");
	    File file = new File(url.getFile());
		String json ="";
		try {
			json = FileUtils.readFileToString(file, "utf-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		JSONArray jsonarr = JSONArray.parseArray(json);
		Iterator it = jsonarr.iterator();
		while(it.hasNext()){
			JSONObject jsonObj = (JSONObject)it.next();
			Exn exn = new Exn();
			exn.setExn(jsonObj.getString("exn"));
			exn.setExgs(jsonObj.getJSONArray("exg").toJavaList(String.class));
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
