package monui.ui.component.base;

import java.util.HashMap;

import com.vaadin.server.Resource;
import com.vaadin.ui.Component;
import com.vaadin.ui.TabSheet;

import monui.impl.caster.CasteeInterface;

public class YukTab extends TabSheet{
	private HashMap<String, Tab> map = new HashMap<String, Tab>();
	
	public YukTab() {
		addStyleName("framed");
		addSelectedTabChangeListener(new SelectedTabChangeListener() {
			@Override
			public void selectedTabChange(SelectedTabChangeEvent event) {
				Component c = event.getComponent();
				if(c instanceof CasteeInterface){
					((CasteeInterface)c).refresh();
				}				
			}
		});
		markAsDirtyRecursive();
	}
	
	public void addTabs(Component c, String caption, Resource icon){
		Tab tab = addTab(c, caption);
		if(icon != null)
			tab.setIcon(icon);
		map.put(caption, tab);
	}	
	
	public void removeTabs(String caption){
		Tab tab = map.get(caption);
		if(tab != null)
			removeTab(tab);
	}	
}
