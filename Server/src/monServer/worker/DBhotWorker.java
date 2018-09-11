package monServer.worker;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import monServer.aggre.AggreInVolatile;
import monServer.net.NetMap;
import yuk.Thread.TimerWork;
import yuk.dic.ModelDic;
import yuk.model.MonitorData;
import yuk.model.multi.DBSpotContainer;
import yuk.model.single.DBHotSpotData;
import yuk.network.SendUtil;
import yuk.network.session.SessionWaraper;

public class DBhotWorker extends TimerWork {
	public DBhotWorker() {
		super();

	}

	@Override
	public void work() throws Exception {
		Map<String, Collection<DBHotSpotData>> dbHotSpot = AggreInVolatile.getDbHotSpot();
		List<SessionWaraper> target = NetMap.accepter.getManager().getTarget();
		MonitorData<DBSpotContainer> data = new MonitorData<DBSpotContainer>(ModelDic.CONSOLE, ModelDic.TYPE_MULTIDBHOT,null);
		for(String key : dbHotSpot.keySet()) {
			DBSpotContainer con = new DBSpotContainer();
			con.name = key;
			con.list.addAll(dbHotSpot.get(key));
			data.dataList.add(con);
		}
		AggreInVolatile.clearDbHotSpot();
		if (data.dataList.size() > 0) {
			for (SessionWaraper session : target)
				SendUtil.sendData(session, data);
		}
	}

	@Override
	public void preWork() throws Exception {
	
	}

}
