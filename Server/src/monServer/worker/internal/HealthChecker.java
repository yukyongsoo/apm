package monServer.worker.internal;

import java.util.HashMap;
import java.util.Map;

import monServer.aggre.AggreInMem;
import monServer.dbAction.DbActionBase;
import yuk.Thread.TimerWork;
import yuk.dic.ModelDic;
import yuk.dic.MsgDic;
import yuk.dic.SystemDic;
import yuk.model.etc.EventData;
import yuk.model.single.HealthData;

public class HealthChecker extends TimerWork{
	public static Map<String, HealthData> healthMap = new HashMap<String, HealthData>();
	
	public HealthChecker() {
		super();
	}

	@Override
	public void work() throws Exception {
		long current = System.currentTimeMillis();
		for(String key : healthMap.keySet()){
			HealthData data = healthMap.get(key);
			if(current - data.time > (SystemDic.PCODE_MIN * 5)){
				data.status = false;
				data.message = MsgDic.NETERROR;
			}
			if(!data.status){
				EventData eventData = new EventData(key, ModelDic.EVENTLEVEL_ERROR, data.message);
				AggreInMem.updateEvent(eventData);
				DbActionBase.insertEvent(eventData);				
			}
		}
	}

	@Override
	public void preWork() throws Exception {
		
		
	}

}
