package agent.aspectP;

import agent.agentC.Agent;
import agent.agentC.config.XmlProperty;
import yuk.dic.CommandDic;
import yuk.dic.ModelDic;
import yuk.dic.SystemDic;
import yuk.model.MonitorData;
import yuk.model.etc.SubResourceData;
import yuk.model.single.ResourceData;
import yuk.util.CommonLogger;

public class CPU {
	CpuMetric cpuThread = null;
	private long getCpu() throws Exception {
		if(cpuThread == null){
			cpuThread = new CpuMetric(SystemDic.PCODE_SEC * 3);
			Agent.getInstance().getManager().addWork("CPUmetric", cpuThread, ModelDic.SEC, SystemDic.PCODE_SEC * 3);
		}
		return cpuThread.getNow();
	}

	public void work(MonitorData<ResourceData> mData) {
		try {
			ResourceData data = new ResourceData(XmlProperty.MYNAME, CommandDic.COMMAND_CPU, "");
			SubResourceData sub = new SubResourceData(getCpu(), 1);
			data.map.put(ModelDic.USED, sub);
			mData.dataList.add(data);
		} catch (Exception e) {
			CommonLogger.getLogger().error(getClass(), "can't send CPU data", e);
		}
	}
}
