package yuk.parser.inter;

import java.util.Collection;

import yuk.model.CastHeader;
import yuk.model.etc.EventData;
import yuk.model.etc.InitData;
import yuk.model.etc.RowTransactionData;
import yuk.model.etc.ServerDBData;
import yuk.model.etc.SessionData;
import yuk.model.multi.DBSpotContainer;
import yuk.model.multi.DistributionContainer;
import yuk.model.multi.ExcptContainer;
import yuk.model.multi.LogContainer;
import yuk.model.multi.ResourceContainer;
import yuk.model.multi.ThreadContainer;
import yuk.model.multi.TransContainer;
import yuk.model.single.DBHotSpotData;
import yuk.model.single.DistributionData;
import yuk.model.single.ExceptionData;
import yuk.model.single.HealthData;
import yuk.model.single.LogData;
import yuk.model.single.MacData;
import yuk.model.single.ResourceData;
import yuk.model.single.StackData;
import yuk.model.single.ThreadHotSpotData;
import yuk.model.single.TransactionData;
import yuk.network.session.SessionWaraper;

public class CommandInterfaceAdapter implements CommandInterface{

	@Override
	public void event(CastHeader header, Collection<EventData> dataSet, SessionWaraper session) throws Exception {
		
		
	}

	@Override
	public void init(CastHeader header, Collection<InitData> dataSet, SessionWaraper session) throws Exception {
		
		
	}

	@Override
	public void rowTrans(CastHeader header, Collection<RowTransactionData> dataSet, SessionWaraper session)
			throws Exception {
		
		
	}

	@Override
	public void db(CastHeader header, Collection<ServerDBData> dataSet, SessionWaraper session) throws Exception {
		
		
	}

	@Override
	public void session(CastHeader header, Collection<SessionData> dataSet, SessionWaraper session) throws Exception {
		
		
	}

	@Override
	public void etc(CastHeader header, Object object, SessionWaraper session) throws Exception {
		
		
	}

	@Override
	public void dbHotSpot(CastHeader header, Collection<DBHotSpotData> dataSet, SessionWaraper session)
			throws Exception {
		
		
	}

	@Override
	public void excpt(CastHeader header, Collection<ExceptionData> dataSet, SessionWaraper session) throws Exception {
		
		
	}

	@Override
	public void health(CastHeader header, Collection<HealthData> dataSet, SessionWaraper session) throws Exception {
		
		
	}

	@Override
	public void log(CastHeader header, Collection<LogData> dataSet, SessionWaraper session) throws Exception {
		
		
	}

	@Override
	public void mac(CastHeader header, Collection<MacData> dataSet, SessionWaraper session) throws Exception {
		
		
	}

	@Override
	public void resource(CastHeader header, Collection<ResourceData> dataSet, SessionWaraper session) throws Exception {
		
		
	}

	@Override
	public void statck(CastHeader header, Collection<StackData> dataSet, SessionWaraper session) throws Exception {
		
		
	}

	@Override
	public void trans(CastHeader header, Collection<TransactionData> dataSet, SessionWaraper session) throws Exception {
		
		
	}

	@Override
	public void hotspot(CastHeader header, Collection<ThreadHotSpotData> dataSet, SessionWaraper session)
			throws Exception {
		
		
	}

	@Override
	public void multiDBHotSpot(CastHeader castHeader, Collection<DBSpotContainer> dataSet, SessionWaraper session)
			throws Exception {
		
		
	}

	@Override
	public void multiExcptSpot(CastHeader castHeader, Collection<ExcptContainer> mExcptSet, SessionWaraper session) throws Exception {
		
		
	}

	@Override
	public void multiLog(CastHeader castHeader, Collection<LogContainer> mLogSet, SessionWaraper session)
			throws Exception {
		
		
	}

	@Override
	public void multiResource(CastHeader castHeader, Collection<ResourceContainer> mResoSet, SessionWaraper session)
			throws Exception {
		
		
	}

	@Override
	public void multiHotSpot(CastHeader castHeader, Collection<ThreadContainer> mThreadSet, SessionWaraper session)
			throws Exception {
		
		
	}

	@Override
	public void multiTrans(CastHeader castHeader, Collection<TransContainer> mTransSet, SessionWaraper session)
			throws Exception {
		
		
	}

	@Override
	public void multiDis(CastHeader castHeader,
			Collection<DistributionContainer> mDisSet, SessionWaraper session) throws Exception {
		
	}

	@Override
	public void dis(CastHeader castHeader, Collection<DistributionData> disSet,
			SessionWaraper session) {
		
	}

}
