package monServer;

import monServer.config.XmlAutoReconfig;
import monServer.config.XmlProperty;
import monServer.config.XmlReader;
import monServer.net.AgentParser;
import monServer.net.ConsoleParser;
import monServer.net.NetMap;
import monServer.net.ServerDataParser;
import monServer.net.ServerParser;
import monServer.worker.DBhotWorker;
import monServer.worker.DisWorker;
import monServer.worker.EventWorker;
import monServer.worker.ExcptWorker;
import monServer.worker.HealthWorker;
import monServer.worker.HotWorker;
import monServer.worker.InfoWorker;
import monServer.worker.LogWorker;
import monServer.worker.ResWorker;
import monServer.worker.StackWorker;
import monServer.worker.TransWorker;
import monServer.worker.internal.ExpireThread;
import monServer.worker.internal.HealthChecker;
import yuk.Thread.TimerManager;
import yuk.dataBase.DBController;
import yuk.dic.ModelDic;
import yuk.dic.SystemDic;
import yuk.network.NetAccepter;

public class ServerStarter {	
	private static TimerManager manager = new TimerManager();
	
	public static TimerManager getManager() {
		return manager;
	}
	
	public static void main(String[] args) {
		init();
	}

	public static boolean init() {
		try {
			XmlReader reader= null;
			if(System.getProperty("XmonS") != null)
				reader = new XmlReader(System.getProperty("XmonS"),false);
			else
				reader = new XmlReader("Server.xml",false);
			if(XmlProperty.ONREFRESH)
				removeDB();
			NetMap.accepter = new NetAccepter();
			NetMap.accepter.init(XmlProperty.SERVERIP,XmlProperty.SERVERPORT, new ServerDataParser(new AgentParser(), null, 
					new ServerParser(),new ConsoleParser(), null));
			initInternalWorker();
			initWorker();
			NetMap.setServerInitData();
			shutdownHook();
			System.out.println("Server started..");
			return true;
		} catch (Exception e) {
			System.out.println("Server start fail");
			e.printStackTrace();
			System.exit(0);
			return false;
		}
	}

	//sleep is prevent concurrent db access
	private static void initWorker() throws Exception {
		TransWorker transWorker = new TransWorker();
		manager.addWork("ATW", transWorker, ModelDic.SEC, SystemDic.PCODE_SEC * XmlProperty.TransWorkerSec);
		
		ResWorker resWorker = new ResWorker();
		manager.addWork("ARW", resWorker, ModelDic.SEC, SystemDic.PCODE_SEC * XmlProperty.ResourceWorkerSec);
		
		LogWorker logWorker = new LogWorker();
		manager.addWork("LOG", logWorker, ModelDic.SEC, SystemDic.PCODE_SEC * XmlProperty.LogWorkerSec);
	
		EventWorker eventWorker = new EventWorker();
		manager.addWork("EVENT", eventWorker, ModelDic.SEC, SystemDic.PCODE_SEC * XmlProperty.EventWorkerSec);
	
		
		DBhotWorker dbHotWorker = new DBhotWorker();
		manager.addWork("DBHOT", dbHotWorker, ModelDic.SEC, SystemDic.PCODE_SEC * XmlProperty.DBHotWorkerSec);
		
		ExcptWorker excptWorker = new ExcptWorker();
		manager.addWork("EXCPT", excptWorker, ModelDic.SEC, SystemDic.PCODE_SEC * XmlProperty.ExcptWorkerSec);
		
		HealthWorker healthWorker = new HealthWorker();
		manager.addWork("HEALTH", healthWorker, ModelDic.SEC, SystemDic.PCODE_SEC * XmlProperty.HealthWorkerSec);
		
		InfoWorker infoWorker = new InfoWorker();
		manager.addWork("INFO", infoWorker, ModelDic.SEC, SystemDic.PCODE_SEC * XmlProperty.InfoWorkerSec);
		
		HotWorker hotWorker = new HotWorker();
		manager.addWork("HOT", hotWorker, ModelDic.SEC, SystemDic.PCODE_SEC * XmlProperty.HotWorkerSec);
		
		StackWorker stackWorker = new StackWorker();
		manager.addWork("STACK", stackWorker, ModelDic.SEC, SystemDic.PCODE_SEC * XmlProperty.StackWorkerSec);		
		
		DisWorker disWorker = new DisWorker();
		manager.addWork("DIS", disWorker, ModelDic.SEC, SystemDic.PCODE_SEC * XmlProperty.DisWorkerSec);		
	}
	
	public static void initInternalWorker() throws Exception{
		ExpireThread ext = new ExpireThread(SystemDic.PCODE_Hour);
		if(XmlProperty.EXPIRESCH)
			manager.addWork("EXT", ext, ModelDic.Hour, SystemDic.PCODE_Hour);
	
		HealthChecker healthChecker = new HealthChecker();
		manager.addWork("HC", healthChecker, ModelDic.Min, SystemDic.PCODE_MIN);
			
		XmlAutoReconfig autoReconfig = new XmlAutoReconfig();
		manager.addWork("Reconfig", autoReconfig, ModelDic.Min, SystemDic.PCODE_MIN);
	}
	
	public static void shutdownHook(){
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				try {
					DBController.stopAll();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}));
	}
	
	public static void removeDB() {
		try {
			DBController.getDb("Action").WriteSql("TRUNCATE TABLE actionsec");
			DBController.getDb("Action").WriteSql("TRUNCATE TABLE actionmin");
			DBController.getDb("Action").WriteSql( "TRUNCATE TABLE actionhour");
			DBController.getDb("Action").WriteSql( "TRUNCATE TABLE actionday");
			DBController.getDb("Action").WriteSql("TRUNCATE TABLE actionmonth");
			DBController.getDb("Event").WriteSql("TRUNCATE TABLE event");
			DBController.getDb("Info").WriteSql( "TRUNCATE TABLE info");
			DBController.getDb("Stack").WriteSql( "TRUNCATE TABLE stack");
			DBController.getDb("Resource").WriteSql("TRUNCATE TABLE resourcesec");
			DBController.getDb("Resource").WriteSql("TRUNCATE TABLE resourcemin");
			DBController.getDb("Resource").WriteSql( "TRUNCATE TABLE resourcehour");
			DBController.getDb("Resource").WriteSql( "TRUNCATE TABLE resourceday");
			DBController.getDb("Resource").WriteSql( "TRUNCATE TABLE resourcemonth");
			DBController.getDb("Action").reopenDataBase();
			DBController.getDb("Event").reopenDataBase();
			DBController.getDb("Info").reopenDataBase();
			DBController.getDb("Stack").reopenDataBase();
			DBController.getDb("Resource").reopenDataBase();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
		

}
