package yuk.model.etc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import yuk.model.AbsFake;

public class InitData extends AbsFake{
	public InitData(String name, String type,Map<String, String> info){
		this.name = name;
		this.type = type;
		this.infoData = info;
	}
	
	private static final long serialVersionUID = 1L;
	public String name = "";
	public String parent = "";
	public String type = "";
	public Map<String, String> infoData;
	public Map<String,  Map<String, Object>> settingData = new HashMap<String, Map<String,Object>>();
	public List<String> childList = new ArrayList<String>();
}
