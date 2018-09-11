package monCollector.worker;

import java.util.HashMap;
import java.util.Map;

import monCollector.config.XmlProperty;
import monCollector.net.NetMap;
import yuk.Thread.TimerWork;
import yuk.dataBase.DBController;
import yuk.dic.ModelDic;
import yuk.dic.MsgDic;
import yuk.model.MonitorData;
import yuk.model.single.HealthData;
import yuk.util.CommonLogger;

public class HealthSender extends TimerWork{
	public static Map<String, HealthData> healthMap = new HashMap<String, HealthData>();

	public HealthSender() {
		super();		
	}
	
	@Override
	public void work() throws Exception {
		try {
			HealthData data = checkHealth();		
			MonitorData<HealthData> object = new MonitorData<HealthData>(ModelDic.SERVER, ModelDic.TYPE_HEALTH,null);
			object.dataList.add(data);
			for(HealthData subData : healthMap.values()){
				object.dataList.add(subData);
			}
			NetMap.send(object);
			healthMap.clear();
		} catch (Exception e) {		
			CommonLogger.getLogger().error(getClass(), "can't send health data to server", e);		
		}
	}
	
	private HealthData checkHealth() throws Exception{	
		HealthData data= new HealthData(XmlProperty.MYNAME);
		data.time = System.currentTimeMillis();
		if(!NetMap.mainServer.isConnected()){
			data.message = "Main " + MsgDic.NETERROR;
			data.status = false;
		}
		if(XmlProperty.BACKUP && !NetMap.backServer.isConnected()){
			data.message = "backup " + MsgDic.NETERROR;
			data.status = false;
		}
		if(!DBController.isFullConntcted()){
				data.message = MsgDic.DBERROR;		
				data.status = false;
		}				
		return data;
	}

	@Override
	public void preWork() throws Exception {
		
		
	}

}
