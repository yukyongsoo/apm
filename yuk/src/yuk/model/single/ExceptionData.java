package yuk.model.single;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExceptionData  extends AbsData{
	public ExceptionData(String name, String type) {
		this.name = name;
		this.type = type;
	}
	
	public String type = "";
	public long count = 0;
	//time, stack// [0] is message
	public Map<Long, List<String>> entireStack = new HashMap<Long, List<String>>();
	public String code = "";
	
	public void modelAdd(ExceptionData source){
		count += source.count;
		for(long time : source.entireStack.keySet()){
			entireStack.put(time, source.entireStack.get(time));
		}
	}
}
