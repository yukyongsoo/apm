package agent.agentC.woker;

import agent.agentC.net.AgentClient;
import agent.agentC.tracer.ThreadTracer;
import yuk.Thread.TimerWork;
import yuk.dic.ModelDic;
import yuk.model.MonitorData;
import yuk.model.single.ThreadHotSpotData;
import yuk.util.CommonLogger;

public class HotSpotSender extends TimerWork{
	public HotSpotSender() {
		super();
	}

	@Override
	public void work() throws Exception {
		try {
			MonitorData<ThreadHotSpotData> mData = new MonitorData<ThreadHotSpotData>(ModelDic.SERVER, ModelDic.TYPE_HOTSPOT,null);
			mData.dataList.addAll(ThreadTracer.getEntireMap().values());
			ThreadTracer.getEntireMap().clear();
			for(ThreadHotSpotData data : mData.dataList){
				data.cal();
			}
			if(mData.dataList.size() > 0)
				AgentClient.client.send(mData);
		} catch (Exception e) {
			CommonLogger.getLogger().error(getClass(), "can't send to collector HotSpot Data", e);	
		}
		
	}

	@Override
	public void preWork() throws Exception {
		
		
	}

}
