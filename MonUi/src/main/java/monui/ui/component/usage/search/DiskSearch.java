package monui.ui.component.usage.search;

import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.themes.ValoTheme;

import monui.impl.caster.CastInfo;
import monui.impl.caster.CasteeInterface;
import monui.impl.util.UiHelper;
import monui.ui.component.base.AbsSearch;
import monui.ui.component.base.YukDate;
import monui.ui.component.notify.NotifyManager;

public class DiskSearch extends AbsSearch{
	YukDate date;
	
	public DiskSearch(String caption, CastInfo info,CasteeInterface target) {
		super(caption, info, false, target);
	}

	@Override
	public void tailWork(boolean value) {
		refresh = value;
	}

	@Override
	public void content(HorizontalLayout contentLayout) {
		FormLayout layout = new FormLayout();
		layout.setSizeFull();
		
		date = new YukDate("Search Time",Resolution.SECOND);	
		layout.addComponent(date);
		contentLayout.addComponent(layout);
		
		FormLayout layout2 = new FormLayout();
		layout2.setSizeFull();
		
		Button button = new Button("Search");
		button.addStyleName(ValoTheme.BUTTON_PRIMARY);
		button.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				try {
					refresh = false;
					tailer.setValue(false);
					UiHelper helper = UiHelper.getHelper();
					helper.sendDisk(info.session, info.name, date.getValue().getTime(),target);
				} catch (Exception e) {
					NotifyManager.getManager(getUI()).showErrorBox("Sending Error", e.getMessage());
				} 
			}
		});		
		layout2.addComponent(button);
		contentLayout.addComponent(layout2);
	}



	
}
