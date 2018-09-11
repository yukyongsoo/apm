package monServer.worker;

import java.util.List;

import monServer.net.NetMap;
import monServer.worker.internal.HealthChecker;
import yuk.Thread.TimerWork;
import yuk.dic.ModelDic;
import yuk.model.MonitorData;
import yuk.model.single.HealthData;
import yuk.network.SendUtil;
import yuk.network.session.SessionWaraper;

public class HealthWorker extends TimerWork {

	public HealthWorker() {
		super();
	}

	@Override
	public void work() throws Exception {
		MonitorData<HealthData> data = new MonitorData<HealthData>(ModelDic.CONSOLE,ModelDic.TYPE_HEALTH, null);
		List<SessionWaraper> target = NetMap.accepter.getManager().getTarget();
		data.dataList.addAll(HealthChecker.healthMap.values());
		if(data.dataList.size() > 0){
			for(SessionWaraper session : target)
				SendUtil.sendData(session, data);
		}
	}

	@Override
	public void preWork() throws Exception {
	
	}
}
