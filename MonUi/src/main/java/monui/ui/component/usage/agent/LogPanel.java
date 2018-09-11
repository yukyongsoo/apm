package monui.ui.component.usage.agent;

import com.vaadin.ui.Panel;

import monui.ui.component.base.YukTable;
import yuk.model.single.LogData;

public class LogPanel extends Panel{
	YukTable table ;
	
	public LogPanel(String name) {
		setCaption(name);
		table = new YukTable();
		table.setSizeFull();
		table.addColumn("level", String.class);
		table.addColumn("count", Long.class);
		setContent(table);
	}

	public void updateData(final LogData log) {
		table.addOrUpdateData(log.level, new Object[]{log.level,1L});
	}

}
