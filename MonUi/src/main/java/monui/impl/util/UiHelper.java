package monui.impl.util;

import java.util.HashMap;
import java.util.Map;

import monui.impl.caster.CasteeInterface;
import monui.impl.caster.Caster;
import monui.impl.net.ServerControll;
import yuk.dic.CommandDic;
import yuk.dic.ModelDic;
import yuk.model.CastHeader;
import yuk.model.MonitorData;
import yuk.model.etc.EventData;
import yuk.model.etc.InitData;
import yuk.model.single.LogData;
import yuk.model.single.MacData;
import yuk.model.single.ResourceData;
import yuk.model.single.StackData;
import yuk.model.single.TransactionData;
import yuk.util.CommonLogger;

public class UiHelper {
	private static Map<String, UiHelper> map = new HashMap<String, UiHelper>();
	private ServerControll sc;

	public static UiHelper getHelper(String key) {
		UiHelper helper;
		if(map.get(key) == null)
			helper = new UiHelper(key);
		else
			helper = map.get(key);
		map.put(key, helper);
		return helper;
	}
	
	public static UiHelper getHelper() {
		return getHelper(UiUtil.getKey());
	}
		
	private UiHelper(String key) {
		try {
			sc = ServerControll.getServerControll(key);
		} catch (Exception e) {
			CommonLogger.getLogger().error(getClass(), "Server Controll init error", e);
		}
	}
	
	private void addTarget(CasteeInterface ci){
		Caster.getCaster(UiUtil.getKey()).addTarget(ci);
	}

	public boolean sendInit(String name) throws Exception {
		CastHeader header = new CastHeader(name);
		MonitorData<InitData> object = new MonitorData<InitData>(ModelDic.CONSOLE, ModelDic.TYPE_INIT, header);
		sc.send(object);		
		return true;
	}

	public void sendStat(String session,String agent,String type, long start, long end,CasteeInterface ci) throws Exception {
		addTarget(ci);
		CastHeader header = new CastHeader(session);
		header.range = type;
		header.from = start;
		header.to = end;
		InitData init = UiUtil.getInit(agent);
		if(init.type.equals(ModelDic.AGENT))
			sendAgent(header,agent);
		else
			sendColl(header,agent);	
	}
	
	private void sendAgent(CastHeader header, String agent) throws Exception{
		TransactionData data = new TransactionData(agent, "", 0, 0, 0);
		MonitorData<TransactionData> object = new MonitorData<TransactionData>(ModelDic.CONSOLE, ModelDic.TYPE_TRANS, header);
		object.dataList.add(data);
		sc.send(object);
				
		ResourceData resource= new ResourceData(agent, CommandDic.COMMAND_CPU, "");
		MonitorData<ResourceData> rObject = new MonitorData<ResourceData>(ModelDic.CONSOLE, ModelDic.TYPE_RESOURCE, header);
		rObject.dataList.add(resource);
		sc.send(rObject);
		
		ResourceData resource2= new ResourceData(agent, CommandDic.COMMAND_MEM, "");
		MonitorData<ResourceData> rObject2 = new MonitorData<ResourceData>(ModelDic.CONSOLE, ModelDic.TYPE_RESOURCE, header);
		rObject2.dataList.add(resource2);
		sc.send(rObject2);
		
		LogData log = new LogData(agent, "", "");
		MonitorData<LogData> rObject3 = new MonitorData<LogData>(ModelDic.CONSOLE, ModelDic.TYPE_LOG, header);
		rObject3.dataList.add(log);
		sc.send(rObject3);
	}
	
	private void sendColl(CastHeader header, String agent) throws Exception{
		ResourceData data= new ResourceData(agent,  CommandDic.COMMAND_SYSCPU, "");
		MonitorData<ResourceData> object = new MonitorData<ResourceData>(ModelDic.CONSOLE, ModelDic.TYPE_RESOURCE, header);
		object.dataList.add(data);
		sc.send(object);
		
		ResourceData data1= new ResourceData(agent, CommandDic.COMMAND_SYSMEM, "");
		MonitorData<ResourceData> object1 = new MonitorData<ResourceData>(ModelDic.CONSOLE, ModelDic.TYPE_RESOURCE, header);
		object1.dataList.add(data1);
		sc.send(object1);
				
		/*MacData data2= new MacData(agent, CommandDic.COMMAND_SYSNET);
		MonitorData<MacData> object2 = new MonitorData<MacData>(ModelDic.CONSOLE, ModelDic.TYPE_MAC, header);
		object2.dataList.add(data2);
		sc.send(object2);
		
		MacData data3= new MacData(agent, CommandDic.COMMAND_SYSDISK);
		MonitorData<MacData> object3 = new MonitorData<MacData>(ModelDic.CONSOLE, ModelDic.TYPE_MAC, header);
		object3.dataList.add(data3);
		sc.send(object3);*/
	}
	
	public void sendDisk(String session, String name, long time,CasteeInterface ci) throws Exception{
		addTarget(ci);
		MacData data = new MacData(name, CommandDic.COMMAND_SYSDISK);
		CastHeader header = new CastHeader(session);
		header.from = time;
		MonitorData<MacData> object = new MonitorData<MacData>(ModelDic.CONSOLE, 
				ModelDic.TYPE_MAC, header);
		object.dataList.add(data);
		sc.send(object);
	}

	public void sendNet(String session, String name, long time,CasteeInterface ci) throws Exception{
		addTarget(ci);
		MacData data = new MacData(name, CommandDic.COMMAND_SYSNET);
		CastHeader header = new CastHeader(session);
		header.from = time;
		MonitorData<MacData> object = new MonitorData<MacData>(ModelDic.CONSOLE, 
				ModelDic.TYPE_MAC, header);
		object.dataList.add(data);
		sc.send(object);
	}

	public void sendEvent(String session, String agent, String level, long time, long time2,CasteeInterface ci) throws Exception {
		addTarget(ci);
		EventData data = new EventData(agent, level, "");
		CastHeader header = new CastHeader(session);
		header.from = time;
		header.to = time2;
		MonitorData<EventData> object = new MonitorData<EventData>(ModelDic.CONSOLE, 
				ModelDic.TYPE_EVENT, header);
		object.dataList.add(data);
		sc.send(object);
		
	}
	
	public void sendStack(String name,String agent, long time,CasteeInterface ci)throws Exception {
		addTarget(ci);
		StackData data = new StackData(agent);
		CastHeader header= new CastHeader(name);
		header.from = time;
		MonitorData<StackData> object = new MonitorData<StackData>(ModelDic.CONSOLE, ModelDic.TYPE_STACK, header);
		object.dataList.add(data);
		sc.send(object);
	}
	
	public void sendLog(String name,String agent,long start, long end,String level, String text,CasteeInterface ci) throws Exception {
		addTarget(ci);
		CastHeader header = new CastHeader(name);
		header.from = start;
		header.to = end;
		LogData data = new LogData(agent, level, text);
		MonitorData<LogData> object = new MonitorData<LogData>(ModelDic.CONSOLE, ModelDic.TYPE_LOG, header);
		object.dataList.add(data);
		sc.send(object);
	}
}
