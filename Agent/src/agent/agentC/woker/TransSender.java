	package agent.agentC.woker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import yuk.Thread.TimerWork;
import yuk.dic.ModelDic;
import yuk.model.MonitorData;
import yuk.model.etc.RowTransactionData;
import yuk.model.single.TransactionData;
import yuk.util.CommonLogger;
import agent.agentC.config.XmlProperty;
import agent.agentC.net.AgentClient;
import agent.agentC.tracer.MainTracer;

public class TransSender extends TimerWork{
	
	public TransSender() {
		super();
	}
	
	@Override
	public void work() throws Exception {
		try {
			Map<String, RowTransactionData> map = MainTracer.getTransMap();
			List<RowTransactionData> rowList = new ArrayList<RowTransactionData>();
			for(String key : map.keySet()){
				RowTransactionData data = map.get(key);
				if(data.end){
					rowList.add(data);
					map.remove(key);	
				}
			}	
			List<TransactionData> list = makeTrans(rowList);		
			MonitorData<TransactionData> object = new MonitorData<TransactionData>(ModelDic.COLLECTOR, ModelDic.TYPE_TRANS,null);	
			object.dataList = list;
			if(object.dataList.size() > 0)
				AgentClient.client.send(object);
		} catch (Exception e) {
				CommonLogger.getLogger().info(getClass(), "can't send trasaction data.", e);
		}		
	}

	@Override
	public void preWork() throws Exception {
		
	}
	
	private List<TransactionData> makeTrans(List<RowTransactionData> rowlist) throws Exception{
		Map<String, TransactionData> map = new HashMap<String, TransactionData>();		
		for(RowTransactionData data : rowlist){
			if(map.get(data.command) == null){
				TransactionData tr = new TransactionData(XmlProperty.MYNAME, data.command,0,0,0);
				tr.rowModelAdd(data, false);
				map.put(data.command, tr);
			}
			else{
				TransactionData tr = map.get(data.command);
				tr.rowModelAdd(data, false);
			}		
		}
		List<TransactionData> list = new ArrayList<TransactionData>();
		list.addAll(map.values());
		for(TransactionData t : list)
			t.cal();
		return list;
	}
}
