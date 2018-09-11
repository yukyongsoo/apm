package monui.ui.component.usage;

import java.util.Map;

import com.vaadin.ui.VerticalLayout;

import monui.ui.component.base.YukTable;
import yuk.model.etc.InitData;

public class SettingContent extends VerticalLayout{
	YukTable table;
	
	public SettingContent() {
		setSizeFull();
		
		table = new YukTable();
		table.setSizeFull();
		
		table.addColumn("Key", String.class);
		table.addColumn("Value", Object.class);
		
		addComponent(table);
	}

	public void updateData(InitData data) {
		Map<String, Map<String, Object>> settingData = data.settingData;
		table.removeAllItems();
		for(Map<String, Object> subMap: settingData.values()){
			for(String key : subMap.keySet()){	
				Object object = subMap.get(key);
				table.addOrRefreshData(key, new Object[]{key,object});
			}			
		}		
	}
}
