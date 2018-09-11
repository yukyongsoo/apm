package monServer.worker;

import java.util.Collection;
import java.util.List;

import monServer.aggre.AggreInMem;
import monServer.dbAction.DbActionBase;
import monServer.net.NetMap;
import yuk.Thread.TimerWork;
import yuk.dic.ModelDic;
import yuk.model.MonitorData;
import yuk.model.single.MacData;
import yuk.network.SendUtil;
import yuk.network.session.SessionWaraper;

public class InfoWorker extends TimerWork{

	public InfoWorker() {
		super();
		
	}

	@Override
	public void work() throws Exception {
		Collection<MacData> mac = AggreInMem.getMac();
		for(MacData dbMac : mac){
			dbMac.time = System.currentTimeMillis();
			DbActionBase.insertInfo(dbMac);
		}
		
		List<SessionWaraper> target = NetMap.accepter.getManager().getTarget();
		MonitorData<MacData> data = new MonitorData<MacData>(ModelDic.CONSOLE, ModelDic.TYPE_MAC, null);
		data.dataList.addAll(mac);
		if(data.dataList.size() > 0){
			for(SessionWaraper session : target)
				SendUtil.sendData(session, data);
		}				
	}

	@Override
	public void preWork() throws Exception {
		
	}
}
