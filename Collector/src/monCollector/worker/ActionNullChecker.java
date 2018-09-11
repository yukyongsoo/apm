package monCollector.worker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import yuk.model.single.TransactionData;
import yuk.util.NormalUtil;

public abstract class ActionNullChecker {
	private static Map<String, List<String>> map = new HashMap<String, List<String>>();
	
	public static void clear(){
		map.clear();
	}
	
	public static void init(String agent, String type){
		String[] typeList = type.split(",");
		List<String> list = new ArrayList<String>();
		for(String t : typeList){
			list.add(t);
		}
		if(list.size() < 1)
			list.add("");
		map.put(agent.toLowerCase(), list);
	}

	public static void putData(Map<String, TransactionData> cache) throws Exception {
		for(String agent : map.keySet()){
			for(String command : map.get(agent)){
				String key = agent;
				if(!command.equals(""))
					key = NormalUtil.makeKey("::", agent,command);
				TransactionData data = new TransactionData(agent, command, 0, 0, 0);
				if(cache.get(key) == null)
					cache.put(key, data);
			}
		}
	}
	
	
	
	
}
