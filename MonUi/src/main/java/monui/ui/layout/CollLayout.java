package monui.ui.layout;

import com.vaadin.icons.VaadinIcons;

import monui.impl.caster.CastInfo;
import monui.ui.component.base.YukTab;
import monui.ui.layout.coll.CollOverViewLayout;
import monui.ui.layout.coll.DiskLayout;
import monui.ui.layout.coll.NetLayout;
import yuk.model.etc.InitData;

public class CollLayout extends YukTab{
	public CollLayout(InitData data) {
		setSizeFull();
		
		CastInfo info = new CastInfo(data.name);
		info.addTarget(data);
		info.addTarget(data.childList);
		addTabs(new CollOverViewLayout(info), "OverView", VaadinIcons.CHART_TIMELINE);
		
		CastInfo info2 = new CastInfo(data.name);
		info2.addTarget(data);
		addTabs(new NetLayout(info2), "Network", VaadinIcons.CONNECT);
		
		CastInfo info3 = new CastInfo(data.name);
		info3.addTarget(data);
		addTabs(new DiskLayout(info3), "Disk", VaadinIcons.HARDDRIVE);
	}
}
