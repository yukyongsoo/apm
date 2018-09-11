package yuk.config;

public abstract class BaseProperty {
	public static String MYNAME = "";
	public static String TYPE = "";
	public static String DESC = "";
	public static boolean TRACE = false;
	public static boolean Slf4j = false;
	public static boolean COMPRESS = true;
	
	public static long ResourceWorkerSec = 30;
	public static long TransWorkerSec = 30;
	public static long LogWorkerSec = 30;
	public static long EventWorkerSec = 30;
	public static long InfoWorkerSec = 30;
	public static long HealthWorkerSec = 30;
	public static long StackWorkerSec = 60;
	public static long DBHotWorkerSec = 30;
	public static long HotWorkerSec = 30;
	public static long ExcptWorkerSec = 30;
	public static long DisWorkerSec = 30;
}
