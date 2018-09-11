package monCollector.worker;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import monCollector.net.NetMap;
import yuk.Thread.TimerWork;
import yuk.dic.ModelDic;
import yuk.model.MonitorData;
import yuk.model.multi.DistributionContainer;
import yuk.model.single.DistributionData;
import yuk.util.CommonLogger;

public class AggreDisSender  extends TimerWork{
	private static Map<String, DistributionContainer> disMap = new ConcurrentHashMap<String, DistributionContainer>();

	public AggreDisSender() {
		super();	
	}

	@Override
	public void work() throws Exception {
		try {
			MonitorData<DistributionContainer> object = new MonitorData<DistributionContainer>(ModelDic.SERVER, ModelDic.TYPE_MULTIDIS,null);	
			object.dataList.addAll(disMap.values());
			for(DistributionContainer data : disMap.values())
				data.cal();
			if(object.dataList.size() > 0)
				NetMap.send(object);
			disMap.clear();
		} catch (Exception e) {
			CommonLogger.getLogger().error(getClass(), "can't aggregate transaction", e);	
		}
		
	}

	@Override
	public void preWork() throws Exception {
		
	}
	

	public void aggre(Collection<DistributionContainer> mDisSet) throws Exception {
		for(DistributionContainer data : mDisSet){
			DistributionContainer con = disMap.get(data.command);
			if(con == null){
				disMap.put(data.command, data);
			}
			else{
				for(DistributionData sub : data.list.values()){
					con.addData(sub);					
				}
			}
		}
		
	}

}
