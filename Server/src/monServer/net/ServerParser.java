package monServer.net;

import java.util.Collection;

import monServer.ServerStarter;
import monServer.ServerStoper;
import monServer.aggre.AggreInMem;
import monServer.aggre.AggreInVolatile;
import monServer.event.StateChecker;
import monServer.worker.internal.HealthChecker;
import yuk.dic.ModelDic;
import yuk.model.CastHeader;
import yuk.model.etc.EventData;
import yuk.model.etc.InitData;
import yuk.model.etc.SessionData;
import yuk.model.multi.DistributionContainer;
import yuk.model.single.DBHotSpotData;
import yuk.model.single.ExceptionData;
import yuk.model.single.HealthData;
import yuk.model.single.LogData;
import yuk.model.single.MacData;
import yuk.model.single.ResourceData;
import yuk.model.single.StackData;
import yuk.model.single.ThreadHotSpotData;
import yuk.model.single.TransactionData;
import yuk.network.session.SessionWaraper;
import yuk.parser.inter.CommandInterfaceAdapter;

public class ServerParser extends CommandInterfaceAdapter{
	//database input
	@Override
	public void trans(CastHeader castHeader,Collection<TransactionData> dataSet, SessionWaraper session) throws Exception {
		AggreInMem.updateTrans(dataSet);
		StateChecker.CheckTrans(dataSet);
	}
	
	@Override
	public void resource(CastHeader castHeader,Collection<ResourceData> dataSet, SessionWaraper session) throws Exception {
		AggreInMem.updateRes(dataSet);
		StateChecker.CheckRes(dataSet);
	}

	@Override
	public void log(CastHeader castHeader,Collection<LogData> dataSet, SessionWaraper session) throws Exception {
		AggreInMem.updateLog(dataSet);
	}
	
	@Override
	public void event(CastHeader castHeader,Collection<EventData> dataSet, SessionWaraper session) throws Exception {
		AggreInMem.updateEvent(dataSet);
	}
	
	@Override
	public void mac(CastHeader castHeader,Collection<MacData> dataSet, SessionWaraper session) throws Exception{
		AggreInMem.updateMac(dataSet);
		StateChecker.CheckInfo(dataSet);
	}
	
	@Override
	public void statck(CastHeader castHeader,Collection<StackData> dataSet, SessionWaraper session) throws Exception {
		AggreInMem.upateStack(dataSet);
	}
	
	//non database input
	@Override
	public void dbHotSpot(CastHeader castHeader,Collection<DBHotSpotData> dataSet, SessionWaraper session) throws Exception {
		AggreInVolatile.saveDbHotSpot(dataSet);
	}
	
	@Override
	public void hotspot(CastHeader castHeader,Collection<ThreadHotSpotData> dataSet, SessionWaraper session) throws Exception{
		AggreInVolatile.saveHotSpot(dataSet);
	}
	
	@Override
	public void excpt(CastHeader castHeader,Collection<ExceptionData> dataSet, SessionWaraper session) throws Exception{
		AggreInVolatile.saveExcpt(dataSet);
		StateChecker.CheckExcpt(dataSet);
	}
		
	
	@Override
	public void multiDis(CastHeader castHeader,Collection<DistributionContainer> mDisSet, SessionWaraper session) {
		AggreInVolatile.saveDis(mDisSet);
	}
	//etc 
	
	@Override
	public void health(CastHeader castHeader,Collection<HealthData> dataSet, SessionWaraper session) throws Exception {
		HealthChecker hc = ServerStarter.getManager().getWork("HC", HealthChecker.class);
		for(HealthData data : dataSet)
			hc.healthMap.put(data.name, data);
	}
	
	@Override
	public void init(CastHeader header, Collection<InitData> dataSet, SessionWaraper session) throws Exception {
		for(InitData init : dataSet){
			if(init.type.equals(ModelDic.CONSOLE))
				NetMap.accepter.getManager().putCastTarget(init.name, session);
			else
				NetMap.accepter.getManager().addChild(init.name,init);
		}
	}
	
	@Override
	public void session(CastHeader castHeader,Collection<SessionData> dataSet, SessionWaraper session) throws Exception {
		for(SessionData data : dataSet)
			NetMap.accepter.getManager().putSession(data.name, session);
	}
	
	@Override
	public void etc(CastHeader castHeader,Object object, SessionWaraper session) throws Exception {
		ServerStoper.stop();
	}



}
