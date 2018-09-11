package monui.ui.component.base;

import org.vaadin.justgage.JustGage;
import org.vaadin.justgage.JustGageConfiguration;

import com.vaadin.ui.HorizontalLayout;

public class GagePanel extends YukPanel{
	JustGage justGage;
	
	public GagePanel(String name, String caption) {
		super(name);		
		JustGageConfiguration config = new JustGageConfiguration();
		config.label = caption;
		justGage = new JustGage(config);
		justGage.setWidth(100, Unit.PERCENTAGE);
        setContent(justGage);
        setSizeFull();
	}
	
	
	public void setValue(int value){
		justGage.refresh(value);
	}


	@Override
	public void setMenu(HorizontalLayout menuBar) {
		
	}



}
