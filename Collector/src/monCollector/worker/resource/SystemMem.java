package monCollector.worker.resource;

import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

import monCollector.config.XmlProperty;
import yuk.dic.CommandDic;
import yuk.dic.ModelDic;
import yuk.model.MonitorData;
import yuk.model.etc.SubResourceData;
import yuk.model.single.ResourceData;
import yuk.util.CommonLogger;


public  class SystemMem
{	
	Sigar sigar = new Sigar();
	
	private double[] getMem() throws Exception
	{
		try {
			double[] d = new double[2];
			Mem mem = sigar.getMem();
			d[0] = changeValue(mem.getTotal());
			d[1] = changeValue(mem.getUsed());
			return d;
		} catch (SigarException e) {
			CommonLogger.getLogger().warn(getClass(), "can't get system memory to server", e);
			return new double[]{2,1};
		}		
	}

	public void work(MonitorData<ResourceData> mData) {		
		try {
			double[] value = getMem();		
			
			ResourceData data = new ResourceData(XmlProperty.MYNAME, CommandDic.COMMAND_SYSMEM, "");
			
			SubResourceData sub1 = new SubResourceData((long) value[0], 1);
			SubResourceData sub2 = new SubResourceData((long) value[1],1);	
			
			data.map.put(ModelDic.TOTAL, sub1);
			data.map.put(ModelDic.USED, sub2);
			
			mData.dataList.add(data);
		} catch (Exception e) {
			CommonLogger.getLogger().error(getClass(), "can't send system memory to server", e);			
		}		
	}
	
	public double changeValue(long value){
		if(value != 0)
			return value /  (1024* 1024);
		else
			return value;		
	}
}
