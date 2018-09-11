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

public class SystemSwap {
	Sigar sigar = new Sigar();	
	
	private double[] getSwap() throws Exception
	{
		try {
			double[] d = new double[3];
			d[0] = changeValue(sigar.getSwap().getTotal());
			d[1] = changeValue(sigar.getSwap().getUsed());
			d[2] = changeValue(sigar.getSwap().getFree());
			return d;
		} catch (SigarException e) {
			CommonLogger.getLogger().warn(getClass(), "can't get system Swap", e);			
			return new double[]{2,1,1};
		}
	}

	public void work(MonitorData<ResourceData> mData) {		
		try {
			double[] value = getSwap();		
			
			ResourceData data = new ResourceData(XmlProperty.MYNAME, CommandDic.COMMAND_SYSSWAP, "");
							
			SubResourceData sub1 = new SubResourceData((long) value[0], 1);			
			SubResourceData sub2 = new SubResourceData((long) value[1], 1);		
			SubResourceData sub3 = new SubResourceData((long) value[2], 1);	
									
			data.map.put(ModelDic.TOTAL, sub1);
			data.map.put(ModelDic.USED, sub2);
			data.map.put(ModelDic.FREE, sub3);
			
			mData.dataList.add(data);
		} catch (Exception e) {
			CommonLogger.getLogger().error(getClass(), "can't send system Swap to server", e);			
		}		
	}
	
	public double changeValue(long value){
		if(value != 0)
			return value / (1024 * 1024);
		else 
			return value;
	}
}
