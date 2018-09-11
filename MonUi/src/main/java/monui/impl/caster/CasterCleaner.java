package monui.impl.caster;

import java.util.Map;

import yuk.Thread.TimerWork;
import yuk.dic.SystemDic;

public class CasterCleaner extends TimerWork{
	private Map<Long, CasteeInterface> targetCaster;

	public CasterCleaner(Map<Long, CasteeInterface> targetCaster) {
		this.targetCaster = targetCaster;
	}

	@Override
	public void preWork() throws Exception {
		
	}

	@Override
	public void work() throws Exception {
		long now = System.currentTimeMillis();
		for(long time : targetCaster.keySet()){
			if(now - time > SystemDic.PCODE_Hour)
				targetCaster.remove(time);			
		}		
	}

}
 