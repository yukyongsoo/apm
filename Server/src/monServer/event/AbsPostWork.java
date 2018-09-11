package monServer.event;

import yuk.model.etc.EventData;
import yuk.util.CommonLogger;

public abstract class AbsPostWork {
	public abstract void work(EventData data);
	public abstract void preWork();
	
	public AbsPostWork() {
		try {
			preWork();
		} catch (Exception e) {
			CommonLogger.getLogger().error(getClass(), "init Post Working on Fail", e);
		}
	}	
	protected void realWork(EventData data){
		try{
			work(data);
		}catch(Exception e){
			CommonLogger.getLogger().error(getClass(), "Post Working on Fail", e);
		}
	}
}
