package yuk.model.etc;

import yuk.model.AbsFake;

public class SessionData extends AbsFake{
	public SessionData(String name, String sId){
		this.name = name;
		this.channelId = sId;
	}
	
	public String name = "";
	public boolean remove = false;
	public String channelId = "";
}
