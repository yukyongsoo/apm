package monui.ui.layout.agent;

import monui.impl.caster.CastInfo;
import monui.ui.component.abs.AbsVer;
import monui.ui.component.base.YukFilterTable;
import yuk.model.CastHeader;
import yuk.model.multi.ThreadContainer;
import yuk.model.single.ThreadHotSpotData;

public class HotLayout extends AbsVer{
	YukFilterTable method;
	
	public HotLayout(CastInfo info) {
		super(info);
		setSizeFull();
		
		method = new YukFilterTable();
		method.addColumn("name", String.class);
		method.addColumn("excution", Long.class);
		method.addColumn("totalTime", Long.class);
		method.addColumn("average", Long.class);
		method.setPageLength(3);
		method.setSizeFull();
		addComponent(method);
		setExpandRatio(method, 1f);
	}
	
	@Override
	public void singleHotSpot(CastHeader header, final ThreadContainer data) throws Exception {
		if(!isAttached())
			return;
		ui.access(new Runnable() {
			@Override
			public void run() {
				for(ThreadHotSpotData newer : data.list)
				method.addOrUpdateData(newer.methodName,
						new Object[]{newer.methodName,newer.count,newer.totalTime,newer.avgTime});
			}
		});
		ui.push();
	}
}
