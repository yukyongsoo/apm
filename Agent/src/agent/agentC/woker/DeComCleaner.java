package agent.agentC.woker;

import java.util.Map;

import agent.agentC.deCom.Decom;
import yuk.Thread.TimerWork;
import yuk.dic.SystemDic;

public class DeComCleaner extends TimerWork{
	
	@Override
	public void work() throws Exception {
		long current = System.currentTimeMillis();
		Map<String, Decom> codeCache = Decom.getCodeCache();
		for(String key : codeCache.keySet()){
			Decom de = codeCache.get(key);
			if(current - de.time > SystemDic.PCODE_Hour){
				codeCache.remove(de);
			}
		}	
	}

	@Override
	public void preWork() throws Exception {
		
	}

}
