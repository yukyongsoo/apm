package monServer.net.stoper;

import java.util.Collection;

import yuk.model.CastHeader;
import yuk.model.etc.EventData;
import yuk.model.etc.InitData;
import yuk.model.etc.ServerDBData;
import yuk.model.multi.LogContainer;
import yuk.model.multi.ResourceContainer;
import yuk.model.multi.TransContainer;
import yuk.model.single.HealthData;
import yuk.model.single.MacData;
import yuk.model.single.StackData;
import yuk.network.session.SessionWaraper;
import yuk.parser.inter.CommandInterfaceAdapter;

public class MonConsoleParser extends CommandInterfaceAdapter{
	@Override
	public void init(CastHeader header, Collection<InitData> dataSet, SessionWaraper session) throws Exception {
		
	}

	@Override
	public void event(CastHeader header, Collection<EventData> dataSet,SessionWaraper session) throws Exception {
		
	}

	@Override
	public void db(CastHeader header, Collection<ServerDBData> dataSet,SessionWaraper session) throws Exception {
		
	}

	@Override
	public void health(CastHeader header, Collection<HealthData> dataSet,SessionWaraper session) throws Exception {
		
	}

	@Override
	public void mac(CastHeader header, Collection<MacData> dataSet,SessionWaraper session) throws Exception {
		
	}

	@Override
	public void statck(CastHeader header, Collection<StackData> dataSet,SessionWaraper session) throws Exception {
	
	}

	@Override
	public void multiLog(CastHeader castHeader,Collection<LogContainer> mLogSet, SessionWaraper session) throws Exception {
		
	}

	@Override
	public void multiResource(CastHeader castHeader,Collection<ResourceContainer> mResoSet, SessionWaraper session) throws Exception {
		
	}

	@Override
	public void multiTrans(CastHeader castHeader,Collection<TransContainer> mTransSet, SessionWaraper session) throws Exception {
		
	}

}
