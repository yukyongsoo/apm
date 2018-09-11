package agent.agentC.tracer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import agent.agentC.config.XmlProperty;
import yuk.model.single.ThreadHotSpotData;

public abstract class ThreadTracer {
	private static Map<String, ThreadHotSpotData> entireMap = new ConcurrentHashMap<String, ThreadHotSpotData>();
		
	public static Map<String, ThreadHotSpotData> getEntireMap() {
		return entireMap;
	}

	public static void traceOut(String shortName,ThreadHotSpotData data) {
		data.totalTime = System.currentTimeMillis() - data.start;
		ThreadHotSpotData old = entireMap.get(shortName);
		if(old == null)		
			entireMap.put(shortName, data);				
		else
			old.modelAdd(data, false);			
	}

	public static ThreadHotSpotData traceIn(String shortName, String longString) {
		ThreadHotSpotData data = new ThreadHotSpotData(XmlProperty.MYNAME);
		data.methodName = longString;
		data.count = 1;
		data.start = System.currentTimeMillis();
		return data;
	}
}
