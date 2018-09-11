package monServer.worker.internal;

import java.util.HashMap;
import java.util.Map;

import yuk.Thread.TimerWork;
import yuk.dataBase.DBController;
import yuk.dic.SystemDic;
import yuk.util.CommonLogger;

public class ExpireThread extends TimerWork{
	Map<String, Long> TimeMap = new HashMap<String, Long>();
	long sleepTime = 0;
	
	public ExpireThread(long sleepTime) {
		super();
		this.sleepTime = sleepTime;
		TimeMap.put("min", (long) 0);
		TimeMap.put("hour", (long) 0);
		TimeMap.put("day", (long) 0);
		TimeMap.put("month", (long) 0);
		TimeMap.put("year", (long) 0);
		TimeMap.put("event", (long) 0);
	}
	
	@Override
	public void work() throws Exception {
		try {
			DBController.getDb("Action").WriteSql("TRUNCATE TABLE actionsec");
			DBController.getDb("Resource").WriteSql("TRUNCATE TABLE resourcesec");
			
			if(checkTime("event", TimeMap.get("event"))){
				DBController.getDb("Event").WriteSql("TRUNCATE TABLE event");
				DBController.getDb("Event").reopenDataBase();
			}
			if(checkTime("min", TimeMap.get("min"))){
				DBController.getDb("Action").WriteSql("TRUNCATE TABLE actionmin");
				DBController.getDb("Resource").WriteSql("TRUNCATE TABLE resourcemin");
				DBController.getDb("Action").reopenDataBase();
				DBController.getDb("Resource").reopenDataBase();
			}
			if(checkTime("hour", TimeMap.get("hour"))){
				DBController.getDb("Action").WriteSql( "TRUNCATE TABLE actionhour");
				DBController.getDb("Resource").WriteSql( "TRUNCATE TABLE resourcehour");
				DBController.getDb("Action").reopenDataBase();
				DBController.getDb("Resource").reopenDataBase();
			}
			if(checkTime("day", TimeMap.get("day"))){
				DBController.getDb("Action").WriteSql("TRUNCATE TABLE actionday");
				DBController.getDb("Resource").WriteSql("TRUNCATE TABLE resourceday");
				DBController.getDb("Action").reopenDataBase();
				DBController.getDb("Resource").reopenDataBase();
			}
			if(checkTime("month", TimeMap.get("month"))){
				DBController.getDb("Action").WriteSql("TRUNCATE TABLE actionmonth");
				DBController.getDb("Resource").WriteSql("TRUNCATE TABLE resourcemonth");
				DBController.getDb("Info").WriteSql("TRUNCATE TABLE info");
				DBController.getDb("Stack").WriteSql("TRUNCATE TABLE stack");
				DBController.getDb("Action").reopenDataBase();
				DBController.getDb("Resource").reopenDataBase();
				DBController.getDb("Info").reopenDataBase();
				DBController.getDb("Stack").reopenDataBase();
			}
			if(checkTime("year", TimeMap.get("year"))){
				DBController.getDb("Action").WriteSql("TRUNCATE TABLE actionyear");
				DBController.getDb("Resource").WriteSql("TRUNCATE TABLE resourceyear");
				DBController.getDb("Action").reopenDataBase();
				DBController.getDb("Resource").reopenDataBase();
			}

		} catch (Exception e) {
			CommonLogger.getLogger().error(getClass(), "can't delete expire data", e);
		} 
	}
	
	@Override
	public void preWork() throws Exception {
		
	}

	private boolean checkTime(String key , long value){
		boolean check = false;
		if(key.equals("min")){
			 if(value == (SystemDic.PCODE_Hour * 48)){
				 TimeMap.put("min", (long) 0);
				 return true;
			 }
			 else{
				 TimeMap.put("min", value + sleepTime);
				 return false;
			 }
		}		
		else if(key.equals("hour")){
			 if(value == (SystemDic.PCODE_Day * 30)){
				 TimeMap.put("hour", (long) 0);
				 return true;
			 }
			 else{
				 TimeMap.put("hour", value + sleepTime);
				 return false;
			 }
		}
		else if(key.equals("day")){
			 if(value == (SystemDic.PCODE_MONTH)){
				 TimeMap.put("day", (long) 0);
				 return true;
			 }
			 else{
				 TimeMap.put("day", value + sleepTime);
				 return false;
			 }
		}
		else if(key.equals("month")){
			if(value == (SystemDic.PCODE_YEAR * 3)){
				 TimeMap.put("month", (long) 0);
				 return true;
			 }
			 else{
				 TimeMap.put("month", value + sleepTime);
				 return false;
			 }
		}
		else if(key.equals("year")){
			if(value == (SystemDic.PCODE_YEAR * 10)){
				 TimeMap.put("year", (long) 0);
				 return true;
			 }
			 else{
				 TimeMap.put("year", value + sleepTime);
				 return false;
			 }
		}
		else if(key.equals("event")){
			if(value == (SystemDic.PCODE_Day * 30)){
				TimeMap.put("event", (long) 0);
				 return true;
			 }
			 else{
				 TimeMap.put("event", value + sleepTime);
				 return false;
			 }
			 
		}
		return check;
	}
}
