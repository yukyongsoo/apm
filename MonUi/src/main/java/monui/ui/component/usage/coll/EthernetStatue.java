package monui.ui.component.usage.coll;

import java.util.HashMap;
import java.util.Map;

import monui.ui.component.base.YukTable;

public class EthernetStatue extends YukTable{
	Map<String, Map<String, Object>> dataList = new HashMap<String, Map<String,Object>>();
	
	public EthernetStatue(String name) {
		super(name);
		setSizeFull();
		
		addColumn("Type", String.class);
		addColumn("Value", Double.class);
	}

	public void updateData(Map<String, Map<String, Object>> dataList) {
		this.dataList = dataList;
	}
	
	public void changeData(String name){
		Map<String, Object> map = dataList.get(name);
		if(map != null){
			addOrRefreshData("MTU", new Object[]{"MTU",map.get("mtu")});
			addOrRefreshData("Max Speed", new Object[]{"Max Speed",map.get("speed")});
			addOrRefreshData("Read", new Object[]{"Read",map.get("Read")});
			addOrRefreshData("Write", new Object[]{"Write",map.get("Write")});
			addOrRefreshData("Read Error", new Object[]{"Read Error",map.get("readError")});
			addOrRefreshData("Write Error", new Object[]{"Write Error",map.get("writeError")});
		}
	}
}
