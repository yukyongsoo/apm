package monui.ui.layout;

import com.vaadin.icons.VaadinIcons;

import monui.impl.caster.CastInfo;
import monui.ui.component.base.YukTab;
import monui.ui.layout.agent.AgentOverViewLayout;
import monui.ui.layout.agent.DbHotLayout;
import monui.ui.layout.agent.ExceptionLayout;
import monui.ui.layout.agent.HotLayout;
import monui.ui.layout.agent.LogLayout;
import monui.ui.layout.agent.MemLayout;
import monui.ui.layout.agent.ThreadLayout;

public class AgentLayout extends YukTab{
	public AgentLayout(String name) {
		setSizeFull();
		
		CastInfo info = new CastInfo(name);
		info.addTarget(name);
		
		addTabs(new AgentOverViewLayout(info), "OverView", VaadinIcons.CHART_TIMELINE);
		addTabs(new LogLayout(info), "Log", VaadinIcons.FILE_TEXT);
		addTabs(new ThreadLayout(info), "Thread", VaadinIcons.FORM);
		addTabs(new HotLayout(info), "HotSpot", VaadinIcons.EYE);
		addTabs(new DbHotLayout(info), "DB HotSpot", VaadinIcons.CROSSHAIRS);
		addTabs(new ExceptionLayout(info), "Exception", VaadinIcons.BUG);
		addTabs(new MemLayout(info), "Memory", VaadinIcons.CUBES);
	}
}
