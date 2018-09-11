package yuk.util;

import java.net.URL;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.util.ContextInitializer;
import ch.qos.logback.core.joran.spi.JoranException;

public class LogbackLogger extends CommonLogger {
	LoggerContext loggerContext;

	protected LogbackLogger() {
		super();
		loggerContext = new LoggerContext();
		ContextInitializer contextInitializer = new ContextInitializer(loggerContext);
		try {
			
			URL configurationUrl = Thread.currentThread().getContextClassLoader().getResource("logback.xml");
			if (configurationUrl == null) {
				throw new IllegalStateException("Unable to find custom logback configuration file");
			}
			contextInitializer.configureByResource(configurationUrl);
		} catch (JoranException e) {
			throw new RuntimeException("Unable to configure logger", e);
		}
	}

	@Override
	public void trace(Class<?> t, String log, Exception e) {
		Logger logger = loggerContext.getLogger(t);
		if(e != null)
			logger.trace(log,e);
		else 
			logger.trace(log);
	}

	@Override
	public void debug(Class<?> t, String log, Exception e) {
		Logger logger = loggerContext.getLogger(t);
		if(e != null)
			logger.debug(log,e);
		else 
			logger.debug(log);
	}

	@Override
	public void info(Class<?> t, String log, Exception e) {
		Logger logger = loggerContext.getLogger(t);
		if(e != null)
			logger.info(log,e);
		else 
			logger.info(log);
	}

	@Override
	public void warn(Class<?> t, String log, Exception e) {
		Logger logger = loggerContext.getLogger(t);
		if(e != null)
			logger.warn(log,e);
		else 
			logger.warn(log);
	}

	@Override
	public void error(Class<?> t, String log, Exception e) {
		Logger logger = loggerContext.getLogger(t);
		if(e != null)
			logger.error(log,e);
		else 
			logger.error(log);
	}

	


	
}
