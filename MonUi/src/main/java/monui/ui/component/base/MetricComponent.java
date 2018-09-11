package monui.ui.component.base;

import org.vaadin.alump.scaleimage.ScaleImage;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import monui.impl.util.UiUtil;

public class MetricComponent extends Panel{
	Label value;
	
	public MetricComponent(String icon,String name) {
		setSizeFull();
		setCaption(name);
		
		VerticalLayout layout = new VerticalLayout();
		layout.setSizeFull();
		layout.setMargin(true);
		setContent(layout);
		
		ScaleImage bigCenterImage = new ScaleImage();
		bigCenterImage.setHeight(64, Unit.PIXELS);
		bigCenterImage.setWidth(64, Unit.PIXELS);
	    bigCenterImage.setSource(UiUtil.getResource(icon));
	    layout.addComponent(bigCenterImage);
				
		value = new Label();
		value.addStyleName(ValoTheme.LABEL_BOLD);
		value.addStyleName(ValoTheme.LABEL_LARGE);
		value.addStyleName("label-right");
		value.setSizeFull();
		
		
		
		layout.addComponent(value);
		layout.setComponentAlignment(value, Alignment.MIDDLE_RIGHT);
	}
	
	public void setValue(String value){
		this.value.setValue(value);
	}
}
