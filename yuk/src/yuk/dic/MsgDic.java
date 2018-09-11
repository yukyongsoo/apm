package yuk.dic;

public abstract class MsgDic {
	public static final int ok = 0;
	public static final int nodata = 1;
	public static final int dberror = 2;	
	public static final int intererror = 3;
	public static final int neterror = 4;
			
	public static final String NODATA = "Data not found";
	public static final String DBERROR = "DB is not connected.";
	public static final String INTERNALERROR = "Program ocuuer internal error";	
	public static final String NETERROR = "target network not connected";	
}
