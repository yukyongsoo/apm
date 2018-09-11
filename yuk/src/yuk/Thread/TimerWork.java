package yuk.Thread;

import java.util.TimerTask;

import yuk.util.CommonLogger;

public abstract class TimerWork extends TimerTask{
	
	public abstract void work() throws Exception;
	public abstract void preWork() throws Exception;
	public TimerWork(){
		try {
			preWork();
		} catch (Exception e) {
			CommonLogger.getLogger().error(getClass(), "timer pre work get error" , e);
		}
	}
	
	public void run(){		
		try {
			work();
		} catch (Exception e) {
			CommonLogger.getLogger().error(getClass(), "timer work get error" , e);
		}					
	}
}
