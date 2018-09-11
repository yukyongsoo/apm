package monServer.dbAction;

import java.util.Collection;

import yuk.dataBase.DBController;
import yuk.model.etc.EventData;
import yuk.model.single.MacData;
import yuk.model.single.ResourceData;
import yuk.model.single.StackData;
import yuk.model.single.TransactionData;

public abstract class DbActionBase {
	public static void insertEvent(EventData data) throws Exception{	
		String insertQ = "Insert into EVENT (NAME,TEXT,EVENTLEVEL,TIME) VALUES (?,?,?,?)";
		DBController.getDb("Event").WritePrepareSql(insertQ, data.name,data.eventLog,data.eventLevel,data.time);
	}
	
	public static void insertAction(String targetTable,TransactionData data) throws Exception{		
			String insertQ = "INSERT INTO " + targetTable +  " (TIME,NAME,GROUPED,AVGVALUE,SUC,FAIL) VALUES (?,?,?,?,?,?)";
			DBController.getDb("Action").WritePrepareSql(insertQ,data.time,data.name,data.command,data.res,data.suc,data.fail);	
	}
	
	public static void updateAction(String targetTable,TransactionData data,TransactionData target) throws Exception{		
		String updateQ = "UPDATE " + targetTable + " SET AVGVALUE=?,SUC=?,FAIL=? WHERE TIME=? AND NAME=? AND GROUPED=?" ;		
		target.modelAdd(data, true);
		DBController.getDb("Action").WritePrepareSql(updateQ, target.res,target.suc,target.fail,target.time,target.name,target.command);
	}
	
	public static TransactionData getLastTrans(String targetTable,TransactionData data,long rollup) throws Exception {
		String getLast = "SELECT TIME,NAME,GROUPED,SUC,FAIL,AVGVALUE FROM " + targetTable 
				+ " WHERE TIME > ? AND NAME=? AND GROUPED=? ORDER By TIME desc LIMIT 1";
		Collection<TransactionData> mapper = DBController.getDb("Action").ReadPrepareSql(TransactionData.class,getLast, 
				data.time - (rollup * 2), data.name,data.command);
		if(mapper.size() > 0)
			return mapper.iterator().next();
		return null;
	} 
	
	public static void insertRes(String targetTable,ResourceData data) throws Exception{
		String insertQ = "INSERT INTO " + targetTable + " (TIME,NAME,COMMAND,RESOURCEID,META) VALUES (?,?,?,?,?)";
		DBController.getDb("Resource").WritePrepareSql(insertQ, data.time, data.name, data.command, data.Rid,data.map);
	}
	
	public static void updateRes(String targetTable,ResourceData data,ResourceData target) throws Exception{		
		String updateQ = "UPDATE " + targetTable + " SET META=? WHERE TIME=? AND NAME=? AND COMMAND=? AND RESOURCEID=?" ;
		target.modelAdd(data, true);
		DBController.getDb("Resource").WritePrepareSql(updateQ, target.map,target.time,target.name,target.command,target.Rid);
	}
	
	public static ResourceData getLastRes(String targetTable,ResourceData data,long rollup) throws Exception {
		String getLast = "SELECT TIME,NAME,COMMAND,RESOURCEID,META FROM " + targetTable 
				+ " WHERE TIME > ? AND NAME=? AND COMMAND=? AND RESOURCEID=? ORDER BY time desc LIMIT 1";
		Collection<ResourceData> mapper = DBController.getDb("Resource").ReadPrepareSql(ResourceData.class,getLast,
				data.time - (rollup * 2),data.name,data.command,data.Rid);
		if(mapper.size() > 0)
			return mapper.iterator().next();
		return null;
	}
	
	public static void insertInfo(MacData data) throws Exception {
		String insertQ = "INSERT INTO INFO (TIME,NAME,COMMAND,DATA) VALUES (?,?,?,?)";
		DBController.getDb("Info").WritePrepareSql(insertQ, data.time, data.name, data.command, data);
	}
	
	public static void insertStack(StackData data) throws Exception {
		String insertQ = "INSERT INTO STACK (TIME,NAME,DATA) VALUES (?,?,?)";
		DBController.getDb("Stack").WritePrepareSql(insertQ, data.time, data.name, data);
	}	
}
