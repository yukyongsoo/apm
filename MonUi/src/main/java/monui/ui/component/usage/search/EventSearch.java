package monui.ui.component.usage.search;

import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

import monui.impl.caster.CastInfo;
import monui.impl.caster.CasteeInterface;
import monui.impl.util.UiHelper;
import monui.impl.util.UiUtil;
import monui.ui.component.base.AbsSearch;
import monui.ui.component.base.YukButton;
import monui.ui.component.base.YukCombo;
import monui.ui.component.base.YukDate;
import monui.ui.component.base.YukFilterTable;
import monui.ui.component.base.YukText;
import monui.ui.component.notify.NotifyManager;
import yuk.dic.SystemDic;
import yuk.model.etc.InitData;
import yuk.util.NormalUtil;

public class EventSearch extends AbsSearch{
	YukFilterTable table;
	YukDate start;
	YukDate end;
	YukCombo agent;
	YukText level;
	
	public EventSearch(String name, CastInfo info,CasteeInterface target) {
		super(name, info, true, target);
	}
	
	public void setTable(YukFilterTable table) {
		this.table = table;
	}

	@Override
	public void tailWork(boolean value) {
		//do not thing
	}

	@Override
	public void content(HorizontalLayout contentLayout) {
		FormLayout layout = new FormLayout();
		layout.setSizeFull();
		
		agent = new YukCombo("Type");
		agent.setRequired(true);
		for(InitData data : UiUtil.getInitColl())
			agent.addItem(data.name);
		for(InitData data : UiUtil.getInitAgent())
			agent.addItem(data.name);
		layout.addComponent(agent);
		
		level = new YukText("Search Level");
		layout.addComponent(level);
		
		FormLayout layout2 = new FormLayout();
		layout2.setSizeFull();
		
		start = new YukDate("Search Start Time",Resolution.SECOND);	
		start.setRequired(true);
		layout2.addComponent(start);
		
		end = new YukDate("Search End Time",Resolution.SECOND);		
		end.setRequired(true);
		layout2.addComponent(end);
		
		FormLayout layout3 = new FormLayout();
		layout3.setSizeFull();
		
		Label dump = new Label();
		layout3.addComponent(dump);
		
		YukButton button = new YukButton("Search",true,30 * SystemDic.PCODE_SEC) {
			@Override
			public void clickFunction() {
				try{
					refresh = false;
					table.getContainerDataSource().removeAllItems();
					UiHelper helper = UiHelper.getHelper();
					if(!NormalUtil.nullValueChecker(agent.getValue(), true)){
						NotifyManager.getManager(getUI()).showErrorBox("Not Set Param", "Agent is not Seted");
						return;
					}
					String ret = UiUtil.checkTime(start.getValue().getTime(), end.getValue().getTime(), SystemDic.PCODE_MONTH);
					if(ret.length() > 1){
						NotifyManager.getManager(getUI()).showErrorBox("Wrong Time Set", ret);
						return;
					}
					helper.sendEvent(info.session, (String)agent.getValue(), level.getValue()
							, start.getValue().getTime(), end.getValue().getTime(),target);
				}
				catch (Exception e) {
					NotifyManager.getManager(getUI()).showErrorBox("Sending Error", e.getMessage());
				} 				
			}
		}; 
		layout3.addComponent(button);
		
		contentLayout.addComponent(layout);
		contentLayout.addComponent(layout2);
		contentLayout.addComponent(layout3);
	}

	

}
