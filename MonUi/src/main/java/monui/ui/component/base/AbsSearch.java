package monui.ui.component.base;

import org.vaadin.teemu.switchui.Switch;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;

import monui.impl.caster.CastInfo;
import monui.impl.caster.CasteeInterface;

public abstract class AbsSearch extends YukPanel{
	protected boolean refresh = true;
	String caption;
	protected Switch tailer;
	protected CastInfo info;
	protected boolean hide;
	protected CasteeInterface target;
	public abstract void tailWork(boolean value);
	public abstract void content(HorizontalLayout contentLayout);
	
	public boolean isRefresh() {
		return refresh;
	}
		
	@Override
	public void preInit(Object o) {
		this.hide = (Boolean) o;
	}

	public AbsSearch(String name, CastInfo info, final boolean hide,CasteeInterface target) {
		super(name, false, hide);
		this.info = info;
		this.caption = name;
		this.target = target;
		
		setWidth(100, Unit.PERCENTAGE);
		
		
		HorizontalLayout layout = new HorizontalLayout();
		layout.setMargin(new MarginInfo(false, false, false, true));
		layout.setSpacing(true);
		setContent(layout);
		content(layout);
	}
	
	@Override
	public void setMenu(HorizontalLayout menuBar){
		if(!hide){
			FormLayout layout = new FormLayout();
			layout.setMargin(false);
			layout.setSpacing(false);
			menuBar.addComponent(layout);
		
			tailer  = new Switch("Auto Refresh", true);
			tailer.addValueChangeListener(new ValueChangeListener() {
				@Override
				public void valueChange(ValueChangeEvent event) {
					boolean check = (Boolean) event.getProperty().getValue();
					tailWork(check);
				}
			});
			tailer.setAnimationEnabled(true);
			tailer.setStyleName("holodeck");
			layout.addComponent(tailer);
		}
	}
}
