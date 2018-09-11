package yuk.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MonitorData<T> implements Serializable {	
	public MonitorData(String target, int type,CastHeader castHeader){
		this.target = target;
		this.type = type;
		this.castHeader = castHeader;
	}
	public String target = "";
	public int type = 999;
	public CastHeader castHeader= null;
	public List<T> dataList = new ArrayList<T>();
}
