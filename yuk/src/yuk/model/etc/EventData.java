package yuk.model.etc;

import yuk.model.AbsFake;

public class EventData extends AbsFake{
	public EventData(String name, String level, String log){
		this.name = name;
		this.eventLevel = level;
		this.eventLog = log;
		this.time = System.currentTimeMillis();
	}
	
	public long time = 0;
	public String name = "";
	public String eventLevel = "";
	public String eventLog = "";
}
