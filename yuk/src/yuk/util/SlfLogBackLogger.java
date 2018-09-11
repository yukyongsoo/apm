package yuk.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SlfLogBackLogger extends CommonLogger{
	private static Logger logger;
	
	protected SlfLogBackLogger(){
		
	}
	
	@Override
	public void trace(Class<?> t, String log, Exception e) {
		logger = LoggerFactory.getLogger(t);
		if(e != null)
			logger.trace(log,e);
		else
			logger.trace(log);
	}

	@Override
	public void debug(Class<?> t, String log, Exception e) {
		logger = LoggerFactory.getLogger(t);
		if(e != null)
			logger.debug(log,e);
		else
			logger.debug(log);
	}

	@Override
	public void info(Class<?> t, String log, Exception e) {
		logger = LoggerFactory.getLogger(t);
		if(e != null)
			logger.info(log,e);
		else
			logger.info(log);
	}

	@Override
	public void warn(Class<?> t, String log, Exception e) {
		logger = LoggerFactory.getLogger(t);
		if(e != null)
			logger.warn(log,e);
		else
			logger.warn(log);
	}

	@Override
	public void error(Class<?> t, String log, Exception e) {
		logger = LoggerFactory.getLogger(t);
		if(e != null)
			logger.error(log,e);
		else
			logger.error(log);
	}

}
