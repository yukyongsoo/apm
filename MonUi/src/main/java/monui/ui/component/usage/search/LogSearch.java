package monui.ui.component.usage.search;

import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

import monui.impl.caster.CastInfo;
import monui.impl.caster.CasteeInterface;
import monui.impl.util.UiHelper;
import monui.sTable.YukSTable;
import monui.ui.component.base.AbsSearch;
import monui.ui.component.base.YukButton;
import monui.ui.component.base.YukDate;
import monui.ui.component.base.YukText;
import monui.ui.component.notify.NotifyManager;
import yuk.dic.SystemDic;

public class LogSearch extends AbsSearch{
	YukDate start;
	YukDate end;
	YukText text;
	YukText level;
	YukSTable table;

	public LogSearch(String caption, CastInfo info,CasteeInterface target) {
		super(caption, info, false, target);
	}
	
	public void setTable(YukSTable table) {
		this.table = table;
	}

	@Override
	public void tailWork(boolean value) {
		refresh = value;
		table.removeAllItems();
	}

	@Override
	public void content(HorizontalLayout contentLayout) {
		FormLayout layout = new FormLayout();
		layout.setSizeFull();
		
		start = new YukDate("Search Start Time",Resolution.SECOND);	
		start.setRequired(true);
		layout.addComponent(start);
		
		end = new YukDate("Search End Time",Resolution.SECOND);	
		end.setRequired(true);
		layout.addComponent(end);
		
		FormLayout layout2 = new FormLayout();
		layout2.setSizeFull();
		
		level = new YukText("Search Level");
		layout2.addComponent(level);
		
		text = new YukText("Search Text");
		layout2.addComponent(text);
		
		FormLayout layout3 = new FormLayout();
		layout3.setSizeFull();
		
		Label dump = new Label();
		layout3.addComponent(dump);
		
		YukButton button = new YukButton("Search",true, SystemDic.PCODE_MIN) {
			@Override
			public void clickFunction() {
				try{
					refresh = false;
					tailer.setValue(false);
					table.removeAllItems();
					UiHelper helper = UiHelper.getHelper();
					helper.sendLog(info.session, info.name, start.getValue().getTime(), 
							end.getValue().getTime(), level.getValue(), text.getValue(),target);
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
