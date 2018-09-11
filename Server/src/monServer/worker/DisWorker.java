package monServer.worker;

import java.util.List;
import java.util.Map;

import monServer.aggre.AggreInVolatile;
import monServer.net.NetMap;
import yuk.Thread.TimerWork;
import yuk.dic.ModelDic;
import yuk.model.MonitorData;
import yuk.model.multi.DistributionContainer;
import yuk.network.SendUtil;
import yuk.network.session.SessionWaraper;

public class DisWorker extends TimerWork{
	public DisWorker() {
		super();
		
	}

	@Override
	public void work() throws Exception {
		Map<String, DistributionContainer> disMap = AggreInVolatile.getDis();
		List<SessionWaraper> target = NetMap.accepter.getManager().getTarget();
		MonitorData<DistributionContainer> data = new MonitorData<DistributionContainer>(ModelDic.CONSOLE, ModelDic.TYPE_MULTIDIS, null);
		for(DistributionContainer dis  : disMap.values()){
			dis.cal();
		}
		data.dataList.addAll(disMap.values());		
		if(data.dataList.size() > 0){
			for(SessionWaraper session : target)
				SendUtil.sendData(session, data);
		}
		AggreInVolatile.clearDis();
	}

	@Override
	public void preWork() throws Exception {
		
	}
}
