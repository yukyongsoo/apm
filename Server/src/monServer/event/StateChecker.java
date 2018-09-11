package monServer.event;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import monServer.aggre.AggreInMem;
import yuk.dic.CommandDic;
import yuk.dic.ModelDic;
import yuk.model.etc.EventData;
import yuk.model.etc.SubResourceData;
import yuk.model.single.ExceptionData;
import yuk.model.single.MacData;
import yuk.model.single.ResourceData;
import yuk.model.single.TransactionData;

public abstract class StateChecker {
	public static void CheckTrans(Collection<TransactionData> dataSet) {
		for(TransactionData data : dataSet){
			if(data.fail > 20){
				makeEventData(data.name,"transaction " +  data.name + " has too many " + data.command + " fail. size is" + data.fail);
			}
			if(data.res > 1000){
				makeEventData(data.name,"transaction " + data.name + " has too high " + data.command + " response. size is" + data.res);
			}
		}
	}

	public static void CheckRes(Collection<ResourceData> dataSet) {
		for(ResourceData data : dataSet){
			if(data.command.equals(CommandDic.COMMAND_SYSCPU)){
				SubResourceData total = data.map.get(ModelDic.TOTAL);
				if(total.value > 90)
					makeEventData(data.name, data.name + " SYSTEM CPU is Too high." + total.value +"%");
			}
			else if(data.command.equals(CommandDic.COMMAND_SYSMEM)){
				SubResourceData total = data.map.get(ModelDic.TOTAL);
				SubResourceData used = data.map.get(ModelDic.USED);
				double value = ((double)used.value / (double)total.value) * 100;
				if(value > 90)
					makeEventData(data.name, data.name + " SYSTEM Memory is Too high." + value+"%");
			}
			else if(data.command.equals(CommandDic.COMMAND_MEM)){
				SubResourceData total = data.map.get(ModelDic.TOTAL);
				SubResourceData used = data.map.get(ModelDic.USED);
				double value = ((double)used.value / (double)total.value) * 100;
				if(value > 90 && used.value > 256)
					makeEventData(data.name, data.name + " App Memory is Too high." + value +"%" );
			}
		}
	}

	public static void CheckExcpt(Collection<ExceptionData> dataSet) {
		Map<String, Long> counter = new HashMap<String, Long>();
		for(ExceptionData data : dataSet){
			Long count = counter.get(data.name);
			if(count == null)
				counter.put(data.name, data.count);
			else
				count += data.count;
		}
		for(String agent : counter.keySet()){
			Long count = counter.get(agent);
			if(count > 10)
				makeEventData(agent, "one of your agent :" + agent + "has many Exception.size is " + count);
		}
	}
	
	
	

	public static void CheckInfo(Collection<MacData> dataSet) {
		for(MacData data : dataSet){
			if(data.command.equals(CommandDic.COMMAND_SYSDISK)){
				for(String name : data.dataList.keySet()){
					Map<String, Object> map = data.dataList.get(name);
					Double queue = (Double) map.get("queue");
					if(queue > 150)
						makeEventData(data.name, name + " in " + data.name +  " has too high Disk I/O. size is" + queue +"%");
				}
			}
			else if(data.command.equals(CommandDic.COMMAND_SYSNET)){
				for(String name : data.dataList.keySet()){
					if(!name.equals("Gen")){
						Map<String, Object> map = data.dataList.get(name);
						Double read = (Double) map.get("Read");
						Double write = (Double) map.get("Write");
						Double speed = (Double) map.get("speed");
						double value = (read + write) / speed;
						if (value > 90)
							makeEventData(data.name, name + " in " + data.name + " has too high Network I/O.size is" + value +"%");		
					}
				}
			}	
		}		
	}
	
	private static void makeEventData(String name,String message){
		EventData data = new EventData(name, ModelDic.EVENTLEVEL_WARN, message);
		AggreInMem.updateEvent(data);
	}
}
