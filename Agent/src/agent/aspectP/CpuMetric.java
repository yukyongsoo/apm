package agent.aspectP;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.ThreadMXBean;

import yuk.Thread.TimerWork;
import yuk.util.CommonLogger;

public class CpuMetric extends TimerWork {
	private ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
	private OperatingSystemMXBean opBean = ManagementFactory.getOperatingSystemMXBean();
	private double now = 0;
	private double last = 0;
	long sleepTime = 1000;
	
	public CpuMetric(long sleepTime) {
		super();
		this.sleepTime = sleepTime;
		if (!threadBean.isThreadCpuTimeSupported()) {
			try {
				threadBean.setThreadCpuTimeEnabled(true);
			} catch (Exception e) {
				CommonLogger.getLogger().warn(getClass(),"this machine not support check java cpu time. always reutrn 0",e);
			}
		}
	}
	
	public long getNow() {
		return (long)now;
	}

	@Override
	public void work() throws Exception {
		double total = 0;
		try {
			long ids[] = threadBean.getAllThreadIds();			
			for (long id : ids) 
				total += threadBean.getThreadCpuTime(id);
			//10000ns
			double pause = opBean.getAvailableProcessors() * 10000 * sleepTime;
			if (last != 0) 
				now = (total - last) / pause;
			last = total;
			if(now < 0)
				now = 0;
		} catch (Exception e) {
			CommonLogger.getLogger().info(getClass(), e.getMessage(), e);
			CommonLogger.getLogger().info(getClass(), "total : " + total + " now :" + now + " last : " + last, null);
		}
	}

	@Override
	public void preWork() throws Exception {
		
	}
}
