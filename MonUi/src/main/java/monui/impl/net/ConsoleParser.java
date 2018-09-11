package monui.impl.net;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import monui.impl.caster.Caster;
import yuk.model.CastHeader;
import yuk.model.etc.EventData;
import yuk.model.etc.InitData;
import yuk.model.multi.DBSpotContainer;
import yuk.model.multi.DistributionContainer;
import yuk.model.multi.ExcptContainer;
import yuk.model.multi.LogContainer;
import yuk.model.multi.ResourceContainer;
import yuk.model.multi.ThreadContainer;
import yuk.model.multi.TransContainer;
import yuk.model.single.HealthData;
import yuk.model.single.MacData;
import yuk.model.single.StackData;
import yuk.network.session.SessionWaraper;
import yuk.parser.inter.CommandInterfaceAdapter;

public class ConsoleParser extends CommandInterfaceAdapter{
	private Caster caster;
	
	public ConsoleParser(Caster caster) {
		this.caster = caster;
	}
	
	@Override
	public void event(CastHeader header, Collection<EventData> dataSet, SessionWaraper session) throws Exception {
		caster.castEvent(header,dataSet);
	}

	@Override
	public void init(CastHeader header, Collection<InitData> dataSet, SessionWaraper session) throws Exception {
		List<InitData> list = new ArrayList<InitData>();
		list.addAll(dataSet);
		caster.setInitList(list);
	}

	@Override
	public void etc(CastHeader header, Object object, SessionWaraper session) throws Exception {
		System.out.println("weird action!!!!!!!!!");
	}

	@Override
	public void health(CastHeader header, Collection<HealthData> dataSet, SessionWaraper session) throws Exception {
		caster.castHealth(header,dataSet);
	}

	@Override
	public void mac(CastHeader header, Collection<MacData> dataSet, SessionWaraper session) throws Exception {
		caster.castMac(header,dataSet);
	}

	@Override
	public void statck(CastHeader header, Collection<StackData> dataSet, SessionWaraper session) throws Exception {
		caster.castStack(header,dataSet);
	}

	@Override
	public void multiDBHotSpot(CastHeader header, Collection<DBSpotContainer> dataSet, SessionWaraper session) throws Exception {
		caster.castDbHotSpot(header,dataSet);
	}

	@Override
	public void multiExcptSpot(CastHeader header, Collection<ExcptContainer> mExcptSet, SessionWaraper session) throws Exception {
		caster.castExcpt(header,mExcptSet);
		
	}

	@Override
	public void multiLog(CastHeader header, Collection<LogContainer> mLogSet, SessionWaraper session)
			throws Exception {
		caster.castLog(header,mLogSet);
		
	}

	@Override
	public void multiResource(CastHeader header, Collection<ResourceContainer> mResoSet, SessionWaraper session)
			throws Exception {
		caster.castReso(header,mResoSet);
		
	}

	@Override
	public void multiHotSpot(CastHeader header, Collection<ThreadContainer> mThreadSet, SessionWaraper session)
			throws Exception {
		caster.castHotSpot(header,mThreadSet);
		
	}

	@Override
	public void multiTrans(CastHeader header, Collection<TransContainer> mTransSet, SessionWaraper session)
			throws Exception {
		caster.castTrans(header,mTransSet);
		
	}
	
	@Override
	public void multiDis(CastHeader castHeader,Collection<DistributionContainer> mDisSet, SessionWaraper session) {
		caster.castDis(castHeader, mDisSet);
	}
}
