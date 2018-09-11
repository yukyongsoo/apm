package monCollector.worker;

import monCollector.net.NetMap;
import monCollector.worker.resource.DiskIO;
import monCollector.worker.resource.NetworkIo;
import yuk.Thread.TimerWork;
import yuk.dic.ModelDic;
import yuk.model.MonitorData;
import yuk.model.single.MacData;

public class MachineInfoThread  extends TimerWork {
	DiskIO diskIO = new DiskIO();
	NetworkIo netIO = new NetworkIo();

	public MachineInfoThread() {
		super();
	}

	@Override
	public void work() throws Exception {
		MonitorData<MacData> mData = new MonitorData<MacData>(ModelDic.SERVER, ModelDic.TYPE_MAC,null);
		diskIO.work(mData);
		netIO.work(mData);
		NetMap.send(mData);
	}

	@Override
	public void preWork() throws Exception {
		
		
	}

}
