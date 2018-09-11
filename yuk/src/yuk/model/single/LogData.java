package yuk.model.single;

public class LogData  extends AbsData{
	public LogData(String name,String level, String log){
		this.name = name;
		this.level = level;
		this.log = log;		
	}
	
	public long time = 0;
	public String log = "";	
	public String level = "";
}
