package monui.impl.net;

import java.util.HashMap;
import java.util.Map;

import monui.impl.caster.Caster;
import monui.impl.caster.CasterCleaner;
import yuk.Thread.TimerManager;
import yuk.dic.ModelDic;
import yuk.dic.SystemDic;
import yuk.model.MonitorData;
import yuk.util.CommonLogger;
import yuk.util.NormalUtil;

public class ServerControll{
	//key = ip::port
	private static Map<String, ServerControll> serverMap = new HashMap<String, ServerControll>();
	//key = ip::port
	private static Map<String, Caster> casterMap = new HashMap<String, Caster>();
	
	public static ServerControll getServerControll(String ip,String port) throws Exception{
		String key = NormalUtil.makeKey("::", ip,port);
		ServerControll controll = null;
		if(serverMap.get(key) == null){
			controll = new ServerControll(ip,port);
			serverMap.put(key, controll);
		}
		controll = serverMap.get(key);
		return controll;
	}
	
	public static ServerControll getServerControll(String key) throws Exception{
		String ip = key.split("::")[0];
		String port = key.split("::")[1];
		return getServerControll(ip, port);
	}
	
	/**
	 * do not call this. instead call Caster.getCaster();
	 * @param key
	 * @return
	 */
	public static Caster getCaster(String key) {
		Caster caster;
		if(casterMap.get(key) == null){
			caster = new Caster();
		}
		else
			caster = casterMap.get(key);
		casterMap.put(key, caster);
		return caster;
	}
	
	private String key = "";
	private UIClient client;
	private UiParser dataWork;
	private Caster caster;
	private TimerManager serverManager = new TimerManager();
	private CasterCleaner casterCleaner;
	
	private ServerControll(String ip, String port) throws Exception{
		try {
			String key = NormalUtil.makeKey("::", ip,port);
			this.key = key;
			client = new UIClient();
			caster = getCaster(key);
			casterCleaner = new CasterCleaner(caster.getTargetCaster());
			serverManager.addWork("TargetCleaner", casterCleaner, ModelDic.Hour, SystemDic.PCODE_Hour);
			dataWork = new UiParser(null, null, null, new ConsoleParser(caster), null);
			client.init(ip, Integer.parseInt(port), dataWork);	
		} catch (Exception e) {
			CommonLogger.getLogger().error(getClass(),"can't init controll",e);
			throw e;
		}
	}

	public boolean isStatus() {
		try {
			return client.isConnected();
		} catch (Exception e) {
			return false;
		}
	}
	
	public void send(MonitorData<?> object){
		try {
			client.send(object);
		} catch (Exception e) {			
			CommonLogger.getLogger().info(getClass(),"can't send." + object ,e);			
		}
	}
		
	public boolean syncSend(MonitorData<?> object){
		try {
			return client.syncSend(object,5000);
		} catch (Exception e) {			
			CommonLogger.getLogger().info(getClass(),"can't sync send." + object,e);		
		}
		return false;
	}	
	
	public void distroy() throws Exception {		
		caster.cleanUp();
	}

}
