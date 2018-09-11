package agent.agentC.woker;

import java.util.Map;

import agent.agentC.net.AgentClient;
import agent.agentC.tracer.MainTracer;
import yuk.Thread.TimerWork;
import yuk.dic.ModelDic;
import yuk.model.MonitorData;
import yuk.model.multi.DistributionContainer;

public class DisSender extends TimerWork{

	public DisSender() {
		super();
	}

	@Override
	public void work() throws Exception {
		Map<String, DistributionContainer> disMap = MainTracer.getDisMap();
		MonitorData<DistributionContainer> mData = new MonitorData<DistributionContainer>(ModelDic.COLLECTOR, ModelDic.TYPE_MULTIDIS,null);
		mData.dataList.addAll(disMap.values());
		for(DistributionContainer data : mData.dataList)
			data.cal();
		if(mData.dataList.size() > 0)
			AgentClient.client.send(mData);
		disMap.clear();
	}

	@Override
	public void preWork() throws Exception {
		
	}

}
