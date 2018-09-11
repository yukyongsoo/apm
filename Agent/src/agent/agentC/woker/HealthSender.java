package agent.agentC.woker;

import java.net.Socket;

import yuk.Thread.TimerWork;
import yuk.dic.ModelDic;
import yuk.dic.MsgDic;
import yuk.model.MonitorData;
import yuk.model.single.HealthData;
import yuk.util.CommonLogger;
import agent.agentC.config.XmlProperty;
import agent.agentC.net.AgentClient;

public class HealthSender extends TimerWork{
	public HealthSender() {
		super();
	}

	@Override
	public void preWork() throws Exception {
		
		
	}

	@Override
	public void work() throws Exception {
		try {
			MonitorData<HealthData> mData = new MonitorData<HealthData>(ModelDic.COLLECTOR, ModelDic.TYPE_HEALTH,null);
			HealthData data = checkHealth();		
			mData.dataList.add(data);
			AgentClient.client.send(mData);
		} catch (Exception e) {
			CommonLogger.getLogger().error(getClass(), "can't send collector health data", e);	
		}	
	}

	
	private HealthData checkHealth() throws Exception{
		HealthData data = new HealthData(XmlProperty.MYNAME);	
		data.time = System.currentTimeMillis();
		if(!AgentClient.client.isConnected()){
			data.message = MsgDic.NETERROR;	
			data.status = false;
		}		
		try {
			if(XmlProperty.XVARM){
				Socket socket = new Socket(XmlProperty.XVARMIP,	XmlProperty.XVARMPORT);
				if (!socket.isConnected()) {
					data.message = "APP has not connected";
					data.status = false;
				}
				socket.close();
			}
		} catch (Exception e) {			
			data.message =  "APP has not connected";
			data.status = false;
		} 
		return data;
	}
}
