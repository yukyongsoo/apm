package monCollector.worker;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import monCollector.net.NetMap;
import yuk.Thread.TimerWork;
import yuk.dic.ModelDic;
import yuk.model.MonitorData;
import yuk.model.single.ResourceData;
import yuk.util.NormalUtil;

public class AggreResThread extends TimerWork{
	//key = name::command::rid
	private Map<String, ResourceData> cache = new ConcurrentHashMap<String, ResourceData>();
	
	public AggreResThread() {
		super();
	}

	@Override
	public void work() throws Exception {
		MonitorData<ResourceData> object = new MonitorData<ResourceData>(ModelDic.SERVER, ModelDic.TYPE_RESOURCE,null);
		for(ResourceData data : cache.values()){
			object.dataList.add(data);
		}
		cache.clear();
		if(object.dataList.size() > 0)
			NetMap.send(object);
	}

	@Override
	public void preWork() throws Exception {
		
		
	}

	public void aggre(Collection<ResourceData> dataSet) {
		for(ResourceData data : dataSet){
			String key = NormalUtil.makeKey("::", data.name,data.command,data.Rid);
			if(cache.containsKey(key)){
				ResourceData old = cache.get(key);
				old.modelAdd(data, true);
			}
			else{
				cache.put(key, data);
			}
		}
	}

}
