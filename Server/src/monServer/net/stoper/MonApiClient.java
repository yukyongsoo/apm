package monServer.net.stoper;

import yuk.dic.ModelDic;
import yuk.model.MonitorData;
import yuk.model.etc.InitData;
import yuk.network.NetClient;

/*
 * only for use close server / console */
public class MonApiClient extends NetClient{
	public MonApiClient() throws Exception {
		super();
	}

	@Override
	public void sendInit() throws Exception {
		MonitorData<InitData> object = new MonitorData<InitData>(ModelDic.CONSOLE, ModelDic.TYPE_INIT, null);
		send(object);
	}
}
