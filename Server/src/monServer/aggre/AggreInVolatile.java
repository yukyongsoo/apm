package monServer.aggre;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import yuk.model.multi.DistributionContainer;
import yuk.model.single.DBHotSpotData;
import yuk.model.single.DistributionData;
import yuk.model.single.ExceptionData;
import yuk.model.single.ThreadHotSpotData;

public abstract class AggreInVolatile {
	//key = name
	private static Map<String, Collection<ThreadHotSpotData>> hotSpotCache = new ConcurrentHashMap<String, Collection<ThreadHotSpotData>>();
	//key = name
	private static Map<String, Collection<DBHotSpotData>> dbHotSpotCache = new ConcurrentHashMap<String, Collection<DBHotSpotData>>();
	//key = name
	private static Map<String, Collection<ExceptionData>> excptCache = new ConcurrentHashMap<String, Collection<ExceptionData>>();
	
	private static Map<String, DistributionContainer> conCache = new ConcurrentHashMap<String, DistributionContainer>();

	public static void saveHotSpot(Collection<ThreadHotSpotData> dataSet) {
		if(dataSet.size() > 0){
			String name = dataSet.iterator().next().name;
			hotSpotCache.put(name, dataSet);
		}
	}

	public static void saveExcpt(Collection<ExceptionData> dataSet) {
		if(dataSet.size() > 0){
			String name = dataSet.iterator().next().name;
			excptCache.put(name, dataSet);
		}
	}

	public static void saveDbHotSpot(Collection<DBHotSpotData> dataSet) {
		if(dataSet.size() > 0){
			String name = dataSet.iterator().next().name;
			dbHotSpotCache.put(name, dataSet);
		}
	}
	
	public static void saveDis(Collection<DistributionContainer> dataSet){
		for(DistributionContainer data : dataSet){
			DistributionContainer con = conCache.get(data.command);
			if(con == null)
				conCache.put(data.command, data);
			else{
				for(DistributionData sub : data.list.values())
					con.addData(sub);
			}
		}
	}

	public static Map<String, Collection<ThreadHotSpotData>> getHotSpot(){
		return hotSpotCache;
	}
	
	public static Map<String, Collection<DBHotSpotData>> getDbHotSpot(){
		return dbHotSpotCache;
	}

	public static Map<String, Collection<ExceptionData>> getExcptSpot(){
		return excptCache;
	}
	
	public static Map<String, DistributionContainer> getDis(){
		return conCache;
	}
	
	public static void clearHotSpot(){
		hotSpotCache.clear();
	}
	
	public static void clearDbHotSpot(){
		dbHotSpotCache.clear();
	}
	
	public static void clearExcpt(){
		excptCache.clear();
	}
	
	public static void clearDis(){
		conCache.clear();
	}	
}
