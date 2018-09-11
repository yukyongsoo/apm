package monui.ui.layout.agent;

import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;

import monui.impl.caster.CastInfo;
import monui.ui.component.abs.AbsHoz;
import monui.ui.component.base.YukTable;
import monui.ui.component.usage.agent.ExceptionInfo;
import yuk.model.CastHeader;
import yuk.model.multi.ExcptContainer;
import yuk.model.single.ExceptionData;


public class ExceptionLayout extends AbsHoz{
	YukTable table;
	ExceptionInfo exceptionInfo;
	
	public ExceptionLayout(CastInfo info) {
		super(info);
		setSizeFull();
		setSpacing(true);
		
		table = new YukTable("Error Type");
		table.setSizeFull();
		table.addStyleName("bold");
		table.addColumn("Name", String.class);
		table.addColumn("Invocation", Long.class);
		table.addItemClickListener(new ItemClickListener() {
			@Override
			public void itemClick(ItemClickEvent event) {
				String value = (String)event.getItem().getItemProperty("Name").getValue();
				if(value != null)
					exceptionInfo.changeData(value);
			}
		});
		addComponent(table);
		setExpandRatio(table, 3);
		
		exceptionInfo = new ExceptionInfo();
		exceptionInfo.setSizeFull();
		addComponent(exceptionInfo);
		setExpandRatio(exceptionInfo, 7);
	}
	
	@Override
	public void singleExcpt(CastHeader header, final ExcptContainer data) throws Exception {
		if(!isAttached())
			return;
		ui.access(new Runnable() {
			@Override
			public void run() {
				for(ExceptionData excpt : data.list){
					table.addOrUpdateData(excpt.type, new Object[]{excpt.type,excpt.count});
				}
			}
		});
		exceptionInfo.updateData(data);
		ui.push();
	}
}
