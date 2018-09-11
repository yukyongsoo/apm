package monServer.log;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.Collection;

import monServer.config.XmlProperty;
import monServer.util.ServerUtil;
import yuk.dic.ModelDic;
import yuk.dic.SystemDic;
import yuk.model.multi.LogContainer;
import yuk.model.single.LogData;
import yuk.util.CommonLogger;
import yuk.util.NormalUtil;

public class LogMaker {
	public static String lineFeed= System.getProperty("line.separator");
	
	public void makeLog(Collection<LogContainer> log) {
		try {
			long current = NormalUtil.SysmileToDateLong(System.currentTimeMillis(), ModelDic.Day);
			for(LogContainer con : log){
				overDayFileDelete(ServerUtil.getLogFolderPath(con.name),current);
				makeFolder(ServerUtil.getLogFolderPath(con.name));
				RandomAccessFile file = new RandomAccessFile(ServerUtil.getLogFilePath(con.name), "rw");
				file.seek(file.length());
				for(LogData data : con.list){
					LogIndexer indexer = LogIndexer.getIndexer(data.name);
					String type = data.time + "$%^" + data.level + "$%^" + data.log;
					file.write(lineFeed.getBytes());
					indexer.indexing(data.time,file.getFilePointer());
					file.write(type.getBytes());
				}				
				file.close();
			}
		} catch (Exception e) {
			CommonLogger.getLogger().error(getClass(), "make log file fail", e);
		}
	}
	
	private void makeFolder(String path) throws Exception{
		File file = new File(path);
		if(!file.exists()){
		    file.mkdirs();
		}
		else if(!file.isDirectory()){
			throw new Exception("already file exist. but not directory");
		}		
	}
	
	private void overDayFileDelete(String path, long current){
		long limit = XmlProperty.REMAINLOGDAY * SystemDic.PCODE_Day;
		File file = new File(path);
		File[] logFiles = file.listFiles();
		if(logFiles != null){
			for (File logFile : logFiles) {
				String name = logFile.getName().replaceAll("_index", "");
				try {
					long time = Long.parseLong(name);
					if (current - time > limit) {
						logFile.delete();
						File indexFile = new File(name + "_index");
						indexFile.delete();
					}
				} catch (NumberFormatException e) {
					CommonLogger.getLogger().info(getClass(),"not mon log file." + name, null);
				}
			}		
		}
	}
	
}
