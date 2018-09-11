package monui.ui.component.usage.dash;

import org.joda.time.format.DateTimeFormatter;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;

import monui.ui.component.base.YukTable;
import yuk.model.etc.EventData;
import yuk.util.NormalUtil;

public class EventPanel extends Panel{
	YukTable table;
	long size = 0;
	
	public EventPanel() {
		setSizeFull();
		setCaption("Entire Event");
		
		HorizontalLayout layout = new HorizontalLayout();
		layout.setSizeFull();
		
		table = new YukTable();
		table.setSizeFull();
		table.addColumn("Time", String.class);
		table.addColumn("Name", String.class);
		table.addColumn("Level", String.class);
		table.addColumn("Text", String.class);
		
		table.setColumnFooter("Time", "Total Num");
		table.setColumnFooter("Agent", String.valueOf(table.getContainerDataSource().getItemIds().size()));
		table.setFooterVisible(true);
		
		
		layout.addComponent(table);
		layout.setExpandRatio(table, 7f);
				
		setContent(layout);
	}

	public void updateData(final EventData data, UI ui) {
		size += 1;
		DateTimeFormatter dateFomat = NormalUtil.getDateFomat("");
		final String print = dateFomat.print(data.time);
		ui.access(new Runnable() {
			@Override
			public void run() {
				table.addOrRefreshData(print, new Object[]{print,data.name,data.eventLevel,data.eventLog});
			}
		});
		ui.push();
	}
	
	
}
