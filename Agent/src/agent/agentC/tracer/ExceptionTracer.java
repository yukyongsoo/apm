package agent.agentC.tracer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import agent.agentC.config.XmlProperty;
import agent.agentC.deCom.Decom;
import yuk.model.single.ExceptionData;

public abstract class ExceptionTracer {
	private static Map<String, ExceptionData> entireMap =new ConcurrentHashMap<String, ExceptionData>();
	
	
	public static Map<String, ExceptionData> getEntireMap() {
		return entireMap;
	}

	public static void TraceIn(Exception e){
		String type = e.getClass().getName();
		ExceptionData data;
		if(entireMap.get(type) == null){
			data = new ExceptionData(XmlProperty.MYNAME,type);
			entireMap.put(type, data);
		}
		data = entireMap.get(type);
		data.count += 1;
		List<String> stackTrace = new ArrayList<String>();
		String message = e.getMessage();
		if(message != null)
			stackTrace.add(e.getMessage());
		for(StackTraceElement ste : e.getStackTrace()){
			stackTrace.add(ste.toString());
		}
		data.entireStack.put(System.currentTimeMillis(), stackTrace);
		for(StackTraceElement ele : e.getStackTrace()){
			if(!ele.getClassName().contains("java.")){
				data.code = Decom.getClassName(ele.getClassName()).code;
				break;
			}
		}
	}
	
	
}
 