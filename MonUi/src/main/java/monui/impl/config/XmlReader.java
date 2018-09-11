package monui.impl.config;

import java.util.Hashtable;

import yuk.config.IConfigReader;
import yuk.util.CommonLogger;

public class XmlReader extends IConfigReader{	
	boolean reconfig;
	
	public XmlReader(String path, boolean reconfig) throws Exception{
		super(path);
		this.reconfig = reconfig;
	}
	
	public void startParms(String szName, Hashtable attrs) {
		if(szName.equals("XMUI")){
			try {
				XmlProperty.MYNAME =  (String)attrs.get("MYNAME");	
				XmlProperty.TYPE = (String)attrs.get("TYPE");	
				XmlProperty.DESC =  (String)attrs.get("DESC");	
				if(!reconfig){
					XmlProperty.IP = (String)attrs.get("IP");
					XmlProperty.PORT = Integer.parseInt((String)attrs.get("PORT"));
				}
				XmlProperty.TRACE = Boolean.parseBoolean((String)attrs.get("TRACE"));		
				XmlProperty.Slf4j =  Boolean.parseBoolean((String)attrs.get("SLF4J"));	
			} catch (Exception e) {
				CommonLogger.getLogger().error(getClass(),"can't parse xml file. check your xml inside war",e);			
			}	
		}	
	}

	// Ending XML section
	public void endParms(String szName) {
		
	}
}
