package monui;

import java.util.List;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;

import monui.impl.caster.CastInfo;
import monui.impl.util.UiUtil;
import monui.ui.component.base.YukMenu;
import monui.ui.component.usage.dialog.SettingDialog;
import monui.ui.component.usage.dialog.HelpDialog;
import monui.ui.layout.AgentLayout;
import monui.ui.layout.CollLayout;
import monui.ui.layout.DashLayout;
import monui.ui.layout.EventLayout;
import monui.ui.layout.stat.StatLayout;
import yuk.model.etc.InitData;

public class Main extends YukMenu implements View{
	public static final String name = "MainView";
	SettingDialog sd;
	HelpDialog hd;
	
	public Main() {
		setUserMenu();
		
		final List<InitData> initColl = UiUtil.getInitColl();
		final List<InitData> initAgent = UiUtil.getInitAgent();
		CastInfo info = new CastInfo("Dash");
		info.addTarget(initColl);
		info.addTarget(initAgent);
		DashLayout dashLayout = new DashLayout(info);
		setScreen(dashLayout);
		
		addMenuArea("Common");
		addItem("Common", "DashBoard", VaadinIcons.DASHBOARD, new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				CastInfo info = new CastInfo("Dash");
				info.addTarget(initColl);
				info.addTarget(initAgent);
				DashLayout dashLayout = new DashLayout(info);
				setScreen(dashLayout);
			}
		});
		
		addItem("Common", "Statistcal", VaadinIcons.NOTEBOOK, new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				CastInfo info = new CastInfo("Stat");
				info.addTarget(initColl);
				info.addTarget(initAgent);
				StatLayout statLayout = new StatLayout(info);
				setScreen(statLayout);
			}
		});
		
		addItem("Common", "Event", VaadinIcons.EXCLAMATION_CIRCLE_O, new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				CastInfo info = new CastInfo("Event");
				info.addTarget(initColl);
				info.addTarget(initAgent);
				EventLayout eventLayout = new EventLayout(info,true);
				setScreen(eventLayout);
			}
		});
		makeCollMenu(initColl);
	}

	private void makeCollMenu(List<InitData> initColl){
		for(final InitData data : initColl){
			addMenuArea(data.name);
			addItem(data.name, "Collector", VaadinIcons.SERVER, new ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					setScreen(new CollLayout(data));
				}
			});
			for(String agentName : data.childList)
				makeAgentMenu(data.name, agentName);
		}		
	}
	
	private void makeAgentMenu(String area, final String name){
		addItem(area, name, VaadinIcons.GLOBE_WIRE, new ClickListener() {			
			@Override
			public void buttonClick(ClickEvent event) {
				setScreen(new AgentLayout(name));
			}
		});
	}
	
	private void setUserMenu(){
		Command setting = new Command() {
			@Override
			public void menuSelected(MenuItem selectedItem) {
				if(sd == null)
					sd = new SettingDialog();
				if(!sd.isAttached())
					UiUtil.getUI().addWindow(sd);
				sd.center();					
			}
		};
		Command help = new Command() {
			@Override
			public void menuSelected(MenuItem selectedItem) {
				if(hd == null)
					hd = new HelpDialog();
				if(!hd.isAttached())
					UiUtil.getUI().addWindow(hd);
				hd.center();
			}
		};		
		Command signOut = new Command() {
			@Override
			public void menuSelected(MenuItem selectedItem) {
				UiUtil.getUI().getSession().setAttribute("user", null);
				UiUtil.getUI().getNavigator().navigateTo("");
			}
		};
		buildUserMenu("Yuk", setting,help, signOut);
	}	

	@Override
	public void enter(ViewChangeEvent event) {
		
	}
}
