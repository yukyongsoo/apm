package agent.agentC;

import agent.agentC.config.XmlAutoReconfig;
import agent.agentC.config.XmlProperty;
import agent.agentC.config.XmlReader;
import agent.agentC.net.AgentClient;
import agent.agentC.net.AgentDataParser;
import agent.agentC.woker.DbHotSpotSender;
import agent.agentC.woker.DeComCleaner;
import agent.agentC.woker.DisSender;
import agent.agentC.woker.ExceptionSender;
import agent.agentC.woker.HealthSender;
import agent.agentC.woker.HotSpotSender;
import agent.agentC.woker.LogSender;
import agent.agentC.woker.ResourceSender;
import agent.agentC.woker.ThreadWorker;
import agent.agentC.woker.TransSender;
import agent.agentC.woker.TransTimeChecker;
import agent.ext.AbsMonitor;
import yuk.Thread.TimerManager;
import yuk.dic.ModelDic;
import yuk.dic.SystemDic;
import yuk.model.MonitorData;
import yuk.model.etc.EventData;
import yuk.model.single.LogData;
import yuk.util.CommonLogger;

public class Agent {
	private static Agent agent;
	public static boolean init = false;
	private TimerManager manager = new TimerManager();
	private AbsMonitor monitor;

	//for use TEST
	public static void main(String[] args){		
		Agent agent = Agent.getInstance();
		agent.init();
	}
	
	public TimerManager getManager() {
		return manager;
	}
	
	private Agent(){
		System.out.println("Agent Install Success..");
	}
	
	public static synchronized Agent getInstance(){
		if (agent == null)
			agent = new Agent();
		return agent;
	}
	
	public void init(){		
		try {
			XmlReader reader= null;
			if(System.getProperty("XmonA") != null)
				reader = new XmlReader(System.getProperty("XmonA"),false);	
			else
				reader = new XmlReader("Agent.xml",false);	
			
			Class c = Class.forName(XmlProperty.CLASS);
			monitor = (AbsMonitor)c.newInstance();
			
			AgentClient.client = new AgentClient();
			AgentClient.client.init(XmlProperty.COLLECTORIP, XmlProperty.COLLECTORPORT, new AgentDataParser(null,null,null,null,null));
			
			ResourceSender resouce = new ResourceSender();
			manager.addWork("Resource", resouce, ModelDic.SEC, XmlProperty.ResourceWorkerSec * SystemDic.PCODE_SEC);
			
			TransSender trans = new TransSender();
			manager.addWork("Trans", trans, ModelDic.SEC, XmlProperty.TransWorkerSec * SystemDic.PCODE_SEC);
			
			LogSender log = new LogSender();
			manager.addWork("Log", log, ModelDic.SEC, XmlProperty.LogWorkerSec * SystemDic.PCODE_SEC);
			
			HealthSender health = new HealthSender();
			manager.addWork("Heath", health,  ModelDic.SEC, XmlProperty.HealthWorkerSec *  SystemDic.PCODE_SEC);
			
			DbHotSpotSender dbHotSpotSender = new DbHotSpotSender();
			manager.addWork("dbHot", dbHotSpotSender,  ModelDic.SEC, XmlProperty.DBHotWorkerSec *  SystemDic.PCODE_SEC);
			
			HotSpotSender hotSpotSender = new HotSpotSender();
			manager.addWork("Hot", hotSpotSender,  ModelDic.SEC, XmlProperty.HotWorkerSec *  SystemDic.PCODE_SEC);
			
			ThreadWorker threadWorker = new ThreadWorker();
			manager.addWork("stack", threadWorker,  ModelDic.SEC, XmlProperty.StackWorkerSec *  SystemDic.PCODE_SEC);
			
			ExceptionSender exceptionSender = new ExceptionSender();
			manager.addWork("Excpt", exceptionSender,  ModelDic.SEC, XmlProperty.ExcptWorkerSec *  SystemDic.PCODE_SEC);
			
			DisSender disSender = new DisSender();
			manager.addWork("Dis", disSender,  ModelDic.SEC, XmlProperty.DisWorkerSec *  SystemDic.PCODE_SEC);
			
			XmlAutoReconfig autoReconfig = new XmlAutoReconfig();
			manager.addWork("reconfig", autoReconfig,  ModelDic.Min, SystemDic.PCODE_MIN);
			
			TransTimeChecker checker = new TransTimeChecker();
			manager.addWork("Transchecker", checker,  ModelDic.Min, SystemDic.PCODE_MIN * 10);	
			
			DeComCleaner cleaner = new DeComCleaner();
			manager.addWork("DecomCleaner", cleaner,  ModelDic.Min, SystemDic.PCODE_MIN * 10);	
			
			System.out.println("agent inited success");			
			init = true;
		} catch (Exception e) {		
			System.out.println("agent inited fail");		
			e.printStackTrace();
			init = false;
		}
	}
			
	public void shutdown(){
		try {
			manager.stopALL();
			AgentClient.client.dispose();
			System.out.println("dispose success agent.");			
			init = false;
		} catch (Exception e) {
			System.out.println("dispose agent fail.");			
			e.printStackTrace();
			init = false;
		}
	}	
	
	public void event(String level, String log) {
		try {
			MonitorData<EventData> mData = new MonitorData<EventData>(ModelDic.SERVER, ModelDic.TYPE_EVENT,null);
			EventData data = new EventData(XmlProperty.MYNAME, level, log);
			mData.dataList.add(data);
			AgentClient.client.send(mData);
		} catch (Exception e) {
			CommonLogger.getLogger().error(getClass(),"can't send event data to collector" , e);
		}
	}
	
	public void log(String level, String log){		
		try {
			log.replaceAll("[\\r\\n]+", " ");
			LogData data = new LogData(XmlProperty.MYNAME,level, log);
			data.time = System.currentTimeMillis();
			if(LogSender.queue != null)
				LogSender.queue.add(data);
		} catch (Exception e) {
			CommonLogger.getLogger().error(getClass(),"can't catch log data",e);
		}
	}
	
	public void inited() {
		monitor.init();
	}

	public void end() {
		monitor.end();
	}

	public void transStart(Object targetClass, Object[] args) {
		monitor.transStart(targetClass, args);
	}

	public void transEnd(Object targetClass,Object returnObject,Object[] args) {
		monitor.transEnd(targetClass,returnObject,args);
	}

	public void log(Object[] args) {
		monitor.log(args);
	}
}
