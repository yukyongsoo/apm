package yuk.model.single;

import java.util.HashMap;
import java.util.Map;

import yuk.model.etc.SubResourceData;

public class ResourceData  extends AbsData{	
	public ResourceData(String name, String command, String rid) throws Exception{
		this.name =name;
		this.command = command;
		this.Rid = rid;
	}
	
	public long time = 0;
	public String command = "";
	public String Rid = "";
	public Map<String, SubResourceData> map = new HashMap<String, SubResourceData>();

	public void modelAdd(ResourceData source,boolean cal){
		for(String key : source.map.keySet()){
			if(map.get(key) == null){
				map.put(key, source.map.get(key));
			}
			else{
				SubResourceData data = map.get(key);
				data.modelAdd(source.map.get(key), cal);
			}
		}
	}
}
