package monServer.net;

import java.util.HashMap;
import java.util.Map;

import monServer.config.XmlProperty;
import yuk.dic.ModelDic;
import yuk.dic.SystemDic;
import yuk.model.etc.InitData;
import yuk.network.NetAccepter;

public abstract class NetMap {
	public static NetAccepter accepter;
	
	public static void setServerInitData() throws Exception{
		InitData data = new InitData(XmlProperty.MYNAME,  ModelDic.SERVER, new HashMap<String, String>());
		
		Map<String, Object> map = new HashMap<String, Object>();
		data.settingData.put(SystemDic.PROPERTY, map);
		map.put("MyName", XmlProperty.MYNAME);
		map.put("Type", XmlProperty.TYPE);
		map.put("desc", XmlProperty.DESC);
		map.put("serverIp", XmlProperty.SERVERIP);
		map.put("serverPort", XmlProperty.SERVERPORT);
		map.put("machineInfo", XmlProperty.EXPIRESCH);
		map.put("expire", XmlProperty.AGGRERES);
		map.put("trace", XmlProperty.TRACE);
		
		accepter.getManager().addChild(XmlProperty.MYNAME,data);
	}
}
