package agent.aspectP;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;

import agent.agentC.config.XmlProperty;
import yuk.dic.CommandDic;
import yuk.dic.ModelDic;
import yuk.model.MonitorData;
import yuk.model.etc.SubResourceData;
import yuk.model.single.ResourceData;
import yuk.util.CommonLogger;

public class Gc {
	//private Map<String, Long> oldCountMap = new HashMap<String, Long>();
	//private Map<String, Long> oldTimeMap = new HashMap<String, Long>();
	
	//yong : Copy,PS Scavenge, ParNew, G1 Young Generation
	//old : MarkSweepCompact, PS MarkSweep, ConcurrentMarkSweep, G1 Mixed Generation
	private void getGc(MonitorData<ResourceData> mData) throws Exception{
			List<GarbageCollectorMXBean> MBean =  ManagementFactory.getGarbageCollectorMXBeans();
			for(GarbageCollectorMXBean bean : MBean){
				String name = bean.getName();
				ResourceData data = new ResourceData(XmlProperty.MYNAME, CommandDic.COMMAND_GC, name);
				//SubResourceData sub1 = new SubResourceData(getValue(oldCountMap, name, bean.getCollectionCount()), 1);
				//SubResourceData sub2 = new SubResourceData(getValue(oldTimeMap, name, bean.getCollectionTime()), 1);
				SubResourceData sub1 = new SubResourceData(bean.getCollectionCount(), 1);
				SubResourceData sub2 = new SubResourceData(bean.getCollectionTime(), 1);
				data.map.put(ModelDic.COMMITED, sub1);
				data.map.put(ModelDic.TIME, sub2); 
				mData.dataList.add(data);
			}		
	}
	
	/*private long getValue( Map<String, Long> targetMap,String name,  long value){
		long real = 0;
		if(targetMap.get(name) == null)
			real = value;
		else{
			real = targetMap.get(name);
			real = value - real;
		}
		targetMap.put(name, value);
		return real;
	}*/

	public void work(MonitorData<ResourceData> mData) {
		try {
			getGc(mData);
		} catch (Exception e) {
			CommonLogger.getLogger().error(getClass(),"can't send GC data",e);
		}
	}
}
