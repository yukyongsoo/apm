package agent.agentC.woker;

import yuk.Thread.TimerWork;
import yuk.dic.ModelDic;
import yuk.model.MonitorData;
import yuk.model.single.DBHotSpotData;
import yuk.util.CommonLogger;
import agent.agentC.net.AgentClient;
import agent.agentC.tracer.DbTracer;

public class DbHotSpotSender extends TimerWork{
	
	public DbHotSpotSender() {
		super();
	}

	@Override
	public void work() throws Exception {
		try {
			MonitorData<DBHotSpotData> mData = new MonitorData<DBHotSpotData>(ModelDic.SERVER, ModelDic.TYPE_DBHOTSPOT,null);
			mData.dataList.addAll(DbTracer.getFinMap().values());
			DbTracer.getFinMap().clear();
			for(DBHotSpotData data : mData.dataList){
				data.cal();
			}
			if(mData.dataList.size() > 0)
				AgentClient.client.send(mData);
		} catch (Exception e) {
			CommonLogger.getLogger().error(getClass(), "can't send to collector DataBase HotSpot Data", e);	
		}
	}

	@Override
	public void preWork() throws Exception {
		
		
	}
}
