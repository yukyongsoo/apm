package monServer.dbAction;

import java.util.Collection;
import java.util.List;

import monServer.ServerStarter;
import monServer.log.LogSearcher;
import monServer.net.NetMap;
import monServer.worker.internal.HealthChecker;
import yuk.dataBase.DBController;
import yuk.dic.ModelDic;
import yuk.dic.MsgDic;
import yuk.dic.SystemDic;
import yuk.model.CastHeader;
import yuk.model.MonitorData;
import yuk.model.etc.EventData;
import yuk.model.etc.InitData;
import yuk.model.etc.ServerDBData;
import yuk.model.multi.AbsContainer;
import yuk.model.multi.LogContainer;
import yuk.model.multi.ResourceContainer;
import yuk.model.multi.TransContainer;
import yuk.model.single.HealthData;
import yuk.model.single.LogData;
import yuk.model.single.MacData;
import yuk.model.single.ResourceData;
import yuk.model.single.StackData;
import yuk.model.single.TransactionData;
import yuk.network.SendUtil;
import yuk.network.session.SessionWaraper;
import yuk.util.CommonLogger;

public abstract class DbActionSender {
	public static void sendInit(SessionWaraper session,Collection<InitData> dataSet, CastHeader header) {
		MonitorData<InitData> object = new MonitorData<InitData>(ModelDic.CONSOLE, ModelDic.TYPE_INIT, header);
		try {
			List<InitData> list = NetMap.accepter.getManager().getAllChild();
			object.dataList = list;
			sendData(object,header,session);
		} catch (Exception e) {
			CommonLogger.getLogger().error(DbActionSender.class,"can't send to console control data that need to be inited",e);
			sendingError(object,header, session, e);
		}
	}

	public static void sendLog(CastHeader header, Collection<LogData> dataSet,SessionWaraper session) {
		MonitorData<LogContainer> object;
		try {
			LogData data = dataSet.iterator().next();
			object = LogSearcher.getData(header, data);
			sendData(object,header, session);
		} catch (Exception e) {
			CommonLogger.getLogger().error(DbActionSender.class,"can't send to console control data that need to be log",e);
			object = new MonitorData<LogContainer>(ModelDic.CONSOLE,ModelDic.TYPE_MULTILOG, header);
			sendingError(object,header, session, e);
		}
	}

	public static void sendHealth(CastHeader header, SessionWaraper session) {
		MonitorData<HealthData> object = new MonitorData<HealthData>(ModelDic.CONSOLE, ModelDic.TYPE_HEALTH, null);
		try {
			HealthChecker hct = ServerStarter.getManager().getWork("HC",HealthChecker.class);
			Collection<HealthData> list = hct.healthMap.values();
			object.dataList.addAll(list);
			sendData(object,header, session);
		} catch (Exception e) {
			CommonLogger.getLogger().error(DbActionSender.class,"can't send to console control data that need to be health",e);
			sendingError(object,header, session, e);
		}
	}

	public static void sendEvent(CastHeader header, Collection<EventData> dataSet, SessionWaraper session) {
		MonitorData<EventData> object = new MonitorData<EventData>(ModelDic.CONSOLE, ModelDic.TYPE_EVENT, null);
		try {
			Collection<EventData> dbMapper = null;
			EventData data = dataSet.iterator().next();
			long start = header.from;
			long end = header.to;
			if(data.eventLevel != null && data.eventLevel.length() > 0){
				String query = "select TIME,NAME,EVENTLEVEL,TEXT from event where time > ? and time < ? and name = ? and  EVENTLEVEL = ? order by time asc";
				dbMapper = DBController.getDb("Event").ReadPrepareSql(EventData.class, query, start, end, data.name, data.eventLevel);
			}
			String query = "select TIME,NAME,EVENTLEVEL,TEXT from event where time > ? and time < ? and name = ? order by time asc";
			dbMapper = DBController.getDb("Event").ReadPrepareSql(EventData.class, query, start, end, data.name);
			object.dataList.addAll(dbMapper);
			sendData(object,header, session);
		} catch (Exception e) {
			CommonLogger.getLogger().error(DbActionSender.class,"can't send to console event that need to be user", e);
			sendingError(object,header, session, e);
		}
	}

	public static void sendStack(SessionWaraper session,Collection<StackData> dataSet, CastHeader header) {
		MonitorData<StackData> object = new MonitorData<StackData>(ModelDic.CONSOLE, ModelDic.TYPE_STACK, header);
		try {
			StackData stack = dataSet.iterator().next();
			String query = "select a.TIME,a.NAME,a.DATA from Stack a,"
						+ "(select max(time) time from Stack c where c.time > ? and c.time < ?) b"
						+ " where a.time > ? and a.time=b.time and a.name=? order by a.time desc";
			long start = header.from - (SystemDic.PCODE_MIN * 5);
			long end = header.from;
			Collection<StackData> dbMapper = DBController.getDb("Stack").ReadPrepareSql(StackData.class, query, start, end,start, stack.name);
			object.dataList.addAll(dbMapper);
			sendData(object,header, session);
		} catch (Exception e) {
			CommonLogger.getLogger().error(DbActionSender.class,"can't send to console stack that need to be user", e);
			sendingError(object,header, session, e);
		}

	}

	public static void sendInfo(SessionWaraper session,Collection<MacData> dataSet, CastHeader header) {
		MonitorData<MacData> object = new MonitorData<MacData>(ModelDic.CONSOLE, ModelDic.TYPE_MAC, header);
		try {
			MacData mac = dataSet.iterator().next();
			String query = "select a.TIME,a.NAME,a.COMMAND,a.DATA from info a, "
					+ " (select max(time) time,name from info c where c.time > ? and c.time < ? group by name,command) b"
					+ " where a.time > ? and a.time=b.time and a.Name=? order by a.time desc";
			long start = header.from - (SystemDic.PCODE_MIN * 5);
			long end = header.from;
			Collection<MacData> dbMapper = DBController.getDb("Info").ReadPrepareSql(MacData.class, query, start,end, start, mac.name);
			object.dataList.addAll(dbMapper);
			sendData(object, header, session);
		} catch (Exception e) {
			CommonLogger.getLogger().error(DbActionSender.class,"can't send to console info that need to be user", e);
			sendingError(object,header, session, e);
		}

	}

	public static void sendDB(SessionWaraper session,Collection<ServerDBData> dataSet, CastHeader header) {
		MonitorData<ServerDBData> object = new MonitorData<ServerDBData>(ModelDic.CONSOLE, ModelDic.TYPE_DB, header);
		try {
			ServerDBData data = dataSet.iterator().next();
			Collection<ServerDBData> dbMapper = DBController.getDb(data.dbType).ReadSql(ServerDBData.class, data.query);
			object.dataList.addAll(dbMapper);
			sendData(object,header, session);
		} catch (Exception e) {
			CommonLogger.getLogger().error(DbActionSender.class,"can't send to console dbdata that need to be user",e);
			sendingError(object,header, session, e);
		}
	}

	public static void sendTrans(SessionWaraper session,Collection<TransactionData> dataSet, CastHeader header) {
		MonitorData<TransContainer> object = new MonitorData<TransContainer>(ModelDic.CONSOLE, ModelDic.TYPE_MULTITRANS, header);
		try {
			TransactionData data = dataSet.iterator().next();
			String query = "select TIME,NAME,GROUPED,AVGVALUE,SUC,FAIL from Action"+ header.range
					+ " where name=? and time between ? and ? order by time asc";
			Collection<TransContainer> mapper = DBController.getDb("Action").ReadPrepareSql(TransContainer.class, query, data.name,header.from, header.to);
			object.dataList.addAll(mapper);
			sendData(object,header, session);
		} catch (Exception e) {
			CommonLogger.getLogger().error(DbActionSender.class,"can't send to console control data that need to be transaction",e);
			sendingError(object,header, session, e);
		}
	}

	public static void sendRes(SessionWaraper session,Collection<ResourceData> dataSet, CastHeader header) {
		MonitorData<ResourceContainer> object = new MonitorData<ResourceContainer>(ModelDic.CONSOLE, ModelDic.TYPE_MULTIRESO, header);
		try {
			ResourceData data = dataSet.iterator().next();
			String query = "select NAME,COMMAND,RESOURCEID,TIME,META from Resource"+ header.range
					+ " where NAME=? and command = ? and time between ? and ? order by time asc ";
			Collection<ResourceContainer> mapper = DBController.getDb("Resource").ReadPrepareSql(ResourceContainer.class, query,
																				data.name, data.command, header.from, header.to);
			object.dataList.addAll(mapper);
			sendData(object,header, session);
		} catch (Exception e) {
			CommonLogger.getLogger().error(DbActionSender.class,"can't send to console control data that need to be resource",e);
			sendingError(object,header, session, e);
		}
	}

	private static void sendData(MonitorData<?> object,CastHeader header, SessionWaraper session) throws Exception {
		object.castHeader = header;
		if(9 < object.type && object.type < 20){
			if(!checkContainer((MonitorData<? extends AbsContainer>) object)){
				header.code = MsgDic.nodata;
				header.msg = MsgDic.NODATA;
			}				
		}		
		else if (object.dataList.size() < 1) {
			header.code = MsgDic.nodata;
			header.msg = MsgDic.NODATA;
		}		
		SendUtil.sendData(session, object);
	}
	
	private static boolean checkContainer(MonitorData<? extends AbsContainer> object) {
		boolean check = false;
		for(AbsContainer con : object.dataList){
			long size = con.getSize();
			if(size > 0)
				check = true;
		}
		return check;
	}

	private static void sendingError(MonitorData<?> object,CastHeader header,SessionWaraper session, Exception error) {
		try {
			header.code = MsgDic.intererror;
			header.msg = error.getMessage();
			SendUtil.sendData(session, object);
		} catch (Exception e) {
			CommonLogger.getLogger().error(DbActionSender.class,"sending error data fail", e);
		}
	}
}
