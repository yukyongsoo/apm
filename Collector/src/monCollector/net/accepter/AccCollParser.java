package monCollector.net.accepter;

import java.util.Collection;

import monCollector.CollectorStarter;
import monCollector.config.XmlProperty;
import monCollector.net.NetMap;
import monCollector.worker.AggreActionThread;
import monCollector.worker.AggreDisSender;
import monCollector.worker.AggreResThread;
import monCollector.worker.HealthSender;
import yuk.model.CastHeader;
import yuk.model.etc.InitData;
import yuk.model.multi.DistributionContainer;
import yuk.model.single.HealthData;
import yuk.model.single.ResourceData;
import yuk.model.single.TransactionData;
import yuk.network.session.SessionWaraper;
import yuk.parser.inter.CommandInterfaceAdapter;

public class AccCollParser extends CommandInterfaceAdapter{
	@Override
	public void trans(CastHeader header,Collection<TransactionData> dataSet, SessionWaraper session) throws Exception {
		AggreActionThread work= CollectorStarter.getManager().getWork("AAT", AggreActionThread.class);
		work.aggre(dataSet);
	}
	@Override
	public void resource(CastHeader header,Collection<ResourceData> dataSet, SessionWaraper session) throws Exception {
		AggreResThread work= CollectorStarter.getManager().getWork("ART", AggreResThread.class);		
		work.aggre(dataSet);
	}

	@Override
	public void health(CastHeader header,Collection<HealthData> dataSet, SessionWaraper session) throws Exception {
		HealthSender sender= CollectorStarter.getManager().getWork("Health", HealthSender.class);
		for(HealthData data : dataSet)
			sender.healthMap.put(data.name, data);
	}

	@Override
	public void init(CastHeader header, Collection<InitData> dataSet, SessionWaraper session) throws Exception {
		for(InitData data : dataSet){
			data.parent = XmlProperty.MYNAME;
			NetMap.accepter.getManager().addChild(data.name, data);
		}		
		NetMap.sendInit();
	}
	
	@Override
	public void multiDis(CastHeader castHeader,Collection<DistributionContainer> mDisSet, SessionWaraper session)  throws Exception{
		AggreDisSender work= CollectorStarter.getManager().getWork("Dis", AggreDisSender.class);
		work.aggre(mDisSet);
	}
}
