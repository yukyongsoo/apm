package monServer.worker;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import monServer.aggre.AggreInVolatile;
import monServer.net.NetMap;
import yuk.Thread.TimerWork;
import yuk.dic.ModelDic;
import yuk.model.MonitorData;
import yuk.model.multi.ThreadContainer;
import yuk.model.single.ThreadHotSpotData;
import yuk.network.SendUtil;
import yuk.network.session.SessionWaraper;

public class HotWorker extends TimerWork{

	public HotWorker() {
		super();
	
	}

	@Override
	public void work() throws Exception {
		Map<String, Collection<ThreadHotSpotData>> hotSpot = AggreInVolatile.getHotSpot();
		List<SessionWaraper> target = NetMap.accepter.getManager().getTarget();
		MonitorData<ThreadContainer> data = new MonitorData<ThreadContainer>(ModelDic.CONSOLE, ModelDic.TYPE_MULTIHOTSPOT, null);
		for(String key : hotSpot.keySet()){
			ThreadContainer con = new ThreadContainer();
			con.name = key;
			con.list.addAll(hotSpot.get(key));
			data.dataList.add(con);
		}
		AggreInVolatile.clearHotSpot();
		if(data.dataList.size() > 0){
			for(SessionWaraper session : target)
				SendUtil.sendData(session, data);
		}
	}

	@Override
	public void preWork() throws Exception {
		
	}
}
