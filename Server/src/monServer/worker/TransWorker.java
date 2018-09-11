package monServer.worker;

import java.util.Collection;
import java.util.List;

import monServer.aggre.AggreInMem;
import monServer.dbAction.DbActionBase;
import monServer.net.NetMap;
import yuk.Thread.TimerWork;
import yuk.dic.ModelDic;
import yuk.dic.SystemDic;
import yuk.model.MonitorData;
import yuk.model.multi.TransContainer;
import yuk.model.single.TransactionData;
import yuk.network.SendUtil;
import yuk.network.session.SessionWaraper;
import yuk.util.NormalUtil;

public class TransWorker extends TimerWork{
	public TransWorker() throws Exception {
		super();
	}

	@Override
	public void work() throws Exception {
		Collection<TransContainer> trans = AggreInMem.getTrans();
		long now = System.currentTimeMillis();
		for (TransContainer con : trans) {
			for (TransactionData dbTrans : con.list) {		
				dbTrans.time = now;
				//DbActionBase.insertAction("actionsec", dbReso);
				insOrupdTrans("actionmin", ModelDic.Min,SystemDic.PCODE_MIN, now, dbTrans);
				insOrupdTrans("actionhour", ModelDic.Hour,SystemDic.PCODE_Hour, now, dbTrans);
				insOrupdTrans("actionday", ModelDic.Day,SystemDic.PCODE_Day, now, dbTrans);
				insOrupdTrans("actionmonth", ModelDic.Month,SystemDic.PCODE_MONTH, now, dbTrans);
			}
		}
		
		List<SessionWaraper> target = NetMap.accepter.getManager().getTarget();
		MonitorData<TransContainer> data = new MonitorData<TransContainer>(ModelDic.CONSOLE, ModelDic.TYPE_MULTITRANS, null);
		data.dataList.addAll(trans);
		if(data.dataList.size() > 0){
			for(SessionWaraper session : target)
				SendUtil.sendData(session, data);
		}		
	}
	
	private void insOrupdTrans(String dbName, String timeType,long rollup, long now, TransactionData data) throws Exception {
		long time = NormalUtil.SysmileToDateLong(now, timeType);
		TransactionData last = DbActionBase.getLastTrans(dbName, data, rollup);
		if(last != null && time - last.time < rollup){
			DbActionBase.updateAction(dbName, data, last);
		}
		else{
			data.time = time;
			DbActionBase.insertAction(dbName, data);
		}
	}

	@Override
	public void preWork() throws Exception {
		
		
	}
	
	
	
	
}
