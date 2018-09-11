package agent.agentC.config;

import java.util.Hashtable;

import yuk.config.IConfigReader;
import yuk.util.CommonLogger;

public class XmlReader extends IConfigReader{	
	boolean reconfig = false;
	//do not use!!!!!!!!!!!!!!
	public XmlReader(String path,boolean reconfig) throws Exception{
		super(path);
		this.reconfig = reconfig;
	}

	public void startParms(String szName, Hashtable attrs) {
		if(szName.equals("XMAGENT")){
			try {
				XmlProperty.MYNAME =  (String)attrs.get("MYNAME");	
				XmlProperty.TYPE = (String) attrs.get("TYPE");
				XmlProperty.DESC =  (String)attrs.get("DESC");	
				XmlProperty.COLLECTORIP = (String)attrs.get("COLLECTORIP");	
				XmlProperty.COLLECTORPORT = Integer.parseInt((String)attrs.get("COLLECTORPORT"));
				XmlProperty.TRACE = Boolean.parseBoolean((String)attrs.get("TRACE"));	
				XmlProperty.Slf4j =  Boolean.parseBoolean((String)attrs.get("SLF4J"));	
				XmlProperty.CLASS = (String)attrs.get("CLASS");	
			} catch (Exception e) {
				CommonLogger.getLogger().error(getClass(), "xml param is wrong. please check your Agent.xml", e);
			}
			
		}
		else if(szName.equals("WORKINTERVAL")){
			XmlProperty.EventWorkerSec = Long.parseLong((String)attrs.get("RESOURCE"));
			XmlProperty.HealthWorkerSec  = Long.parseLong((String)attrs.get("HEALTH"));
			XmlProperty.LogWorkerSec  = Long.parseLong((String)attrs.get("LOG"));
			XmlProperty.ResourceWorkerSec  = Long.parseLong((String)attrs.get("EVENT"));
			XmlProperty.TransWorkerSec  = Long.parseLong((String)attrs.get("TRANS"));
			XmlProperty.StackWorkerSec =  Long.parseLong((String)attrs.get("STACK"));
			XmlProperty.HotWorkerSec =  Long.parseLong((String)attrs.get("HOTSPOT"));
			XmlProperty.DBHotWorkerSec =  Long.parseLong((String)attrs.get("DBHOTSPOT"));
			XmlProperty.ExcptWorkerSec =  Long.parseLong((String)attrs.get("EXCPT"));
			XmlProperty.DisWorkerSec =  Long.parseLong((String)attrs.get("DIS"));
		}
		else if(szName.equals("XVARM")){
			try {
				XmlProperty.XVARMIP = (String)attrs.get("XVARMIP");	
				XmlProperty.XVARMPORT = Integer.parseInt((String)attrs.get("XVARMPORT"));
				XmlProperty.XVARMID = (String)attrs.get("XVARMID");	
				XmlProperty.XVARMPASS = (String)attrs.get("XVARMPASS");	
				XmlProperty.PHYSICAL = Boolean.parseBoolean((String)attrs.get("PHYSICAL"));
				XmlProperty.XVARM = true;
			} catch (Exception e) {
				XmlProperty.XVARM = false;
				CommonLogger.getLogger().info(getClass(), "XVARM PARAM of xml is wrong."+ " xvarm work not running. your app is not xvarm so disregard",e);
			}	
		}
		
		
	}

	// Ending XML section
	public void endParms(String szName) {
		
	}
}
