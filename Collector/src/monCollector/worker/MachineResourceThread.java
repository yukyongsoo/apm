package monCollector.worker;

import monCollector.net.NetMap;
import monCollector.worker.resource.SystemCpu;
import monCollector.worker.resource.SystemMem;
import monCollector.worker.resource.SystemSwap;
import yuk.Thread.TimerWork;
import yuk.dic.ModelDic;
import yuk.model.MonitorData;
import yuk.model.single.ResourceData;
import yuk.util.CommonLogger;

public class MachineResourceThread extends TimerWork {
	SystemMem mem = new SystemMem();
	SystemCpu cpu = new SystemCpu();
	SystemSwap swap = new SystemSwap();

	public MachineResourceThread() {
		super();
	}

	@Override
	public void work() throws Exception {
		try {
			MonitorData<ResourceData> mData = new MonitorData<ResourceData>(ModelDic.SERVER, ModelDic.TYPE_RESOURCE,null);
			mem.work(mData);
			cpu.work(mData);
			swap.work(mData);
			NetMap.send(mData);
		} catch (Exception e) {
			CommonLogger.getLogger().error(getClass(), "can't send resource data", e);			
		}
		
	}

	@Override
	public void preWork() throws Exception {
		
		
	}

}
