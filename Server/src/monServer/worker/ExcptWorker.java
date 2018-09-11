package monServer.worker;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import monServer.aggre.AggreInVolatile;
import monServer.net.NetMap;
import yuk.Thread.TimerWork;
import yuk.dic.ModelDic;
import yuk.model.MonitorData;
import yuk.model.multi.ExcptContainer;
import yuk.model.single.ExceptionData;
import yuk.network.SendUtil;
import yuk.network.session.SessionWaraper;

public class ExcptWorker extends TimerWork{

	public ExcptWorker() {
		super();
		
	}

	@Override
	public void work() throws Exception {
		Map<String, Collection<ExceptionData>> excptSpot = AggreInVolatile.getExcptSpot();
		List<SessionWaraper> target = NetMap.accepter.getManager().getTarget();
		MonitorData<ExcptContainer> data = new MonitorData<ExcptContainer>(ModelDic.CONSOLE, ModelDic.TYPE_MULTIEXCPT, null);
		for(String key : excptSpot.keySet()){
			ExcptContainer con = new ExcptContainer();
			con.name = key;
			con.list.addAll(excptSpot.get(key));
			data.dataList.add(con);
		}
		AggreInVolatile.clearExcpt();
		if(data.dataList.size() > 0){
			for(SessionWaraper session : target)
				SendUtil.sendData(session, data);
		}
	}

	@Override
	public void preWork() throws Exception {
		
	}

}
