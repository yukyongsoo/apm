package agent.aspectP;

import agent.agentC.config.XmlProperty;
import yuk.dic.CommandDic;
import yuk.dic.ModelDic;
import yuk.model.MonitorData;
import yuk.model.etc.SubResourceData;
import yuk.model.single.ResourceData;
import yuk.util.CommonLogger;

public  class JavaHeap
{

	protected long getFreeMemory() {
		return Runtime.getRuntime().freeMemory() ;
	}

	protected long getMaxMemory() {
		return Runtime.getRuntime().maxMemory() ;
	}

	protected long getTotalMemory() {
		return Runtime.getRuntime().totalMemory() ;
	}

	protected long getAllocation() {
		return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
	}
	
	private long[] getJavaHeap() 
	{
		long[] d = new long[2];
		d[0] = changeValue(getAllocation());
		d[1] = changeValue(getTotalMemory());
		return d;
	}

	public void work(MonitorData<ResourceData> mData) {	
		try {
			long[] d = getJavaHeap();			
			ResourceData data = new ResourceData(XmlProperty.MYNAME, CommandDic.COMMAND_MEM, "");

			SubResourceData sub1 = new SubResourceData(d[0], 1);	
			SubResourceData sub2 = new SubResourceData(d[1], 1);
			
			data.map.put(ModelDic.USED, sub1);
			data.map.put(ModelDic.TOTAL, sub2);
			mData.dataList.add(data);
			
		} catch (Exception e) {
			CommonLogger.getLogger().error(getClass(),"can't send javaheap", e);
		}
	}
	
	public long changeValue(long value){
		return value / (1024 * 1024);
	}
}
