package agent.agentC.woker;

import yuk.Thread.TimerWork;
import yuk.dic.ModelDic;
import yuk.model.MonitorData;
import yuk.model.single.StackData;
import agent.agentC.net.AgentClient;
import agent.aspectP.ThreadDump;

public class ThreadWorker extends TimerWork{
	ThreadDump dump = new ThreadDump();

	public ThreadWorker() {
		super();
	}

	@Override
	public void work() throws Exception {
		MonitorData<StackData> mData = new MonitorData<StackData>(ModelDic.SERVER, ModelDic.TYPE_STACK,null);
		dump.work(mData);
		if(mData.dataList.size() > 0)
			AgentClient.client.send(mData);
	}

	@Override
	public void preWork() throws Exception {
		
		
	}

}
