package monCollector.config;


import java.io.File;
import java.net.URL;

import monCollector.CollectorStarter;
import monCollector.worker.MachineInfoThread;
import monCollector.worker.MachineResourceThread;
import yuk.Thread.TimerWork;
import yuk.dic.ModelDic;
import yuk.dic.SystemDic;
import yuk.util.CommonLogger;

public class XmlAutoReconfig extends TimerWork{
	boolean reconfig = true;
	long last =0 ;
	
	public XmlAutoReconfig() {
		super();
	}

	@Override
	public void work() throws Exception {
		try {
			File file = null;
			if(System.getProperty("XmonC") != null)
				file = new File(System.getProperty("XmonC"));
			else{
				URL url = getClass().getClassLoader().getResource("Collector.xml");
				file = new File(url.toURI().getPath());
			}
			long now = file.lastModified();
			
			XmlReader reader = null;
			if(last != now){
				if(System.getProperty("XmonC") != null)
					reader = new XmlReader(System.getProperty("XmonC"),reconfig);
				else
					reader = new XmlReader("Collector.xml",reconfig);
				last = now;
				if(XmlProperty.INFO && !CollectorStarter.getManager().isWork("Resource")){
					MachineResourceThread mrt = new MachineResourceThread();
					MachineInfoThread mit = new MachineInfoThread();
					CollectorStarter.getManager().addWork("Resource",mrt,ModelDic.SEC, SystemDic.PCODE_SEC * XmlProperty.ResourceWorkerSec);
					CollectorStarter.getManager().addWork("ResourceInfo",mit,ModelDic.SEC, SystemDic.PCODE_SEC * XmlProperty.InfoWorkerSec);
				}
				else if(!XmlProperty.INFO && CollectorStarter.getManager().isWork("Resource")){
					CollectorStarter.getManager().removeWork("Resource");
					CollectorStarter.getManager().removeWork("ResourceInfo");
				}
			}
		} catch (Exception e) {
			CommonLogger.getLogger().info(getClass(),"auto reconfig fail",e);				
		}
	}

	@Override
	public void preWork() throws Exception {
		try {
			File file = null;
			if(System.getProperty("XmonC") != null)
				file = new File(System.getProperty("XmonC"));
			else{
				URL url = getClass().getClassLoader().getResource("Collector.xml");
				file = new File(url.toURI().getPath());
			}
			last = file.lastModified();
		} catch (Exception e) {
			CommonLogger.getLogger().info(getClass(),"auto reconfig init fail",e);	
		}
		
	}

}
