package agent.agentC.tracer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import agent.agentC.Agent;
import agent.agentC.config.XmlProperty;
import yuk.model.etc.RowTransactionData;
import yuk.model.multi.DistributionContainer;
import yuk.util.CommonLogger;

public abstract class MainTracer {
	//total mapper
	private static Map<String, RowTransactionData> transMap = new ConcurrentHashMap<String, RowTransactionData>();
	//dis mapper
	private static Map<String, DistributionContainer> disMap = new ConcurrentHashMap<String, DistributionContainer>();
	
	public static Map<String, RowTransactionData> getTransMap() {
		return transMap;
	}
	
	public static Map<String, DistributionContainer> getDisMap() {
		return disMap;
	}
	
	public static void initTrans(String id, String group){
		try {
			RowTransactionData actionData = new RowTransactionData();
			actionData.tid = id;
			actionData.command = group;
			actionData.time = System.currentTimeMillis();
			transMap.put(id, actionData);
		} catch (Exception e) {
			CommonLogger.getLogger().error(Agent.class, "can't catch init transaction", e);
		}
	}

	public static void endTrans(String id, boolean result){
		try {
			RowTransactionData actionData = transMap.get(id);
			if(actionData != null){
				actionData.res = System.currentTimeMillis() - actionData.time;
				actionData.result = result;		
				actionData.end = true;
				DistributionContainer con = disMap.get(actionData.command);
				if(con == null){
					con = new DistributionContainer();
					con.name = XmlProperty.MYNAME;
					con.command = actionData.command;
					disMap.put(con.command, con);
				}
				con.addData(actionData);
			}			
		} catch (Exception e) {
			CommonLogger.getLogger().error(Agent.class, "can't catch end transaction", e);
		}
	}
}
