package yuk.util;

import yuk.config.BaseProperty;

public abstract class CommonLogger {
	private static CommonLogger logger;
	
	public static CommonLogger getLogger(){
		if(logger == null){
			if(BaseProperty.Slf4j)
				logger = new SlfLogBackLogger();
			else
				logger = new LogbackLogger();
		}
		return logger;
	}
	
	
	
	protected CommonLogger() {}
	public abstract void trace(Class<?> t, String log, Exception e);
	public abstract void debug(Class<?> t,String log, Exception e);
	public abstract void info(Class<?> t,String log, Exception e);
	public abstract void warn(Class<?> t,String log, Exception e);
	public abstract void error(Class<?> t,String log, Exception e);
}
