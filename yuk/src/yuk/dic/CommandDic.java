package yuk.dic;

import java.nio.charset.Charset;

public abstract class CommandDic {
	public static final String COMMAND_ARCHIVE = "ARCHIVE";
	public static final String COMMAND_MEM = "MEMORY";
	public static final String COMMAND_GC = "GC";
	public static final String COMMAND_MEMPOOL = "MEMPOOL";
	public static final String COMMAND_CPU = "CPU";	
	public static final String COMMAND_DBCONN = "DB Connection";
	public static final String COMMAND_DATACONN = "Data Connection";	
	
	public static final String COMMAND_SYSDISK = "SYSTEM IO";
	public static final String COMMAND_SYSNET = "SYSTEM NETWORK";
	public static final String COMMAND_SYSMEM = "SYSTEM MEMORY";
	public static final String COMMAND_SYSCPU = "SYSTEM CPU";
	public static final String COMMAND_SYSSWAP = "SYSTEM SWAP";	
	
	public static final String COMMAND_TRANS = "TRANSACTION";	
	public static final String COMMAND_RESPONSE = "RESPONSE";	
	public static final String COMMAND_FAILTRANS = "FAIL TRANSACTION";	
		
	public static final Charset charset = Charset.forName("UTF-8");	
}
