package monCollector.worker.resource;

import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

import monCollector.config.XmlProperty;
import yuk.dic.CommandDic;
import yuk.dic.ModelDic;
import yuk.model.MonitorData;
import yuk.model.etc.SubResourceData;
import yuk.model.single.ResourceData;
import yuk.util.CommonLogger;

public class SystemCpu {
	Sigar sigar = new Sigar();	
	
	private double[] getCPU() throws Exception
	{
		try {
			double[] d = new double[3];
		
			d[0] = sigar.getCpuPerc().getCombined() * 100;
			double user = sigar.getCpu().getUser();
			double sys = sigar.getCpu().getSys();
			double total = user + sys;
			user = user / total;
			sys = sys / total;
			d[1] = user * d[0];
			d[2] = sys * d[0];
			return d;
		} catch (SigarException e) {
			CommonLogger.getLogger().warn(getClass(), "can't get system memory", e);		
			return new double[]{2,1,1};
		}
	}

	public void work(MonitorData<ResourceData> mData) {		
		try {
			double[] value = getCPU();		

			ResourceData data = new ResourceData(XmlProperty.MYNAME, CommandDic.COMMAND_SYSCPU, "");
				
			SubResourceData sub1 = new SubResourceData((long) value[0], 1);				
			SubResourceData sub2 = new SubResourceData((long) value[1], 1);		
			SubResourceData sub3 = new SubResourceData((long) value[2], 1);		
							
			data.map.put(ModelDic.TOTAL, sub1);
			data.map.put(ModelDic.USER, sub2);
			data.map.put(ModelDic.SYSTEM, sub3);
			
			mData.dataList.add(data);
		} catch (Exception e) {
			CommonLogger.getLogger().error(getClass(), "can't send system memory to server", e);			
		}		
	}
	
	public double changeValue(long value){
		if(value != 0)
			return value / (1024 * 1024);
		else
			return value;	
	}
}
