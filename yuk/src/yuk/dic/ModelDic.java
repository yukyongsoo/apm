package yuk.dic;

import java.lang.reflect.Type;
import java.util.Collection;

import com.google.gson.reflect.TypeToken;

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

public abstract class ModelDic {
	//etc
	public static final int TYPE_EVENT = 1;
	public static final Type EVENTTYPE = new TypeToken<Collection<EventData>>(){}.getType();
	public static final int TYPE_INIT = 2;
	public static final Type INITTYPE = new TypeToken<Collection<InitData>>(){}.getType();
	public static final int TYPE_ROWDATA = 3;
	public static final Type ROWTYPE = new TypeToken<Collection<RowTransactionData>>(){}.getType();
	public static final int TYPE_DB = 4;
	public static final Type DBTYPE = new TypeToken<Collection<ServerDBData>>(){}.getType(); 
	public static final int TYPE_SESSION = 5;
	public static final Type SESSIONTYPE = new TypeToken<Collection<SessionData>>(){}.getType();
	//multi
	public static final int TYPE_MULTIDBHOT = 10;
	public static final Type M_DBSPOTTYPE = new TypeToken<Collection<DBSpotContainer>>(){}.getType();
	public static final int TYPE_MULTIEXCPT = 11;
	public static final Type M_EXCPTTYPE = new TypeToken<Collection<ExcptContainer>>(){}.getType();
	public static final int TYPE_MULTILOG = 12;
	public static final Type M_LOGTYPE = new TypeToken<Collection<LogContainer>>(){}.getType();
	public static final int TYPE_MULTIRESO = 14;
	public static final Type M_RESTYPE = new TypeToken<Collection<ResourceContainer>>(){}.getType();
	public static final int TYPE_MULTIHOTSPOT = 15;
	public static final Type M_SPOTTYPE = new TypeToken<Collection<ThreadContainer>>(){}.getType();
	public static final int TYPE_MULTITRANS = 16;
	public static final Type M_TRANSTYPE = new TypeToken<Collection<TransContainer>>(){}.getType();
	public static final int TYPE_MULTIDIS = 17;
	public static final Type M_DISTYPE = new TypeToken<Collection<DistributionContainer>>(){}.getType();
	//single
	public static final int TYPE_DBHOTSPOT = 20;
	public static final Type DBSPOTTYPE = new TypeToken<Collection<DBHotSpotData>>(){}.getType();
	public static final int TYPE_EXCPT = 21;
	public static final Type EXCPTTYPE = new TypeToken<Collection<ExceptionData>>(){}.getType();
	public static final int TYPE_HEALTH= 22;
	public static final Type HEALTHTYPE = new TypeToken<Collection<HealthData>>(){}.getType();
	public static final int TYPE_LOG = 23;
	public static final Type LOGTYPE = new TypeToken<Collection<LogData>>(){}.getType();
	public static final int TYPE_MAC = 24;
	public static final Type MACTYPE = new TypeToken<Collection<MacData>>(){}.getType();
	public static final int TYPE_RESOURCE= 25;	
	public static final Type RESTYPE = new TypeToken<Collection<ResourceData>>(){}.getType();
	public static final int TYPE_STACK = 26;
	public static final Type STACKTYPE = new TypeToken<Collection<StackData>>(){}.getType();	
	public static final int TYPE_HOTSPOT = 27;
	public static final Type SPOTTYPE = new TypeToken<Collection<ThreadHotSpotData>>(){}.getType();
	public static final int TYPE_TRANS = 28;
	public static final Type TRANSTYPE = new TypeToken<Collection<TransactionData>>(){}.getType();
	public static final int TYPE_DIS = 29;
	public static final Type DISTYPE = new TypeToken<Collection<DistributionData>>(){}.getType();
	//period
	public static final String SEC = "Sec";
	public static final String Min = "Min";
	public static final String Hour = "Hour";
	public static final String Day = "Day";
	public static final String Month = "Month";
	//transType
	public static final String SUC = "Success";
	public static final String ERR = "Error";
	//property
	public static final String REMAIN= "Remain";
	public static final String USED = "Used";
	public static final String TOTAL = "Total";
	public static final String WRITE = "Write";
	public static final String READ  = "Read";
	public static final String FREE = "FREE";
	public static final String USER = "USER";
	public static final String SYSTEM = "SYSTEM";
	public static final String TIME = "TIME";
	public static final String COMMITED = "COMMITED";
	public static final String PROCESSING = "PROCESSING";
	public static final String READY = "READY";
	public static final String PEEK = "PEEK";	
	//dest
	public static final String AGENT = "AGENT";
	public static final String CONSOLE = "CONSOLE";
	public static final String COLLECTOR = "COLLECTOR";
	public static final String SERVER = "SERVER";	
	//error level
	public static final String EVENTLEVEL_ERROR = "1";
	public static final String EVENTLEVEL_WARN = "2";
	public static final String EVENTLEVEL_INFO = "3";
	
}
