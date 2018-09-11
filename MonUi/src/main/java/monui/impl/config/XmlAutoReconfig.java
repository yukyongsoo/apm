package monui.impl.config;

import java.io.File;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import monui.impl.util.UiUtil;
import yuk.Thread.TimerManager;
import yuk.Thread.TimerWork;
import yuk.dic.ModelDic;
import yuk.dic.SystemDic;
import yuk.util.CommonLogger;

public class XmlAutoReconfig extends TimerWork {
	private static TimerManager globalTimer;

	public synchronized static void init() {
		try {
			if (globalTimer == null) {
				String path = UiUtil.getBasePath();
				System.out.println("Configration of logger." + path);
				CommonLogger.getLogger().debug(XmlAutoReconfig.class, "YukMonitoring is inited", null);
				globalTimer = new TimerManager();
				XmlReader reader = null;
				if (System.getProperty("XmonU") != null)
					reader = new XmlReader(System.getProperty("XmonU"), false);
				else
					reader = new XmlReader(path + "/WEB-INF/XMUI.xml", false);
				XmlAutoReconfig autoReconfig = new XmlAutoReconfig(path);
				globalTimer.addWork("GlobalAutoReconfig", autoReconfig, ModelDic.Min, SystemDic.PCODE_MIN);
			}
		} catch (Exception e1) {			
			CommonLogger.getLogger().error(XmlAutoReconfig.class, "parse error. check you xml", e1);
		}
	}

	boolean reconfig = true;
	long last;
	String path = "";

	public XmlAutoReconfig(String path) {
		super();
		this.path = path;		
	}

	@Override
	public void work() throws Exception {
		try {
			File file = null;
			if (System.getProperty("XmonU") != null)
				file = new File(System.getProperty("XmonU"));
			else {
				file = new File(path + "/WEB-INF/XMUI.xml");
			}
			long now = file.lastModified();

			XmlReader reader = null;
			if (last != now) {
				if (System.getProperty("XmonU") != null)
					reader = new XmlReader(System.getProperty("XmonU"), reconfig);
				else
					reader = new XmlReader(path + "/WEB-INF/XMUI.xml", reconfig);
				last = now;

			}
		} catch (Exception e) {
			CommonLogger.getLogger().info(getClass(), "auto reconfig fail", e);
		}
	}

	@Override
	public void preWork() throws Exception {
		try {
			File file = null;
			if (System.getProperty("XmonU") != null)
				file = new File(System.getProperty("XmonU"));
			else {
				file = new File(path + "/WEB-INF/XMUI.xml");
			}
			last = file.lastModified();
		} catch (Exception e) {
			CommonLogger.getLogger().info(getClass(), "auto reconfig init fail", e);
		}
	}

}
