package monCollector.net.accepter;

import java.util.Collection;

import monCollector.net.NetMap;
import yuk.dic.ModelDic;
import yuk.model.CastHeader;
import yuk.model.MonitorData;
import yuk.model.etc.EventData;
import yuk.model.etc.SessionData;
import yuk.model.single.DBHotSpotData;
import yuk.model.single.ExceptionData;
import yuk.model.single.LogData;
import yuk.model.single.StackData;
import yuk.model.single.ThreadHotSpotData;
import yuk.network.session.SessionWaraper;
import yuk.parser.inter.CommandInterfaceAdapter;

public class AccServerParser extends CommandInterfaceAdapter{
	@Override
	public void log(CastHeader header,Collection<LogData> dataSet, SessionWaraper session) throws Exception {
		MonitorData<LogData> object = new MonitorData<LogData>(ModelDic.SERVER, ModelDic.TYPE_LOG,null);
		object.dataList.addAll(dataSet);
		NetMap.send(object);
	}

	@Override
	public void dbHotSpot(CastHeader header,Collection<DBHotSpotData> dataSet, SessionWaraper session) throws Exception {
		MonitorData<DBHotSpotData> object = new MonitorData<DBHotSpotData>(ModelDic.SERVER, ModelDic.TYPE_DBHOTSPOT,null);
		object.dataList.addAll(dataSet);
		NetMap.send(object);
	}

	@Override
	public void event(CastHeader header,Collection<EventData> dataSet, SessionWaraper session) throws Exception {
		MonitorData<EventData> object = new MonitorData<EventData>(ModelDic.SERVER, ModelDic.TYPE_EVENT,null);		
		object.dataList.addAll(dataSet);
		NetMap.send(object);	
	}

	@Override
	public void session(CastHeader header,Collection<SessionData> dataSet, SessionWaraper session) throws Exception {
		for(SessionData data : dataSet)
			NetMap.accepter.getManager().putSession(data.name, session);
	}
	
	@Override
	public void hotspot(CastHeader header,Collection<ThreadHotSpotData> dataSet, SessionWaraper session) throws Exception{
		MonitorData<ThreadHotSpotData> object = new MonitorData<ThreadHotSpotData>(ModelDic.SERVER, ModelDic.TYPE_HOTSPOT,null);		
		object.dataList.addAll(dataSet);
		NetMap.send(object);	
	}

	@Override
	public void excpt(CastHeader header,Collection<ExceptionData> dataSet, SessionWaraper session) throws Exception{
		MonitorData<ExceptionData> object = new MonitorData<ExceptionData>(ModelDic.SERVER, ModelDic.TYPE_EXCPT,null);		
		object.dataList.addAll(dataSet);
		NetMap.send(object);	
	}
	@Override
	public void statck(CastHeader header,Collection<StackData> dataSet, SessionWaraper session) throws Exception {
		MonitorData<StackData> object = new MonitorData<StackData>(ModelDic.SERVER, ModelDic.TYPE_STACK,null);		
		object.dataList.addAll(dataSet);
		NetMap.send(object);	
		
	}
}
