package monui.impl.net;

import java.util.HashMap;

import monui.impl.config.XmlProperty;
import yuk.dic.ModelDic;
import yuk.model.MonitorData;
import yuk.model.etc.InitData;
import yuk.network.NetClient;

public class UIClient extends NetClient{

	public UIClient() throws Exception {
		super();
	}

	@Override
	public void sendInit() throws Exception {
		InitData data = new InitData(XmlProperty.MYNAME, XmlProperty.TYPE, new HashMap<String, String>());
		MonitorData<InitData> object = new MonitorData<InitData>(ModelDic.SERVER,ModelDic.TYPE_INIT,null);
		object.dataList.add(data);
		send(object);
	}

}
