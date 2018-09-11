package monCollector.net;

import monCollector.config.XmlProperty;
import yuk.model.MonitorData;
import yuk.network.NetAccepter;
import yuk.util.CommonLogger;

public abstract class NetMap {
	public static CollClient mainServer;
	public static CollClient backServer;
	public static NetAccepter accepter;

	public static <T> void send(MonitorData<T> data) throws Exception {
		try {
			mainServer.send(data);
		} catch (Exception e) {
			CommonLogger.getLogger().warn(NetMap.class,"can't send main server try to back up", e);
		}
		if (XmlProperty.BACKUP)
			backServer.send(data);
	}

	public static void sendInit() throws Exception {
		try {
			mainServer.sendInit();
		} catch (Exception e) {
			CommonLogger.getLogger().warn(NetMap.class,"can't send main server try to back up", e);
		}
		if (XmlProperty.BACKUP)
			backServer.sendInit();
	}
}
