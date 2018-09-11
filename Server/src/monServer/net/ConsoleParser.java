package monServer.net;

import java.util.Collection;

import monServer.dbAction.DbActionSender;
import yuk.model.CastHeader;
import yuk.model.etc.EventData;
import yuk.model.etc.InitData;
import yuk.model.etc.ServerDBData;
import yuk.model.single.HealthData;
import yuk.model.single.LogData;
import yuk.model.single.MacData;
import yuk.model.single.ResourceData;
import yuk.model.single.StackData;
import yuk.model.single.TransactionData;
import yuk.network.session.SessionWaraper;
import yuk.parser.inter.CommandInterfaceAdapter;

public class ConsoleParser extends CommandInterfaceAdapter{
	@Override
	public void health(CastHeader header, Collection<HealthData> dataSet, SessionWaraper session) throws Exception {
		DbActionSender.sendHealth(header,session);
	}
	
	@Override
	public void event(CastHeader header, Collection<EventData> dataSet, SessionWaraper session) throws Exception {
		DbActionSender.sendEvent(header,dataSet,session);
	}
	
	@Override
	public void log(CastHeader header, Collection<LogData> dataSet, SessionWaraper session) throws Exception {
		DbActionSender.sendLog(header,dataSet,session);		
	}
	@Override
	public void db(CastHeader header, Collection<ServerDBData> dataSet, SessionWaraper session) throws Exception {
		DbActionSender.sendDB(session,dataSet,header);			
	}
	
	@Override
	public void statck(CastHeader header, Collection<StackData> dataSet, SessionWaraper session) throws Exception {
		DbActionSender.sendStack(session,dataSet,header);	
	}
	
	@Override
	public void mac(CastHeader header, Collection<MacData> dataSet, SessionWaraper session) throws Exception {
		DbActionSender.sendInfo(session,dataSet,header);	
	}
	
	@Override
	public void resource(CastHeader header, Collection<ResourceData> dataSet, SessionWaraper session) throws Exception {
		DbActionSender.sendRes(session,dataSet,header);	
		
	}
	
	@Override
	public void trans(CastHeader header, Collection<TransactionData> dataSet, SessionWaraper session) throws Exception {
		DbActionSender.sendTrans(session,dataSet,header);	
	}
	
	@Override
	public void init(CastHeader header, Collection<InitData> dataSet, SessionWaraper session) throws Exception {
		DbActionSender.sendInit(session,dataSet,header);
	}
}
