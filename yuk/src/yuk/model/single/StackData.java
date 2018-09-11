package yuk.model.single;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StackData  extends AbsData{
	public StackData(String name){
		this.name = name;
	}
	public long time = 0;
	public Map<String,String> threadState =  new HashMap<String,String>();
	public Map<String, List<String>> stackTrace = new HashMap<String, List<String>>();
	public List<String> lockThreadList = new ArrayList<String>();
}
