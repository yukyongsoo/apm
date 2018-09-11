package monui.ui.component.usage.search;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;

import monui.impl.caster.CastInfo;
import monui.impl.caster.CasteeInterface;
import monui.impl.util.UiHelper;
import monui.impl.util.UiUtil;
import monui.ui.component.abs.AbsGrid;
import monui.ui.component.base.AbsSearch;
import monui.ui.component.base.YukButton;
import monui.ui.component.base.YukCombo;
import monui.ui.component.base.YukDate;
import monui.ui.component.notify.NotifyManager;
import monui.ui.layout.stat.StatAgent;
import monui.ui.layout.stat.StatColl;
import monui.ui.layout.stat.StatLayout;
import yuk.dic.ModelDic;
import yuk.dic.SystemDic;
import yuk.model.etc.InitData;
import yuk.util.NormalUtil;

public class StatSearch extends AbsSearch{
	YukCombo agent;
	YukCombo type;
	YukDate start;
	YukDate end;
	AbsGrid realtTarget;
	
	public StatSearch(String caption, CastInfo info,CasteeInterface target) {
		super(caption, info, true, target);
	}
	
	@Override
	public void tailWork(boolean value) {
		//noting to do
	}

	@Override
	public void content(HorizontalLayout contentLayout) {
		FormLayout layout = new FormLayout();
		contentLayout.addComponent(layout);
		
		agent = new YukCombo("Type");
		agent.setRequired(true);
		for(InitData data : UiUtil.getInitAgent())
			agent.addItem(data.name);
		for(InitData data : UiUtil.getInitColl())
			agent.addItem(data.name);
		layout.addComponent(agent);
		agent.addValueChangeListener(new ValueChangeListener() {	
			@Override
			public void valueChange(ValueChangeEvent event) {
				String name = (String) event.getProperty().getValue();
				if(name != null){
					InitData data = UiUtil.getInit(name);	
					if(data.type.equals(ModelDic.COLLECTOR)){
						realtTarget = new StatColl(info);
						((StatLayout)target).setContent(realtTarget);
						
					}
					else{
						realtTarget = new StatAgent(info);
						((StatLayout)target).setContent(realtTarget);
						
					}
				}
			}
		});
			
		type = new YukCombo("Period");
		type.setRequired(true);
		type.addItems("Miunte","Hour","Day","Month");
		layout.addComponent(type);
		
		FormLayout layout2 = new FormLayout();
		contentLayout.addComponent(layout2);
		
		
		start = new YukDate("Start Date :",Resolution.SECOND);		
		start.setRequired(true);
		layout2.addComponent(start);
		
		end = new YukDate("End Date :",Resolution.SECOND);		
		end.setRequired(true);
		layout2.addComponent(end);
		
		FormLayout layout3 = new FormLayout();
		contentLayout.addComponent(layout3);
		
		YukButton button = new YukButton("Search",true,30 * SystemDic.PCODE_SEC) {
			@Override
			public void clickFunction() {
				try {
					UiHelper helper = UiHelper.getHelper();
					if(!NormalUtil.nullValueChecker( agent.getValue(), true)){
						NotifyManager.getManager(getUI()).showErrorBox("Not Set Param", "Agent is not Seted");
						return;
					}						
					if(!NormalUtil.nullValueChecker( type.getValue(), true)){
						NotifyManager.getManager(getUI()).showErrorBox("Not Set Param", "Type is not Seted");
						return;
					}
					String ret = UiUtil.checkTime(start.getValue().getTime(), end.getValue().getTime(), SystemDic.PCODE_MONTH);
					if(ret.length() > 1){
						NotifyManager.getManager(getUI()).showErrorBox("Wrong Time Set", ret);
						return;
					}
					helper.sendStat(info.session, (String) agent.getValue(), 
							setType(type.getValue()), start.getValue().getTime(), end.getValue().getTime(),realtTarget);
				} catch (Exception e) {
					NotifyManager.getManager(getUI()).showErrorBox("Sending Error", e.getMessage());
				}				
			}
		};
		layout3.addComponent(button);
	}
	
	private String setType(Object object) throws Exception{
		String temp = (String)object;
		if(temp.equals("Second"))
			return ModelDic.SEC;
		else if(temp.equals("Miunte"))
			return ModelDic.Min;
		else if(temp.equals("Hour"))
			return ModelDic.Hour;
		else if(temp.equals("Day"))
			return ModelDic.Day;
		else if(temp.equals("Month"))
			return ModelDic.Month;		
		else
			throw new Exception("not supported Time Type");
	}	
}
