package monServer;

import monServer.net.NetMap;
import monServer.net.stoper.MonApiClient;
import yuk.dataBase.DBController;
import yuk.dic.ModelDic;
import yuk.dic.SystemDic;
import yuk.model.MonitorData;

public class ServerStoper {
	public static void main(String[] args){
		try {
			//for Test
			String ip = "127.0.0.1";
			String port = "2109";
			
			//String ip = args[0];
			//String port = args[1];
			int iport = Integer.parseInt(port);
			MonitorData<Object> object = new MonitorData<Object>(ModelDic.SERVER,SystemDic.MONSHUTDOWN,null);
			MonApiClient client = new MonApiClient();
			client.init(ip, iport,  null);
			client.send(object);
			Thread.currentThread().sleep(5000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.exit(0);
	}
	
	
	public static void stop(){		
		try {
			DBController.stopAll();
			NetMap.accepter.dispose();
			ServerStarter.getManager().stopALL();
			System.out.println("Server closed....");
			Thread.currentThread().sleep(3000);
		} catch (Exception e) {			
			System.out.println("Server close action fail..");
			e.printStackTrace();
		}
		System.exit(0);
	}	
}
