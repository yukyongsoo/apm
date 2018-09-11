package agent.agentC.tracer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import agent.agentC.config.XmlProperty;
import yuk.model.single.DBHotSpotData;

public abstract class DbTracer {
	private static Map<Object, DBHotSpotData> map =
			new ConcurrentHashMap<Object, DBHotSpotData>();
	private static Map<String, DBHotSpotData> finMap =
			new ConcurrentHashMap<String, DBHotSpotData>();

	public static Map<String, DBHotSpotData> getFinMap(){
		return finMap;
	}
	
	public static void dbStart(Object obj,String query){
		DBHotSpotData data = new DBHotSpotData(XmlProperty.MYNAME, query);
		map.put(obj, data);
	}
	
	public static void dbEnd(Object obj,long excute){
		DBHotSpotData data = map.remove(obj);
		if(data != null){
			data.count = 1;
			data.totalTime = excute;

			DBHotSpotData real = finMap.get(data.queryName);
			if (real == null)
				finMap.put(data.queryName, data);
			else
				real.modelAdd(data, false);
		}
	}
	
	public static void dbFin(String query,long excute){
		DBHotSpotData data = new DBHotSpotData(XmlProperty.MYNAME, query);
		data.count = 1;
		data.totalTime = excute;
		
		DBHotSpotData real = finMap.get(query);
		if(real == null)
			finMap.put(query, data);
		else
			real.modelAdd(data, true);
	}
}
