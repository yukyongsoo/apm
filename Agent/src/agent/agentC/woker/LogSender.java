package agent.agentC.woker;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import yuk.Thread.TimerWork;
import yuk.dic.ModelDic;
import yuk.model.MonitorData;
import yuk.model.single.LogData;
import yuk.util.CommonLogger;
import agent.agentC.net.AgentClient;

public class LogSender extends TimerWork{
	private static MonitorData<LogData> mData;
	public static Queue<LogData> queue = new ConcurrentLinkedQueue<LogData>();
	
	public LogSender() {
		super();
	}
	
	@Override
	public void preWork() throws Exception {
		mData = new MonitorData<LogData>(ModelDic.SERVER, ModelDic.TYPE_LOG,null);
	}

	@Override
	public void work() throws Exception {
		try {
			if(queue.size() > 0){
				mData.dataList.addAll(queue);
				queue.clear();
				AgentClient.client.send(mData);
				mData.dataList.clear();
			}
		} catch (Exception e) {
			CommonLogger.getLogger().error(getClass(), "can't send log Data", e);
		}
		
	}



}
