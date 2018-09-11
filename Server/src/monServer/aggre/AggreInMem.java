package monServer.aggre;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import yuk.model.etc.EventData;
import yuk.model.multi.LogContainer;
import yuk.model.multi.ResourceContainer;
import yuk.model.multi.TransContainer;
import yuk.model.single.LogData;
import yuk.model.single.MacData;
import yuk.model.single.ResourceData;
import yuk.model.single.StackData;
import yuk.model.single.TransactionData;
import yuk.util.NormalUtil;

public abstract class AggreInMem {
	//key = name:command;
	private static Map<String, TransactionData> transCache = new ConcurrentHashMap<String, TransactionData>();
	//key = name::command:rid;
	private static Map<String, ResourceData> resCache = new ConcurrentHashMap<String, ResourceData>();
	//key = name
	private static Map<String, LogContainer> logCache = new ConcurrentHashMap<String, LogContainer>();
	//key = name:command
	private static Map<String, MacData> macCache = new ConcurrentHashMap<String, MacData>();
	//key = name
	private static Map<String, StackData> stackCache = new ConcurrentHashMap<String, StackData>();
	//key = hashCode;
	private static Queue<EventData> eventCache = new ConcurrentLinkedQueue<EventData>();
	
	public static void updateTrans(Collection<TransactionData> dataSet) {
		for(TransactionData data : dataSet){
			String key = NormalUtil.makeKey("::", data.name,data.command);
			TransactionData target = transCache.get(key);
			if(target == null)
				transCache.put(key, data);
			else{
				target.modelAdd(data, true);
			}
		}		
	}
	
	public static Collection<TransContainer> getTrans(){
		Map<String, TransContainer> map = new HashMap<String, TransContainer>();
		for(TransactionData data : transCache.values()){
			TransContainer con = map.get(data.name);
			if(con == null){
				con = new TransContainer();
				con.name = data.name;
				map.put(con.name, con);
			}
			con.list.add(data);
		}		
		transCache.clear();
		return map.values();
	}

	public static void updateRes(Collection<ResourceData> dataSet) {
		for(ResourceData data : dataSet){
			String key = NormalUtil.makeKey("::", data.name,data.command,data.Rid);
			ResourceData target = resCache.get(key);
			if(target == null)
				resCache.put(key, data);
			else{
				target.modelAdd(data, true);
			}
		}			
	}
	
	public static Collection<ResourceContainer> getRes(){
		Map<String, ResourceContainer> map = new HashMap<String, ResourceContainer>();
		for(ResourceData data : resCache.values()){
			ResourceContainer con = map.get(data.name);
			if(con == null){
				con = new ResourceContainer();
				con.name = data.name;
				map.put(con.name, con);
			}
			con.list.add(data);
		}		
		resCache.clear();
		return map.values();
	}
	
	public static void updateLog(Collection<LogData> dataSet) {
		for(LogData data : dataSet){
			LogContainer target = logCache.get(data.name);
			if(target == null){
				target = new LogContainer();
				target.name = data.name;
				logCache.put(data.name, target);
			}
			target.list.add(data);
		}			
	}
	
	public static Collection<LogContainer> getLog(){
		List<LogContainer> list = new ArrayList<LogContainer>();
 		list.addAll(logCache.values());
 		logCache.clear();
		return list;
	}	
	
	public static void updateEvent(Collection<EventData> dataSet) {
		eventCache.addAll(dataSet);
	}
	
	public static void updateEvent(EventData data) {
		eventCache.add(data);
	}
	
	public static Collection<EventData> getEvent() {
		return eventCache;
	}
	
	public static void clearEvent(){
		eventCache.clear();
	}

	public static void updateMac(Collection<MacData> dataSet) {
		for(MacData data : dataSet){
			String key = NormalUtil.makeKey("::", data.name,data.command);
			macCache.put(key, data);
		}			
	}
	
	public static Collection<MacData> getMac() {
		List<MacData> list = new ArrayList<MacData>();
		list.addAll(macCache.values());
 		macCache.clear();
		return list;
	}

	public static void upateStack(Collection<StackData> dataSet) {
		for(StackData data : dataSet){
			stackCache.put(data.name, data);
		}
	}
	
	public static Collection<StackData> getStack() {
		List<StackData> list = new ArrayList<StackData>();
		list.addAll(stackCache.values());
 		stackCache.clear();
		return list;
	}
}
