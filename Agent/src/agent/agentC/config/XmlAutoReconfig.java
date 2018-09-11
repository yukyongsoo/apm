package agent.agentC.config;

import java.io.File;
import java.net.URL;

import yuk.Thread.TimerWork;
import yuk.util.CommonLogger;

public class XmlAutoReconfig extends TimerWork{
	boolean reconfig = true;
	long last = 0;

	public XmlAutoReconfig() {
		super();
	}

	@Override
	public void work() {
		try {
			File file;
			if(System.getProperty("XmonA") != null){
				file = new File(System.getProperty("XmonA"));
			}
			else{
				URL url = getClass().getClassLoader().getResource("Agent.xml");
				file = new File(url.toURI().getPath());
			}
			long now = file.lastModified();
			if(last != now){
				XmlReader reader= null;
				if(System.getProperty("XmonA") != null)
					reader = new XmlReader(System.getProperty("XmonA"),reconfig);	
				else
					reader = new XmlReader("Agent.xml",reconfig);
				last = now;
			}
		} catch (Exception e) {
			CommonLogger.getLogger().info(getClass(),"auto reconfig init fail",e);
		}	
	}

	@Override
	public void preWork() throws Exception {
		File file;
		if(System.getProperty("XmonA") != null){
			file = new File(System.getProperty("XmonA"));
		}
		else{
			URL url = getClass().getClassLoader().getResource("Agent.xml");
			file = new File(url.toURI().getPath());
		}
		last = file.lastModified();
	}
}
