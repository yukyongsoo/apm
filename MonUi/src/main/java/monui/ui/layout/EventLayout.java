package monui.ui.layout;

import java.util.Collection;

import monui.impl.caster.CastInfo;
import monui.ui.component.abs.AbsVer;
import monui.ui.component.base.YukFilterTable;
import monui.ui.component.notify.NotifyManager;
import monui.ui.component.usage.search.EventSearch;
import yuk.model.CastHeader;
import yuk.model.etc.EventData;
import yuk.util.NormalUtil;

public class EventLayout extends AbsVer{
	YukFilterTable table;
	EventSearch searchMenu;
	
	public EventLayout(CastInfo info,boolean search) {
		super(info);
		setSizeFull();
		
		if(search){
			searchMenu = new EventSearch("Event Search", info,this);
			addComponent(searchMenu);
			searchMenu.setSizeUndefined();
			searchMenu.setWidth(100, Unit.PERCENTAGE);
		}
		
		table = new YukFilterTable();
		table.setSizeFull();
		table.addColumn("Time", String.class);
		table.addColumn("Name", String.class);
		table.addColumn("EventLevel", String.class);
		table.addColumn("EventText", String.class);
		addComponent(table);
		setExpandRatio(table, 1f);
		
		if(search)
			searchMenu.setTable(table);		
	}
	
	@Override
	public void singleEvent(CastHeader header, final EventData data) throws Exception {
		if(!isAttached())
			return;
		if(searchMenu.isRefresh())
			updateData(data);		
		ui.push();
	}
	
	@Override
	public void targetEvent(CastHeader header, Collection<EventData> dataSet) throws Exception {
		if(!isAttached())
			return;
		if(NotifyManager.getManager(ui).showErrordata(header))
			return;
		for(EventData data : dataSet){
			if (!searchMenu.isRefresh()) {
				updateData(data);				
			}
		}
		ui.push();
	}
	
	private void updateData(final EventData data){
		ui.access(new Runnable() {
			@Override
			public void run() {
				String time = NormalUtil.getDateFomat("").print(data.time);
				table.addOrRefreshData(time, new Object[]{time,data.name,data.eventLevel,data.eventLog});				
			}
		});		
	}
}
