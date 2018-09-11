package agent.agentC.woker;

import java.util.Map;

import yuk.Thread.TimerWork;
import yuk.dic.SystemDic;
import yuk.model.etc.RowTransactionData;
import agent.agentC.tracer.MainTracer;

public class TransTimeChecker extends TimerWork{
	public TransTimeChecker() {
		super();
	}

	@Override
	public void work() throws Exception {
		long current = System.currentTimeMillis();
		Map<String, RowTransactionData> map = MainTracer.getTransMap();
		for(String key : map.keySet()){
			RowTransactionData data = map.get(key);
			if(current - data.time > SystemDic.PCODE_MIN * 10){
				map.remove(key);	
			}
		}		
	}

	@Override
	public void preWork() throws Exception {
		
	}

}
