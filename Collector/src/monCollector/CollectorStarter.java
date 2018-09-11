package monCollector;

import monCollector.config.XmlAutoReconfig;
import monCollector.config.XmlProperty;
import monCollector.config.XmlReader;
import monCollector.net.CollClient;
import monCollector.net.CollParser;
import monCollector.net.NetMap;
import monCollector.net.accepter.AccCollParser;
import monCollector.net.accepter.AccServerParser;
import monCollector.net.client.CliAgentParser;
import monCollector.worker.AggreActionThread;
import monCollector.worker.AggreDisSender;
import monCollector.worker.AggreResThread;
import monCollector.worker.HealthSender;
import monCollector.worker.MachineInfoThread;
import monCollector.worker.MachineResourceThread;
import yuk.Thread.TimerManager;
import yuk.dic.ModelDic;
import yuk.dic.SystemDic;
import yuk.network.NetAccepter;

public class CollectorStarter {

	private static TimerManager manager = new TimerManager();
	
	public static TimerManager getManager() {
		return manager;
	}

	public static void main(String[] args){
		init();
	}
	
	public static boolean init(){
		boolean init = false;
		try {	
			System.setProperty("java.library.path", "lib/binder");
			XmlReader reader = null;
			if(System.getProperty("XmonC") != null)
				reader = new XmlReader(System.getProperty("XmonC"),false);
			else
				reader = new XmlReader("Collector.xml",false);
			NetMap.accepter = new NetAccepter();
			NetMap.accepter.init(XmlProperty.COLLECTORIP, XmlProperty.COLLECTORPORT, new CollParser(null, new AccCollParser(), new AccServerParser(), null, null));
			
			
			NetMap.mainServer = new CollClient();
			NetMap.mainServer.init(XmlProperty.SERVERIP, XmlProperty.SERVERPORT, new CollParser(new CliAgentParser(), null, null, null, null));
			if(XmlProperty.BACKUP){
				NetMap.backServer = new CollClient();
				NetMap.backServer.init(XmlProperty.BACKIP, XmlProperty.BACKPORT, new CollParser(new CliAgentParser(), null, null, null, null));
			}
			if(NetMap.mainServer.isConnected())
				System.out.println("mainServer Connecter Open");		
			if(XmlProperty.BACKUP && NetMap.backServer.isConnected())
				System.out.println("backUpServer Connecter Open");		
			
			MachineResourceThread mrt = new MachineResourceThread();
			MachineInfoThread mit = new MachineInfoThread();
			
			if(XmlProperty.INFO){
				manager.addWork("Resource",mrt,ModelDic.SEC, SystemDic.PCODE_SEC * XmlProperty.ResourceWorkerSec);
				manager.addWork("ResourceInfo",mit,ModelDic.SEC, SystemDic.PCODE_SEC * XmlProperty.InfoWorkerSec);
			}
			
			AggreResThread art = new AggreResThread();
			manager.addWork("ART",art,ModelDic.SEC, SystemDic.PCODE_SEC * XmlProperty.ResourceWorkerSec);
	
			AggreActionThread aat = new AggreActionThread();
			manager.addWork("AAT",aat,ModelDic.SEC, SystemDic.PCODE_SEC * XmlProperty.TransWorkerSec);
		
			HealthSender ht = new HealthSender();
			manager.addWork("Health",ht,ModelDic.SEC, SystemDic.PCODE_SEC * XmlProperty.HealthWorkerSec); 
			
			AggreDisSender ds = new AggreDisSender();
			manager.addWork("Dis",ds,ModelDic.SEC, SystemDic.PCODE_SEC * XmlProperty.DisWorkerSec); 
			
			XmlAutoReconfig autoReconfig = new XmlAutoReconfig();
			manager.addWork("Reconfig",autoReconfig,ModelDic.Min, SystemDic.PCODE_MIN); 
			
			init = true;			
			System.out.println("collector started..");
		} catch (Exception e) {
			System.out.println("collector start fail");
			e.printStackTrace();
			System.exit(0);
		}		
		return init;
	}
}
