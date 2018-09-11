package yuk.dic;


public abstract class SystemDic {
	// periodCode
	public static final long PCODE_SEC = 1000L;
	public static final long PCODE_MIN = PCODE_SEC * 60;
	public static final long PCODE_Hour = PCODE_MIN * 60;
	public static final long PCODE_Day = PCODE_Hour * 24;
	public static final long PCODE_MONTH = PCODE_Day *30;
	public static final long PCODE_YEAR = PCODE_MONTH * 12;
	//system command
	public static final int MONSHUTDOWN = 1001;
	public static final int MONRESTART = 1002;
	public static final int DBSHUTDOWN = 1000;
	//setting property
	public static final String PROPERTY = "PROPERTY";
	public static final String EXTENDER = "EXTENDER";
	
	//public static final Class hotSpotClass = new ArrayList<ThreadHotSpotData>().getClass();
	//public static final Class dbHotSpotClass = new ArrayList<DBHotSpotData>().getClass();
	//public static final Class excptClass = new ArrayList<ExceptionData>().getClass();
}
