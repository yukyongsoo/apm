package monui.ui.component.usage.agent;

import com.vaadin.ui.Panel;

import monui.ui.component.base.YukTable;

public class QueryPanel extends Panel{
	YukTable table;
	
	public QueryPanel(String name) {
		setCaption(name);

		table = new YukTable();
		table.setSizeFull();
		table.addColumn("query", String.class);
		table.addColumn("execution", long.class);
		table.addColumn("avarage", long.class);
		table.addColumn("totalTime", long.class);
		setContent(table);
	}
}
