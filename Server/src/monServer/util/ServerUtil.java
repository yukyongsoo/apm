package monServer.util;

import yuk.dic.ModelDic;
import yuk.util.NormalUtil;
import monServer.config.XmlProperty;

public abstract class ServerUtil {
	public static String getLogFolderPath(String name){
		String path = XmlProperty.SAVELOGPATH + "/" + name;
		path = path.replaceAll("//", "/");
		return path;
	}
	
	public static String getLogFilePath(long time, String name) throws Exception{
		String path = getLogFolderPath(name);
		long fileName = NormalUtil.SysmileToDateLong(time, ModelDic.Day);
		path = path + "/" + fileName;
		return path;
	}
	
	public static String getLogFilePath(String name) throws Exception{
		String path = getLogFolderPath(name);
		long fileName = NormalUtil.SysmileToDateLong(System.currentTimeMillis(), ModelDic.Day);
		path = path + "/" + fileName;
		return path;
	}
	
	

}
