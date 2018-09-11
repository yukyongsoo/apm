package monServer.worker;

import java.util.Collection;
import java.util.List;

import monServer.aggre.AggreInMem;
import monServer.dbAction.DbActionBase;
import monServer.event.EventHandle;
import monServer.net.NetMap;
import yuk.Thread.TimerWork;
import yuk.dic.ModelDic;
import yuk.model.MonitorData;
import yuk.model.etc.EventData;
import yuk.network.SendUtil;
import yuk.network.session.SessionWaraper;

public class EventWorker extends TimerWork{

	public EventWorker() {
		super();
	}

	@Override
	public void work() throws Exception {
		Collection<EventData> event = AggreInMem.getEvent();
		if(EventHandle.getHandle() != null)
			EventHandle.getHandle().handleData(event);
		List<SessionWaraper> target = NetMap.accepter.getManager().getTarget();
		MonitorData<EventData> data = new MonitorData<EventData>(ModelDic.CONSOLE, ModelDic.TYPE_EVENT, null);
		data.dataList.addAll(event);
		if(data.dataList.size() > 0){
			for(SessionWaraper session : target)
				SendUtil.sendData(session, data);
		}
		//db insert;
		AggreInMem.clearEvent();
		for(EventData dbEvent : data.dataList){
			DbActionBase.insertEvent(dbEvent);
		}	
	}

	@Override
	public void preWork() throws Exception {
			
	}
}
