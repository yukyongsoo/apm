package agent.agentC.woker;

import yuk.Thread.TimerWork;
import yuk.dic.ModelDic;
import yuk.model.MonitorData;
import yuk.model.single.ResourceData;
import yuk.util.CommonLogger;
import agent.agentC.config.XmlProperty;
import agent.agentC.net.AgentClient;
import agent.aspectP.CPU;
import agent.aspectP.Gc;
import agent.aspectP.JavaHeap;
import agent.aspectP.MemPool;
import agent.ext.xtorm.XtormArchive;
import agent.ext.xtorm.XtormDataConn;
import agent.ext.xtorm.XtormDbConn;

public class ResourceSender extends TimerWork {
	public CPU cpu = new CPU();
	public JavaHeap heap = new JavaHeap();
	public XtormArchive archive = new XtormArchive();
	public Gc gcCopy = new Gc();
	public MemPool memPool = new MemPool();
	
	public XtormDataConn dataConn = new XtormDataConn();
	public XtormDbConn dbConn = new XtormDbConn();

	public ResourceSender() {
		super();
	}

	@Override
	public void work() throws Exception {
		try {
			MonitorData<ResourceData> data = new MonitorData<ResourceData>(ModelDic.COLLECTOR, ModelDic.TYPE_RESOURCE,null);
			if (cpu != null)
				cpu.work(data);
			if (heap != null)
				heap.work(data);
			if (archive != null && XmlProperty.XVARM) 
				archive.work(data);	
			if (dataConn != null  && XmlProperty.XVARM) 
				dataConn.work(data);
			if (dbConn != null  && XmlProperty.XVARM) 
				dbConn.work(data);
			if(gcCopy != null)
				gcCopy.work(data);
			if(memPool != null)
				memPool.work(data);
			AgentClient.client.send(data);
		} catch (Exception e) {
			CommonLogger.getLogger().error(getClass(), "can't send resource data", e);
		}
		
	}

	@Override
	public void preWork() throws Exception {
		
		
	}
}
