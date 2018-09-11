package monCollector.worker.resource;

import java.util.HashMap;

import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

import monCollector.config.XmlProperty;
import yuk.dic.CommandDic;
import yuk.model.MonitorData;
import yuk.model.single.MacData;
import yuk.util.CommonLogger;

public class DiskIO {
	Sigar sigar = new Sigar();

	public void getDiskIo(MacData data) throws Exception{
		try {
			FileSystem[] fileSystemList = sigar.getFileSystemList();
			for (int i = 0; i < fileSystemList.length; i++) {
				FileSystem fs = fileSystemList[i];
				int type = fs.getType();
				if (type == FileSystem.TYPE_LOCAL_DISK || type == FileSystem.TYPE_NETWORK) {
					FileSystemUsage usage = sigar.getFileSystemUsage(fs.getDirName());
					data.controllerList.add(fs.getDirName());
					HashMap<String, Object> dataMap = new HashMap<String, Object>();
					data.dataList.put(fs.getDirName(), dataMap);
					dataMap.put("Read", changeKB(usage.getDiskReadBytes()));
					dataMap.put("Write", changeKB(usage.getDiskWriteBytes()));
					dataMap.put("queue",  usage.getDiskQueue() * 100);
					dataMap.put("usage",  usage.getUsePercent() * 100);
					dataMap.put("total", changeKB(usage.getTotal()));
					dataMap.put("used",  changeKB(usage.getUsed()));
					dataMap.put("avail",  changeKB(usage.getAvail()));
				}
			}
		} catch (SigarException e) {
			CommonLogger.getLogger().warn(getClass(), "can't get diskio data", e);
		}	
	}

	public void work(MonitorData<MacData> mData) {	
		try {
			MacData data = new MacData(XmlProperty.MYNAME, CommandDic.COMMAND_SYSDISK);
			getDiskIo(data);		
			data.name = XmlProperty.MYNAME;
			data.command = CommandDic.COMMAND_SYSDISK;
			mData.dataList.add(data);
		} catch (Exception e) {
			CommonLogger.getLogger().error(getClass(), "can't send diskio data to server", e);
		}
	}
	
	private long changeKB(long value){
		if(value < 0 || value == 0){
			return Math.max(1, value);
		}
		else {
			return value / (1024 * 1024);
		}
	}

}
