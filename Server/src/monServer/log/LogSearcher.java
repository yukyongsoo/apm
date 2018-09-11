package monServer.log;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import monServer.log.LogIndexer.IndexPoint;
import monServer.util.ServerUtil;
import yuk.dic.ModelDic;
import yuk.model.CastHeader;
import yuk.model.MonitorData;
import yuk.model.multi.LogContainer;
import yuk.model.single.LogData;
import yuk.util.CommonLogger;

public class LogSearcher {
	public static MonitorData<LogContainer> getData(CastHeader header, LogData data) {
		MonitorData<LogContainer> mon = new MonitorData<LogContainer>(ModelDic.CONSOLE, ModelDic.TYPE_MULTILOG, header);
		try {
			IndexPoint point = LogIndexer.getIndexer(data.name).getIndexedPoint(header.from,header.to);
			List<LogData> list = getLog(point.start,point.end,header.from,data);
			LogContainer con = new LogContainer();
			con.name = data.name;
			con.list = list;
			mon.dataList.add(con);
		} catch (Exception e) {
			CommonLogger.getLogger().error(LogSearcher.class, "can't get log. error has it.", e);
		}
		return mon;
	}

	private static List<LogData> getLog(long start, long end, long time, LogData data) throws Exception {
		List<LogData> list = new ArrayList<LogData>();
		boolean levelFilter = false;
		boolean textFilter =false;
		boolean bothFilter = false;
		
		String path = ServerUtil.getLogFilePath(time,data.name);
		File file = new File(path);
		if(data.level.length() > 0 )
			levelFilter = true;
		if(data.log.length() > 0)
			textFilter = true;
		if(levelFilter && textFilter)
			bothFilter = true;
		
		if(file.exists()){
			RandomAccessFile raf = new RandomAccessFile(file, "r");
			raf.seek(start);
			String row = "";
			while(raf.getFilePointer() < end && (row= raf.readLine()) != null){
				String temp[] = row.split("[$%^]+");
				try {
					if (bothFilter) {
						if (!filterSearcher(temp[1], data.level) && !filterSearcher(temp[2], data.log))
							continue;
					}
					else if (levelFilter) {
						if (!filterSearcher(temp[1], data.level))
							continue;
					}else if (textFilter) {
						if (!filterSearcher(temp[2], data.log))
							continue;
					}
					LogData log = new LogData(data.name, temp[1], temp[2]);
					log.time = Long.parseLong(temp[0]);
					list.add(log);
				} catch (Exception e) {
					//skip
				}				
			}
			raf.close();
		}
		return list;
	}

	
	
	private static boolean filterSearcher(String target, String filter){
		if(target.indexOf(filter.codePointAt(0)) == -1)
			return false;
		if(!target.contains(filter))
			return false;
		return true;
	}
	
}
