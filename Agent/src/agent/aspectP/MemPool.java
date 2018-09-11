package agent.aspectP;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;
import java.util.List;

import agent.agentC.config.XmlProperty;
import yuk.dic.CommandDic;
import yuk.dic.ModelDic;
import yuk.model.MonitorData;
import yuk.model.etc.SubResourceData;
import yuk.model.single.ResourceData;
import yuk.util.CommonLogger;

public class MemPool {
	private void getMemPool(MonitorData<ResourceData> mData) throws Exception{
		List<MemoryPoolMXBean> memoryPoolMXBeans = ManagementFactory.getMemoryPoolMXBeans();
		for(MemoryPoolMXBean bean :memoryPoolMXBeans){
			ResourceData data = new ResourceData(XmlProperty.MYNAME, CommandDic.COMMAND_MEMPOOL, bean.getName());
			MemoryUsage peakUsage = bean.getPeakUsage();
			MemoryUsage usage = bean.getUsage();
			
			SubResourceData sub = new SubResourceData(changeValue(usage.getMax()), 1);
			data.map.put(ModelDic.TOTAL, sub);
			SubResourceData sub2 = new SubResourceData(changeValue(usage.getUsed()), 1);
			data.map.put(ModelDic.USED, sub2);
			SubResourceData sub3 = new SubResourceData(changeValue(peakUsage.getUsed()), 1);
			data.map.put(ModelDic.PEEK, sub3);
			mData.dataList.add(data);
			
		}
	}
	
	public void work(MonitorData<ResourceData> mData) {
		try {
			getMemPool(mData);
		} catch (Exception e) {
			CommonLogger.getLogger().error(getClass(), "can't send MemPool data", e);
		}
	}
	
	public long changeValue(long value){
		return value / (1024 * 1024);
	}
}
