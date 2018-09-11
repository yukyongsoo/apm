package agent.aspectP;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import agent.agentC.config.XmlProperty;
import yuk.model.MonitorData;
import yuk.model.single.StackData;
import yuk.util.CommonLogger;

public class ThreadDump {

	private StackData getStack() throws Exception{	
		StackData data = new StackData(XmlProperty.MYNAME);
		ThreadMXBean bean = ManagementFactory.getThreadMXBean();
		long[] deadLocked = bean.findDeadlockedThreads();
		Set<Long> set = new HashSet<Long>();
		if(deadLocked != null){
			for(long tId : deadLocked)
				set.add(tId);
		}
		
		ThreadInfo[] infos = bean.getThreadInfo(bean.getAllThreadIds(), 30);
		for(ThreadInfo info : infos){		
			List<String> list = new ArrayList<String>();
			data.threadState.put(info.getThreadName(), info.getThreadState().toString());			
			if(set.contains(info.getThreadId()))
				data.lockThreadList.add(info.getThreadName());
			StackTraceElement[] statcks = info.getStackTrace();
			for(StackTraceElement element : statcks){			
				list.add(element.toString());
			}	
			data.stackTrace.put(info.getThreadName(), list);
		}		
		return data;
	}
	
	public void work(MonitorData<StackData> mData) {
		try {
			StackData data = getStack();
			mData.dataList.add(data);
		} catch (Exception e) {
			CommonLogger.getLogger().error(getClass(),"can't send stack data",e);
		}
	}
	
}
