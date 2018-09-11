package monCollector.worker;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import monCollector.net.NetMap;
import yuk.Thread.TimerWork;
import yuk.dic.ModelDic;
import yuk.model.MonitorData;
import yuk.model.single.TransactionData;
import yuk.util.CommonLogger;
import yuk.util.NormalUtil;

public class AggreActionThread extends TimerWork{
	//key = name::group
	private Map<String, TransactionData> cache = new ConcurrentHashMap<String, TransactionData>();

	public AggreActionThread() {
		super();
	}

	@Override
	public void work() throws Exception {
		try {
			MonitorData<TransactionData> object = new MonitorData<TransactionData>(ModelDic.SERVER, ModelDic.TYPE_TRANS,null);	
			for(TransactionData data : cache.values()){
				object.dataList.add(data);
			}
			cache.clear();
			ActionNullChecker.putData(cache);
			if(object.dataList.size() > 0)
				NetMap.send(object);
		} catch (Exception e) {
			CommonLogger.getLogger().error(getClass(), "can't aggregate transaction", e);	
		}
	}
	
	@Override
	public void preWork() throws Exception {
		
		
	}

	public void aggre(Collection<TransactionData> dataSet) throws Exception {
		for(TransactionData data : dataSet){
			String key = NormalUtil.makeKey("::", data.name,data.command);
			if(cache.containsKey(key)){
				TransactionData old = cache.get(key);
				old.modelAdd(data, true);
			}
			else{
				cache.put(key, data);
			}
		}
		
	}
}
