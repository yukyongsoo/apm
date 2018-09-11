 package monCollector.worker.resource;

import java.util.HashMap;
import java.util.Map;

import monCollector.config.XmlProperty;

import org.hyperic.sigar.NetFlags;
import org.hyperic.sigar.NetInterfaceConfig;
import org.hyperic.sigar.NetInterfaceStat;
import org.hyperic.sigar.NetStat;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

import yuk.dic.CommandDic;
import yuk.model.MonitorData;
import yuk.model.single.MacData;
import yuk.util.CommonLogger;

public class NetworkIo {
	Sigar sigar = new Sigar();

	Map<String, Long> rxOldMap = new HashMap<String, Long>();
	Map<String, Long> txOldMap = new HashMap<String, Long>();

	public void getMetric(MacData data) throws SigarException {
		try {
			HashMap<String, Object> genMap = new HashMap<String, Object>();
			data.dataList.put("Gen", genMap);
			NetStat state = sigar.getNetStat();
			
			genMap.put("CloseWait", state.getTcpCloseWait());
			genMap.put("Establish", state.getTcpEstablished());
			genMap.put("TimeWait", state.getTcpTimeWait());
			genMap.put("FinishWait1", state.getTcpFinWait1());
			genMap.put("FinishWait2", state.getTcpFinWait2());
			genMap.put("Close", state.getTcpClosing());
			genMap.put("Listen", state.getTcpListen());
			
			
			for (String ni : sigar.getNetInterfaceList()) {			
				NetInterfaceStat netStat = sigar.getNetInterfaceStat(ni);
				NetInterfaceConfig ifConfig = sigar.getNetInterfaceConfig(ni);
				String bindedIp = ifConfig.getAddress();
				if (!NetFlags.NULL_HWADDR.equals(ifConfig.getHwaddr())
						&& !bindedIp.startsWith("0")) {
					data.controllerList.add(ifConfig.getDescription());
					String hwaddr = ifConfig.getHwaddr();
					long rxCurrent = netStat.getRxBytes();
					long txCurrent = netStat.getTxBytes();
					HashMap<String, Object> map = new HashMap<String, Object>();
					data.dataList.put(ifConfig.getDescription(), map);
					long rxNow = calByte(hwaddr, rxOldMap, rxCurrent);
					long txNow =  calByte(hwaddr, txOldMap, txCurrent);
					map.put("Read", rxNow);
					map.put("Write", txNow);
					map.put("speed", changeValue(netStat.getSpeed()));
					map.put("mtu", ifConfig.getMtu());
					map.put("readError", netStat.getRxErrors());
					map.put("writeError", netStat.getTxErrors());
					
				}
			}
		} catch (SigarException e) {
			CommonLogger.getLogger().warn(getClass(), "can't get network data", e);		
		}
	}
	
	public long calByte(String hwadder, Map<String, Long> map, long current){
		long value = current;
		if(map.get(hwadder) != null)
			value = current - map.get(hwadder);
		map.put(hwadder, current);
		return changeValue(value);
	}

	public void work(MonitorData<MacData> mData) {
		try {
			MacData data = new MacData(XmlProperty.MYNAME,CommandDic.COMMAND_SYSNET);
			getMetric(data);		
			data.name = XmlProperty.MYNAME;
			data.command = CommandDic.COMMAND_SYSNET;
			mData.dataList.add(data);
		} catch (Exception e) {
			CommonLogger.getLogger().error(getClass(), "can't send resource data to server", e);			
		}
	}
	
	private long changeValue(long value){
		if(value != 0)
			return value / (1024 * 1024);
		else
			return value;
	}
}

