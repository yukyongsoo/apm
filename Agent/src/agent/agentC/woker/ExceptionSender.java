package agent.agentC.woker;

import yuk.Thread.TimerWork;
import yuk.dic.ModelDic;
import yuk.model.MonitorData;
import yuk.model.single.ExceptionData;
import yuk.util.CommonLogger;
import agent.agentC.net.AgentClient;
import agent.agentC.tracer.ExceptionTracer;

public class ExceptionSender extends TimerWork{

	public ExceptionSender() {
		super();
	}

	@Override
	public void work() throws Exception {
		try {
			MonitorData<ExceptionData> mData = new MonitorData<ExceptionData>(ModelDic.SERVER, ModelDic.TYPE_EXCPT,null);
			mData.dataList.addAll(ExceptionTracer.getEntireMap().values());
			ExceptionTracer.getEntireMap().clear();
			if(mData.dataList.size() > 0)
				AgentClient.client.send(mData);
		} catch (Exception e) {
			CommonLogger.getLogger().error(getClass(), "can't send collector DataBase HotSpot Data", e);	
		}
	}

	@Override
	public void preWork() throws Exception {
		
		
	}

}
