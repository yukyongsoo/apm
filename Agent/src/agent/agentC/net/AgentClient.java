package agent.agentC.net;

import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import agent.agentC.config.XmlProperty;
import yuk.config.BaseProperty;
import yuk.dic.ModelDic;
import yuk.dic.SystemDic;
import yuk.model.MonitorData;
import yuk.model.etc.InitData;
import yuk.network.NetClient;
import yuk.util.CommonLogger;

public class AgentClient extends NetClient {
	public static AgentClient client;
	
	public AgentClient() {
		super();
	}

	@Override
	public void sendInit() throws Exception {
		MonitorData<InitData> object = new MonitorData<InitData>(ModelDic.COLLECTOR, ModelDic.TYPE_INIT,null);
		InitData data = new InitData(XmlProperty.MYNAME, BaseProperty.TYPE, getInfo());
		data.settingData.put(SystemDic.PROPERTY, getSettingData());
		object.dataList.add(data);
		send(object);
	}

	private static Map<String, String> getInfo() throws Exception{
		Map<String, String> map = new HashMap<String, String>();		
		List<String> inputArguments = ManagementFactory.getRuntimeMXBean().getInputArguments();
		String process = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
		String input = "";
		for(String s : inputArguments)
			input += s;
		map.put("pid", process);
		map.put("arch", System.getProperty("os.arch"));
		map.put("classpath", System.getProperty("java.class.path"));
		map.put("userdir", System.getProperty("user.dir"));
		map.put("jvmopt", input);
		map.put("Version", System.getProperty("java.version"));
		map.put("JavaHome", System.getProperty("java.home"));	
		return map;
	}

	private static Map<String, Object> getSettingData() throws Exception{
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("MyName", XmlProperty.MYNAME);
			map.put("Type", XmlProperty.TYPE);
			map.put("desc", XmlProperty.DESC);
			map.put("xvarmIp", XmlProperty.XVARMIP);
			map.put("xvarmPort", XmlProperty.XVARMPORT);
			map.put("Physical", XmlProperty.PHYSICAL);
			map.put("collectorIp", XmlProperty.COLLECTORIP);
			map.put("collectorPort", XmlProperty.COLLECTORPORT);
			map.put("trace", XmlProperty.TRACE);
			return map;
		} catch (Exception e) {
			CommonLogger.getLogger().error(AgentClient.class,"can't send setting data to collector",e);
			return new HashMap<String, Object>();
		}
	}
}
