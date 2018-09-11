package monCollector.config;

import java.util.Hashtable;

import monCollector.worker.ActionNullChecker;
import yuk.config.IConfigReader;
import yuk.util.CommonLogger;

public class XmlReader extends IConfigReader{	
	boolean reconfig = false;
	boolean subList = false;
	
	public XmlReader(String path, boolean reconfig) throws Exception{
		super(path);
		this.reconfig = reconfig;
	}
	
	public void startParms(String szName, Hashtable attrs) {
		if(szName.equals("XMCOLLECTOR")){
			try {
				XmlProperty.MYNAME =  (String)attrs.get("MYNAME");	
				XmlProperty.TYPE = (String) attrs.get("TYPE");
				XmlProperty.DESC =  (String)attrs.get("DESC");	
				XmlProperty.INFO = Boolean.parseBoolean((String)attrs.get("MACHINEINFO"));	
				if(!reconfig){
					XmlProperty.COLLECTORIP = (String)attrs.get("COLLECTORIP");	
					XmlProperty.COLLECTORPORT = Integer.parseInt((String)attrs.get("COLLECTORPORT"));	
					XmlProperty.SERVERIP = (String)attrs.get("SERVERIP");	
					XmlProperty.SERVERPORT = Integer.parseInt((String)attrs.get("SERVERPORT"));
					try {
						XmlProperty.BACKIP = (String)attrs.get("BACKIP");	
						XmlProperty.BACKPORT =  Integer.parseInt((String)attrs.get("BACKPORT"));
					} catch (Exception e) {
						XmlProperty.BACKUP = false;
						CommonLogger.getLogger().info(getClass(),"backup server is not work. check your backserver or xmlfile.",null);	
					}	
				}
				XmlProperty.TRACE =  Boolean.parseBoolean((String)attrs.get("TRACE"));		
				XmlProperty.Slf4j =  Boolean.parseBoolean((String)attrs.get("SLF4J"));	
			} catch (Exception e) {
				CommonLogger.getLogger().error(getClass(), "xml is not complete form. check you xml", e);				
			}	
		}		
		else if(szName.equals("WORKINTERVAL")){
			XmlProperty.EventWorkerSec = Long.parseLong((String)attrs.get("RESOURCE"));
			XmlProperty.HealthWorkerSec  = Long.parseLong((String)attrs.get("HEALTH"));
			XmlProperty.LogWorkerSec  = Long.parseLong((String)attrs.get("LOG"));
			XmlProperty.ResourceWorkerSec  = Long.parseLong((String)attrs.get("EVENT"));
			XmlProperty.TransWorkerSec  = Long.parseLong((String)attrs.get("TRANS"));
			XmlProperty.InfoWorkerSec  = Long.parseLong((String)attrs.get("INFO"));
			XmlProperty.DisWorkerSec  = Long.parseLong((String)attrs.get("DIS"));
		}
		else if(szName.equals("SUBCHILDLIST")){
			ActionNullChecker.clear();
			subList = true;
		}
		else if(subList){
			ActionNullChecker.init(szName, (String) attrs.get("TYPE"));
		}
	}

	// Ending XML section
	public void endParms(String szName) {
		if(szName.equals("SUBCHILDLIST")){
			subList = false;
		}
	}
}
