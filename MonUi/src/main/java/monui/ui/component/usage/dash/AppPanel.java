package monui.ui.component.usage.dash;

import java.util.HashMap;
import java.util.Map;

import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;

import monui.impl.util.IconDic;
import monui.impl.util.UiUtil;
import monui.ui.component.base.MetricComponent;
import yuk.model.single.HealthData;

public class AppPanel extends Panel{
	Map<String, Boolean> serverMap = new HashMap<String, Boolean>();
	Map<String, Boolean> appMap = new HashMap<String, Boolean>();
	MetricComponent serverOn;
	MetricComponent serverOff;
	MetricComponent appOn;
	MetricComponent appOff;
	
	public AppPanel() {
		setSizeFull();
		setCaption("Application Status");
		
		GridLayout layout = new GridLayout();
		layout.setColumns(2);
		layout.setRows(2);
		layout.setSpacing(true);
		layout.setSizeFull();
		
		serverOn = new MetricComponent(IconDic.serverOn, "Server online");
		serverOn.setValue("0");
		layout.addComponent(serverOn);
		
		serverOff = new MetricComponent(IconDic.serverDown, "Server Offline");
		serverOff.setValue("0");
		layout.addComponent(serverOff);
		
		appOn = new MetricComponent(IconDic.appOn, "App online");
		appOn.setValue("0");
		layout.addComponent(appOn);
		
		appOff = new MetricComponent(IconDic.appDown, "App offline");
		appOff.setValue("0");
		layout.addComponent(appOff);
				
		setContent(layout);
	}

	public void updateData(final HealthData data, UI ui) {
		ui.access(new Runnable() {
			@Override
			public void run() {
				int sOn = 0;
				int sOff = 0;
				int aOn = 0;
				int aOff = 0;
				if(UiUtil.isColl(data.name))
					serverMap.put(data.name, data.status);
				else
					appMap.put(data.name, data.status);
				for(String name : serverMap.keySet()){
					if(serverMap.get(name))
						sOn += 1;
					else
						sOff += 1;
				}
				for(String name : appMap.keySet()){
					if(appMap.get(name))
						aOn += 1;
					else
						aOff += 1;
				}
				serverOn.setValue(String.valueOf(sOn));
				serverOff.setValue(String.valueOf(sOff));
				appOn.setValue(String.valueOf(aOn));
				appOff.setValue(String.valueOf(aOff));
			}
		});
		ui.push();
	}
}
