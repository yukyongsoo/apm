package monServer.worker;

import java.util.Collection;
import java.util.List;

import monServer.aggre.AggreInMem;
import monServer.config.XmlProperty;
import monServer.dbAction.DbActionBase;
import monServer.net.NetMap;
import yuk.Thread.TimerWork;
import yuk.dic.ModelDic;
import yuk.dic.SystemDic;
import yuk.model.MonitorData;
import yuk.model.multi.ResourceContainer;
import yuk.model.single.ResourceData;
import yuk.network.SendUtil;
import yuk.network.session.SessionWaraper;
import yuk.util.NormalUtil;

public class ResWorker extends TimerWork{
	public ResWorker() throws Exception {
		super();
	}

	@Override
	public void work() throws Exception {
		Collection<ResourceContainer> res = AggreInMem.getRes();
		long now = System.currentTimeMillis();
		for(ResourceContainer con : res){
			for(ResourceData dbReso : con.list){	
				dbReso.time = now;
				//DbActionBase.insertRes("resourcesec", dbReso);			
				if (XmlProperty.AGGRERES) {
					insOrupdRes("resourcemin",  ModelDic.Min, SystemDic.PCODE_MIN, now, dbReso);
					insOrupdRes("resourcehour", ModelDic.Hour,SystemDic.PCODE_Hour, now, dbReso);
					insOrupdRes("resourceday", ModelDic.Day,SystemDic.PCODE_Day, now, dbReso);
					insOrupdRes("resourcemonth",ModelDic.Month, SystemDic.PCODE_MONTH, now, dbReso);
				}
			}
		}
		
		List<SessionWaraper> target = NetMap.accepter.getManager().getTarget();
		MonitorData<ResourceContainer> data = new MonitorData<ResourceContainer>(ModelDic.CONSOLE, ModelDic.TYPE_MULTIRESO, null);
		data.dataList.addAll(res);
		if(data.dataList.size() > 0){
			for(SessionWaraper session : target)
				SendUtil.sendData(session, data);
		}
	}
	
	private void insOrupdRes(String dbName, String timeType,long rollup, long now, ResourceData data) throws Exception {
		long time = NormalUtil.SysmileToDateLong(now, timeType);
		ResourceData last = DbActionBase.getLastRes(dbName, data, rollup);
		if(last != null && time - last.time < rollup){
			DbActionBase.updateRes(dbName, data, last);
		}
		else{
			data.time = time;
			DbActionBase.insertRes(dbName, data);
		}
	}

	@Override
	public void preWork() throws Exception {
		
		
	}
}
