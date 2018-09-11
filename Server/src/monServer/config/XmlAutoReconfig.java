package monServer.config;

import java.io.File;
import java.net.URL;

import monServer.ServerStarter;
import monServer.worker.internal.ExpireThread;
import yuk.Thread.TimerWork;
import yuk.dic.ModelDic;
import yuk.dic.SystemDic;
import yuk.util.CommonLogger;

public class XmlAutoReconfig extends TimerWork{
	boolean reconfig = true;
	long last;
	
	public XmlAutoReconfig() {
		super();
		
	}

	@Override
	public void work() throws Exception {
		try {
			File file = null;
			if(System.getProperty("XmonS") != null){
				file = new File(System.getProperty("XmonS"));
			}
			else{
				URL url = getClass().getClassLoader().getResource("Server.xml");
				file = new File(url.toURI().getPath());
			}
			long now = file.lastModified();
			
			if(last != now){
				XmlReader reader= null;
				if(System.getProperty("XmonS") != null)
					reader = new XmlReader(System.getProperty("XmonS"),reconfig);
				else
					reader = new XmlReader("Server.xml",reconfig);
				last = now;
				
				if(XmlProperty.EXPIRESCH && !ServerStarter.getManager().isWork("EXT")){
					ExpireThread ext = new ExpireThread(SystemDic.PCODE_MIN);
					ServerStarter.getManager().addWork("EXT", ext, ModelDic.Min, SystemDic.PCODE_MIN);
				}
				else if(!XmlProperty.EXPIRESCH && ServerStarter.getManager().isWork("EXT")){
					ServerStarter.getManager().removeWork("EXT");
				}
			}
		} catch (Exception e) {
			CommonLogger.getLogger().info(getClass(), "auto reconfig fail", e);				
		}
	}

	@Override
	public void preWork() throws Exception {
		try {
			File file = null;
			if(System.getProperty("XmonS") != null){
				file = new File(System.getProperty("XmonS"));
			}
			else{
				URL url = getClass().getClassLoader().getResource("Server.xml");
				file = new File(url.toURI().getPath());
			}
			last = file.lastModified();
		} catch (Exception e) {
			CommonLogger.getLogger().info(getClass(), "auto reconfig init fail", e);
		}
	}
}
