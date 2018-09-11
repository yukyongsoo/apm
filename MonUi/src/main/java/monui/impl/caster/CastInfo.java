package monui.impl.caster;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import yuk.model.etc.InitData;

public class CastInfo {
	public CastInfo(String name) {
		this.name = name;
	}
	
	public void addTarget(InitData data){
		this.target.add(data.name);
	}
	
	public void addTarget(Collection<InitData> datas){
		for(InitData data : datas)
			this.target.add(data.name);
	}
	
	public void addTarget(List<String> childList) {
		this.target.addAll(childList);
	}
	
	public void addTarget(String name) {
		this.target.add(name);
	}
	
	public String name = "";
	public String session = "";
	public List<String> target = new ArrayList<String>();
	
	
}
