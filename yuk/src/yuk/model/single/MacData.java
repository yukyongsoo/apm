package yuk.model.single;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MacData  extends AbsData{
	public MacData(String name, String command){
		this.name = name;
		this.command = command;
	}
	public long time = 0;
	public String command = "";
	public List<String> controllerList = new ArrayList<String>();
	public Map<String, Map<String, Object>> dataList = new HashMap<String, Map<String,Object>>();
}
