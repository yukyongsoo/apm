package yuk.parser;

import java.util.Collection;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.netty.channel.Channel;
import yuk.dic.ModelDic;
import yuk.model.CastHeader;
import yuk.model.etc.EventData;
import yuk.model.etc.InitData;
import yuk.model.etc.RowTransactionData;
import yuk.model.etc.ServerDBData;
import yuk.model.etc.SessionData;
import yuk.model.multi.DBSpotContainer;
import yuk.model.multi.DistributionContainer;
import yuk.model.multi.ExcptContainer;
import yuk.model.multi.LogContainer;
import yuk.model.multi.ResourceContainer;
import yuk.model.multi.ThreadContainer;
import yuk.model.multi.TransContainer;
import yuk.model.single.DBHotSpotData;
import yuk.model.single.DistributionData;
import yuk.model.single.ExceptionData;
import yuk.model.single.HealthData;
import yuk.model.single.LogData;
import yuk.model.single.MacData;
import yuk.model.single.ResourceData;
import yuk.model.single.StackData;
import yuk.model.single.ThreadHotSpotData;
import yuk.model.single.TransactionData;
import yuk.network.session.SessionWaraper;
import yuk.parser.inter.CommandInterface;
import yuk.util.CommonLogger;

public abstract class DataParser {
	private static JsonParser jsonParser = new JsonParser();
	Gson gson = new Gson();
	private CommandInterface agentInterface;
	private CommandInterface collectorInterface;
	private CommandInterface serverInterface;
	private CommandInterface consoleInterface;
	private CommandInterface etcInterface;

	public CommandInterface getAgentInterface() {
		return agentInterface;
	}

	public CommandInterface getCollectorInterface() {
		return collectorInterface;
	}

	public CommandInterface getServerInterface() {
		return serverInterface;
	}

	public CommandInterface getConsoleInterface() {
		return consoleInterface;
	}

	public CommandInterface getEtcInterface() {
		return etcInterface;
	}

	public DataParser(CommandInterface agent, CommandInterface collector, CommandInterface server,
			CommandInterface console, CommandInterface etc) {
		this.agentInterface = agent;
		this.collectorInterface = collector;
		this.serverInterface = server;
		this.consoleInterface = console;
		this.etcInterface = etc;
	}

	public DataParser parse(String sJsonData, Channel session) throws Exception {
		JsonObject jsonObject = jsonParser.parse(sJsonData).getAsJsonObject();
		String target = jsonObject.get("target").getAsString();
		int type = jsonObject.get("type").getAsInt();
		CastHeader header = null;
		if (jsonObject.get("castHeader") != null) {
			JsonObject castHeader = jsonObject.get("castHeader").getAsJsonObject();
			header = gson.fromJson(castHeader, CastHeader.class);
		}
		JsonArray dataString = jsonObject.get("dataList").getAsJsonArray();
		SessionWaraper waraper = SessionWaraper.getSession(session);
		targetSelector(target, type, header, dataString, waraper);
		return this;
	}

	private void targetSelector(String target, int type, CastHeader castHeader, JsonArray dataString,
			SessionWaraper waraper) throws Exception {
		if (target.equals(ModelDic.SERVER))
			parse(serverInterface, type, castHeader, dataString, waraper);
		else if (target.equals(ModelDic.COLLECTOR))
			parse(collectorInterface, type, castHeader, dataString, waraper);
		else if (target.equals(ModelDic.AGENT))
			parse(agentInterface, type, castHeader, dataString, waraper);
		else if (target.equals(ModelDic.CONSOLE))
			parse(consoleInterface, type, castHeader, dataString, waraper);
		else
			parse(etcInterface, type, castHeader, dataString, waraper);
	}

	private void parse(CommandInterface targetInterface, int type,CastHeader castHeader,JsonArray dataString,  SessionWaraper session) {
		try {
			switch (type) {
				//etc
				case ModelDic.TYPE_EVENT:
					Collection<EventData> eventSet = gson.fromJson(dataString, ModelDic.EVENTTYPE); 
					targetInterface.event(castHeader, eventSet, session);
				break;
				case ModelDic.TYPE_INIT:
					Collection<InitData> initSet = gson.fromJson(dataString, ModelDic.INITTYPE); 
					targetInterface.init(castHeader,initSet,session);
				break;
				case ModelDic.TYPE_ROWDATA:
					Collection<RowTransactionData> rowSet = gson.fromJson(dataString, ModelDic.ROWTYPE); 
					targetInterface.rowTrans(castHeader, rowSet, session);
				break;
				case ModelDic.TYPE_DB:
					Collection<ServerDBData> dbSet = gson.fromJson(dataString, ModelDic.DBTYPE); 
					targetInterface.db(castHeader, dbSet, session);
				break;
				case ModelDic.TYPE_SESSION:
					Collection<SessionData> sessionSet = gson.fromJson(dataString, ModelDic.SESSIONTYPE); 
					targetInterface.session(castHeader,sessionSet, session);
				break;
				//single
				case ModelDic.TYPE_DBHOTSPOT:
					Collection<DBHotSpotData> dbhotSet = gson.fromJson(dataString, ModelDic.DBSPOTTYPE); 
					targetInterface.dbHotSpot(castHeader, dbhotSet, session);
				break;
				case ModelDic.TYPE_EXCPT:
					Collection<ExceptionData> excptSet = gson.fromJson(dataString, ModelDic.EXCPTTYPE); 
					targetInterface.excpt(castHeader,excptSet, session);
				break;
				case ModelDic.TYPE_HEALTH:
					Collection<HealthData> healthSet = gson.fromJson(dataString, ModelDic.HEALTHTYPE); 
					targetInterface.health(castHeader, healthSet, session);
				break;
				case ModelDic.TYPE_LOG:
					Collection<LogData> logSet = gson.fromJson(dataString, ModelDic.LOGTYPE); 
					targetInterface.log(castHeader, logSet, session);
				break;
				case ModelDic.TYPE_MAC :
					Collection<MacData> macSet = gson.fromJson(dataString, ModelDic.MACTYPE); 
					targetInterface.mac(castHeader,macSet, session);
				break;
				case ModelDic.TYPE_RESOURCE:
					Collection<ResourceData> resoSet = gson.fromJson(dataString, ModelDic.RESTYPE); 
					targetInterface.resource(castHeader, resoSet, session);
				break;
				case ModelDic.TYPE_STACK:
					Collection<StackData> stackSet = gson.fromJson(dataString, ModelDic.STACKTYPE); 
					targetInterface.statck(castHeader,stackSet, session);
				break;
				case ModelDic.TYPE_HOTSPOT:
					Collection<ThreadHotSpotData> hotSet = gson.fromJson(dataString, ModelDic.SPOTTYPE); 
					targetInterface.hotspot(castHeader,hotSet, session);
				break;
				case ModelDic.TYPE_TRANS:
					Collection<TransactionData> transSet = gson.fromJson(dataString, ModelDic.TRANSTYPE); 
					targetInterface.trans(castHeader,transSet,session);
				break;
				case ModelDic.TYPE_DIS:
					Collection<DistributionData> disSet = gson.fromJson(dataString, ModelDic.DISTYPE); 
					targetInterface.dis(castHeader,disSet,session);
				//multi
				case ModelDic.TYPE_MULTIDBHOT:
					Collection<DBSpotContainer> mDBSet = gson.fromJson(dataString, ModelDic.M_DBSPOTTYPE); 
					targetInterface.multiDBHotSpot(castHeader, mDBSet, session);
				break;
				case ModelDic.TYPE_MULTIEXCPT:
					Collection<ExcptContainer> mExcptSet = gson.fromJson(dataString, ModelDic.M_EXCPTTYPE); 
					targetInterface.multiExcptSpot(castHeader, mExcptSet, session);
				break;
				case ModelDic.TYPE_MULTILOG:
					Collection<LogContainer> mLogSet = gson.fromJson(dataString, ModelDic.M_LOGTYPE); 
					targetInterface.multiLog(castHeader, mLogSet, session);
				break;
				case ModelDic.TYPE_MULTIRESO:
					Collection<ResourceContainer> mResoSet = gson.fromJson(dataString, ModelDic.M_RESTYPE); 
					targetInterface.multiResource(castHeader, mResoSet, session);
				break;
				case ModelDic.TYPE_MULTIHOTSPOT:
					Collection<ThreadContainer> mThreadSet = gson.fromJson(dataString, ModelDic.M_SPOTTYPE); 
					targetInterface.multiHotSpot(castHeader, mThreadSet, session);
				break;
				case ModelDic.TYPE_MULTITRANS:
					Collection<TransContainer> mTransSet = gson.fromJson(dataString, ModelDic.M_TRANSTYPE); 
					targetInterface.multiTrans(castHeader, mTransSet, session);
				break;
				case ModelDic.TYPE_MULTIDIS:
					Collection<DistributionContainer> mDisSet = gson.fromJson(dataString, ModelDic.M_DISTYPE); 
					targetInterface.multiDis(castHeader, mDisSet, session);
				break;
				//etc
				default:
					targetInterface.etc(castHeader,dataString,session);
				break;
			}
		}catch (Exception e) {
			CommonLogger.getLogger().error(getClass(), "parser has error.check your bis logic", e);
		}
	}

}
