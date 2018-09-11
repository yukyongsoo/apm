package monui.ui.component.usage.agent;

import java.util.List;
import java.util.Map;

import monui.ui.component.base.YukList;

public class StackPanel extends YukList{	
	Map<String, List<String>> stackTrace;
	
	public StackPanel(String string) {
		setCaption(string);
	}

	public void updateList(Map<String, List<String>> stackTrace) {
		this.stackTrace = stackTrace;
	}

	public void changeList(String name){
		List<String> list = stackTrace.get(name);
		removeAllItems();
		if(list != null)
			addItems(list);
	}
}
