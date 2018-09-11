package monServer.worker;

import java.util.Collection;
import java.util.List;

import monServer.aggre.AggreInMem;
import monServer.log.LogMaker;
import monServer.net.NetMap;
import yuk.Thread.TimerWork;
import yuk.dic.ModelDic;
import yuk.model.MonitorData;
import yuk.model.multi.LogContainer;
import yuk.network.SendUtil;
import yuk.network.session.SessionWaraper;

public class LogWorker extends TimerWork{

	public LogWorker() {
		super();
		
	}

	@Override
	public void work() throws Exception {
		Collection<LogContainer> log = AggreInMem.getLog();
		List<SessionWaraper> target = NetMap.accepter.getManager().getTarget();
		MonitorData<LogContainer> data = new MonitorData<LogContainer>(ModelDic.CONSOLE, ModelDic.TYPE_MULTILOG, null);
		data.dataList.addAll(log);
		if(data.dataList.size() > 0){
			for(SessionWaraper session : target)
				SendUtil.sendData(session, data);
		}
		LogMaker maker = new LogMaker();
		maker.makeLog(log);
	}

	@Override
	public void preWork() throws Exception {
		
	}

}
