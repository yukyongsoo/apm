package agent.ext;



public abstract class AbsMonitor {
	public abstract void init();

	public abstract void end();

	public abstract void transStart(Object targetClass, Object[] args);

	public abstract void transEnd(Object targetClass,Object returnObject,Object[] args);

	public abstract void log(Object[] args);
}
