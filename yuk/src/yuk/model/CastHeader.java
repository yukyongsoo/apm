package yuk.model;

import java.io.Serializable;

import yuk.dic.MsgDic;

public class CastHeader implements Serializable{
	public CastHeader(String session){
		this.sessionNames = session;
	}
	
	public String sessionNames = "";
	public String range = "";
	public long from = 0;
	public long to = 0;
	public int code = MsgDic.ok;
	public String msg = "";
}
