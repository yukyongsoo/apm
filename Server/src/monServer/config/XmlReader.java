package monServer.config;

import java.lang.reflect.Constructor;
import java.util.Hashtable;

import monServer.event.AbsPostWork;
import monServer.event.EventHandle;
import yuk.config.IConfigReader;
import yuk.dataBase.DBController;
import yuk.util.CommonLogger;

public class XmlReader extends IConfigReader{
	boolean reconfig = false;
	public XmlReader(String path, boolean reconfig) throws Exception{
		super(path);	
		this.reconfig = reconfig;
	}
	
	public void startParms(String szName, Hashtable attrs) {
		if (szName.equals("AMASSSERVER")) {
			try {
				XmlProperty.MYNAME = (String) attrs.get("NAME");
				XmlProperty.TYPE = (String) attrs.get("TYPE");
				XmlProperty.EXPIRESCH = Boolean.parseBoolean((String) attrs.get("EXPIRESCH"));
				XmlProperty.AGGRERES = Boolean.parseBoolean((String) attrs.get("AGGRERES"));
				XmlProperty.TYPE = (String) attrs.get("TYPE");
				XmlProperty.TRACE = Boolean.parseBoolean((String)attrs.get("TRACE"));			
				XmlProperty.SAVELOGPATH = (String)attrs.get("SAVELOGPATH");	
				XmlProperty.REMAINLOGDAY= Integer.parseInt((String)attrs.get("REMAINLOGDAY"));	
				XmlProperty.Slf4j =  Boolean.parseBoolean((String)attrs.get("SLF4J"));	
				XmlProperty.ONREFRESH =  Boolean.parseBoolean((String)attrs.get("ONREFRESH"));	
				if(!reconfig){
					XmlProperty.SERVERIP = (String) attrs.get("IP");
					XmlProperty.SERVERPORT = Integer.parseInt(((String) attrs.get("PORT")));	
				}
			} catch (Exception e) {
				CommonLogger.getLogger().error(getClass(), "can't parse default xml. check you xml", e);				
			}
		}		
		else if (szName.equals("POOL")) {
			if(!reconfig){
				String name = (String)attrs.get("NAME");
				String driver = (String)attrs.get("DRIVER");
				String connect = (String)attrs.get("CONNECT");
				String user = (String)attrs.get("USER");
				String pass = (String)attrs.get("PSWD");
				try {
					DBController controller = new DBController(name, user, pass, driver, connect);
				} catch (Exception e) {
					CommonLogger.getLogger().error(getClass(), "can't conntion to database. check you xml", e);				
				}
			}
		} 
		else if(szName.equals("WORKINTERVAL")){
			XmlProperty.TransWorkerSec  = Long.parseLong((String)attrs.get("TRANS"));
			XmlProperty.ResourceWorkerSec  = Long.parseLong((String)attrs.get("RESOURCE"));
			XmlProperty.LogWorkerSec = Long.parseLong((String)attrs.get("LOG"));
			XmlProperty.EventWorkerSec = Long.parseLong((String)attrs.get("EVENT"));
			XmlProperty.InfoWorkerSec =  Long.parseLong((String)attrs.get("INFO"));
			XmlProperty.HealthWorkerSec =  Long.parseLong((String)attrs.get("HEALTH"));
			XmlProperty.StackWorkerSec =  Long.parseLong((String)attrs.get("STACK"));
			XmlProperty.DBHotWorkerSec =  Long.parseLong((String)attrs.get("DBHOT"));
			XmlProperty.HotWorkerSec =  Long.parseLong((String)attrs.get("HOT"));
			XmlProperty.ExcptWorkerSec =  Long.parseLong((String)attrs.get("EXCPTION"));
			XmlProperty.DisWorkerSec =  Long.parseLong((String)attrs.get("DIS"));
		}
		else if(szName.equals("STATEHANDLE")){
			try {
				String className = (String)attrs.get("POSTWORKER");
				if(className == null || className.length() < 1){
					CommonLogger.getLogger().warn(getClass(), "your post worker is not set.", null);		
					return;
				}
				Class<?> c = Class.forName(className);
				Constructor<?>[] constructors = c.getConstructors();
				AbsPostWork newInstance = (AbsPostWork)constructors[0].newInstance();
				EventHandle.init(newInstance);			
				XmlProperty.health =  Boolean.parseBoolean((String)attrs.get("HEALTH"));
				XmlProperty.trans =  Boolean.parseBoolean((String)attrs.get("TRANS"));	
				XmlProperty.cpu =  Boolean.parseBoolean((String)attrs.get("CPU"));	
				XmlProperty.mem =  Boolean.parseBoolean((String)attrs.get("MEMORY"));	
				XmlProperty.appMem =  Boolean.parseBoolean((String)attrs.get("APPMEMORY"));	
				XmlProperty.excpt =  Boolean.parseBoolean((String)attrs.get("EXCPT"));	
				XmlProperty.disk =  Boolean.parseBoolean((String)attrs.get("DISK"));	
				XmlProperty.net =  Boolean.parseBoolean((String)attrs.get("NETWORK"));
			} catch (Exception e) {
				CommonLogger.getLogger().error(getClass(), "can't set your Post Worker. check you xml", e);		
			} 
		}
	}

	// Ending XML section
	public void endParms(String szName) {
	
	}
}
