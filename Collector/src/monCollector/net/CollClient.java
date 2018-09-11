package monCollector.net;

import java.util.HashMap;
import java.util.Map;

import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.Sigar;

import monCollector.config.XmlProperty;
import yuk.dic.ModelDic;
import yuk.dic.SystemDic;
import yuk.model.MonitorData;
import yuk.model.etc.InitData;
import yuk.network.NetClient;

public class CollClient extends NetClient{

	public CollClient() throws Exception {
		super();
	}

	@Override
	public void sendInit() throws Exception {
		MonitorData<InitData> mData = new MonitorData<InitData>(ModelDic.SERVER, ModelDic.TYPE_INIT,null);
		InitData data = new InitData(XmlProperty.MYNAME, XmlProperty.TYPE, getInfo());
		data.settingData.put(SystemDic.PROPERTY, getSetting());
		mData.dataList.add(data);
		
		for(String child : NetMap.accepter.getManager().getChildMap().keySet()){
			InitData childData = NetMap.accepter.getManager().getChildMap().get(child);
			data.childList.add(child);
			mData.dataList.add(childData);
		}
		send(mData);	
	}

	private Map<String, String> getInfo() throws Exception{
		Sigar sigar = new Sigar();
		Map<String, String> map = new HashMap<String, String>();
		CpuInfo[] cpuInfos = sigar.getCpuInfoList();
		for(CpuInfo info : cpuInfos){
			map.put("CPU Clock", String.valueOf(info.getMhz()));
			map.put("CPU Core", String.valueOf(info.getTotalCores()));
		}
		
		map.put("Total Memory", String.valueOf(sigar.getMem().getTotal() / (1024 * 1024)));
		map.put("Host Name", sigar.getNetInfo().getHostName());
		map.put("Total Process",  String.valueOf(sigar.getProcStat().getTotal()));
		map.put("Total Swap Page Size", String.valueOf(sigar.getSwap().getTotal() / (1024 * 1024)));
		map.put("OS Name", System.getProperty("os.name"));
		map.put("OS Version", System.getProperty("os.version"));
		return map;
	}

	private Map<String, Object> getSetting() throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("MyName", XmlProperty.MYNAME);
		map.put("Type", XmlProperty.TYPE);
		map.put("desc", XmlProperty.DESC);
		map.put("collectorIp", XmlProperty.COLLECTORIP);
		map.put("collectorPort", XmlProperty.COLLECTORPORT);
		map.put("serverIp", XmlProperty.SERVERIP);
		map.put("serverPort", XmlProperty.SERVERPORT);
		map.put("backIp", XmlProperty.BACKIP);
		map.put("backPort", XmlProperty.BACKPORT);
		map.put("machineInfo", XmlProperty.INFO);
		map.put("trace", XmlProperty.TRACE);
		return map;
	}

	
}
