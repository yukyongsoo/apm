package monui.ui.layout.agent;

import java.util.Collection;

import org.joda.time.format.DateTimeFormatter;

import monui.impl.caster.CastInfo;
import monui.sTable.YukSTable;
import monui.ui.component.abs.AbsVer;
import monui.ui.component.notify.NotifyManager;
import monui.ui.component.usage.search.LogSearch;
import yuk.model.CastHeader;
import yuk.model.multi.LogContainer;
import yuk.model.single.LogData;
import yuk.util.NormalUtil;

public class LogLayout  extends AbsVer{
	YukSTable table;
	LogSearch logMenu;
	DateTimeFormatter format;
	
	public LogLayout(CastInfo info) {
		super(info);
		setSpacing(true);
		
		format = NormalUtil.getDateFomat("");
		logMenu = new LogSearch("Log Search",info,this);
		addComponent(logMenu);
		logMenu.setSizeUndefined();
		logMenu.setWidth(100, Unit.PERCENTAGE);
		
		table = new YukSTable("Time","Level","Log");
		table.setWidth(100, Unit.PERCENTAGE);
		logMenu.setTable(table);
		addComponent(table);
		setExpandRatio(table, 1f);
	}
	
	@Override
	public void singleLog(CastHeader header, LogContainer data) throws Exception {
		if(!isAttached())
			return;
		if(logMenu.isRefresh())
			addLog(data);
	}
	
	@Override
	public void targetLog(CastHeader header, Collection<LogContainer> dataSet) throws Exception {
		if(!isAttached())
			return;
		if(NotifyManager.getManager(ui).showErrordata(header))
			return;
		for(LogContainer data : dataSet){
			if(!logMenu.isRefresh())
				addLog(data);		
		}
	}
	
	private void addLog(final LogContainer data){
		for (LogData log : data.list) {
			if (logMenu.isRefresh() && table.getSize() > 1000)
				table.removeItem();
			String time = format.print(log.time);
			table.addLogItem(time, log.level, log.log);
		}
		table.draw();			
	}
	
	@Override
	public void detach() {
		table.removeAllItems();
		super.detach();
	}
}
